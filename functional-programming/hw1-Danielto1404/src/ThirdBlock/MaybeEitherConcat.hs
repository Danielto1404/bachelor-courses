module ThirdBlock.MaybeEitherConcat
    ( -- * Functions
        maybeConcat
      , eitherConcat
    ) where

import Data.Maybe (catMaybes)

-- | Function 'maybeConcat' takes list of 'Maybe' objects
-- containing list as wrapped value and returns union
-- list of existing wrapped values
maybeConcat
  :: [Maybe [a]]
  -> [a]
maybeConcat = concat . catMaybes

-- | Function 'eitherConcat' takes list of 'Either' objects
-- containing 'Monoid' and returns a pair
-- representing the monoidal concatenation of the corresponding constructors
eitherConcat
  :: (Monoid a, Monoid b)
  => [Either a b]
  -> (a, b)
eitherConcat = foldl folder (mempty, mempty)
  where
    folder (x, y) (Left  value) = (x <> value, y)
    folder (x, y) (Right value) = (x, y <> value)
