#!/bin/bash

cd ../../../../../../

REPOSITORY_PATH=$PWD

cd ../
ROOT=$PWD

ALL_MODULES_PATH=java-advanced-2020/modules
INFO_MODULE_PATH=info/kgeorgiy/java/advanced/implementor
INFO_MODULE_NAME=info.kgeorgiy.java.advanced.implementor

RU_PATH=ru/ifmo/rain/korolev/implementor
SOLUTIONS_PATH=${REPOSITORY_PATH}/java-solutions
SRC_PATH=${SOLUTIONS_PATH}/${RU_PATH}
INFO_PATH=${ROOT}/${ALL_MODULES_PATH}/${INFO_MODULE_NAME}/${INFO_MODULE_PATH}
OUT_PATH=${SRC_PATH}/_build/production

rm -rf OUT_PATH
mkdir -p "${OUT_PATH}"/${RU_PATH}
mkdir -p "${OUT_PATH}"/${INFO_MODULE_PATH}

cp "${SRC_PATH}"/*.java "${OUT_PATH}"/"${RU_PATH}"
cp "${INFO_PATH}"/Impler.java "${OUT_PATH}"/"${INFO_MODULE_PATH}"
cp -p "${INFO_PATH}"/ImplerException.java "${OUT_PATH}"/"${INFO_MODULE_PATH}"
cp -p "${INFO_PATH}"/JarImpler.java "${OUT_PATH}"/"${INFO_MODULE_PATH}"

cd "${OUT_PATH}" || exit

javac "${RU_PATH}"/JarImplementor.java --source-path "${PWD}"

echo -e "Manifest-Version: 1.0\nMain-Class: ru.ifmo.rain.korolev.implementor.JarImplementor" > MANIFEST.MF

rm -rf ${RU_PATH}/*.java ${INFO_MODULE_PATH}/*.java

# c = create, f = file, m = manifest
jar cfm _implementor.jar MANIFEST.MF ${RU_PATH} ${INFO_MODULE_PATH}
