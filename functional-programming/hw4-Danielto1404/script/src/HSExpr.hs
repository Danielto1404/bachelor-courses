{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE GADTs             #-}
{-# LANGUAGE InstanceSigs      #-}
{-# LANGUAGE TypeFamilies      #-}

module HSExpr
  ( HSExpr(..)
  , HSVar(..)
  , VarWrapper
  , (@=)
  , (@>)
  , (@>=)
  , (@<)
  , (@<=)
  , (@==)
  , (#)
  ) where

import Data.Typeable (Typeable)

-- | Class to mark data types that are supported in HalyavaScript.
class (Ord a, Show a, Typeable a) => HSVar a where
  defaultVal :: a

instance HSVar Int where
  defaultVal = 0

instance HSVar Double where
  defaultVal = 0.0

instance HSVar String where
  defaultVal = ""

instance HSVar Bool where
  defaultVal = False


-- | Type family that enables using different variable wrappers in different
-- instances of HSExpr.
type family VarWrapper (f :: * -> *) :: * -> *

-- | Tagless final technique, applied to the HalyavaScript eDSL.
class HSExpr expr where

  hsAssign
    :: HSVar a
    => (VarWrapper expr) a
    -> a
    -> expr ()

  hsGt
    :: HSVar a
    => (VarWrapper expr) a
    -> a
    -> expr Bool

  hsGtE
    :: HSVar a
    => (VarWrapper expr) a
    -> a
    -> expr Bool

  hsLt
    :: HSVar a
    => (VarWrapper expr) a
    -> a
    -> expr Bool

  hsLtE
    :: HSVar a
    => (VarWrapper expr) a
    -> a
    -> expr Bool
  hsEq
    :: HSVar a
     => (VarWrapper expr) a
    -> a
    -> expr Bool

  hsSemicol
    :: expr a
    -> expr b
    -> expr b

  hsWithVar
    :: HSVar a
    => a
    -> ((VarWrapper expr) a -> expr b)
    -> expr b

  hsReadVar
    :: HSVar a
    => (VarWrapper expr) a -> (a -> expr b)
    -> expr b

  hsWhile
    :: expr Bool
    -> expr a
    -> expr ()

  hsIfElse
    :: expr Bool
    -> expr a
    -> expr a
    -> expr a

  hsFun
    :: (HSVar a, HSVar b)
    => b
    -> ((VarWrapper expr) a -> (VarWrapper expr) b -> expr d)
    -> a
    -> expr b

infixr 2 @=
infixr 2 @>
infixr 2 @>=
infixr 2 @<
infixr 2 @<=
infixr 2 @==

infixr 0 #

-- | Operator synonym for assignment.
(@=)
  :: (HSExpr expr, HSVar a)
  => (VarWrapper expr) a
  -> a
  -> expr ()
(@=) = hsAssign

-- | Operator synonym for "greater than".
(@>)
  :: (HSExpr expr, HSVar a)
  => (VarWrapper expr) a
  -> a
  -> expr Bool
(@>) = hsGt

-- | Operator synonym for "greater than or equal to".
(@>=)
  :: (HSExpr expr, HSVar a)
  => (VarWrapper expr) a
  -> a
  -> expr Bool
(@>=) = hsGtE

-- | Operator synonym for "less than".
(@<)
  :: (HSExpr expr, HSVar a)
  => (VarWrapper expr) a
  -> a
  -> expr Bool
(@<) = hsLt

-- | Operator synonym for "less than or equal to".
(@<=)
  :: (HSExpr expr, HSVar a)
  => (VarWrapper expr) a
  -> a
  -> expr Bool
(@<=) = hsLtE

-- | Operator synonym for "equal to".
(@==)
  :: (HSExpr expr, HSVar a)
  => (VarWrapper expr) a
  -> a
  -> expr Bool
(@==) = hsEq

-- | Operator synonym for a semicolon-like statement join.
(#) :: HSExpr expr => expr a -> expr b -> expr b
(#) = hsSemicol
