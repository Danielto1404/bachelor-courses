#!/bin/bash

./iv_process.sh&
pid1=$!

./iv_process.sh&
pid2=$!

echo $pid1 $pid2
renice +10 $pid1

cpulimit -p $pid1 -l 20 &

sleep 3
ps ax -o pid,pcpu | grep "$pid1\|$pid2"

kill $pid1
kill -9 $pid2
