module Main
  ( main
  ) where

import Control.Monad    (replicateM_)
import System.Random    (getStdRandom, random, randomR)
import Test.Tasty       (defaultMain)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import Hashtable

-- | Generate random sample (Char, Int) pair.
rndPair
  :: IO (Char, Int)
rndPair = do
  key <- getStdRandom (randomR ('a', 'z'))
  val <- getStdRandom random
  return (key, val)

-- | Generate sample hashtable with random initial contents.
sampleCHT
  :: IO (ConcurrentHashTable Char Int)
sampleCHT = do
  cht     <- newCHT
  pairCnt <- getStdRandom (randomR (1 :: Int, 100))
  replicateM_ pairCnt rndPair
  return cht

-- | Perform hashtable correctness tests.
spec :: Spec
spec = do
  describe "Hashtable" $ do
    it "Empty ht is 0-sized" $ do
      cht <- newCHT
      sz  <- sizeCHT cht
      sz `shouldBe` 0
    it "Insertion of new key increases ht size by 1" $ do
      cht <- sampleCHT
      sz0 <- sizeCHT cht
      putCHT 'k' 239 cht
      sz1 <- sizeCHT cht
      sz0 + 1 `shouldBe` sz1
    it "Insertion of existing key does not change size" $ do
      cht <- sampleCHT
      putCHT 'k' 239 cht
      sz0 <- sizeCHT cht
      putCHT 'k' 30 cht
      sz1 <- sizeCHT cht
      sz0 `shouldBe` sz1
    it "Element is retrievable after insertion" $ do
      cht <- sampleCHT
      putCHT 'k' 239 cht
      val <- getCHT 'k' cht
      val `shouldBe` Just 239
    it "Insertion of pair with known key updates value" $ do
      cht <- sampleCHT
      putCHT 'k' 239 cht
      putCHT 'k' 30 cht
      val <- getCHT 'k' cht
      val `shouldBe` Just 30
    it "Retrieval does not delete element" $ do
      cht <- sampleCHT
      putCHT 'k' 239 cht
      _    <- getCHT 'k' cht
      val1 <- getCHT 'k' cht
      val1 `shouldBe` Just 239
    it "Cannot retrieve unknown keys" $ do
      cht <- sampleCHT
      val <- getCHT 'k' cht
      val `shouldBe` Nothing

main
  :: IO ()
main = testSpec "CHT test" spec >>= defaultMain
