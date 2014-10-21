int main() {
//? stackprotector
	init_stackprotector();
//?
//+ osc_main_signalhandler
//? have_tasks
	init();
//?
//+ osc_main_preschedule

	initialization = 0;
	Schedule();

	while (1) {
//? debug
		fprintf(stderr, "Idle Task...\n");
//?
		sleep(1);
		/* Hier kommt mal ein sigsuspend hin */
	}

	return 0;
}

