{-# LANGUAGE InstanceSigs #-}

module ListZipper
  ( -- * Constructors
    ListZipper (..)
    -- * Functions
  , listWrite
  , listLeft
  , listRight
  , iterateTail
  , genericMove
  , toList
  ) where

import Control.Comonad (Comonad (..))

-- | Data type represents zipper via 'List'
data ListZipper a = LZ [a] a [a]

instance Functor ListZipper where
  fmap
    :: (a -> b)
    -> (ListZipper a)
    -> (ListZipper b)
  fmap f (LZ ls x rs) = LZ (fmap f ls) (f x) (fmap f rs)

instance Comonad ListZipper where
  extract
    :: ListZipper a
    -> a
  extract (LZ _ x _) = x

  duplicate
    :: ListZipper a
    -> ListZipper (ListZipper a)
  duplicate z = LZ (iterateTail listLeft z) z (iterateTail listRight z)

-- | Write to the 'ListZipper' focused cell.
listWrite
  :: a            -- ^ new focused element value
  -> ListZipper a -- ^ old 'ListZipper'
  -> ListZipper a -- ^ updated 'ListZipper'
listWrite x (LZ ls _ rs) = LZ ls x rs

-- | Shift 'ListZipper' focused element to the left.
listLeft
  :: ListZipper a -- ^ current zipper
  -> ListZipper a -- ^ shifted zipper
listLeft (LZ (a : as) x bs) = LZ as a (x : bs)
listLeft _                  = error "ListZipper left side is empty"

-- | Shift 'ListZipper' focused element to the right.
listRight
  :: ListZipper a -- ^ current zipper
  -> ListZipper a -- ^ shifted zipper
listRight (LZ as x (b : bs)) = LZ (x : as) b bs
listRight _                  = error "ListZipper right side is empty"

-- | Like 'iterate', but without the initial value.
iterateTail
  :: (a -> a) -- ^ function to produce elements
  -> a        -- ^ initial element
  -> [a]      -- ^ infinite list of produced elements
iterateTail f = tail . iterate f

-- | Given 2 functions to produce elements,
-- generate 'ListZipper' from the focused element.
genericMove
  :: (a -> a)     -- ^ function to produce left elements of zipper
  -> (a -> a)     -- ^ function to produce right elements of zipper
  -> a            -- ^ initial value
  -> ListZipper a -- ^ created zipper
genericMove f g e = LZ (iterateTail f e) e (iterateTail g e)

-- | Convert 'ListZipper' to a list with the specified threshold.
toList
  :: Int          -- ^ number of elements to be taken
  -> ListZipper a -- ^ 'ListZipper' object from which elements will be taken
  -> [a]          -- ^ 'List' of taken elements
toList n (LZ ls x rs) = reverse (take n ls) ++ [x] ++ take n rs
