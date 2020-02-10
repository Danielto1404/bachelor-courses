#!/bin/bash

outfile="process_task3.out"

ps ax -o pid,command | grep "\/sbin\/.*" | awk '{print $1}' > $outfile
