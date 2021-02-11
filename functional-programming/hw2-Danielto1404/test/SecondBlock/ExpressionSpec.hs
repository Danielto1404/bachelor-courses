module SecondBlock.ExpressionSpec
    ( expressionEvalTestTree
    ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import SecondBlock.Expression

expressionEvalTestTree
  :: IO TestTree
expressionEvalTestTree = testSpec "Evaluating expression tests:" expressionEvalSpec

expressionEvalSpec
  :: Spec
expressionEvalSpec = do
    it "Value should return its value 4 === 4" $ do
      eval (Value 4) `shouldBe` Right 4
    it "Add test: 2 + 3" $ do
      eval (Add (Value 2) (Value 3)) `shouldBe` Right 5
    it "Sub test: 8 - 11" $ do
      eval (Sub (Value 8) (Value 11)) `shouldBe` Right (-3)
    it "Mul test: -2 * 5" $ do
      eval (Mul (Value (-2)) (Value 5)) `shouldBe` Right (-10)
    describe "Div" $ do
      it "should be correct: 6 / 3" $ do
        eval (Div (Value 6) (Value 3)) `shouldBe` Right 2
      it "division should be int 7 / 3" $ do
        eval (Div (Value 7) (Value 3)) `shouldBe` Right 2
      it "division by zero 239 / 0 should return DivisionByZero error" $ do
        eval (Div (Value 239) (Value 0)) `shouldBe` Left DivisionByZero
    describe "Pow" $ do
      it "should pow numbers: 5 ^ 3" $ do
        eval (Pow (Value 5) (Value 3)) `shouldBe` Right 125
      it "should return error: 3 ^ (-4)" $ do
        eval (Pow (Value 3) (Value (-4))) `shouldBe` Left NegativePower
      it "should return correct result: 0 ^ 0" $ do
        eval (Pow (Value 0) (Value 0)) `shouldBe` Right 1
    describe "Complex expressions" $ do
      it "should return error in case of negative power 10 ^ (10 - 13) * 5" $ do
        eval exp1 `shouldBe` Left NegativePower
      it "2 ^ 4 * (3 / (-1 + 1)) should return DivisionByZero error" $ do
        eval exp2 `shouldBe` Left DivisionByZero
      it "Evaluating correct complex expression" $ do
        eval exp3 `shouldBe` Right (-2)
    where
      exp3 = Add (Pow (Value 3) (Value 1)) $
              (Sub (Div (Value 7) (Value 4)) (Mul (Value 2) (Value 3)))
      exp2 = Mul (Pow (Value 2) (Value 4)) $
               (Div (Value 3) (Add (Value 1) (Value (-1))))
      exp1 = Pow (Value 10) (Mul (Sub (Value 10) (Value 13)) (Value 5))
