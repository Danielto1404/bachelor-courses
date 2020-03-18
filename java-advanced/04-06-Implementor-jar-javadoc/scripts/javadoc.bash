#!/bin/bash

cd ..

ROOT=$PWD

MODULE_PATH=ru/ifmo/rain/korolev/implementor
INFO_MODULE_PATH=info/kgeorgiy/java/advanced/implementor

SRC_PATH=${ROOT}/src/${MODULE_PATH}
INFO_PATH=${ROOT}/src/${INFO_MODULE_PATH}

javadoc \
 -link https://docs.oracle.com/en/java/javase/11/docs/api/ \
 -html5 -private \
 -d JAVADOC \
 "${SRC_PATH}"/*.java "${INFO_PATH}"/*.java