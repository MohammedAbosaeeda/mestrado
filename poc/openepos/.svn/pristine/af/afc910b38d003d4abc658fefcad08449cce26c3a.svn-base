# EPOS Application Makefile

include ../makedefs

#CXXFLAGS := $(MACH_CC_FLAGS) -nostdinc -Wall -I$(INCLUDE) -g

all: install

$(APPLICATION):	$(APPLICATION).o $(LIB)/*
		$(ALD) $(ALDFLAGS) -o $@ $(APPLICATION).o
		
$(APPLICATION).o: $(APPLICATION).cc $(SRC) 
		$(ACC) $(ACCFLAGS) -o $@ $< #-g -feliminate-unused-debug-symbols

install: $(APPLICATION)
ifndef  GDB
		$(STRIP) -o $(APPLICATION).tmp $<
else
		$(INSTALL) $< $(APPLICATION).tmp
endif
		$(INSTALL) $(APPLICATION).tmp $(IMG)/$(APPLICATION)
		$(CLEAN) $(APPLICATION).tmp

clean:
		$(CLEAN) *.o $(APPLICATION)

