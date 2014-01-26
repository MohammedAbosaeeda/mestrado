#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "bd_speedup.eps"
set key inside left top
set yrange[2.85:2.95]
set xrange[1:4]
set xtics ("70" 1, "120" 2, "2000" 3, "3500" 4)
set xlabel "Bitrate (Mbit/s)"
set ylabel "Speedup (X)" 
plot '../dat/bd_speedup.dat' using ($1) with linespoints title 'Proposal speedup'
