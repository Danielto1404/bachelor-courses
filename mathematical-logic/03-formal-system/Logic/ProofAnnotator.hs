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

-- add right part of implication to map as a key, and left part of (implication, pos) as a value
add_to_MP_Map :: Expression -> Int -> MP_Map -> MP_Map
add_to_MP_Map (Binary Impl left right) pos m = Map.insertWith (\[newLeft] lefts -> newLeft : lefts) right [(left, pos)] m
add_to_MP_Map _                        _   m = m


-- add expression to map as a key, ans position of this expression in initial evidence as a value
add_to_Index_Map :: Expression -> Int -> Index_Map -> Index_Map
add_to_Index_Map = Map.insert


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


data Substitution = Found Expression | Correct | IncorrectSub deriving (Show, Eq, Ord)


unpackValues :: Substitution -> Substitution -> Substitution
unpackValues Correct Correct                      = Correct
unpackValues sub@(Found _) Correct                = sub
unpackValues Correct sub@(Found _)                = sub
unpackValues sub@(Found e) (Found e') | e == e'   = sub
                                | otherwise = IncorrectSub
unpackValues _        _                           = IncorrectSub


substitution :: String -> Expression -> Expression -> Substitution
substitution x pattern expr = findSub x pattern expr where

    findSub :: String -> Expression -> Expression -> Substitution
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
                                                                          | otherwise -> Found var
                                                        Found e                       -> Found e

                                           | otherwise                                 = IncorrectSub

    findSub _ _ _                          = IncorrectSub


data CheckSubstitution = T | F | Error String Expression


toBool ::(CheckSubstitution, Int) -> Bool
toBool (T, _) = True
toBool (F, _) = False
toBool _      = True


isJust :: Maybe a -> Bool
isJust (Just _) = True
isJust _        = False


isSubstitution :: String -> Expression -> Expression -> CheckSubstitution
isSubstitution x pattern expr = case substitution x pattern expr of
                                    IncorrectSub                  -> F
                                    Correct                       -> T

                                    Found var@(Var v) | x == v    -> T
                                                      | otherwise -> if (checkFree x pattern var)
                                                                         then T
                                                                         else Error x var

                                    Found e                       -> if (checkFree x pattern e)
                                                                         then T
                                                                         else Error x e


checkFree :: String -> Expression -> Expression -> Bool
checkFree x pattern found = null $ Set.intersection (getUsedQuantifiers x pattern) (getFreeVars found)


getFreeVars :: Expression -> Str_Set
getFreeVars expr = extractFreeVars expr Set.empty where
    extractFreeVars (Quan _ v e)   used              = extractFreeVars e (Set.insert v used)
    extractFreeVars (Unary _ e)    used              = extractFreeVars e used
    extractFreeVars (Binary _ l r) used              = Set.union (extractFreeVars l used) (extractFreeVars r used)
    extractFreeVars (Predicate _)  _                 = Set.empty
    extractFreeVars Zero           _                 = Set.empty
    extractFreeVars (Var v) used | Set.member v used = Set.empty
                                 | otherwise         = Set.insert v Set.empty


getUsedQuantifiers :: String -> Expression -> Str_Set
getUsedQuantifiers x expr = extractUsedVars x expr where
    extractUsedVars x (Binary _ l r)              = Set.union (extractUsedVars x l) (extractUsedVars x r)
    extractUsedVars x (Unary _ e)                 = extractUsedVars x e
    extractUsedVars x (Predicate _)               = Set.empty
    extractUsedVars x Zero                        = Set.empty
    extractUsedVars x (Var v)       | x /= v      = Set.empty
                                    | otherwise   = Set.insert indicator_constant Set.empty

    extractUsedVars x (Quan _ v e)  | v == x      = Set.empty
                                    | otherwise   = let resultSet = (extractUsedVars x e)
                                                    in case null resultSet of
                                                        True  -> Set.empty
                                                        False -> Set.insert v resultSet




getForallRule :: Expression -> Index_Map -> (CheckSubstitution, Int)
getForallRule (Binary Impl psi (Quan Any x phi)) indexMap =
    let expr = (Binary Impl psi phi) in
            case Map.lookup expr indexMap of
                Nothing  -> (F, 0)
                (Just i) -> case Set.member x (getFreeVars psi) of
                                True  -> (Error x Zero, 0)
                                False -> (T, i)
getForallRule _ _ = (F, 0)


getExistsRule :: Expression -> Index_Map -> (CheckSubstitution, Int)
getExistsRule (Binary Impl (Quan Exists x phi) psi) indexMap =
    let expr = (Binary Impl phi psi) in
        case Map.lookup expr indexMap of
            Nothing  -> (F, 0)
            (Just i) -> case Set.member x (getFreeVars psi) of
                            True  -> (Error x Zero, 0)
                            False -> (T, i)
getExistsRule _ _ = (F, 0)


predicateAxiomForall :: Expression -> (CheckSubstitution, Int)
predicateAxiomForall (Binary Impl (Quan Any x psi) psi') = (isSubstitution x psi psi', 0)
predicateAxiomForall _                                   = (F, 0)


predicateAxiomExists :: Expression -> (CheckSubstitution, Int)
predicateAxiomExists (Binary Impl psi' (Quan Exists x psi)) = (isSubstitution x psi psi', 0)
predicateAxiomExists _                                      = (F, 0)


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
                 substitution x phi phiWithX' == (Found . Unary Next . Var $ x) &&
                 substitution x phi phiWithZero == Found Zero
isInductionAxiom _ = False


-- For every proof line create a ProofState object associated with proof line evidence
buildProof :: [Expression] -> [ProofState]
buildProof proofLines = proofBuilder proofLines 1 Map.empty Map.empty where

    -- marks proof lines as one of proof state
    proofBuilder :: [Expression] -> Int -> MP_Map -> Index_Map -> [ProofState]
    proofBuilder []       _   _              _        = []
    proofBuilder (e : es) pos modusPonensMap indexMap
        | axiomIndex /= 0                             = (Axiom axiomIndex)                     : nextProofs

        | toBool forall                               = case forall of
                                                            (T, _)           -> (Axiom 11)     : nextProofs
                                                            ((Error v e), _) -> [NotFree v e]

        | toBool exists                               = case exists of
                                                            (T, _)           -> (Axiom 12)     : nextProofs
                                                            ((Error v e), _) -> [NotFree v e]

        | isInductionAxiom e                          = InductionAxiom                         : nextProofs

        | formalAxiomIndex /= 0                       = (FormalAxiom formalAxiomIndex)         : nextProofs

        | isJust maybeMP                              = let (Just mp) = maybeMP in mp          : nextProofs

        | toBool forallRule                           = case forallRule of
                                                            (T, i)           -> (Intro i)      : nextProofs
                                                            ((Error v _), _) -> [OccursFree v]

        | toBool existsRule                           = case existsRule of
                                                            (T, i)           -> (Intro i)      : nextProofs
                                                            ((Error v _), _) -> [OccursFree v]

        | otherwise                                   = [NotProved]

        where
            modusPonensMap'  = add_to_MP_Map    e pos modusPonensMap
            indexMap'        = add_to_Index_Map e pos indexMap
            nextProofs       = proofBuilder es (pos + 1) modusPonensMap' indexMap'

            axiomIndex       = getAxiomIndex e
            forall           = predicateAxiomForall e
            exists           = predicateAxiomExists e

            formalAxiomIndex = getFormalAxiomIndex e

            maybeMP          = getModusPonens e modusPonensMap indexMap

            forallRule       = getForallRule e indexMap
            existsRule       = getExistsRule e indexMap

