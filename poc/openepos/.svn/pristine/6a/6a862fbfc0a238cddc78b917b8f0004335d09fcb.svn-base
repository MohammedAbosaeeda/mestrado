void DisableAllInterrupts() {
//? signals
	if (intlevel == 0) {
		sigset_t mask;
		sigfillset(&mask);
		sigprocmask(SIG_BLOCK, &mask, &oldmask);
	}
	intlevel++;
//?
}

void EnableAllInterrupts() {
//? signals
	intlevel--;
	if (intlevel == 0) sigprocmask(SIG_SETMASK, &oldmask, NULL);
//?
}

void SuspendAllInterrupst() {
	DisableAllInterrupts();
}

void ResumeAllInterrupts() {
	EnableAllInterrupts();
}

void SuspendOSInterrupts() {
	DisableAllInterrupts();
}

void ResumeOSInterrupts() {
	EnableAllInterrupts();
}

