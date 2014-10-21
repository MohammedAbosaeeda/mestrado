#!/bin/bash

PROGRAM=tst_key_bootstrapping_secure_nic
touch last_compile
LAST_COMPILE="`cat last_compile`"

if [ "$LAST_COMPILE" == "SINK" ]; then
	make USER_CXXFLAGS="-DSINK_NODE" USER_ACC="-DSINK_NODE" APPLICATION=$PROGRAM && arm-objcopy -I elf32-little -O binary img/$PROGRAM.img img/$PROGRAM.bin && sudo python2 tools/emote/red-bsl.py -t /dev/ttyUSB0 -f img/$PROGRAM.bin -S
else
	make veryclean && make USER_CXXFLAGS="-DSINK_NODE" USER_ACC="-DSINK_NODE" APPLICATION=$PROGRAM && arm-objcopy -I elf32-little -O binary img/$PROGRAM.img img/$PROGRAM.bin && echo "SINK" > last_compile && sudo python2 tools/emote/red-bsl.py -t /dev/ttyUSB0 -f img/$PROGRAM.bin -S
fi
