module SecondBlock.Split
  ( -- * Functions
      splitOn
    , joinWith
  ) where


import Data.List.NonEmpty as NE (NonEmpty(..), toList)

-- | Function 'splitOn' takes split element that satisfy to 'Eq' instance
--  and a list and returns 'NonEmpty' object which contains nested list
-- of elements by given split element
splitOn
  :: (Eq a)
  => a
  -> [a]
  -> NonEmpty [a]
splitOn delimiter = foldr splitOnGroups ([] :| [])
  where
    splitOnGroups e (group :| groups)
      | e == delimiter = [] :| (group : groups)
      | otherwise      = (e : group) :| groups

-- | Function 'joinWith' takes join element and 'NonEmpty' values
-- and returs list of values joined with join element
joinWith
  :: a
  -> NonEmpty [a]
  -> [a]
joinWith delimiter = (foldr1 joinGroups) . NE.toList
  where
    joinGroups group groups = (group ++ (delimiter : groups))
