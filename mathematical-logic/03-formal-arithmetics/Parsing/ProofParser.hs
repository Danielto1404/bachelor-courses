module Parsing.ProofParser where

import Parsing.Lexer
import Parsing.Grammar
import Logic.Expression


parseExpression :: String -> Expression
parseExpression = parse . alexScanTokens


getProvable :: String -> Expression
getProvable s = findTourniquet s where
    findTourniquet []                     = error "Unable to find tourniquet in empty string"
    findTourniquet ('|' : '-' : provable) = parseExpression provable
    findTourniquet (x : xs)               = findTourniquet xs


parseList :: [String] -> [Expression]
parseList [] = []
parseList xs = map parseExpression xs
