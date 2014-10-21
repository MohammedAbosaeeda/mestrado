#include <stdio.h>
#include <unistd.h>

#include "os.h"

void StartupHook() {
	printf("Start!\n");
}

void PreTaskHook() {
	TaskType t;
	GetTaskID(&t);
	printf("> %d\n", t);
}

void PostTaskHook() {
	TaskType t;
	GetTaskID(&t);
	printf("< %d\n", t);
}

TASK(job1) {
	printf("A\n");
	ActivateTask(job2);
	TerminateTask();
}

TASK(job2) {
	printf("B\n");
	ActivateTask(job3);
	TerminateTask(); 
}

TASK(job3) {
	printf("C\n");
	TerminateTask();
}

