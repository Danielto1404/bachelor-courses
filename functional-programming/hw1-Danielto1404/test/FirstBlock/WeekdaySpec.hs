module FirstBlock.WeekdaySpec
 ( weekdayTestTree
 ) where

import Test.Tasty (TestTree)
import Test.Tasty.Hspec (Spec, describe, it, shouldBe, testSpec)

import FirstBlock.Weekday (Weekday(..), afterDays, daysToParty, isWeekend, nextDay)

weekdayTestTree
  :: IO TestTree
weekdayTestTree = testSpec "Weekday" weekdaySpec

weekdaySpec
  :: Spec
weekdaySpec = do
  describe "nextDay" $ do
    it "nextDay Monday" $
      nextDay Monday `shouldBe` Tuesday
    it "nextDay Tuesday" $
      nextDay Tuesday `shouldBe` Wednesday
    it "nextDay Wednesday" $
      nextDay Wednesday `shouldBe` Thursday
    it "nextDay Thursday" $
      nextDay Thursday `shouldBe` Friday
    it "nextDay Friday" $
      nextDay Friday `shouldBe` Saturday
    it "nextDay Saturday" $
      nextDay Saturday `shouldBe` Sunday
    it "nextDay Sunday" $
      nextDay Sunday `shouldBe` Monday

  describe "afterDays" $ do
    it "flip afterDays $ 1 ~ nextDay" $
      afterDays Sunday 3 `shouldBe` Wednesday
    it "afterDays 7" $
      afterDays Monday 7 `shouldBe` Monday
    it "afterDays positive" $
      afterDays Tuesday 30 `shouldBe` Thursday
    it "afterDays 0" $
      afterDays Tuesday 7 `shouldBe` Tuesday

  describe "isWeekend" $ do
    it "isWeekend Monday" $
      isWeekend Monday `shouldBe` False
    it "isWeekend Tuesday" $
      isWeekend Tuesday `shouldBe` False
    it "isWeekend Wednesday" $
      isWeekend Wednesday `shouldBe` False
    it "isWeekend Thursday" $
      isWeekend Thursday `shouldBe` False
    it "isWeekend Friday" $
      isWeekend Friday `shouldBe` False
    it "isWeekend Saturday" $
      isWeekend Saturday `shouldBe` True
    it "isWeekend Sunday" $
      isWeekend Sunday `shouldBe` True

  describe "daysToParty" $ do
    it "daysToParty Monday" $
      daysToParty Monday `shouldBe` 4
    it "daysToParty Tuesday" $
      daysToParty Tuesday `shouldBe` 3
    it "daysToParty Wednesday" $
      daysToParty Wednesday `shouldBe` 2
    it "daysToParty Thursday" $
      daysToParty Thursday `shouldBe` 1
    it "daysToParty Friday" $
      daysToParty Friday `shouldBe` 0
    it "daysToParty Saturday" $
      daysToParty Saturday `shouldBe` 6
    it "daysToParty Sunday" $
      daysToParty Sunday `shouldBe` 5
