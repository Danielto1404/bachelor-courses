module Logic.ProofAnnotator where

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
type Str_Set   = Set.Set String


indicator_constant :: String
indicator_constant = "INDICATOR_CONSTANT"


isJust :: Maybe a -> Bool
isJust (Just _) = True
isJust _        = False

-- add right part of implication to map as a key, and left part of (implication, pos) as a value
add2ModusPonensMap :: Expression -> Int -> MP_Map -> MP_Map
add2ModusPonensMap (Binary Impl l r) pos m = Map.insertWith (\[left] lefts -> left : lefts) r [(l, pos)] m
add2ModusPonensMap _                 _   m = m


-- add expression to map as a key, ans position of this expression in initial evidence as a value
add2IndexMap :: Expression -> Int -> Index_Map -> Index_Map
add2IndexMap = Map.insert


-- get Modus Ponens or Incorrect if Modus Ponens doesn't exist
getModusPonens :: Expression -> MP_Map -> Index_Map -> Maybe ProofState
getModusPonens e modusPonensMap indexMap = case Map.lookup e modusPonensMap of
    Nothing    -> Nothing
    (Just mps) -> let result = extractModusPonens mps
                      extractModusPonens :: [(Expression, Int)] -> Maybe ProofState
                      extractModusPonens []            = Nothing
                      extractModusPonens ((e, i) : es) =
                          case Map.lookup e indexMap of
                              Nothing  -> extractModusPonens es
                              (Just j) -> (Just $ MP j i)

                  in result


data SubstitutionState = Found Expression | Correct | IncorrectSub deriving (Eq, Show)


