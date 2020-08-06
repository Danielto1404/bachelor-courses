module Parsing.ProofParser  where

import Parsing.Lexer
import Parsing.Grammar
import Logic.Expression (Expression(..))

parseExpression :: String -> Expression
parseExpression = parse . alexScanTokens