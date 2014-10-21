#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "os.h"

int fd;

ISR2(RXInterrupt) {
	ActivateTask(Receive);
}

TASK(Setup) {
	fprintf(stderr, "Setup\n");
	fd = open("fifo", O_RDONLY | O_NONBLOCK);
	fprintf(stderr, "Open\n");
	if (fd == -1) {
		fprintf(stderr, "Error opening FIFO.\n");
		ShutdownOS(1);
	}
	fprintf(stderr, "Open successful, fd = %d\n", fd);
	SetupIOISR(fd, RXInterrupt, POLL_READ);
	fprintf(stderr, "Interrupt was set up.\n");
	TerminateTask();
}

TASK(Receive) {
	char Buffer[256];
	int Read;
	int i;

	Read = read(fd, Buffer, sizeof(Buffer));
	printf("Read %d bytes: '", Read);
	for (i = 0; i < Read; i++) printf("%c", (Buffer[i] < 32) ? '.' : Buffer[i]);
	printf("'\n");

	TerminateTask();
}

