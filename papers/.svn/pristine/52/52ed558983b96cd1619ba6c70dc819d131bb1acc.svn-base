#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "jm_tru_perf.eps"
set key inside left top
set yrange[1:6]
set xrange[0:6]
set ticslevel 1
set xlabel "LSB Truncation (bits)"
set ylabel "Speedup (X)" 
plot '../dat/jm_tru_perf.dat' using ($1) with linespoints title 'ForemanQCIF',\
     '../dat/jm_tru_perf.dat' using ($2) with linespoints title 'MobileCIF',\
     '../dat/jm_tru_perf.dat' using ($3) with linespoints title 'City4CIF',\
     '../dat/jm_tru_perf.dat' using ($4) with linespoints title 'Stockholm720p'
