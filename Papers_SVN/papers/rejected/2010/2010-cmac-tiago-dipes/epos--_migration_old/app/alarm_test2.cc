// EPOS-- Alarm Abstraction Test Program

#include <utility/ostream.h>
#include <alarm.h>

__USING_SYS

const int iterations = -1;

void func_a(void);
void func_b(void);

OStream cout;

int main()
{
    cout << "Alarm test\n";

    Handler_Function handler_a(&func_a);
    Alarm alarm_a(1000000, &handler_a, iterations);

    //Handler_Function handler_b(&func_b);
    //Alarm alarm_b(60000000, &handler_b, iterations/2);

    // Note that in case of idle-waiting, this thread will go into suspend and
    // the alarm handlers above will trigger the functions in the context of the
    // idle thread!
    //Alarm::delay(30000000 * (iterations + 1));
    while(true);

    cout << "I'm done, bye!\n";

    return 0;
}

void func_a()
{
    cout << "func_a()\n";
}

void func_b(void)
{
	cout << "func_b()\n";
}

