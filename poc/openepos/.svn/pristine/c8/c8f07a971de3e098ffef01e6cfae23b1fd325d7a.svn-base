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
    cout << "basestime: " << basetime << "\n";
}


TASK(job1) {
    static int i = 0;
    i++;
    cout << "Task1. time: " << GetTime()-basetime << "\n";
    if(i<5) TerminateTask();
    else ShutdownOS(0);
}


