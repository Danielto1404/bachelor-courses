{-# LANGUAGE InstanceSigs #-}

module FirstBlock.Tree
  ( -- * Data types
    Tree(..)

    -- * Functions
  , isEmpty
  , find
  , fromList
  , insert
  , remove
  , size
  ) where

import Data.List.NonEmpty as NE (NonEmpty (..), (<|), head)

data Tree a
  -- | Constructor of 'Tree' representing empty node.
  = Leaf
  -- | Constructor of 'Tree' representing inner node.
  | Branch
      {
        values  :: NonEmpty a -- ^ Field storing values.
        , left  :: Tree a     -- ^ Field storing left subtree.
        , right :: Tree a     -- ^ Field storing right subtree.
      }
  deriving (Show)

-- | 'Tree' type conforms to 'Eq' class type
instance (Eq a) => Eq (Tree a) where
  (==)
    :: Tree a
    -> Tree a
    -> Bool
  (==) Leaf           Leaf              = True
  (==) (Branch v l r) (Branch v' l' r') =
    (v == v') && (l == l') && (r == r')
  (==) _              _                 = False

-- | Function 'isEmpty' checks wheater our 'Tree' empty or not.
isEmpty
  :: Tree a
  -> Bool
isEmpty Leaf = True
isEmpty _    = False

-- | Function 'size' calculates number of elements in given 'Tree'.
size
  :: Tree a
  -> Int
size Leaf = 0
size tree = (length $ values tree) + (size $ left tree) + (size $ right tree)

-- | Function 'find' takes 'Tree' and an element to find
-- and returns 'Nothing' if no value presented and 'Just' otherwise.
find
  :: (Ord a)
  => Tree a
  -> a
  -> Maybe a
find Leaf _   = Nothing
find (Branch vals l r) x
  | x < value = find l x
  | x > value = find r x
  | otherwise = Just x
  where
    value = NE.head vals

-- | Function 'insert' takes a 'Tree' object and a value
-- and returns a new tree with given value inserted into given 'Tree'.
insert
  :: (Ord a)
  => Tree a
  -> a
  -> Tree a
insert Leaf x  = Branch (x :| []) Leaf Leaf
insert tree@(Branch vals l r) x
  | x < value  = tree { left   = insert l x }
  | x > value  = tree { right  = insert r x }
  | otherwise  = tree { values = x <| vals  }
  where
    value = NE.head vals

-- | Function 'fromList' takes a list of values and
-- returns 'Tree' object constructed from given list values.
fromList
  :: (Ord a)
  => [a]
  -> Tree a
fromList = foldl insert Leaf

-- | Function 'remove' takes a 'Tree' object and a value
-- and returns given 'Tree' with removed given value from it.
remove
  :: (Ord a)
  => Tree a
  -> a
  -> Tree a
remove Leaf _ = Leaf
remove tree@(Branch vals l r) x
  | x < value = tree { left  = remove l x }
  | x > value = tree { right = remove r x }
  | otherwise = case vals of
                  (_ :| []) -> mergeBinarySearchTrees l r
                  _         -> tree { values = updateValues vals }
  where
   value = NE.head vals

   updateValues
     :: NonEmpty a
     -> NonEmpty a
   updateValues (_ :| h : xs) = h :| xs
   updateValues _             = error "List length must be >= 2"

   mergeBinarySearchTrees
     :: Tree a
     -> Tree a
     -> Tree a
   mergeBinarySearchTrees leftTree rootTree =
     case rootTree of
       Leaf                        -> leftTree
       Branch { left = pivotTree } ->
         rootTree { left = mergeBinarySearchTrees leftTree pivotTree }


-- SecondBlock Foldable instance for 'Tree'

-- | 'Tree' type conforms to 'Foldable' class type
instance Foldable Tree where
  foldMap
    :: Monoid m
    => (a -> m)
    -> Tree a
    -> m
  foldMap _ Leaf              = mempty
  foldMap f (Branch vals l r) = (foldMap f vals) <> (foldMap f l) <> (foldMap f r)

  foldr
    :: (a -> b -> b)
    -> b
    -> Tree a
    -> b
  foldr _ acc Leaf              = acc
  foldr f acc (Branch vals l r) =
    let
      rightResult = foldr f acc r
      nodeResult  = foldr f rightResult vals
      leftResult  = foldr f nodeResult l
    in leftResult
