#!/usr/bin/gnuplot -persist
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18 
set xlabel "Number of Nodes" 
set ylabel "Energy (J)" 
set yrange [0:18000]
set xtics 20
set label "battery capacity" at 110,9600 center
plot [20:200] "../dat/adzrp-energy_measurements.dat" using 1:($2*3*3600/1000*2400) with linespoints title "AD-ZRP", "../dat/adzrp-energy_measurements.dat" using 1:($3*3*3600/1000*2400) with linespoints title "Energy-aware AD-ZRP", 9180 with lines notitle
