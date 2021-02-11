{-# LANGUAGE Rank2Types #-}

module FSTraversal
  ( -- * Functions
    cd
  , ls
  , file
  ) where

import FS
import FSLenses
import Lens.Micro (Traversal', traversed, filtered)

-- | View the specified subdirectory.
cd
  :: FilePath
  -> Traversal' FS FS
cd fsName = _Dir . contents . traversed . _Dir . validDirs where
  validDirs
    :: Traversal' FS FS
  validDirs = filtered predicate

  predicate
    :: FS
    -> Bool
  predicate (Dir n _) = n == fsName
  predicate _         = False

-- | List contents of the currently viewed directory.
ls
  :: Traversal' FS FilePath
ls = _Dir . contents . traversed . name

-- | Return the name of the specified file, if exists, or return Nothing.
file
  :: FilePath
  -> Traversal' FS FilePath
file fsName = _Dir . contents . traversed . _File . validFiles . name where
  validFiles
    :: Traversal' FS FS
  validFiles = filtered predicate

  predicate
    :: FS
    -> Bool
  predicate (File n) = n == fsName
  predicate _        = False
