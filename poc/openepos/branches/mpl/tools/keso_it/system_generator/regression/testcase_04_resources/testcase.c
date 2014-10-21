#include <stdio.h>
#include <unistd.h>

#include "os.h"

TASK(job1) {
	printf("T1 - 1\n");
	GetResource(res2);
	ActivateTask(job2);
	printf("T1 - 2\n");
	ReleaseResource(res2);
	printf("T1 - 3\n");
	ShutdownOS(0);
}

TASK(job2) {
	printf("T2 - 1\n");
	GetResource(res3);
	printf("T2 - 2\n");
	GetResource(res2);
	ActivateTask(job3);
	printf("T2 - 3\n");
	ReleaseResource(res2);
	printf("T2 - 4\n");
	ReleaseResource(res3);
	printf("T2 - 5\n");
	TerminateTask();
}

TASK(job3) {
	printf("T3 - 1\n");
	GetResource(res1);
	printf("T3 - 2\n");
	ReleaseResource(res1);
	printf("T3 - 3\n");
	TerminateTask();
}

