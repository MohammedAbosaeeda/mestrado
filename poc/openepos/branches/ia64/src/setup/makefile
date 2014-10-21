# EPOS Setup Makefile

include ../../makedefs

TARGET := $(if $(shell find $(MACH)*), install)

all:		$(TARGET)

$(MACH)_setup64.o: $(MACH)_setup64.cc
		$(CXX) $(CXXFLAGSO1) $<

$(MACH)_setup64:	$(MACH)_setup64.o
		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic -Ttext=$(SETUP_ADDR) -o $@ $^ -l$(LINIT) -l$(LMACH) -l$(LARCH) -l$(LUTIL) -lgcc

$(MACH)_setup.o: $(MACH)_setup.cc
		$(CXX32) $(CXXFLAGSO1) $<

$(MACH)_setup:	$(MACH)_setup.o
#		$(LD) $(LDFLAGS) -L$(CCLIB) --omagic -Ttext=$(SETUP_ADDR) -o $@ $^ -l$(LINIT) -l$(LMACH) -l$(LARCH) -l$(LUTIL) -lgcc
		# We can't use the libraries here because they could be compiled for 64bit
		# so we'll make everything without using them...		
		$(LD32) $(LDFLAGS) -L$(CCLIB) --omagic -o $@ $^

install:	$(MACH)_setup $(MACH)_setup64
ifndef 	GDB
		for f in $^ ; do \
			$(STRIP) -o $$f.tmp $$f ; \
		done
else
		for f in $^ ; do \
			$(INSTALL) $$f $$f.tmp ; \
		done
endif
		for f in $^ ; do \
			$(INSTALL) $$f.tmp $(IMG)/$$f ; \
			$(CLEAN) $$f.tmp ; \
		done

clean:
		$(CLEAN) *.o *_setup *_setup64
