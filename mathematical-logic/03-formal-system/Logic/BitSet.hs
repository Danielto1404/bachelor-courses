module Logic.BitSet (setFrom, (@|), (@&), (@?), isEmpty, emptyBitSet, indicatorSet) where

import Data.Bits
import Data.Char


firstCharToBit :: String -> Int
firstCharToBit []        = 0
firstCharToBit (v : _) = 1 `shiftL` (ord v - 97)

setFrom :: String -> Int
setFrom = firstCharToBit


infixl 9 @|  -- add first symbol of string to bit mask
(@|) :: String -> Int -> Int
var @| mask = (firstCharToBit var) .|. mask


infixl 9 @& -- mask intersection
(@&) :: Int -> Int -> Int
(@&) first second = first .&. second


infixl 9 @? -- is member of set
(@?) :: String -> Int -> Bool
var @? mask = ((firstCharToBit var) @& mask) /= 0


isEmpty :: Int -> Bool
isEmpty = (== 0)


emptyBitSet :: Int
emptyBitSet = 0


indicatorSet :: Int
indicatorSet = 2 ^ 26
