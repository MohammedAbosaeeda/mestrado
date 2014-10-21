/**
 * contains mappings of linux system calls to tricore-osek
 */

void OSEKOSTerminateTask() __attribute__ ((noreturn));

void exit(int status) {
	OSEKOSTerminateTask();
}

