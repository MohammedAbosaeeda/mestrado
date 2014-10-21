# High-level synthesis makefile

SUBDIRS := hls/add hls/mult

.PHONY: all clean $(SUBDIRS) 

all: $(SUBDIRS)

$(SUBDIRS):
	@echo "Compiling high-level C++ into rtl for component $@"
	make all --directory=$@

clean:
	@for dir in $(SUBDIRS); do \
		if [ -d $$dir ]; then (cd $$dir; make clean) fi \
	done

