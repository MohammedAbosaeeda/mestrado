void remove_element(struct bbqueue *bbq) {
//? debug
	if (bbq->size == 0) {
		fprintf(stderr, "[DEBUG] Queue underflow, trying to remove last element from %p.\n", bbq);
		resolve_queuenumber(bbq);
	}
//?
//? assertions
	assert(bbq->size > 0, "remove_element: Queue underflow");
//?
//? debug
	if (bbq->size == 0) abort();
//?
	bbq->size--;
}

void add_element_last(struct bbqueue *bbq, unsigned char new_tid) {
//? debug
	if (bbq->size == QUEUE_LENGTH) {
		fprintf(stderr, "[DEBUG] Queue overflow, trying to insert last TID = %d into %p.\n", new_tid, bbq); 
		resolve_queuenumber(bbq);
	}
//?
//? assertions
	assert(bbq->size < QUEUE_LENGTH, "add_element_last: Queue overflow");
//?
//? debug
	if (bbq->size == QUEUE_LENGTH) abort();
//?
	bbq->size++;
	bbq->elements[bbq->write].tid = new_tid;
	bbq->write++;
	if (bbq->write >= QUEUE_LENGTH) bbq->write -= QUEUE_LENGTH;
}

void add_element_first(struct bbqueue *bbq, unsigned char new_tid) {
	int writeptr;
//? debug
	if (bbq->size == QUEUE_LENGTH) {
		fprintf(stderr, "[DEBUG] Queue overflow, trying to insert last TID = %d into %p.\n", new_tid, bbq); 
		resolve_queuenumber(bbq);
	}
//?
//? assertions
	assert(bbq->size < QUEUE_LENGTH, "add_element_first: Queue overflow");
//?
//? debug
	if (bbq->size == QUEUE_LENGTH) abort();
//?
	bbq->size++;
	writeptr = bbq->write - bbq->size;
	if (writeptr < 0) writeptr += QUEUE_LENGTH;
	bbq->elements[writeptr].tid = new_tid;
}

