cd $EPOS
application= "philosophers_dinner"

gnome-terminal --command 
rm report.log
rm time.log
make APPLICATION="philosophers_dinner" | grep warning > original_trace.log
make | grep warning > original_trace.log


cp app/philosophers_dinner_traits.h include/traits_original.h

datainicial=`date +%s`
echo "0;$datainicial;;" >> time.log 

echo "--------TEST REPORT--------" >> report.log
echo `grep -m 1 "^APPLICATION " makedefs` >> report.log

for ((total=0;total <100;total++))
    do
	echo "$total;$tempo;;"  >> time.log 
        cp app/philosophers_dinner_traits.h include/traits_original.h
        rand_n=$[19+RANDOM %170]
        echo "ORIGINAL TRAITS LINE = `cat app/philosophers_dinner_traits.h | head -n $rand_n | tail -n 1`" >> report.log
        cat app/philosophers_dinner_traits.h | head -n $rand_n | tail -n 1| grep false
        if [$? =="1"]; then
            sed -i "$rand_n s/= false/= true/" app/philosophers_dinner_traits.h
       else
            sed -i "$rand_n s/= true/= false/" app/philosophers_dinner_traits.h
        fi
echo "MODIFYED TRAITS LINE = $rand_n `cat app/philosophers_dinner_traits.h | head -n $rand_n | tail -n 1`" >> report.log

echo "------------------ COMPILING ERRORS " >> report.log
datafinal=`date +%s`
soma=`expr $datafinal - $datainicial`
resultado=`expr 10800 + $soma`
tempo=`date -d @$resultado +%H:%M:%S`

datainicial= $datafinal

make APPLICATION="philosophers_dinner"
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
cp include/traits_original.h app/philosophers_dinner_traits.h
done
echo "$total;$tempo;;" >> time.log 

echo "END"
