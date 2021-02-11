module Main where

import FS (getDirectory)

main :: IO ()
main = do
  putStrLn "\nPut absolute file to root directory to create virtual FS\n"
  root <- getLine
  fs   <- getDirectory root
  putStrLn $ show fs
