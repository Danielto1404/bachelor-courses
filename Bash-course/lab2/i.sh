#!/bin/bash

outFile="errors.log"

grep --recursive --no-filename --only-matching -s "ACPI.*" /var/log/ |
sed '/\./p' > $outFile

# grep [--prams | --pattern | --source | out_source]
# sed [-n = не выводим все строки, которые обрабатываем]
# -s == silence (no error msg)
