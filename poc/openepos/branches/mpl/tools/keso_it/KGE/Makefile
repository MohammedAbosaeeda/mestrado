PWD=pwd

all: crt_directory crt_jino crt_epslib crt_swt crt_kge crt_workbench
	find bin -name ".svn" | xargs rm -rf

clean:
	rm -rf bin

crt_directory:
	mkdir -p bin/workbench/ ; mkdir -p bin/kge/
	cp params/* bin/.

crt_jino:
	cd ../src/; make builder
	cp -r ../src/builder/classes bin/jino

crt_epslib:
	find epslib -name "*.java" | xargs javac -d bin/kge

crt_kge:
	find KesoEditor -name "*.java" | xargs javac -d bin/kge -classpath swt-linux-x86/swt.jar:bin/kge:bin/jino
	mkdir -p bin/kge/keso/editor/icons/
	cp KesoEditor/keso/editor/icons/* bin/kge/keso/editor/icons/
	mkdir -p bin/kge/keso/help/
	cp -r KesoEditor/keso/help/* bin/kge/keso/help/

crt_swt:
	cp -r swt-linux-x86 bin/.

crt_workbench:
	cp -r ../src/libs bin/workbench/.
	#cp -r ../src/buildstamps bin/workbench/.
	cp -r ../src/rc bin/workbench/.
	#cp -r ../src/templates bin/workbench/.
	#cp -r ../src/tmp bin/workbench/.
	#cp -r ../src bin/serverworkbench
	#rm -rf bin/serverworkbench/builder

tags:
	ctags-exuberant `find epslib KesoEditor -name "*.java"`

	
	
