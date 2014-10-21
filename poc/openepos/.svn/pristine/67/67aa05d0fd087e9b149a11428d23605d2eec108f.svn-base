CC = gcc
#CFLAGS += -O1 -Wall
CFLAGS += -pthread
CFLAGS += -O3 -Wall
#CFLAGS += -static
CFLAGS += -falign-functions=1
#CFLAGS += -freorder-functions
#CFLAGS += -m32
#CFLAGS += -DTEST3_TSC
#CFLAGS += -DTEST5
#CFLAGS += -fno-optimize-sibling-calls 
#CFLAGS += -ffixed-r15
#-fstrength-reduce 
#-fcse-follow-jumps -fcse-skip-blocks
#CFLAGS += -ansi -D_POSIX_SOURCE -D_XOPEN_SOURCE=500
CFLAGS += -g
#CFLAGS += -pedantic
LDFLAGS =
APPLICATION = testtask
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
	valgrind -q --db-attach=yes ./$(APPLICATION)


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
