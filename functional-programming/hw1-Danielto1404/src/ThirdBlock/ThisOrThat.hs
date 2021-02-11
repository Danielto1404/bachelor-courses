{-# LANGUAGE InstanceSigs #-}

module ThirdBlock.ThisOrThat
    ( -- * Constructors
      ThisOrThat (..)
    ) where

-- | Type 'ThisOrThat' represents disjunction data structure
data ThisOrThat a b
  = This a    -- ^ Constructor of 'ThisOrThat' representing 'This' value
  | That b    -- ^ Constructor of 'ThisOrThat' representing 'That' value
  | Both a b  -- ^ Constructor of 'ThisOrThat' representing 'Both' values
  deriving (Show, Eq)

-- | 'ThisOrThat' conforms to 'Semigroup' class type
instance (Semigroup a, Semigroup b) => Semigroup (ThisOrThat a b) where
  (<>)
    :: ThisOrThat a b
    -> ThisOrThat a b
    -> ThisOrThat a b
  (<>) (This x)   (This y)     = This (x <> y)
  (<>) (This x)   (That y)     = Both x y
  (<>) (This x)   (Both x' y)  = Both (x <> x') y
  (<>) (That x)   (That y)     = That (x <> y)
  (<>) (That y)   (This x)     = Both x y
  (<>) (That y)   (Both x y')  = Both x (y <> y')
  (<>) (Both x y) (Both x' y') = Both (x <> x') (y <> y')
  (<>) (Both x y) (This x')    = Both (x <> x') y
  (<>) (Both x y) (That y')    = Both x (y <> y')
