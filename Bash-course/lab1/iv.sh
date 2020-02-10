#!/bin/bash

cnt=0

while true
do
  read number
  check=$(( number % 2 ))

  if [[ "$check" -ne 0 ]]; then cnt=$(( cnt + 1 ))
  else break
  fi

done

echo "You've entered: $cnt odd number(s)"
