module ThirdBlock.NameEndoSpec
    ( nameTestTree
    ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import ThirdBlock.NameEndo (Name (..), Endo (..))

nameTestTree
  :: IO TestTree
nameTestTree = testSpec "Name monoid and semigroup" nameSpec

nameSpec
  :: Spec
nameSpec = do
  describe "Name monoid and semigroup check" $ do
    it "server . root" $
      (Name "server") <> (Name "root") `shouldBe` Name "server.root"
    it "Empty <> Name \"something\"" $
      Empty <> Name "something" `shouldBe` Name "something"
    it "Name \"something\" <> Empty" $
      (Name "something") <> Empty `shouldBe` Name "something"
    it "Name \"\" <> Empty" $
      (Name "") <> Empty `shouldBe` Name ""
    it "Name \"root\" <> Name \"\"" $
      (Name "root") <> (Name "") `shouldBe` Name "root."

  describe "Endo monoid and semigroup check" $ do
    it "pow2 . plus1" $
      (getEndo $ pow2 <> plus1) 10 `shouldBe` 121
    it "mconcat [pow2, plus1, mul10]" $
      (getEndo $ mconcat [pow2, plus1, mul10]) 2 `shouldBe` 441
    it "mempty <> pow2" $
      (getEndo $ mempty <> pow2) 3 `shouldBe` 9
    it "mul10 <> mempty" $
      (getEndo $ mul10 <> mempty) 23.9 `shouldBe` 239
    it "mconcat [mempty, mempty, mempty]" $
      (getEndo $ mconcat [mempty, mempty, mempty]) 239 `shouldBe` 239
  where
    pow2 = Endo (^ 2)
    plus1 = Endo (+ 1)
    mul10 = Endo (* 10)
    divide3 = Endo (/ 3)
