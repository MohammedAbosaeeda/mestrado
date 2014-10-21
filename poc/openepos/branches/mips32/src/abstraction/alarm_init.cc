// EPOS-- Alarm Abstraction Initialization

#include <alarm.h>
#include <machine.h>

__BEGIN_SYS

int Alarm::init(System_Info * si)
{
    db<Init, Alarm>(TRC) << "Alarm::init()\n";

    CPU::int_disable();

	Machine::int_vector(Machine::irq2int(IC::IRQ_COUNTER), int_handler);
	Machine::int_vector(Machine::irq2int(IC::IRQ_COUNTER_NOT), int_handler);

    _timer.frequency(FREQUENCY);
	
	db<Init, Alarm>(TRC) << "Alarm::_timer.frequency() => " << _timer.frequency() << "\n";
	
	//IC::enable(); //deixar default por enqaunto - by david

    //CPU::int_enable();

    return 0;
}

__END_SYS
