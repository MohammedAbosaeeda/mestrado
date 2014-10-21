# EPOS-- Boot Makefile

include $(EPOS)/makedefs

all: 		install

# PC
pc_boot:        pc_boot.o
		ld86 -0 -s -T $(BOOT_ADDR) -o $@ $<

pc_boot.o:      pc_boot.s
		as86 -0 -o $@ $<

pc_install:	pc_boot
		$(DD) if=$< of=$<.img ibs=32 skip=1 obs=512


# ATMega16
atmega16_boot: FORCE
		$(TOUCH) $(MACH)_boot

atmega16_install: $(MACH)_boot
		$(TOUCH) $(MACH)_boot.img


# ATMega128
atmega128_boot: FORCE
		$(TOUCH) $(MACH)_boot

atmega128_install: $(MACH)_boot
		$(TOUCH) $(MACH)_boot.img


# SoftMIPS
softmips_boot:	softmips_boot.o
		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic \
		--section-start $(MACH_CODE_NAME)=$(BOOT_ADDR) \
		--entry=$(BOOT_ADDR) -Map softmips.map \
		-o $@ $< -l$(LUTIL) -l$(LSYS) -l$(LINIT) -lgcc -lc -lgcc
		/usr/local/mips/gcc/bin/mips-objdump -S softmips_boot > tt.S

softmips_boot.o: softmips_boot.cc
		$(CXX) $(CXXFLAGS) softmips_boot.cc

softmips_boot.s: softmips_boot.cc
		$(CXX) $(CXXFLAGS) -S softmips_boot.cc

softmips_install: softmips_boot
		$(STRIP) -o $<.img $<
		cp softmips_boot.img test
		/home/david/epos/plasma/trunk/tools/convert_bin
		cp test.bin softmips_boot.img
		rm test test.bin

simulation:	softmips_boot.o
		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic \
		--section-start $(MACH_CODE_NAME)=0x00000000 \
		--entry=0x00000000 -Map softmips.map \
		-o $@ $< -l$(LUTIL) -l$(LSYS) -l$(LINIT) -lgcc -lc -lgcc
		$(STRIP) -o $@.img $@
		cp $@.img test
		/home/david/epos/plasma/trunk/tools/convert_bin
		cp test.bin $@.img
		../../../../plasma/trunk/tools/ram_image ../../../../plasma/trunk/vhdl/ram_xilinx.vhd code.txt ../../../../plasma/trunk/vhdl/ram_image.vhd
		rm test test.bin $@

install:        $(MACH)_install
		$(INSTALL) $(MACH)_boot.img $(IMG)/$(MACH)_boot
		$(CLEAN) $(MACH)_boot.img

test:

clean:
		$(CLEAN) *.s *.o *_boot *.tmp *.map

FORCE:
