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
.SUFFIXES: .dep

SRC=$(wildcard *.c)
DEPS=$(SRC:%.c=%.dep)
OBJSBASE=$(SRC:%.c=%.o)

CC=tricore-gcc
CFLAGS=-Os -g -static -fno-builtin -I../../..

all: $(OBJSBASE)

clean:
	rm -f $(OBJSBASE) $(DEPS)

distclean: clean

.c.o:
	$(CC) $(CFLAGS) -c -o $@ $<

.c.dep:
	$(CC) -MM -o $@ $<

-include $(DEPS)

