#include <stdio.h>
#include <unistd.h>

#include "os.h"

static long task_local[] = { 0xdeadbeef , 0xdeadbeef , 0xdeadbeef };

void PreTaskHook() {
	TaskType tid;
	GetTaskID(&tid);
	task_local[tid] = 0xbeef;
}

void PostTaskHook() {
	TaskType tid;
	GetTaskID(&tid);
	task_local[tid] = 0xdeadbeef;
}

TASK(job1) {
	static int i = 0;
	i++;
	printf("Task1. 0x%x 0x%x 0x%x\n", task_local[0], task_local[1], task_local[2]);
	if (i == 1) ActivateTask(job2);
	TerminateTask();
}

TASK(job2) {
	printf("Task2. 0x%x 0x%x 0x%x\n", task_local[0], task_local[1], task_local[2]);
	ActivateTask(job1);
	ChainTask(job3);
}

TASK(job3) {
	printf("Task3. 0x%x 0x%x 0x%x\n", task_local[0], task_local[1], task_local[2]);
	ShutdownOS(0);
	TerminateTask();
}

