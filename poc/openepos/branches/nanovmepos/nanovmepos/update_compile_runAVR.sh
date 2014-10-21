path_nvm_default=~/Lisha/nanovm_epos/branches/nanovm_cpp/vm/build/unix;
trunkpath=~/Lisha/nanovm_epos/trunk/nanovmepos

cd $path_nvm_default 
make 
mv nvmdefault.h $trunkpath/src/vm
cd -
make APPLICATION=pc_app
/usr/local/avr/gcc-4.0.2_patched/bin/avr-objcopy -O ihex img/pc_app.img img/pc_app.hex
sudo avrdude -p m1281 -c jtagmkII -P usb -U flash:w:img/pc_app.hex -v
