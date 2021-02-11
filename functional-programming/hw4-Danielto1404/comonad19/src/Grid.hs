{-# LANGUAGE InstanceSigs #-}

module Grid
  ( -- * Constructors
    Grid (..)

    -- * Functions
  , up
  , down
  , left
  , right
  , gridWrite
  , horizontal
  , vertical
  ) where

import ListZipper
import Control.Comonad (Comonad (..))

-- | 2D grid data type representation
newtype Grid a
  = Grid -- ^ Wrapping constructor for 'ListZipper' of 'ListZipper's
  -- | Outer 'ListZipper' is responsible for rows
  -- | Inner 'ListZipper' is responsible for collumns
  { unGrid :: ListZipper (ListZipper a)
  }

instance Functor Grid where
  fmap
    :: (a -> b)
    -> (Grid a)
    -> (Grid b)
  fmap f grid = Grid $ fmap (fmap f) $ unGrid grid

instance Comonad Grid where
  extract
    :: Grid a
    -> a
  extract (Grid g) = extract $ extract g

  duplicate
    :: Grid a
    -> Grid (Grid a)
  duplicate = Grid . fmap horizontal . vertical

-- | Move focused element of 'Grid' up.
up
  :: Grid a
  -> Grid a
up (Grid g) = Grid (listLeft g)

-- | Move focused element of 'Grid' down.
down
  :: Grid a
  -> Grid a
down (Grid g) = Grid (listRight g)

-- | Move focused element of 'Grid' to the left.
left
  :: Grid a
  -> Grid a
left (Grid g) = Grid (fmap listLeft g)

-- | Move focused element of 'Grid' to the right.
right
  :: Grid a
  -> Grid a
right (Grid g) = Grid (fmap listRight g)

-- | Write value to the focused element cell of 'Grid'.
gridWrite
  :: a      -- ^ new focused element value
  -> Grid a -- ^ old 'Grid'
  -> Grid a -- ^ updated 'Grid'
gridWrite x (Grid g) = Grid $ listWrite newLine g
 where
  oldLine = extract g
  newLine = listWrite x oldLine

-- | Make 'ListZipper' of 'Grid's with viewport moved to left and right.
horizontal
  :: Grid a
  -> ListZipper (Grid a)
horizontal = genericMove left right

-- | Make 'ListZipper' of 'Grid's with focused element moved up and down.
vertical
  :: Grid a
  -> ListZipper (Grid a)
vertical = genericMove up down
