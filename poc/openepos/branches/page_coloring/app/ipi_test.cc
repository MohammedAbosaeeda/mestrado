#include <utility/ostream.h>
#include <thread.h>
#include <display.h>
#include <semaphore.h>
#include <tsc.h>
#include <ic.h>

__USING_SYS

Display d;
OStream cout;

int main()
{
    d.clear();
    cout << "Sending an IPI to processor 1..\n";
    IC::ipi_send(1, IC::INT_RESCHEDULER);
    
    while(1);
}