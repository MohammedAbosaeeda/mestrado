#include <utility/ostream.h>
#include <osek_os.h>

__USING_SYS


TASK(job1) {
	static int i = 0;
	i++;
	OStream cout; cout << "Task1.\n";
	if(i==1) ActivateTask(job2);
	TerminateTask();
}

TASK(job2) {
	OStream cout; cout << "Task2.\n";
	ActivateTask(job1);
	ChainTask(job3);
	TerminateTask();
}

TASK(job3) {
	OStream cout; cout << "Task3.\n";
	ShutdownOS(0);
	TerminateTask();
}
