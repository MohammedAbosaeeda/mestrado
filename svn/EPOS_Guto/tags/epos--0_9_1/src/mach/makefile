# EPOS-- Machine Mediators Makefile

include		$(EPOS)/makedefs

SUBDIRS := common $(MACH)

all:		$(SUBDIRS)

$(SUBDIRS):	FORCE
		(cd $@ && $(MAKE))

test:
		make MAKE:="$(MAKETEST)" $(SUBDIRS)

clean:
		make MAKE:="$(MAKECLEAN)" $(SUBDIRS)

FORCE:
