// EPOS Task Abstraction Declarations

#include <thread.h>

#ifndef __task_h
#define __task_h

#include <utility/malloc.h>
#include <address_space.h>
#include <segment.h>
#include <object_table.h>

__BEGIN_SYS

class Task
{
    friend class System;
    friend class Thread;

private:
    typedef CPU::Log_Addr Log_Addr;
    typedef CPU::Phy_Addr Phy_Addr;
    typedef CPU::Context Context;
    typedef class Queue<Thread> Queue;

protected:
    Task(Address_Space * as, const Segment * cs, const Segment * ds,
         Log_Addr code, Log_Addr data)
    : _as(as), _cs(cs), _ds(ds), _code(code), _data(data) {}

public:
    Task(const Segment & cs, const Segment & ds);
    ~Task();

    Address_Space * address_space() const { return _as; }

    const Segment * code_segment() const { return _cs; }
    const Segment * data_segment() const { return _ds; }
    Object_Table& table_task() { return _table_task; }
    Object_Table& table_semaphore() { return _table_semaphore; }

    //TODO Testando mudanca para usermode
    static void switch_to_user_mode();

    Log_Addr code() const { return _code; }
    Log_Addr data() const { return _data; }

    static Task * self();

private:
    void activate() const { _as->activate(); }

    static void init();
    
public:
    Object_Table _table_task;
    Object_Table _table_semaphore;

private:
    Address_Space * _as;
    const Segment * _cs;
    const Segment * _ds;
    Log_Addr _code;
    Log_Addr _data;


    Queue _threads;

    static Task * _master;
};

__END_SYS

#endif