unpackValues :: SubstitutionState -> SubstitutionState -> SubstitutionState
unpackValues Correct Correct                      = Correct
unpackValues sub@(Found _) Correct                = sub
unpackValues Correct sub@(Found _)                = sub
unpackValues sub@(Found e) (Found e') | e == e'   = sub
                                      | otherwise = IncorrectSub
unpackValues _        _                           = IncorrectSub


try2Substitute :: String -> Expression -> Expression -> SubstitutionState
try2Substitute x pattern expr = findSub x pattern expr where

    findSub :: String -> Expression -> Expression -> SubstitutionState
    findSub x (Binary op l r) (Binary op' l' r') | op == op' = unpackValues (findSub x l l') (findSub x r r')
                                                 | otherwise = IncorrectSub

    findSub x (Var v) therm                | x == v    = Found therm
                                           | otherwise = case therm of
                                                            (Var v') | v == v'   -> Correct
                                                                     | otherwise -> IncorrectSub
                                                            _                    -> IncorrectSub

    findSub x (Unary op e) (Unary op' e')  | op == op' = findSub x e e'
                                           | otherwise = IncorrectSub

    findSub x (Predicate p) (Predicate p') | p == p'   = Correct
                                           | otherwise = IncorrectSub

    findSub x Zero Zero                                = Correct

    findSub x (Quan q v e) (Quan q' v' e') | q == q' && v == v' = let result = findSub x e e' in
                                               if (x /= v)
                                                then result
                                                else
                                                    case result of
                                                        Correct                       -> Correct
                                                        IncorrectSub                  -> IncorrectSub
                                                        Found var@(Var z) | z == x    -> Correct
                                                                          | otherwise -> IncorrectSub
                                                        Found e                       -> IncorrectSub

                                           | otherwise                                 = IncorrectSub

    findSub _ _ _                          = IncorrectSub


data CheckSubstitutionState = T | F | NotFreeError String Expression | OccursFreeError String | IntroRule Int


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
                                    IncorrectSub -> F
                                    Correct      -> T
                                    Found therm  -> case therm of
                                                        var@(Var v) | x == v    -> T
                                                                    | otherwise -> checkFree x pattern var
                                                        e                       -> checkFree x pattern e


checkFree :: String -> Expression -> Expression -> CheckSubstitutionState
checkFree x pattern found =
    if null $ Set.intersection (getQuantifiersVars x pattern) (getFreeVars found)
        then T
        else NotFreeError x found


getFreeVars :: Expression -> Str_Set
getFreeVars expr = extractFreeVars expr Set.empty where
    extractFreeVars (Binary _ l r) used              = Set.union (extractFreeVars l used) (extractFreeVars r used)
    extractFreeVars (Quan _ v e)   used              = extractFreeVars e (Set.insert v used)
    extractFreeVars (Unary _ e)    used              = extractFreeVars e used
    extractFreeVars (Predicate _)  _                 = Set.empty
    extractFreeVars Zero           _                 = Set.empty
    extractFreeVars (Var v) used | Set.member v used = Set.empty
                                 | otherwise         = Set.insert v Set.empty


getQuantifiersVars :: String -> Expression -> Str_Set
getQuantifiersVars x expr = extractQuanVars x expr where
    extractQuanVars x (Binary _ l r)              = Set.union (extractQuanVars x l) (extractQuanVars x r)
    extractQuanVars x (Unary _ e)                 = extractQuanVars x e
    extractQuanVars x (Predicate _)               = Set.empty
    extractQuanVars x Zero                        = Set.empty
    extractQuanVars x (Var v)       | x /= v      = Set.empty
                                    | otherwise   = Set.insert indicator_constant Set.empty

    extractQuanVars x (Quan _ v e)  | v == x      = Set.empty
                                    | otherwise   = let resultSet = (extractQuanVars x e)
                                                    in case null resultSet of
                                                        True  -> Set.empty
                                                        False -> Set.insert v resultSet


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
                                           (Just n) -> case Set.member x (getFreeVars psi) of
                                                           True  -> OccursFreeError x
                                                           False -> IntroRule n


-- INDUCTION AXIOM
isInductionAxiom :: Expression -> Bool
isInductionAxiom (Binary Impl
                    (Binary And
                        phiWithZero
                        (Quan Any x
                            (Binary Impl
                                phi phiWithX')))
                    phi') =
                 phi == phi' &&
                 try2Substitute x phi phiWithX' == (Found . Unary Next . Var $ x) &&
                 try2Substitute x phi phiWithZero == Found Zero
isInductionAxiom _ = False


-- For every proof line create a ProofState object associated with proof line evidence
annotate :: [Expression] -> [ProofState]
annotate proofLines = proofBuilder proofLines 1 Map.empty Map.empty where

    -- marks proof lines as one of proof state
    proofBuilder :: [Expression] -> Int -> MP_Map -> Index_Map -> [ProofState]
    proofBuilder []       _   _              _        = []
    proofBuilder (e : es) pos modusPonensMap indexMap

        | schemeAxiomIndex /= 0                       = (Axiom schemeAxiomIndex) : nextProofs

        | toBool forallAxiom                          = case forallAxiom of
                                                            T                  -> (Axiom 11) : nextProofs
                                                            (NotFreeError v e) -> [NotFree v e]

        | toBool existsAxiom                          = case existsAxiom of
                                                            T                  -> (Axiom 12) : nextProofs
                                                            (NotFreeError v e) -> [NotFree v e]

        | isInductionAxiom e                          = InductionAxiom : nextProofs

        | formalAxiomIndex /= 0                       = (FormalAxiom formalAxiomIndex) : nextProofs

        | isJust maybeMP                              = let (Just mp) = maybeMP in mp : nextProofs

        | toBool forallRule                           = case forallRule of
                                                            (IntroRule n)       -> (Intro n) : nextProofs
                                                            (OccursFreeError v) -> [OccursFree v]

        | toBool existsRule                           = case existsRule of
                                                            (IntroRule n)       -> (Intro n) : nextProofs
                                                            (OccursFreeError v) -> [OccursFree v]

        | otherwise                                   = [NotProved]

        where
            modusPonensMap'  = add2ModusPonensMap e pos modusPonensMap
            indexMap'        = add2IndexMap       e pos indexMap
            nextProofs       = proofBuilder es (pos + 1) modusPonensMap' indexMap'

            schemeAxiomIndex = getAxiomIndex e
            forallAxiom      = getForallAxiom e
            existsAxiom      = getExistsAxiom e

            formalAxiomIndex = getFormalAxiomIndex e

            maybeMP          = getModusPonens e modusPonensMap indexMap

            forallRule       = getForallRule e indexMap
            existsRule       = getExistsRule e indexMap