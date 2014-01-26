 echo "ORIGINAL TRAITS LINE = `cat include/traits.h | head -n 13 | tail -n 1`" 

 echo "FALSE = `cat include/traits.h | head -n 13 | tail -n 1 | grep false`" 
echo "a hora da verdade        $?"
if [ $? == "0" ]; then
 echo "SIMMMMM"
fi
 echo "TRUE = `cat include/traits.h | head -n 13 | tail -n 1 | grep false`" 
echo "a hora da verdade        $?"
if [ $? == "0" ]; then
 echo "SIMMMMM"
fi

echo "fora do echo"
 echo "FALSE ="
cat include/traits.h | head -n 13 | tail -n 1 | grep false
if [ $? == "0" ]; then
 echo "SIMMMMM"
fi
 echo "true ="
cat include/traits.h | head -n 13 | tail -n 1 | grep true
echo "a hora da verdade        $?"
if [ $? == "0" ]; then
 echo "SIMMMMM"
fi

#tail -n 100 /var/log/somelog|grep someword

for ((total=0;total <100;total++))
    do
       echo $[RANDOM%100]  >> numbers
done
