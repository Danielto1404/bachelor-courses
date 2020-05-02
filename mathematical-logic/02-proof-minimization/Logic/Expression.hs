module Logic.Expression where

data Operation = And
               | Impl
               | Or deriving (Eq, Ord)

data Expression = Binary Operation Expression Expression
                | Not Expression
                | Var String deriving (Eq, Ord)

instance Show Operation where
    show And  = "&"
    show Or   = "|"
    show _    = "->"

instance Show Expression where
    show (Binary operation left right) = concat ["(",
                                                show left,
                                                " ",
                                                show operation,
                                                " ",
                                                show right,
                                                ")"]

    show (Not (Var variable))          = concat ["!", variable]
    show (Not expression)              = concat ["(!", show expression, ")"]
    show (Var variable)                = variable
