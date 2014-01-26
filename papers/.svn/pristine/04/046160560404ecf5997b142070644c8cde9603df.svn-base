avr-objcopy -O ihex img/epos.img epos.hex
uisp -dprog=mib510 -dpart=atmega128 -dserial=/dev/ttyUSB0 --erase --upload --verify if=epos.hex

