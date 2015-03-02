#!/bin/bash

cd $EPOS/src/abstraction

software_architectures=('LIBRARY' 'BUILTIN' 'KERNEL')
hardware_architectures=('ia32' 'armv7')

#Para cada arquivo de tese, monte o nome do traits.
#Se o arquivo existir, modifique-o, senão modifique o arquivo base, que fica na pasta raiz

for test_file in *_test.cc 
do
   test_trait=`echo $test_file | cut -d'.' -f 1`
   test_trait=`echo ${test_trait}_traits.h`

   if [ -e $test_trait ]; then
      cp $test_trait ${test_trait}".bkp"

      #troque a arquitetura de software
      for mode in "${software_architectures[@]}" 
      do 
         sed -i "s/static const unsigned int MODE.*/static const unsigned int MODE = $mode;/g" $test_trait

         #troque a arquitetura de hardware
	 for arch in "${hardware_architectures[@]}"
	 do
            sed -i "s/static const unsigned int ARCH.*/static const unsigned int ARCH = $arch;/g" $test_trait
	
	    #troque a quantidade de cpus, ou seja, acionando multicore
   	    for ((quantidade_cpu=1;quantidade_cpu<3;quantidade_cpu++))
	    do
               sed -i "s/static const unsigned int CPUS.*/static const unsigned int CPUS = $quantidade_cpu;/g" $test_trait
	       echo "=========="
	       echo $test_file
	       echo $mode
	       echo $arch
	       echo $quantidade_cpu

               `make test APPLICATION=$test_file` > test_trace.log
	    done
	 done
      done
      cp ${test_trait}".bkp" $test_trait
   fi
done
