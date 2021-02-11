{-# LANGUAGE InstanceSigs #-}
{-# LANGUAGE FlexibleInstances #-}
{-# OPTIONS_GHC -fno-warn-orphans #-}

module FileSystem.FSActionsIO
  ( -- * File system main commands. (FSActions implementation for IO)
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

import qualified System.Directory as Dir

import Control.Monad.IO.Class     (liftIO)
import Control.Monad.Extra        (partitionM)
import Control.Exception          (throw)
import Data.Time.Clock            (UTCTime)
import System.FilePath            ((</>))

import FileSystem.FSActions
import FileSystem.FSException
import FileSystem.FSEnviroment
import Utils.RainbowText

-- | 'FSActions' instance for 'RealfS'
-- @See 'FSActions'
instance FSActions RealFS where
  logMessage
    :: String
    -> RealFS ()
  logMessage = liftIO . putStrLn

  logException
    :: String
    -> RealFS ()
  logException = liftIO . showException

  logFileName
    :: String
    -> RealFS ()
  logFileName = liftIO . showFile

  logFolderName
    :: String
    -> RealFS ()
  logFolderName = liftIO . showFolder

  logPath
    :: String
    -> RealFS ()
  logPath = liftIO . showPath

  getCurrentDirectory
    :: RealFS FilePath
  getCurrentDirectory = getEnvCurrentDirectory

  getRootDirectory
    :: RealFS FilePath
  getRootDirectory = getEnvRootDirectory

  setCurrentDirectory
    :: FilePath
    -> RealFS ()
  setCurrentDirectory = setEnvCurrentDirectory

  directoryFiles
    :: FilePath
    -> RealFS [FilePath]
  directoryFiles = liftIO . Dir.listDirectory

  createDirectory
    :: FilePath
    -> RealFS ()
  createDirectory = liftIO . Dir.createDirectoryIfMissing True

  doesFileExist
    :: FilePath
    -> RealFS Bool
  doesFileExist = liftIO . Dir.doesFileExist

  doesDirectoryExist
    :: FilePath
    -> RealFS Bool
  doesDirectoryExist = liftIO . Dir.doesDirectoryExist

  readFromFile
    :: FilePath
    -> RealFS String
  readFromFile = liftIO . readFile

  writeToFile
    :: FilePath
    -> String
    -> RealFS ()
  writeToFile path = liftIO . writeFile path

  removeFile
    :: FilePath
    -> RealFS ()
  removeFile = liftIO . Dir.removeFile

  removeDirectory
    :: FilePath
    -> RealFS ()
  removeDirectory path = do
    currentDirectory <- getCurrentDirectory
    if currentDirectory == path
    then throw UnableToDeleteCurrentDirectory
    else liftIO $ Dir.removeDirectoryRecursive path

  findAllFiles
    :: FilePath
    -> RealFS [FilePath]
  findAllFiles path = do
    names                 <- liftIO $ Dir.listDirectory path
    let paths             =  map (path </>) names
    (dirPaths, filePaths) <- liftIO $ partitionM Dir.doesDirectoryExist paths
    subDirFilePaths       <- sequence $ map findAllFiles dirPaths
    return $ filePaths ++ concat subDirFilePaths


  getPermissions
    :: FilePath
    -> RealFS Dir.Permissions
  getPermissions = liftIO . Dir.getPermissions

  getSize
    :: FilePath
    -> RealFS Integer
  getSize = liftIO . Dir.getFileSize

  fileModificationTime
    :: FilePath
    -> RealFS UTCTime
  fileModificationTime = liftIO . Dir.getModificationTime
