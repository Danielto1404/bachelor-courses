module Parser.StartOptionParser
  ( -- * Functions
    startOptionParser
  ) where

import Options.Applicative (Parser, command, info, progDesc,
                            argument, str, metavar, hsubparser)

import Command.StartOption

-- | Returs parser for 'StartOption' representation with --help support.
startOptionParser
  :: Parser StartOption
startOptionParser = hsubparser $
     command
       "virtual"
       (info (pure VIRTUAL)
             (progDesc
             "Starts emulation of virtual file system manager with root at ~"
             )
       )
  <> command
       "exit"
       (info (pure EXIT)
             (progDesc "Terminates application.")
       )
  <> command
       "real"
       (info (REAL <$> argument str (metavar "DIRECTORY"))
             (progDesc "Starts file manager which works with real file system")
       )
