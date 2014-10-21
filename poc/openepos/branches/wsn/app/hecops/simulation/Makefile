all: clean read_data.o hecops_wrapper.o
	g++ -o hecops hecops_wrapper.o read_data.o libgraphic.a -L/usr/X11R6/lib -lX11 -lm -lc -Wno-deprecated -pthread

read_data.o:
	g++ -c read_data.cc

hecops_wrapper.o:
	g++ -c hecops_wrapper.cc

clean:
	rm *.o
