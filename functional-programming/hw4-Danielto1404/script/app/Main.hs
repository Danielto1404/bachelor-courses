module Main
  ( main
  ) where


import HSExpr
import Interpreter
import Transpiler


-- | HalyavaScript sample with integers, boolean, function and if-else block.
-- For a given @x@ calculates @boolean (> 10)@.
gt10
  :: HSExpr expr
  => Int
  -> expr Bool
gt10 =
  hsFun False $ \x result ->
    hsIfElse (x @> 10) (result @= True) (result @= False)

main
  :: IO ()
main = do
  putStrLn $ "Input value to eval gt10 (x): " <> "\n"
  x          <- readLn :: IO Int
  putStrLn "\n"
  let res    = interpret (gt10 x)
  let jsCode = transpile (gt10 x)
  putStrLn $ jsCode <> "\n"
  putStrLn $ "Result of evaluating: = " <> show res <> "\n"
