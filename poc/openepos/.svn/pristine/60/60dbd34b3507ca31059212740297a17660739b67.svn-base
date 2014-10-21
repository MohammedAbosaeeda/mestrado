CC = gcc
#CFLAGS = -O2 -Wall
LDFLAGS = 
CFLAGS = -O0 -Wall -ggdb3
APPLICATION = testtask
SUPPLEMENTARY = OS.h
OBJS = OS.o dispatch.o

all: $(APPLICATION)

clean:
	rm -f $(APPLICATION) $(OBJS)

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
