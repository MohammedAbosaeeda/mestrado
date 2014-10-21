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
	printf("JOB1: Aktiviere...\n");
	ActivateTask(job2);
	printf("habe aktiviert.\n");
	TerminateTask();
}

TASK(job2) {
	printf("Job2 läuft.\n");
	GetResource(foo);
	printf("Halte Resource und aktiviere!\n");
	ActivateTask(job1);
	printf("Halte immernoch Resource, gebe jetzt frei...\n");
	ReleaseResource(foo);
	printf("Resource wurde freigegeben, jetzt beende ich mich.\n");
	TerminateTask(); 
}

TASK(job3) {
	TerminateTask();
}

