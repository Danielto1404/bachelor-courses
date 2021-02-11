module SecondBlock.Moving
    (
    ) where

import Control.Monad.State (State(..), evalState, get, put)

-- | Simple Moving Average implementation via State Monad.
-- takes window size and values
-- returns list of moving averages.
moving
  :: Int
  -> [Double]
  -> [Double]
moving _ [] = []
moving n xs | n <= 0    = error "Window size should be > 0" 
            | otherwise = evalState (simpleMovingAverage xs) []
  where
    simpleMovingAverage
      :: [Double]
      -> State [Double] [Double]
    simpleMovingAverage []       = return []
    simpleMovingAverage (y : ys) = do
      pool <- get
      let nPool = if (length pool) < n
          then pool ++ [y]
          else tail pool ++ [y] -- here we know that list is not empty.
      let avg = sum nPool / fromIntegral (length nPool)
      put nPool
      avgs <- simpleMovingAverage ys
      return $ avg : avgs
