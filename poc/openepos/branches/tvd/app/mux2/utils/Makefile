# $Id: Makefile,v 1.1.2.1 2006-02-01 18:39:33 augusto Exp $

CFLAGS=-O2 -g -I../includes/
LDFLAGS=
CC=gcc
SRCS=main.c utils.c ts.c psi.c
OBJS=main.o utils.o ts.o psi.o

all: es2tssingle

es2tssingle: es2tssingle.o
	$(CC) $(LDFLAGS) -o es2tssingle es2tssingle.o ../src/ts.o

clean:
	rm es2tssingle *.o

