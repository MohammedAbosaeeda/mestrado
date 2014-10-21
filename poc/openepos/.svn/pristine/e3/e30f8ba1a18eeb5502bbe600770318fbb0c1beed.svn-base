StatusType SetEvent(TaskType id, EventMaskType mask) {
//? debug
	fprintf(stderr, "[DEBUG] taskdesc[TID = %d] signalling TID %d event 0x%x\n", current_task, id, mask);
//?
	taskdesc[id].event_mask |= mask;
	if ((taskdesc[id].state == WAITING) && (taskdesc[id].event_mask & taskdesc[id].event_wait)) {
		taskprio_runnable[taskdesc[id].defaultprio / 8 / sizeof(MACHINEWORD)] |= (1 << (taskdesc[id].defaultprio % (8 * sizeof(MACHINEWORD))));
		taskdesc[id].state = READY;
		Schedule();
	}
	return E_OK;
}

StatusType ClearEvent(EventMaskType mask) {
//? debug
	fprintf(stderr, "[DEBUG] taskdesc[TID = %d] clearing event 0x%x\n", current_task, mask);
//?
	taskdesc[current_task].event_mask &= ~mask;
	return E_OK;
}

StatusType GetEvent(TaskType id, EventMaskType *mask) {
	*mask = taskdesc[id].event_mask;
	return E_OK;
}

StatusType WaitEvent(EventMaskType mask) {
//? debug
	fprintf(stderr, "[DEBUG] taskdesc[TID = %d] waiting for event 0x%x\n", current_task, mask);
//?
	taskdesc[current_task].event_wait = mask;
	if (!(taskdesc[current_task].event_wait & taskdesc[current_task].event_mask)) {
		taskprio_runnable[taskdesc[current_task].defaultprio / 8 / sizeof(MACHINEWORD)] &= ~(1 << (taskdesc[current_task].defaultprio % (8 * sizeof(MACHINEWORD))));
		taskdesc[current_task].state = WAITING;
		Schedule();
	}
	return E_OK;
}

