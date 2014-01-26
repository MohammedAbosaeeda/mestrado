#by trier

# definindo variaveis
PDF_COMPILER = pdflatex
LATEX_COMPILER = latex
BIB_COMPILER = bibtex
BIB_FILE = flash.bib
BIBLIOGRAPHY_FILE = flash.bbl
CONVERT_EPS_FILES = ./convertPDF.sh
PDF_FILES = FGTBehaviour.pdf ftl.pdf jffs2.pdf lkm.pdf MirrorBit.pdf MOSFETandFGT.pdf MOSFETBehaviour.pdf mtdarch.pdf winupdate.pdf trueffs.pdf hmtarch.pdf
EPS_FILES = FGTBehaviour.eps ftl.eps jffs2.eps lkm.eps MirrorBit.eps MOSFETandFGT.eps MOSFETBehaviour.eps mtdarch.eps winupdate.eps trueffs.eps hmtarch.eps

#compilacao do pdf
flash.pdf: $(PDF_FILES) flash.tex $(BIB_FILE)
	$(LATEX_COMPILER) flash
	$(BIB_COMPILER) flash
	$(LATEX_COMPILER) flash
	$(LATEX_COMPILER) flash
	$(PDF_COMPILER) flash.tex

#script para transformar eps->pdf
$(PDF_FILES): $(EPS_FILES) $(CONVERT_EPS_FILES)
	$(CONVERT_EPS_FILES)

#Gerar o arquivo .bbl para bibliografia... <-- ainda nao funciona
$(BIBLIOGRAPHY_FILE): $(BIB_FILE)
	bibtex $(BIB_FILE)

# remove todos pdfs do diretorio
clean:
	rm *.pdf *.dvi *.aux *.log *.blg *.bbl *~
