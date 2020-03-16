#!/bin/bash

cd ..

ROOT=$PWD

MODULE_PATH=ru/ifmo/rain/korolev/implementor
INFO_MODULE_PATH=info/kgeorgiy/java/advanced/implementor

OUT_PATH=${ROOT}/out/production/04-Implementor

SRC_PATH=${ROOT}/src/${MODULE_PATH}
INFO_PATH=${ROOT}/src/${INFO_MODULE_PATH}

javac "${SRC_PATH}"/*.java "${SRC_PATH}"/utils/*.java "${INFO_PATH}"/*.java -d "${OUT_PATH}"
