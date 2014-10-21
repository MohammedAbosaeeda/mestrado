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
    cout << "Task1.\n";
    TickType increment = 8;
    TickType cycle = 2;
    SetRelAlarm(myAlarm, increment, cycle);
}

ALARMCALLBACK(almcallback1) {
    static int cnt = 0;
    cnt++;
    cout << "Callback1: " << GetTime() - basetime << "\n";
}
