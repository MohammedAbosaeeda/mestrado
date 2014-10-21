#include <stdio.h>
#include <unistd.h>

#include "OS.h"

/*
TASK(task_job1) {
	int MaxTime = 5;
	printf("Started task 1. Running for %d.\n", MaxTime);
	GetResource(RES_BAR);
	while (MaxTime != 0) {
		printf("Task 1 (%d remaining).\n", MaxTime);
		sleep(1);
		MaxTime--;
	}
	ReleaseResource(RES_BAR);
//	ChainTask(task_job2);
	TerminateTask();
}

TASK(task_job2) {
	printf("Task 2.\n");
	sleep(1);
	printf("Terminating Task2\n");
	TerminateTask();
}

TASK(task_job3) {
	int x = 2;
	printf("Started Task3\n");
	while (x-- > 0) {
		printf("Task 3...\n");
		sleep(1);
	}
	ActivateTask(task_job1);
	printf("Terminating Task3\n");
	TerminateTask();
}
*/

TASK(task_job1) {
	printf("Job1 Running!\n");
	WaitEvent(1);
	printf("Done waiting\n");
	TerminateTask();
}

TASK(task_job2) { 
	printf("Task2\n");
	SetEvent(task_job1, 1);
	TerminateTask(); 
}

TASK(task_job3) {
	TerminateTask();
}

