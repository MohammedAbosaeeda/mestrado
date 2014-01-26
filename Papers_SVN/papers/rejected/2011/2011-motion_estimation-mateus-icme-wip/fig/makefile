EPS = .
PDF = .
CGM = .
PNG = .
FIG2DEV = fig2dev
GNU2EPS = gnuplot
BAR2EPS = perl bargraph.pl -eps
EPS2PDF = epstopdf --filter
XMI2EPS = umbrello --export eps

%.eps: %.fig
	$(FIG2DEV) -L eps -N $< $(EPS)/$@

%.pdf: %.fig
	$(FIG2DEV) -L pdf -N -c -p a $< $(PDF)/$@

%.cgm: %.fig
	$(FIG2DEV) -L cgm $< $(CGM)/$@

%.png: %.fig
	$(FIG2DEV) -L png -N -m 2 -b 5 -S 0 $< $(PNG)/$@

%.eps: %.gnu
	$(GNU2EPS) $< > $(EPS)/$@

%.pdf: %.gnu
	$(GNU2EPS) $< | $(EPS2PDF) > $(PDF)/$@

%.eps: %.bar
	$(BAR2EPS) $< > $(EPS)/$@

%.pdf: %.bar
	$(BAR2EPS) $< | $(EPS2PDF) > $(PDF)/$@

%.eps: %.xmi
	$(XMI2EPS) $< > $(EPS)/$@

%.pdf: %.xmi
	$(XMI2EPS) $< | $(EPS2PDF) > $(PDF)/$@


all: eps

eps: fig2eps gnu2eps xmi2eps
fig2eps: $(subst .fig,.eps,$(wildcard *.fig))
gnu2eps: $(subst .gnu,.eps,$(wildcard *.gnu))
xmi2eps: $(subst .xmi,.eps,$(wildcard *.xmi))

pdf: fig2pdf gnu2pdf xmi2pdf
fig2pdf: $(subst .fig,.pdf,$(wildcard *.fig))
gnu2pdf: $(subst .gnu,.pdf,$(wildcard *.gnu))
xmi2pdf: $(subst .xmi,.pdf,$(wildcard *.xmi))

png: fig2png gnu2png xmi2png
fig2png: $(subst .fig,.png,$(wildcard *.fig))
gnu2png: $(subst .gnu,.png,$(wildcard *.gnu))
xmi2png: $(subst .xmi,.png,$(wildcard *.xmi))


cgm: fig2cgm gnu2cgm xmi2cgm
fig2cgm: $(subst .fig,.cgm,$(wildcard *.fig))
gnu2cgm: $(subst .gnu,.cgm,$(wildcard *.gnu))
xmi2cgm: $(subst .xmi,.cgm,$(wildcard *.xmi))

#clean:
#       rm -rf *.cgm *.cgm *.cgm *.cgm
