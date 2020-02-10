#!/bin/bash

let ppid=0
let sleepAvg=0
let sum=0
let cnt=1

res=""
outFile="process_task6.out"
inFile="process_task5.out"

while read cur
do :
	#....|=|num ....|=|num <- ppid ....|=|num <- sleepAvg
	nextPpid=$(echo $cur | awk -F '=' '{print $3}' | awk '{print $1}')
	nextSleepAvg=$(echo $cur | awk -F '=' '{print $4}')
	if [ $ppid == $nextPpid ];
	then
		sum=$(echo "scale=10; $sum+$nextSleepAvg" | bc)
		let cnt=$((cnt+1))
	else
		sleepAvg=$(echo "scale=10; $sum/$cnt" | bc)
		res=$res"\nAverage Sleeping Children of ParentID="$ppid" is "$sleepAvg
		ppid=$nextPpid
		sum=$nextSleepAvg
		cnt=1
	fi
	res=$res'\n'$cur
done < $inFile

sleepAvg=$(echo "scale=10; $sum/$cnt" | bc)
res=$res"\nAverage Sleeping Children of ParentID="$ppid" is "$sleepAvg

echo -e $res > $outFile

#bc - calulates in linux; scale - num range
#echo
