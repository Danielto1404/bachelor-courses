#!/bin/bash

echo $$ > pidpr

mode="+"
term()
{
	echo "STOP"
	exit
}

Plus()
{
	mode="+"
}

Mult()
{
	mode="MUL"
}

trap 'term' SIGTERM
trap 'Plus' USR1
trap 'Mult' USR2

a=1
while true
do
	case $mode in
		"MUL")
			let a=$a*2
			;;
		"+")
			let a=$a+2
			;;
    esac
echo $a
sleep 2
done
