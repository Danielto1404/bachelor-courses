#!/bin/bash

outfile="process_task1.out"

ps -a -u $USER -o pid,command | awk '{printf $1 ": " $2 "\n"}' > $outfile
wc  -l $outfile | awk '{print $1}'

# -a all process
# -u user
# -o process to selecet
