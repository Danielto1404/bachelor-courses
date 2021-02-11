{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE GADTs             #-}
{-# LANGUAGE InstanceSigs      #-}
{-# LANGUAGE TypeFamilies      #-}

module Transpiler
  ( transpile
  ) where

import Control.Monad.State (State, evalState, gets, modify, state)

import HSExpr

-- | Record type that stores information about known script variables.
data VarInfo =
  VarInfo
  { viCount      :: Int
  , viStack      :: [String]
  , viBlockDepth :: Int
  }

-- | Initialize an empty variable information record.
emptyVarInfo
  :: VarInfo
emptyVarInfo = VarInfo 0 [] 0

-- | Mark a deeper block of code for wider indentation.
increaseDepth
  :: VarInfo
  -> VarInfo
increaseDepth vi = vi { viBlockDepth = viBlockDepth vi + 1 }

-- | Mark a less deep block of code for smaller indentation.
decreaseDepth
  :: VarInfo
  -> VarInfo
decreaseDepth vi = vi { viBlockDepth = viBlockDepth vi - 1 }

-- | Generate an indentation string for current code depth.
indent
  :: VarInfo
  -> String
indent vi = replicate (4 * viBlockDepth vi) ' '

-- | Mark creation of d new variables.
incVarCounter
  :: Int
  -> VarInfo
  -> VarInfo
incVarCounter d vi = vi { viCount = (viCount vi + d) }

-- | Push a variable reference to variable name stack.
pushVarRef
  :: VarRef a
  -> VarInfo
  -> VarInfo
pushVarRef (VarRef i) vi = vi { viStack = (("v" ++ show i) : viStack vi) }

-- | Pop a variable name, if in stack, or show the specified value.
popOrUse
  :: Show a
  => a
  -> VarInfo
  -> (String, VarInfo)
popOrUse fallback vi = case oldStack of
  [] -> (show fallback, vi)
  _  -> (head oldStack, vi { viStack = tail oldStack })
  where oldStack = viStack vi

-- | Wrapper type for the transpilation state.
data VarCountState a = VS (State VarInfo String)
data VarRef a = VarRef Int
type instance VarWrapper VarCountState = VarRef

-- | Main transpiler function.
transpile
  :: VarCountState a
  -> String
transpile (VS st) = evalState st emptyVarInfo

-- | Helper function for binary operator transpilation.
boolOp
  :: HSVar a
  => String
  -> VarRef a
  -> a
  -> VarCountState b
boolOp sign (VarRef i) val = VS $ do
  n <- state (popOrUse val)
  return $ concat ["v", show i, " ", sign, " ", n]

instance HSExpr VarCountState where
  hsAssign
    :: HSVar a
    => VarRef a
    -> a
    -> VarCountState ()
  hsAssign (VarRef i) val = VS $ do
    n         <- state (popOrUse val)
    indentStr <- gets indent
    return $ concat [indentStr, "v", show i, " = ", n, ";"]

  hsGt
    :: HSVar a
    => VarRef a
    -> a
    -> VarCountState Bool
  hsGt = boolOp ">"

  hsGtE
    :: HSVar a
    => VarRef a
    -> a
    -> VarCountState Bool
  hsGtE = boolOp ">="

  hsLt
    :: HSVar a
    => VarRef a
    -> a
    -> VarCountState Bool
  hsLt = boolOp "<"

  hsLtE
    :: HSVar a
    => VarRef a
    -> a
    -> VarCountState Bool
  hsLtE = boolOp "<="

  hsEq
    :: HSVar a
    => VarRef a
    -> a
    -> VarCountState Bool
  hsEq = boolOp "=="

  hsSemicol
    :: VarCountState a
    -> VarCountState b
    -> VarCountState b
  hsSemicol (VS a) (VS b) = VS $ do
    aRepr <- a
    bRepr <- b
    return $ concat [aRepr, "\n", bRepr]

  hsWithVar
    :: HSVar a
    => a
    -> (VarRef a -> VarCountState b)
    -> VarCountState b
  hsWithVar val f = VS $ do
    newI <- gets viCount
    modify (incVarCounter 1)
    indentStr <- gets indent
    let (VS b) = f (VarRef newI)
    bRepr <- b
    return
      $ concat [indentStr, "var v", show newI, " = ", show val, ";\n", bRepr]

  hsReadVar
    :: HSVar a
    => VarRef a
    -> (a -> VarCountState b)
    -> VarCountState b
  hsReadVar ref f = VS $ do
    modify (pushVarRef ref)
    let (VS b) = f defaultVal
    b

  hsWhile
    :: VarCountState Bool
    -> VarCountState a
    -> VarCountState ()
  hsWhile (VS cond) (VS body) = VS $ do
    indentStr <- gets indent
    condRepr  <- cond
    modify increaseDepth
    bodyRepr <- body
    modify decreaseDepth
    return
      $ concat
          [ indentStr
          , "while ("
          , condRepr
          , ") {\n"
          , bodyRepr
          , "\n"
          , indentStr
          , "}"
          ]

  hsIfElse
    :: VarCountState Bool
    -> VarCountState a
    -> VarCountState a
    -> VarCountState a
  hsIfElse (VS cond) (VS a) (VS b) = VS $ do
    indentStr <- gets indent
    reprCond  <- cond
    modify increaseDepth
    reprA <- a
    reprB <- b
    modify decreaseDepth
    return $ concat
      [ indentStr
      , "if ("
      , reprCond
      , ") {\n"
      , reprA
      , "\n"
      , indentStr
      , "} else {\n"
      , reprB
      , "\n"
      , indentStr
      , "}"
      ]

  hsFun
    :: (HSVar a, HSVar b)
    => b
    -> (VarRef a -> VarRef b -> VarCountState d)
    -> a
    -> VarCountState b
  hsFun output expr input = VS $ do
    indentStr <- gets indent
    iIn       <- gets viCount
    let iOut = iIn + 1
    modify (incVarCounter 2)
    let (VS b) = expr (VarRef iIn) (VarRef iOut)
    modify increaseDepth
    bRepr <- b
    modify decreaseDepth
    return $ concat
      [ indentStr
      , "(function(v"
      , show iIn
      , ") {\n"
      , indentStr
      , "    var v"
      , show iOut
      , " = "
      , show output
      , ";\n"
      , bRepr
      , "\n    return v"
      , show iOut
      , ";\n})("
      , show input
      , ");"
      ]
