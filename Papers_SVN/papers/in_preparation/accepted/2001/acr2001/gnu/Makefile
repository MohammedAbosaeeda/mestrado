EPS = ../eps
PDF = ../pdf
GNU2EPS = gnuplot

%.eps: %.gnu
	$(GNU2EPS) $^ > $(EPS)/$@

%.pdf: %.gnu
	$(GNU2EPS) $^ | epstopdf --filter > $(PDF)/$@
#	$(GNU2EPS) $^ | gs -q -dNOPAUSE -dBATCH -sDEVICE=pdfwrite -sOutputFile="| cat" -c save pop -f - > $(PDF)/$@

all: eps

eps: $(subst .gnu,.eps,$(wildcard *.gnu))

pdf: $(subst .gnu,.pdf,$(wildcard *.gnu))
