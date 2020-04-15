module Parsing.ExpressionParser where

import Parsing.Lexer
import Parsing.Grammar
import Logic.Expression

parseExpression :: String -> Expression
parseExpression = parse . alexScanTokens