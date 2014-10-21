#
#  Copyright (C) 2005 Christian Wawersich 
#
#  This file is part of the KESO Operating System.
#
#  It is distributed in the hope that it will be useful, but
#  WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
#
#  Please contact wawi@cs.fau.de for more info.
#

.PHONY: distclean clean all libKESO-i386-linux.a libKESO-tricore-osek.a
.SUFFIXES: $(SUFFIXES) .dep

CC=gcc
CFLAGS=-Os -ansi -g -static -Wall -fno-builtin -I$(PWD)

KESOLIBS=libKESO-i386-linux.a libKESO-tricore-osek.a libKESO-avr-mdsa.a
SRC=$(wildcard *.c)
DEPS=$(SRC:%.c=%.dep)

all: libKESO-i386-linux.a libKESO-tricore-osek.a libKESO-avr-mdsa.a
	
libKESO-i386-linux.a: $(DEPS)
	cd arch/i386-linux  ; make ARCH=i386-linux
	cd build/i386-linux ; make ARCH=i386-linux
	rm -f libKESO-i386-linux.a ; ln -s build/i386-linux/libKESO-i386-linux.a .

libKESO-avr-mdsa.a: $(DEPS)
	cd arch/avr-mdsa  ; make ARCH=avr-mdsa CC=avr-gcc
	cd build/avr-mdsa ; make ARCH=avr-mdsa CC=avr-gcc
	rm -f libKESO-avr-mdsa.a ; ln -s build/avr-mdsa/libKESO-avr-mdsa.a .
	
libKESO-tricore-osek.a: $(DEPS)
	cd arch/tricore-osek  ; make ARCH=tricore-osek CC=tricore-gcc
	cd build/tricore-osek ; make ARCH=tricore-osek CC=tricore-gcc
	rm -f libKESO-tricore-osek.a ; ln -s build/tricore-osek/libKESO-tricore-osek.a .

clean:
	cd arch/i386-linux    ; make ARCH=i386-linux clean
	cd build/i386-linux   ; make ARCH=i386-linux clean
	cd arch/tricore-osek  ; make ARCH=tricore-osek clean
	cd build/tricore-osek ; make ARCH=tricore-osek clean
	cd build/avr-mdsa   ; make ARCH=avr-mdsa clean
	rm -f *.dep

distclean: clean
	rm -f $(DEPS) $(KESOLIBS)
	cd arch/i386-linux    ; make ARCH=i386-linux distclean 
	cd build/i386-linux   ; make ARCH=i386-linux distclean
	cd arch/tricore-osek  ; make ARCH=tricore-osek distclean
	cd build/tricore-osek ; make ARCH=tricore-osek distclean
	cd build/avr-mdsa   ; make ARCH=avr-mdsa distclean
					
.c.dep:
	$(CC) -MM -o $@ $<

