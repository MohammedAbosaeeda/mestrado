#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "jm_tru_qual.eps"
set key inside left top
set yrange[0:3]
set xrange[0:6]
set ticslevel 1
set xlabel "LSB Truncation (bits)"
set ylabel "PSNR Degradation (dB)"
plot '../dat/jm_tru_qual.dat' using ($1) with linespoints title 'ForemanQCIF',\
     '../dat/jm_tru_qual.dat' using ($2) with linespoints title 'MobileCIF',\
     '../dat/jm_tru_qual.dat' using ($3) with linespoints title 'City4CIF',\
     '../dat/jm_tru_qual.dat' using ($4) with linespoints title 'Stockholm720p'
