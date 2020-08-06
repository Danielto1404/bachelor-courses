module Logic.MinimizeHypotheses where

import qualified Data.Set as Set
import Numeric (showIntAtBase)
import Data.Char (intToDigit)
import Data.List (sortBy)

import Logic.Expression (Expression(..), apply)

type VarsValues = [(String, Bool)]
type Table  = [(VarsValues, Bool)]


getValue :: String -> VarsValues -> Bool
getValue x []                        = False
getValue x ((v, b) : vs) | x == v    = b
                         | otherwise = getValue x vs


evaluate :: Expression -> VarsValues -> Bool
evaluate (Binary op left right) values = (apply op) (evaluate left values) (evaluate right values)
evaluate (Not e)                values = not (evaluate e values)
evaluate (Var x)                values = getValue x values


extractVariables :: Expression -> Set.Set String
extractVariables (Binary op left right) = let leftVars = extractVariables left
                                          in case Set.size leftVars of
                                                 3 -> leftVars
                                                 _ -> Set.union leftVars (extractVariables right)
extractVariables (Not e)                = extractVariables e
extractVariables (Var x)                = Set.insert x Set.empty


makeBinary :: Int -> Int -> String
makeBinary n number = putLeadingZeros rest binary where
                      binary = showIntAtBase 2 intToDigit number ""
                      rest   = max 0 $ n - length binary

                      putLeadingZeros :: Int -> String -> String
                      putLeadingZeros 0 binary = binary
                      putLeadingZeros n binary = '0' : putLeadingZeros (n - 1) binary


buildVarsValues :: [String] -> [VarsValues]
buildVarsValues vars = [ zip vars $ fromStringToBool $ makeBinary n number | number <- [0..2^n-1] ] where
                           n = length vars
                           fromStringToBool :: String -> [Bool]
                           fromStringToBool []           = []
                           fromStringToBool ('0' : rest) = False : fromStringToBool rest
                           fromStringToBool ('1' : rest) = True  : fromStringToBool rest


buildTruthTable :: Expression -> Table
buildTruthTable e = let vars   = Set.toList (extractVariables e)
                        values = buildVarsValues vars
                    in zip values $ map (evaluate e) values



data HypothesesState = Negation [Expression] | Forward [Expression]


checkAllOnes :: [String] -> Table -> Bool
checkAllOnes vars table = all (\(_, b) -> b) $
                          filter saveOnes table where
                              saveOnes :: (VarsValues, Bool) -> Bool
                              saveOnes (values, _) = all (\x -> getValue x values) vars


subsets :: [String] -> [[String]]
subsets []     = [[]]
subsets (x:xs) = subsets xs ++ map (x:) (subsets xs)


invert :: Table -> Table
invert []                 = []
invert ((values, b) : xs) = (invertValues values, not b) : invert xs where
                            invertValues :: VarsValues -> VarsValues
                            invertValues []            = []
                            invertValues ((v, b) : xs) = (v, not b) : invertValues xs


findHypotheses :: Expression -> Maybe HypothesesState
findHypotheses e = let table = buildTruthTable e
                       vars  = map (\(v, _) -> v) (fst $ head table)
                       sets  = sortBy (\a b -> compare (length a) (length b)) (subsets vars)

                       find :: Table -> [[String]] -> ([Expression] -> HypothesesState) -> Maybe HypothesesState
                       find _      []          _                                    = Nothing
                       find table (set : sets) constructor | checkAllOnes set table = Just $ constructor $ map Var set
                                                           | otherwise              = find table sets constructor

                   in case find table sets Forward of
                          hypotheses@(Just _) -> hypotheses
                          Nothing             -> find (invert table) sets Negation

