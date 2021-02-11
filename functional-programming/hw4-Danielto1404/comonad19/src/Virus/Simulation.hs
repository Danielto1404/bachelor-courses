{-# LANGUAGE InstanceSigs #-}

module Virus.Simulation
  ( -- * Constructors
    Simulation (..)

    -- * Functions
  , setupSimulation
  , evolve
  , evolveSteps
  ) where

import Virus.Configuration
import Grid
import ListZipper

import Control.Comonad (extend, extract)
import System.Random   (mkStdGen, getStdRandom, random)
import Data.List       (intercalate)

-- | Data type which represents state of virus infection.
data Simulation
  = Simulation
  { gridState   :: Grid Person -- ^ 'Grid' with 'Person'
  , virusConfig :: CovidConfig -- ^ Virus configuration parameters
  , gridSize    :: Int         -- ^ Grid size to be displayed in terminal
  }

instance Show Simulation where
  show
    :: Simulation
    -> String
  show s = intercalate "\n" rowsShown where
    size      = gridSize s
    rows      = unGrid . gridState $ s
    rowsList  = toList size $ fmap (toList size) rows
    rowsShown = map (concatMap show) rowsList

-- | Provided with simulation parameters,
-- instantiate a simulation with initial state.
setupSimulation
  :: CovidConfig   -- ^ virus configuration parameters
  -> Int           -- ^ 'Grid' size
  -> IO Simulation
setupSimulation config size = do
  seed <- getStdRandom random :: IO Int
  let initialPerson       = Person Susceptible (mkStdGen seed)
  let initialRow          = genericMove leftSeed rightSeed initialPerson
  let initialGrid         = Grid $ genericMove upSeed downSeed initialRow
  let startInfectionState = gridWrite
        (initialPerson { personState = Incubating $ incubatingDuration config })
        initialGrid

  return $ Simulation
           { gridState   = startInfectionState
           , virusConfig = config
           , gridSize    = size
           } where
  leftSeed
    :: Person
    -> Person
  leftSeed = generateNextSeed (* 239)

  rightSeed
    :: Person
    -> Person
  rightSeed = generateNextSeed (* 30)

  upSeed
    :: (Functor f)
    => f Person
    -> f Person
  upSeed = fmap (generateNextSeed (`div` 3))

  downSeed
    :: (Functor f)
    => f Person
    -> f Person
  downSeed = fmap (generateNextSeed (+ 33351))

-- | Advance the simulation by a step ("day").
evolve
  :: Simulation -- ^ current 'Simulation' state
  -> Simulation -- ^ next day 'Simulation' state
evolve s = s { gridState = extend (rule $ virusConfig s) (gridState s) } where
  rule
    :: CovidConfig -- ^ Virus main configuration parameters
    -> Grid Person -- ^ 'Grid' focused on 'Person'
    -> Person      -- ^ Updated person
  rule config grid = updatedPerson where
    person
      :: Person
    person = extract grid

    currentState
      :: PersonState
    currentState = personState person

    updatedPerson
      :: Person
    updatedPerson = case currentState of
      Incubating 1 -> person { personState = Symptoms $ illnessDuration config }
      Symptoms   1 -> person { personState = Immune   $ immuneDuration config  }
      Immune     1 -> person { personState = Susceptible                       }
      Incubating n -> person { personState = Incubating (n - 1)                }
      Symptoms   n -> person { personState = Symptoms   (n - 1)                }
      Immune     n -> person { personState = Immune     (n - 1)                }
      Susceptible  -> infect (virusConfig s) grid

-- | Checks when left, right, up, down neighbours can infect focused 'Person'
canBeInfectedByNeighbours
  :: Grid Person -- ^ 'Grid' focused on 'Person'
  -> Bool
canBeInfectedByNeighbours grid = any canTransmit $
  map (\direction -> extract $ direction grid) [left, right, up, down]

-- | Tries to infect focused 'Person'.
-- If infection succeeded then incubating 'Person' returned
-- otherwise returns healthy 'Person'
-- @ In each case new random generator 'StdGen'
-- would be setted to randomState field
infect
  :: CovidConfig -- ^ Virus main configuration parameters
  -> Grid Person -- ^ 'Grid' focused on 'Person'
  -> Person      -- ^ Infected or healthy person
infect config grid =
  if (canBeInfectedByNeighbours grid && chance <= probabilityToBeInfected)
  then incubatingPerson config newRndState
  else healthyPerson newRndState
  where
    probabilityToBeInfected = infectionProb config
    (chance, newRndState)   = random . randomState . extract $ grid

-- | Takes initial 'Simulation' state and applies 'evolve' operation via
-- 'iterate' function
evolveSteps
  :: Simulation   -- ^ Initial 'Simulation' state
  -> [Simulation]
evolveSteps = iterate evolve
