#!/bin/bash

# shellcheck disable=SC2045
rm results.txt

for FILE in $(ls)
do
  # shellcheck disable=SC1009
  if cmp -s "$FILE" "run_all_datasets.bash"; then
    echo "Done"
  else
    python3 ../smape_regression.py "$FILE"
  fi
done