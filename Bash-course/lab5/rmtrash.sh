#!/bin/bash

DIR="$HOME/.trash"
TARGET="$PWD/$1"
DATE=$(date +"%d-%m-%y_%T")

if [[ "$#" != 1 ]]
then
	echo "Usage: rmtrash.sh TARGET_FILE"
	exit 0
fi

if [ ! -f "$TARGET" ]
then
	echo "Invalid target file"
	exit 1
fi

if [ ! -d "$DIR" ]
then
	mkdir "$DIR"
        touch "$DIR/trash.log"
fi

# if (( $(ls -1 "$DIR/*.trash" | wc -l == 0 ))
# then
# 	ORD=1
# else
# 	ORD=$(( $( ls -1 "$DIR" | grep -o "[[:digit:]]*" | sort -nr | head -n +1 ) + 1 ))
# fi

ln "$TARGET" "$DIR/$DATE.trash"
echo "$TARGET $DATE" >> "$DIR/trash.log"
rm "$TARGET"

