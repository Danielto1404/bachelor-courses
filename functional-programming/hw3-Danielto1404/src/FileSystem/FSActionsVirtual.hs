{-# LANGUAGE InstanceSigs #-}
{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE AllowAmbiguousTypes #-}
{-# OPTIONS_GHC -fno-warn-orphans #-}

module FileSystem.FSActionsVirtual
  ( -- * File system main commands. (FSActions implementation for FileSystem)
    exit
  , help
  , clear
  , pwd
  , dir
  , ls
  , cd
  , info
  , cat
  , touch
  , mkdir
  , rm
  , write
  , find
  ) where

import Control.Monad.Trans.Reader (ask)
import Data.IORef                 (readIORef, modifyIORef)
import Control.Monad.IO.Class     (liftIO)
import Control.Exception          (throw)
import System.Directory           (Permissions)
import Data.Time.Clock            (UTCTime)

import FileSystem.FSActions
import FileSystem.FSException
import FileSystem.FSEnviroment
import FileSystem.VirtualFS
import Utils.RainbowText
import Utils.Time

-- | 'FSActions' instance for 'VirtualFS'
-- @See 'FSActions'
instance FSActions VirtualFS where

  logMessage
    :: String
    -> VirtualFS ()
  logMessage = liftIO . putStrLn

  logException
    :: String
    -> VirtualFS ()
  logException = liftIO . showException

  logFileName
    :: String
    -> VirtualFS ()
  logFileName = liftIO . showFile

  logFolderName
    :: String
    -> VirtualFS ()
  logFolderName = liftIO . showFolder

  logPath
    :: String
    -> VirtualFS ()
  logPath = liftIO . showPath

  getCurrentDirectory
    :: VirtualFS FilePath
  getCurrentDirectory = getEnvCurrentDirectory

  getRootDirectory
    :: VirtualFS FilePath
  getRootDirectory = getEnvRootDirectory

  setCurrentDirectory
    :: FilePath
    -> VirtualFS ()
  setCurrentDirectory = setEnvCurrentDirectory

  directoryFiles
    :: FilePath
    -> VirtualFS [FilePath]
  directoryFiles = readFileTypeOperation (throw DirectoryExpected)
                                         (map fsName . folderTree)

  createDirectory
    :: FilePath
    -> VirtualFS ()
  createDirectory path = modifyFileSystem $ createElement defaultFolder path

  doesFileExist
    :: FilePath
    -> VirtualFS Bool
  doesFileExist = doesExist isFile

  doesDirectoryExist
    :: FilePath
    -> VirtualFS Bool
  doesDirectoryExist = doesExist (not . isFile)

  readFromFile
    :: FilePath
    -> VirtualFS String
  readFromFile = readFileTypeOperation fileContent
                                       (throw FileExpected)

  writeToFile
    :: FilePath
    -> String
    -> VirtualFS ()
  writeToFile path contents = do
    let time = createZeroDay
    modifyFileSystem $ createElement (defaultFile contents time) path

  removeFile
    :: FilePath
    -> VirtualFS ()
  removeFile path = modifyFileSystem (removeElement path)

  removeDirectory
    :: FilePath
    -> VirtualFS ()
  removeDirectory path = do
    currentDirectory <- getCurrentDirectory
    if currentDirectory == path
    then throw UnableToDeleteCurrentDirectory
    else modifyFileSystem (removeElement path)

  findAllFiles
    :: FilePath
    -> VirtualFS [FilePath]
  findAllFiles = readFileTypeOperation (throw DirectoryExpected)
                                       (map fsPath . fsFiles . Folder)

  getPermissions
    :: FilePath
    -> VirtualFS Permissions
  getPermissions = readFileTypeOperation filePermissions
                                         folderPermissions

  getSize
    :: FilePath
    -> VirtualFS Integer
  getSize = readFileTypeOperation fileSize
                                  folderSize

  fileModificationTime
    :: FilePath
    -> VirtualFS UTCTime
  fileModificationTime = readFileTypeOperation fileUpdateTime
                                               (throw FileExpected)


-- Helper functions

readFileSystem
  :: (FileSystem -> a)
  -> VirtualFS a
readFileSystem get = ask >>= (liftIO . readIORef)
                         >>= (return . get . virtualFileSystem)


modifyFileSystem
  :: (FileSystem -> ErrorOrFS)
  -> VirtualFS ()
modifyFileSystem modify = do
  fsState <- ask
  fs      <- (fmap $ modify . virtualFileSystem) $ liftIO $ readIORef fsState
  case fs of
    Right updated -> liftIO $ modifyIORef fsState $ changeVirtualFS updated
    Left  e       -> throw e


readFileTypeOperation
  :: (FileInfo   -> a)
  -> (FolderInfo -> a)
  -> FilePath
  -> VirtualFS a
readFileTypeOperation fileOperation folderOperation path =
  readFileSystem (resolver . findElement path)
    where
      resolver fs = case fs of
        Right (File   file  ) -> fileOperation file
        Right (Folder folder) -> folderOperation folder
        Left  e               -> throw e


doesExist
  :: (FileSystem -> Bool)
  -> FilePath
  -> VirtualFS Bool
doesExist predicate path = readFileSystem $ predicateResolver . findElement path
  where
    predicateResolver
      :: ErrorOrFS
      -> Bool
    predicateResolver fs = case fs of
      Right fsElement -> predicate fsElement
      Left  _         -> False
