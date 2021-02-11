{-# LANGUAGE InstanceSigs #-}

module FS
  ( -- * Constructors
    FS (..)

    -- * Functions
  , getDirectory
  ) where

import Control.Exception     (throwIO)
import System.Directory      (doesDirectoryExist, listDirectory)
import System.FilePath.Posix (takeFileName, (</>))

-- | Data for FileSystem structure representation.
data FS
  = Dir                   -- ^ Directory item constructor
  { _name     :: FilePath -- ^ directory name
  , _contents :: [FS]     -- ^ directory subtree
  }
  | File                  -- ^ File item constructor
  { _name     :: FilePath -- ^ file name
  } deriving (Eq)

instance Show FS where
  show
    :: FS -- ^ virtual 'FS' representation
    -> String
  show fileSystem = showHelper 0 fileSystem where
    showHelper
      :: Int -- ^ indent from left anchor
      -> FS  -- ^ virtual 'FS' representation
      -> String
    showHelper n (File name)     = fileIndent n <> name
    showHelper n (Dir name tree) =
      dirIndent n <>
      name        <>
      concatMap (showHelper (n + 4)) tree

    spaces
      :: Int -- ^ number of spaces
      -> String
    spaces n = replicate n ' '

    fileIndent
      :: Int
      -> String
    fileIndent n = '\n' : spaces n <> "* "

    dirIndent
      :: Int
      -> String
    dirIndent n = '\n' : spaces n <> "- "


-- | Reads structure of filesystem with root at given path into virtual 'FS'.
-- @ Throws error if specified path is not point to directory.
getDirectory
  :: FilePath -- ^ root directory filepath
  -> IO FS    -- ^ virtual 'FS' representation
getDirectory root = do
  fs <- getFileSystemTree root
  case fs of
    File _ -> throwIO $ userError "Given path is not point to directory"
    _      -> return fs

-- | Reads structure of filesystem with root at given path into virtual 'FS'.
getFileSystemTree
  :: FilePath -- ^ root directory filepath
  -> IO FS    -- ^ virtual 'FS' representation
getFileSystemTree path = do
  let name = takeFileName path
  isDir <- doesDirectoryExist path
  if isDir
  then do
    subtree  <- listDirectory $ path
    contents <- traverse (getFileSystemTree . (path </>)) subtree
    pure $ Dir
           { _name     = name
           , _contents = contents
           }
  else pure $ File
              { _name = name
              }
