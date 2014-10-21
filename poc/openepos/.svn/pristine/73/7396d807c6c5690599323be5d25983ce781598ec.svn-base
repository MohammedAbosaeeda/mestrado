void activatetask(TaskType id) {
//? debug
	fprintf(stderr, "[DEBUG] Activating task TID = %d\n", id);
//?
	taskdesc[id].numbertasks++;
	taskdesc[id].state = READY;
	taskprio_runnable[taskdesc[id].defaultprio / 8 / sizeof(MACHINEWORD)] |= (1 << (taskdesc[id].defaultprio % (8 * sizeof(MACHINEWORD))));
	add_element_last(queues + taskdesc[id].defaultprio - 1, id);
	if (current_priority == -1) current_priority = taskdesc[id].defaultprio - 1;
}

StatusType ActivateTask(TaskType id) {
	if (id >= NUMTASKS) return E_OS_LIMIT;
	if ((taskdesc[id].numbertasks) > MAX_TASK_ACTIVE) return E_OS_LIMIT;
	activatetask(id);
	if (!initialization) Schedule();
	return E_OK;
}

