#!/bin/bash
\
inFile="/var/log/Xorg.0.log"
outFile="information_warnings.log"

(grep -E "\] \(II\)" $inFile & grep -E "\] \(WW\)" $inFile) |
sed "s/] (II)/ Information:/g" | sed "s/] (WW)/ Warning:/g" > $outFile

 cat $outFile
