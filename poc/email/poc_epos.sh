#!/bin/bash
EPOS_DIR=$1

cd $EPOS_DIR/src/abstraction

app=dmec_app;
trait=NUM_WORKERS;
trait_line=57;
min=1;
max=15;
debug=false;
failure=0;
success=0;

echo "~*~*~*~*~* TEST REPORT ~*~*~*~*~*" >> report.log
echo "Application: ${app}" >> report.log

for test_file in ${app}_test.cc 
do
trait=`echo ${app}_traits.h`
failure=0
success=0
echo "${app}_traits.h"
if [ -e $trait ]; then
cp ${trait} ${trait}.bkp
else
cp ../../include/system/traits.h ${app}_traits.h
fi
if [ ${debug} ]; then
sed -i '/main()/,/return *;*}/ {s/return/cout << "****TAP - test successful" <<endl;return/g}' ${application}.cc
fi
echo "ORIGINAL TRAIT LINE = `cat include/traits.h | head -n $trait_line | tail -n 1`" >> report.log
for ((total=${min}; total < ${max} ;total++))
do 
sed -i "$trait_line s/ 6/$total/" include/traits.h
echo "MODIFYED TRAIT LINE = $trait_line `cat include/traits.h | head -n $rand_n | tail -n 1`" >> report.log
log_name=${app}_trait_${trait}.log
cd $EPOS_DIR
`make automated_test APPLICATION=${app} 3>&1 1>>log/${log_name} 2>&1`
if grep -q '****TAP - test successful' log/${log_name}; then
success=$((success+1))
rm log/${log_name}
else
failure=$((failure+1))
fi
cd $EPOS_DIR/src/abstraction
done
echo "Successful tests:${success} - Failed tests:${failure}">> report.log
done
