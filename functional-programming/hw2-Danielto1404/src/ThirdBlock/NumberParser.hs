module ThirdBlock.NumberParser
    ( -- * Parsers
      numberParser
    ) where

import ThirdBlock.BasicParsers (element, satisfy, ok)
import ThirdBlock.Parser (Parser(..))

import Control.Applicative (some, (<|>), liftA2)
import Data.Char (isDigit)
import Data.Functor (($>))

-- | Parse number.
-- Number can be parsed from a string that satisfies following regexp: (+-)?(\d)+
numberParser
  :: Parser Char Int
numberParser = toInt <$> liftA2 (++) sign digits where

-- | Parse digis chars.
digits
  :: Parser Char String
digits = some $ satisfy isDigit

-- | Parse '+', '-' signs or eps.
sign
  :: Parser Char String
sign = plus <|> minus <|> eps

-- | Parse '+' char.
plus
  :: Parser Char String
plus = pure <$> element '+'

-- | Parse '-' char.
minus
  :: Parser Char String
minus = pure <$> element '-'

-- | Converts string in (+-)?(\d)+ regexp to signed int.
toInt
  :: String
  -> Int
toInt ('+' : s) = read s
toInt s         = read s

-- | Parser that doesn't consume input and put empty string as parsed value.
eps
  :: Parser Char String
eps = ok $> ""
