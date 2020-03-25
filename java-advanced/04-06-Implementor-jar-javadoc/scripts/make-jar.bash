#!/bin/bash

cd ..

MODULE_PATH=ru/ifmo/rain/korolev/implementor
MODULE_NAME=ru.ifmo.rain.korolev.implementor
INFO_MODULE_PATH=info/kgeorgiy/java/advanced/implementor

mkdir generated
mkdir -p generated/${MODULE_PATH}/utils
mkdir -p generated/${INFO_MODULE_PATH}

#mkdir -p directories - creates all auxiliary directories without warning and errors

cp src/${MODULE_PATH}/NativeCodeImplementor.java generated/${MODULE_PATH}
cp src/${MODULE_PATH}/FilesUtils.java generated/${MODULE_PATH}
cp src/${MODULE_PATH}/JarImplementor.java generated/${MODULE_PATH}
cp -R src/${INFO_MODULE_PATH}/* generated/${INFO_MODULE_PATH}

# cp -R
# In -R mode, cp will continue copying even if errors are detected.
#
# Note that cp copies hard-linked files as separate files.  If you
# need to preserve hard links, consider using tar(1), cpio(1), or
# pax(1) instead.

echo -e "Manifest-Version: 1.0\nMain-Class: ${MODULE_NAME}.Implementor" >generated/MANIFEST.MF

javac generated/${MODULE_PATH}/JarImplementor.java --source-path generated
jar cfm generated/implementor.jar generated/MANIFEST.MF -C generated ru -C generated info
