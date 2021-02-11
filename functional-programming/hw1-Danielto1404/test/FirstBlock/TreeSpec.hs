module FirstBlock.TreeSpec
 ( treeTestTree
 ) where

import Data.List.NonEmpty (NonEmpty(..))
import Numeric.Natural (Natural)
import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, shouldNotBe, testSpec)

import FirstBlock.Tree (Tree(..), isEmpty, find, fromList, insert, remove, size)


treeTestTree
  :: IO TestTree
treeTestTree = testSpec "Tree" treeSpec

treeSpec
  :: Spec
treeSpec = do
  describe "(==)" $ do
    it "Leafs" $
      (Leaf :: Tree Int) `shouldBe` (Leaf :: Tree Int)
    it "Leaf != Branch" $
      Leaf `shouldNotBe` twoValueTree
    it "twoValueTree == twoValueTree" $
      twoValueTree `shouldBe` twoValueTree
    it "tree == tree" $
      tree `shouldBe` tree
    it "twoValueTree != tree" $
      twoValueTree `shouldNotBe` tree
    it "1 != 2" $
      single 1 `shouldNotBe` twoValueTree

  describe "isEmpty" $ do
    it "empty Leaf" $
      isEmpty Leaf `shouldBe` True
    it "empty Branch" $
      isEmpty tree `shouldBe` False

  describe "find" $ do
    it "find in Leaf" $
      find Leaf 1 `shouldBe` Nothing
    it "find in singleton (ok)" $
      find twoValueTree 2 `shouldBe` Just 2
    it "find in singleton (fail)" $
      find twoValueTree 1 `shouldBe` Nothing
    it "find in branch (ok)" $
      find tree 3 `shouldBe` Just 3
    it "find in branch (fail)" $
      find tree 4 `shouldBe` Nothing

  describe "insert" $ do
    it "[2]" $
      fromList [2] `shouldBe` twoValueTree
    it "[2,2,1,3]" $
      fromList [2, 2, 1, 3] `shouldBe` tree
    it "[2,1,3,2]" $
      fromList [2, 1, 3, 2] `shouldBe` tree

  describe "insert" $ do
    it "insert into Leaf" $
      find (insert Leaf 1) 1 `shouldBe` Just 1
    it "insert 2,1,3 into [2]" $
      foldl insert twoValueTree [2, 1, 3] `shouldBe` tree
    it "insert & find 4" $
      find (insert tree 4) 4 `shouldBe` Just 4

  describe "remove" $ do
    it "remove from Leaf" $
      remove Leaf (1 :: Int) `shouldBe` Leaf
    it "remove 2 from [2]" $
      remove twoValueTree 2 `shouldBe` Leaf
    it "remove 1 from [1,2,2,3]" $
      find (remove tree 1) 1 `shouldBe` Nothing
    it "remove 2 from [1,2,2,3]" $
      find (remove tree 2) 2 `shouldBe` Just 2
    it "remove 3 from [1,2,2,3]" $
      find (remove tree 3) 3 `shouldBe` Nothing
    it "remove 4 from [1,2,2,3]" $
      remove tree 4 `shouldBe` tree

  describe "remove (createtree)" $ do
    it "size (ok) 1" $
      size fullWithOutZero `shouldBe` (size full) - 1
    it "size (ok) 2" $
      size fullWithOutFour `shouldBe` (size full) - 1
    it "size (ok) 3" $
      size fullWithOutMinusFour `shouldBe` (size full) - 1
    it "find rmin in full \\ 4" $
      find fullWithOutFour 5 `shouldBe` Just 5
    it "find 0 in full \\ 0" $
      find fullWithOutZero 0 `shouldBe` Nothing
    it "find rmin in full \\ 0" $
      find fullWithOutZero 1 `shouldBe` Just 1
    it "find rmin in full \\ -4" $
      find fullWithOutMinusFour (-3) `shouldBe` Just (-3)
    it "find 4 in full \\ 4" $
      find fullWithOutFour 4 `shouldBe` Nothing
    it "find -4 in full \\ -4" $
      find fullWithOutMinusFour (-4) `shouldBe` Nothing

  describe "size" $ do
    it "size single" $
      size twoValueTree `shouldBe` 1
    it "size tree" $
      size tree `shouldBe` 4
    it "size (full)" $
      size full `shouldBe` 2 ^ treeSize - 1

  where
    treeSize
      :: Int
    treeSize = 12

    single
      :: Int
      -> Tree Int
    single x = Branch (x :| []) Leaf Leaf

    twoValueTree = single (2)
    tree         = Branch (2 :| [2]) (single 1) (single 3)

    createtree
      :: Int
      -> Int
      -> Tree Int
    createtree 0 _ = Leaf
    createtree x v =
      Branch (v :| []) (createtree (x - 1) (v - nValue)) (createtree (x - 1) (v + nValue))
        where
          nValue = 2 ^ (x - 2)

    full                 = createtree treeSize 0
    fullWithOutZero      = remove full 0
    fullWithOutFour      = remove full 4
    fullWithOutMinusFour = remove full (-4)
