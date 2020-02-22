#!/bin/bash

$(mkfifo channel5)

while true
do
    read s
    echo "$s" >> channel5

    if [[ "$s" == "QUIT" ]]
    then
        exit 0
    fi

    if [[ ! "$s" =~ [0-9]+ && "$s" != "+" && "$s" != "*" ]]
    then
        echo "Stopped by incorrect message"
        exit 1
    fi
done
