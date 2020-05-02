module Logic.AxiomChecker where

import Logic.Expression

isFirstAxiom :: Expression -> Bool
isFirstAxiom (Binary Impl a (Binary Impl b a')) =
                 a == a'
isFirstAxiom _ = False


isSecondAxiom :: Expression -> Bool
isSecondAxiom (Binary Impl (Binary Impl a b) (Binary Impl (Binary Impl a' (Binary Impl b' c)) (Binary Impl a'' c'))) =
                  a == a' && a == a'' && b == b' && c == c'
isSecondAxiom _ = False


isThirdAxiom :: Expression -> Bool
isThirdAxiom (Binary Impl a (Binary Impl b (Binary And a' b'))) =
                 a == a' && b == b'
isThirdAxiom _ = False


isFourthAxiom :: Expression -> Bool
isFourthAxiom (Binary Impl (Binary And a b) a') =
                  a == a'
isFourthAxiom _ = False


isFifthAxiom :: Expression -> Bool
isFifthAxiom (Binary Impl (Binary And a b) b') =
                 b == b'
isFifthAxiom _ = False


isSixthAxiom :: Expression -> Bool
isSixthAxiom (Binary Impl a (Binary Or a' b)) =
                 a == a' 
isSixthAxiom _ = False


isSeventhAxiom :: Expression -> Bool
isSeventhAxiom (Binary Impl b (Binary Or a b')) =
                   b == b'
isSeventhAxiom _ = False


isEighthAxiom :: Expression -> Bool
isEighthAxiom (Binary Impl (Binary Impl a c) (Binary Impl (Binary Impl b c') (Binary Impl (Binary Or a' b') c''))) =
                  a == a' && b == b' && c == c' && c == c''
isEighthAxiom _ = False


isNinthAxiom :: Expression -> Bool
isNinthAxiom (Binary Impl (Binary Impl a b) (Binary Impl (Binary Impl a' (Not b')) (Not a''))) =
                 a == a' && a == a'' && b == b'
isNinthAxiom _ = False


isTenthAxiom :: Expression -> Bool
isTenthAxiom (Binary Impl (Not (Not a)) a') =
                 a == a'
isTenthAxiom _ = False


getAxiomIndex :: Expression -> Int
getAxiomIndex exp | isFirstAxiom   exp = 1
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