GENOPTS = -D debug -D codecomments
ARCH = ia32

all: copy

clean:
	rm -f ./out/*	


copy: gen
	cp out/alarm.h           ${KESOROOTPATH}/epos--/include/
	cp out/alarm_osek.cc     ${KESOROOTPATH}/epos--/src/abstraction
	cp out/cpu.cc            ${KESOROOTPATH}/epos--/src/arch/${ARCH}/
	cp out/cpu.h             ${KESOROOTPATH}/epos--/include/arch/${ARCH}/
	cp out/os.cc             ${KESOROOTPATH}/epos--/osek/
	cp out/osek_os.h         ${KESOROOTPATH}/epos--/include/
	cp out/osek_typedefs.h   ${KESOROOTPATH}/epos--/include/
	cp out/osek_task.cc      ${KESOROOTPATH}/epos--/src/abstraction
	cp out/osek_task.h       ${KESOROOTPATH}/epos--/include/
	cp out/thread.h          ${KESOROOTPATH}/epos--/include/
	cp out/thread_osek.cc    ${KESOROOTPATH}/epos--/src/abstraction/
	cp testcase.cc           ${KESOROOTPATH}/epos--/osek/

gen:
	${JAVA} -cp ${KESOROOTPATH}/EPOS-OSEK/classes josek.JOsek -f configuration.oil -o ./out/ -b EPOS-OSEK -h ia32 -r -c ../../ ${GENOPTS}


recompile:
	cd ${KESOROOTPATH}/EPOS-OSEK/ && make
