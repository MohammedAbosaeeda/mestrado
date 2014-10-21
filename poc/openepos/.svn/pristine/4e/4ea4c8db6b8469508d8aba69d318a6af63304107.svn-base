// EPOS Alarm Abstraction Initialization

#include <system/kmalloc.h>
#include <alarm.h>

__BEGIN_SYS

int Single_Core_Alarm::init()
{
    db<Init, Single_Core_Alarm>(TRC) << "Single_Core_Alarm::init()\n";
    
    _timer = new (kmalloc(sizeof(Alarm_Timer))) Alarm_Timer(&handler);

    return 0;
}

int SMP_Alarm::init()
{
    db<Init, Alarm>(TRC) << "SMP_Alarm::init()\n";
    
    _timer = new (kmalloc(sizeof(Alarm_Timer))) Alarm_Timer(&handler);

    return 0;
}

__END_SYS
