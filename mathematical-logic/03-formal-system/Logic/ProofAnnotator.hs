module Logic.ProofAnnotator where

import Logic.Expression
import Logic.AxiomChecker
import Logic.ProofExpression
import Logic.BitSet

import Data.List
import Data.Bits
import qualified Data.Map.Strict as Map

-- for key contains right part of implication
-- value contains list of left part of this implication and position in global list
type MP_Map    = Map.Map Expression [(Expression, Int)]
type Index_Map = Map.Map Expression Int


isJust :: Maybe a -> Bool
isJust (Just _) = True
isJust _        = False


-- add right part of implication to map as a key, and left part of (implication, pos) as a value
add2ModusPonensMap :: Expression -> Int -> MP_Map -> MP_Map
add2ModusPonensMap (Binary Impl left right) pos m =
    Map.insertWith (\[leftPart] leftParts -> leftPart : leftParts) right [(left, pos)] m
add2ModusPonensMap _                 _   m        = m


-- add expression to map as a key, ans position of this expression in initial evidence as a value
add2IndexMap :: Expression -> Int -> Index_Map -> Index_Map
add2IndexMap = Map.insert


getModusPonens :: Expression -> MP_Map -> Index_Map -> Maybe ProofState
getModusPonens e modusPonensMap indexMap = case Map.lookup e modusPonensMap of
    Nothing    -> Nothing
    (Just mps) -> let result = extractModusPonens mps (0, 0)
                      extractModusPonens :: [(Expression, Int)] -> (Int, Int) -> Maybe ProofState
                      extractModusPonens [] (0, 0)               = Nothing
                      extractModusPonens [] (i, j)               = Just $ MP i j
                      extractModusPonens ((e, i) : es) mpIndexes =
                          case Map.lookup e indexMap of
                              Nothing  -> extractModusPonens es mpIndexes
                              (Just j) -> extractModusPonens es $! (if (j, i) > mpIndexes then (j, i) else mpIndexes)

                  in result


data SubstitutionState = Found Expression Int | Correct | IncorrectSub deriving (Eq, Show)


unpackValues :: SubstitutionState -> SubstitutionState -> SubstitutionState
unpackValues Correct         Correct                     = Correct
unpackValues sub@(Found _ _) Correct                     = sub
unpackValues Correct         sub@(Found _ _)             = sub
unpackValues (Found e set)   (Found e' set') | e == e'   = Found e (set .|. set')
                                             | otherwise = IncorrectSub
unpackValues _               _                           = IncorrectSub


