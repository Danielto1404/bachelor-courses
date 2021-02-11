module ThirdBlock.ListParser
    ( -- * Parsers
      listListParser
    ) where

import ThirdBlock.BasicParsers (element, satisfy, eof)
import ThirdBlock.Parser (Parser(..))
import ThirdBlock.NumberParser (numberParser)

import Control.Applicative (many, (<|>))
import Data.Functor (($>))
import Control.Monad (replicateM, guard)
import Data.Char (isSpace)

-- | Parser of list of list of numbers separated by comma ignoring spaces.
-- String format: (<number of elements in list>, <exactly n elements>)*
-- Negative list length isn't allowed.
listListParser
  :: Parser Char [[Int]]
listListParser = do
  x  <- list
  let rest = ws >> comma >> ws >> listListParser
  xs <- (rest <|> (ws >> eol))
  return $ x : xs

-- | Parser that returns list of numbers.
-- String format: <number of elements in list>, <exactly n elements>.
-- otherwise falls.
list
  :: Parser Char [Int]
list = do
  _ <- ws
  n <- numberParser
  guard (n >= 0)
  replicateM n (ws >> comma >> ws >> numberParser)

-- | Parser comma char.
comma
  :: Parser Char Char
comma = element ','

-- | Parse whitespace chars.
ws
  :: Parser Char String
ws = many $ satisfy isSpace

-- | Parser that checks wheater the input is finished and returns empty list
-- otherwise falls
eol
  :: Parser Char [a]
eol = eof $> []
