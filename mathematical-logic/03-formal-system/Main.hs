{-# OPTIONS_GHC -O2 -optc-O2 #-}
{-# OPTIONS_GHC -fwarn-incomplete-patterns #-}


module Main where

import Parsing.ProofParser
import Logic.AxiomChecker
import Logic.ProofExpression
import Logic.ProofAnnotator

main :: IO ()
main = do
--    contents                        <- readFile "debug.txt"
--    contents                        <- readFile "input.txt"
    contents                        <- getContents

    let (provableLine : proofLines) = lines contents
    let provable                    = getProvable provableLine
    let initialEvidence             = parseList $ proofLines
    let proofLines                  = annotate initialEvidence


---------------------DEBUG AREA---------------------------------------------------


--    putStrLn $ show $ try2Substitute "x" (head initialEvidence) (last initialEvidence)
--    putStrLn $ show $ getQuantifiersVars "x" $ head initialEvidence
--    putStrLn $ show $ getFreeVars $ head initialEvidence
--    writeFile "output.txt" ("|-" ++ (show provable) ++ "\n" ++ getDescribingProof proofLines initialEvidence)


----------------------------------------------------------------------------------

    putStrLn ("|-" ++ (show provable))
    putStrLn $ getDescribingProof proofLines initialEvidence
    if (last initialEvidence /= provable) then
         case last proofLines of
            (NotFree _ _)  -> return ()
            (OccursFree _) -> return ()
            NotProved      -> return ()
            _              -> putStrLn "The proof proves different expression."
    else return ()

