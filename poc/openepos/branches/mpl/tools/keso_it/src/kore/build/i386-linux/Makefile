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

.PHONY: clean all distclean
	
SRC=$(wildcard ../../*.c)
DEPS=$(SRC:%.c=%.dep)
OBJSBASE=$(SRC:../../%.c=%.o)
VPATH=../..

all: libKESO-$(ARCH).a

libKESO-$(ARCH).a: $(OBJSBASE) ../../arch/$(ARCH)/linux_syscalls.o
	ar r libKESO-$(ARCH).a $(OBJSBASE) ../../arch/$(ARCH)/linux_syscalls.o

clean:
	rm -f $(OBJSBASE)

distclean: clean
	rm -f libKESO-$(ARCH).a

.c.o:
	$(CC) $(CFLAGS) -c -o $@ $<

-include $(DEPS)

