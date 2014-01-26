set terminal postscript eps monochrome dashed "Helevetica" 24
set output 'latency.eps'
set noclip points
set clip one
set noclip two
set border
set boxwidth
set dummy x,y
set format x "%g"
set format y "%g"
set format z "%g"
set nogrid
set key 4.5, 74
set nolabel
set noarrow
set nologscale
set offsets 0, 0, 0, 0
set nopolar
set angles radians
set noparametric
set view 60, 30, 1, 1
set samples 100, 100
set isosamples 10, 10
set surface
set nocontour
set clabel
set nohidden3d
set cntrparam order 4
set cntrparam linear
set cntrparam levels auto 5
set cntrparam points 5
set size 1,1
set data style points
set function style lines
set xzeroaxis
set yzeroaxis
set tics in
set ticslevel 0.5
set xtics ("4" 0, "8" 1, "16" 2, "32" 3, "64" 4, "128" 5, "256" 6, "512" 7, "1K" 8, "2K" 9, "4K" 10)
set ytics
set ztics
set title "" 0,0
set notime
set rrange [-0 : 10]
set trange [-5 : 5]
set urange [-5 : 5]
set vrange [-5 : 5]
set xlabel "message size (bytes)" 0,0
set xrange [0 : 10]
set ylabel "time (us)" 0,0
set yrange [0 : 80]
set zlabel "" 0,0
set zrange [-10 : 10]
set autoscale r
set autoscale t
set noautoscale   
set autoscale z
set zero 1e-08
plot [0:10] '< cut -f2 epos_native.dat' title "Native ix86 EPOS" with linespoints 1, '< cut -f2 epos_linux.dat' title "Linux guest EPOS" with linespoints 4
