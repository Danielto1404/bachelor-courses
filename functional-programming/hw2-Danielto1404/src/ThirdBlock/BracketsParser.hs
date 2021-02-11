module ThirdBlock.BracketsParser
    ( -- * Parsers
      bracketsParser
    ) where

import ThirdBlock.BasicParsers (eof, element)
import ThirdBlock.Parser (Parser(..))
import Control.Applicative (many)

-- | Parse correct bracket sequence.
-- Correct bracket sequence is a string that satisfies following grammar:
--   S -> (S)
--   S -> S S
--   S -> eps
bracketsParser
  :: Parser Char ()
bracketsParser = many innerBrackets >> eof where
  innerBrackets
    :: Parser Char Char
  innerBrackets = openedBracket >> (many innerBrackets) >> closedBracket

-- | Parser that consumes opened bracket '('
-- otherwise falls
openedBracket
  :: Parser Char Char
openedBracket = element '('

-- | Parser that consumes closed bracket ')'
-- otherwise falls
closedBracket
  :: Parser Char Char
closedBracket = element ')'
