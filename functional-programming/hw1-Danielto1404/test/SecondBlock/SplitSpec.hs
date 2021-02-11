module SecondBlock.SplitSpec
 ( splitTestTree
 ) where

import Data.List.NonEmpty (NonEmpty(..), (<|), fromList)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, shouldNotBe, testSpec)

import SecondBlock.Split (splitOn, joinWith)


splitTestTree
  :: IO TestTree
splitTestTree = testSpec "Split" splitSpec

splitSpec
  :: Spec
splitSpec = do
  describe "splitOn" $ do
    it "path/to/file" $
      splitOn '/' "path/to/file" `shouldBe` fromList ["path", "to", "file"]
    it "<empty>" $
      splitOn '/' "" `shouldBe` fromList [""]
    it "split with repeated split element" $
      splitOn 'y' "xyyyyz" `shouldBe` fromList ["x", "", "" , "", "z"]

  describe "joinWith" $ do
    it "[\"path\", \"to\", \"file\"]" $
      joinWith '/' (fromList ["path", "to", "file"]) `shouldBe` "path/to/file"
    it "empty case" $
      joinWith '!' (fromList [""]) `shouldBe` ""
    it "join with repeated empty elements" $
      joinWith 'y' (fromList ["x", "", "", "z"]) `shouldBe` "xyyyz"

  describe "joinWith . splitOn element" $ do
    it "[\"path\", \"to\", \"file\"]" $
      (joinWith '/' $ splitOn '/' "path/to/file") `shouldBe` "path/to/file"
    it "empty case" $
      (joinWith 'x' $ splitOn 'y' "z") `shouldBe` "z"
    it "repeated element" $
      ((joinWith '!') $ splitOn '!' "He!!0 how !!! u?") `shouldBe` "He!!0 how !!! u?"
