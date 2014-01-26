all: latex pdf

latex:
	latex wso.tex && latex wso.tex && latex wso.tex

pdf:
	dvipdf wso.dvi && xpdf -z 3 wso.pdf &

link:
	ln -s ../../images/ images && ln -s ../../graphs/ graphs

clean:
	rm wso.aux wso.dvi wso.log wso.pdf
