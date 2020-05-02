module Parsing.ProofParser where

import Parsing.Lexer
import Parsing.Grammar
import Logic.Expression

parseExpression :: String -> Expression
parseExpression = parse . alexScanTokens


parseHypothesesLine :: String -> (String, String)
parseHypothesesLine s = (hypotheses, provable) where

    findTourniquetIndex []              = error "Unable to find tourniquet in empty string"
    findTourniquetIndex ('|' : '-' : _) = 0
    findTourniquetIndex (x : xs)        = 1 + findTourniquetIndex xs

    n          = findTourniquetIndex s
    hypotheses = take n s
    provable   = drop (n + 2) s


commaSplit :: String -> [String]
commaSplit s = reverse $ splitter s [] [] where
    splitter :: String -> String -> [String] -> [String]
    splitter (',' : xs) part acc = splitter xs [] (reverse part : acc)
    splitter (x : xs)   part acc = splitter xs (x:part) acc
    splitter []         part acc = reverse part : acc


parseList :: [String] -> [Expression]
parseList [] = []
parseList xs = map parseExpression xs

getHypotheses :: String -> [Expression]
getHypotheses = parseList . filter (not . null) . commaSplit