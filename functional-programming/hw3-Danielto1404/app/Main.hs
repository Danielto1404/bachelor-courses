module Main
    ( -- * Functions
      main
    ) where

import Command.StartOption (StartOption (..))
import Command.Handler     (execVirtualFS, execRealFS)
import Parser.RunParser    (runStartOptionParser)
import System.IO           (hFlush, stdout)
import System.Directory    (doesDirectoryExist, getCurrentDirectory)
import Utils.RainbowText

-- Entry point for file manager
-- Prints welcome message and prompts user to specify enviroment
-- @See 'askEnv'
main
  :: IO ()
main = do
  showMagenta $ concat
    [ "\n"
    , "\n      --------------------- "
    , "\n     |                     | "
    , "\n     |  File manager v1.0  | "
    , "\n     |                     | "
    , "\n      --------------------- "
    , "\n"
    ]
  askEnv

-- Prompts user to specify enviroment
-- If enviroment was correctly specified then runs file system interaction
-- otherwise ask user again until success or 'EXIT' command
-- @See 'execVirtualFS', 'execRealFS', 'runStartOptionParser'
askEnv
  :: IO ()
askEnv = do
  putStr "Usage: "
  showCyan "virtual | real ROOT_DIRECTORY_PATH"
  putStrLn "\n"
  putStr "Input emulatig enviroment >> "
  hFlush $ stdout
  enviroment <- getLine
  option     <- runStartOptionParser enviroment
  case option of
    EXIT        -> putStrLn "Terminating.."
    INVALID     -> putStrLn "Use --help to see start options.\n" >> askEnv
    VIRTUAL     -> execVirtualFS
    (REAL root) -> do
                   isValidDirectory <- doesDirectoryExist root
                   currentDirectory <- getCurrentDirectory
                   let root' = if root == "."
                               then currentDirectory
                               else root
                   if isValidDirectory
                   then execRealFS root'
                   else putStrLn "Please give valid directory path.\n" >> askEnv
