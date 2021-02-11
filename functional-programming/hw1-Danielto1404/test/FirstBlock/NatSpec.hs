module FirstBlock.NatSpec
 ( natTestTree
 ) where

import Control.Exception (evaluate)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec
  (Spec, anyErrorCall, describe, it, shouldBe, shouldNotBe, shouldThrow,
  testSpec)

import FirstBlock.Nat (Nat(..), add, mul, sub, fromNat, toNat, divide, modulo, isEven)

natTestTree
  :: IO TestTree
natTestTree = testSpec "Nat" natSpec

natSpec
  :: Spec
natSpec = do
  describe "(==)" $ do
    it "0 == 0" $
      zero `shouldBe` zero
    it "1 != 0" $
      one `shouldNotBe` zero
    it "1 != 2" $
      one `shouldNotBe` two
    it "0 != 3" $
      zero `shouldNotBe` three

  describe "add" $ do
    it "1 + 0" $
      add one zero `shouldBe` one
    it "2 + 1" $
      add two one `shouldBe` three
    it "1 + 2" $
      add one two `shouldBe` three
    it "0 + 3" $
      add zero three `shouldBe` three

  describe "mul" $ do
    it "2 * 0" $
      mul two zero `shouldBe` zero
    it "0 * 3" $
      mul zero three `shouldBe` zero
    it "3 * 2" $
      mul three two `shouldBe` six
    it "2 * 2" $
      mul two two `shouldBe` four

  describe "sub" $ do
    it "4 - 1" $
      sub four one `shouldBe` three
    it "1 - 6" $
      sub one six `shouldBe` zero
    it "5 - 2" $
      sub five two `shouldBe` three

  describe "integer convertion" $ do
    it "fromInteger 4" $
      toNat 4 `shouldBe` four
    it "toInteger 4" $
      fromNat four `shouldBe` 4
    it "fromInteger 0" $
      toNat 0 `shouldBe` zero
    it "toInteger 0" $
      fromNat zero `shouldBe` 0
    it "fromInteger negative" $
      evaluate (toNat (-1)) `shouldThrow` anyErrorCall

  describe "compare" $ do
    it "2 < 3" $
      two < three `shouldBe` True
    it "1 <= 1" $
       one <= one `shouldBe` True
    it "4 > 3" $
      four > three `shouldBe` True
    it "5 <= 1" $
      five <= one `shouldBe` False
    it "3 == 3" $
      three == three `shouldBe` True
    it "4 >= 6" $
      four >= six `shouldBe` False

  describe "division" $ do
    it "1 / 0 should throw error" $
      evaluate (divide one zero) `shouldThrow` anyErrorCall
    it " 2 / 3 === 0" $
      divide two three `shouldBe` zero
    it "5 / 3 === 1" $
      divide five three `shouldBe` one
    it "6 / 2 === 3" $
      divide six two `shouldBe` three

  describe "modulo" $ do
    it "1 mod 2" $
      modulo one two `shouldBe` one
    it "4 mod 2" $
      modulo four two `shouldBe` zero
    it "5 mod 3" $
      modulo five three `shouldBe` two

  describe "isEven" $ do
    it "isEven 0" $
      isEven zero `shouldBe` True
    it "isEven 1" $
      isEven one `shouldBe` False
    it "isEven 2" $
      isEven two `shouldBe` True
    it "isEven 5" $
      isEven five `shouldBe` False

  where
    zero  = Z
    one   = S zero
    two   = S one
    three = S two
    four  = S three
    five  = S four
    six   = S five
