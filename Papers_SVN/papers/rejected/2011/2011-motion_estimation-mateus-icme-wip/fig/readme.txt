Para gerar as figuras:

// Figuras de gráfico de barra
- Pré-requisitos:
gnuplot
perl
transfig (contém fig2dev)

- Comando para gerar figuras em EPS:
$ perl bargraph.pl -eps jm_perf.bar > jm_perf.eps
$ perl bargraph.pl -eps jm_qual.bar > jm_qual.eps

// Figuras de linhas
- Pré-requisitos:
gnuplot

- Comando para gerar figuras:
$ gnuplot truncation_vs_psnr.gnuplot
