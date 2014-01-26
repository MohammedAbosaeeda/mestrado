#!/usr/bin/gnuplot -persist
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18 
set xlabel "Number of Nodes" 
set ylabel "Energy per Received Byte (mJ)" 
set yrange [0:2]
set format y "%1.1f"
set xtics 1
plot [2:5] "../dat/cmac-energy_measurements.dat" using 1:($5*1000) with linespoints title "CSMA-CA", "../dat/cmac-energy_measurements.dat" using 1:($6*1000) with linespoints title "Beacon"
