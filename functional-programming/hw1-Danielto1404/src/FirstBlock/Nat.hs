{-# LANGUAGE InstanceSigs #-}

module FirstBlock.Nat
  ( -- * Data types
    Nat(..)

    -- * Functions
    , add
    , mul
    , sub
    , fromNat
    , toNat
    , divide
    , isEven
    , modulo
  ) where

-- | Type 'Nat' represents a non-negative integer in Peano's arithmetic.
data Nat
  = Z     -- | Constructor of 'Nat' representing zero value
  | S Nat -- | Constructor of 'Nat' representing the value next to the given one
  deriving (Show)

-- | 'Nat' type conforms to 'Eq' class type
instance Eq Nat where
  (==)
    :: Nat
    -> Nat
    -> Bool
  (==) Z       Z   = True
  (==) (S x) (S y) = x == y
  (==) _       _   = False

-- | 'Nat' type conforms to 'Ord' class type
instance Ord Nat where
  (<=)
    :: Nat
    -> Nat
    -> Bool
  (<=) Z     _     = True
  (<=) _     Z     = False
  (<=) (S x) (S y) = x <= y

-- | 'Nat' type conforms to 'Enum' class type
instance Enum Nat where
  toEnum
    :: Int
    -> Nat
  toEnum 0 = Z
  toEnum n
    | n < 0     = error "Natural numbers can't be negative"
    | otherwise = S $ toEnum $ n - 1

  fromEnum
    :: Nat
    -> Int
  fromEnum Z     = 0
  fromEnum (S x) = (1 +) $ fromEnum x

-- | Function 'add' takes two numbers in Peano arithmetics ('Nat') and returns
-- sum of given numbers in Peano arithmetics
add
  :: Nat
  -> Nat
  -> Nat
add n Z     = n
add n (S x) = S $ add n x

-- | Function 'mul' takes two numbers in Peano arithmetics ('Nat') and returns
-- product of given numbers in Peano arithmetics
mul
  :: Nat
  -> Nat
  -> Nat
mul Z _     = Z
mul _ Z     = Z
mul n (S x) = add n $ mul n x

-- | Function 'sub' takes two numbers in Peano arithmetics ('Nat')' and returns
-- substraction result in Peano arithmetics
sub
 :: Nat
 -> Nat
 -> Nat
sub n Z         = n
sub Z _         = Z
sub (S x) (S y) = sub x y

-- | Function 'toNat' returns converted int number
-- to object in Peano arithmetics ('Nat')
toNat
  :: Int
  -> Nat
toNat = toEnum

-- | Function 'fromNat' returs converted number given
-- in Peano arithmetics ('Nat') to 'Int' value
fromNat
  :: Nat
  -> Int
fromNat = fromEnum

-- | Function 'isEven' takes 'Nat' object and returns boolean value indicating
-- wheater number isEven
isEven
  :: Nat
  -> Bool
isEven Z         = True
isEven (S Z)     = False
isEven (S (S a)) = isEven a

-- | Function 'divide' takes two 'Nat' objects and returns division result
divide
  :: Nat
  -> Nat
  -> Nat
divide _ Z = error "division by zero"
divide a b
  | a < b     = Z
  | otherwise = S $ divide (sub a b) b


-- | Function 'modulo' takes 'Nat' objects and returns module result
modulo
  :: Nat
  -> Nat
  -> Nat
modulo a b = sub a $ mul (divide a b) b
