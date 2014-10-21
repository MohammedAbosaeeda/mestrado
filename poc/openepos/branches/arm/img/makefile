# EPOS Image Makefile

include	../makedefs

IMAGE := $(addsuffix .img,$(APPLICATION))
FLASH := $(addsuffix .hex,$(APPLICATION))
OUTPUT := $(addsuffix .out,$(APPLICATION))

all:		$(IMAGE)

flash:		$(FLASH)

run:		$(IMAGE)
		$(EMULATOR) $(IMAGE) | $(TEE) $(OUTPUT)

debug:		$(IMAGE)
		$(EMULATOR) $(IMAGE) | $(TEE) $(OUTPUT) &
		$(DEBUGGER) $(APP)/$(APPLICATION)

clean:
		$(CLEAN) *_boot *_setup *_init *_system $(APPLICATION)
