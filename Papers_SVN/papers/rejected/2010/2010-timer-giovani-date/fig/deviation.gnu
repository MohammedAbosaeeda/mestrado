#!/usr/bin/gnuplot -persist
#
#    
#    	G N U P L O T
#    	Version 4.2 patchlevel 3 
#    	last modified Mar 2008
#    	System: Linux 2.6.27.24-170.2.68.fc10.i686
#    
#    	Copyright (C) 1986 - 1993, 1998, 2004, 2007, 2008
#    	Thomas Williams, Colin Kelley and many others
#    
#    	Type `help` to access the on-line reference manual.
#    	The gnuplot FAQ is available from http://www.gnuplot.info/faq/
#    
#    	Send bug reports and suggestions to <http://sourceforge.net/projects/gnuplot>
#    
# set terminal wxt 0
# set output
set terminal postscript eps noenhanced defaultplex \
   leveldefault monochrome blacktext \
   dashed dashlength 1.0 linewidth 1.0 butt \
   palfuncparam 2000,0.003 \
   "Helvetica" 14 
set bar 2.000000
set boxwidth 0.5 relative
set xtics border in scale 0,0 nomirror  ("Periodic 1000 Hz" 1.00000, "Periodic 500 Hz" 2.00000, "Periodic 50 Hz" 3.00000, "Single-shot" 4.00000)
set ylabel "Period (us)" 
set xrange [0.5:4.5]
set yrange [38.5:43]
plot "../dat/deviation.dat" with boxerrorbars title ""
