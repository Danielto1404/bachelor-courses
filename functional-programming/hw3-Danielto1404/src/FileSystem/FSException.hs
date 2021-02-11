{-# LANGUAGE InstanceSigs #-}

module FileSystem.FSException
  ( -- * Constructors
    FSException (..)
  ) where

import Control.Exception (Exception)

-- | Data type represents all types of file system errors.
-- @See show instance for getting more information about 'FSException' types.
data FSException
  = DoesNotExists
  | AlreadyExists
  | FileDoesNotExists
  | DirectoryDoesNotExists
  | FileExpected
  | DirectoryExpected
  | FileAlreadyExists
  | UnableToDeleteCurrentDirectory
  | DirectoryAlreadyExists
  | RootDirectoryDeletionDenied
  | RootDirectoryOverridingDenied
  | EmptyPath
  | InvalidPath
  | ImpossibleToGoOutsideRoot

-- | 'FSException' type conforms to 'Show' type class.
instance Show FSException where
  show
    :: FSException
    -> String
  show DoesNotExists =
    "Error: No such file or directory."
  show AlreadyExists =
    "Error: File or directory already exists."
  show FileDoesNotExists =
    "Error: No such file."
  show DirectoryDoesNotExists =
    "Error: No such directory."
  show FileExpected =
    "Error: Please give file path instead of directory path."
  show DirectoryExpected =
    "Error: Please give directory path instead of file path."
  show FileAlreadyExists =
    "Error: File with the same name already exists."
  show UnableToDeleteCurrentDirectory =
    "Error: Unable to delete current directory."
  show DirectoryAlreadyExists =
    "Error: Directory with the same name already exists."
  show RootDirectoryDeletionDenied =
    "Error: Unable to delete root directory. Permission denied."
  show RootDirectoryOverridingDenied =
    "Error: Unable to override root directory. Permission denied."
  show EmptyPath =
    "Error: given path is empty."
  show InvalidPath =
    "Error: Path is invalid."
  show ImpossibleToGoOutsideRoot =
    "Error: Impossible to got outside root directory."

-- | 'FSException' type conforms to 'Exception' class type.
instance Exception FSException where
