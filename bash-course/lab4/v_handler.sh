#!/bin/bash

prevresult=1
result=1
operation='+'


while true
do
	line=$(cat channel5)
	case $line in
		"+")
			operation='+'
			echo "Adding numbers"
			;;

		"*")
			operation='*'
			echo "Multiplying numbers"
			;;
		
		[0-9]*)
			prevresult=$result
			if [[ $operation == '+' ]]
				then
					let result=$result+$line
				else
					let result=$result\*$line
			fi
			echo "$prevresult$operation$line = $result"
			;;

		"QUIT")
			echo "Have a  nice day"
			exit 0
			;;

		*)
			echo "Stopped <can't current parse symbol>"
			exit 0
			;;	
	esac	
done
