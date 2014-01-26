#!/usr/bin/gnuplot -persist
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18 
set xlabel "Number of Nodes" 
set ylabel "Life-time (days)" 
set yrange [0:50]
set xtics 20
set label "expected lifetime" at 110,26 center
plot [20:200] "../dat/adzrp-life_time.dat" using 1:($2) with linespoints title "AD-ZRP", "../dat/adzrp-life_time.dat" using 1:($3) with linespoints title "Energy-aware AD-ZRP", 25 with lines notitle
