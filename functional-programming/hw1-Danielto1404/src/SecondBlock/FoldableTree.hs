{-# LANGUAGE InstanceSigs #-}

module SecondBlock.FoldableTree
  ( -- * Data types
    Tree(..)

    -- * Functions
  , isEmpty
  , find
  , fromList
  , insert
  , remove
  , size
  ) where

import FirstBlock.Tree
