module Parser.CommandParser
  ( -- * Functions
    commandParser
  ) where

import Options.Applicative (Parser, command, info, progDesc,
                            argument, str, metavar, hsubparser, many)

import Command.Command


-- | Returs parser for 'Command' representation with --help support.
commandParser
  :: Parser Command
commandParser = hsubparser $
     command
       "exit"
       (info (pure EXIT) (progDesc "Terminates programm"))
  <> command
       "help"
       (info (pure HELP) (progDesc "Usage guide"))
  <> command
       "clear"
       (info (pure CLEAR) (progDesc "Clears terminal"))
  <> command
       "pwd"
        (info (pure PWD) (progDesc "Shows path of current directory"))
  <> command
       "dir"
       (info (pure DIR) (progDesc "Shows the content of current directory"))
  <> command
       "ls"
       (info (LS <$> argument str (metavar "FILE"))
             (progDesc "Shows the content of given directory")
       )
  <> command
       "cd"
       (info (CD <$> argument str (metavar "DIRECTORY"))
             (progDesc "Goes to given directory")
       )
  <> command
       "info"
       (info (INFO <$> argument str (metavar "FILE | DIRECTORY"))
             (progDesc $ concat [
                  "Shows information about file or directory:"
                , "permissions, size, name, extension, directory contents"
                ]
             )
       )
  <> command
       "cat"
       (info (CAT <$> argument str (metavar "FILE"))
             (progDesc "Shows the content of file")
       )
  <> command
       "touch"
       (info (TOUCH <$> argument str (metavar "FILE"))
             (progDesc "Creates file")
       )
  <> command
       "mkdir"
       (info (MKDIR <$> argument str (metavar "DIRECTORY"))
             (progDesc "Creates directory")
       )
  <> command
       "rm"
       (info (RM <$> argument str (metavar "FILE | DIRECTORY"))
             (progDesc "Removes file or directory")
       )
  <> command
       "find"
       (info (FIND <$> argument str (metavar "FILE_NAME"))
             (progDesc $ concat [
                  "Finds all files in the current directory"
                , "and subdirectories of current directory"
                , "that have given name"
                ]
             )
       )
  <> command
       "write"
       (info (WRITE <$> argument str (metavar "FILE") <*>
                        many (argument str (metavar "TEXT")))
             (progDesc "Writes text to given file")
       )
