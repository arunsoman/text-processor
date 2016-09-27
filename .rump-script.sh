#!/bin/bash

if [ $# -eq 4 ]
  then
    echo "KERNONLY PLATFORM EXTRAFLAGS PACKAGE RUMPRUN_TOOLCHAIN_TUPLE (eg. x86_64-rumprun-netbsd, i486-rumprun-netbsdelf) expected"
fi

sudo apt-get update -y
sudo apt-get install qemu-kvm libxen-dev -y
sudo apt-get install --only-upgrade binutils gcc -y

git clone http://repo.rumpkernel.org/rumprun
cd rumprun
git submodule update --init
./build-rr.sh -o myobj -j16 -qq ${KERNONLY} ${PLATFORM} ${EXTRAFLAGS}
cd ..

export PATH=$PATH:$HOME/text-processor/rumprun/myobj/app-tools:$HOME/text-processor/rumprun/./rumprun/bin

git clone https://github.com/arunsoman/rumprun-packages
cd rumprun-packages
git submodule update --init
# Builds one specific package, specified by $PACKAGE
if [ -z "${PACKAGE}" ]; then
	echo "PACKAGE is not set"
        echo "using openjdk8"
        PACKAGE=openjdk8
fi
cd ${PACKAGE}
# Openjdk make should not be used with option -j
if [ "${PACKAGE}" == "openjdk8" ]; then
	make
else
	make -j2
fi
