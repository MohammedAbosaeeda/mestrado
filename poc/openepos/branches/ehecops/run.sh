#!/bin/sh
make APPLICATION=$1
/usr/local/arm/gcc/bin/arm-objcopy -I elf32-little -O binary img/$1.img img/$1.bin
sudo python tools/emote/red-bsl.py -t /dev/ttyUSB$2 -f img/$1.bin -S
