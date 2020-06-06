module Logic.Expression where

data Operation  = Impl
                | Or
                | And
                | Equal
                | Add
                | Mul deriving (Eq, Ord)


data Quantifier = Any
                | Exists deriving (Eq, Ord)


data UnaryOp    = Next

                | Not deriving (Eq, Ord)

data Expression = Binary Operation Expression Expression
                | Unary UnaryOp Expression
                | Quan Quantifier String Expression
                | Zero
                | Predicate String
                | Var String deriving (Eq, Ord)


instance Show Operation where
    show Impl  = "->"
    show Or    = "|"
    show And   = "&"
    show Equal = "="
    show Add   = "+"
    show Mul   = "*"


instance Show Expression where
    show (Binary operation left right)     = concat ["(",
                                                     show left,
                                                     show operation,
                                                     show right,
                                                     ")"]

    show (Unary Not expression)            = concat ["(", "!", show expression,  ")"]
    show (Unary Next expression)           = concat [show expression, "\'"]

    show (Quan Exists variable expression) = concat ["(?", variable, ".", show expression, ")"]
    show (Quan Any variable expression)    = concat ["(@", variable, ".", show expression, ")"]

    show Zero                              = "0"

    show (Predicate predicate)             = predicate

    show (Var variable)                    = variable
