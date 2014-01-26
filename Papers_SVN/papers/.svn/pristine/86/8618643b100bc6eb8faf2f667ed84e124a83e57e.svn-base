#rm img/epos.hex
avr-objcopy -O ihex img/epos.img img/epos.hex
sudo avrdude -p m1281 -c jtagmkII -P usb:00A0000025CB -U flash:w:img/epos.hex -v
