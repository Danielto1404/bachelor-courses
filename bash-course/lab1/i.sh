#!/bin/bash

if [[ $# -ne 2 ]]; then
  echo "Wrong number of args"
else

  if [[ "$1" == "$2" ]]
  then echo "String are equals! Value of strings: $1"
  else echo "Strnig not equals!"
  fi
  
fi
