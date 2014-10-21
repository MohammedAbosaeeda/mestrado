void dump_taskstates(const char *reason) {
	int i;
	fprintf(stderr, "------------------------- Start %s --------------------------------------------------\n", reason);
	/* Dump all tasks, including idle task (task 0) */
	for (i = 0; i <= NUMTASKS; i++) {
		fprintf(stderr, "taskdesc[%2d] TID %2d: ", i, i - 1);
		switch (taskdesc[i].state) {
			case READY		: fprintf(stderr, "Ready    "); break;
			case SUSPENDED	: fprintf(stderr, "Suspended"); break;
			case WAITING	: fprintf(stderr, "Waiting  "); break;
			case RUNNING	: fprintf(stderr, "Running  "); break;
			default			: fprintf(stderr, "INVALID  ");
		}
		fprintf(stderr, ", ");
		fprintf(stderr, "Eventmask: 0x%x, ", taskdesc[i].event_mask);
		fprintf(stderr, "Eventwait: 0x%x\n", taskdesc[i].event_wait);
	}
	fprintf(stderr, "------------------------- End %s --------------------------------------------------\n", reason);
}

void dump_queues_and_taskstates(const char *reason) {
	dump_queues(reason);
	dump_taskstates(reason);
}

