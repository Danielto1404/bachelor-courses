{-# LANGUAGE InstanceSigs #-}

module ThirdBlock.Parser
    ( -- * Data types
      Parser(..)
    ) where

import Control.Applicative (Alternative, (<|>), empty)

-- | Data type representing parser.
-- Wrapped function that takes stream and returns
-- optional result of a parsing as pair of parsed token and remaining stream.
newtype Parser s a =
  Parser
    { runParser :: [s] -> Maybe (a, [s]) }


instance Functor (Parser s) where
  fmap
    :: (a -> b)
    -> Parser s a
    -> Parser s b
  fmap transform parser = Parser $ \stream -> do
      (value, restStream) <- runParser parser stream
      return (transform value, restStream)


instance Applicative (Parser s) where
  pure
    :: a
    -> Parser s a
  pure value = Parser $ \stream -> Just (value, stream)

  (<*>)
    :: Parser s (a -> b)
    -> Parser s a
    -> Parser s b
  (<*>) functionParser valueParser = Parser $ \stream -> do
    (transform, functionRestStream) <- runParser functionParser stream
    (value, valueRestStream)        <- runParser valueParser functionRestStream
    return (transform value, valueRestStream)


instance Monad (Parser s) where
  (>>=)
    :: Parser s a
    -> (a -> Parser s b)
    -> Parser s b
  (>>=) parser constructor = Parser $ \stream -> do
    (value, rest) <- runParser parser stream
    runParser (constructor value) rest


instance Alternative (Parser s) where
   empty
     :: Parser s a
   empty = Parser $ \_ -> Nothing

   (<|>)
     :: Parser s a
     -> Parser s a
     -> Parser s a
   (<|>) first second = Parser $ \stream ->
     runParser first stream <|> runParser second stream
