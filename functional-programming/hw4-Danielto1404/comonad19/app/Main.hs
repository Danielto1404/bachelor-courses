module Main where

import Control.Concurrent (threadDelay)
import System.Process     (system)

import Virus.Configuration
import Virus.Simulation

-- | Clear visible portion of the ANSI terminal output.
clearScreen
  :: IO ()
clearScreen = do
  _ <- system "clear"
  return ()

-- | Display simulation states in terminal with cleanup and delay.
printSteps
  :: [Simulation]
  -> IO ()
printSteps steps = do
  mapM_ (\s -> clearScreen >> print s >> threadDelay 500000) steps

-- | Request simulation parameters from the user
-- and display the requested number of steps.
main
  :: IO ()
main = do
  putStrLn "Probability of getting infected:"
  prob <- readLn :: IO Double
  putStrLn "Days for incubation:"
  incubatingDays <- readLn :: IO Int
  putStrLn "Days of symptoms:"
  illnessDays <- readLn :: IO Int
  putStrLn "Days of immunity:"
  immuneDays <- readLn :: IO Int
  putStrLn "Grid size:"
  size <- readLn :: IO Int
  putStrLn "Days to simulate:"
  n <- readLn :: IO Int

  case configure prob incubatingDays illnessDays immuneDays of
    Just config -> do
      s <- setupSimulation config size
      printSteps $ take n $ evolveSteps s
    Nothing
      -> putStrLn "Check if probability value in [0..1] and all day values are positive"
