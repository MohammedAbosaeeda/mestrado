#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

void t1();
void t2();
void handler(int);

unsigned int stacks[2][8192];
void *espctx[3];

unsigned int task=2;
sigset_t glbmask;

void handler(int sig) {
	/* save context */
	asm ( "pushf\n\t"
	      "pusha\n\t"
	      "movl %%esp, (%0, %1, 4)"
	    : /* no output ops */
	    : "r"(espctx), "r"(task)
	    : "%esp" 
	    );

	task = (task^1) & 1; /* cludge */
	
	/* switch stack and restore context */
	asm ( "movl (%0, %1, 4), %%esp\n\t" 
	      "popa\n\t"
	      "popf"
	    : /* no output ops */
	    : "r"(espctx), "r"(task)
	    : "%esp"
	    );

	/* unblock signals */
	asm ( "pushl $0\n\t"
	      "pushl %0\n\t"
	      "pushl $2\n\t"
	      "call  sigprocmask\n\t"
	      "addl  $12, %%esp"
	    :
	    : "i"(&glbmask)
	    : "%esp", "%eax"
	    );
	/* transfer control */
}

int main() {
	struct sigaction sa;

	sigemptyset(&glbmask);
	sa.sa_handler = handler;
	sigemptyset(&sa.sa_mask);
	sa.sa_flags=0;
	if(-1 == sigaction(SIGUSR1, &sa, NULL)) {
		perror("sigaction");
		exit(1);
	}

	/* EIP und EBP init */
	stacks[0][8191] = (unsigned int) t1;               /* EIP */
	stacks[0][8190] = (unsigned int) &stacks[0][8191]; /* EBP */
	/*stacks[0][8184] = (unsigned int) &stacks[0][8189];  ESP */
	/* EIP, EBP, FLAGS, 32 byte Registers */
	espctx[0]       = &stacks[0][8181];

	stacks[1][8191] = (unsigned int) t2;
	stacks[1][8190] = (unsigned int) &stacks[1][8191];
/*	stacks[1][8184] = (unsigned int) &stacks[1][8189];*/
	espctx[1]       = &stacks[1][8181];

	printf("PID: %d\n", getpid());

	while(1) {
	}

	return 0;
}

void t1() {
	int i=0;
	while(1) {
		fprintf(stderr, "Task1 (%d)\n",i);
		sleep(1);
		i++;
	}
}

void t2() {
	int i=0;
	while(1) {
		fprintf(stderr, "Task2 (%d)\n",i);
		sleep(1);
		i++;
	}
}

