{-# LANGUAGE InstanceSigs #-}

module FirstBlock.Weekday
  ( -- * Data types
    Weekday(..)

    -- * Functions
  , afterDays
  , daysToParty
  , isWeekend
  , nextDay
  ) where

-- | Type 'Weekday' represents one of the days of the week
data Weekday
  = Monday    -- ^ 'Weekday' represents monday
  | Tuesday   -- ^ 'Weekday' represents tuesday
  | Wednesday -- ^ 'Weekday' represents wednesday
  | Thursday  -- ^ 'Weekday' represents thursday
  | Friday    -- ^ 'Weekday' represents friday
  | Saturday  -- ^ 'Weekday' represents saturday
  | Sunday    -- ^ 'Weekday' represents sunday
  deriving (Show)

instance Eq Weekday where
  (==)
    :: Weekday
    -> Weekday
    -> Bool
  (==) Monday    Monday    = True
  (==) Tuesday   Tuesday   = True
  (==) Wednesday Wednesday = True
  (==) Thursday  Thursday  = True
  (==) Friday    Friday    = True
  (==) Saturday  Saturday  = True
  (==) Sunday    Sunday    = True
  (==) _         _         = False

-- | Function 'nextDay' takes a 'Weekday' object and returns the next day object.
nextDay
  :: Weekday
  -> Weekday
nextDay Monday    = Tuesday
nextDay Tuesday   = Wednesday
nextDay Wednesday = Thursday
nextDay Thursday  = Friday
nextDay Friday    = Saturday
nextDay Saturday  = Sunday
nextDay Sunday    = Monday

-- | Function 'afterDays' takes a 'Weekday' and a 'Int' number
-- and returns a day that happens after this number of days after a given day.
afterDays
  :: Weekday
  -> Int
  -> Weekday
afterDays day 0 = day
afterDays day n
  | n < 0     = error "After days count argument must be >= 0"
  | otherwise = nextDay $ afterDays day $ n - 1

-- | Function 'isWeekend' takes a 'Weekday' and returns
-- boolean value indicating whether is's 'Saturday' or 'Sunday'.
isWeekend
  :: Weekday
  -> Bool
isWeekend Saturday = True
isWeekend Sunday   = True
isWeekend _        = False

-- | Function 'daysToParty' takes a 'Weekday' and returns
-- the number of days until the nearest 'Friday'.
daysToParty
  :: Weekday
  -> Int
daysToParty Friday = 0
daysToParty day = (1 +) $ daysToParty $ nextDay day
