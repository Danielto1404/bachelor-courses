#!/bin/bash

dirs="/bin"
scriptRegex="^#!/bin/*"

grep -r $scriptRegex $dirs | uniq -c | sort -n -r | head -1 |
awk '{print $2}' | sed 's/:/  |  /'
