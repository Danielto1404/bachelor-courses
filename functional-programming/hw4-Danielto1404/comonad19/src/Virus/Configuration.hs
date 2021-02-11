{-# LANGUAGE InstanceSigs #-}

module Virus.Configuration
  ( -- * Constructors
    CovidConfig (..)
  , PersonState (..)
  , Person      (..)

    -- * Functions
  , configure
  , canTransmit
  , generateNextSeed
  , healthyPerson
  , incubatingPerson
  ) where

import System.Random (StdGen, mkStdGen, random)

-- | Data type which represents Covid-19 transmit parameters.
data CovidConfig
  = CovidConfig
  { infectionProb      :: Double -- ^ Virus infection probability
  , incubatingDuration :: Int    -- ^ 'Person' incubating days
  , illnessDuration    :: Int    -- ^ 'Person' illness days
  , immuneDuration     :: Int    -- ^ 'Person' immune days
  } deriving (Show, Eq)

-- | Takes all 'CovidConfig' parameters and tries to create 'CovidConfig' object
-- If at least one of the parameter is invalid then 'Nothing' returned
configure
  :: Double -- ^ Virus infection probability
  -> Int    -- ^ 'Person' incubating days
  -> Int    -- ^ 'Person' illness days
  -> Int    -- ^ 'Person' immune days
  -> Maybe CovidConfig
configure prob incubatingDays illnessDays immuneDays =
  if (checkConfigurationParams prob incubatingDays illnessDays immuneDays)
  then Just $ CovidConfig { infectionProb      = prob
                          , incubatingDuration = incubatingDays
                          , illnessDuration    = illnessDays
                          , immuneDuration     = immuneDays
                          }
  else Nothing

-- | Checks when that probability in range from 0 to 1 included
-- and days parameters are positive
checkConfigurationParams
  :: Double -- ^ Virus infection probability
  -> Int    -- ^ 'Person' incubating days
  -> Int    -- ^ 'Person' illness days
  -> Int    -- ^ 'Person' immune days
  -> Bool
checkConfigurationParams prob incubatingDays illnessDays immuneDays =
  prob           >= 0 &&
  prob           <= 1 &&
  incubatingDays > 0  &&
  illnessDays    > 0  &&
  immuneDays     > 0


-- | Data type for person's infection state in the simulation.
data PersonState
  = Susceptible    -- ^ healthy  'Person' state (can be infected)
  | Symptoms Int   -- ^ infected 'Person' state (person know about infection)
  | Incubating Int -- ^ infected 'Person' state (person doesn't know about infection)
  | Immune Int     -- ^ healthy  'Person' state (can't be infected)

-- | Data type for person's random & infection state in the simulation
data Person
  = Person
  { personState :: PersonState -- ^ person infection state
  , randomState :: StdGen      -- ^ random state machine
  }

instance Show PersonState where
  show
    :: PersonState
    -> String
  show Susceptible    = "🙂"
  show (Incubating _) = "🦠"
  show (Symptoms   _) = "🥵"
  show (Immune     _) = "🏥"

instance Show Person where
  show
    :: Person
    -> String
  show = show . personState

-- | Return true if 'Person' is infected, otherwise false
canTransmit
  :: Person -- ^ 'Person' illness state
  -> Bool
canTransmit (Person (Incubating _) _) = True
canTransmit (Person (Symptoms   _) _) = True
canTransmit _                         = False

-- | Takes transform function (seed -> seed) and creates new 'StdGen' using
-- given transform function
generateNextSeed
  :: (Int -> Int) -- ^ Function which converts generated seed
  -> Person       -- ^ Old person which incapsulates 'StdGen'
  -> Person
generateNextSeed f p@(Person _ rndGen) = p { randomState = nRandomState } where
  nRandomState
    :: StdGen
  nRandomState = mkStdGen (f $ fst $ random rndGen)

-- | Creates healthy 'Person' with given random state
healthyPerson
  :: StdGen -- ^ Person new random state
  -> Person
healthyPerson = Person Susceptible

-- | Creates incubating 'Person' with given virus configuration ('CovidConfig')
-- and random state
incubatingPerson
  :: CovidConfig -- ^ Virus configuration
  -> StdGen      -- ^ Person new random state
  -> Person
incubatingPerson config =
  Person (Incubating $ incubatingDuration config)
