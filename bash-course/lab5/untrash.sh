#!/bin/bash

DIR="$HOME/.trash"
RESTORE=0

if [ "$#" != 1 ]
then
	echo "Usage: untrash.sh TARGET_NAME"
	exit 1
fi

if [ ! -r "$DIR" ]
then
	echo "Trash bin was not created or not available"
	exit 1
fi

if [ ! -f "$DIR/trash.log" ]
then
	echo "trash.log is not readable. Unable to proceed."
	exit 1
fi

echo "Attempting to reset..."

IFS=$'\n'
for LINE in $(cat "$DIR/trash.log")
do
	CANDIDATE_DIR=$(awk '{print $1}' <<< "$LINE")
	CANDIDATE_FILE=$(awk '{print $2 ".trash"}' <<< "$LINE")

	if [[ "$CANDIDATE_DIR" != *$1 ]]
	then
		continue
	fi

	if [ ! -f "$DIR/$CANDIDATE_FILE" ]
	then
		continue
	fi

	while true
	do
		echo "Is source $CANDIDATE_DIR? (Y/n)"
		read RESPONSE <& 0
		case "$RESPONSE" in
			"Y")
				RESTORE=1
				break
				;;
			"n")
				RESTORE=0
				break
				;;
			*)
				;;
		esac
	done

	if [[ $RESTORE == 0 ]]
	then
		continue
	fi

	echo "Resetting $CANDIDATE_FILE to $CANDIDATE_DIR..."
 	ln "$DIR/$CANDIDATE_FILE" "$CANDIDATE_DIR" 2> /dev/null
	if [[ $? != 0 ]]
	then
		echo "Unable to restore in original directory. Will save at home directory."
		ln "$DIR/$CANDIDATE_FILE" "$HOME/$1"
	fi
	rm "$DIR/$CANDIDATE_FILE"
	break
done

if [[ $RESTORE == 0 ]]
then
	echo "End of candidates list"
fi
