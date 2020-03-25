#!/bin/bash

cd ../../../../../../../

ROOT=$PWD

ALL_MODULES_PATH=java-advanced-2020/modules
INFO_MODULE_PATH=info/kgeorgiy/java/advanced/implementor
INFO_MODULE_NAME=info.kgeorgiy.java.advanced.implementor

SRC_PATH=${ROOT}/java-advanced-2020-solutions/java-solutions/ru/ifmo/rain/korolev/implementor
INFO_PATH=${ROOT}/${ALL_MODULES_PATH}/${INFO_MODULE_NAME}/${INFO_MODULE_PATH}

javadoc \
  -link https://docs.oracle.com/en/java/javase/11/docs/api/ \
  -html5 -private -author -version\
  -d "${SRC_PATH}"/_javadoc \
  "${SRC_PATH}"/*.java \
  "${INFO_PATH}"/Impler.java "${INFO_PATH}"/ImplerException.java "${INFO_PATH}"/JarImpler.java
