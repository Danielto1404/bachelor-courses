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

import Data.Bits ((.|.), (.&.), xor, shiftL)
import Data.Char (ord)

maskFor :: String -> Int
maskFor (c : []) = 1 `shiftL` (ord c - 97)
maskFor _        = 67108864                 -- 2^26 non ALPHABET char


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
