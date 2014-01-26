TOP = paper.tex
BIB = paper.bib
TAB = tab/*.tex
FIG = fig/*.eps fig/*.pdf
OTHER = utils.tex
SRC = $(TOP) $(BIB) $(TAB) $(FIG) $(OTHER)

all: paper.pdf

paper.pdf: $(SRC)
	rubber -d $(TOP)

html: $(SRC)
	latex2html $(TOP)

paper.rtf: $(SRC)
	latex2rtf $(TOP)
	
clean:
	rm -rf paper.pdf paper.rtf paper
	rm -rf *.aux *.bbl *.blg *.aux *.log *.backup
	rm -rf tab/*.aux tab/*.bbl tab/*.blg tab/*.aux tab/*.log tab/*.backup
