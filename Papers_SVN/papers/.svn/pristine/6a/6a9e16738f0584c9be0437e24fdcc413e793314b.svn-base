set terminal postscript eps monochrome dashed "Helevetica" 28
set output 'pentium.eps'
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
set key
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
set xtics 1,1,4
set ytics
set ztics
set title "" 0,0
set notime
set rrange [-0 : 10]
set trange [-5 : 5]
set urange [-5 : 5]
set vrange [-5 : 5]
set xlabel "Number of processors" 0,0
set xrange [1 : 4]
set ylabel "MB/s" 3,0
set yrange [0 : 80]
set zlabel "" 0,0
set zrange [-10 : 10]
set autoscale r
set autoscale t
set noautoscale   
set autoscale z
set zero 1e-08
plot [1:4] "pentium.dat" title "" with linespoints
