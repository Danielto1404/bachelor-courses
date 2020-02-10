#!/bin/bash

DIR="$HOME/restore"
SRC_DIR=$(ls -d -1 "$HOME/Backup-"????"-"??"-"?? 2> /dev/null | tail -n 1 2> /dev/null)

if [[ "$SRC_DIR" == "" ]]
then
	echo "No backup directories. Cannot restore."
	exit 1
fi

echo "Restoring from $SRC_DIR to $DIR..."
rm -r "$DIR" 2> /dev/null
mkdir "$DIR"

for FILE in $(ls -1 "$SRC_DIR")
do
	if [[ "$FILE" != *.????-??-?? ]]
	then
		cp "$SRC_DIR/$FILE" "$DIR"
	fi
done

echo "Done"
