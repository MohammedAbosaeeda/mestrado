// EPOS Task Abstraction Implementation

#include <task.h>
#include <semaphore.h>
__BEGIN_SYS

// Class attributes
Task * Task::_master;


// Methods
Task::Task(const Segment & code, const Segment & data)
: _as (new (SYSTEM) Address_Space), _cs(&code), _ds(&data)
{
    db<Task>(TRC) << "Task(cs=" << _cs << ",ds=" << _ds << ") => " << this << "\n";

    _as->attach(*_cs);
    _as->attach(*_ds);
}


Task::~Task()
{
    db<Task>(TRC) << "~Task(this=" << this << ")\n";

    while(!_threads.empty())
        delete _threads.remove()->object();
    for(int i = 0; i < 32; i++) {
        Semaphore* ptr = (Semaphore*)_table_semaphore.get_object(i);
        if(ptr)
            delete ptr;
    }

    for(int i = 0; i < 32; i++) {
        Task* ptr = (Task*)_table_task.get_object(i);
        if(ptr)
            delete ptr;
    }
}


Task * Task::self()
{
    return Thread::self()->_task;
}

void Task::switch_to_user_mode() {
    ASM(  
    "cli\n"
    "mov $0x23, %ax\n" 
    "mov %ax, %ds\n"
    "mov %ax, %es\n"
    "mov %ax, %fs\n"
    "mov %ax, %gs\n"
    "\n               "
    "mov %esp, %eax\n"
    "pushl $0x23\n"
    "pushl %eax\n"
    "pushf\n"
    "pushl $0x1B\n"
    "push $1f\n"
    "iret\n"
    "1:"
    "");
}

__END_SYS
