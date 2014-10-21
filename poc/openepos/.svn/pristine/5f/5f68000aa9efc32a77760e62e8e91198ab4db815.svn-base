# EPOS-- Boot Makefile

include $(EPOS)/makedefs

all: 		install

#PC
pc_boot.o:      pc_boot.s
		as86 -0 -o $@ $<

pc_boot:        pc_boot.o
		ld86 -0 -s -T $(BOOT_ADDR) -o $@.tmp $<
		$(DD) if=$@.tmp of=$< ibs=32 skip=1 obs=512
		$(CLEAN) $@.tmp

pc_install:	pc_boot


#ATMega16
atmega16_boot: FORCE
		$(TOUCH) $(MACH)_boot

atmega16_install: $(MACH)_boot
		$(TOUCH) $(MACH)_boot.img


#ATMega128
atmega128_boot: FORCE
		$(TOUCH) $(MACH)_boot

atmega128_install: $(MACH)_boot
		$(TOUCH) $(MACH)_boot.img

# LEON2
leon2_boot:	FORCE
		$(TOUCH) $(MACH)_boot

#leon2_boot:	leon2_boot.o
#		$(MOVE)	$< $(MACH)_boot.o
#		$(INSTALL) $^ $@
#		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic \
#		--section-start $(MACH_CODE_NAME)=$(BOOT_ADDR) \
#		--entry=$(BOOT_ADDR) \
#		-o $@ $< -l$(LUTIL) -l$(LSYS) -l$(LINIT) -lgcc -lc -lgcc

leon2_install:	leon2_boot leon2_boot.o


install:        $(MACH)_install
		$(INSTALL) $(MACH)_boot $(IMG)/$(MACH)_boot

test:

clean:
		$(CLEAN) *.s *.o *_boot *.tmp

FORCE:
