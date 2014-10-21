#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

#include "OS.h"

#define STACKSIZE_TASK1 65536
#define STACKSIZE_TASK2 65536
#define STACKSIZE_TASK3 65536

#define MAX_TASK_ACTIVE 4

extern void JOSEK_TASK_task_job1();
extern void JOSEK_TASK_task_job2();
extern void JOSEK_TASK_task_job3();

extern void switchtask(void **old, void **new);
extern void dispatch(void **new);

static void schedule(unsigned char);

static unsigned char current_task = 0;

static unsigned char task1_stack[STACKSIZE_TASK1];
static unsigned char task2_stack[STACKSIZE_TASK2];
static unsigned char task3_stack[STACKSIZE_TASK3];

static sigset_t oldmask;
static unsigned char intlevel = 0;
static int reslocks = 0x00;

enum TASKSTATE { READY, SUSPENDED, WAITING, RUNNING };

struct taskdesc_s {
	const void *stackbase;
	void *stack;
	void (*launch) ();
	enum TASKSTATE state;
	unsigned char defaultprio;
	struct queueelem *lastqueue;
	unsigned char numbertasks;
	EVENTMASKTYPE event_mask;
	EVENTMASKTYPE event_wait;
};

static struct taskdesc_s taskdesc[4] = {
	/* for x86 */
	{ NULL,			NULL,										NULL,					READY,     0, NULL, 0, 0, 0 },
	{ task1_stack,	task1_stack + STACKSIZE_TASK1 - CTXSIZE,	JOSEK_TASK_task_job1,	READY,     1, NULL, 0, 0, 0 },
	{ task2_stack,	task2_stack + STACKSIZE_TASK2 - CTXSIZE,	JOSEK_TASK_task_job2,	SUSPENDED, 2, NULL, 0, 0, 0 },
	{ task3_stack,	task3_stack + STACKSIZE_TASK3 - CTXSIZE,	JOSEK_TASK_task_job3,	SUSPENDED, 3, NULL, 0, 0, 0 }
};

#define QUEUE_LENGTH 6

struct queueelem {
	struct queueelem *prev;
	unsigned char tid;
};

struct bbqueue {
	unsigned char write;
	unsigned char size;
	struct queueelem elements[QUEUE_LENGTH];
};

/* Number of different priorities - TODO: unnormalized (scheduler priority queue) */
#define NUMBER_PRIORITIES 4
struct bbqueue queues[NUMBER_PRIORITIES];

#define INVALID_TASK		(sizeof(taskdesc)/sizeof(struct taskdesc_s))
#define NUMTASKS			INVALID_TASK

/*   task1 (1) benutzt RES_MOO, RES_BAR
 *   task2 (2) benutzt RES_MOO, RES_FOO
 *   task3 (3) benutzt RES_BAR
 *
 *   -> Prioritäten
 *      RES_MOO = 2
 *      RES_FOO = 2
 *      RES_BAR = 3
 */
enum RESOURCES { RES_SCHEDULER, iRES_MOO, iRES_FOO, iRES_BAR };
RESOURCETYPE ResourcePriority[] = { 0, 2, 2, 3 };
static int Stacksizes[] = {STACKSIZE_TASK1, STACKSIZE_TASK2, STACKSIZE_TASK3 };

void Schedule() {
	schedule(0);
}

typedef unsigned long PTRTYPE;

int getqueuenumber(struct queueelem *qe) {
	int i, j;
	j = -1;
	for (i = 0; i < NUMBER_PRIORITIES; i++) {
		if (((PTRTYPE)qe >= (PTRTYPE)(&queues[i])) && ((PTRTYPE)qe <= (PTRTYPE)&(queues[i].elements[QUEUE_LENGTH - 1]))) {
			j = i;
			break;
		}
	}
	if (j == -1) {
		printf("getqueuenumber impossible: memory %p out of range.\n", qe);
		abort();
	}
	return j;
}

void dump_queues() {
	int i, j;
	for (i = 0; i < sizeof(queues)/sizeof(struct bbqueue); i++) {
		printf("%d: Writeptr = %d, Size = %d\n", i, queues[i].write, queues[i].size);
		printf("   ");
		for (j = 0; j < queues[i].size; j++) {
			int pos;
			pos = queues[i].write - queues[i].size + j;
			if (pos < 0) pos += QUEUE_LENGTH;
			printf("[%p %d] ", queues[i].elements[pos].prev, queues[i].elements[pos].tid);
		}
		printf("\n\n");
	}
}

