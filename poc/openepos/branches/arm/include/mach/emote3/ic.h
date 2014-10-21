// EPOS eMote3 IC Mediator Declarations

#ifndef __emote3_ic_h
#define __emote3_ic_h

#include <cpu.h>
#include <ic.h>
#include <mach/common/cortex_m3.h>

__BEGIN_SYS

class eMote3_IC: private IC_Common, private Cortex_M3_IC
{
    friend class eMote3;

private:
    typedef CPU::Reg32 Reg32;
    typedef CPU::Log_Addr Log_Addr;
    typedef Cortex_M3_IC Engine;

public:
    using IC_Common::Interrupt_Id;
    using IC_Common::Interrupt_Handler;
    using Engine::INTS;
    using Engine::INT_TIMER;
    using Engine::IRQ_TIMER;

public:
    eMote3_IC() {}

    static Interrupt_Handler int_vector(const Interrupt_Id & i) {
        assert(i < INTS);
        return _int_vector[i];
    }

    static void int_vector(const Interrupt_Id & i, const Interrupt_Handler & h) {
        db<IC>(TRC) << "IC::int_vector(int=" << i << ",h=" << reinterpret_cast<void *>(h) <<")" << endl;
        assert(i < INTS);
        _int_vector[i] = h;
    }

    static void enable() {
        db<IC>(TRC) << "IC::enable()" << endl;
        Engine::enable();
    }

    static void enable(const IRQ & i) {
        db<IC>(TRC) << "IC::enable(irq=" << i << ")" << endl;
        assert(i < IRQS);
        Engine::enable(i);
    }

    static void disable() {
        db<IC>(TRC) << "IC::disable()" << endl;
        Engine::disable();
    }

    static void disable(const IRQ & i) {
        db<IC>(TRC) << "IC::disable(irq=" << i << ")" << endl;
        assert(i < IRQS);
        Engine::disable(i);
    }

private:
    static void dispatch();

    static void int_not(const Interrupt_Id & i);

    static void init();

private:
    static Interrupt_Handler _int_vector[INTS];
};

__END_SYS

#endif
