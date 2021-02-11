{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
module Paths_integration (
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

bindir     = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/integration/.stack-work/install/x86_64-osx/1b27a6cb896ce60fd505ab9dab8192899f9817c23a8def433f988c1a248e5f70/8.8.3/bin"
libdir     = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/integration/.stack-work/install/x86_64-osx/1b27a6cb896ce60fd505ab9dab8192899f9817c23a8def433f988c1a248e5f70/8.8.3/lib/x86_64-osx-ghc-8.8.3/integration-0.1.0.0-2op4XZw9jmH6NmzhWyDYO"
dynlibdir  = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/integration/.stack-work/install/x86_64-osx/1b27a6cb896ce60fd505ab9dab8192899f9817c23a8def433f988c1a248e5f70/8.8.3/lib/x86_64-osx-ghc-8.8.3"
datadir    = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/integration/.stack-work/install/x86_64-osx/1b27a6cb896ce60fd505ab9dab8192899f9817c23a8def433f988c1a248e5f70/8.8.3/share/x86_64-osx-ghc-8.8.3/integration-0.1.0.0"
libexecdir = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/integration/.stack-work/install/x86_64-osx/1b27a6cb896ce60fd505ab9dab8192899f9817c23a8def433f988c1a248e5f70/8.8.3/libexec/x86_64-osx-ghc-8.8.3/integration-0.1.0.0"
sysconfdir = "/Users/daniilkorolev/Documents/Haskell/hw4-Danielto1404/integration/.stack-work/install/x86_64-osx/1b27a6cb896ce60fd505ab9dab8192899f9817c23a8def433f988c1a248e5f70/8.8.3/etc"

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "integration_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "integration_libdir") (\_ -> return libdir)
getDynLibDir = catchIO (getEnv "integration_dynlibdir") (\_ -> return dynlibdir)
getDataDir = catchIO (getEnv "integration_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "integration_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "integration_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
