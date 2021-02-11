module Main
  ( main
  ) where

import Lens.Micro       ((^.), (^?), (^..))
import Test.Tasty       (TestTree, defaultMain)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import FS
import FSLenses
import FSTraversal
import TestFS

fsTestTree
  :: IO TestTree
fsTestTree = testSpec "FS lenses tests:" fsSpec

fsSpec
  :: Spec
fsSpec = do
  describe "Basic lenses" $ do
    it "_Dir traversal traverses only directories" $ do
      testFS ^? _Dir `shouldBe` Just testFS
      fileFS ^? _Dir `shouldBe` Nothing
    it "_File traversal traverses only files" $ do
      fileFS ^? _File `shouldBe` Just fileFS
      testFS ^? _File `shouldBe` Nothing
    it "name lense gets both dir and file names" $ do
      testFS ^. name `shouldBe` "project"
      fileFS ^. name `shouldBe` "file.txt"
    it "contents is Nothing only for files" $ do
      testFS ^? contents `shouldBe` Just (_contents testFS)
      fileFS ^? contents `shouldBe` Nothing
  describe "dirs & files access lenses" $ do
    it "dirName gets only directory names" $ do
      dirName testFS `shouldBe` Just "project"
      dirName fileFS `shouldBe` Nothing
    it "fileName gets only file names" $ do
      fileName fileFS `shouldBe` "file.txt"
      fileName testFS `shouldBe` ""
    it "dirChildren returns dir children for dirs and [] for files" $ do
      dirChildren testFS `shouldBe` _contents testFS
      dirChildren fileFS `shouldBe` []
    it "dirFilenames return dir children filenames non-recursively" $ do
      dirFileNames testFS `shouldBe` ["LICENSE", "runner.exe"]
      dirFileNames fileFS `shouldBe` []
    it "firstSubdirectory returns name of first subdir, if exists" $ do
      firstSubdirectory testFS `shouldBe` Just "src"
      firstSubdirectory fileFS `shouldBe` Nothing
  describe "path functions" $ do
    it "renameToRoot always changes name of root object to /" $ do
      renameToRoot testFS `shouldBe` (testFS { _name = "/" })
      renameToRoot fileFS `shouldBe` (fileFS { _name = "/" })
    it "addRootNameSuffix always adds given suffix to root object name" $ do
      addSuffixToRootName "-haskell" testFS
        `shouldBe` (testFS { _name = "project-haskell" })
      addSuffixToRootName "-haskell" fileFS
        `shouldBe` (fileFS { _name = "file.txt-haskell" })
  describe "traversal operations" $ do
    it "File existence check works as expected" $ do
      let f   = testFS ^? cd "test" . file "Spec.hs"
      let noF = testFS ^? cd "src" . file "not-existed.file"
      f `shouldBe` Just "Spec.hs"
      noF `shouldBe` Nothing
    it "Directory enumeration works as expected" $ do
      testFS
        ^..        ls
        `shouldBe` ["LICENSE", "src", "test", ".git", "runner.exe"]

main
  :: IO ()
main = fsTestTree >>= defaultMain
