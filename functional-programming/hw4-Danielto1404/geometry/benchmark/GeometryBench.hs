module GeometryBench
  ( -- * Functions
    strictDoubleArea
  , strictPerimeter
  , naiveDoubleArea
  , naivePerimeter
  ) where

import Control.DeepSeq (NFData)
import Criterion.Main  (Benchmark, bench, nf)
import Data.List       (iterate')

import Geometry

circleDots
  :: Double -- ^ radius of circle
  -> Int    -- ^ amount of vertices
  -> Polygon
circleDots r n | n <= 0    = []
               | otherwise = map dotByAngle angles where
  angleStep
    :: Double
  angleStep = 2 * pi / fromIntegral n
  dotByAngle
    :: Double
    -> Point
  dotByAngle a = Point (round (r * cos a)) (round (r * sin a))

  angles
    :: [Double]
  angles = take n $ iterate' (+ angleStep) 0

polygons
  :: [Polygon]
polygons = map (circleDots 1000) [100000, 1000000]

strictPerimeter
  :: [Benchmark]
strictPerimeter = map (benchmark perimeter) polygons

strictDoubleArea
  :: [Benchmark]
strictDoubleArea = map (benchmark doubleArea) polygons

naivePerimeter
  :: [Benchmark]
naivePerimeter = map (benchmark perimeterNaive) polygons

naiveDoubleArea
  :: [Benchmark]
naiveDoubleArea = map (benchmark doubleAreaNaive) polygons

benchmark
  :: NFData b
  => ([a] -> b)
  -> [a]
  -> Benchmark
benchmark f testPolygons =
  bench ("1e" <> show size <> " vertices") (nf f testPolygons)
    where
      size
        :: Int
      size = round (logBase 10 . fromIntegral . length $ testPolygons :: Double)
