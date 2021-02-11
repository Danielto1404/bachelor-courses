{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
module Paths_file_system (
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

bindir     = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/file-system/.stack-work/install/x86_64-osx/04a750352cf73641c10e848236b8cd5f9338488d96a8ffbc1a5c0bf21d48a3f7/8.8.4/bin"
libdir     = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/file-system/.stack-work/install/x86_64-osx/04a750352cf73641c10e848236b8cd5f9338488d96a8ffbc1a5c0bf21d48a3f7/8.8.4/lib/x86_64-osx-ghc-8.8.4/file-system-0.1.0.0-IXYNPyErirREnIM4K70p2b-file-system-test"
dynlibdir  = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/file-system/.stack-work/install/x86_64-osx/04a750352cf73641c10e848236b8cd5f9338488d96a8ffbc1a5c0bf21d48a3f7/8.8.4/lib/x86_64-osx-ghc-8.8.4"
datadir    = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/file-system/.stack-work/install/x86_64-osx/04a750352cf73641c10e848236b8cd5f9338488d96a8ffbc1a5c0bf21d48a3f7/8.8.4/share/x86_64-osx-ghc-8.8.4/file-system-0.1.0.0"
libexecdir = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/file-system/.stack-work/install/x86_64-osx/04a750352cf73641c10e848236b8cd5f9338488d96a8ffbc1a5c0bf21d48a3f7/8.8.4/libexec/x86_64-osx-ghc-8.8.4/file-system-0.1.0.0"
sysconfdir = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/file-system/.stack-work/install/x86_64-osx/04a750352cf73641c10e848236b8cd5f9338488d96a8ffbc1a5c0bf21d48a3f7/8.8.4/etc"

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "file_system_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "file_system_libdir") (\_ -> return libdir)
getDynLibDir = catchIO (getEnv "file_system_dynlibdir") (\_ -> return dynlibdir)
getDataDir = catchIO (getEnv "file_system_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "file_system_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "file_system_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
