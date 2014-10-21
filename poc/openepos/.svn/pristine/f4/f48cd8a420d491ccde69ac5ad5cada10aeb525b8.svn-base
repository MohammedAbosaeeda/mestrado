#include <utility/ostream.h>
#include <osek_os.h>

__USING_SYS

OStream cout;

TASK(job1) {
	static int i = 0;
	i++;
	cout << "Task1: " << i << "\n";
	if (i <= 5) {
		ChainTask(job1);
	} else {
		ChainTask(job2);
	}
}

TASK(job2) {
	cout << "Task2.\n";
	ChainTask(job3);
}

TASK(job3) {
   cout << "Task3.\n";
	ShutdownOS(0);
	TerminateTask();
}

