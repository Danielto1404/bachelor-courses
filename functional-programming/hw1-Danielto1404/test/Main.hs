module Main (main) where

import Test.Tasty (defaultMain, testGroup)

import FirstBlock.WeekdaySpec (weekdayTestTree)
import FirstBlock.NatSpec (natTestTree)
import FirstBlock.TreeSpec (treeTestTree)

import SecondBlock.SplitSpec (splitTestTree)
import SecondBlock.SplitProp (splitPropTestTree)
import SecondBlock.FoldableTreeProp (treeFoldableTestTree)

import ThirdBlock.MaybeEitherConcatSpec (maybeEitherConcatTestTree)
import ThirdBlock.NameEndoSpec (nameTestTree)
import ThirdBlock.ThisOrThatSpec (thisOrThatTestTree)

main
  :: IO ()
main = do
  weekdayTest           <- weekdayTestTree
  natTest               <- natTestTree
  treeTest              <- treeTestTree

  splitSpecTest         <- splitTestTree
  splitPropTest         <- splitPropTestTree
  foldableTreeTest      <- treeFoldableTestTree

  maybeEitherConcatTest <- maybeEitherConcatTestTree
  nameEndoTest          <- nameTestTree
  thisOrThatTest        <- thisOrThatTestTree

  defaultMain $
    testGroup "All" [ testGroup "First block" [weekdayTest, natTest, treeTest],
                      testGroup "Second block" [splitSpecTest, splitPropTest, foldableTreeTest],
                      testGroup "Third block" [maybeEitherConcatTest, nameEndoTest, thisOrThatTest]
                    ]
