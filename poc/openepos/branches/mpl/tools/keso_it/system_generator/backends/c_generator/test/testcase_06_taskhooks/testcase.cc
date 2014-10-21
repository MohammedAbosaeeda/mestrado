#include <utility/ostream.h>
#include <osek_os.h>

__USING_SYS

OStream cout;

static long task_local[] = { 0xdeadbeef , 0xdeadbeef , 0xdeadbeef };

void PreTaskHook() {
	TaskType tid;
	GetTaskID(&tid);
	task_local[tid] = 0xbeef;
}

void PostTaskHook() {
	TaskType tid;
	GetTaskID(&tid);
	task_local[tid] = 0xdeadbeef;
}

TASK(job1) {
	static int i = 0;
	i++;
	cout << "Task1. " << task_local[0] << 
                 " " << task_local[1] << 
                 " " << task_local[2] << "\n";

	if (i == 1) ActivateTask(job2);
	TerminateTask();
}

TASK(job2) {
	cout << "Task2. " << task_local[0] << 
                 " " << task_local[1] << 
                 " " << task_local[2] << "\n";

	ActivateTask(job1);
	ChainTask(job3);
}

TASK(job3) {
	cout << "Task3. " << task_local[0] << 
                 " " << task_local[1] << 
                 " " << task_local[2] << "\n";

	ShutdownOS(0);
	TerminateTask();
}
