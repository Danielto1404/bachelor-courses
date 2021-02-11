module FileSystem.FSActions
  ( -- * Type classes
    FSActions

    -- * File system main commands.
  , exit
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
  , find
  , write

  -- * File system supplementary functions.
  , logMessage
  , logException
  , logFileName
  , logFolderName
  , logPath
  , getCurrentDirectory
  , getRootDirectory
  , setCurrentDirectory
  , directoryFiles
  , createDirectory
  , doesFileExist
  , doesDirectoryExist
  , readFromFile
  , writeToFile
  , removeFile
  , removeDirectory
  , findAllFiles
  , getPermissions
  , getSize
  , fileModificationTime
  ) where

import qualified System.Directory as Dir

import Data.Time.Clock          (UTCTime)
import System.FilePath.Posix    (takeExtension, (</>))
import Control.Exception        (throw)
import Control.Monad            (replicateM_)

import FileSystem.FSException
import Utils.Path

-- | Type class representing File system commands
class (Monad m) => FSActions m where

  -- | Terminates file system action
  exit
    :: m ()
  exit = logMessage "TERMINATING.."

  -- | Shows help usage message
  help
    :: m ()
  help = logMessage "Use --help to see usage message."

  -- | Clears interaction terminal.
  clear
    :: m ()
  clear = replicateM_ 100 (logMessage "")

  -- | Shows current directory of file system
  -- @See 'getCurrentDirectory'
  pwd
    :: m ()
  pwd = do
    currentDirectory <- getCurrentDirectory
    logPath currentDirectory

  -- | Shows contents of current directory output same as 'ls'
  dir
    :: m ()
  dir = getCurrentDirectory >>= ls

  -- | Prints contents of given directory 'FilePath'
  -- @See 'showFolderFiles'
  -- * 'DirectoryExpected'
  --    Command for file 'FilePath'
  -- * 'DirectoryDoesNotExists'
  --    Command for non-existent 'FilePath'
  ls
    :: FilePath
    -> m ()
  ls path = do
    absolutePath <- searchPath path
    fileTypeResolver absolutePath
                     (throw DirectoryExpected)
                     (directoryFiles absolutePath
                        >>= showFolderFiles absolutePath)
                     (throw DirectoryDoesNotExists)

  -- | Changes directory to given directory 'FilePath'
  -- @See 'setCurrentDirectory'
  -- * 'DirectoryExpected'
  --    Command for file 'FilePath'
  -- * 'DirectoryDoesNotExists'
  --    Command for non-existent 'FilePath'
  cd
    :: FilePath
    -> m ()
  cd path = do
    absolutePath <- searchPath path
    fileTypeResolver absolutePath
                     (throw DirectoryExpected)
                     (setCurrentDirectory absolutePath)
                     (throw DirectoryDoesNotExists)

  -- | Prints information at given 'FilePath'
  -- @See 'showFileInfo' for file path
  -- @See 'showFolderInfo' for directory path
  -- * 'DoesNotExists'
  --    Command for non-existent 'FilePath'
  info
    :: FilePath
    -> m ()
  info path = do
    absolutePath <- searchPath path
    fileTypeResolver absolutePath
                     (showFileInfo absolutePath)
                     (showFolderInfo absolutePath)
                     (throw DoesNotExists)

  -- | Prints file contents at given file 'FilePath'
  -- @See 'readFromFile', 'logMessage'
  -- * 'FileExpected'
  --    Command for directory 'FilePath'
  -- * 'FileDoesNotExists'
  --    Command for non-existent 'FilePath'
  cat
    :: FilePath
    -> m ()
  cat path = do
    absolutePath <- searchPath path
    fileTypeResolver absolutePath
                     (readFromFile absolutePath >>= logMessage)
                     (throw FileExpected)
                     (throw FileDoesNotExists)

  -- | Creates empty file at given 'FilePath'
  -- @See 'writeToFile'
  -- * 'FileAlreadyExists'
  --    Command for already existing file 'FilePath'
  -- * 'DirectoryAlreadyExists'
  --    Command for already existing directory 'FilePath'
  touch
    :: FilePath
    -> m ()
  touch path = do
    absolutePath <- modifyPath path
    fileTypeResolver absolutePath
                     (throw FileAlreadyExists)
                     (throw DirectoryAlreadyExists)
                     (writeToFile absolutePath [])

  -- | Creates empty directory at given 'FilePath'
  -- @See 'createDirectory'
  -- * 'FileAlreadyExists'
  --    Command for already existing file 'FilePath'
  -- * 'DirectoryAlreadyExists'
  --    Command for already existing directory 'FilePath'
  mkdir
    :: FilePath
    -> m ()
  mkdir path = do
    absolutePath <- modifyPath path
    fileTypeResolver absolutePath
                     (throw FileAlreadyExists)
                     (throw DirectoryAlreadyExists)
                     (createDirectory absolutePath)

  -- | Removes element at given 'FilePath'
  -- @See 'removeFile' for file path
  -- @See 'removeDirectory' for directory path
  -- * 'DoesNotExists'
  --    Command for non-existent 'FilePath'
  rm
    :: FilePath
    -> m ()
  rm path = do
    absolutePath <- modifyPath path
    fileTypeResolver absolutePath
                     (removeFile absolutePath)
                     (removeDirectory absolutePath)
                     (throw DoesNotExists)

  -- | Finds all files with given name at current directory
  -- and subdirectories of current directory (extension sensitive)
  -- * 'DirectoryExpected'
  --    Command for already existing file path.
  -- * 'DirectoryDoesNotExists'
  --    Command for non-existent directory path.
  find
    :: String
    -> m ()
  find name = do
    path <- getCurrentDirectory
    fileTypeResolver path
                     (throw DirectoryExpected)
                     (logFiles path)
                     (throw DirectoryDoesNotExists)
    where
      logFiles
        :: (FSActions m)
        => FilePath
        -> m ()
      logFiles absolutePath = do
        files   <- findAllFiles absolutePath
        let  fs =  filter ((== name) . extractName) files
        case fs of
          [] -> logException "No files found."
          _  -> showFiles fs

  -- | Writes given contents to file at given 'FilePath' (rewrite file)
  -- @See writeToFile
  -- * 'FileExpected'
  --    Command for already existing directory path
  -- * 'FileDoesNotExists'
  --    Command for non-existent 'FilePath'
  write
    :: FilePath
    -> String
    -> m ()
  write path contents = do
    absolutePath <- modifyPath path
    fileTypeResolver absolutePath
                     (writeToFile absolutePath contents)
                     (throw FileExpected)
                     (throw FileDoesNotExists)

  -- | Converts given 'FilePath' to absolute ("../../.." included)
  -- @See 'searchingAbsolutePath'
  searchPath
    :: FilePath
    -> m FilePath
  searchPath = makeAbsolute searchingAbsolutePath

  -- | Converts given 'FilePath' to absolute ("../../.." not included)
  -- @See 'modifyingAbsolutePath'
  modifyPath
    :: FilePath
    -> m FilePath
  modifyPath = makeAbsolute modifyingAbsolutePath

  -- | Takes 'FilePath' transformation function

  makeAbsolute
    :: (FilePath -> FilePath -> FilePath)
    -> FilePath
    -> m FilePath
  makeAbsolute transform path = do
    root             <- getRootDirectory
    currentDirectory <- getCurrentDirectory
    let absolute     = transform currentDirectory path
    let isSubPath    = absolute `isSubPathOf` root
    let isValid      = absolute /= "."
    case (isValid, isSubPath) of
      (True, True) -> return absolute
      _            -> throw ImpossibleToGoOutsideRoot



-- Supplementary functions.

  -- | Logs given string
  logMessage
    :: String
    -> m ()

  -- | Logs given string exception (Used for colorized output)
  logException
    :: String
    -> m ()

  -- | Logs given file name (Used for colorized output)
  logFileName
    :: String
    -> m ()

  -- | Logs given folder name (Used for colorized output)
  logFolderName
    :: String
    -> m ()

  -- | Logs given 'FilePath' (Used for colorized output)
  logPath
    :: FilePath
    -> m ()

  -- | Returns current directory of file manager
  getCurrentDirectory
    :: m FilePath

  -- | Returns root directory of file manager
  getRootDirectory
    :: m FilePath

   -- | Sets current directory with given 'FilePath'
  setCurrentDirectory
    :: FilePath
    -> m ()

  -- | Returns list of 'FilePath'es for given directory 'FilePath'
  directoryFiles
    :: FilePath
    -> m [FilePath]

  -- | Creates directory at given 'FilePath'
  createDirectory
    :: FilePath
    -> m ()

  -- | Returns 'Bool' value indicating wheather given 'FilePath'
  -- is file path
  doesFileExist
    :: FilePath
    -> m Bool

  -- | Returns 'Bool' value indicating wheather given 'FilePath'
  -- is directory path
  doesDirectoryExist
    :: FilePath
    -> m Bool

  -- | Returns contents for file at given 'FilePath'
  readFromFile
    :: FilePath
    -> m String

  -- | Writes contents for given file 'FilePath'
  writeToFile
    :: FilePath
    -> String
    -> m ()

  -- | Removes file at given 'FilePath'
  removeFile
    :: FilePath
    -> m ()

  -- | Removes directory at given 'FilePath'
  removeDirectory
    :: FilePath
    -> m ()

  -- | Returns list of all file pathes with given root directory
  findAllFiles
    :: FilePath
    -> m [FilePath]

  -- | Returns 'Permissions' for element at given 'FilePath'
  getPermissions
    :: FilePath
    -> m Dir.Permissions

  -- | Returns 'Integer' for element at given 'FilePath'
  getSize
    :: FilePath
    -> m Integer

  -- | Returns 'UTCTime' for file at given 'FilePath'
  fileModificationTime
    :: FilePath
    -> m UTCTime

  -- | Takes 'FilePath', file action, directory action, and other action
  -- | Resolves given 'FilePath' and starts corresponding action
  fileTypeResolver
    :: FilePath
    -> m a
    -> m a
    -> m a
    -> m a
  fileTypeResolver path fileAction directoryAction otherAction = do
    isFile      <- doesFileExist      path
    isDirectory <- doesDirectoryExist path
    case isFile of
      True  -> fileAction
      False -> case isDirectory of
                 True  -> directoryAction
                 False -> otherAction


