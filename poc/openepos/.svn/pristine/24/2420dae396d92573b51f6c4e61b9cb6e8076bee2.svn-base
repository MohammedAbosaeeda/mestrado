.PHONY: all clean test rtest

JOSEKSRC:=$(shell find -name '*.java')
JOSEKOPTS = -f keso_main_complex.oil 
JOSEKOPTS += -h `r=\`uname -m\`; if [ "$$r" == "i686" ]; then echo x86_32; else if [ "$$r" == "x86_64" ]; then echo x86_64; else echo unknown; fi fi`
JOSEKOPTS += -r
#JOSEKOPTS += -D signals
#JOSEKOPTS += -D timer
#JOSEKOPTS += -D debug
#JOSEKOPTS += -D codecomments
#JOSEKOPTS += -D stackprotector
#JOSEKOPTS += -D stackprotector_simple
#JOSEKOPTS += -D assertions
#JOSEKOPTS += -D saveram

CTAGBIN=/usr/bin/ctags-exuberant
GCJ=gcj-3.4
#GCJ=gcj-4.1

all: classes/josek/JOsek.class

classes/josek/JOsek.class: parser/ConfigReader.java $(JOSEKSRC)
	$(JAVAC) -d classes -sourcepath . $(shell find -name '*.java')
#	$(JAVAC) -Xlint:unchecked -d classes -sourcepath . $(JOSEKSRC)

josek.jar: classes/josek/JOsek.class
	#jar -cvfm josek.jar manifest -C classes/ josek/
	jar -cvfm0 josek.jar manifest -C classes/ josek/

jc: josek.jar
	jdxc -j jc josek.jar 

josek-bin: josek.jar
	$(GCJ) -O2 -fno-store-check --main=josek.JOsek -o josek-bin josek.jar

clean:
	rm -rf classes/*
	rm -f parser/*.java
	make -C generated distclean

parser/ConfigReader.java: parser/ConfigReader.jj
	javacc -OUTPUT_DIRECTORY=parser/ parser/ConfigReader.jj

test: classes/josek/JOsek.class
	java -cp classes josek.JOsek $(JOSEKOPTS) 
	make -C generated

rtest: all
	cd regression ; ./regression_test

doc:
	xpdf ../../../os223.pdf &

oildoc:
	xpdf ../../../oil25.pdf &

tags:
	$(CTAGBIN) `find . -name "*.java"` 


