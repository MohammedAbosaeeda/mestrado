# EPOS-- Application Makefile

include $(EPOS)/makedefs

all:		install


$(APPLICATION):	$(APPLICATION).o
		$(ALD) $(ALDFLAGS) -o $@ $<

$(APPLICATION).o: $(APPLICATION).cc $(SRC)/system/$(MACH)_system
		$(ACC) $(ACCFLAGS) -o $@ $<

leon2_app:	leon2_app.o
		$(ALD) $(ALDFLAGS) -o $@ $(SRC)/boot/leon2_boot.o $(SRC)/setup/leon2_setup.o $<


install: 	$(APPLICATION)
		$(STRIP) -o $(APPLICATION).img $<
		$(INSTALL) $(APPLICATION).img $(IMG)/$(APPLICATION)
		$(CLEAN) $(APPLICATION).img

clean:
		$(CLEAN) *.o $(APPLICATION)
