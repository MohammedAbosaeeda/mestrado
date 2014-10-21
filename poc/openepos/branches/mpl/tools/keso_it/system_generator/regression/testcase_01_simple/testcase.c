#include <stdio.h>
#include <unistd.h>

#include "os.h"

TASK(job1) {
	static int i = 0;
	i++;
	printf("Task1.\n");
	if (i == 1) ActivateTask(job2);
	TerminateTask();
}

TASK(job2) {
	printf("Task2.\n");
	ActivateTask(job1);
	ChainTask(job3);
}

TASK(job3) {
	printf("Task3.\n");
	ShutdownOS(0);
	TerminateTask();
}

