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

all: libos 

libos: 
	$(CC) $(CFLAGS) -c interrupt.c
	$(CC) $(CFLAGS) -c event.c

clean:
	rm -f interrupt.o event.o
	
distclean: clean
		

