module Main where

import Parsing.ExpressionParser

main :: IO ()
main = interact $ show . parseExpression
--    expression <- getLine
--    putStrLn . show . parseExpression $ expression