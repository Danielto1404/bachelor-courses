module Command.Command
  ( -- * Constructors
    Command (..)
  ) where

-- | Data type represents all types of file system commands.
-- See 'FSActions' for get specified information about commands.
data Command =
    EXIT                    -- | terminate file manager constructor
  | HELP                    -- | commands help constructor
  | DIR                     -- | show current directory contents constructor
  | CLEAR                   -- | clear terminal window constructor
  | PWD                     -- | show current directory path constructor
  | LS    FilePath          -- | show directory contents constructor
  | CD    FilePath          -- | change directory constructor
  | INFO  FilePath          -- | element information constructor
  | CAT   FilePath          -- | show file contents constructor
  | TOUCH FilePath          -- | create file constructor
  | MKDIR FilePath          -- | create directory constructor
  | RM    FilePath          -- | remove element constructor
  | FIND  String            -- | find element constructor
  | WRITE FilePath [String] -- | write file constructor
  | INVALID                 -- | invalid command constructor
  deriving (Show, Eq)
