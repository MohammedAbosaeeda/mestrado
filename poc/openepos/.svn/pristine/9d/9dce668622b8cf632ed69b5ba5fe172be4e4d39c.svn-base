# EPOS Abstractions Makefile

include ../../makedefs

OBJS := $(subst .cc,.o,$(shell find *.cc | grep -v _test | grep -v _init))
INITS := $(subst .cc,.o,$(shell find *.cc | grep _init))
TESTS := $(subst .cc,,$(shell find *.cc | grep _test))

OBJS_INIT := $(subst .cc,.o,$(shell find ../../../unified/components/src/ -mindepth 1 -name "*.cc" -type f))

all:		$(LIBSYS) $(LIBINIT)

$(LIBSYS):	$(LIBSYS)($(OBJS) $(OBJS_INIT))	

$(LIBINIT):	$(LIBINIT)($(INITS))

test:		$(TESTS)

clean:
		$(CLEAN) *.o *_test
