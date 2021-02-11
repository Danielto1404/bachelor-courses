module FileSystem.VirtualFS
  ( -- * Constructors
    FileInfo (..)
  , FolderInfo (..)
  , FileSystem (..)

     -- * Aliases
  , ErrorOrFS

     -- * Functions
  , fsFiles
  , fsName
  , fsSize
  , fsPath
  , isFile
  , findElement
  , removeElement
  , createElement
  , defaultFile
  , defaultFolder
  ) where

import qualified Data.List as LS

import Data.Either                (fromRight)
import System.Directory           (Permissions, emptyPermissions)
import Data.Time.Clock            (UTCTime)
import System.FilePath.Posix      ((</>), splitDirectories)

import FileSystem.FSException

-- | File data structure
data FileInfo =
  FileInfo
    { filePath        :: FilePath    -- ^ Absolute file path from root
    , fileName        :: FilePath    -- ^ Short file name representation
    , filePermissions :: Permissions -- ^ File 'Permissions' representation
    , fileUpdateTime  :: UTCTime     -- ^ file modification time representation
    , fileContent     :: String      -- ^ File contents representation
    , fileSize        :: Integer     -- ^ File size in bytes representation
    } deriving (Show, Eq)

-- | Folder data structure
data FolderInfo =
  FolderInfo
    { folderPath        :: FilePath     -- ^ Absolute path from root
    , folderName        :: String       -- ^ Short folder name representation
    , folderTree        :: [FileSystem] -- ^ Folder contents representation
    , folderTreeCount   :: Int          -- ^ Amount of folder content elements
    , folderSize        :: Integer      -- ^ Folder size in bytes representation
    , folderPermissions :: Permissions  -- ^ Folder 'Permission' representation
    } deriving (Show, Eq)

-- | File system data structure
data FileSystem
  = File   FileInfo   -- | 'FileInfo' constructor
  | Folder FolderInfo -- | 'FolderInfo' constructor
  deriving (Show, Eq)

-- | Type for representing 'FSException' or 'FileSystem'
type ErrorOrFS = Either FSException FileSystem

-- | Returns absolute path from root for given 'FileSystem' element
fsPath
  :: FileSystem -- ^ file system element
  -> FilePath   -- ^ file system element absolute path from root
fsPath (File   FileInfo   { filePath   = path }) = path
fsPath (Folder FolderInfo { folderPath = path }) = path

-- | Returns short name for given 'FileSystem' element
fsName
  :: FileSystem -- ^ file system element
  -> FilePath   -- ^ file system element short name
fsName (File   FileInfo   { fileName   = name }) = name
fsName (Folder FolderInfo { folderName = name }) = name

-- | Returns size for given 'FileSystem' element
fsSize
  :: FileSystem -- ^ files system element
  -> Integer    -- ^ file system element size in bytes
fsSize (File   FileInfo   { fileSize   = size }) = size
fsSize (Folder FolderInfo { folderSize = size }) = size

-- | Returns 'Bool' value indicating wheather given 'FileSystem' element is file
isFile
  :: FileSystem
  -> Bool
isFile (File _) = True
isFile _        = False

-- | Returns empty folder with given name at given 'FileSystem' element subtree
defaultFolder
  :: FileSystem -- ^ root file system
  -> FilePath   -- ^ folder name
  -> FileSystem -- ^ updated file system
defaultFolder fs name = Folder
  FolderInfo
    { folderPath        = fsPath fs </> name
    , folderName        = name
    , folderTree        = []
    , folderTreeCount   = 0
    , folderSize        = 0
    , folderPermissions = emptyPermissions
    }

-- | Returns empty file with given name, contents, time
-- at given 'FileSystem' element subtree
defaultFile
  :: String      -- ^ contents
  -> UTCTime     -- ^ modification time
  -> FileSystem  -- ^ root file system
  -> FilePath    -- ^ file name
  -> FileSystem  -- ^ updated file system tree
defaultFile contents time fs name = File
  FileInfo
    { filePath        = fsPath fs </> name
    , fileName        = name
    , fileContent     = contents
    , filePermissions = emptyPermissions
    , fileSize        = fromIntegral $ length contents
    , fileUpdateTime  = time
    }

-- | Returns list of files in given 'FileSystem' element subtree
fsFiles
  :: FileSystem   -- ^ root file system
  -> [FileSystem] -- ^ file system files
fsFiles file@(File _)                             = [file]
fsFiles (Folder FolderInfo { folderTree = tree }) = concatMap fsFiles tree

-- | Takes action function and runs it in case first element in given
-- 'FilePath' is same as 'fsName' of given 'FileSystem' element
-- * EmptyPath
--   In case given 'FilePath' is empty
-- * InvalidPath
--   In case given 'FilePath' first element not equal to 'fsName' for
--   given 'FileSystem' element
validatePath
  :: (FileSystem -> [FilePath]-> ErrorOrFS) -- ^ action for correct path
  -> FilePath                               -- ^ action file path
  -> FileSystem                             -- ^ root file system
  -> ErrorOrFS                              -- ^ updated file system or error
validatePath action path fs = case (splitDirectories path) of
  []                              -> Left EmptyPath
  (dir : dirs) | dir /= fsName fs -> Left InvalidPath
               | otherwise        -> action fs dirs

-- | Finds 'FileSystem' element for given relative 'FilePath' from
-- given 'FileSystem' element
findElement
  :: FilePath   -- ^ file path to find
  -> FileSystem -- ^ root file system
  -> ErrorOrFS  -- ^ found file system or 'FSException'
findElement = validatePath findRecursive
  where
    findRecursive
      :: FileSystem -- ^ current file system subtree
      -> [FilePath] -- ^ splited by path separator finding path
      -> ErrorOrFS  -- ^ found file system element or 'FSException'
    findRecursive fs              []           = Right fs
    findRecursive (File _)        _            = Left DirectoryDoesNotExists
    findRecursive (Folder folder) (dir : dirs) = do
      case LS.find ((== dir) . fsName) $ folderTree folder of
        Nothing      -> Left DoesNotExists
        (Just found) -> case dirs of
                          [] -> Right found
                          _  -> findRecursive found dirs

-- | Takes 'Bool' value, directory 'FileSystem' element,
-- 'FileSystem' element to update and removes updating element if given
-- 'Bool' value is true (needsRemoving)
-- Removes found element if needed and updates
-- directory contents, amount of directory contents and directory size
updateFolder
  :: Bool        -- ^ needs removing indicator
  -> FileSystem  -- ^ root file system
  -> FileSystem  -- ^ element to find
  -> ErrorOrFS   -- ^ updated folder or 'FSException'
updateFolder _             (File _)        _ = Left DirectoryExpected
updateFolder needsRemoving (Folder folder) e = Right $ Folder
  folder
    { folderTree      = tree
    , folderSize      = size
    , folderTreeCount = cnt
    }
  where
    removed
      :: [FileSystem]
    removed = filter ((fsName e /=) . fsName) (folderTree folder)

    tree
      :: [FileSystem]
    tree = if needsRemoving
           then removed
           else e : removed

    size
      :: Integer
    size = sum $ map fsSize tree

    cnt
      :: Int
    cnt = length tree

-- | Takes 'FileSystem' element creation function and tries to create element
-- for given 'FileSystem' element subtree
createElement
  :: (FileSystem -> FilePath -> FileSystem) -- ^ element constructor
  -> FilePath                               -- ^ creation path
  -> FileSystem                             -- ^ root file system
  -> ErrorOrFS                              -- ^ updated file system or error
createElement constructor = validatePath create
  where
    create
      :: FileSystem -- ^ current root file system subtree
      -> [FilePath] -- ^ splited by path separator creation path
      -> ErrorOrFS  -- ^ updated file system or 'FSException'
    create _         []           = Left RootDirectoryOverridingDenied
    create (File _ ) _            = Left FileAlreadyExists
    create folder    (dir : dirs) = case dirs of
      [] -> case found of
              Right (Folder _) -> Left DirectoryAlreadyExists
              _                -> do
                                  let created = constructor folder dir
                                  updateFolder False folder created
      _ -> do
           let next =  fromRight defaultDirectory found
           created  <- create next dirs
           updateFolder False folder created

      where
        found
          :: ErrorOrFS
        found = findElement (fsName folder </> dir) folder

        defaultDirectory
          :: FileSystem
        defaultDirectory = defaultFolder folder dir

-- | Takes 'FilePath' for removing element and tries to find and delete
-- element from given 'FileSystem' element subtree
removeElement
  :: FilePath   -- ^ element removing path
  -> FileSystem -- ^ root file system subtree
  -> ErrorOrFS  -- ^ updated file system or 'FSException'
removeElement = validatePath remove
  where
    remove
      :: FileSystem -- ^ current root file system subtree
      -> [FilePath] -- ^ splited by path separator removing path
      -> ErrorOrFS  -- ^ updated file system or 'FSException'
    remove _        []           = Left RootDirectoryDeletionDenied
    remove (File _) _            = Left DirectoryDoesNotExists
    remove folder   (dir : dirs) = do
      found <- findElement (fsName folder </> dir) folder
      case dirs of
        [] -> updateFolder True folder found
        _  -> remove found dirs >>= updateFolder False folder
