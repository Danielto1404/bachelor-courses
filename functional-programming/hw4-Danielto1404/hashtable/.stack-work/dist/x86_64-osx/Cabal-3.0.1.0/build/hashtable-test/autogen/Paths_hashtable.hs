{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
module Paths_hashtable (
    version,
    getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

#if defined(VERSION_base)

#if MIN_VERSION_base(4,0,0)
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#else
catchIO :: IO a -> (Exception.Exception -> IO a) -> IO a
#endif

#else
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#endif
catchIO = Exception.catch

version :: Version
version = Version [0,1,0,0] []
bindir, libdir, dynlibdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/hashtable/.stack-work/install/x86_64-osx/8f860404649a74cdb138ebde489fbf10f69ea7393e34747b82da955faa972fb6/8.8.3/bin"
libdir     = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/hashtable/.stack-work/install/x86_64-osx/8f860404649a74cdb138ebde489fbf10f69ea7393e34747b82da955faa972fb6/8.8.3/lib/x86_64-osx-ghc-8.8.3/hashtable-0.1.0.0-3A0d8jyJVop17nlsUJrgm6-hashtable-test"
dynlibdir  = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/hashtable/.stack-work/install/x86_64-osx/8f860404649a74cdb138ebde489fbf10f69ea7393e34747b82da955faa972fb6/8.8.3/lib/x86_64-osx-ghc-8.8.3"
datadir    = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/hashtable/.stack-work/install/x86_64-osx/8f860404649a74cdb138ebde489fbf10f69ea7393e34747b82da955faa972fb6/8.8.3/share/x86_64-osx-ghc-8.8.3/hashtable-0.1.0.0"
libexecdir = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/hashtable/.stack-work/install/x86_64-osx/8f860404649a74cdb138ebde489fbf10f69ea7393e34747b82da955faa972fb6/8.8.3/libexec/x86_64-osx-ghc-8.8.3/hashtable-0.1.0.0"
sysconfdir = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/hashtable/.stack-work/install/x86_64-osx/8f860404649a74cdb138ebde489fbf10f69ea7393e34747b82da955faa972fb6/8.8.3/etc"

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "hashtable_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "hashtable_libdir") (\_ -> return libdir)
getDynLibDir = catchIO (getEnv "hashtable_dynlibdir") (\_ -> return dynlibdir)
getDataDir = catchIO (getEnv "hashtable_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "hashtable_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "hashtable_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
