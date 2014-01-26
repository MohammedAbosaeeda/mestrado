EPS = .
PDF = .
PNG = .
FIG2DEV = fig2dev
DIA2DEV = dia -n
EPS2PDF = epstopdf

%.eps: %.fig
	$(FIG2DEV) -L eps $< $(EPS)/$@

%.pdf: %.fig
	$(FIG2DEV) -L pdf -c -p a $< $(PDF)/$@

%.png: %.fig
	$(FIG2DEV) -L png -m 2 -b 5 -S 0 $< $(PNG)/$@

%.eps: %.dia
	$(DIA2DEV) -t eps -e $(EPS)/$@ $<

%.pdf: %.dia
	$(DIA2DEV) -t eps -e __tmp.eps $<
	$(EPS2PDF) __tmp.eps
	mv __tmp.pdf $(PDF)/$@
	rm -rf __tmp.eps

all: pdf

eps: $(subst .fig,.eps,$(wildcard *.fig)) $(subst .dia,.eps,$(wildcard *.dia)) 

pdf: $(subst .fig,.pdf,$(wildcard *.fig)) $(subst .dia,.pdf,$(wildcard *.dia)) 

png: $(subst .fig,.png,$(wildcard *.fig)) $(subst .dia,.png,$(wildcard *.dia)) 
