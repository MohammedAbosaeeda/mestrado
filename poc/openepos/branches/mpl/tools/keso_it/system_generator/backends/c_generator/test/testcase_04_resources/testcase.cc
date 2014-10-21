#include <utility/ostream.h>
#include <osek_os.h>

__USING_SYS

OStream cout;

TASK(job1) {
    cout << "T1 - 1\n";
    GetResource(res2);
    ActivateTask(job2);
    cout << "T1 - 2\n";
    ReleaseResource(res2);
    cout << "T1 - 3\n";
    ShutdownOS(0);
}

TASK(job2) {
    cout << "T2 - 1\n";
    GetResource(res3);
    cout << "T2 - 2\n";
    GetResource(res2);
    ActivateTask(job3);
    cout << "T2 - 3\n";
    ReleaseResource(res2);
    cout << "T2 - 4\n";
    ReleaseResource(res3);
    cout << "T2 - 5\n";
    TerminateTask();
}

TASK(job3) {
    cout << "T3 - 1\n";
    GetResource(res1);
    cout << "T3 - 2\n";
    ReleaseResource(res1);
    cout << "T3 - 3\n";
    TerminateTask();
}
