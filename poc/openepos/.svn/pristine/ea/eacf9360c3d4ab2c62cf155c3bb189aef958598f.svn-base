#include <stdio.h>
#include <unistd.h>

#include "os.h"

TASK(job1) {
	printf("Event 1\n");
	ActivateTask(job2);
	WaitEvent(event1);
	ClearEvent(event1);
	printf("Event 5\n");
	SetEvent(job3, event3);
	TerminateTask();
}

TASK(job2) {
	printf("Event 2\n");
	ActivateTask(job3);
	WaitEvent(event2);
	ClearEvent(event2);
	printf("Event 4\n");
	SetEvent(job1, event1);
	TerminateTask();
}

TASK(job3) {
	printf("Event 3\n");
	SetEvent(job2, event2);
	WaitEvent(event3);
	ClearEvent(event3);
	printf("Event 6\n");
	ShutdownOS(0);
}

