module Main (main) where

import Test.Tasty (defaultMain, testGroup)
--
import FirstBlock.StringSumSpec (stringSumTestTree)
import SecondBlock.ExpressionSpec (expressionEvalTestTree)
import ThirdBlock.ParserSpec (parserTestTree)

main
  :: IO ()
main = do
  stringSumTest      <- stringSumTestTree
  expressionEvalTest <- expressionEvalTestTree
  parserTest         <- parserTestTree

  defaultMain $
    testGroup "HW02" [ testGroup "First block"  [stringSumTest],
                       testGroup "Second block" [expressionEvalTest],
                       testGroup "Third block"  [parserTest]
                     ]
