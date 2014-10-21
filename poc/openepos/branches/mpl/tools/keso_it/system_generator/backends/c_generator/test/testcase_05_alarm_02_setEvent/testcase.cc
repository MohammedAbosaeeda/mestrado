#include <utility/ostream.h>
#include <osek_os.h>
#include <clock.h>

__USING_SYS

OStream cout;
Clock clock;
long int basetime = 0;

long int GetTime() {
    return clock.now();
}

void StartupHook() {
    basetime = GetTime();
}


TASK(job1) {
    cout << "Task1. Time: " << GetTime() << "\n";
    WaitEvent(event1);
    ActivateTask(job2);
    TerminateTask();
}

TASK(job2) {
    cout << "Task2. Time: " << GetTime() << "\n";
    TerminateTask();
}

