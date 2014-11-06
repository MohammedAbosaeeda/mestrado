#!/bin/bash
EPOS_DIR=$1

cd $EPOS_DIR/src/abstraction

email_body=$EPOS_DIR/report.log
storage_options=(512 1024 2048 3072 4096)
multithread_options=('true' 'false');
quantum_options=(10000 20000 50000 100000 200000 500000);
scheduling_options=('RR' 'FCFS');

sed -i "s/include makedefs/include makedefs \n\nautomated_test: \n\t\$\(INSTALL\) \$\(SRC\)\/abstraction\/\$\(APPLICATION\).cc \$\(APP\) \n\t\$\(INSTALL\) \$\(SRC\)\/abstraction\/\$\(APPLICATION\)_traits.h \$\(APP\) \n\t\$\(MAKETEST\) clean1 run1 \n\t\$\(CLEAN\) \$\(APP\)\/\$\(APPLICATION\)\* \n/g" makefile

#Para cada arquivo de tese, monte o nome do traits.
#Se o arquivo existir, modifique-o, senão modifique o arquivo base, que fica na pasta raiz

echo "= = = = = TEST REPORT = = = = =" >> ${email_body}
echo "= Configurations =">> ${email_body}
echo "Quantum: 10ms, 20ms, 50ms, 100ms, 200ms, 500ms">> ${email_body}
echo "Stack">> ${email_body}
echo "Heap">> ${email_body}
echo "Schedulers">> ${email_body}


for test_file in *_test.cc 
do
   application=`echo ${test_file} | cut -d'.' -f 1`
   trait=`echo ${application}_traits.h`
   failure=false
   echo "${application}_traits.h"
   echo "= = "${application}" = =">> ${email_body}


   sed -i '/main()/,/return *;*}/ {s/return/cout << "****TAP - test successful" <<endl;return/g}' ${application}.cc

   if [ -e $trait ]; then
      cp ${trait} ${trait}.bkp
   else
      cp ../../include/system/traits.h ${application}_traits.h
   fi

   for app_stack in "${storage_options[@]}"
   do
      sed -i "/template <> struct Traits<Application>: public Traits<void>/,/template <> struct/ {s/static const unsigned int STACK_SIZE.*/static const unsigned int STACK_SIZE = $app_stack;/g}" $trait

	
         for app_heap in "${storage_options[@]}"
         do
            if [ "$app_heap" -gt "$app_stack" ]; then
               sed -i "/template <> struct Traits<Application>: public Traits<void>/,/template <> struct/ {s/static const unsigned int STACK_SIZE.*/static const unsigned int STACK_SIZE = $app_heap;/g}" $trait
            fi

	    for sys_stack in "${storage_options[@]}"
	    do
	       sed -i "/template <> struct Traits<System>: public Traits<void>/,/template <> struct/ {s/static const unsigned int STACK_SIZE.*/static const unsigned int STACK_SIZE = $sys_stack;/g}" $trait

	       for sys_quantum in "${quantum_options[@]}"
	       do
	          sed -i "/template <> struct Traits<System>: public Traits<void>/,/template <> struct/ {s/static const unsigned int QUANTUM.*/static const unsigned int QUANTUM = $sys_quantum;/g}" $trait

   	          for thread_quantum in "${quantum_options[@]}"
	          do
	             sed -i "s/static const unsigned int QUANTUM.*/static const unsigned int QUANTUM = $thread_quantum;/g" $trait

		     for scheduler in "${scheduling_options[@]}"
	             do
	                sed -i "s/typedef Scheduling_Criteria::.*/typedef Scheduling_Criteria::$scheduler Criterion;/g" $trait
	    
   	                echo "Applicatin stack:${app_stack}, Application heap:${app_heap}, System stack:${sys_stack}, System heap:${sys_heap}, System quantum:${sys_quantum}, Thread quantum:${thread_quantum}, Scheduler:${scheduler}."

	                log_name=${application}_AppStack_${app_stack}_SysQuantum_${sys_quantum}_ThreadQuantum_${thread_quantum}_Scheduler_${scheduler}.log
	                cd $EPOS_DIR
	                `make automated_test APPLICATION=${application} 3>&1 1>>log/${log_name} 2>&1`
		      
                         if grep -q '****TAP - test successful' log/${log_name}; then
			    if [ !${failure} ]; then
		               echo "SUCCESSFUL" >> ${email_body}
			    fi
		         else
                             failure=true
		            echo "Test Failure! Configuration: Applicatin stack:${app_stack}, Application heap:${app_heap}, System stack:${sys_stack}, System heap:${sys_heap}, System quantum:${sys_quantum}, Thread quantum:${thread_quantum}, Scheduler:${scheduler}.">> ${email_body}
		            echo "Loading QEMU..."	            
		            echo "Execution log and debugging information on file '${log_name}'">> ${email_body}
		         fi

	                cd $EPOS_DIR/src/abstraction
                     done
                  done
	       done
            done
         done
      done
done
