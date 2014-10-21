# EPOS Source Makefile

include	../makedefs

SUBDIRS := utility arch mach abstraction system setup boot init

all:		$(SUBDIRS)

$(SUBDIRS):	FORCE
		(cd $@ && $(MAKE))

clean:
		make MAKE:="$(MAKECLEAN)" $(SUBDIRS)

FORCE:
