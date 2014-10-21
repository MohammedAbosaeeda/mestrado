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

all: linux_syscalls.o

clean:
	rm -f linux_syscalls.o

distclean: clean

linux_syscalls.o: linux_syscalls.S linux_syscalls.h
	$(CC) -c linux_syscalls.S 

