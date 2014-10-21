# $Id: Makefile,v 1.1.2.1 2006-02-01 18:39:05 augusto Exp $

CFLAGS=-O2 -g -I../includes/ `xml2-config --cflags`
LDFLAGS=-L/usr/lib -lz -pthread -lm `xml2-config --libs`
CC=gcc
SRCS=main.c utils.c ts.c psi.c config.c crc32.c queue.c mux.c pat.c pmt.c output.c es.c
OBJS=main.o utils.o ts.o psi.o config.o crc32.o queue.o mux.o pat.o pmt.o output.o es.o

all: mux

mux: $(OBJS)
	$(CC) $(LDFLAGS) -o mux $(OBJS)

$(OBJS): $(SRCS)
	$(CC) $(CFLAGS) -c $(SRCS)

clean:
	rm mux $(OBJS)

