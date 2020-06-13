module Logic.AxiomChecker (getAxiomSchemeIndex, getFormalAxiomIndex) where

import Logic.Expression
import Parsing.ProofParser 
import Data.List (elemIndex)


-- LOGIC AXIOMS
isFirstAxiom :: Expression -> Bool
isFirstAxiom (Binary Impl
                   a
                   (Binary Impl
                         b a')) = a == a'
isFirstAxiom _ = False


isSecondAxiom :: Expression -> Bool
isSecondAxiom (Binary Impl
                    (Binary Impl
                        a b)
                    (Binary Impl
                        (Binary Impl
                            a'
                            (Binary Impl
                                b' c))
                        (Binary Impl
                            a'' c'))) = a == a' && a == a'' && b == b' && c == c'
isSecondAxiom _ = False

isThirdAxiom :: Expression -> Bool
isThirdAxiom (Binary Impl
                   (Binary And
                         a b)
                   a') = a == a'
isThirdAxiom _ = False


isFourthAxiom :: Expression -> Bool
isFourthAxiom (Binary Impl
                   (Binary And
                        a b)
                   b') = b == b'
isFourthAxiom _ = False


isFifthAxiom :: Expression -> Bool
isFifthAxiom (Binary Impl
                   a
                   (Binary Impl
                        b
                        (Binary And
                              a' b'))) = a == a' && b == b'
isFifthAxiom _ = False


isSixthAxiom :: Expression -> Bool
isSixthAxiom (Binary Impl
                  a
                  (Binary Or
                       a' b)) = a == a'
isSixthAxiom _ = False


isSeventhAxiom :: Expression -> Bool
isSeventhAxiom (Binary Impl
                    b
                    (Binary Or
                         a b')) = b == b'
isSeventhAxiom _ = False


isEighthAxiom :: Expression -> Bool
isEighthAxiom (Binary Impl
                  (Binary Impl
                      a c)
                  (Binary Impl
                       (Binary Impl
                             b c')
                       (Binary Impl
                             (Binary Or
                                  a' b')
                             c''))) = a == a' && b == b' && c == c' && c == c''
isEighthAxiom _ = False


isNinthAxiom :: Expression -> Bool
isNinthAxiom (Binary Impl
                (Binary Impl
                    a b)
                (Binary Impl
                    (Binary Impl
                        a' (Unary Not b'))
                    (Unary Not a''))) = a == a' && a == a'' && b == b'
isNinthAxiom _ = False


isTenthAxiom :: Expression -> Bool
isTenthAxiom (Binary Impl
                (Unary Not (Unary Not a))
                a') = a == a'
isTenthAxiom _ = False


getAxiomSchemeIndex :: Expression -> Int
getAxiomSchemeIndex exp | isFirstAxiom   exp = 1
                        | isSecondAxiom  exp = 2
                        | isThirdAxiom   exp = 3
                        | isFourthAxiom  exp = 4
                        | isFifthAxiom   exp = 5
                        | isSixthAxiom   exp = 6
                        | isSeventhAxiom exp = 7
                        | isEighthAxiom  exp = 8
                        | isNinthAxiom   exp = 9
                        | isTenthAxiom   exp = 10
                        | otherwise          = 0


-- FORMAL AXIOMS
formalAxioms :: [Expression]
formalAxioms = map parseExpression
    [
        "a = b -> a = c -> b = c",
        "a = b -> a' = b'",
        "a' = b' -> a = b",
        "!(a' = 0)",
        "a + 0 = a",
        "a + b' = (a + b)'",
        "a * 0 = 0",
        "a * b' = a * b + a"
    ]


getFormalAxiomIndex :: Expression -> Int
getFormalAxiomIndex exp = case elemIndex exp formalAxioms of
                            Nothing  -> 0
                            Just idx -> idx + 1