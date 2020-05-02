module Logic.ProofMinimizator where

import Logic.Expression
import Logic.AxiomChecker
import Logic.ProofExpression

import Data.List
import qualified Data.Map as Map
import qualified Data.Set as Set

-- for key contains right part of implication
-- value contains list of left part of this implication and position in global list
type MP_Map    = Map.Map Expression [(Expression, Int)]
type Index_Map = Map.Map Expression Int
type Int_Set   = Set.Set Int


-- add right part of implication to map as a key, and left part of (implication, pos) as a value
add_to_MP_Map :: Expression -> Int -> MP_Map -> MP_Map
add_to_MP_Map (Binary Impl left right) pos m = Map.insertWith (\[newLeft] lefts -> newLeft : lefts) right [(left, pos)] m
add_to_MP_Map _                        _   m = m


-- add expression to map as a key, ans position of this expression in initial evidence as a value
add_to_Index_Map :: Expression -> Int -> Index_Map -> Index_Map
add_to_Index_Map = Map.insert


-- get Modus Ponens or Incorrect if Modus Ponens doesn't exist
getModusPonens :: Expression -> MP_Map -> Index_Map -> ProofState
getModusPonens e modusPonensMap indexMap = case Map.lookup e modusPonensMap of
    Nothing    -> Incorrect
    (Just mps) -> extractModusPonens mps where
        extractModusPonens :: [(Expression, Int)] -> ProofState
        extractModusPonens []              = Incorrect
        extractModusPonens ((e, pos) : es) = case Map.lookup e indexMap of
                                               Nothing         -> extractModusPonens es
                                               (Just olderPos) -> MP pos olderPos


-- For every proof line create a ProofState object associated with proof line evidence
buildProof :: [Expression] -> [Expression] -> [ProofState]
buildProof proofLines hypotheses = proofBuilder proofLines 1 Map.empty Map.empty where

    -- marks proof lines as one of proof state
    proofBuilder :: [Expression] -> Int -> MP_Map -> Index_Map -> [ProofState]
    proofBuilder []       _   _              _        = []
    proofBuilder (e : es) pos modusPonensMap indexMap
        | axiomIndex /= 0                             = (Axiom axiomIndex)                         : nextProofs
        | hypIndex /= 0                               = (Hyp hypIndex)                             : nextProofs
        | otherwise                                   = (getModusPonens e modusPonensMap indexMap) : nextProofs
        where
            modusPonensMap' = add_to_MP_Map    e pos modusPonensMap
            indexMap'       = add_to_Index_Map e pos indexMap
            nextProofs      = proofBuilder es (pos + 1) modusPonensMap' indexMap'

            axiomIndex      = getAxiomIndex e
            hypIndex        = case elemIndex e hypotheses of
                                Nothing      -> 0
                                (Just index) -> index + 1


-- deletes unnecessary proof lines (saves lines used only in MP,
-- and lines which are the final state of MP (Hypotheses or Axiom))
simplify :: [ProofState] -> Int_Set
simplify states = Set.insert n (dfs (reverse states) n (Set.fromList [n])) where
   n = length states - 1

-- walks recursively through MP lines
   dfs :: [ProofState] -> Int -> Int_Set -> Int_Set
   dfs ((MP i j) : ps) index used | Set.member index used = mark [i', j'] $ nextProofs $ mark [i', j'] used
                                  | otherwise             = nextProofs used
                                 where
                                    -- walks recursively through next MP lines
                                    nextProofs :: Int_Set -> Int_Set
                                    nextProofs = dfs ps (index - 1)

                                    i' = i - 1
                                    j' = j - 1

                                    -- add MP indexes to dfs recursive tree
                                    mark :: [Int]-> Int_Set -> Int_Set
                                    mark (x:xs) used = Set.insert x $ mark xs (Set.insert x used)
                                    mark []     used = used

   dfs [] _   _                                           = Set.empty
   dfs (_ : ps) index used                                = dfs ps (index - 1) used


-- removes elements in list by index
removeByIndex :: [Expression] -> Int_Set -> [Expression]
removeByIndex proof indexes = iterator proof indexes 0 where
    iterator :: [Expression] -> Int_Set -> Int -> [Expression]
    iterator []       _       _                          = []
    iterator (p : ps) used index | Set.member index used = p : nextProofs
                                 | otherwise             = nextProofs
                                 where nextProofs = iterator ps used (index + 1)


-- remove all duplicates, and saves proof lines only before the first provable
removeDuplicates :: [Expression] -> Expression -> [Expression]
removeDuplicates proof provable = reverse $ saveDistinct [] proof Set.empty where
          saveDistinct :: [Expression] -> [Expression] -> Set.Set Expression -> [Expression]
          saveDistinct _     []     _                        = []
          saveDistinct saved (x:xs) used | x == provable     = provable : saved
                                         | Set.member x used = saveDistinct saved xs used
                                         | otherwise         = saveDistinct (x : saved) xs (Set.insert x used)
