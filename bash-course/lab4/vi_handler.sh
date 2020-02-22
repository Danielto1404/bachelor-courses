#!/bin/bash

echo $$ > .pid


mode="counting"

termination()
{
	mode="stop"
}

trap 'termination' SIGTERM

a=0;
while true
do
	case $mode in
		"counting")
			let a=$a+1
			echo $a
			;;
		"stop")
			echo "STOP"
			exit
			;;

	esac
sleep 2
done