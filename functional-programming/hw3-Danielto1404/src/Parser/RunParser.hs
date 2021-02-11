module Parser.RunParser
  ( -- * Functions
    runStartOptionParser
  , runCommandParser
  ) where

import qualified Command.Command      as CMD
import qualified Command.StartOption  as SO

import Parser.CommandParser     (commandParser)
import Parser.StartOptionParser (startOptionParser)
import Control.Exception        (catch)
import System.Exit              (ExitCode)
import Options.Applicative      (Parser, ParserInfo, prefs, showHelpOnEmpty,
                                 helper, info, execParserPure, idm,
                                 handleParseResult
                                )

-- | Takes input 'String' and returns 'SO.StartOption' if parsing on given
-- input was succeeded otherwise prints error and help message.
runStartOptionParser
  :: String             -- ^ input for parsing
  -> IO SO.StartOption  -- ^ parsed command or invalid command
runStartOptionParser = handleParser startOptionParser startOptionsParserHandler

-- | Takes input 'String' and returns 'CMD.Command' if parsing on given
-- input was succeeded otherwise prints error and help message.
runCommandParser
  :: String          -- ^ input for parsing
  -> IO CMD.Command  -- ^ parsed command or invalid command
runCommandParser = handleParser commandParser commandParserHandler

-- | If 'startOptionParser' fails returns 'SO.INVALID' commnad.
startOptionsParserHandler
  :: ExitCode
  -> IO SO.StartOption -- ^ invalid command for start option parser
startOptionsParserHandler _ = return SO.INVALID

-- | If 'commandParser' fails returns 'CMD.INVALID' commnad.
commandParserHandler
  :: ExitCode
  -> IO CMD.Command -- ^ invalid command for command parser
commandParserHandler _ = return CMD.INVALID

-- | Takes 'Parser' and returns parser that supports "--help" commnad.
helperParser
  :: Parser a      -- ^ parser
  -> ParserInfo a  -- ^ helper parser with "--help support"
helperParser parser = info (helper <*> parser) idm

-- | Handle 'execParser' if it fails. Do given handle action on error.
handleParser
  :: Parser a           -- ^ parser
  -> (ExitCode -> IO a) -- ^ exit code handler
  -> String             -- ^ input for parsing
  -> IO a               -- ^ parsed result
handleParser parser handler input = catch (execParser parser input) handler

-- | Takes parser, input string and returns result of parsing if it was
-- succeeded, otherwise throws 'ExitCode' error.
execParser
  :: Parser a -- ^ parser
  -> String   -- ^ input for parsing
  -> IO a     -- ^ parsed result or 'ExitCode'
execParser parser input = handleParseResult $
  execParserPure (prefs showHelpOnEmpty) (helperParser parser) (words input)
