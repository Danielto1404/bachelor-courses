{-# OPTIONS_GHC -O2 -optc-O2 #-}
{-# OPTIONS_GHC -fwarn-incomplete-patterns #-}

module Main where

import Parsing.ProofParser
import Logic.ProofExpression
import Logic.ProofMinimizator

main :: IO ()
main = do
    contents                       <- getContents
    let (firstLine : proofLines)   = lines contents
    let (hypothesesLine, provable) = parseHypothesesLine firstLine
    let provableExpr               = parseExpression provable
    let hypotheses                 = getHypotheses hypothesesLine
    let initialEvidence            = parseList $ proofLines
    let initialChecked             = buildProof initialEvidence hypotheses
    if (null proofLines || any (== Incorrect) initialChecked || last initialEvidence /= provableExpr) then
            putStrLn "Proof is incorrect"
    else do
        let proof          = removeDuplicates initialEvidence provableExpr
        let states         = buildProof proof hypotheses
        let uselessIndexes = simplify states
        let finalProof     = removeByIndex proof uselessIndexes
        let finalStates    = buildProof finalProof hypotheses
        putStrLn $ firstLine
        putStrLn $ getDescribingProof finalStates finalProof


