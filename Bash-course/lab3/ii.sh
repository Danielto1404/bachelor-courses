#!/bin/bash

ps -e -o pid,start | tail +2 | tail -1
