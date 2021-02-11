module SecondBlock.SplitProp
 ( splitPropTestTree
 ) where

import Hedgehog
import Hedgehog.Gen as Gen
import Hedgehog.Range as Range

import Test.Tasty (TestTree)
import Test.Tasty.Hedgehog (testProperty)

import SecondBlock.Split (splitOn, joinWith)

splitPropTestTree
  :: IO TestTree
splitPropTestTree = return $
  testProperty "joinWith . splitOn" propSplit

propSplit
  :: Property
propSplit =
  property $ do
    string <- forAll $ genString 0 1000
    delim <- forAll Gen.alpha
    joinWith delim (splitOn delim string) === string

genString
  :: Int
  -> Int
  -> Gen String
genString minLength maxLength =
  let listLength = Range.linear minLength maxLength
  in Gen.list listLength Gen.alpha
