.PHONY: all short shrink 

all: allbib.tex all.bib macros-long.bib
	rm -vf allbib.bbl
	pdflatex allbib.tex
	bibtex allbib
	pdflatex allbib.tex
	pdflatex allbib.tex

short: allbib-short.pdf

shrink: allbib-shrink.pdf

allbib-short.pdf: allbib-short.tex all.bib macros-short.bib
	pdflatex allbib-short.tex
	bibtex allbib-short
	pdflatex allbib-short.tex
	pdflatex allbib-short.tex

shrink.bib: all.bib allbib-shrink.tex 
	./export_cites.pl -all -shrink -nopages -nomonth allbib-shrink.tex all.bib > shrink.bib

allbib-shrink.pdf: allbib-shrink.tex all.bib macros-short.bib shrink.bib
	pdflatex allbib-shrink.tex
	bibtex allbib-shrink
	pdflatex allbib-shrink.tex
	pdflatex allbib-shrink.tex

bib:
	bibtex allbib


clean:
	rm -f *~ *.{aux,bbl,blg,dvi,log,pdf} shrink.bib [A-Z]*.bib

# expanded.bib: expand all macros and xrefs in all entries from macros-long.bib + all.bib
expanded.bib: all.bib macros-long.bib
	bib2bib -s 'year' -r --expand --expand-xrefs -ob expanded.bib macros-long.bib all.bib


# Default rule: interpret goal as author or editor and export an expanded (macro-free) bibliography
# of all matching entries
#
# make Lohmann --> Lohmann.bib
#
%: 
	bib2bib -s 'year' -r --expand -c 'author : "$(*F)" or editor : "$(*F)"' -ob $@.bib macros-long.bib all.bib
