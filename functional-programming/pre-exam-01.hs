1) Basics

List comprehensions ∷ [operation x | x <- range, condition x]
[x * y | x <- [1,3,5], y <- [2,4,6], x * y >= 10]

{-# NameOfPragma #-} (
  TupleSections,
  LambdaCase,
  ViewPatterns,
  DuplicateRecordFields - for same fields in record syntax,
  RecordWildCards :: { .. } - all fields stored,
  InstanceSign - for declaring functions def in type claseses)


($) :: infixr 0
(&) :: infixl 1

NF   - all sub-expressions are evaluated
WHNF - constructor or lambda (2 : [1, 3], [1, 2 * 2, 3 ^ 3], Just(2+7))


instead of read use readMaybe / readEither



2) Semigroup

class Semigroup m where
  (<>)    :: m -> m -> m
  sconcat :: NonEmpty a -> a
  stimes  :: Integral b => b -> a -> a  -- делает список и сворачивает его.

Законы: (x <> y) <> z == x <> (y <> z)


3) Monoid

class Semigrop m => Monoid m where
  mempty  :: m           -- neural element
  mappend :: m -> m -> m -- (<>)
  mconcat :: [m] -> m    -- fold

Законы: mempty <> z == z
        z <> mempty == z

Sum / Product / Max / Min / First / Last / All / Any


4) Foldable
foldr :: (a -> b -> b) -> b -> [a] -> b
foldl :: (b -> a -> a) -> b -> [a] -> b


class Foldable t where
  fold    :: Monoid m => t m -> m
  foldMap :: Monoid m -> (a -> m) -> t a -> m
  foldr   :: (a -> b -> b) -> b -> t a -> b

  -- Minimal == foldMap | foldr


5) Functor

f :: * -> *

class Functor f where
  fmap :: (a -> b) -> f a -> f b


instance Functor Maybe
  fmap _ Nothing  = Nothing
  fmap f (Just x) = Just (f x)

instance Functor []
  fmap = map

instance Functor ((,) s) where
  fmap g (x, y) = (x, g y)

instance Functor ((->) e) where
  fmap = (.)
  (a -> b) -> (e -> a) -> (e -> b), e - enviroment.



<$> =  infixl 4
<$  :: a -> f b -> f a - полностью затирает значение контсантой.



Законы: fmap id         === id
        fmap (f . g) xs === (fmap f . fmap g) xs - не изменяет структуру


6) Applicative
(*) <$> (Just 3) == Just (3 *)


class Functor f => Applicative f where
  pure   :: a -> f a                            -- наивная упаковка в коробку
  <*>    :: f (a -> b) -> f a -> f b            -- вычисления из контекста
  liftA2 :: (a -> b -> c) -> f a -> f b -> f c
  liftA3 :: ----


instance Applicative Maybe where
  pure = Just

  Nothing <*> _ = Nothing
  Just f  <*> m = f <$> m

instance Applicative [] where
  pure x = [x]
  fs <*> xs = [f x | f <- fs, x <- xs]


Законы: pure id <*> v             == v
        pure (.) <*> u <*> v <* w == u <*> (v <*> w)
        pure f <*> pure x         == pure (f x)
        u <*> pure y              == pure ($ y) <*> u


7) Alternative

class Applicative f => Alternative f where
  empty :: f a
  (<|>) :: f a -> f a -> f a

instance Alternative Maybe where
  empty = Nothing
  Nothing <|> r = r
  l       <|> _ = l


8) Traversable

class (Functor t, Foldable t) => Traversable t where
  traverse  :: Applicative f => (a -> f b) -> t a -> f (t b)
  sequenceA :: Applicative f => t (f a) -> f (t a)


instance Traversable Maybe where
  traverse :: Applicative f => (a -> f b) -> Maybe a -> f (Maybe b)
  traverse _ Nothing  = pure Nothing
  traverse f (Just x) = Just <$> f x


instance Traversable [] where
  traverse :: Applicative f => (a -> f b) -> [a] -> f [b]
  traverse f = foldr consF (pure [])
    where
      consF x ys = (:) <$> f x <*> ys

  sequenceA = traverse id



9) Monad


class Monad m where
  return :: a -> m a
  (>>=)  :: m a -> (a -> m b) -> m b  --- bind

  (>>)   :: m a -> m b -> m b
  (>>) a b = a >>= _ -> b

  infixl 1 :: (>>=), (>>)

  fail :: String -> m a
  fail = error


Законы: return a >>= k   === k a
        m >>= return     === m
        (m >>= k) >>= k' === m >>= (\x -> k x >>= k')


instance Monad Maybe where
  return :: a -> Maybe a
  return = Just

  (>>=) Nothing  _ = Nothing
  (>>=) (Just x) k = k x

  fail :: String -> Maybe a
  fail = Nothing


instance Monad [] where
  return :: a -> [a]
  return x = [x]

  (>>=) xs f = concatMap k xs

  fail :: String -> [a]
  fail = []


  join :: Monad m => m (m a) -> m a



10) IO
newtype IO a = IO (RealWorld -> (RealWorld, a))

instance Monad IO where
  return :: a -> IO a
  return a = IO $ \world -> (world, a))

  (>>=) :: IO a -> (a -> IO b) -> (IO b)
  (>>=) m k = IO $ \w -> case m w of (w' a) -> k a w'



11) Reader

-- read from enviroment
instance Monad ((->) env) where
  return :: a -> (env -> a)
  return x = const x

  (>>=) :: (env -> a) -> (a -> (env -> b)) -> (env -> b)
  (>>=) monad kleisli = \env -> kleisli (monad env) env
  -- передает полученное окружение в оба вычисления


newtype Reader r a = Reader { runReader :: (r -> a) }

instance Monad Reader where
  return x = reader $ \env -> x
  m >>= k  = reader $ \env -> let v = runReader m env in
                                runReader (k v) env


12) Writer

newtype Writer w a = Writer { runWriter :: (a, w) }

instance (Monoid w) => Monad (Writer w) where
  return x = Writer (x, empty)
  m >>= k  = let (x, u) = runWriter m
                 (y, v) = runWriter $ k x
                 in Writer (y, u `mappend` v)


13) State

newtype State s a = State { runState :: s -> (a, s) }

instance Monad (State s) where
  return x = state $ \st -> (x, st)
  m >>= k  = state $ \st -> let (x, st') = runState m st
                                m'       = k x
                            in runState m' st'
