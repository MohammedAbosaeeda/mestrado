#!/bin/bash
EPOS_DIR=blah
START=`date +%s`
cd $EPOS_DIR/src/abstraction 

email_body=$EPOS_DIR/report.log 
num_workers_options=(1 2 3 4 5 6 7 8 9 10);
quantum_options=(10000);
failure=0; 
success=0;
sed -i "s/include makedefs/include makedefs \n\nautomated_test: \n\t\$\(INSTALL\) \$\(SRC\)\/abstraction\/\$\(APPLICATION\).cc \$\(APP\) \n\t\$\(INSTALL\) \$\(SRC\)\/abstraction\/\$\(APPLICATION\)_traits.h \$\(APP\) \n\t\$\(MAKETEST\) clean1 run1 \n\t\$\(CLEAN\) \$\(APP\)\/\$\(APPLICATION\)\* \n/g" makefile
sed -i "s/$(MACH_PC)_CC_FLAGS.*/(MACH_PC)_CC_FLAGS     := -g -Wa,--32/g" makedefs 

echo "= = = = = TEST REPORT = = = = =" >> ${email_body}
echo "= Configurations =">> ${email_body}
echo "QUANTUM: 10000" >> ${email_body}
echo "NUM_WORKERS: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10" >> ${email_body}
echo "">> ${email_body}

for test_file in semaphore_test.cc
do 
	application=`echo ${test_file} | cut -d'.' -f 1` 
	trait=`echo ${application}_traits.h`
	failure=0
	success=0
	echo "${application}_traits.h"
 	sed -i '/main()/,/return *;*}/ {s/return/cout << "****TAP - test successful" <<endl;return/g}' ${application}.cc 
	if [ -e $trait ]; then 
		cp ${trait} ${trait}.bkp 
	else 
		 cp ../../include/system/traits.h ${application}_traits.h 
	fi 
	for quantum in "${quantum_options[@]}" do 
		sed -i "/template <> struct Traits<Thread>: public Traits<void>/,/template <> struct/ {s/static const unsigned int QUANTUM.*/static const unsigned int QUANTUM = $quantum;/g}" $trait 
		for num_workers in "${num_workers_options[@]}" do 
			sed -i "s/static const unsigned int NUM_WORKERS.*/static const unsigned int NUM_WORKERS = $num_workers;/g}" $trait 
			echo "= Application:${application}"
			echo "QUANTUM: $quantum NUM_WORKERS: $num_workers "
			log_name=${application}_QUANTUM_$quantum_NUM_WORKERS_$num_workers.log
			cd $EPOS_DIR
			`make automated_test APPLICATION=${application} 3>&1 1>>log/${log_name} 2>&1`
			if grep -q '****TAP - test successful' log/${log_name}; then
				success=$((success+1))
				mv log/${log_name} log/_success_${log_name}
			else
				failure=$((failure+1))
			fi
			cd $EPOS_DIR/src/abstraction
		done
	done

	echo "">> ${email_body}
	echo "= Application: "${application}" =">> ${email_body}
	echo "Successful tests:${success} - Failed tests:${failure}" >> ${email_body}
	if [ "${failure}" -gt 0 ]; then
		echo "Failed tests execution log and debugging information on attachment." >> ${email_body}
	fi
done
END=`date +%s`
DIFF=$END - $START
echo `date -r $DIFF +'%H:%M:%S'` >> ${email_body}
