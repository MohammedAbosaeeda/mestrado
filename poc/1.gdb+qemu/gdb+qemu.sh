#!/bin/bash

screen -S qemu -dm bash -c  "qemu-system-i386 -fda /home/tinha/mestrado/poc/1.gdb+qemu/ine5424-20141/img/philosophers_dinner.img -serial stdio -s -S"

sleep 1

screen -S gdb -dm bash -c "gdb --batch -x /home/tinha/mestrado/poc/1.gdb+qemu/breakpoint.sh"

echo "fim"
