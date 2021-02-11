module FirstBlock.StringSumSpec
    ( stringSumTestTree
    ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, it, shouldBe, testSpec)

import FirstBlock.StringSum (stringSum)

stringSumTestTree
  :: IO TestTree
stringSumTestTree = testSpec "String sum tests:" stringSumSpec

stringSumSpec
  :: Spec
stringSumSpec = do
    it "Empty string \"\"" $
      stringSum "" `shouldBe` Nothing
    it "1 2 3 4 5 === 15" $
      stringSum "1 2 3 4 5" `shouldBe` Just 15
    it "-10 4 -7 12 3 -2 === 0" $
      stringSum "-10 4 -7 12 3 -2" `shouldBe` Just 0
    it "1, 2, 3, 4, 5 === Nothing (because of comma)" $
      stringSum "1, 2, 3, 4, 5" `shouldBe` Nothing
    it "Negative numbers: -1 -4 -24 -239 === -268" $
      stringSum "-1 -4 -24 -239" `shouldBe` Just (-268)
    it "1 2 3 4five 6six 7seven === Nothing" $
      stringSum "1 2 3 4five 6six 7seven" `shouldBe` Nothing
    it "1 2 3 4, 5 === Nothing" $
      stringSum "1 2 3 4, 5" `shouldBe` Nothing
    it "Hello, how are you? 10 === Nothing" $
      stringSum "Hello, how are you? 10" `shouldBe` Nothing
    it "1.0 3.0 5.0 === Nothing" $
      stringSum "1.0 3.0 5.0" `shouldBe` Nothing
    it "many spaces 1   10      40" $
      stringSum "1   10      40" `shouldBe` Just 51
