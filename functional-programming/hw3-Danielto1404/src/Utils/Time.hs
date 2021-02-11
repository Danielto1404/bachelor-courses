module Utils.Time
  ( createZeroDay
  ) where

import Data.Time.Clock
import Data.Time.Calendar (Day(..))

createZeroDay
  :: UTCTime
createZeroDay = do
  let zeroDay     = ModifiedJulianDay 0
  let zeroDayTime = secondsToDiffTime 0
  UTCTime zeroDay zeroDayTime
