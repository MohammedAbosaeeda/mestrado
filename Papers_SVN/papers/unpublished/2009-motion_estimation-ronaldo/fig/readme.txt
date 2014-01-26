Para gerar as figuras:

// Figuras de gráfico de barra
- Pré-requisitos:
gnuplot
perl
transfig (contém fig2dev)

- Comando para gerar figuras em EPS:
$ perl bargraph.pl -eps speedup.gnuplot > speedup.eps
$ perl bargraph.pl -eps quality_loss.gnuplot > quality_loss.eps

// Figuras de linhas
- Pré-requisitos:
gnuplot

- Comando para gerar figuras:
$ gnuplot truncation_vs_psnr.gnuplot
