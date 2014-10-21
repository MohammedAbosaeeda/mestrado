##################################################
# Project Setup
##################################################
TOP_MODULE = platform

# Top level sources
TOP_SRCS = \
platform.vhd \
platform.ucf \
../rsp.vhd
SOURCES = $(abspath $(TOP_SRCS))

# Machine sources
include ../../../mach/epossoc/rtsnoc/hw.srcs
SOURCES += $(abspath $(addprefix ../../../mach/epossoc/rtsnoc/, $(MACH_EPOSSOC_RTSNOC_SRCS)))

# HLS Components instantiated
export HLS_COMPONENTS = add
#HLS sources
include ../../../framework/catapult/top_level/hw.srcs
SOURCES += $(abspath $(addprefix ../../../framework/catapult/top_level/, $(FRAMEWORK_CATAPULT_TOP_SRCS)))


##################################################
# Project Properties
##################################################
PROJECT_PROPERTIES = \
family Virtex6 \
device xc6vlx240t \
package ff1156 \
speed -1 \
top_level_module_type "HDL" \
synthesis_tool "XST (VHDL/Verilog)" \
simulator "Modelsim-SE Mixed" \
"Preferred Language" "Verilog" \
"Enable Message Filtering" FALSE \
"Display Incremental Messages" FALSE 

PROM_DEVICE = xcf128x 

PROM_DEVICE_FLAGS = -data_width 16

##################################################
# Process Properties
##################################################
SYNTHESIZE_PROPERTIES = ""

TRANSLATE_PROPERTIES = ""

MAP_PROPERTIES = \
"Generate Detailed Map Report" TRUE \
"Enable Multi-Threading" 2

PLACE_ROUTE_PROPERTIES = \
"Enable Multi-Threading" 2

STATIC_TIMING_PROPERTIES = ""

GEN_PROG_FILE_PROPERTIES = \
"Create Binary Configuration File" TRUE \
"Create ReadBack Data Files" TRUE \
"Create Mask File" TRUE

SIM_MODEL_PROPERTIES = ""

##################################################
# Include other makefiles 
##################################################

# common definitions
include ../../../tools/xilinx/Makefile.common

