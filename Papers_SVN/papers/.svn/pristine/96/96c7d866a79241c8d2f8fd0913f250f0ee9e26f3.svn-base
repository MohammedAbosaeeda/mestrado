#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>

__USING_SYS

int function1(void);
int function2(void);
int function3(void);

Thread* main_thread;
Thread* a;
Thread* b;
Thread* c;

OStream cout;

int main() {
    cout << "Test: \n";

    main_thread = Thread::self();

    cout << "The main thread.\n";
    cout << "Creating another 3 threads...\n";

    a = new Thread(&function1);
    b = new Thread(&function2);
    c = new Thread(&function3);

    a->pass();
    b->pass();
    c->pass();

    return 0;
}

int function1(void) {
    int a = 0;
    while (1) {
        cout << "Thread A - ";
        a++;
        cout << a << "\n";
        Alarm::delay(20000);
    }
}

int function2(void) {
    int b = 0;
    while (1) {
        cout << "Thread B - ";
        b++;
        cout << b << "\n";
        Alarm::delay(400000);
    }
}

int function3(void) {
    int c = 0;
    while (1) {
        cout << "Thread C - ";
        c++;
        cout << c << "\n";
        Alarm::delay(600000);
    }
}

