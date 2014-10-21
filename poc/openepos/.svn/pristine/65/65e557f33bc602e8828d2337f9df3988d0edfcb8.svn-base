# EPOS Main Makefile

include makedefs

SUBDIRS := etc tools src app img

all:	$(SUBDIRS)

$(SUBDIRS):	FORCE
		(cd $@ && $(MAKE))

run:		FORCE
		(cd img && $(MAKE) run)

runtest:	test
		(cd img && $(MAKE) runtest)

test:		$(SUBDIRS)
		(cd src && $(MAKETEST))
		(cd img && $(MAKETEST))


# Added by mkl. Do not commit in LISHA's Subversion - for AVR8
elf2hex:
		$(OBJCOPY) -O ihex img/$(APPLICATION).img img/$(APPLICATION).hex


# Added by mkl. Do not commit in LISHA's Subversion - for AVR8
upload_atmega1281:
		sudo avrdude -p m1281 -c jtagmkII -P usb -U flash:w:img/$(APPLICATION).hex -v


# Added by mkl. Do not commit in LISHA's Subversion - for ARM7
elf2bin:
		$(OBJCOPY) -O binary img/$(APPLICATION).img img/$(APPLICATION).bin


# Added by mkl. Do not commit in LISHA's Subversion - for ARM7
upload_to_emote_usb0:
		red-bsl.py -f img/$(APPLICATION).bin -t /dev/ttyUSB0


# Added by mkl. Do not commit in LISHA's Subversion - for ARM7
upload_to_emote_usb1:
		red-bsl.py -f img/$(APPLICATION).bin -t /dev/ttyUSB1


clean:		FORCE
		(cd src && $(MAKECLEAN))
		find $(LIB) -maxdepth 1 -type f -exec $(CLEAN) {} \;
		cp gitaux/.gitignore $(LIB)		

veryclean:
		make MAKE:="$(MAKECLEAN)" $(SUBDIRS)
		find $(LIB) -maxdepth 1 -type f -exec $(CLEAN) {} \;
		find $(BIN) -maxdepth 1 -type f -exec $(CLEAN) {} \;
		find $(APP) -maxdepth 1 -type f -perm +111 -exec $(CLEAN) {} \;
		find $(IMG) -name "*.img" -exec $(CLEAN) {} \;
		find $(IMG) -name "*.out" -exec $(CLEAN) {} \;
		find $(TOP) -name "*~" -exec $(CLEAN) {} \; 
		cp gitaux/.gitignore $(LIB)
		cp gitaux/.gitignore $(BIN)		

dist:		veryclean
		find $(TOP) -name ".*project" -exec $(CLEAN) {} \;
		find $(TOP) -name CVS -type d -print | xargs $(CLEANDIR)
		find $(TOP) -name .svn -type d -print | xargs $(CLEANDIR)
		find $(TOP) -name "*.h" -print | xargs sed -i "1r $(TOP)/LICENSE" 
		find $(TOP) -name "*.cc" -print | xargs sed -i "1r $(TOP)/LICENSE" 
		sed -e 's/^\/\//#/' LICENSE > LICENSE.mk
		find $(TOP) -name "makedefs" -print | xargs sed -i "1r $(TOP)/LICENSE.mk" 
		find $(TOP) -name "makefile" -print | xargs sed -i "1r $(TOP)/LICENSE.mk"
		$(CLEAN) LICENSE.mk 
		sed -e 's/^\/\//#/' LICENSE > LICENSE.as
		find $(TOP) -name "*.S" -print | xargs sed -i "1r $(TOP)/LICENSE.as" 
		$(CLEAN) LICENSE.as 

FORCE:
