ARTICLE_NAME = paper

all: pdf

pdf:
	pdflatex $(ARTICLE_NAME)
	bibtex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)

rtf:
	latex2rtf $(ARTICLE_NAME)
	bibtex $(ARTICLE_NAME)
	latex2rtf $(ARTICLE_NAME)
	latex2rtf $(ARTICLE_NAME)

simple:
	pdflatex $(ARTICLE_NAME)
	pdflatex $(ARTICLE_NAME)


clean:
	rm -f *.log *.aux *.bbl *.blg *.toc *.idx *.lof *.lot *~ \
$(ARTICLE_NAME).dvi $(ARTICLE_NAME).pdf $(ARTICLE_NAME)-fonts_embedded.pdf


embedded: pdf
	gs -dSAFER -dNOPLATFONTS -dNOPAUSE -dBATCH -sDEVICE=pdfwrite -sPAPERSIZE=letter -dCompatibilityLevel=1.4 -dPDFSETTINGS=/printer -dCompatibilityLevel=1.4 -dMaxSubsetPct=100 -dSubsetFonts=true -dEmbedAllFonts=true -sOutputFile=$(ARTICLE_NAME)-fonts_embedded.pdf -f $(ARTICLE_NAME).pdf

