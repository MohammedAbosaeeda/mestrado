EPS = .
PDF = .
CGM = .
PNG = .
FIG2DEV = fig2dev

%.eps: %.fig
	$(FIG2DEV) -L eps -N $< $(EPS)/$@

%.pdf: %.fig
	$(FIG2DEV) -L pdf -N -c -p a $< $(PDF)/$@

%.cgm: %.fig
	$(FIG2DEV) -L cgm $< $(CGM)/$@

%.png: %.fig
	$(FIG2DEV) -L png -N -m 2 -b 5 -S 0 $< $(PNG)/$@

all: pdf

eps: $(subst .fig,.eps,$(wildcard *.fig))

pdf: $(subst .fig,.pdf,$(wildcard *.fig))

cgm: $(subst .fig,.cgm,$(wildcard *.fig))

png: $(subst .fig,.png,$(wildcard *.fig))

clean:
	rm -rf *.eps *.pdf *.cgm *.png
