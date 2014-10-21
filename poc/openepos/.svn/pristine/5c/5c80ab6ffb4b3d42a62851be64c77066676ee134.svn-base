void dump_queues(const char *reason) {
	int i, j;
	fprintf(stderr, "========================= Start %s ==================================================\n", reason);
	for (i = sizeof(queues)/sizeof(struct bbqueue) - 1; i >= 0; i--) {
		fprintf(stderr, "%d: Writeptr = %d, Size = %d\n", i, queues[i].write, queues[i].size);
		fprintf(stderr, "   > ");
		for (j = 0; j < queues[i].size; j++) {
			int pos;
			pos = queues[i].write - queues[i].size + j;
			if (pos < 0) pos += QUEUE_LENGTH;
			fprintf(stderr, "[%d] ", queues[i].elements[pos].tid);
		}
		fprintf(stderr, "\n");
	}
	fprintf(stderr, "========================= End %s ====================================================\n", reason);
}

void resolve_queuenumber(struct bbqueue *bbq) {
	int i;
	fprintf(stderr, "  -> "); 
	for (i = 0; i < NUMBER_PRIORITIES; i++) {
		if (bbq == queues + i) fprintf(stderr, "queues[%d]\n", i);
		return;
	}
	fprintf(stderr, "Couldn't resolve queue pointer %p.\n", bbq);
}

