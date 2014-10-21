// EPOS eMote3 Timer Mediator Declarations

#ifndef __emote3_timer_h
#define __emote3_timer_h

#include <cpu.h>
#include <ic.h>
#include <rtc.h>
#include <timer.h>
#include <machine.h>
#include <mach/common/cortex_m3.h>

__BEGIN_SYS

class eMote3_Timer: protected Timer_Common
{
    friend class eMote3;
    friend class Init_System;

protected:
    static const unsigned int CHANNELS = 3;
    static const unsigned int FREQUENCY = Traits<eMote3_Timer>::FREQUENCY;

    typedef Cortex_M3_Sys_Tick Engine;
    typedef Engine::Count Count;
    typedef IC::Interrupt_Id Interrupt_Id;

public:
    using Timer_Common::Hertz;
    using Timer_Common::Tick;
    using Timer_Common::Handler;
    using Timer_Common::Channel;

    // Channels
    enum {
        SCHEDULER,
        ALARM,
        USER
    };

public:
    eMote3_Timer(const Hertz & frequency, const Handler & handler, const Channel & channel, bool retrigger = true):
        _channel(channel), _initial(FREQUENCY / frequency), _retrigger(retrigger), _handler(handler) {
        db<Timer>(TRC) << "Timer(f=" << frequency << ",h=" << reinterpret_cast<void*>(handler)
                       << ",ch=" << channel << ") => {count=" << _initial << "}" << endl;

        if(_initial && (unsigned(channel) < CHANNELS) && !_channels[channel])
            _channels[channel] = this;
        else
            db<Timer>(WRN) << "Timer not installed!"<< endl;

        for(unsigned int i = 0; i < Traits<Machine>::CPUS; i++)
            _current[i] = _initial;
    }

    ~eMote3_Timer() {
        db<Timer>(TRC) << "~Timer(f=" << frequency() << ",h=" << reinterpret_cast<void*>(_handler)
                       << ",ch=" << _channel << ") => {count=" << _initial << "}" << endl;

        _channels[_channel] = 0;
    }

    Hertz frequency() const { return (FREQUENCY / _initial); }
    void frequency(const Hertz & f) { _initial = FREQUENCY / f; reset(); }

    Tick read() { return _current[Machine::cpu_id()]; }

    int reset() {
        db<Timer>(TRC) << "Timer::reset() => {f=" << frequency()
                       << ",h=" << reinterpret_cast<void*>(_handler)
                       << ",count=" << _current[Machine::cpu_id()] << "}" << endl;

        int percentage = _current[Machine::cpu_id()] * 100 / _initial;
        _current[Machine::cpu_id()] = _initial;

        return percentage;
    }

    void handler(const Handler * handler) { _handler = handler; }

    static void enable() { Engine::enable(); }
    static void disable() { Engine::disable(); }

private:
    static Hertz count2freq(const Count & c) {
        return c ? Engine::clock() / c : 0;
    }

    static Count freq2count(const Hertz & f) {
        return f ? Engine::clock() / f : 0;
    }

    static void int_handler(const Interrupt_Id & i);

    static void init();

private:
    unsigned int _channel;
    Count _initial;
    bool _retrigger;
    volatile Count _current[Traits<Machine>::CPUS];
    Handler * _handler;

    static eMote3_Timer * _channels[CHANNELS];
};


// Timer used by Thread::Scheduler
class Scheduler_Timer: public eMote3_Timer
{
private:
    typedef RTC::Microsecond Microsecond;

public:
    Scheduler_Timer(const Microsecond & quantum, const Handler & handler): eMote3_Timer(1000000 / quantum, handler, SCHEDULER) {}
};


// Timer used by Alarm
class Alarm_Timer: public eMote3_Timer
{
public:
    static const unsigned int FREQUENCY = Timer::FREQUENCY;

public:
    Alarm_Timer(const Handler & handler): eMote3_Timer(FREQUENCY, handler, ALARM) {}
};

// TODO: replace with timers 0-3
// Timer available for users
class User_Timer: public eMote3_Timer
{
private:
    typedef RTC::Microsecond Microsecond;

public:
    User_Timer(const Microsecond & quantum, const Handler & handler): eMote3_Timer(1000000 / quantum, handler, USER, true) {}
};

__END_SYS

#endif

