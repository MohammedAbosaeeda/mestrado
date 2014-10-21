hist:
	g++ -o monitor monitor.cc screen.cc graphic.cc -L/usr/X11R6/lib -lX11 -lm -lc -Wno-deprecated -pthread

lib: clean
	g++ -c monitor.cc screen.cc graphic.cc -Wno-deprecated
	ar rc libgraphic.a screen.o graphic.o monitor.o
	ranlib libgraphic.a

clean:
	rm -f libgraphic.a screen.o graphic.o monitor.o
	
