#include <stdio.h>
#include <unistd.h>

#include "os.h"

void StartupHook() {
}

void PreTaskHook() {
}

void PostTaskHook() {
}

ALARMCALLBACK(callbackfunc) {
	
}

TASK(job1) {
	printf("A\n");
	ActivateTask(job2);
	WaitEvent(coolevent);
	ClearEvent(coolevent);
	printf("C\n");
	TerminateTask();
}

TASK(job2) {
	printf("B\n");
	SetEvent(job1, coolevent);
	printf("D\n");
	ActivateTask(job3);
	TerminateTask(); 
}

TASK(job3) {
	printf("E\n");
	ShutdownOS(0);
}

