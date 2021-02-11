module TestFS
  ( testFS
  , fileFS
  ) where

import FS

fileFS
  :: FS
fileFS = File { _name = "file.txt" }

testFS
  :: FS
testFS =
  Dir
  { _name = "project"
  , _contents =
      [ File { _name = "LICENSE" }
      , Dir  { _name     = "src"
             , _contents =
                 [ File { _name = "FS.hs" }
                 , File { _name = "Main.hs" }
                 ]
             }
      , Dir  { _name     = "test"
             , _contents =
                 [ File { _name = "Spec.hs" }
                 , File { _name = ".gitignore" }
                 , File { _name = "FSTest.hs" }
                 ]
             }
      , Dir { _name     = ".git"
            , _contents =
                [ File { _name = "sha256.crypt" }
                , File { _name = "git-properties.pr" }
                , File { _name = "user-info.info" }
                ]
            }
      , File { _name = "runner.exe"}
      ]
  }
