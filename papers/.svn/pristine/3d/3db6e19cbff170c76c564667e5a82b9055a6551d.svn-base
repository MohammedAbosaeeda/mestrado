#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "jm_sub_qual.eps"
set key inside left top
set yrange[0:1]
set xrange[0:3]
set ticslevel 1
set xtics ("1:1" 0, "2:1" 1, "4:1" 2, "8:1" 3)
set xlabel "Subsampling scheme"
set ylabel "PSNR Degradation (dB)" 
plot '../dat/jm_sub_qual.dat' using ($1) with linespoints title 'ForemanQCIF',\
     '../dat/jm_sub_qual.dat' using ($2) with linespoints title 'MobileCIF',\
     '../dat/jm_sub_qual.dat' using ($3) with linespoints title 'City4CIF',\
     '../dat/jm_sub_qual.dat' using ($4) with linespoints title 'Stockholm720p'
