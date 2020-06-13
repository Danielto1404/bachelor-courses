module Logic.ProofExpression (ProofState (..), getDescribingProof) where

import Logic.Expression


data ProofState = MP Int Int
                | Axiom Int
                | FormalAxiom Int
                | Intro Int
                | InductionAxiom

                | NotFree String Expression
                | OccursFree String
                | NotProved


instance Show ProofState where
    show (MP i j)        = concat ["M.P. ", show i, ", ", show j]
    show (Axiom n)       = concat ["Ax. sch. ", show n]
    show (FormalAxiom n) = concat ["Ax. A", show n]
    show (Intro n)       = concat ["?@-intro ", show n]
    show InductionAxiom  = "Ax. sch. A9"

    show (NotFree v e)   = concat ["variable ", v, " is not free for term ", show e, " in ?@-axiom."]
    show (OccursFree v)  = concat ["variable ", v, " occurs free in ?@-rule."]
    show NotProved       = "is not proved."


-- returns describing of the proof state
getDescribingLine :: ProofState -> Int -> String
getDescribingLine state n = concat ["[", show n, ". ", show state, "]" ]


getTheWholeLine :: Int -> ProofState -> Expression -> String
getTheWholeLine n err@(NotFree _ _)  _ = concat ["Expression ", show n, ": ", show err, "\n"]
getTheWholeLine n err@(OccursFree _) _ = concat ["Expression ", show n, ": ", show err, "\n"]
getTheWholeLine n err@NotProved      _ = concat ["Expression ", show n, " ",  show err, "\n"]
getTheWholeLine n state expression     = concat [getDescribingLine state n,
                                                " ",
                                                show expression,
                                                "\n"
                                                ]

-- returns describing proof lines
getDescribingProof :: [ProofState] -> [Expression]  -> String
getDescribingProof states expressions = concat $ collector (zip states expressions) 1 [] where
    collector :: [(ProofState, Expression)] -> Int -> [String] -> [String]
    collector []       _ acc = reverse ((init $ head acc) : (tail acc))
    collector (p : ps) n acc = collector ps (n + 1) $ (lineCollector p : acc) where
        lineCollector :: (ProofState, Expression) -> String
        lineCollector = uncurry (getTheWholeLine n)