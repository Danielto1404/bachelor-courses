#!/bin/bash

cat $(find /var/log/*.log) | wc -l

# cat = concatenate - объединить файлы
# find = найти по пути в директории
# wc = количество строчек, -l = --lines
