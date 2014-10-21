#!/bin/bash

PROGRAM=tst_key_bootstrapping_secure_nic
touch last_compile
LAST_COMPILE="`cat last_compile`"

if [ "$LAST_COMPILE" == "SENSOR" ]; then
	make USER_CPPFLAGS="-DSENSOR_NODE" USER_ACC="-DSENSOR_NODE" APPLICATION=$PROGRAM && arm-objcopy -I elf32-little -O binary img/$PROGRAM.img img/$PROGRAM.bin && sudo python2 tools/emote/red-bsl.py -t /dev/ttyUSB1 -f img/$PROGRAM.bin -S
else
	make veryclean && make USER_CPPFLAGS="-DSENSOR_NODE" USER_ACC="-DSENSOR_NODE" APPLICATION=$PROGRAM && arm-objcopy -I elf32-little -O binary img/$PROGRAM.img img/$PROGRAM.bin && echo "SENSOR" > last_compile && sudo python2 tools/emote/red-bsl.py -t /dev/ttyUSB1 -f img/$PROGRAM.bin -S
fi
