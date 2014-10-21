#!/bin/bash

for f in build/*.bit;
do 
    echo "Processing $f file.."
    promgen -b -w -p bin -data_width 32 -u 0 $f -o ${f%.*}.bin
done
