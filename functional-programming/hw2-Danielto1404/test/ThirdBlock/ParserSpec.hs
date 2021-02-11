module ThirdBlock.ParserSpec
    ( parserTestTree
    ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import ThirdBlock.BasicParsers (ok, eof, satisfy, element, stream)
import ThirdBlock.Parser (Parser(..))
import ThirdBlock.ListParser (listListParser)
import ThirdBlock.NumberParser (numberParser)
import ThirdBlock.BracketsParser (bracketsParser)
import Data.Char (ord, chr)
import Control.Applicative ((<|>), empty)

parser
  :: Parser Char String
parser = Parser $ \s -> Just ("ITMO", s)

run
  :: Parser Char a
  -> Maybe (a, String)
run p = runParser p "abc"

parserTestTree
  :: IO TestTree
parserTestTree = testSpec "Parser tests:" parserSpec

parserSpec
  :: Spec
parserSpec = do
  describe "Laws for parser class types" $ do

    describe "Functor" $ do
      it "identity: fmap id == id" $ do
        run (id <$> parser) `shouldBe` (id $ run parser)
      it "composition: fmap (f . g) == fmap f . fmap g" $ do
        run ((('C':) . ('T':)) <$> parser) `shouldBe`
          ((\(x, y) -> ('C':x, y)) <$> (run (('T':) <$> parser)))

    describe "Applicative" $ do
      it "identity: pure id <*> v == v" $ do
        run (pure id <*> parser) `shouldBe` (run parser)
      it "homomorphism: pure f <*> pure x == pure (f x)" $ do
        run (pure ord <*> pure 'x') `shouldBe` run (pure (ord 'x'))
      it "interchange: u <*> pure y == pure ($ y) <*> u" $ do
        run (pure ord <*> pure 'y') `shouldBe` run (pure ($ 'y') <*> pure ord)

    describe "Monad" $ do
      it "left identity: return a >>= k = k a" $ do
        run (return 'a' >>= bind) `shouldBe` run (bind 'a')
      it "right identity: m >>= return = m" $ do
        run (parser >>= return) `shouldBe` run parser
      it "associativity: m >>= (x) -> k x >>= h == (m >>=k) >>= h" $ do
        run (aParser >>= \x -> bind x >>= nextChar) `shouldBe`
          run (aParser >>= bind >>= nextChar)

    describe "Alternative" $ do
      it "left empty: empty <|> u == u" $ do
        run (empty <|> parser) `shouldBe` run parser
      it "right empty: u <|> empty == u" $ do
        run parser `shouldBe` run (empty <|> parser)
      it "associativity u <|> (v <|> w) == (u <|> v) <|> w" $ do
        run (pure 'a' <|> (pure 'b' <|> pure 'c')) `shouldBe`
          run ((pure 'a' <|> pure 'b') <|> pure 'c')


  describe "Basic parsers tests" $ do
    it "ok parser test" $ do
      runParser ok "input" `shouldBe` Just ((), "input")
    it "eof parser test on empty input" $ do
      runParser eof "" `shouldBe` Just ((), [])
    it "eof parser test on not empty input" $ do
      runParser eof "a" `shouldBe` Nothing
    it "satisfy parser test on valid char" $ do
      runParser (satisfy (const True)) "abc" `shouldBe` Just ('a', "bc")
    it "satisfy parser test on invalid char" $ do
      runParser (satisfy (const False)) "abc" `shouldBe` Nothing
    it "element parser test on valid char" $ do
      runParser (element 'a') "abc" `shouldBe` Just ('a', "bc")
    it "element parser test on invalid char" $ do
      runParser (element 'x') "abc" `shouldBe` Nothing
    it "stream parser on valid stream" $ do
      runParser (stream "null") "null_some" `shouldBe` Just ("null", "_some")
    it "stream parser on invalid stream" $ do
      runParser (stream "null") "nulxx" `shouldBe` Nothing


  describe "Brackets parser tests" $ do
    it "empty string" $ do
      runParser bracketsParser "" `shouldBe` Just ((), [])
    it "()" $ do
      runParser bracketsParser "()" `shouldBe` Just ((), [])
    it "()()" $ do
      runParser bracketsParser "()()" `shouldBe` Just ((), [])
    it "(())" $ do
      runParser bracketsParser "(())" `shouldBe` Just ((), [])
    it "(()(()))" $ do
      runParser bracketsParser "(()(()))" `shouldBe` Just ((), [])
    it "()()(()(()()()))()()" $ do
      runParser bracketsParser "()()(()(()()()))()()" `shouldBe` Just ((), [])
    it "incorrect input: (" $ do
      runParser bracketsParser "(" `shouldBe` Nothing
    it "incorrect input: )" $ do
      runParser bracketsParser ")" `shouldBe` Nothing
    it "incorrect input: (()))" $ do
      runParser bracketsParser "(()))" `shouldBe` Nothing
    it "incorrect input: ((())" $ do
      runParser bracketsParser "((())" `shouldBe` Nothing
    it "incorrect input: )(" $ do
      runParser bracketsParser ")(" `shouldBe` Nothing
    it "incorrect input: () hello" $ do
      runParser bracketsParser "() hello" `shouldBe` Nothing


  describe "Number parser tests" $ do
    it "empty string" $ do
      runParser numberParser "" `shouldBe` Nothing
    it "float number 23.0" $ do
      runParser numberParser "23.0" `shouldBe` Just (23, ".0")
    it "negative number: -239" $ do
      runParser numberParser "-239" `shouldBe` Just (-239, [])
    it "positive number: +239" $ do
      runParser numberParser "+239" `shouldBe` Just (239, [])
    it "ignores leading zeros: 0000000239" $ do
      runParser numberParser "0000000239" `shouldBe` Just (239, [])
    it "leading multiple sign chars should fail: ++239" $ do
      runParser numberParser "++239" `shouldBe` Nothing
    it "remaining stream should be saved: +239 hello" $ do
      runParser numberParser "+239 hello" `shouldBe` Just(239, " hello")
    it "word's should not be parsed: ITMO" $ do
      runParser numberParser "ITMO" `shouldBe` Nothing


  describe "List list parser tests:" $ do
    it "empty string" $ do
      parseListList "" `shouldBe` Nothing
    it "example from task condition" $ do
      parseListList "2,1,+10,3,5,-7,2" `shouldBe`
        Just ([[1, 10], [5, -7, 2]], "")
    it "zero list size" $ do
      parseListList "0, 0, 1, 2, 0" `shouldBe` Just ([[], [], [2], []], "")
    it "negative list size should fail" $ do
      parseListList "0, 0, -1, 2, 0" `shouldBe` Nothing
    it "ignores spaces" $ do
      parseListList "  2    ,1,  +10,    3, 5,      -7,    2   " `shouldBe`
        Just ([[1, 10], [5, -7, 2]], "")
    it "incorrect list size should fail" $ do
      parseListList "10,9,8,7,6" `shouldBe` Nothing
    it "unparsed nummbers should fail" $ do
      parseListList "2, 23.0, 19" `shouldBe` Nothing
    it "zero list size" $ do
      parseListList "0" `shouldBe` Just ([[]], "")
    it "only spaces should fail" $ do
      parseListList "        " `shouldBe` Nothing
    it "missing separator should fail" $ do
      parseListList "3, 9, 10 11" `shouldBe` Nothing

  where
    bind
      :: Char
      -> Parser Char Char
    bind = \x -> Parser $ \_ -> Just (x, "@HI man@")

    nextChar
      :: Char
      -> Parser Char Char
    nextChar = \x -> Parser $ \st -> Just (chr $ ord x + 1, st)

    aParser
      :: Parser Char Char
    aParser = return 'a'

    parseListList
       :: String
       -> Maybe ([[Int]], String)
    parseListList = runParser listListParser
