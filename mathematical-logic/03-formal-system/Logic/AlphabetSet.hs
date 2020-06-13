module Logic.AlphabetSet (
                    Logic.AlphabetSet.AlphabetSet (..),
                    Logic.AlphabetSet.lookup,
                    Logic.AlphabetSet.add,
                    Logic.AlphabetSet.intersection,
                    (<?>),
                    (<|>),
                    (<&>),
                    (<@>),
                    Logic.AlphabetSet.isEmpty,
                    Logic.AlphabetSet.fromValue) where
-- Simple english alphabet bit set implementation (using bit masks for char's)

import Data.Bits ((.|.), (.&.), xor)

maskFor :: String -> Int
maskFor "a" = 1
maskFor "b" = 2
maskFor "c" = 4
maskFor "d" = 8
maskFor "e" = 16
maskFor "f" = 32
maskFor "g" = 64
maskFor "h" = 128
maskFor "i" = 256
maskFor "j" = 512
maskFor "k" = 1024
maskFor "l" = 2048
maskFor "m" = 4096
maskFor "n" = 8192
maskFor "o" = 16384
maskFor "p" = 32768
maskFor "q" = 65536
maskFor "r" = 131072
maskFor "s" = 262144
maskFor "t" = 524288
maskFor "u" = 1048576
maskFor "v" = 2097152
maskFor "w" = 4194304
maskFor "x" = 8388608
maskFor "y" = 16777216
maskFor "z" = 33554432
maskFor _   = 67108864


data AlphabetSet = Singleton | Set Int | Empty deriving (Show)


-- UNION ZONE
union :: AlphabetSet -> AlphabetSet -> AlphabetSet

union Singleton Singleton = Singleton
union Singleton Empty     = Singleton
union Empty Singleton     = Singleton

union Singleton set@(Set _) = set
union set@(Set _) Singleton = set
union Empty set@(Set _)     = set
union set@(Set _) Empty     = set
union (Set a) (Set b)       = Set (a .|. b)

union Empty Empty         = Empty


infixl 9 <|>
(<|>) :: AlphabetSet -> AlphabetSet -> AlphabetSet
(<|>) = union


-- INTERSECTION ZONE
intersection :: AlphabetSet -> AlphabetSet -> AlphabetSet

intersection _         Empty     = Empty
intersection Empty     _         = Empty

intersection Singleton Singleton = Singleton

intersection Singleton _         = Empty
intersection _         Singleton = Empty

intersection (Set a) (Set b)     = Set (a .&. b)


infixl 9 <&>
(<&>) :: AlphabetSet -> AlphabetSet -> AlphabetSet
(<&>) = intersection


-- LOOKUP ZONE
lookup :: String -> AlphabetSet -> Bool
lookup _ Singleton = False
lookup _ Empty     = False
lookup x set       = not $ isEmpty (Logic.AlphabetSet.intersection (fromValue x) set)


infixl 9 <?>
(<?>) :: String -> AlphabetSet -> Bool
(<?>) = Logic.AlphabetSet.lookup


fromValue :: String -> AlphabetSet
fromValue x = if checkValidity x then Set (maskFor x) else Empty


isEmpty :: AlphabetSet -> Bool
isEmpty Empty   = True
isEmpty (Set s) = s == 0
isEmpty _       = False


add :: String -> AlphabetSet -> AlphabetSet
add x set = case set of
                s@(Set _) -> s <|> (fromValue x)
                _         -> fromValue x


infixl 9 <@>
(<@>) :: String -> AlphabetSet -> AlphabetSet
(<@>) = add


-- HELPERS ZONE
checkValidity :: String -> Bool
checkValidity (x : []) = x >= 'a' && x <= 'z'
checkValidity _        = False
