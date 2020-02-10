#!/bin/bash
while true
do
	read line
	if [[ $line = "TERM" ]]
		then
			echo "TERMINATION"
			kill -SIGTERM $(cat .pid)
			exit 0
	fi
done
