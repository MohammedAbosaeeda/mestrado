#!/bin/bash
GDB='arm-xilinx-eabi-gdb'
if [ "$1" == "gui" ]; then
	GDB='arm-insight'
fi

#killing anyone using port :1234
dead=$(lsof -i :1234 | grep --only-matching "[0-9][0-9]*" | head -n 1)
if [ "$dead" -z ]; then
	kill -9 $dead
	echo "Killing $dead"
fi

echo "Running qemu."
gnome-terminal -x bash -c './dry_run.sh'
$GDB -ex "target remote :1234"

#gnome-terminal -x bash -c 'arm-none-eabi-gdb -ex "target remote :1234";bash'
