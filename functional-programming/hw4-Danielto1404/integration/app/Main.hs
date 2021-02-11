module Main where

import Integration
import Text.Read (readMaybe)

main :: IO ()
main = do
  putStrLn "\nRun this program with \"+RTS -N6 -s -l -RTS\" flags to enable multithreading\n"
  putStrLn "Put lower bound and upper bound of integration interval for:\n"
  putStrLn "    1"
  putStrLn " -------  -  cos(x)"
  putStrLn " tg(x^2)\n"
  rawBounds <- getLine
  let bounds = words rawBounds
  case bounds of
    (lb : rb : []) -> do
      case ((readMaybe lb :: Maybe Double), (readMaybe rb :: Maybe Double)) of
        (Just a, Just b) -> do
          putStrLn $ "Start integration process..." <> "\n"
          res <- integrateConcurrently a b
          putStrLn $ "Integration result:= " <> (show res) <> "\n"
          putStrLn "Check result in WolframAlpha and see the difference :)"
          putStrLn "It will be less than 1e-4"
        _ -> putStrLn "Please input double values."
    _              -> putStrLn "Invalid bounds."
