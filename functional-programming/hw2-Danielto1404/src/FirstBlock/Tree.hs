{-# LANGUAGE InstanceSigs #-}

module FirstBlock.Tree
    ( -- * Data types
      Tree(..)
    ) where

import Control.Applicative(liftA2)

-- | Structure representing a binary tree.
-- Constructors:
--   * Leaf has a value and no children
--   * Branch has only two children
data Tree a
  = Branch (Tree a) (Tree a)
  | Leaf a
  deriving (Show, Eq)


instance Functor Tree where
  fmap
    :: (a -> b)
    -> Tree a
    -> Tree b
  fmap f (Leaf value)     = Leaf $ f value
  fmap f (Branch lhs rhs) = Branch (fmap f lhs) (fmap f rhs)


instance Applicative Tree where
  pure
    :: a
    -> Tree a
  pure = Leaf

  (<*>)
    :: Tree (a -> b)
    -> Tree a
    -> Tree b
  (<*>) (Leaf f)         tree = f <$> tree
  (<*>) (Branch lfs rfs) tree = Branch (lfs <*> tree) (rfs <*> tree)


instance Foldable Tree where
    foldMap
      :: Monoid m
      => (a -> m)
      -> Tree a
      -> m
    foldMap f (Leaf value)     = f value
    foldMap f (Branch lhs rhs) = foldMap f lhs <> foldMap f rhs


instance Traversable Tree where
  traverse
    :: Applicative f
    => (a -> f b)
    -> Tree a
    -> f (Tree b)
  traverse g (Leaf value)     = Leaf <$> g value
  traverse g (Branch lhs rhs) = liftA2 Branch (traverse g lhs) (traverse g rhs)
