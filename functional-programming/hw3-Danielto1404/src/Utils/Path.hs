module Utils.Path
  ( -- * Functions
    searchingAbsolutePath
  , modifyingAbsolutePath
  , isSubPathOf
  , getRelativeFromRoot
  , extractName
  , nlConcat
  , showPermissions
  , spaces
  ) where

import qualified Data.List as LS

import System.Directory      (Permissions(..))
import System.FilePath.Posix (takeFileName, normalise
                              , dropTrailingPathSeparator, makeRelative, (</>)
                              , splitDirectories, joinPath)

-- | Takes 'Bool' indicating wheather double dots are allowed in 'FilePath'
-- also takes root path and path for making absolute.
-- Returs absolute path from root to given path.
toAbsolute
  :: Bool     -- ^ is cd up (/../..) allowed
  -> FilePath -- ^ root path
  -> FilePath -- ^ path to modify
  -> FilePath
toAbsolute isDoubleDotAllowed root path = if isDoubleDotAllowed
                                          then normalisePath $ cdUP absolute
                                          else normalisePath absolute
  where
    root'
      :: FilePath

    root' = normalisePath root

    path'
      :: FilePath
    path' = normalisePath path

    relative
      :: FilePath
    relative = makeRelative root' path'

    absolute
      :: FilePath
    absolute = root' </> relative

-- | returns 'toAbsolute' with double dot enabled options.
-- Returs absolute path from root to given path with 'cdUP' transformation.
searchingAbsolutePath
  :: FilePath -- ^ root path
  -> FilePath -- ^ path to modify
  -> FilePath
searchingAbsolutePath = toAbsolute True

-- | returns 'toAbsolute' with double dot disabled options.
-- If absolute path contains ".." then returns empty 'FilePath' ([])
modifyingAbsolutePath
  :: FilePath -- ^ root path
  -> FilePath -- ^ path to modify
  -> FilePath
modifyingAbsolutePath root path = validate absolute where
  absolute
    :: FilePath
  absolute = toAbsolute False root path

  validate
    :: FilePath
    -> FilePath
  validate p = if LS.elem ".." (splitDirectories p)
                  then []
                  else p

-- | Takes 'FilePath' and removes unnecessary transitions
-- some-dir-1/some-dir-2/../.. === current directory
cdUP
  :: FilePath -- ^ path
  -> FilePath -- ^ updated path
cdUP []   = []
cdUP [x]  = [x]
cdUP path = combine [] (splitDirectories path) where
  combine
    :: [FilePath] -- ^ previous path names
    -> [FilePath] -- ^ current path names
    -> FilePath   -- ^ result path
  combine acc []                = joinPath $ reverse acc
  combine acc [x]               = joinPath $ reverse (x : acc)
  combine acc (_ : ".." : dirs) = cdUP $ joinPath ((reverse acc) ++ dirs)
  combine acc (dir : dirs)      = combine (dir : acc) dirs

-- | Combines normalise and dropTrailingPathSeparator
-- See 'normalise' and 'dropTrailingPathSeparator'
normalisePath
  :: FilePath -- ^ path
  -> FilePath -- ^ normalised path
normalisePath =  dropTrailingPathSeparator . normalise

-- | Takes path to check and root path and returns 'True' if given path to check
-- is subpath of given root path otherwise 'False'.
isSubPathOf
  :: FilePath -- ^ path to check
  -> FilePath -- ^ root path
  -> Bool
isSubPathOf path root = check (normalisePath path) (normalisePath root) where
  check
    :: FilePath
    -> FilePath
    -> Bool
  check _        []                   = True
  check []       _                    = False
  check (x : xs) (y : ys) | x /= y    = False
                          | otherwise = check xs ys

-- | Takes root and absolute path and extracts relative 'FilePath' from root
-- including root name.
getRelativeFromRoot
  :: FilePath -- ^ root path
  -> FilePath -- ^ path from root
  -> FilePath
getRelativeFromRoot root path = extractName root </> relative where
  root'
    :: FilePath

  root' = normalisePath root

  path'
    :: FilePath
  path' = normalisePath path

  relative
    :: FilePath
  relative = case makeRelative root' path' of
               "." -> ""
               rel -> rel

-- | Takes 'FilePath' and extracts file path element name (See 'takeFileName').
-- For "/" returns "/".
extractName
  :: FilePath -- ^ file path
  -> FilePath -- ^ extracted file name
extractName "/" = "/"
extractName p = takeFileName p

-- | Takes list of strings and joins them by new line char.
nlConcat
  :: [String] -- ^ string to join with '\n'
  -> String
nlConcat = concatMap (\s -> s ++ "\n")

-- | Takes spaces offset number and returns 'String' of
-- 'Permission' values aligned with given offset from left.
showPermissions
  :: Permissions -- ^ permissions to show
  -> Int         -- ^ indent from left in spaces
  -> String
showPermissions p n = nlConcat [
  "Permissions: "
  , spaces n ++ "  readable:   " ++ show (readable   p)
  , spaces n ++ "  writable:   " ++ show (writable   p)
  , spaces n ++ "  executable: " ++ show (executable p)
  , spaces n ++ "  searchable: " ++ show (searchable p)
  ]

-- | Takes number of spaces and returns space char
-- replicated given number of times.
spaces
  :: Int     -- ^ number of spaces
  -> String
spaces n = replicate n ' '
