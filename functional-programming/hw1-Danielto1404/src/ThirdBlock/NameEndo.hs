{-# LANGUAGE InstanceSigs #-}

module ThirdBlock.NameEndo
    ( -- * Constructors
        Name (..)
      , Endo (..)
    ) where

-- | Type 'Name'
data Name
  = Empty        -- ^ Constructor of 'Name' type representing empty value
  | Name String  -- ^ Constructor of 'Name' type representing some string value
  deriving (Show, Eq)

-- | 'Name' type conforms to 'Semigroup' class type
instance Semigroup Name where
  (<>)
    :: Name
    -> Name
    -> Name
  Empty    <> name     = name
  name     <> Empty    = name
  (Name x) <> (Name y) = Name $ concat [x, ".", y]

-- | 'Name' type conforms to 'Monoid' class type
instance Monoid Name where
  mempty
    :: Name
  mempty = Empty


-- | Type 'Endo' represents wrappend endomorphism function
newtype Endo a
  -- ^ Constructor of 'Endo' type representing wrappend endomorphism function
  = Endo { getEndo :: a -> a }

-- | 'Name' type conforms to 'Semigroup' class type
instance Semigroup (Endo a) where
  (<>)
    :: Endo a
    -> Endo a
    -> Endo a
  (Endo f) <> (Endo g) = Endo (f . g)

-- | 'Endo' type conforms to 'Monoid' class type
instance Monoid (Endo a) where
  mempty :: Endo a
  mempty = Endo id
