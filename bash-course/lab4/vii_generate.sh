#!/bin/bash

while true
do
	read line
	case $line in
		"TERM")
			kill -SIGTERM $(cat pidpr)
			exit
			;;
		"+")
			kill -USR1 $(cat pidpr)
			;;
		"*")
			kill -USR2 $(cat pidpr)
			;;
	esac
done