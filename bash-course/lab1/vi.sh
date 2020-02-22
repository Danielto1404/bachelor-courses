#!/bin/bash

if [ $PWD == $HOME ]; then
    echo "Your home directory = $HOME"
    exit 0
else
    echo "Script works not in home directory"
    exit 1
fi
