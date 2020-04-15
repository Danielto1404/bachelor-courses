module Logic.Expression where

data Operation = And
               | Impl
               | Or

data Expression = Binary Operation Expression Expression
                | Not Expression
                | Var String

instance Show Operation where
    show And  = "&"
    show Or   = "|"
    show _    = "->"

instance Show Expression where
    show (Binary operation left right) = concat ["(",
                                                show operation,
                                                ",",
                                                show left,
                                                ",",
                                                show right,
                                                ")"]

    show (Not expression)              = concat ["(!", show expression, ")"]
    show (Var variable)                = variable
