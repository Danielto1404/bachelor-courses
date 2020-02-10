#!/bin/bash

ps ax -o pid,rss,vsz | tail +2 |
awk '{print "pid: "$1 " | " "kb: "($3 - $2)/4}' | sort -n -r -k5
