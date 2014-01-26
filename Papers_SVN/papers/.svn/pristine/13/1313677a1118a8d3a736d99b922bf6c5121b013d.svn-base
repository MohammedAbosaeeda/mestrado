#!/usr/bin/gnuplot -persist

# Average node energy consumption for 25 days
reset
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Number of nodes"
set ylabel "Energy consumption (mAh)"
#set yrange [0:7]
set output "../adzrp-avg_node_energy.eps"
set label "battery capacity" at 110,875 center
plot "adzrp_15min.dat" using 1:($2*60*60*24*25) with linespoints title "Original consumption", "adzrp_15min.dat" using 1:($3*60*60*24*25) with linespoints title "Energy-aware consumption", 850 with lines notitle


# Average node lifetime
reset
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Number of nodes"
set ylabel "Battery lifetime (days)"
set yrange [15:45]
set output "../adzrp-avg_node_lifetime.eps"
set label "target lifetime" at 110,26 center
plot "adzrp_15min.dat" using 1:4 with linespoints title "Original lifetime", "adzrp_15min.dat" using 1:5 with linespoints title "Energy-aware lifetime", 25 with lines notitle


# Network quality Original
reset
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Number of nodes"
set ylabel "Percentage (%)"
set yrange [0:100]
set output "../adzrp-network_original.eps"
plot "adzrp_15min.dat" using 1:($6*100) with linespoints title "Original packet delivery ratio", "adzrp_15min.dat" using 1:($8*100) with linespoints title "Original link failures", "adzrp_15min.dat" using 1:($10*100) with linespoints title "Original broken routes"


# Network quality energy aware
reset
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 18
set xlabel "Number of nodes"
set ylabel "Percentage (%)"
set yrange [0:100]
set output "../adzrp-network_ea.eps"
plot "adzrp_15min.dat" using 1:($7*100) with linespoints title "Energy-aware packet delivery ratio", "adzrp_15min.dat" using 1:($9*100) with linespoints title "Energy-aware link failures", "adzrp_15min.dat" using 1:($11*100) with linespoints title "Energy-aware broken routes"
