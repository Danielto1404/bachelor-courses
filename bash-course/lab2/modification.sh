#!/bin/bash

dirs="/var/log"
outFile="var-log.list"

find $dirs | sed "s/\/var\/log\///" | awk '{print($1"\n")}' > $outFile

cat $outFile
