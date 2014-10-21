// EPOS Task Test Program

#include <utility/ostream.h>
#include <semaphore_stub.h>
#include <alarm.h>
#include <task.h>

__USING_SYS

const int iterations = 10;

int func_a(void);
int func_b(void);

Thread * a;
Thread * b;
Thread * m;

OStream cout;

int main()
{
    Semaphore_Stub* sem = new Semaphore_Stub(2);
    cout << "Stub address: " << sem << "\n";
    sem->p();
    sem->v();
    delete sem;    
}
