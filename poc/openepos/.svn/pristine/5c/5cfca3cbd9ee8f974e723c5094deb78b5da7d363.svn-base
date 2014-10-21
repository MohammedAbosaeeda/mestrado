#!/bin/bash
if [ $# == 1 ]; then
	arm-objcopy -I elf32-little -O binary img/$1.img img/$1.bin && sudo python2 tools/emote/red-bsl.py -t /dev/ttyUSB0 -f img/$1.bin -S
elif [ $# == 2 ]; then
	arm-objcopy -I elf32-little -O binary img/$1.img img/$1.bin && sudo python2 tools/emote/red-bsl.py -t $2 -f img/$1.bin -S
else
	echo "Usage: $0 <program_name> [tty_port]"
fi
