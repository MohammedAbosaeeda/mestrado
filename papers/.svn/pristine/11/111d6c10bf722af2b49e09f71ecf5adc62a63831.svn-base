#!/usr/bin/gnuplot -persist
set term postscript eps enhanced "Helvetica" 14
set output "bd_psnr.eps"
set key inside left top
set yrange[33:45]
set xrange[0:4]
#set xrange[0:3500]
#set ticslevel 1
#set xtics ("0", "500", "1000", "1500", "2000", "2500", "3000", "3500")
#set xtics ("0" 0, "28" 1, "24" 2, "20" 3, "16" 4)
#set xtics (70, "28" 1, "24" 2, "20" 3, "16" 4)
#set xtics (0, 70, 120, 2000, 3500)
set xtics ("0" 0, "70" 1, "120" 2, "2000" 3, "3500" 4)
set xlabel "Bitrate (Mbit/s)"
set ylabel "PSNR (dB)" 
plot '../dat/bd_psnr.dat' using ($1) with linespoints title 'Original',\
     '../dat/bd_psnr.dat' using ($2) with linespoints title 'Proposal'
