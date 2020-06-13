module Logic.ProofAnnotator (annotate) where

import Logic.Expression
import Logic.AxiomChecker (getFormalAxiomIndex, getAxiomSchemeIndex)
import Logic.ProofExpression (ProofState (..))

import Logic.AlphabetSet (AlphabetSet (..), (<?>), (<|>), (<@>), (<&>), isEmpty, fromValue)

import qualified Data.Map as Map (lookup, empty, insert, insertWith, Map (..))

-- for key contains right part of implication
-- value contains list of left part of given implication and position in global list
type ModusPonensMap     = Map.Map Expression [(Expression, Int)]

-- indices of expressions
type ExpressionIndexMap = Map.Map Expression Int


isJust :: Maybe a -> Bool
isJust (Just _) = True
isJust _        = False


-- add right_part_of_implication to map as a key, and (left_part_of_implication, pos) as a value
add2ModusPonensMap :: Expression -> Int -> ModusPonensMap -> ModusPonensMap
add2ModusPonensMap (Binary Impl left right) pos m =
    Map.insertWith (\[leftPart] leftParts -> leftPart : leftParts) right [(left, pos)] m
add2ModusPonensMap _                 _   m        = m


-- add expression to map as a key, ans position of this expression in initial evidence as a value
add2IndexMap :: Expression -> Int -> ExpressionIndexMap -> ExpressionIndexMap
add2IndexMap = Map.insert


-- get Modus Ponens or Incorrect if Modus Ponens doesn't exist
getModusPonens :: Expression -> ModusPonensMap -> ExpressionIndexMap -> Maybe ProofState
getModusPonens e modusPonensMap indexMap = case Map.lookup e modusPonensMap of
    Nothing    -> Nothing
    (Just mps) -> let result = extractModusPonens mps (0, 0)
                      extractModusPonens :: [(Expression, Int)] -> (Int, Int) -> Maybe ProofState
                      extractModusPonens [] (0, 0)               = Nothing
                      extractModusPonens [] (i, j)               = Just $ MP i j
                      extractModusPonens ((e, i) : es) mpIndices =
                          case Map.lookup e indexMap of
                              Nothing  -> extractModusPonens es mpIndices
                              (Just j) -> extractModusPonens es (if (j, i) > mpIndices then (j, i) else mpIndices)

                  in result


data SubstitutionState = Found Expression AlphabetSet | Correct | IncorrectSub