-- | Take directory 'FilePath' and list of directory content names
-- | Logs them via 'logDirectoryName', 'logFileName'
showFolderFiles
  :: (FSActions m)
  => FilePath
  -> [FilePath]
  -> m ()
showFolderFiles root = (logDirectoryName >>) . mapM_ logFSName
  where
    logDirectoryName
      :: (FSActions m)
      => m ()
    logDirectoryName = logMessage ("Folder: " ++ extractName root)

    logFSName
      :: (FSActions m)
      => FilePath
      -> m ()
    logFSName name = fileTypeResolver (root </> name)
                       (logFileName   $ "  * " ++ name)
                       (logFolderName $ "  - " ++ name)
                       (logException "  [Unknown file type]")

-- | Takes file 'FilePath' and logs file info:
-- * file path
-- * file extension
-- * file permissions
-- * file size
-- * file update time
-- @See 'showPermissions', 'extractName', 'getPermissions', 'takeExtension'
showFileInfo
  :: (FSActions m)
  => FilePath
  -> m ()
showFileInfo path = do
  let name      =  extractName          path
  let extension =  takeExtension        path
  permissions   <- getPermissions       path
  updateTime    <- fileModificationTime path
  size          <- getSize              path
  logMessage $ nlConcat [
      "File:          " ++ name
    , "  path:        " ++ path
    , "  permissions: " ++ (showPermissions permissions 20)
    , "  size:        " ++ (show size) ++ " bytes"
    , "  extension:   " ++ if extension /= [] then extension else "No extension"
    , "  update time: " ++ (show updateTime)
    ]

-- | Takes list of 'FilePath'es and logs them with 'logFileName' function
showFiles
  :: (FSActions m)
  => [FilePath]
  -> m ()
showFiles = mapM_ (\path -> logFileName $ " *  " ++ path)

-- | Takes directory 'FilePath' and logs directory info:
-- * directory path
-- * directory contents
-- * directory permissions
-- * directory size
-- * amount of directory files
-- @See 'showPermissions', 'extractName', 'getPermissions', 'directoryFiles'
showFolderInfo
  :: (FSActions m)
  => FilePath
  -> m ()
showFolderInfo path = do
  let name    =  extractName    path
  permissions <- getPermissions path
  elements    <- directoryFiles path
  size        <- getSize        path
  logMessage $ nlConcat [
      "Folder:            " ++ name
    , "  path:            " ++ path
    , "  permissions:     " ++ (showPermissions permissions 20)
    , "  size:            " ++ (show size) ++ " bytes"
    , "  amount of files: " ++ (show $ length elements)
    ]
