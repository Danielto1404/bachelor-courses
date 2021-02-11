{-# LANGUAGE LambdaCase #-}

module ThirdBlock.BasicParsers
    ( -- * Parsers
       ok
     , eof
     , satisfy
     , element
     , stream
    ) where

import ThirdBlock.Parser (Parser(..))

-- | Parser that always succeeds.
ok
  :: Parser s ()
ok = pure ()

-- | Parser that checks wheater the input is finished
-- otherwise falls.
eof
  :: Parser s ()
eof = Parser $ \case
  [] -> Just ((), [])
  _  -> Nothing

-- | Parser that consumes element if it satisfies predicate.
-- otherwise falls.
satisfy
  :: (s -> Bool)
  -> Parser s s
satisfy predicate = Parser $ \case
  (x : xs) | predicate x -> Just (x, xs)
  _                      -> Nothing

-- | Parser that consumes given char otherwise falls.
element
  :: (Eq s)
  => s
  -> Parser s s
element c = satisfy (== c)

-- | Parser that consumes given String otherwise falls.
stream
  :: (Eq s)
  => [s]
  -> Parser s [s]
stream = traverse element
