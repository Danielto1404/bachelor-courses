module Main
  ( main
  ) where

import Control.Concurrent.Async (mapConcurrently_)
import Control.DeepSeq          (NFData (..), deepseq)
import Control.Monad            (replicateM, replicateM_)
import Criterion.Main
import Data.Hashable            (Hashable)
import System.Random            (getStdRandom, random, randomR)

import Hashtable

-- | Generate random sample (Char, Int) pair.
rndPair :: IO (Char, Int)
rndPair = do
  key   <- getStdRandom (randomR ('a', 'z'))
  value <- getStdRandom random
  return (key, value)

-- | Generate sample hashtable with random initial contents.
sampleCHT
  :: IO (ConcurrentHashTable Char Int)
sampleCHT = do
  cht     <- newCHT
  pairCnt <- getStdRandom (randomR (1 :: Int, 100))
  replicateM_ pairCnt rndPair
  return cht

-- | Type alias for a test operation to perform.
type Operation = ConcurrentHashTable Char Int -> IO ()

-- | Generate a sample "get" operation.
genGetOp
  :: IO Operation
genGetOp = do
  key   <- getStdRandom (randomR ('a', 'z'))
  return $ getFn key
 where
  getFn
    :: (Hashable k, Eq k, NFData v)
    => k
    -> ConcurrentHashTable k v
    -> IO ()
  getFn k cht = getCHT k cht >>= \v -> v `deepseq` return ()

-- | Generate a sample "size" operation.
genSizeOp
  :: IO Operation
genSizeOp = return sizeFn
 where
  sizeFn
    :: ConcurrentHashTable k v
    -> IO ()
  sizeFn cht = sizeCHT cht >>= \v -> v `deepseq` return ()

-- | Generate a sample "put" operation.
genPutOp
  :: IO Operation
genPutOp = do
  (key, val) <- rndPair
  return $ putCHT key val

-- | Generate a sample random hashtable operation; probability: 80% put, 10% get, 10% size.
genRandomOp
  :: IO Operation
genRandomOp = do
  opType <- getStdRandom (randomR (1 :: Int, 10))
  case opType of
    1 -> genGetOp
    2 -> genSizeOp
    _ -> genPutOp

-- | Test set of 10^5 sample "put" operations.
putOps
  :: IO [Operation]
putOps = replicateM (10 ^ (5 :: Int)) genPutOp

-- | Test set of 10^5 sample "get" operations.
getOps
  :: IO [Operation]
getOps = replicateM (10 ^ (5 :: Int)) genGetOp

-- | Test set of 10^5 sample "size" operations.
sizeOps
  :: IO [Operation]
sizeOps = replicateM (10 ^ (5 :: Int)) genSizeOp

-- | Test set of 10^5 sample random operations.
allOps
  :: IO [Operation]
allOps = replicateM (10 ^ (5 :: Int)) genRandomOp

-- | Given a sample list of test operations, perform them concurrently to test the hashtable.
operations
  :: IO (ConcurrentHashTable Char Int)
  -> [ConcurrentHashTable Char Int -> IO ()]
  -> IO (ConcurrentHashTable Char Int)
operations chtF ops = do
  cht <- chtF
  mapConcurrently_ (\f -> f cht) ops
  return cht

-- | Execute "put", "get", "size" and all operation benchmarks.
main :: IO ()
main = defaultMain
  [ env putOps $ \ops -> bench "Put operations" $ whnfIO $ operations newCHT ops
  , env getOps
    $ \ops -> bench "Get operations" $ whnfIO $ operations sampleCHT ops
  , env sizeOps
    $ \ops -> bench "Size operations" $ whnfIO $ operations sampleCHT ops
  , env allOps $ \ops -> bench "All operations" $ whnfIO $ operations newCHT ops
  ]
