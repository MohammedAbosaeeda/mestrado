#include <stdio.h>
#include <sys/time.h>
#include <unistd.h>

#include "os.h"

double basetime = 0.0;

double GetTime() {
	struct timeval TimeVal;
	gettimeofday(&TimeVal, NULL);
	return ((double)TimeVal.tv_sec+((double)TimeVal.tv_usec/1000000.0));
}

void StartupHook() {
	basetime = GetTime();
}

ALARMCALLBACK(almcallback) {
	static int cnt = 0;
	cnt++;
	printf("Callback: %.0f\n", GetTime() - basetime);
	if (cnt == 3) ShutdownOS(0);
}

