#!/usr/bin/gnuplot -persist

# All solutions
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Residual battery charge (%)"
set ylabel "BET Utilization (%)"
#set yrange [60:61]
#set xrange [0:0.003]
#set label "Solution space" at 110,26 center
set output "../../adzrp-solutions.eps"
plot "data.dat" using ($3*100):((1-$2)*100) notitle

# BET in freq
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Collector task frequency (Hz)"
set ylabel "BET Utilization (%)"
#set yrange [0:100]
set xrange [0:200]
#set label "Solution space" at 110,26 center
set output "../../adzrp-bet_freq.eps"
plot "data.dat" using 1:((1-$2)*100) notitle

# Batt in freq
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Collector task frequency (Hz)"
set ylabel "Residual battery charge (%)"
#set yrange [0:100]
set xrange [0:200]
#set label "Solution space" at 110,26 center
set output "../../adzrp-batt_freq.eps"
plot "data.dat" using 1:($3*100) notitle

# Lost in freq
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Collector task frequency (Hz)"
set ylabel "Lost Hard Deadlines (%)"
#set yrange [0:100]
set xrange [0:200]
#set label "Solution space" at 110,26 center
set output "../../adzrp-lost_freq.eps"
plot "data.dat" using 1:($4*100) notitle