void remove_element(struct bbqueue *bbq) {
	if (bbq->size == 0) {
		printf("Error: Queue underflow, trying to remove last element from %p.\n", bbq); 
		abort(); 
	}
	bbq->size--;
//	printf("REMOVE "); dump_queues();
}

struct queueelem* add_element_last(struct bbqueue *bbq, struct queueelem *new_prev, unsigned char new_tid) {
	if (bbq->size == QUEUE_LENGTH) {
		printf("Error: Queue overflow, trying to insert last %p/%d into %p.\n", new_prev, new_tid, bbq); 
		abort(); 
	}
	bbq->size++;
	bbq->elements[bbq->write].prev = new_prev;
	bbq->elements[bbq->write].tid = new_tid;
	bbq->write++;
	if (bbq->write >= QUEUE_LENGTH) bbq->write -= QUEUE_LENGTH;
//	printf("ADD_LAST "); dump_queues();
	return &(bbq->elements[bbq->write - 1]);
}

struct queueelem* add_element_first(struct bbqueue *bbq, struct queueelem *new_prev, unsigned char new_tid) {
	if (bbq->size == QUEUE_LENGTH) {
		printf("Error: Queue overflow, trying to insert last %p/%d into %p.\n", new_prev, new_tid, bbq); 
		abort(); 
	}
	bbq->size++;
	int write = bbq->write - bbq->size;
	if (write < 0) write += QUEUE_LENGTH;
	bbq->elements[write].prev = new_prev;
	bbq->elements[write].tid = new_tid;
//	printf("ADD_FIRST "); dump_queues();
	return &(bbq->elements[write]);
}

void dump_taskstates() {
	int i;
	for (i = 0; i < NUMTASKS; i++) {
		printf("Task %d\n", i);
		printf("State    : ");
		switch (taskdesc[i].state) {
			case READY: printf("Ready"); break;
			case SUSPENDED: printf("Suspended"); break;
			case WAITING: printf("Waiting"); break;
			case RUNNING: printf("Running"); break;
		}
		printf("\n");
		printf("Eventmask: %d\n", taskdesc[i].event_mask);
		printf("Eventwait: %d\n", taskdesc[i].event_wait);
		printf("\n");
	}
}

unsigned char recalculate_suspended_states() {
	unsigned char change = 0;
	int i;
	for (i = 0; i < NUMTASKS; i++) {
		if ((taskdesc[i].state == WAITING) && (taskdesc[i].event_mask & taskdesc[i].event_wait)) {
			change = 1;
			taskdesc[i].state = READY;
		}
	}
	return change;
}

static void schedule(unsigned char term) {
	int i;
	int new_task = 0;

	/* If no task applicable, schedule idle task */
	new_task = 0;

	/* Find a matching task */
	for (i = NUMBER_PRIORITIES - 1; i >= 0; i--) {
		if (queues[i].size > 0) {
			/* Found a entry! */
			new_task = queues[i].elements[queues[i].write - queues[i].size].tid;
			if (taskdesc[new_task].state == READY) break;
			new_task = 0;
		}
	}

	if (new_task != current_task) {
		unsigned char ot = current_task;
		current_task = new_task;
		if (term == 0) {
			switchtask(&taskdesc[ot].stack, &taskdesc[new_task].stack);
		} else {
			dispatch(&taskdesc[new_task].stack);
		}
		return;
	}

	/* Schedule Idle Task */
}

STATUSTYPE ActivateTask(TASKTYPE id) {
	struct queueelem *qe;
	if (id >= NUMTASKS) return E_OS_LIMIT;
	if ((taskdesc[id].numbertasks) > MAX_TASK_ACTIVE) return E_OS_LIMIT;
	taskdesc[id].numbertasks++;
	taskdesc[id].state = READY;
	qe = add_element_last(&queues[taskdesc[id].defaultprio], NULL, id);
	if (taskdesc[id].lastqueue == NULL) taskdesc[id].lastqueue = qe;
	Schedule();
	return E_OK;
}

