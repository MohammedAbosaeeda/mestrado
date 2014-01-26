#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "bd_perf.eps"
set key inside left top
set yrange[250:1200]
set xrange[0:4]
set xtics ("0" 0, "70" 1, "120" 2, "2000" 3, "3500" 4)
set xlabel "Bitrate (Mbit/s)"
set ylabel "Encoding time (s)" 
plot '../dat/bd_perf.dat' using ($1) with linespoints title 'Original',\
     '../dat/bd_perf.dat' using ($2) with linespoints title 'Proposal'
