#!/bin/bash

if [[ $# -ne 3 ]]; then
  echo "Wrong number of args"
else

  if [[ $1 -ge $2 && $1 -ge $3 ]]; then max=$1
  elif [[ $2 -ge $3 ]]; then max=$2
  else max=$3
  fi

  echo $max

fi
