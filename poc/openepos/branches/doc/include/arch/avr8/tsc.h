// EPOS-- AVR8 TSC Mediator Declarations

#ifndef __avr8_tsc_h
#define __avr8_tsc_h

#include <system/memory_map.h>
#include <cpu.h>
#include <tsc.h>

__BEGIN_SYS

class AVR8_TSC: public TSC_Common
{
private:
    // Imports
    typedef IO_Map<Machine> IO;

    typedef CPU::Reg8 Reg8;
    typedef CPU::Reg16 Reg16;

    // Timer1 Registers
    enum {
        TCNT1L	= 0x2C, ///< AVR Register, see AVR manual
        TCNT1H	= 0x2D,
        TCCR1B	= 0x2E,
        TCCR1A	= 0x2F,
        TIMSK	= 0x37,
    };

    // Timer1 Register Bits
    enum {
        // TCCR1A
        COM1A1 = 0x80,
        COM1A0 = 0x40,
        COM1B1 = 0x20,
        COM1B0 = 0x10,
        FOC1A  = 0x08,
        FOC1B  = 0x04,
        WGM11  = 0x02,
        WGM10  = 0x01,
        // TCCR1B
        ICNC1  = 0x80,
        ICES1  = 0x40,
        WGM13  = 0x10,
        WGM12  = 0x08,
        CS12   = 0x04,
        CS11   = 0x02,
        CS10   = 0x01,
        // TIMSK
        TOIE1  = 0x04
    };

public:
    AVR8_TSC() { /* Actual timer initialization is up to init */ }

    static Hertz frequency() { return Traits<Machine>::CLOCK / 8; }
    static Time_Stamp time_stamp() {
	   return tcnt1hl() | _ts << (sizeof(Reg16) * 8);
    }

    static void init();

private:

    static void enable() { timsk(timsk() | TOIE1); }
    static void disable() { timsk(timsk() & ~TOIE1); }

    static Reg8 timsk() { return AVR8::in8(IO::TIMSK); }
    static void timsk(Reg8 value) { AVR8::out8(IO::TIMSK,value); }
    static Reg8 tccr1a() { return CPU::in8(TCCR1A); }
    static void tccr1a(Reg8 value) { CPU::out8(TCCR1A, value); }
    static Reg8 tccr1b() { return CPU::in8(TCCR1B); }
    static void tccr1b(Reg8 value) { CPU::out8(TCCR1B, value); }
    static Reg16 tcnt1hl() { return CPU::in16(TCNT1L); }
    static void tcnt1hl(Reg16 value) { return CPU::out16(TCNT1L, value); }

public:
    static volatile unsigned long _ts; ///< Current AVR TimeStamp
};

__END_SYS

#endif