#!/bin/bash
EPOS_DIR=/home/tinha/demo/ine5424-20141
email_body=$EPOS_DIR/report.log 
quantum_options=(BLAH 10000);
failure=0; 
success=0;

cd $EPOS_DIR 

grep -q -F "automated_test" makefile || echo "

automated_test:\n\t\$(INSTALL) \$(SRC)/abstraction/\$(APPLICATION).cc \$(APP)\n\t\$(INSTALL) \$(SRC)/abstraction/\$(APPLICATION)_traits.h \$(APP)\n\t\$(MAKETEST) clean1 run1\n\t\$(CLEAN) \$(APP)/\$(APPLICATION)*\n" >> makefile
sed -i "s/\$(MACH_PC)_CC_FLAGS.*/\$(MACH_PC)_CC_FLAGS     := -ggdb -Wa,--32/g" makedefs 

echo "= = = = = TEST REPORT = = = = =" >> ${email_body}
echo "= Configurations =">> ${email_body}
echo "QUANTUM: BLAH, 10000" >> ${email_body}
cd $EPOS_DIR/src/abstraction 

for test_file in semaphore_test.cc
do 
	application=`echo ${test_file} | cut -d'.' -f 1` 
	trait=`echo ${application}_traits.h`
	failure=0
	success=0
	echo "${application}_traits.h"
 	cp ${application}.cc ${application}.bkp
	sed -i '/main()/,/return *;*}/ {s/return/cout << "****TAP - test successful" <<endl;return/g}' ${application}.cc 
	if [ -e $trait ]; then 
		cp ${trait} ${trait}.bkp 
	else 
		 cp ../../include/system/traits.h ${application}_traits.h 
	fi 
	for quantum in "${quantum_options[@]}" 
	do 
		sed -i "/template <> struct Traits<Thread>: public Traits<void>/,// {s/^\([a-zA-Z0-9[:space:]]*QUANTUM[[:space:]]*=[[:space:]]*\).*\$/\1$quantum;/g}" $trait 

		echo "= Application:${application}"
		echo "QUANTUM: $quantum "
		log_name=${application}_QUANTUM_$quantum.log
		cd $EPOS_DIR
		`make automated_test APPLICATION=${application} 3>&1 1>>log/${log_name} 2>&1`
		if grep -q '****TAP - test successful' log/${log_name}; then
			success=$((success+1))
			mv log/${log_name} log/_success_${log_name}
		else
			failure=$((failure+1))
			screen -S qemu -X quit 1> /dev/null
			screen -S gdb -X quit 1> /dev/null
			screen -S qemu -dm bash -c  "qemu-system-i386 -fda $EPOS_DIR/img/${application}.img -serial stdio -s -S"
			sleep 1
			gdb -ex "target remote:1234" -ex "set confirm off"
		fi
		cd $EPOS_DIR/src/abstraction
	done
	echo "= Application: ${application} =">> ${email_body}
	echo "Successful tests:${success} - Failed tests:${failure}" >> ${email_body}
	if [ "${failure}" -gt 0 ]; then
		echo "Failed tests execution log and debugging information on attachment.

" >> ${email_body}
	fi
	cp ${trait}".bkp" ${trait}
	rm ${trait}".bkp"
	cp ${application}.bkp ${application}.cc
	rm ${application}.bkp
done
