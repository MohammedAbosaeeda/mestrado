# EPOS Source Makefile

include	../makedefs

SUBDIRS := utility arch mach abstraction setup boot system init

all:		$(SUBDIRS)

$(SUBDIRS):	FORCE
		(cd $@ && $(MAKE))

clean:
		make MAKE:="$(MAKECLEAN)" $(SUBDIRS)

FORCE:
