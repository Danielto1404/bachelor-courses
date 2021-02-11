module Command.StartOption
  ( -- * Constructors
    StartOption (..)
  ) where

-- | Data type represents all types of file system start options.
data StartOption
  = VIRTUAL        -- | Starting virtual file system user input representation.
  | REAL FilePath  -- | Starting real file system user input representation.
  | INVALID        -- | Invalid user input representation.
  | EXIT           -- | Terminating user input representation.
  deriving (Show, Eq)
