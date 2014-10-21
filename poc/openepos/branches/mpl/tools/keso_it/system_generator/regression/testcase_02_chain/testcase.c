#include <stdio.h>
#include <unistd.h>

#include "os.h"

TASK(job1) {
	static int i = 0;
	i++;
	printf("Task1: %d\n", i);
	if (i <= 5) {
		ChainTask(job1);
	} else {
		ChainTask(job2);
	}
}

TASK(job2) {
	printf("Task2.\n");
	ChainTask(job3);
}

TASK(job3) {
	printf("Task3.\n");
	ShutdownOS(0);
	TerminateTask();
}

