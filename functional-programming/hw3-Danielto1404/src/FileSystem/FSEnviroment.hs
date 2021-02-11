{-# LANGUAGE InstanceSigs #-}

module FileSystem.FSEnviroment
  ( -- * Constructors
    VirtualFSEnviroment (..)
  , RealFSEnviroment (..)

    -- * Functions
  , getEnvCurrentDirectory
  , getEnvRootDirectory
  , setEnvCurrentDirectory

    -- * VirtualFS functions
  , changeVirtualFS

    -- Aliases
  , FSEnv
  , VirtualFS
  , RealFS
  ) where

import Control.Monad.Trans.Reader (ReaderT, ask)
import Data.IORef                 (IORef, readIORef, modifyIORef)
import Control.Monad.IO.Class     (liftIO)
import Control.Exception          (throw)

import FileSystem.VirtualFS       (FileSystem)
import Utils.Path                 (isSubPathOf)
import FileSystem.FSException


type FSEnv env = ReaderT (IORef env) IO    -- ^ fs enviroment Aliases
type VirtualFS = FSEnv VirtualFSEnviroment -- ^ type alias for virtual fs
type RealFS    = FSEnv RealFSEnviroment    -- ^ type alias for real fs

data RealFSEnviroment =
  RealFSEnviroment
    { realRootDirectory    :: FilePath -- ^ Root directory representation
    , realCurrentDirectory :: FilePath -- ^ Current directory representation
    } deriving (Eq, Show)


data VirtualFSEnviroment =
  VirtualFSEnviroment
    { virtualRootDirectory    :: FilePath -- ^ Root directory representation
    , virtualCurrentDirectory :: FilePath  -- ^ Current directory representation
    , virtualFileSystem       :: FileSystem -- ^ FileSystem state representation
    } deriving (Eq, Show)

-- | Common interface for both 'VirtualFSEnviroment', 'RealFSEnviroment'
class FileSystemEnv env where

  -- | Returns root directory for specified enviroment
  envRootDirectory
    :: env
    -> FilePath -- ^ root directory file path

  -- | Returs current directory for specified enviroment
  envCurrentDirectory
    :: env
    -> FilePath -- ^ current directory file path

  -- | Changes current directory field for specified enviroment
  envChangeCurrentDirectory
    :: FilePath -- ^ file path to be setted
    -> env      -- ^ old file system enviroment
    -> env      -- ^ updated file system enviroment

-- | 'FileSystemEnv' instance for 'VirtualFSEnviroment'
instance FileSystemEnv VirtualFSEnviroment where
  envRootDirectory
    :: VirtualFSEnviroment
    -> FilePath
  envRootDirectory = virtualRootDirectory

  envCurrentDirectory
    :: VirtualFSEnviroment
    -> FilePath
  envCurrentDirectory = virtualCurrentDirectory

  envChangeCurrentDirectory
    :: FilePath
    -> VirtualFSEnviroment
    -> VirtualFSEnviroment
  envChangeCurrentDirectory path fs = fs { virtualCurrentDirectory = path }

-- | 'FileSystemEnv' instance for 'RealFSEnviroment'
instance FileSystemEnv RealFSEnviroment where
  envRootDirectory
    :: RealFSEnviroment
    -> FilePath
  envRootDirectory = realRootDirectory

  envCurrentDirectory
    :: RealFSEnviroment
    -> FilePath
  envCurrentDirectory = realCurrentDirectory

  envChangeCurrentDirectory
    :: FilePath
    -> RealFSEnviroment
    -> RealFSEnviroment
  envChangeCurrentDirectory path fs = fs { realCurrentDirectory = path }

-- | Change virtualFileSystem field in 'VirtualFSEnviroment' object
changeVirtualFS
  :: FileSystem          -- ^ file system to update
  -> VirtualFSEnviroment -- ^ old virtual file system enviroment
  -> VirtualFSEnviroment -- ^ updated virtual file system enviroment
changeVirtualFS fsTree fsEnv = fsEnv { virtualFileSystem = fsTree }

-- | Takes getter function and applies it to specified enviroment
readEnviroment
  :: FileSystemEnv env
  => (env -> a)  -- ^ file system enviroment getter function
  -> FSEnv env a
readEnviroment getter = ask >>= (liftIO . readIORef)
                            >>= (return . getter)

-- | Returns current directory for specified enviroment
getEnvCurrentDirectory
  :: FileSystemEnv env
  => FSEnv env FilePath
getEnvCurrentDirectory = readEnviroment envCurrentDirectory

-- | Returns root directory for specified enviroment
getEnvRootDirectory
  :: FileSystemEnv env
  => FSEnv env FilePath
getEnvRootDirectory = readEnviroment envRootDirectory

-- | Takes new current directory 'FilePath' and tries to set it as new
-- current directory
-- * 'ImpossibleToGoOutsideRoot'
--   In case of given path is not subpath of root directory
--  @See 'isSubPathOf'
setEnvCurrentDirectory
  :: FileSystemEnv env -- ^ file system enviroment
  => FilePath          -- ^ file path to be setted
  -> FSEnv env ()
setEnvCurrentDirectory path = do
  enviroment <- ask
  root       <- liftIO $ (fmap envRootDirectory) $ readIORef enviroment
  if path `isSubPathOf` root
  then liftIO $ modifyIORef enviroment $ envChangeCurrentDirectory path
  else throw ImpossibleToGoOutsideRoot
