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


ALARMCALLBACK(almcallback) {
    static int cnt = 0;
    cnt++;
    cout << "Callback: " << GetTime() - basetime << "\n";
    if (cnt == 3) ShutdownOS(0);
}
