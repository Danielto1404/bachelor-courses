module FSLenses
  ( -- * Functions
    _File
  , _Dir
  , name
  , contents
  , dirChildren
  , dirName
  , fileName
  , renameToRoot
  , addSuffixToRootName
  , firstSubdirectory
  , dirFileNames
  ) where

import FS

import Lens.Micro (Lens', Traversal', lens, traversed, (&), (.~), (<>~), (^.)
                  , (^..), (^?)
                  )

-- | Traverse FS if it is a file.
_File
  :: Traversal' FS FS
_File _ fs@(Dir _ _) = pure fs
_File f file         = f file

-- | Traverse FS if it is a directory.
_Dir
  :: Traversal' FS FS
_Dir _ fs@(File _) = pure fs
_Dir f directory   = f directory

-- | Get or set name of a filesystem.
name
  :: Lens' FS FilePath
name = lens _name (\fs newName -> fs { _name = newName })

-- | Traverse contents of a directory.
contents
  :: Traversal' FS [FS]
contents _ fs@(File _ ) = pure fs
contents f (Dir n tree) = (Dir n) <$> f tree

-- | Get contents of a directory as a 'List'.
dirChildren
  :: FS
  -> [FS]
dirChildren fs = fs ^. contents

-- | Returns name of a directory if 'FS' item is 'Dir',
-- Nothing otherwise.
dirName
  :: FS
  -> Maybe FilePath
dirName fs = fs ^? _Dir . name

-- | Returns name of a file if 'FS' item is 'File',
-- empty 'FilePath' otherwise.
fileName
  :: FS
  -> FilePath
fileName fs = fs ^. _File . name

-- | Change name of the root filesystem to "/".
renameToRoot
  :: FS
  -> FS
renameToRoot fs = fs & name .~ "/"

-- | Add a suffix to the name of the root filesystem.
addSuffixToRootName
  :: FilePath
  -> FS
  -> FS
addSuffixToRootName suffix fs = fs & name <>~ suffix

-- | Get the name of the first subdirectory of a directory, or Nothing, if
-- directory subtree is empty.
firstSubdirectory
  :: FS
  -> Maybe FilePath
firstSubdirectory fs = fs ^? contents . traversed . _Dir . name

-- | Get names of all files in a directory as a 'List'.
dirFileNames
  :: FS
  -> [FilePath]
dirFileNames fs = fs ^.. contents . traversed . _File . name
