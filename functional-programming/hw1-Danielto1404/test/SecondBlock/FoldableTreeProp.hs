module SecondBlock.FoldableTreeProp
 ( treeFoldableTestTree
 ) where

import Data.Foldable (toList)
import Data.List (sort)

import Hedgehog
import Hedgehog.Gen as Gen
import Hedgehog.Range as Range

import Test.Tasty (TestTree, testGroup)
import Test.Tasty.Hedgehog (testProperty)

import SecondBlock.FoldableTree (Tree(..), fromList)

treeFoldableTestTree
  :: IO TestTree
treeFoldableTestTree = return $
  testGroup "Tree property tests: toList . fromList === sort"
  [  testProperty "with Int values tree"    propIntFold
    , testProperty "with String values tree" propStringFold
  ]

genIntList
  :: Gen [Int]
genIntList = Gen.list (Range.linear 0 1000) Gen.enumBounded

genStrings
 :: Gen String
genStrings = Gen.list (Range.linear 0 1000) (Gen.element ['a'..'z'])


testWithList
  :: (Ord a, Show a)
  => Gen [a]
  -> Property
testWithList tests = property $ do
  list <- forAll tests
  (toList $ fromList list) === (sort list)

propIntFold
  :: Property
propIntFold = testWithList genIntList

propStringFold
  :: Property
propStringFold = testWithList genStrings
