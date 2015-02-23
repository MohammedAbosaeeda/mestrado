cd $EPOS
gnome-terminal --command 
#rm report.log
make veryclean all
make | grep warning > original_trace.log

cp include/traits.h include/traits_original.h
echo "--------TEST REPORT--------" >> report.log
echo `grep -m 1 "^APPLICATION " makedefs` >> report.log

for ((total=0;total <100;total++))
    do
        cp include/traits.h include/traits_original.h
        rand_n=$[19+RANDOM %170]
        echo "ORIGINAL TRAITS LINE = `cat include/traits.h | head -n $rand_n | tail -n 1`" >> report.log
        cat include/traits.h | head -n $rand_n | tail -n 1| grep false
        if [$? =="1"]; then
            sed -i "$rand_n s/= false/= true/" include/traits.h
       else
            sed -i "$rand_n s/= true/= false/" include/traits.h
        fi
echo "MODIFYED TRAITS LINE = $rand_n `cat include/traits.h | head -n $rand_n | tail -n 1`" >> report.log

echo "------------------ COMPILING ERRORS " >> report.log
make veryclean all
make | grep warning > test_trace.log


#diff -w original_trace.log  test_trace.log >> report.log
#test=$[diff -q < (sort original_trace.log| uniq) <  (sort test_trace.log| uniq)]
#echo "teste se eh igual"+$test
#if [test -eq 0]; then
diff -w original_trace.log  test_trace.log >> report.log 
#fi
#rm test_trace.log

#donne
"----OK"
cp include/traits_original.h include/traits.h
done

echo "END"
