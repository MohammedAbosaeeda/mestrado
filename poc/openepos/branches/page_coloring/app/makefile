# EPOS Application Makefile

include ../makedefs

APP_PATH := $(subst _app,,$(APPLICATION))
ISDIR := $(shell test -d $(APP_PATH) && echo 0)

ifeq ($(ISDIR), 0)
SRC := $(shell find ./$(APP_PATH)/src/ -mindepth 1 -name "*.cc" -type f)
OBJS := $(subst .cc,.do,$(SRC))
else
OBJS :=
endif

#CXXFLAGS := $(MACH_CC_FLAGS) -nostdinc -Wall -I$(INCLUDE) -g

# This is used by canneal_app parsec benchmark application
ENABLE_THREADS :=

ifeq ($(APPLICATION),canneal_app)
ENABLE_THREADS := -D ENABLE_THREADS
endif

#ifeq ($(APPLICATION),ocean_app)
#ENABLE_THREADS := -D_POSIX_C_SOURCE=200112
#endif

all:		install

.SUFFIXES: .do .cc

.cc.do :	$(SRC)
		$(CXX) $(CXXFLAGS) -o $@ $< $(ENABLE_THREADS)

$(APPLICATION):	$(APPLICATION).o $(LIB)/* $(OBJS)
		$(ALD) $(ALDFLAGS) -o $@ $(APPLICATION).o $(OBJS)

$(APPLICATION).o: $(APPLICATION).cc $(SRC)
		$(ACC) $(ACCFLAGS) -o $@ $< $(ENABLE_THREADS) -D __PARALLEL

install: 	$(APPLICATION)
ifneq ($(GDB_DEBUG), 1)
		$(STRIP) -o $(APPLICATION).tmp $<
else
		cp $< $(APPLICATION).tmp
endif
		$(INSTALL) $(APPLICATION).tmp $(IMG)/$(APPLICATION)
		$(CLEAN) $(APPLICATION).tmp

clean:
		$(CLEAN) *.o $(APPLICATION) $(OBJS)
