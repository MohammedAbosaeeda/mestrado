FILENAME=power

default:
	latex $(FILENAME).tex
	bibtex $(FILENAME)
	latex $(FILENAME).tex
	latex $(FILENAME).tex
view:
	xdvi $(FILENAME).dvi &

pdf: default
	pdflatex $(FILENAME).tex

pdf_view:
	kpdf $(FILENAME).pdf &

ps: default
	pslatex $(FILENAME).tex

ps_view:
	gs $(FILENAME).ps &

clean:
	rm *~ *.aux *.bak *.bbl *.blg *.dvi *.log *.pdf *.ps *.toc
