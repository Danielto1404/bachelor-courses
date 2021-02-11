module Main
  ( main
  ) where


import Test.Tasty       (defaultMain, testGroup)
import FS.VirtualFSSpec (virtualFSSpecTree, logsTestTree)


main
  :: IO ()
main = do
  testedFS <- virtualFSSpecTree
  logsFS   <- logsTestTree

  defaultMain $
    testGroup "HW03" [ testGroup "Virtual FS testing"
                         [ testedFS
                         -- , logsFS  Uncoment to see logs
                         ]
                     ]
