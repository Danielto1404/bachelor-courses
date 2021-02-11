{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE GADTs             #-}
{-# LANGUAGE InstanceSigs      #-}
{-# LANGUAGE TypeFamilies      #-}

module Interpreter
  ( interpret
  ) where

import Control.Monad       (when)
import Control.Monad.State (State, evalState, get, modify, state)
import Data.Typeable       (Typeable, cast)
import Data.Map            (Map)
import qualified Data.Map as Map

import HSExpr

-- | Typed storage box for HalyavaScript variables.
data VarBox where
  Box :: Typeable a => a -> VarBox

-- | Map of variable reference ids to variable boxes.
type Vars = Map Int VarBox

-- | HalyavaScript execution state.
type ExecState = State Vars

-- | Variable reference type.
newtype VarIndex a = VarIndex Int

-- | Type family instance for 'ExecState's variable wrapper.
type instance VarWrapper ExecState = VarIndex

-- | Main interpreter function.
interpret
  :: ExecState a
  -> a
interpret programmState = evalState programmState Map.empty

-- | Function that creates a new variable reference and assigns it an id.
newVarRef
  :: Typeable a
  => a
  -> ExecState (VarIndex a)
newVarRef value = state addVar where
  addVar
    :: Vars
    -> (VarIndex a, Vars)
  addVar vm = (VarIndex i, Map.insert i (Box value) vm)
    where i = Map.size vm

-- | Function that reads the variable by its reference.
readVarRef
  :: Typeable a
  => VarIndex a
  -> ExecState a
readVarRef (VarIndex refId) = do
  vars <- get
  case Map.lookup refId vars of
    Just (Box x) -> case cast x of
      Nothing    -> error "Casting failed"
      Just value -> return value
    Nothing -> error "Nonexistent reference"

-- | Function that writes the variable by its reference.
writeVarRef
  :: Typeable a
  => VarIndex a
  -> a
  -> ExecState ()
writeVarRef (VarIndex refId) value = modify $ Map.insert refId (Box value)

-- | Helper function for binary operator interpretation.
boolOp
  :: HSVar a
  => (a -> a -> Bool)
  -> VarIndex a
  -> a
  -> ExecState Bool
boolOp op varRef value = do
  readed <- readVarRef varRef
  return (op readed value)

instance HSExpr ExecState where
  hsAssign
    :: HSVar a
    => VarIndex a
    -> a
    -> ExecState ()
  hsAssign = writeVarRef

  hsGt
    :: HSVar a
    => VarIndex a
    -> a
    -> ExecState Bool
  hsGt = boolOp (>)

  hsGtE
    :: HSVar a
    => VarIndex a
    -> a
    -> ExecState Bool
  hsGtE = boolOp (>=)

  hsLt
    :: HSVar a
    => VarIndex a
    -> a
    -> ExecState Bool
  hsLt = boolOp (<)

  hsLtE
    :: HSVar a
    => VarIndex a
    -> a
    -> ExecState Bool
  hsLtE = boolOp (<=)

  hsEq
    :: HSVar a
    => VarIndex a
    -> a
    -> ExecState Bool
  hsEq = boolOp (==)

  hsSemicol
    :: ExecState a
    -> ExecState b
    -> ExecState b
  hsSemicol = (>>)

  hsWithVar
    :: HSVar a
    => a
    -> (VarIndex a -> ExecState b)
    -> ExecState b
  hsWithVar val f = newVarRef val >>= f

  hsReadVar
    :: HSVar a
    => VarIndex a
    -> (a -> ExecState b)
    -> ExecState b
  hsReadVar ref = (readVarRef ref >>=)

  hsWhile
    :: ExecState Bool
    -> ExecState a
    -> ExecState ()
  hsWhile cond body = do
    isExec <- cond
    when (isExec) (body >> hsWhile cond body)

  hsIfElse
    :: ExecState Bool
    -> ExecState a
    -> ExecState a
    -> ExecState a
  hsIfElse cond ifBody elseBody = do
    isExec <- cond
    if (isExec)
    then ifBody
    else elseBody

  hsFun
    :: (HSVar a, HSVar b)
    => b
    -> (VarIndex a -> VarIndex b -> ExecState c)
    -> a
    -> ExecState b
  hsFun outputValue expr inputValue = do
    input  <- newVarRef inputValue
    output <- newVarRef outputValue
    expr input output >> readVarRef output
