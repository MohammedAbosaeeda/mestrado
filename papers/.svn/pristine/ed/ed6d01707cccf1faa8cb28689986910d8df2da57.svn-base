#!/usr/bin/gnuplot -persist
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 14 
set xlabel "Sample" 
set yrange [38:55]
plot [0.1:1000] "../dat/interference.dat" using ($6) with lines title ""
