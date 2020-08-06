{-# OPTIONS_GHC -O2 -optc-O2 #-}
{-# OPTIONS_GHC -fwarn-incomplete-patterns #-}

module Main where

import Parsing.ProofParser
import Logic.MinimizeHypotheses
import Parsing.ProofParser (parseExpression)

main :: IO ()
main = do

--    contents                       <- getContents
    contents                       <- readFile "input.txt"

    let (provable : [])             = lines contents


---------------------------------- DEBUG AREA -----------------------------

    let expression                  = parseExpression provable

--    putStrLn $ concatMap (\x -> show x ++ "\n") (buildTruthTable expression)

    case findHypotheses expression of
        Nothing  -> putStrLn ":("
        (Just x) -> case x of
                        Forward  exps -> putStrLn (concatMap (\x -> show x ++ "\n") exps)
                        Negation exps -> putStrLn (concatMap (\x -> show x ++ "\n") exps)

--    putStrLn $ show $ evaluate expression [("A", True), ("B", True), ("C", True)]



-----------------------------------------------------------------------------