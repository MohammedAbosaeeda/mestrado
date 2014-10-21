#include <utility/ostream.h>
#include <osek_os.h>

__USING_SYS

OStream cout;

TASK(job1) {
    cout << "Event 1\n";
    ActivateTask(job2);
    WaitEvent(event1);
    ClearEvent(event1);
    cout << "Event 5\n";
    SetEvent(job3, event3);
    TerminateTask();
}

TASK(job2) {
    cout << "Event 2\n";
    ActivateTask(job3);
    WaitEvent(event2);
    ClearEvent(event2);
    cout << "Event 4\n";
    SetEvent(job1, event1);
    TerminateTask();
}

TASK(job3) {
    cout << "Event 3\n";
    SetEvent(job2, event2);
    WaitEvent(event3);
    ClearEvent(event3);
    cout << "Event 6\n";
    ShutdownOS(0);
}
