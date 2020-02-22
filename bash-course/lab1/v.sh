#!/bin/bash

while true; do
    echo "

    ~        MENU:         ~
    ~  1 - open the NANO   ~
    ~  2 - open the VI     ~
    ~  3 - open the LINKS  ~
    ~  4 - exit            ~

"

    read choice

    case "$choice" in
        1 ) vi ;;
        2 ) nano ;;
        3 ) chrome ;;
        4 ) exit 0 ;;
        * ) echo "Uncorrect choice! Try again!"
    esac
done
