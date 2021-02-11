{-# LANGUAGE BangPatterns #-}

module Integration
  ( -- * Data types & constructors
    Range

    -- * Functions
  , makeRange
  , sequenceIntegration
  , concurrentIntegration
  , integrateSequently
  , integrateConcurrently
  ) where

import System.Random                (getStdRandom, randomR)
import Control.Parallel.Strategies  (rpar, parMap)
-- | Data type which represents integration range
data Range
  = Range
  { lowerBound :: !Double -- ^ lower bound for integration range
  , upperBound :: !Double -- ^ upper bound for integration range
  } deriving (Show, Eq)

-- | Data type which represents Monte-Carlo rectangle
data MCRectangle
  = MCRectangle
  { mcSquare  :: InfDouble -- ^ rectange square
  , mcXRange  :: Range     -- ^ x rectange range
  , mcYRange  :: Range     -- ^ y rectange range
  } deriving (Show, Eq)

-- | Data type which extends 'Double' data type and provided 'Infinity' value
data InfDouble
  = Infinity      -- ^ infinity constructor
  | Value !Double -- ^ strict value constructor
  deriving (Eq)

instance Show InfDouble where
  show Infinity  = "infinity"
  show (Value x) = show x

-- | Data type for ceiling or flooring result
data CeilOrFloor
  = Ceil  -- ^ ceil  operation constructor
  | Floor -- ^ floor operation constructor
  deriving (Show, Eq)

instance Semigroup InfDouble where
  (<>) = (<+>)

instance Monoid InfDouble where
  mempty = Value 0

-- | Addition operator for 'InfDouble' data type
infixl 6 <+>
(<+>)
  :: InfDouble -- ^ first value
  -> InfDouble -- ^ second value
  -> InfDouble -- ^ addition result
Infinity  <+> _         = Infinity
_         <+> Infinity  = Infinity
(Value x) <+> (Value y) = Value (x + y)


-- | Constructor for 'Range' data type
-- throws error if lower bound > upper bound
makeRange
  :: Double -- ^ lower bound
  -> Double -- ^ upper boun
  -> Range  -- ^ Created range with given bounds
makeRange lower upper =
  case lower <= upper of
    True  -> Range lower upper
    False -> error "lower bound should be <= upper bound"

-- | Returns range length
rangeLength
  :: Range  -- ^ range
  -> Double -- ^ range length
rangeLength range = upperBound range - lowerBound range

-- | Returns range sign according to upper bound
rangeSign
  :: Range  -- ^ range
  -> Double -- ^ sign value
rangeSign (Range _ upper) = if upper >= 0
                            then 1
                            else (-1)

-- | Return zero range with lower bound and upper bound equal to 0
zeroRange
  :: Range
zeroRange = Range 0 0

-- | Finds closest n value for: x ^ 2 < pi * n inequality
-- if 'Ceil' op provided.
-- Finds closest n value for: x ^ 2 > pi * n inequality
-- if 'Floor' operation provided.
-- This inequalities comes from periodic of 1 / tg (x^2)
findPeriodValue
  :: CeilOrFloor -- ^ value to apply ceil or floor operation after calculating
  -> Double      -- ^ given x
  -> Int         -- ^ resulting n periodic value
findPeriodValue op x = case op of
  Ceil  -> ceiling diff
  Floor -> floor   diff
  where
    diff
      :: Double
    diff = xSign * (x * x / pi)

    xSign
      :: Double
    xSign = if x > 0
            then 1
            else -1

-- | Creates 'MCRectangle' from xRange ('Range') for 'f' function
mcRectangle
  :: Range       -- ^ xRange
  -> MCRectangle
mcRectangle xRange@(Range lower upper) =
  if lowerNPeriod <= upperNPeriod
  then MCRectangle Infinity xRange zeroRange
  else case upper < 0 of
    -- interval of function increasing
    True  ->
      MCRectangle (Value $ (yUpper - yLower) * rangeLength xRange)
                  xRange
                  (Range yLower yUpper)
    -- interval of function decreasing
    False ->
      MCRectangle (Value $ (yLower - yUpper) * rangeLength xRange)
                  xRange
                  (Range yUpper yLower)
  where
    lowerNPeriod
      :: Int
    lowerNPeriod = findPeriodValue Ceil lower

    upperNPeriod
      :: Int
    upperNPeriod = findPeriodValue Floor upper

    yLower
      :: Double
    yLower = f lower

    yUpper
      :: Double
    yUpper = f upper

-- | Takes range and splits given range with uniform step according to number of
-- required ranges
splitNRange
  :: Range   -- ^ range for splitting
  -> Int     -- ^ number of required ranges
  -> [Range] -- 'List' of splitted ranges
splitNRange (Range lower upper) n | n <= 0    = []
                                  | otherwise = makeRanges lower n
  where
    step
      :: Double
    step = (upper - lower) / (fromIntegral n)

    makeRanges
      :: Double  -- ^ current lower boubd
      -> Int     -- ^ rest amount of steps
      -> [Range] -- ^ 'List' of splitted ranges
    makeRanges lb k | k <= 0    = []
                    | otherwise =
      (Range lb $ lb + step) : makeRanges (lb + step) (k - 1)

-- | Function to integrate (1 / tg(x^2) - cos(x))
f
  :: Double -- ^ x value
  -> Double -- ^ function value at given x
f x = 1 / tan (x * x) - cos x

-- | Calculates integral with given 'MCRectangle' value
-- | Returns 'Infinity' if monte carlo rectangle does not coverage
-- otherwise calculates integral via Monte-Carlo probability method
mcIntegral
  :: Int          -- ^ number of points to generate
  -> MCRectangle  -- ^ monte carlo rectange value
  -> IO InfDouble -- ^ integral value if it's coverage otherwise infinity
mcIntegral n rectange =
  case mcSquare rectange of
    Infinity -> return Infinity
    Value s  -> do
                p <- probability rectange n
                let yRange       = mcYRange rectange
                let (yLB, yUB)   = (lowerBound yRange, upperBound yRange)
                let xRangeLength = rangeLength $ mcXRange rectange
                let sign         = rangeSign (mcYRange rectange)
                let vertRectSize = if yUB < 0
                                   then yUB
                                   else yLB
                return $ Value $ sign * s * p + vertRectSize * xRangeLength

-- | Returns random double betweeen 0 and 1
randomDouble
  :: IO Double -- ^ Double value from 0 to 1
randomDouble = getStdRandom (randomR (0, 1))

-- | Returns probability of generated n points that are under integral curve
-- of 'f' function
probability
  :: MCRectangle -- ^ Monte-Carlo rectange value ('MCRectangle')
  -> Int         -- ^ number of generated points
  -> IO Double   -- ^ probability of valid point that are under integral curve
probability rectange n | n <= 0    = pure 0
                       | otherwise = do
  cnt <- accumulate 0 n
  return $ cnt / (fromIntegral n)
  where
    xRange
      :: Range
    xRange = mcXRange rectange

    yRange
      :: Range
    yRange = mcYRange rectange

    accumulate
      :: Double
      -> Int
      -> IO Double
    accumulate !acc k | k <= 0    = pure acc
                      | otherwise = do
      xOffset <- randomDouble
      yOffset <- randomDouble
      let x = lowerBound xRange + xOffset * rangeLength xRange
      let y = lowerBound yRange + yOffset * rangeLength yRange
      let value = integralCurveValue y (f x)
      accumulate (acc + value) (k - 1)

-- | Returns 1 if point is under integral curve
-- (upper for negative function value)
-- otherwise 0
integralCurveValue
  :: Double -- ^ y random
  -> Double -- ^ y actual
  -> Double -- ^ result of checking point under integral curve
integralCurveValue yRnd yActual = case yActual >= 0 of
  True  -> if yRnd <= yActual
           then 1
           else 0
  False -> if yRnd >= yActual
           then 1
           else 0

-- | Sequence implementation of Monte-Carlo integration method
-- for 'f' function with given integration 'Range', number of
-- ranges to split and number of generated point in each range
sequenceIntegration
  :: Range        -- ^ integration range
  -> Int          -- ^ number of ranges
  -> Int          -- ^ number of generated points in each range
  -> IO InfDouble -- ^ integration result
sequenceIntegration range n k = (fmap mconcat) $ sequence $
  map (mcIntegral k . mcRectangle) (splitNRange range n)

-- | Concurrent implementation of Monte-Carlo integration method
-- for 'f' function with given integration 'Range', number of
-- ranges to split and number of generated point in each range
-- Strategy b -> (a -> b) -> [a] -> [b]
concurrentIntegration
  :: Range        -- ^ integration range
  -> Int          -- ^ number of ranges
  -> Int          -- ^ number of generated points in each range
  -> IO InfDouble -- ^ integration result
concurrentIntegration range n k = (fmap mconcat) $ sequence parallelResults
  where
    rects
      :: [MCRectangle]
    rects = map mcRectangle $ splitNRange range n

    parallelResults
      :: [IO InfDouble]
    parallelResults = parMap rpar (mcIntegral k) rects

-- | Takes lower and upper bound and integrates 'f' function
-- through lower bound upper bound interval in sequence strategy
-- default number of splitted ranges = 1000
-- default number of generated points according to Monte-Carlo method = 1000
-- to change default params @see 'sequenceIntegration'
integrateSequently
  :: Double       -- ^ lower bound
  -> Double       -- ^ upper bound
  -> IO InfDouble -- ^ integration result
integrateSequently lb rb = sequenceIntegration (makeRange lb rb) 1000 1000

-- | Takes lower and upper bound and integrates 'f' function
-- through lower bound upper bound interval in parallel strategy
-- default number of splitted ranges = 1000
-- default number of generated points according to Monte-Carlo method = 1000
-- to change default params @see 'concurrentIntegration'
integrateConcurrently
  :: Double       -- ^ lower bound
  -> Double       -- ^ upper bound
  -> IO InfDouble -- ^ integration result
integrateConcurrently lb rb = concurrentIntegration (makeRange lb rb) 1000 1000
