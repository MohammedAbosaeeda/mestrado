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

.PHONY: clean all distclean libos

all: linux-osek-mapper.o libos

libos:
	cd libos ; make
	
clean:
	rm -f linux-osek-mapper.o
	cd libos ; make clean

distclean: clean
	cd libos ; make distclean

linux-osek-mapper.o: linux-osek-mapper.c
	$(CC) $(CFLAGS) -c linux-osek-mapper.c

