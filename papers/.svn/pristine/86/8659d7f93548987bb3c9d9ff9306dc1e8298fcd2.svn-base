#!/usr/bin/gnuplot -persist
#
#    
#    	G N U P L O T
#    	Version 4.2 patchlevel 4 
#    	last modified Sep 2008
#    	System: Linux 2.6.28-15-generic
#    
#    	Copyright (C) 1986 - 1993, 1998, 2004, 2007, 2008
#    	Thomas Williams, Colin Kelley and many others
#    
#    	Type `help` to access the on-line reference manual.
#    	The gnuplot FAQ is available from http://www.gnuplot.info/faq/
#    
#    	Send bug reports and suggestions to <http://sourceforge.net/projects/gnuplot>
#    
# set terminal x11 0
# set output
reset
#set output
#set terminal latex roman 10
set output
set terminal postscript eps enhanced color dashed "Helvetica" 14
set grid
set xtics 2
set ytics 2
set ztics 5
set xlabel "N de blocos seriais"
set ylabel "N de blocos paralelos"
set zlabel "Tempo medio"
#
# Comandos específicos para visualização 3D
set hidden3d
set view 58,348,0.95,0.95 #set view 72,216,0.95,0.95
set data style points
set dgrid3d 30,30,2
splot 'fig_gnuplot_overhead_test.points' using ($10):($11):($12) t"" with lines 1 #, 'fig_gnuplot_overhead_test.points' using ($10):($11):($13) t"" with lines 2
#    EOF