STATUSTYPE SetEvent(TASKTYPE id, EVENTMASKTYPE mask) {
	taskdesc[id].event_mask |= mask;
	if (recalculate_suspended_states()) Schedule();
	return E_OK;
}

STATUSTYPE ClearEvent(EVENTMASKTYPE mask) {
	taskdesc[current_task].event_mask &= ~mask;
	return E_OK;
}

STATUSTYPE GetEvent(TASKTYPE id, EVENTMASKTYPE *mask) {
	*mask = taskdesc[id].event_mask;
	return E_OK;
}

STATUSTYPE WaitEvent(EVENTMASKTYPE mask) {
	taskdesc[current_task].event_wait = mask;
	taskdesc[current_task].state = WAITING;
	recalculate_suspended_states();
	Schedule();
	return E_OK;
}

void resetstack(unsigned char stack) {
	void **stacktop;
	stacktop = (void**)((char*)taskdesc[stack].stackbase + Stacksizes[stack - 1] - sizeof(void*));
	*stacktop = taskdesc[stack].launch;			/* Return Address */
	stacktop[-1] = 0;							/* Sane value for EFLAGS/RFLAGS */
	taskdesc[stack].stack = (char*)stacktop - CTXSIZE + sizeof(void*);
	taskdesc[stack].state = SUSPENDED;
	taskdesc[stack].lastqueue = NULL;
}

void TerminateTask() {
	remove_element(&queues[getqueuenumber(taskdesc[current_task].lastqueue)]);
	taskdesc[current_task].numbertasks--;
	resetstack(current_task);
	schedule(1);
}

void ChainTask(TASKTYPE id) {
	taskdesc[current_task].numbertasks--;
	resetstack(current_task);
	taskdesc[id].state = READY;
	schedule(1);
}

void GetTaskID(TASKTYPE *id) {
	*id = current_task;
}

void GetTaskState(TASKTYPE id, unsigned char *state) {
	*state = taskdesc[id].state;
}

void DisableAllInterrupts() {
	if (intlevel == 0) {
		sigset_t mask;
		sigfillset(&mask);
		sigprocmask(SIG_BLOCK, &mask, &oldmask);
	}
	intlevel++;
}

void EnableAllInterrupts() {
	intlevel--;
	if (intlevel == 0) sigprocmask(SIG_SETMASK, &oldmask, NULL);
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

void GetResource(RESOURCETYPE res) {
	if (reslocks & (1 << res)) {
		/* Resource is not available, already locked */
		return;
	}
	reslocks |= (1 << res);
	if (res == RES_SCHEDULER) {
		DisableAllInterrupts();
	} else {
		RESOURCETYPE new_prio = ResourcePriority[res];
//		RESOURCETYPE current_prio = getqueuenumber(taskdesc[current_task].lastqueue);
		taskdesc[current_task].lastqueue = add_element_first(&queues[new_prio], taskdesc[current_task].lastqueue, current_task);
	}
}

void ReleaseResource(RESOURCETYPE res) {
	if (!(reslocks & (1 << res))) {
		/* Resource was never locked */
		return;
	}
	reslocks &= ~(1 << res);
	if (res == RES_SCHEDULER) {
		EnableAllInterrupts();
	} else {
		remove_element(&queues[getqueuenumber(taskdesc[current_task].lastqueue)]);
		taskdesc[current_task].lastqueue = taskdesc[current_task].lastqueue->prev;
	}
}

void handler(int signal) {
//	alarm(3);
	ActivateTask(task_job2);
}

void init() {
	int i;
	for (i = 1; i < NUMTASKS; i++) {
		resetstack(i);
	}
}

int main() {
	struct sigaction A;

	A.sa_handler = handler;
	sigemptyset(&A.sa_mask);
	A.sa_flags = SA_RESTART;
	if ((sigaction(SIGALRM, &A, NULL) == -1) ||
		((sigaction(SIGUSR1, &A, NULL) == -1)) ||
		((sigaction(SIGUSR2, &A, NULL) == -1))) {
		perror("sigaction");
		exit(1);
	}

	alarm(3);

	init();
	ActivateTask(task_job1);
	Schedule();

	while (1) {
		fprintf(stderr, "Idle Task...\n");
		sleep(1);
		/* Hier kommt mal ein sigsuspend hin */
	}

	return 0;
}

