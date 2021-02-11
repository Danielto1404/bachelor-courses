module Utils.RainbowText
  ( -- * Functions
    showCurrentDirectory
  , showException
  , showYellow
  , showMagenta
  , showCyan
  , showText
  , showPath
  , showFile
  , showFolder
  ) where

import Data.Text (pack)
import Rainbow   (Chunk, chunk, putChunks, putChunkLn, fore, bold
                 , red, green, yellow, magenta, cyan, white
                 )

-- | Converts given 'String' to 'Chunk' for colorized text representation.
strChunk
  :: String -- ^ string to convert
  -> Chunk
strChunk = chunk . pack

-- | Takes list of pairs ('String', 'Chunk' -> 'Chunk') and for each pair
-- transforms given 'String' (first element of pair) with color representation
-- function (second element of pair).
showRainbowText
  :: [(String, Chunk -> Chunk)] -- ^ list of strings and transform functions
  -> IO ()
showRainbowText = putChunks .
  map (\(text, transform) -> transform $ strChunk text)

-- | Takes name of enviroment and current directory and shows them on console
-- using 'green' color for enviroment and 'manenta' color for current directory.
showCurrentDirectory
  :: String   -- ^ enviroment name
  -> FilePath -- ^ current directory file path
  -> IO ()
showCurrentDirectory env path = showRainbowText
  [ ("\n" , bold . fore green)
  , ("("  , bold . fore green)
  , (env  , bold . fore green)
  , (")"  , bold . fore green)
  , (": " , bold . fore green)
  , (path , fore magenta)
  , ("\n" , bold . fore green)
  , (">> ", bold . fore white)
  ]

-- | Takes a color representation function and text and shows text transformed
-- with given color representation function.
showText
  :: (Chunk -> Chunk) -- ^ text transformation function
  -> String           -- ^ text to show
  -> IO ()
showText transform = putChunkLn . transform . strChunk

-- | Shows given 'String' with 'red' color.
showException
  :: String -- ^ string exception to show
  -> IO ()
showException = showText (fore red)

-- | Shows given 'String' with 'bold' 'magenta' color.
showPath
  :: FilePath -- ^ path to show
  -> IO ()
showPath = showText (bold . fore magenta)

-- | See 'showCyan'
showFile
  :: FilePath -- ^ file path
  -> IO ()
showFile = showCyan

-- | See 'showYellow'
showFolder
  :: FilePath -- ^ folder path
  -> IO ()
showFolder = showYellow

-- | Shows given 'String' with 'yellow' color.
showYellow
  :: String -- ^ text to show
  -> IO ()
showYellow = showText (fore yellow)

-- | Shows given 'String' with 'magenta' color.
showMagenta
  :: String -- ^ text to show
  -> IO ()
showMagenta = showText (fore magenta)

-- | Shows given 'String' with 'cyan' color.
showCyan
  :: String -- ^ text to show
  -> IO ()
showCyan = showText (fore cyan)
