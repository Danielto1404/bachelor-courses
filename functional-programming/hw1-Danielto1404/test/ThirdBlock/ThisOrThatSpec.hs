module ThirdBlock.ThisOrThatSpec
    ( thisOrThatTestTree
    ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import ThirdBlock.ThisOrThat (ThisOrThat (..))

thisOrThatTestTree
  :: IO TestTree
thisOrThatTestTree = testSpec "ThisOrThat semigroup block" thisOrThatSpec

thisOrThatSpec
  :: Spec
thisOrThatSpec = do
  describe "Simple tests" $ do
    it "this a <> this b" $
      this "a" <> this "b" `shouldBe` this "ab"
    it "that b <> that a" $
      that "b" <> that "a" `shouldBe` that "ba"
    it "that a <> this b" $
      that "a" <> this "b" `shouldBe` both "b" "a"
    it "both a b <> this c" $
      both "a" "b" <> this "c" `shouldBe` both "ac" "b"
    it "both a b <> that c" $
      both "a" "b" <> that "c" `shouldBe` both "a" "bc"

  where
    this s = This s :: ThisOrThat String String
    that s = That s :: ThisOrThat String String
    both a b = Both a b :: ThisOrThat String String
