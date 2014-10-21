##################################################
# Project Setup
##################################################
TOP_MODULE = virtual_platform

# HLS Components instantiated
export HLS_COMPONENTS = dummy_callee dummy_caller

# Top level sources
SOURCES = $(abspath virtual_platform.cc)

# Machine sources
include ../../../mach/epossoc/rtsnoc/hw.srcs
SOURCES += $(abspath $(addprefix ../../../mach/epossoc/rtsnoc/, $(MACH_EPOSSOC_RTSNOC_SC_SRCS)))


##################################################
# Include other makefiles 
##################################################

# common definitions
include ../../../tools/systemc/Makefile.common


