# $Id: Makefile,v 1.1.2.1 2006-02-01 18:30:20 augusto Exp $

MAKE= make
SUBDIRS= src utils

all:
	@for i in $(SUBDIRS); do \
	(cd $$i; $(MAKE) all); done

clean:
	@for i in $(SUBDIRS); do \
	(cd $$i; $(MAKE) clean); done

