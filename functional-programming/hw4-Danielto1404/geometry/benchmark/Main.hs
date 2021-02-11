module Main
  ( main
  ) where

import Criterion.Main (defaultMain, bgroup)
import GeometryBench

main :: IO ()
main = do
  defaultMain
    [ bgroup "strict perimeter"   strictPerimeter
    , bgroup "naive perimeter"    naivePerimeter
    , bgroup "strict double area" strictDoubleArea
    , bgroup "naive double area"  naiveDoubleArea
    ]
