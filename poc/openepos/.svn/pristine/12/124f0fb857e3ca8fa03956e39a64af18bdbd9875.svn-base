// EPOS Semaphore Abstraction Test Program

#include <utility/ostream.h>
#include <thread.h>
#include <mutex.h>
#include <task_proxy.h>
#include <alarm.h>
#include <display.h>
#include <task.h>

__USING_SYS
#define Semaphore Semaphore_Proxy


const int iterations = 10;

Mutex table;

Thread * phil[5];

OStream cout;

int philosopher(void* a)
{
    for(int i = 0; i < 10; i++) {
        cout << a << ":" << *((int*)a) << "\n";
        Thread::yield();
    }
}

int main()
{
    int a = 20;
    int b = 80;

    cout << "Main task: " << Task::self() << "\n";
    cout << &a <<"\n";
    cout << &b <<"\n";
    cout << "Treta aqui:\n";

    Task_Proxy t(&philosopher, &a);
    Task_Proxy t2(&philosopher, &b);

    cout << "The end!\n";

    return 0;
}
