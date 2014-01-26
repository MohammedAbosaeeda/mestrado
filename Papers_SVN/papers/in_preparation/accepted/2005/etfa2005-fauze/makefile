all:
	latex latex8
	bibtex latex8
	latex latex8
	latex latex8
	dvips -Pdownload35 -Ppdf -G0 -ta4 latex8
	dvipdf -DownloadBase14 latex8
#	ps2pdf -dCompatibilityLevel=1.3 -dMAxSubsetPct=100 \
	-dSubsetFonts=true -dEmbedAllFonts=true -sPAPERSIZE=a4 latex8.ps
