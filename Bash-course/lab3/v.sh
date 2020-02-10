#!/bin/bash

doubleRegex="[[:digit:]]+\.?[[:digit:]]*"

for pid in $(ps -a -x -o pid)
do
        statusFile="/proc/"$pid"/status"
        schedFile="/proc/"$pid"/sched"

        ppid=$(grep -s -h "PPid" $statusFile | grep -E -o $doubleRegex)
        sleepavg=$(grep -s -h "se.sum_exec_runtime" $schedFile | grep -E -o $doubleRegex)

        if [[ -z $sleepavg ]]
        then
                sleepavg=0
        fi

        if [[ -z $ppid ]]
        then
                ppid=0
        fi
        echo "ProcessID=$pid : Parent_ProcessID=$ppid : Average_Sleeping_Time=$sleepavg"

done | sort -n -t "=" -k3 > process.outi

cat process.out


# avg_atom = p->se.sum_exec_runtime;
#                 if (nr_switches)
#                         avg_atom = div64_ul(avg_atom, nr_switches);
