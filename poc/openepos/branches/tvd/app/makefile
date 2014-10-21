# EPOS-- Application Makefile

include $(EPOS)/makedefs

all:		install


#$(APPLICATION):	$(APPLICATION).o
#		$(ALD) $(ALDFLAGS) -o $@ $<

$(APPLICATION):	$(APPLICATION).o
		$(ALD) $(ALDFLAGS) -o $@ Wrapper.o WPthread.o WStream.o Util.o mux2/src/*.o $<
#		$(ALD) $(ALDFLAGS) -o $@ $<

$(APPLICATION).o: $(APPLICATION).cc $(SRC)/system/$(MACH)_system
		$(ACC) $(ACCFLAGS) wrapper/WStream.cc
		$(ACC) $(ACCFLAGS) wrapper/WPthread.cc
		$(ACC) $(ACCFLAGS) wrapper/Util.cc
		$(ACC) $(ACCFLAGS) wrapper/Wrapper.cc
		$(ACC) $(ACCFLAGS) -o $@ $<

install: 	$(APPLICATION)
		$(STRIP) -o $(APPLICATION).img $<
		$(INSTALL) $(APPLICATION).img $(IMG)/$(APPLICATION)
		$(CLEAN) $(APPLICATION).img

clean:
		$(CLEAN) *.o $(APPLICATION)
