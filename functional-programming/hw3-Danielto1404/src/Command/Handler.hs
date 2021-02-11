{-# LANGUAGE BlockArguments #-}
{-# LANGUAGE ScopedTypeVariables #-}

module Command.Handler
  ( -- * Functions
    execRealFS
  , execVirtualFS
  , defaultFS
  ) where

import Control.Monad.Trans.Reader (runReaderT)
import Control.Monad.IO.Class     (MonadIO, liftIO)
import Control.Monad.Catch        (MonadCatch, Handler (..), catches)
import Control.Exception          (SomeException)
import System.Directory           (emptyPermissions)
import System.IO.Error            (ioeGetErrorType)
import System.IO                  (hFlush, stdout)
import Data.IORef                 (newIORef)

import FileSystem.VirtualFS       (FileSystem (..), FolderInfo (..))
import Parser.RunParser           (runCommandParser)
import Command.Command
import FileSystem.FSActions
import FileSystem.FSActionsIO()
import FileSystem.FSActionsVirtual()
import FileSystem.FSException
import FileSystem.FSEnviroment
import Utils.RainbowText
import Utils.Path

-- | Starts infinite command prompts from user.
-- | Terminates when 'EXIT' 'Command' occured.
-- | Otherwise makes user input command and continuous interaction.
fsInteraction
  :: (FSActions m, MonadIO m, MonadCatch m)
  => String -- ^ enviroment name
  -> m ()   -- ^ file system actions
fsInteraction env = do
  dirPath <- getCurrentDirectory
  root    <- getRootDirectory
  let relative = getRelativeFromRoot root dirPath
  liftIO $ showCurrentDirectory env relative
  liftIO $ hFlush stdout
  input   <- liftIO $ getLine
  command <- liftIO $ runCommandParser input
  case command of
    EXIT -> return ()
    _    -> do
            catches (applyCommand command) errorHandlers
            fsInteraction env

-- | Returns list of all error handlers for files system exceptions.
errorHandlers
  :: (FSActions m, MonadIO m)
  => [Handler m ()] -- ^ handlers for file manager exceptions
errorHandlers = [
    Handler $ \(e :: FSException  ) -> liftIO $ showException $ show e
  , Handler $ \(e :: IOError      ) -> liftIO $ showException $ showIOError e
  , Handler $ \(e :: SomeException) -> liftIO $ showException $ show e
  ]

-- | Extracts 'String' information about 'IOError' type.
-- | See 'ioeGetErrorType'.
showIOError
  :: IOError -- ^ io error
  -> String  -- ^ error type
showIOError = ("Error: " ++) . show . ioeGetErrorType

-- | Makes file system action representing given 'Command'.
applyCommand
  :: (FSActions m, MonadIO m)
  => Command -- ^ parsed command
  -> m ()    -- ^ corresponding to parsed command file system action
applyCommand INVALID               = return ()
applyCommand HELP                  = help
applyCommand EXIT                  = exit
applyCommand CLEAR                 = clear
applyCommand PWD                   = pwd
applyCommand DIR                   = dir
applyCommand (LS    path)          = ls    path
applyCommand (CD    path)          = cd    path
applyCommand (INFO  path)          = info  path
applyCommand (CAT   path)          = cat   path
applyCommand (TOUCH path)          = touch path
applyCommand (MKDIR path)          = mkdir path
applyCommand (RM    path)          = rm    path
applyCommand (FIND  name)          = find  name
applyCommand (WRITE path contents) = write path $ unwords contents

-- | Runs real file system interaction at given root directory.
execRealFS
  :: String -- ^ file system root
  -> IO ()  -- ^ IO interaction
execRealFS root = newIORef (RealFSEnviroment root root) >>=
                  runReaderT (fsInteraction "real")

-- | Runs virtual file system interaction.
execVirtualFS
  :: IO ()  -- ^ IO interaction
execVirtualFS = newIORef (VirtualFSEnviroment "~" "~" defaultFS) >>=
                runReaderT (fsInteraction "virtual")

-- | Returns default virtual file system with root at "~".
defaultFS
  :: FileSystem
defaultFS = Folder
  FolderInfo
    { folderPath = "~"
    , folderName = "~"
    , folderPermissions = emptyPermissions
    , folderSize = 0
    , folderTreeCount = 0
    , folderTree = []
    }
