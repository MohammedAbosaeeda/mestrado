#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "boat1_power_consumption.eps"
set key inside left top
set yrange[1:6]
set xrange[0:7]
set ticslevel 1
set xlabel "Execution time (s)"
set ylabel "Power (W)" 
plot '../dat/boat1_power_consumption.dat' using ($1) with linespoints title 'KESO+EPOS',\
     '../dat/boat1_power_consumption.dat' using ($2) with linespoints title 'NanoVM+EPOS',\
     '../dat/boat1_power_consumption.dat' using ($3) with linespoints title 'KESO+JOSEK',\
     '../dat/boat1_power_consumption.dat' using ($4) with linespoints title 'NanoVM+HW',\
     '../dat/boat1_power_consumption.dat' using ($5) with linespoints title 'Darjeeling+TinyOS'
