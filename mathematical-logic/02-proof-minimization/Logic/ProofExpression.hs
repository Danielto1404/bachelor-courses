module Logic.ProofExpression where

import Logic.Expression

data ProofState = MP Int Int | Axiom Int | Hyp Int | Incorrect deriving Eq

instance Show ProofState where
    show (Axiom n) = concat ["Ax. sch. ", show n]
    show (MP i j)  = concat ["M.P. ", show i, ", ", show j]
    show (Hyp n)   = concat ["Hypothesis ", show n]
    show Incorrect = "Incorrect"


-- returns describing of the proof state
getDescribingLine :: ProofState -> Int -> String
getDescribingLine state n = concat ["[",
                                    show n,
                                    ". ",
                                    show state,
                                    "]"
                                    ]

-- returns single describing line (EX. "[5. Ax. sch. 1] (A -> (A -> A))" )
getTheWholeLine :: Int -> ProofState -> Expression -> String
getTheWholeLine n state expression = concat [getDescribingLine state n,
                                             " ",
                                             show expression,
                                             "\n"
                                             ]

-- returns describing proof lines
getDescribingProof :: [ProofState] -> [Expression]  -> String
getDescribingProof states expressions = concat $ collector (zip states expressions) 1 [] where
    collector :: [(ProofState, Expression)] -> Int -> [String] -> [String]
    collector []       _ acc = reverse acc
    collector (p : ps) n acc = collector ps (n + 1) $ (lineCollector p : acc) where
        lineCollector :: (ProofState, Expression) -> String
        lineCollector = uncurry (getTheWholeLine n)