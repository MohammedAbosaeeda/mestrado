void resetTID(TaskType tid) {
	void **stacktop;
	stacktop = (void**)((char*)taskdesc[tid].stackbase + Stacksizes[tid] - sizeof(void*));
	*stacktop = (void (*)())taskdesc[tid].launch;			/* Return Address */
	stacktop[-1] = 0;										/* Sane value for EFLAGS/RFLAGS */
	taskdesc[tid].stack = (char*)stacktop - CTXSIZE + sizeof(void*);
	if (taskdesc[tid].numbertasks == 0) {
		taskdesc[tid].state = SUSPENDED;
		taskprio_runnable[taskdesc[tid].defaultprio / 8 / sizeof(MACHINEWORD)] &= ~(1 << (taskdesc[tid].defaultprio % (8 * sizeof(MACHINEWORD))));
	}
}

