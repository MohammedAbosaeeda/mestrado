void schedule(unsigned char term) {
	int priority, priolvl;
	int new_task;

//? assertions
	assert((term == 0) || (term == 1), "schedule: invalid parameter");
//?	
	/* If no task applicable, schedule idle task */
	priority = -1;
	new_task = TID_IDLE_TASK;

	/* Find a matching task */
	for (priolvl = NUMBER_PRIORITIES / 8 / sizeof(MACHINEWORD); priolvl >= 0; priolvl--) {
		if (taskprio_runnable[priolvl]) {
			int i;
			/* Which is the task priority that is runnable? */
			priority = bitsearch_reverse(taskprio_runnable[priolvl]) + (8 * sizeof(MACHINEWORD) * priolvl) - 1;
			for (i = 0; i < queues[priority].size; i++) {
				/* found a not empty queue! */
				unsigned char position;
				if (queues[priority].write >= queues[priority].size) position = queues[priority].write - queues[priority].size + i;
					else position = queues[priority].write + QUEUE_LENGTH - queues[priority].size + i;
//? debug
				fprintf(stderr, "[DEBUG] Looking into queue %d, element %d (mem %d): TID %d\n", priority, i, position, queues[priority].elements[position].tid);
//?
				if (taskdesc[queues[priority].elements[position].tid].state == READY) {
					new_task = queues[priority].elements[position].tid;
					break;
				}
			}

		}
	}

//? debug
	fprintf(stderr, "[DEBUG] Scheduler: %d => %d (Term = %d)\n", current_task, new_task, term);
//?
	if ((new_task != current_task) || (term)) {
		unsigned char ot = current_task;

		current_priority = priority + 1;

//+ osc_schedule_posttaskhook
		current_task = new_task;
//+ osc_schedule_pretaskhook
		if (term == 0) {
//? debug
			fprintf(stderr, "[DEBUG] Switchtask: %p => %p\n", taskdesc[ot].stack, taskdesc[new_task].stack);
//?
			switchtask(&taskdesc[ot].stack, taskdesc[new_task].stack);
		} else {
//? debug
			fprintf(stderr, "[DEBUG] Dispatch: %p\n", taskdesc[new_task].stack);
//?
			dispatch(taskdesc[new_task].stack);
		}
		return;
	}

	/* Schedule Idle Task */
}

