{-# LANGUAGE ScopedTypeVariables #-}

module Hashtable
  ( -- * Constructors
    ConcurrentHashTable (..)
  , newCHT
  , putCHT
  , getCHT
  , sizeCHT
  ) where

import Control.Monad          (when)
import Data.List              (find)
import Data.Hashable          (Hashable, hash)
import Control.Concurrent.STM (STM, TVar, readTVarIO, newTVar, readTVar
                              , atomically, writeTVar
                              )
import Data.Vector            (Vector)
import qualified Data.Vector as Vec

-- | Lock-free hashtable elements container alias. Using 'Tvar'
type Storage k v = TVar (Vector (TVar [(k, v)]))

-- | Lock-free size value alias. Using 'Tvar'
type Size        = TVar Int

-- | Lock-free capacity value alias. Using 'TVar'
type Capacity    = TVar Int

-- Represents hashtable on 'Vector' of 'List's
data ConcurrentHashTable k v =
  ConcurrentHashTable
  { chtStorage  :: Storage k v -- ^ Storage of elements
  , chtSize     :: Size        -- ^ Size of hashable
  , chtCapacity :: Capacity    -- ^ Capacity of hashable
  }

-- | Instantiates a new empty concurrent hashtable.
newCHT
  :: IO (ConcurrentHashTable k v)
newCHT = atomically $ do
  let initialCapacity = 16
  storage  <- newTVar =<< Vec.replicateM initialCapacity (newTVar [])
  size     <- newTVar 0
  capacity <- newTVar initialCapacity
  return $ ConcurrentHashTable { chtStorage  = storage
                               , chtSize     = size
                               , chtCapacity = capacity
                               }

-- | Retrieves a value from concurrent hashtable by key, or returns Nothing if absent.
getCHT
  :: (Hashable k, Eq k)      -- ^ 'Hashable' an 'Eq' constraint for key value
  => k                       -- ^ key
  -> ConcurrentHashTable k v -- ^ hashable to find
  -> IO (Maybe v)            -- ^ found element
getCHT key cht = atomically $ do
  capacity      <- readTVar $ chtCapacity cht
  storage       <- readTVar $ chtStorage cht
  let index     = keyHashIndex key capacity
  valuesAtIndex <- readTVar $ Vec.unsafeIndex storage index
  let valuesMatchingKey = find ((== key) . fst) valuesAtIndex
  case valuesMatchingKey of
    Just (_, v) -> return $ Just v
    Nothing     -> return Nothing

-- | Calculate storage index by key and hashtable capacity.
keyHashIndex
  :: Hashable k -- ^ 'Hashable' constraint for key value
  => k          -- ^ key value
  -> Int        -- ^ hashable capacity
  -> Int        -- ^ Index for hashtable cell related to hash value of key
keyHashIndex key = mod (hash key)

-- | Appends key-value pair to list if absent, or replaces existing pair with that key.
insertPair
  :: Eq k
  => k                -- ^ key
  -> v                -- ^ value
  -> [(k, v)]         -- ^ list to insert
  -> (Bool, [(k, v)]) -- ^ modified list with inserted condition
insertPair key value [] = (True, [(key, value)])
insertPair key value ((k, v) : xs)
  | k == key  = (False, (k, value) : xs)
  | otherwise = (appended, (k, v) : next)
  where (appended, next) = insertPair key value xs

-- | Relocates bucket's elements to a new hashtable storage.
copyElements
  :: forall k v. (Hashable k, Eq k)
  => Int                     -- ^ new capacity
  -> Vector (TVar [(k, v)])  -- ^ new vector
  -> TVar [(k, v)]           -- ^ bucket
  -> STM ()
copyElements capacity newVector bucket = do
  elements <- readTVar bucket
  mapM_ copy elements
 where
   copy
     :: (k, v) -- ^ pair to copy
     -> STM ()
   copy (k, v) = do
     let index      = keyHashIndex k capacity
     let storage    = Vec.unsafeIndex newVector index
     list           <- readTVar storage
     let (_, list') = insertPair k v list
     writeTVar storage list'

-- | Put key-value pair into the hashtable, or replace value for key if exists.
putCHT
  :: (Hashable k, Eq k)
  => k  -- ^ key
  -> v  -- ^ value
  -> ConcurrentHashTable k v -> IO () -- ^ modified hashable
putCHT key value cht = atomically $ do
  let storage    = chtStorage cht
  let tvSize     = chtSize cht
  let tvCapacity = chtCapacity cht
  size           <- readTVar tvSize
  capacity       <- readTVar tvCapacity

  when (size == capacity) $ do
    let newCapacity = capacity * 2
    newStorage      <- Vec.replicateM newCapacity (newTVar [])
    buckets         <- readTVar storage
    Vec.mapM_ (copyElements newCapacity newStorage) buckets
    writeTVar storage newStorage
    writeTVar tvCapacity newCapacity

  newCapacity       <- readTVar tvCapacity
  newStorage        <- readTVar storage
  let valuesAtIndex = Vec.unsafeIndex newStorage $ keyHashIndex key newCapacity
  list              <- readTVar valuesAtIndex
  let (appended, newValuesAtIndex) = insertPair key value list
  writeTVar valuesAtIndex newValuesAtIndex

  when appended $ do
    writeTVar tvSize (size + 1)

-- | Returns size of a hashtable.
sizeCHT
  :: ConcurrentHashTable k v -- ^ hashable
  -> IO Int
sizeCHT cht = readTVarIO $ chtSize cht