-- Finds substitution and quantifiers applied on free occurs of given variable
substitute :: String -> Expression -> Expression -> SubstitutionState
substitute x (Binary op l r) (Binary op' l' r')
                                          | op == op' =
                                              let leftSub = (substitute x l l') in
                                                  case leftSub of
                                                      IncorrectSub        -> IncorrectSub
                                                      Correct             -> (substitute x r r')
                                                      found@(Found e set) ->
                                                          case (substitute x r r') of
                                                              IncorrectSub                -> IncorrectSub
                                                              Correct                     -> found
                                                              (Found e' set') | e == e'   -> Found e $ set <|> set'
                                                                              | otherwise -> IncorrectSub
                                          | otherwise                                      = IncorrectSub

substitute x (Var v) therm                | x == v    = Found therm Singleton
                                          | otherwise = case therm of
                                                            (Var v') | v == v'   -> Correct
                                                                     | otherwise -> IncorrectSub
                                                            _                    -> IncorrectSub

substitute x (Unary op e) (Unary op' e')  | op == op' = substitute x e e'
                                          | otherwise = IncorrectSub

substitute x (Predicate p) (Predicate p') | p == p'   = Correct
                                          | otherwise = IncorrectSub

substitute x Zero Zero                                = Correct

substitute x (Quan q v e) (Quan q' v' e') | q == q' && v == v' =
                                               let result = substitute x e e'
                                               in case result of
                                                    Correct                             -> Correct
                                                    IncorrectSub                        -> IncorrectSub
                                                    found@(Found therm usedQuantifiers) ->
                                                        if (x /= v)
                                                            then case isEmpty usedQuantifiers of
                                                                   True  -> found
                                                                   False -> Found therm $ v <@> usedQuantifiers
                                                            else case therm of
                                                                   (Var z) | z == x    -> Correct
                                                                           | otherwise -> IncorrectSub
                                                                   _                   -> IncorrectSub
                                          | otherwise                                   = IncorrectSub

substitute _ _ _                          = IncorrectSub


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
validateSubstitution x pattern expr = case substitute x pattern expr of
                                    IncorrectSub -> F
                                    Correct      -> T
                                    Found therm usedQuantifiers ->
                                        case therm of
                                            var@(Var v) | x == v    -> T
                                                        | otherwise -> checkFree x usedQuantifiers var
                                            e                       -> checkFree x usedQuantifiers e


checkFree :: String -> AlphabetSet -> Expression -> CheckSubstitutionState
checkFree x usedQuantifiers found =
    if isEmpty $ usedQuantifiers <&> (getFreeVars found)
        then T
        else NotFreeError x found


getFreeVars :: Expression -> AlphabetSet
getFreeVars expr = extractFreeVars expr Empty where
    extractFreeVars (Binary _ l r) used       = (extractFreeVars l used) <|> (extractFreeVars r used)
    extractFreeVars (Quan _ v e)   used       = extractFreeVars e (v <@> used)
    extractFreeVars (Unary _ e)    used       = extractFreeVars e used
    extractFreeVars (Predicate _)  _          = Empty
    extractFreeVars Zero           _          = Empty
    extractFreeVars (Var v) used | v <?> used = Empty
                                 | otherwise  = fromValue v


-- Intro rules zone
getForallRule :: Expression -> ExpressionIndexMap -> CheckSubstitutionState
getForallRule (Binary Impl psi (Quan Any x phi)) indexMap =
    let expr = (Binary Impl psi phi) in
        extractRule x psi expr indexMap
getForallRule _ _ = F


getExistsRule :: Expression -> ExpressionIndexMap -> CheckSubstitutionState
getExistsRule (Binary Impl (Quan Exists x phi) psi) indexMap =
    let expr = (Binary Impl phi psi) in
        extractRule x psi expr indexMap
getExistsRule _ _ = F


extractRule :: String -> Expression -> Expression -> ExpressionIndexMap -> CheckSubstitutionState
extractRule x psi expr indexMap = case Map.lookup expr indexMap of
                                           Nothing  -> F
                                           (Just n) -> case x <?> (getFreeVars psi) of
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
                    let
                        nextState = substitute x phi phiWithX'
                        zeroState = substitute x phi phiWithZero
                    in case (nextState, zeroState) of
                        (Found (Unary Next (Var v)) _, Found Zero _) -> v == x && phi == phi'
                        _                                            -> False

isInductionAxiom _ = False


-- For every proof line create a ProofState object associated with proof line evidence
annotate :: [Expression] -> [ProofState]
annotate proofLines = proofBuilder proofLines 1 Map.empty Map.empty where

    -- marks proof lines as one of proof state
    proofBuilder :: [Expression] -> Int -> ModusPonensMap -> ExpressionIndexMap -> [ProofState]
    proofBuilder []       _   _              _        = []
    proofBuilder (e : es) pos modusPonensMap indexMap

        | schemeAxiomIndex /= 0          = (Axiom schemeAxiomIndex)                  : nextProofs

        | toBool forallAxiom             = case forallAxiom of
                                               T                  -> (Axiom 11)      : nextProofs
                                               (NotFreeError v e) -> [NotFree v e]

        | toBool existsAxiom             = case existsAxiom of
                                               T                  -> (Axiom 12)      : nextProofs
                                               (NotFreeError v e) -> [NotFree v e]

        | isInductionAxiom e             = InductionAxiom : nextProofs

        | formalAxiomIndex /= 0          = (FormalAxiom formalAxiomIndex)             : nextProofs

        | isJust maybeMP                 = let (Just mp) = maybeMP in mp              : nextProofs

        | toBool existsRule              = case existsRule of
                                               (IntroRule n)       -> (Intro n)       : nextProofs
                                               (OccursFreeError v) -> [OccursFree v]

        | toBool forallRule              = case forallRule of
                                               (IntroRule n)       -> (Intro n)       : nextProofs
                                               (OccursFreeError v) -> [OccursFree v]

        | otherwise                      = [NotProved]

        where
            modusPonensMap'  = add2ModusPonensMap e pos modusPonensMap
            indexMap'        = add2IndexMap       e pos indexMap
            nextProofs       = proofBuilder es (pos + 1) modusPonensMap' indexMap'

            schemeAxiomIndex = getAxiomSchemeIndex e
            forallAxiom      = getForallAxiom e
            existsAxiom      = getExistsAxiom e

            formalAxiomIndex = getFormalAxiomIndex e

            maybeMP          = getModusPonens e modusPonensMap indexMap

            forallRule       = getForallRule e indexMap
            existsRule       = getExistsRule e indexMap