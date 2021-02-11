module FirstBlock.StringSum
    ( -- * Functions
      stringSum
    ) where

import Text.Read(readMaybe)

-- | Sum numbers in the string separated by spaces.
-- Returns Nothing if some numbers cannot be converted to an Int
-- or input is empty.
stringSum
  :: String
  -> Maybe Int
stringSum "" = Nothing
stringSum s  = (<$>) sum . traverse readMaybe . words $ s
