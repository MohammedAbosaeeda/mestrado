TOP = paper.tex
BIB = paper.bib
TAB = tab/*.tex
FIG = fig/*.eps fig/*.pdf
OTHER = utils.tex paper-1st_round_reviews.tex paper_appendix_UML.tex paper_appendix_CPP.tex
SRC = $(TOP) $(BIB) $(TAB) $(FIG) $(OTHER)

all: paper

paper: paper.pdf

paper_rtf: paper.rtf

paper_html: html

paper_1st_round: paper-1sr_round_reviews.pdf

rebuttal_1st_round: tc-1st_round-rebuttal.pdf


paper.pdf: $(SRC)
	rubber -W refs -W misc -d $(TOP)
	
paper-1sr_round_reviews.pdf: $(SRC)
	rubber -W refs -W misc -d paper-1st_round_reviews.tex
	
tc-1st_round-rebuttal.pdf: tc-1st_round-rebuttal.tex
	rubber -W refs -W misc -d tc-1st_round-rebuttal.tex

html: $(SRC)
	latex2html $(TOP)

paper.rtf: $(SRC)
	latex2rtf $(TOP)
	
clean:
	rm -rf paper.rtf paper
	rm -rf *.aux *.bbl *.blg *.aux *.log *.backup
	rm -rf tab/*.aux tab/*.bbl tab/*.blg tab/*.aux tab/*.log tab/*.backup
