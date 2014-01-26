#!/bin/sh


for t in `ls *.tex`; do
	pdflatex $t;
	pdfcrop `echo $t | sed 's/\(.*\)\.tex$/\1\.pdf/'` `echo $t | sed 's/\(.*\)\.tex$/\1\.pdf/'`
	rm `echo $t | sed 's/\(.*\)\.tex$/\1\.aux/'`;
	rm `echo $t | sed 's/\(.*\)\.tex$/\1\.log/'`;
done;

cd optimizations/hypothetic_app
./generate_plots.gnu
cd ../adzrp_1h
./generate_plots.gnu

cd ..
cd ..

for f in `ls *.eps`; do
	epstopdf $f;
done;

