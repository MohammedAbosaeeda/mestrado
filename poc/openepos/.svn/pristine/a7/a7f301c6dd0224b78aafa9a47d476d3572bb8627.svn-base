include $(EPOS)/makedefs

CCFLAGS = -DUNIX -I. -I$(EPOS)/include  -DUNIX -c -std=c99 -O2 -Wall -nostdinc -ffunction-sections 
CC		=  $(COMP_PREFIX)gcc

all: 	$(LIBVM)

nvmfile.o: nvmfile.cc nvmdefault.h

$(LIBVM): $(LIBVM)(array.o error.o heap.o loader.o\
	 native_impl.o nvmcomm1.o\
	 nvmcomm2.o nvmfile.o nvmstring.o\
	 stack.o vm.o NanoVM.o native_formatter.o\
	 native_stdio.o native_thread.o \
	 threadmapper.o	native_alarm.o\
	 threadwrapper.o native_semaphore.o \
		native_avr.o) 

#	ld -i array.o debug.o error.o heap.o loader.o native_thread.o native_formatter.o native_impl.o native_lcd.o native_stdio.o nvmcomm1.o nvmcomm2.o nvmfile.o nvmstring.o stack.o vm.o NanoVM.o -o eposjava.o


#install: eposjava.o
#	$(INSTALL) eposjava.o $(LIB)/

clean: 
	rm -f *.o
	
