// EPOS Alarm Abstraction Initialization

#include <system/kmalloc.h>
#include <alarm.h>

__BEGIN_SYS

int Alarm::init()
{
    db<Init, Alarm>(TRC) << "Alarm::init()\n";
    
    kout << "Alarm::init()\n";
    
    _timer = new (kmalloc(sizeof(Alarm_Timer))) Alarm_Timer(&handler);
    
    kout << "Alarm::init() end\n";
    
    return 0;
}

__END_SYS
