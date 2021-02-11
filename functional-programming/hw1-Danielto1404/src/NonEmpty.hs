{-# LANGUAGE InstanceSigs #-}

module ThirdBlock.NonEmpty
  ( -- * Constructors
    NonEmpty (..)
  ) where

-- | Type 'NonEmpty' represents non empty list (have at least on element)
data NonEmpty a
  = a :| [a] -- ^ Basic conctructor for NonEmpty type
  deriving (Show)

-- | 'NonEmpty' type conforms to 'Semigroup' type class
instance (Semigroup a) => (Semigroup NonEmpty a) where
  (<>)
    :: NonEmpty a
    -> NonEmpty a
    -> NonEmpty a
  (<>) (x :| xs) (y :| ys) = x :| (xs ++ (y : ys))
