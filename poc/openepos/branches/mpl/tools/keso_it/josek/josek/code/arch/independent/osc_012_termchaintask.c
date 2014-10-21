unsigned char __TerminateTask() {
//? assertions
	/* task priority must be at the default level */
	assert(current_task < sizeof(taskdesc), "TerminateTask current_task range");
	assert(current_priority == taskdesc[current_task].defaultprio, "TerminateTask CurrentPriority");

//?
	remove_element(queues + current_priority - 1);
	taskdesc[current_task].numbertasks--;
	resetTID(current_task);
	schedule(1);

	return E_OK;	/* Will never return, but nevertheless... */
}

unsigned char __ChainTask(TaskType tid) {
//? assertions
	assert(tid < sizeof(taskdesc), "ChainTask TID range");
	assert(current_task < sizeof(taskdesc), "ChainTask current_task range");

//?
	remove_element(queues + current_priority - 1);
	taskdesc[current_task].numbertasks--;
	resetTID(current_task);
	activatetask(tid);
	schedule(1);

	return E_OK;	/* Will never return, but nevertheless... */
}

