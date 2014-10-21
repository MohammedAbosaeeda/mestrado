/* Conforms to OSEK/VDX Binding Specification v1.42 */
#ifndef STATUSTYPEDEFINED
#define STATUSTYPEDEFINED
typedef unsigned char StatusType;
#define E_OK			0
#endif

typedef unsigned char PriorityType;
typedef unsigned char TaskType;
typedef unsigned char ResourceType;
typedef unsigned char EventMaskType;
typedef unsigned char CounterType;
typedef unsigned char CounterTickType;
typedef unsigned char AlarmTickType;
typedef unsigned char AlarmBaseType;
typedef void (*ISRFunctionType)(int);

enum TASKSTATE { READY, SUSPENDED, WAITING, RUNNING };

#define E_OS_ACCESS		1
#define E_OS_CALLLEVEL	2
#define E_OS_ID			3
#define E_OS_LIMIT		4
#define E_OS_NUFUNC		5
#define E_OS_RESOURCE	6
#define E_OS_STATE		7
#define E_OS_VALUE		8

/* ??? */
#define E_OS_NOFUNC		9

#define POLL_READ		1
#define POLL_WRITE		2
#define POLL_ERROR		4
#define POLL_HANGUP		8

#define TASK(name)				void JOSEK_TASK_##name()
#define ALARMCALLBACK(name)		void JOSEK_CALLBACK_##name()
#define ISR2(name)				void JOSEK_ISR_##name(int __signalnumber)

struct taskdesc_s {
	const void *stackbase;
	void *stack;
	void (*launch) ();
	enum TASKSTATE state;
	PriorityType defaultprio;
	unsigned char numbertasks;
	EventMaskType event_mask;
	EventMaskType event_wait;
};

extern struct taskdesc_s taskdesc[];
extern unsigned char current_task;

#define GetTaskID(id)						((*(id)) = current_task, E_OK)
#define GetTaskState(id, state)				((*(state)) = taskdesc[id].state, E_OK)

#define QUEUE_LENGTH 6

struct queueelem {
	TaskType tid;
};

struct bbqueue {
	unsigned char write;
	unsigned char size;
	struct queueelem elements[QUEUE_LENGTH];
};

