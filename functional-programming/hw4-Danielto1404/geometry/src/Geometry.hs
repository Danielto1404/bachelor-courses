{-# LANGUAGE BangPatterns #-}

module Geometry
  ( -- * Constructors
    Point(..)

    -- * Aliases
  , Polygon

    -- * Functions
  , plus
  , minus
  , scalarProduct
  , crossProduct
  , perimeter
  , doubleArea
  , perimeterNaive
  , doubleAreaNaive
  ) where

import Data.Function (on)

-- | Data type of Point, which represents point in 2D with two 'Int' coordinates
data Point = Point
  { x :: Int -- ^ x coordinate
  , y :: Int -- ^ y coordinate
  }

instance Show Point where
  show (Point xi yi) = show (xi, yi)

type Polygon = [Point]

-- | Calculates 2-dimensional addition for two points
plus
  :: Point -- ^ first point
  -> Point -- ^ second point
  -> Point -- ^ addition result of two given points
plus a b = Point (x a + x b) (y a + y b)

-- | Calculates 2-dimensional difference for two points
minus
  :: Point -- ^ first point
  -> Point -- ^ second point
  -> Point -- ^ difference result of two given points
minus a b = Point (x a - x b) (y a - y b)

-- | Calculates scalar product of two points.
scalarProduct
  :: Point -- ^ first point
  -> Point -- ^ second point
  -> Int   -- ^ scalar result for two given points
scalarProduct a b = x a * x b + y a * y b

-- | Calculates pseudo-scalar product of two points
crossProduct
  :: Point -- ^ first point
  -> Point -- ^ second point
  -> Int   -- ^ determinant value for given 2-dimensional points
crossProduct a b = x a * y b - x b * y a

-- | Calculates euclidean 2-dimensional distance betweeen two points
distance
  :: Point  -- ^ first point
  -> Point  -- ^ second point
  -> Double -- ^ euclidean distance betweeen given points
distance a b = sqrt $ fromIntegral $ on (+) (\z -> z * z) dx dy
  where
    dx = x a - x b
    dy = y a - y b

-- | Folds given Polygon with given function, that transforms two 'Point's
-- to the number value (strict implementation via BangPatterns).
polygonFoldr
  :: (Num a)
  => (Point -> Point -> a) -- ^ function to fold with
  -> Polygon -- ^ 'List' of polygon's vertices in counterclockwise order
  -> a       -- ^ resulting number
polygonFoldr _ []             = 0
polygonFoldr f points@(a : _) =
  fst $ foldr (\b (!acc, c) -> (acc + f b c, b)) (0, a) points

-- | Calculate perimeter of given polygon,
-- which is represented by list of its vertices. (Strict implementation)
perimeter
  :: Polygon -- ^ polygon vertices given in counterclockwise order
  -> Double  -- ^ perimeter for given polygon vertices
perimeter = polygonFoldr distance

-- | Calculate doubled area of given polygon,
-- which is represented by list of its vertices. (Strict implementation)
-- Uses Gauss's formula.
doubleArea
  :: Polygon -- ^ polygon vertices given in counterclockwise order
  -> Int     -- ^ resulting doubled area for given polygon
doubleArea = polygonFoldr crossProduct

-- | Folds given Polygon with given function, that transforms two 'Point's
-- to the number value. (Non-strict implementation)
polygonFoldrNaive
  :: (Num a)
  => (Point -> Point -> a) -- ^ function to fold with
  -> Polygon -- ^ 'List' of polygon's vertices in counterclockwise order
  -> a       -- ^ resulting number
polygonFoldrNaive _ []             = 0
polygonFoldrNaive f points@(a : _) =
  fst $ foldr (\b (acc, c) -> (acc + f b c, b)) (0, a) points

-- | Calculate perimeter of given polygon,
-- which is represented by list of its vertices. (Non-strict implementation)
perimeterNaive
  :: Polygon -- ^ polygon vertices given in counterclockwise order
  -> Double  -- ^ perimeter for given polygon vertices
perimeterNaive = polygonFoldrNaive distance

-- | Calculate doubled area of given polygon,
-- which is represented by list of its vertices. (Non-strict implementation)
-- Uses Gauss's formula.
doubleAreaNaive
  :: Polygon -- ^ polygon vertices given in counterclockwise order
  -> Int     -- ^ resulting doubled area for given polygon
doubleAreaNaive = polygonFoldrNaive crossProduct
