module FS.FSExample
  ( -- * Functions
    defaultFS
  , execVirtualFS

    -- * FileSystems
  , a_folder                -- I know that it's not Style Guide prefered naming
  , a_b_folders             -- but in this case i think it's more readable
  , a_b_touch               -- and understandable way to name test file systems
  , a_b_c_d_nested_folders
  , c_d_nested_folders
  , a_file_write

    -- * Folders
  , aFolder
  , bFolder
  , cFolder
  , dFolder

    -- * Files
  , aFile
  , bFile
  , cFile
  , dFile
  ) where

import System.Directory           (emptyPermissions)
import System.FilePath.Posix      ((</>))
import Control.Monad.Trans.Reader (runReaderT)
import Data.IORef                 (IORef, newIORef)
import Command.Handler            (defaultFS)
import FileSystem.VirtualFS
import FileSystem.FSActions
import FileSystem.FSEnviroment
import FileSystem.FSActionsVirtual()
import Utils.Time


execVirtualFS
  :: VirtualFS ()
  -> IO (IORef VirtualFSEnviroment)
execVirtualFS actions = do
  ref <- newIORef (VirtualFSEnviroment "~" "~" defaultFS)
  runReaderT actions ref
  return ref


emptyFolder
  :: String     -- ^ folder name
  -> FileSystem -- ^ Created folder
emptyFolder name = Folder
  FolderInfo
    { folderName        = name
    , folderPath        = name -- ^ Would be changed
    , folderTree        = []
    , folderSize        = 0
    , folderTreeCount   = 0
    , folderPermissions = emptyPermissions
    }

emptyFile
  :: String
  -> FileSystem
emptyFile = createFile []

createFile
  :: String     -- ^ content
  -> String     -- ^ file name
  -> FileSystem -- ^ Created file
createFile content name = File
  FileInfo
    { fileName        = name ++ ".txt"
    , filePath        = name ++ ".txt" -- ^ Would be changed
    , fileContent     = content
    , filePermissions = emptyPermissions
    , fileSize        = fromIntegral $ length content
    , fileUpdateTime  = createZeroDay
    }

addFilesToFolder
  :: FileSystem
  -> [FileSystem]
  -> FileSystem
addFilesToFolder (File _) _ = error "Please provide folder instead of file"
addFilesToFolder (Folder folder) fls =
  Folder $ folder { folderTreeCount = length tree
                  , folderTree      = tree
                  }
  where
    rootPath
      :: FilePath
    rootPath = folderPath folder

    setPath
      :: FilePath
      -> FileSystem
      -> FileSystem
    setPath p (File   file  ) = File $
      file { filePath = p </> (fileName file) }
    setPath p (Folder folder) = Folder $
      folder { folderPath = p </> (folderName folder) }

    tree
      :: [FileSystem]
    tree = map (setPath rootPath) fls


-- Simple file systems block

aFolder
  :: FileSystem
aFolder = emptyFolder "a"

bFolder
  :: FileSystem
bFolder = emptyFolder "b"

cFolder
  :: FileSystem
cFolder = emptyFolder "c"

dFolder
  :: FileSystem
dFolder = emptyFolder "d"

aFile
  :: FileSystem
aFile = emptyFile "a"

bFile
  :: FileSystem
bFile = emptyFile "b"

cFile
  :: FileSystem
cFile = emptyFile "c"

dFile
  :: FileSystem
dFile = emptyFile "d"


updateFS
  :: FileSystem
  -> FileSystem
updateFS fs = recursiveUpdate (fsPath fs) fs where
  recursiveUpdate
    :: FilePath   -- ^ root path
    -> FileSystem -- ^ root fs
    -> FileSystem
  recursiveUpdate p (File   file  ) = File $ file { filePath = p }
  recursiveUpdate p (Folder folder) = Folder $
    folder { folderTree = tree
           , folderSize = size
           }
    where
      tree
        :: [FileSystem]
      tree = map
        (\fs -> recursiveUpdate (p </> fsName fs) fs) (folderTree folder)

      size
        :: Integer
      size = sum $ map fsSize tree

-- File systems functions block
addFiles
  :: [FileSystem]
  -> FileSystem
addFiles = updateFS . addFilesToFolder defaultFS


addNestedFolders
  :: [FileSystem]
  -> FileSystem
addNestedFolders fls = recursiveAdd "~" defaultFS fls
  where
    recursiveAdd
      :: FilePath     -- ^ root path
      -> FileSystem   -- ^ root fs
      -> [FileSystem] -- ^ fs subtree
      -> FileSystem   -- ^ result
    recursiveAdd _ _ [] = error "Please provide non-empty list"
    recursiveAdd p (Folder folder) ((Folder child) : []) = Folder $
      folder
        { folderTree =
            [ Folder $ child { folderPath = p </> folderName child }
            ]
        , folderTreeCount = 1
        , folderPath = p
        }
    recursiveAdd p (Folder folder) (fld@(Folder child) : xs) = Folder $
      folder
        { folderTree =
            [ recursiveAdd (p </> folderName child) fld xs
            ]
        , folderTreeCount = 1
        , folderPath = p
        }

    recursiveAdd _ _ _ = error "Please provide files only"


-- File system examples block
a_folder = addFiles [aFolder]

a_b_folders
  :: FileSystem
a_b_folders = addFiles [aFolder, bFolder]

c_d_nested_folders = addNestedFolders [cFolder, dFolder]

a_b_c_d_nested_folders
  :: FileSystem
a_b_c_d_nested_folders = addNestedFolders
  [ aFolder
  , bFolder
  , cFolder
  , dFolder
  ]

a_b_touch
  :: FileSystem
a_b_touch = addFiles [addFilesToFolder aFolder [bFile]]

a_file_write = addFiles
  [ createFile "I love haskell" "a" ]
