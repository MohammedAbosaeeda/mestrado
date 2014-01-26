# EPOS-- Application Makefile

include ../makedefs

all:		install


$(APPLICATION):	$(APPLICATION).o $(LIB)/*
		$(ALD) $(ALDFLAGS) -o $@ $<
#		$(ALD) $(ALDFLAGS) -o $@ flash.o $<

$(APPLICATION).o: $(APPLICATION).cc 
#		$(ACC) $(ACCFLAGS) $(APP)/flash.cc
		$(ACC) $(ACCFLAGS) -o $@ $<

install: 	$(APPLICATION)
		$(STRIP) -o $(APPLICATION).tmp $<
		$(INSTALL) $(APPLICATION).tmp $(IMG)/$(APPLICATION)
		$(CLEAN) $(APPLICATION).tmp

clean:
		$(CLEAN) *.o $(APPLICATION)
