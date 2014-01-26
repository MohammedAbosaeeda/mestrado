ARTICLE_NAME = paper
pdf:
	pdflatex $(ARTICLE_NAME)
	bibtex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)

clean:
	rm -f *.log *.aux *.bbl *.blg *.toc *.idx *.lof *.lot *.out *.rtf *.spl *~ $(ARTICLE_NAME).dvi

