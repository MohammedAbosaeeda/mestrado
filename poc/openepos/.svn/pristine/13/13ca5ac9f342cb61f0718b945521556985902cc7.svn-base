void JOSEK_ISR_IO(int __signalnumber) {
	int i;
	if (__signalnumber != SIGIO) return;
	for (i = 0; i < NUMBER_IO_ISR; i++) {
		if (IO_ISR_Table[i].events & IO_ISR_Table[i].revents) ISRFunction(i)(0);
	}
	IO_ISR_Processed = 1;
}

void *IO_ISR_Helper_Thread(void *Argument) {
	while (1) {
		int PollReturn;
		int i;

		for (i = 0; i < NUMBER_IO_ISR; i++) IO_ISR_Table[i].revents = 0;
		if ((PollReturn = poll(IO_ISR_Table, NUMBER_IO_ISR, -1)) < 0) {
			fprintf(stderr, "poll error: %s\n", strerror(errno));
			continue;
		}

		if (PollReturn == 0) continue;

		IO_ISR_Processed = 0;
		kill(getpid(), SIGIO);
		while (!IO_ISR_Processed) usleep(100 * 1000);	/* Wait until all interrupts are handled */
	}

	pthread_exit(NULL);
	return NULL;
}

StatusType SetupIOISR(int fd, int isrlvl, unsigned char target) {
	IO_ISR_Table[isrlvl].fd = fd;
	IO_ISR_Table[isrlvl].events = 0;
	
	if (target & POLL_READ) IO_ISR_Table[isrlvl].events |= POLLIN;
	if (target & POLL_WRITE) IO_ISR_Table[isrlvl].events |= POLLOUT;
	if (target & POLL_ERROR) IO_ISR_Table[isrlvl].events |= POLLERR;
	if (target & POLL_HANGUP) IO_ISR_Table[isrlvl].events |= POLLHUP;
	
	return E_OK;
}

