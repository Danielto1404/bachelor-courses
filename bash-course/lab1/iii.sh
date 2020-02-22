#!/bin/bash

read cur_str

while [[ "$cur_str" != "q" ]]
do
  result+=$cur_str
  read cur_str
done

echo $result
