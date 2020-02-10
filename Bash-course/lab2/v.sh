#!/bin/bash

inFile="/etc/passwd"

sed '/^#/d' "$inFile" | awk -F ":" '{print $3 " " $1}' | sort -n

# В начале sed убирает служебную закомментированную информацию.
