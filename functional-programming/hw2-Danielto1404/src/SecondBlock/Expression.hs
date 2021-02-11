{-# LANGUAGE LambdaCase #-}

module SecondBlock.Expression
    ( -- Data types
      Expression(..)
      , ArithmeticError(..)

      -- * Functions
      , eval
    ) where

-- Typealias for convinience usage of expression result type.
type ExpressionResult = Either ArithmeticError Int

-- | Structure representing arithmetic expressions.
-- Constructors:
--   * Value represents single value
--   * Add is addition of two expressions
--   * Sub is subtraction of two expressions
--   * Mul is multiplication of two expressions
--   * Div is division of two expressions
--   * Pow is power of two expressions
data Expression
  = Value Int
  | Add Expression Expression
  | Sub Expression Expression
  | Mul Expression Expression
  | Div Expression Expression
  | Pow Expression Expression
  deriving (Show, Eq)

  -- | Structure representing error that can occur while evaluating expressions.
  -- Constructors:
  --   * DivisionByZero occurs when expression is divided by zero
  --   * NegativePow occurs when expression is powered to negative number.
data ArithmeticError
  = DivisionByZero
  | NegativePower
  deriving (Show, Eq)

-- | Evaluate expression and return either result of evaluation or error.
eval
  :: Expression
  -> ExpressionResult
eval (Value x)     = Right x
eval (Add lhs rhs) = safeEval (wrap (+)) lhs rhs
eval (Sub lhs rhs) = safeEval (wrap (-)) lhs rhs
eval (Mul lhs rhs) = safeEval (wrap (*)) lhs rhs
eval (Div lhs rhs) = safeEval safeDiv lhs rhs where
  safeDiv x y | y == 0    = Left DivisionByZero
              | otherwise = Right $ x `div` y
eval (Pow lhs rhs) = safeEval safePow lhs rhs where
  safePow x y | y < 0     = Left NegativePower
              | otherwise = Right $ x ^ y

-- Wraps given binary function to function that returns result of operation
-- packed into Right constructor.
wrap
  :: (Int -> Int -> Int)
  -> (Int -> Int -> ExpressionResult)
wrap op = \x y -> Right $ x `op` y

-- Returns operation applied to given lhs and rhs or error.
safeEval
  :: (Int -> Int -> ExpressionResult)
  -> Expression
  -> Expression
  -> ExpressionResult
safeEval op lhs rhs = do
  x <- eval lhs
  y <- eval rhs
  x `op` y
