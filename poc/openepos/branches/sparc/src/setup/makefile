# EPOS-- Setup Makefile

include $(EPOS)/makedefs

all:		install

$(MACH)_setup:	$(MACH)_setup.o 
		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic \
		--section-start $(MACH_CODE_NAME)=$(SETUP_ADDR) \
		--entry=$(SETUP_ADDR) \
		-o $@ $< -l$(LUTIL) -l$(LSYS) -l$(LINIT) -lgcc -lc -lgcc

$(MACH)_install:$(MACH)_setup
		$(STRIP) -o $<.img $<


# LEON2
leon2_setup:	FORCE
		$(TOUCH) $(MACH)_setup
#leon2_setup:	FORCE
#		$(TOUCH) $(MACH)_setup.img

#leon2_setup:	leon2_setup.o
#		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic \
#		--section-start $(MACH_CODE_NAME)=$(BOOT_ADDR) \
#		--entry=$(BOOT_ADDR) \
#		-o $@ $< -l$(LUTIL) -l$(LSYS) -l$(LINIT) -lgcc -lc -lgcc

leon2_install:	leon2_setup leon2_setup.o


install:        $(MACH)_install
		$(INSTALL) $(MACH)_setup $(IMG)/$(MACH)_setup
		$(CLEAN) $(MACH)_setup.img

test:


clean:
		$(CLEAN) *.o *_setup

FORCE:
