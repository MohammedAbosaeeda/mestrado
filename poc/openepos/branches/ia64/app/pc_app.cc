#include <utility/ostream.h>
#include <thread.h>
#include <semaphore.h>

__USING_SYS

OStream cout;
Semaphore x(0);

int test() {
    cout << "[Thread1] Hello, master. This is thread1 :)\n";
    return 7; // Testing return value
}

int test_semaphore() {
    cout << "[Thread2] Testing semaphore...\n";
    cout << "[Thread2] x.v()...\n";
    x.v();
    cout << "[Thread2] x.v() Done!\n";
    return 0;
}

int main()
{
    Display::clear();

    cout << "==============================================================\n";
    cout << "[Main] Hello master. You did it :) Congratulations!\n";
    cout << "\n";

    cout << "[Main] Application Memory allocation test...\n";
    cout << "[Main] malloc(sizeof(int))...\n";
    int* i = (int*) malloc(sizeof(int));
    *i = 9;
    cout << "[Main] int => " << i << " = " << *i << "\n";
    cout << "[Main] Done!\n";
    cout << "\n";

    cout << "[Main] Application Thread test...\n";
    Thread* t = new Thread(&test);
    cout << "[Main] I'll wait the thread to finish.\n";
    int status = t->join();
    cout << "[Main] Thread has finished with status " << status << "\n";
    cout << "\n";

    cout << "[Main] Application Semaphore test...\n";
    Thread* t2 = new Thread(&test_semaphore);
    cout << "[Main] x.p()...\n";
    x.p();
    cout << "[Main] x.p() done!\n";
    cout << "\n";

    cout << "[Main] I have nothing else to do, master. I'll take a nap.\n";
    while(1);

    return 0;
}
