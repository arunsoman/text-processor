git clone http://repo.rumpkernel.org/rumprun
cd rumprun
git submodule update --init
CC=cc ./build-rr.sh hw
