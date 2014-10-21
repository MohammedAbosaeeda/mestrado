#JOSEKOPTS = -D debug
CC = gcc
#CFLAGS = -O2 -Wall
CFLAGS = -O0 -Wall -ggdb3
CFLAGS += -ansi -D_POSIX_SOURCE -D_XOPEN_SOURCE=500
#CFLAGS += -pedantic
LDFLAGS =
APPLICATION = testcase
SUPPLEMENTARY = os.h
OBJS = os.o dispatch.o

all: $(APPLICATION)

clean:
	rm -f $(APPLICATION) $(OBJS)

distclean:
	rm -f $(APPLICATION) $(OBJS)
	rm -f dispatch.s os.c os.h

test: $(APPLICATION)
	./$(APPLICATION)

kill:
	killall -9 $(APPLICATION)

valgrind: $(APPLICATION)
	valgrind ./$(APPLICATION)


$(APPLICATION): $(APPLICATION).c $(SUPPLEMENTARY) $(OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) -o $(APPLICATION) $(APPLICATION).c $(OBJS)

.c.o:
	$(CC) $(CFLAGS) -c -o $@ $<

.s.o:
	$(CC) $(CFLAGS) -c -o $@ $<

sig1:
	-/bin/kill -s SIGUSR1 `ps -A | grep testtask | awk '{print $$1}'`
	/bin/kill -s SIGUSR1 `ps -A | grep memcheck | awk '{print $$1}'`

sig2:
	-/bin/kill -s SIGUSR2 `ps -A | grep testtask | awk '{print $$1}'`
	/bin/kill -s SIGUSR2 `ps -A | grep memcheck | awk '{print $$1}'`

dump: $(APPLICATION)
	objdump -D $(APPLICATION) | less

debug: $(APPLICATION)
	gdb ./$(APPLICATION)

gen:
	 java -cp ../../classes josek.JOsek -f configuration.oil -c ../../josek/code/arch/ -o ./ $(JOSEKOPTS)
