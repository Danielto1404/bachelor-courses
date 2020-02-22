#!/bin/bash

dirs='/etc'
outFile="emails.list"
emailRegex="[[:alnum:]._%+-]+@[[:alnum:].-]+\.[[:alpha:]]{1,10}"

grep -E --recursive --no-filename --only-matching -s $emailRegex $dirs |
sort -u > $outFile
