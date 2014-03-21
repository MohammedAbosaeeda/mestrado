ARTICLE_NAME = tese_rita
pdf:
	pdflatex $(ARTICLE_NAME)
	bibtex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)

clean:
	rm -f *.log *.aux *.bbl *.blg *.toc *.idx *.lof *.lot *.out *.rtf *.spl *.las  *~ $(ARTICLE_NAME).dvi $(ARTICLE_NAME).pdf 2

