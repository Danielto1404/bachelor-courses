module FS.VirtualFSSpec
  ( virtualFSSpecTree
  , logsTestTree
  ) where

import Test.Tasty       (TestTree)
import Test.Tasty.Hspec (Spec, Selector, it, shouldBe, testSpec, describe
                        , anyException, shouldThrow
                        )
import Data.IORef       (IORef, readIORef, modifyIORef, newIORef)

import FileSystem.FSActions
import FileSystem.FSException
import FileSystem.FSEnviroment
import FileSystem.VirtualFS
import FS.FSExample

logsTestTree
  :: IO TestTree
logsTestTree = testSpec "Logs: " logsSpec

virtualFSSpecTree
  :: IO TestTree
virtualFSSpecTree = testSpec "Virtual FS tests:" specFileSystem

checkFS
  :: IORef VirtualFSEnviroment
  -> FileSystem
  -> IO ()
checkFS ref expected = do
  env        <- readIORef ref
  let actual = virtualFileSystem env
  expected `shouldBe` actual

checkCurrentDirectory
  :: IORef VirtualFSEnviroment
  -> FilePath
  -> IO ()
checkCurrentDirectory ref expected = do
  env        <- readIORef ref
  let actual = virtualCurrentDirectory env
  expected `shouldBe` actual

anyFSError
  :: Selector FSException
anyFSError = const True

specFileSystem
  :: Spec
specFileSystem = do

  describe "-- mkdir --" $ do
    it "creating multiple directories" $ do
      ref <- execVirtualFS (mkdir "b" >> mkdir "a")
      checkFS ref a_b_folders
    it "creating nested directories" $ do
      ref <- execVirtualFS (mkdir "a/b/c/d")
      checkFS ref a_b_c_d_nested_folders
    it "trying to create directory that already exists" $ do
      execVirtualFS (mkdir "a" >> mkdir "b" >> mkdir "a") `shouldThrow`
        anyFSError

  describe "-- touch & cd --" $ do
    it "creating simple file" $ do
      ref <- execVirtualFS (mkdir "a" >> cd "a" >> touch "b.txt")
      checkFS ref a_b_touch
    it "trying to create file that already exists" $ do
      execVirtualFS (touch "a.txt" >> touch "a.txt") `shouldThrow`
        anyFSError

  describe "-- rm --" $ do
    it "removing directories" $ do
      ref <- execVirtualFS (mkdir "a/b/c/d" >> mkdir "x" >> rm "a/b" >> rm "x")
      checkFS ref a_folder
    it "removing files" $ do
      ref <- execVirtualFS (touch "a.txt" >> mkdir "c/d" >> touch "c/d/a.txt"
                            >> rm "a.txt" >> rm "c/d/a.txt"
                           )
      checkFS ref c_d_nested_folders

  describe "-- write --" $ do
    it "writing text to file" $ do
      ref <- execVirtualFS (touch "a.txt" >> write "a.txt" "I love haskell")
      checkFS ref a_file_write

  describe "-- cd  --" $ do
    it "moving to child directory" $ do
      ref <- execVirtualFS (mkdir "a/b/c/d" >> cd "a/../a/b/c/..")
      checkCurrentDirectory ref "~/a/b"
    it "moving to the parent directory" $ do
      ref <- execVirtualFS (mkdir "a/b/c/d" >> cd "a/b/c/d"
                            >> cd ".." >> cd "../../.."
                           )
      checkCurrentDirectory ref "~"
    it "moving to the directory that does not exist" $ do
      execVirtualFS (mkdir "a/b/c/d" >> cd "a/b/c" >> cd "e") `shouldThrow`
        anyFSError

logsSpec
  :: Spec
logsSpec = do
  describe "-- find --" $ do
    it "find file by name" $ do
      _ <- execVirtualFS (mkdir "a/b/c/d" >> mkdir "c/a" >> mkdir "d"
                          >> touch "a/b/c/d/a.txt" >> touch "c/a/a.txt"
                          >> touch "d/a.txt" >> touch "a.txt"
                          >> find "a.txt"
                         )
      return ()
    it "find file that does not exist" $ do
      _ <- execVirtualFS (mkdir "a/b" >> mkdir "b/c" >> mkdir "d/e/c"
                          >> find "a.txt"
                         )
      return ()
