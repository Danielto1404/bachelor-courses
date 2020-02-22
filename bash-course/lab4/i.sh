#!/bin/bash

mkdir -p ~/test && { echo "Catalog test was created succesfully" >> ~/report;
touch ~/test/$(date +"%F_%R"); }

ping -c 3 "google.com" || echo "Error pinging google.com" >> ~/report

echo; echo "~/report file:"; cat ~/report; echo

# mkdir -p no error will be reported if a directory given as an operand already exists
# ping -c = number of packets to sent and recive
