all: paper.pdf paper.tex

paper.pdf: paper.tex
	rubber -d $<
	
clean:
	rm -rf *.aux *.bbl *.blg *.aux *.log *.backup
	rm -rf tab/*.aux tab/*.bbl tab/*.blg tab/*.aux tab/*.log tab/*.backup
