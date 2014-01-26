ARTICLE_NAME = sbesc
pdf:
	pdflatex $(ARTICLE_NAME)
	bibtex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)

clean:
	rm -f *.log *.aux *.bbl *.blg *.toc *.idx *.lof *.lot *.out *~ \
$(ARTICLE_NAME).dvi

