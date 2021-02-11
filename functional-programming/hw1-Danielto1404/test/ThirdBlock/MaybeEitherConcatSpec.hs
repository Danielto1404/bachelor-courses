module ThirdBlock.MaybeEitherConcatSpec
    ( maybeEitherConcatTestTree
    ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import ThirdBlock.MaybeEitherConcat (maybeConcat, eitherConcat)

maybeEitherConcatTestTree
  :: IO TestTree
maybeEitherConcatTestTree = testSpec "Maybe Either concat" maybeEitherConcatSpec

maybeEitherConcatSpec
  :: Spec
maybeEitherConcatSpec = do
  describe "Maybe concat" $ do
    it "[Just [5], Nothing, Nothing, Just [2, 3, 9]]" $
      maybeConcat [Just [5], Nothing, Nothing, Just [2, 3, 9]] `shouldBe` [5, 2, 3, 9]
    it "[Nothing, Nothing, Nothing]" $
      maybeConcat emptyMaybes `shouldBe` []
    it "[Just [2], Just [3], Just [9]]" $
      maybeConcat [Just [2], Just [3], Just [9]] `shouldBe` [2, 3, 9]
    it "[Just \"Hello world!\", Just \" \", Just \"My name is Daniel\"]" $
      maybeConcat [Just "Hello world!", Just " ", Just "My name is Daniel"]
        `shouldBe` "Hello world! My name is Daniel"

  describe "Either concat" $ do
    it "[Left [2, 3], Right \"Hello\", Right \" there!\", Left [9]]" $
      eitherConcat [Left [2, 3], Right "Hello", Right " there!", Left [9]]
        `shouldBe` ([2, 3, 9], "Hello there!")
    it "[Right \"Cat\", Right \" say\", Right \" meeeoooow\"]" $
      eitherConcat ([Right "Cat", Right " say", Right " meeeoooow"] :: [Either [Int] String])
        `shouldBe` ([], "Cat say meeeoooow")
    it "[Left \"Wolf\", Left \" say\", Left \" auuuuuuuuuu\"]" $
      eitherConcat ([Left "Wolf", Left " say", Left " auuuuuuuuuu"] :: [Either String [Int]])
        `shouldBe` ("Wolf say auuuuuuuuuu", [])

  where
    emptyMaybes
      :: [Maybe String]
    emptyMaybes = [Nothing, Nothing, Nothing]
