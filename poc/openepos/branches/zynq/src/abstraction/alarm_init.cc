// EPOS Alarm Abstraction Initialization

#include <system.h>
#include <alarm.h>

__BEGIN_SYS

int Alarm::init()
{
    db<Init, Alarm>(TRC) << "Alarm::init()" << endl;

	kout << "Creating timer\n";
    _timer = new (SYSTEM) Alarm_Timer(handler);
	kout << "Timer created\n";

    return 0;
}

__END_SYS
