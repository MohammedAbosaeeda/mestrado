EPS = .
PDF = .
CGM = .
PNG = .
FIG2DEV = fig2dev

%.eps: %.fig
	$(FIG2DEV) -L eps $< $(EPS)/$@

%.pdf: %.fig
	$(FIG2DEV) -L pdf -c -p a $< $(PDF)/$@

%.cgm: %.fig
	$(FIG2DEV) -L cgm $< $(CGM)/$@

%.png: %.fig
	$(FIG2DEV) -L png -m 2 -b 5 -S 0 $< $(PNG)/$@

all: eps

eps: $(subst .fig,.eps,$(wildcard *.fig))

pdf: $(subst .fig,.pdf,$(wildcard *.fig))

cgm: $(subst .fig,.cgm,$(wildcard *.fig))

png: $(subst .fig,.png,$(wildcard *.fig))

