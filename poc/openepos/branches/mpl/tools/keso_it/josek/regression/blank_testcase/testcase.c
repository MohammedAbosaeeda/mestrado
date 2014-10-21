#include <stdio.h>
#include <unistd.h>

#include "os.h"

TASK(job1) {
	TerminateTask();
}

TASK(job2) {
	TerminateTask();
}

TASK(job3) {
	TerminateTask();
}

