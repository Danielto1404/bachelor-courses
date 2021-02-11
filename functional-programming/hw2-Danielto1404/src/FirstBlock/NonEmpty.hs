{-# LANGUAGE InstanceSigs #-}

module FirstBlock.NonEmpty
  ( -- * Data types
    NonEmpty(..)
  ) where

import Control.Applicative(liftA2)

-- | Structure representing non-empty list.
-- Has one constructor (:|) that takes first element on the left and
-- list of remaining elements on the right.
data NonEmpty a = a :| [a]
  deriving (Show, Eq)

instance Functor NonEmpty where
  fmap
    :: (a -> b)
    -> NonEmpty a
    -> NonEmpty b
  fmap f (x :| xs) = (f x) :| (map f xs)


instance Applicative NonEmpty where
  pure
    :: a
    -> NonEmpty a
  pure = (:| [])

  (<*>)
    :: NonEmpty (a -> b)
    -> NonEmpty a
    -> NonEmpty b
  (<*>) (f :| fs) (x :| xs) = (f x) :| rest where
    rest = (map f xs) ++ (fs <*> x:xs)


instance Foldable NonEmpty where
  foldMap
    :: Monoid m
    => (a -> m)
    -> NonEmpty a
    -> m
  foldMap f (x :| xs) = f x <> foldMap f xs


instance Traversable NonEmpty where
  traverse
    :: Applicative f
    => (a -> f b)
    -> NonEmpty a
    -> f (NonEmpty b)
  traverse g (x :| xs) = liftA2 (:|) (g x) (traverse g xs)


instance Monad NonEmpty where
  (>>=)
    :: NonEmpty a
    -> (a -> NonEmpty b)
    -> NonEmpty b
  (>>=) (x :| xs) f = y :| (ys ++ (xs >>= toList . f))
    where
      (y :| ys) = f x
      toList
        :: NonEmpty a
        -> [a]
      toList (v :| vs) = v : vs