try2Substitute :: String -> Expression -> Expression -> SubstitutionState
try2Substitute x pattern expr = findSub x pattern expr where

    findSub :: String -> Expression -> Expression -> SubstitutionState
    findSub x (Binary op l r) (Binary op' l' r')
                                           | op == op' = let leftSub = (findSub x l l')
                                                         in case leftSub of
                                                              IncorrectSub -> IncorrectSub
                                                              Correct      -> (findSub x r r')
                                                              found        -> unpackValues found (findSub x r r')
                                           | otherwise = IncorrectSub

    findSub x (Var v) therm                | x == v    = Found therm indicatorSet
                                           | otherwise = case therm of
                                                            (Var v') | v == v'   -> Correct
                                                                     | otherwise -> IncorrectSub
                                                            _                    -> IncorrectSub

    findSub x (Unary op e) (Unary op' e')  | op == op' = findSub x e e'
                                           | otherwise = IncorrectSub

    findSub x (Predicate p) (Predicate p') | p == p'   = Correct
                                           | otherwise = IncorrectSub

    findSub x Zero Zero                                = Correct

    findSub x (Quan q v e) (Quan q' v' e') | q == q' && v == v' =
                                               let result = findSub x e e'
                                               in case result of
                                                    Correct                                 -> Correct
                                                    IncorrectSub                            -> IncorrectSub
                                                    found@(Found therm usedQuantifiersMask) ->
                                                        if (x /= v)
                                                            then case isEmpty usedQuantifiersMask of
                                                                     True  -> found
                                                                     False -> Found therm (v @| usedQuantifiersMask)
                                                            else case therm of
                                                                     (Var z) | z == x    -> Correct
                                                                             | otherwise -> IncorrectSub
                                                                     _                   -> IncorrectSub
                                           | otherwise                                    = IncorrectSub

    findSub _ _ _                          = IncorrectSub


data CheckSubstitutionState = T | F | NotFreeError String !Expression | OccursFreeError String | IntroRule Int


toBool :: CheckSubstitutionState -> Bool
toBool F = False
toBool _ = True


-- 11, 12 Axioms
getForallAxiom :: Expression -> CheckSubstitutionState
getForallAxiom (Binary Impl (Quan Any x psi) psi') = validateSubstitution x psi psi'
getForallAxiom _                                   = F


getExistsAxiom :: Expression -> CheckSubstitutionState
getExistsAxiom (Binary Impl psi' (Quan Exists x psi)) = validateSubstitution x psi psi'
getExistsAxiom _                                      = F


validateSubstitution :: String -> Expression -> Expression -> CheckSubstitutionState
validateSubstitution x pattern expr = case try2Substitute x pattern expr of
                                    IncorrectSub                    -> F
                                    Correct                         -> T
                                    Found therm usedQuantifiersMask ->
                                        case therm of
                                            var@(Var v) | x == v    -> T
                                                        | otherwise -> checkFree x usedQuantifiersMask var
                                            e                       -> checkFree x usedQuantifiersMask e


checkFree :: String -> Int -> Expression -> CheckSubstitutionState
checkFree x usedQuantifiersMask found =
    if isEmpty $ usedQuantifiersMask @& (getFreeVars found)
        then T
        else NotFreeError x found


getFreeVars :: Expression -> Int
getFreeVars expr = extractFreeVars expr emptyBitSet where
    extractFreeVars :: Expression -> Int -> Int
    extractFreeVars (Binary _ l r) used      = (extractFreeVars l used) .|. (extractFreeVars r used)
    extractFreeVars (Quan _ v e)   used      = extractFreeVars e (v @| used)
    extractFreeVars (Unary _ e)    used      = extractFreeVars e used
    extractFreeVars (Predicate _)  _         = emptyBitSet
    extractFreeVars Zero           _         = emptyBitSet
    extractFreeVars (Var v) used | v @? used = emptyBitSet
                                 | otherwise = setFrom v


-- Intro rules zone
getForallRule :: Expression -> Index_Map -> CheckSubstitutionState
getForallRule (Binary Impl psi (Quan Any x phi)) indexMap =
    let expr = (Binary Impl psi phi) in
        extractRule x psi expr indexMap
getForallRule _ _ = F


getExistsRule :: Expression -> Index_Map -> CheckSubstitutionState
getExistsRule (Binary Impl (Quan Exists x phi) psi) indexMap =
    let expr = (Binary Impl phi psi) in
        extractRule x psi expr indexMap
getExistsRule _ _ = F


extractRule :: String -> Expression -> Expression -> Index_Map -> CheckSubstitutionState
extractRule x psi expr indexMap = case Map.lookup expr indexMap of
                                           Nothing  -> F
                                           (Just n) -> if (x @? getFreeVars psi)
                                                           then OccursFreeError x
                                                           else IntroRule n


-- INDUCTION AXIOM
isInductionAxiom :: Expression -> Bool
isInductionAxiom (Binary Impl
                    (Binary And
                        phiWithZero
                        (Quan Any x
                            (Binary Impl
                                phi phiWithX')))
                    phi') =
                    let
                        nextState = try2Substitute x phi phiWithX'
                        zeroState = try2Substitute x phi phiWithZero
                    in case (nextState, zeroState) of
                        (Found (Unary Next (Var v)) _, Found Zero _) -> v == x && phi == phi'
                        _                                            -> False

isInductionAxiom _ = False


-- For every proof line create a ProofState object associated with proof line evidence
annotate :: [Expression] -> [ProofState]
annotate proofLines = proofBuilder proofLines 1 0 Map.empty Map.empty where

    -- marks proof lines as one of proof state
    proofBuilder :: [Expression] -> Int -> Int -> MP_Map -> Index_Map -> [ProofState]
    proofBuilder []            _   _    _              _        = []
    proofBuilder exps@(e : es) pos from modusPonensMap indexMap

        | from == 0 && schemeAxiomIndex /= 0          = (Axiom schemeAxiomIndex) : nextProofs

        | from <= 1 && toBool forallAxiom             = case forallAxiom of
                                                          T                  -> (Axiom 11) : nextProofs
                                                          (NotFreeError v e) ->
                                                            case proofBuilder exps pos 2 modusPonensMap indexMap of
                                                                err@[OccursFree _] -> err
                                                                [NotProved]        -> [NotFree v e]
                                                                [NotFree _ _]      -> [NotFree v e]
                                                                answer             -> answer


        | from <= 2 && toBool existsAxiom             = case existsAxiom of
                                                          T                  -> (Axiom 12) : nextProofs
                                                          (NotFreeError v e) ->
                                                            case proofBuilder exps pos 3 modusPonensMap indexMap of
                                                                err@[OccursFree _] -> err
                                                                [NotProved]        -> [NotFree v e]
                                                                answer             -> answer

        | from <= 3 && isInductionAxiom e             = InductionAxiom : nextProofs

        | from <= 4 && formalAxiomIndex /= 0          = (FormalAxiom formalAxiomIndex) : nextProofs

        | from <= 5 && isJust maybeMP                 = let (Just mp) = maybeMP in mp : nextProofs

        | from <= 6 && toBool existsRule              = case existsRule of
                                                          (IntroRule n)       -> (Intro n) : nextProofs
                                                          (OccursFreeError v) ->
                                                            case proofBuilder exps pos 7 modusPonensMap indexMap of
                                                                [NotProved]    -> [OccursFree v]
                                                                [OccursFree x] -> [OccursFree x]
                                                                answer         -> answer

        | from <= 7 && toBool forallRule              = case forallRule of
                                                            (IntroRule n)       -> (Intro n) : nextProofs
                                                            (OccursFreeError v) -> [OccursFree v]

        | otherwise                                   = [NotProved]

        where
            modusPonensMap'  = add2ModusPonensMap e pos modusPonensMap
            indexMap'        = add2IndexMap       e pos indexMap
            nextProofs       = proofBuilder es (pos + 1) 0 modusPonensMap' indexMap'

            schemeAxiomIndex = getAxiomIndex e
            forallAxiom      = getForallAxiom e
            existsAxiom      = getExistsAxiom e

            formalAxiomIndex = getFormalAxiomIndex e

            maybeMP          = getModusPonens e modusPonensMap indexMap

            forallRule       = getForallRule e indexMap
            existsRule       = getExistsRule e indexMap