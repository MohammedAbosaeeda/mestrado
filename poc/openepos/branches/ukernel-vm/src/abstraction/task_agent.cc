#include <task_agent.h>
#include <system.h>
__BEGIN_SYS


Syscall_Response Task_Agent::constructor(Object_Table& tb, Syscall_Message& m) {
    Syscall_Response r;
    if(m.nparams != 2); //TODO
    
    const Task * task0 = Task::self();
    Address_Space * as0 = task0->address_space();

    OStream cout;
    cout << "Geronimooooo!!!\n"; 
    //Task::switch_to_user_mode();

    const Segment * cs0 = task0->code_segment();
    CPU::Log_Addr code0 = task0->code();

    const Segment * ds0 = task0->data_segment();
    CPU::Log_Addr data0 = task0->data();

    Segment* cs1 = new (SYSTEM) Segment(cs0->size());
    Segment* ds1 = new (SYSTEM) Segment(ds0->size());
    CPU::Log_Addr * code1 = as0->attach(*cs1);
    CPU::Log_Addr * data1 = as0->attach(*ds1);

    cout << "Copying code from " << (void*)code0 << " to " << (void*)code1 << "\n";

    memcpy(code1, code0, cs1->size());
    
    cout << "Copying data from " << (void*)data0 << " to " << (void*)data1 << "\n";
    memcpy(data1, data0, ds1->size());

    as0->detach(*cs1);
    as0->detach(*ds1);
    
    cout << "Parametro no agent: " << *(void**)m.params[1] << " : " <<*(int*)(*(void**)(m.params[1])) << "\n";
    
    Task * task1 = new (SYSTEM) Task(*cs1, *ds1);
    new (SYSTEM) Thread(*task1, *(int (**)(void*))(m.params[0]), *(void**)(m.params[1]) ); 

    Object_Id id = tb.insert_object((Object_Address)task1);
    r.id = id;
    
    if (id == -1) //ERRO
        r.error_code = -1;
    cout << "Parametro no agent2: " << *(void**)m.params[1] << " : " <<*(int*)(*(void**)(m.params[1])) << "\n";

    return r;
}

Syscall_Response Task_Agent::destructor(Object_Table& tb, Syscall_Message& m) {
    Syscall_Response r;
    if(m.nparams != 0); //TODO
    Task* s = (Task*) tb.delete_object(m.object);
    delete s;
    r.error_code = 0;
    return r;
}

__END_SYS

