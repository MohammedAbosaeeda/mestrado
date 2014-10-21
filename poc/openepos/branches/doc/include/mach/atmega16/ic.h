// EPOS-- ATMega16 Interrupt Controller Mediator Declarations

#ifndef __atmega16_ic_h
#define __atmega16_ic_h

#include "../avr_common/ic.h"

__BEGIN_SYS

class ATMega16_IC: public IC_Common, private AVR_IC
{
private:
    typedef AVR8::Reg8 Reg8;

    /// Interrupt IO Registers
    enum {
        GICR    = 0x3b, ///< General Interrupt Control Register
        GIFR    = 0x3a, ///< General Interrupt Flag Register
        TIMSK   = 0x39, ///< Timer Interrupt Mask Register
        TIFR    = 0x38, ///< Timer Interrupt Flag Register
        TWCR    = 0x36, ///< TWI Control Register
        MCUCR   = 0x35, ///< MCU Control Register
        MCUCSR  = 0x34, ///< MCU Control and Status Register
        EECR    = 0x1c, ///< EEPROM Control Register
        SPSR    = 0x0e, ///< SPI Status Register
        SPCR    = 0x0d, ///< SPI Control Register
        UCSRA   = 0x0b, ///< USART Control and Status Register A
        UCSRB   = 0x0a, ///< USART Control and Status Register B
        ACSR    = 0x08, ///< Analog Comparator Control and Status Register
        ADCSRA  = 0x06  ///< ADC Control and Status Register A
    };

    /// Interrupt Register Flags
    enum {
        /// GICR (General Interrupt Control Register)
        INT1     = 0x80,
        INT0     = 0x40,
        INT2     = 0x20,
        IVSEL    = 0x02,
        IVCE     = 0x01,
        /// GIFR (General Interrupt Flag Register)
        INTF1    = 0x80,
        INTF0    = 0x40,
        INTF2    = 0x20,
        /// TIMSK (Timer Interrupt Mask Register)
        OCIE2    = 0x80,
        TOIE2    = 0x40,
        TICIE1   = 0x20,
        OCIE1A   = 0x10,
        OCIE1B   = 0x08,
        TOIE1    = 0x04,
        OCIE0    = 0x02,
        TOIE0    = 0x01,
        /// TIFR (Timer Interrupt Flag Register)
        OCF2     = 0x80,
        TOV2     = 0x40,
        ICF1     = 0x20,
        OCF1A    = 0x10,
        OCF1B    = 0x08,
        TOV1     = 0x04,
        OCF0     = 0x02,
        TOV0     = 0x01,
        /// TWCR (TWI Control Register)
        TWINT    = 0x80,
        TWIE     = 0x01,
        /// MCUCR (MCU Control Register)
        ISC11    = 0x08,
        ISC10    = 0x04,
        ISC01    = 0x02,
        ICS00    = 0x01,
        /// MCUCSR (MCU Control and Status Register)
        ISC2     = 0x40,
        /// EECR (EEPROM Control Register)
        EERIE    = 0x40,
        /// SPSR (SPI Status Register)
        SPIF     = 0x80,
        /// SPCR (SPI Control Register)
        SPIE     = 0x80,
        /// UCSRA (USART Control and Status Register A)
        RXC  = 0x80,
        TXC  = 0x40,
        UDRE     = 0x20,
        /// UCSRB (USART Control and Status Register B)
        RXCIE    = 0x80,
        TXCIE    = 0x40,
        UDRIE    = 0x20,
        /// ACSR (Analog Comparator Control and Status Register)
        ACI  = 0x10,
        ACIE     = 0x80,
        /// ADCSRA (ADC Control and Status Register A)
        ADIF     = 0x10,
        ADIE     = 0x80
    };

public:

    enum {
        IRQ_RESET        = 0,   ///< External Pin, Power-on Reset, Brown-out Reset, Watchdog Reset, and JTag AVR Reset
        IRQ_IRQ0         = 1,   ///< External Interrupt Request 0
        IRQ_IRQ1         = 2,   ///< External Interrupt Request 1
        IRQ_TIMER2_COMP  = 3,   ///< Timer/Counter2 Compare Match
        IRQ_TIMER2_OVF   = 4,   ///< Timer/Counter2 Overflow
        IRQ_TIMER1_CAPT  = 5,   ///< Timer/Counter1 Capture Event
        IRQ_TIMER1_COMPA = 6,   ///< Timer/Counter1 Compare Match A
        IRQ_TIMER1_COMPB = 7,   ///< Timer/Counter1 Compare Match B
        IRQ_TIMER1_OVF   = 8,   ///< Timer/Counter1 Overflow
        IRQ_TIMER0_OVF   = 9,   ///< Timer/Counter0 Overflow
        IRQ_SPI_STC      = 10,  ///< Serial Transfer Complete
        IRQ_USART0_RXC   = 11,  ///< USART, Rx Complete
        IRQ_USART0_UDRE  = 12,  ///< USART Data Register Empty
        IRQ_USART0_TXC   = 13,  ///< USART, Tx Complete
        IRQ_ADC          = 14,  ///< ADC Conversion Complete
        IRQ_EE_RDY       = 15,  ///< EEPROM Ready
        IRQ_ANA_COMP     = 16,  ///< Analog Comparator
        IRQ_TWI          = 17,  ///< Two-wire Serial Interface
        IRQ_IRQ2         = 18,  ///< External Interrupt Request 2
        IRQ_TIMER0_COMP  = 19,  ///< Timer/Counter0 Compare Match
        IRQ_SPM_RDY      = 20,  ///< Store Program Memory Ready

        IRQ_TSC      = 100, ///< not implemented
        IRQ_TIMER    = IRQ_TIMER0_COMP
    };

public:
    ATMega16_IC() {};

    static void enable(IRQ irq) {

        if (IRQ_IRQ0 == irq) { gicr(gicr() | INT0); return; }
        if (IRQ_IRQ1 == irq) { gicr(gicr() | INT1); return; }
        if (IRQ_IRQ2 == irq) { gicr(gicr() | INT2); return; }

        if (IRQ_TIMER2_COMP == irq) { timsk(timsk() | OCIE2); return; }
        if (IRQ_TIMER2_OVF == irq) { timsk(timsk() | TOIE2); return; }

        if (IRQ_TIMER1_CAPT == irq) { timsk(timsk() | TICIE1); return; }
        if (IRQ_TIMER1_COMPA == irq) { timsk(timsk() | OCIE1A); return; }
        if (IRQ_TIMER1_COMPB == irq) { timsk(timsk() | OCIE1B); return; }
        if (IRQ_TIMER1_OVF == irq) { timsk(timsk() | TOIE1); return; }

        if (IRQ_TIMER0_OVF == irq) { timsk(timsk() | TOIE0); return; }
        if (IRQ_TIMER0_COMP == irq) { timsk(timsk() | OCIE0); return; }

        if (IRQ_SPI_STC == irq) { spcr(spcr() | SPIE); return; }

        if (IRQ_USART0_RXC == irq) { ucsrb(ucsrb() | RXCIE); return; }
        if (IRQ_USART0_UDRE == irq) { ucsrb(ucsrb() | UDRIE); return; }
        if (IRQ_USART0_TXC == irq) { ucsrb(ucsrb() | TXCIE); return; }

        if (IRQ_ADC == irq) { adcsra(adcsra() | ADIE); return; }

        if (IRQ_ANA_COMP == irq) { acsr(acsr() | ACIE); return; }

        if (IRQ_EE_RDY == irq) { eecr(eecr() | EERIE); return; }

        if (IRQ_TWI == irq) { twcr(twcr() | TWIE); return; }

        // if (IRQ_RESET  == irq) {return;}   // Always enabled
        // if (IRQ_SPM_RDY  == irq) {return;} // Unused
    }

    static void disable() {
        gicr(gicr() & ~(INT0 | INT1 | INT2));

        timsk(timsk() & ~(OCIE2 | TOIE2));

        timsk(timsk() & ~(TICIE1 | OCIE1A | OCIE1B | TOIE1));

        timsk(timsk() & ~(TOIE0 | OCIE0));

        spcr(spcr() & ~SPIE);

        ucsrb(ucsrb() & ~(RXCIE | UDRIE | TXCIE));

        adcsra(adcsra() & ~ADIE);

        acsr(acsr() & ~ACIE);

        eecr(eecr() & ~EERIE);

        twcr(twcr() & ~TWIE);
    }
    static void disable(IRQ irq) {

        if (IRQ_IRQ0 == irq) { gicr(gicr() & ~INT0); return; }
        if (IRQ_IRQ1 == irq) { gicr(gicr() & ~INT1); return; }
        if (IRQ_IRQ2 == irq) { gicr(gicr() & ~INT2); return; }

        if (IRQ_TIMER2_COMP == irq) { timsk(timsk() & ~OCIE2); return; }
        if (IRQ_TIMER2_OVF == irq) { timsk(timsk() & ~TOIE2); return; }

        if (IRQ_TIMER1_CAPT == irq) { timsk(timsk() & ~TICIE1); return; }
        if (IRQ_TIMER1_COMPA == irq) { timsk(timsk() & ~OCIE1A); return; }
        if (IRQ_TIMER1_COMPB == irq) { timsk(timsk() & ~OCIE1B); return; }
        if (IRQ_TIMER1_OVF == irq) { timsk(timsk() & ~TOIE1); return; }

        if (IRQ_TIMER0_OVF == irq) { timsk(timsk() & ~TOIE0); return; }
        if (IRQ_TIMER0_COMP == irq) { timsk(timsk() & ~OCIE0); return; }

        if (IRQ_SPI_STC == irq) { spcr(spcr() & ~SPIE); return; }

        if (IRQ_USART0_RXC == irq) { ucsrb(ucsrb() & ~RXCIE); return; }
        if (IRQ_USART0_UDRE == irq) { ucsrb(ucsrb() & ~UDRIE); return; }
        if (IRQ_USART0_TXC == irq) { ucsrb(ucsrb() & ~TXCIE); return; }

        if (IRQ_ADC == irq) { adcsra(adcsra() & ~ADIE); return; }

        if (IRQ_ANA_COMP == irq) { acsr(acsr() & ~ACIE); return; }

        if (IRQ_EE_RDY == irq) { eecr(eecr() & ~EERIE); return; }

        if (IRQ_TWI == irq) { twcr(twcr() & ~TWIE); return; }

        // if (IRQ_RESET  == irq) {return;}   // Always enabled
        // if (IRQ_SPM_RDY  == irq) {return;} // Unused
    }

private:
    static Reg8 gicr() { return AVR8::in8(GICR); }
    static void gicr(Reg8 value) { AVR8::out8(GICR,value); }
    static Reg8 gifr() { return AVR8::in8(GIFR); }
    static void gifr(Reg8 value) {AVR8::out8(GIFR,value); }
    static Reg8 timsk() { return AVR8::in8(TIMSK); }
    static void timsk(Reg8 value) {AVR8::out8(TIMSK,value); }
    static Reg8 tifr() { return AVR8::in8(TIFR); }
    static void tifr(Reg8 value) {AVR8::out8(TIFR,value); }
    static Reg8 twcr() { return AVR8::in8(TWCR); }
    static void twcr(Reg8 value) { AVR8::out8(TWCR,value); }
    static Reg8 mcucr() { return AVR8::in8(MCUCR); }
    static void mcucr(Reg8 value) {AVR8::out8(MCUCR,value); }
    static Reg8 mcucsr() { return AVR8::in8(MCUCSR); }
    static void mcucsr(Reg8 value) {AVR8::out8(MCUCSR,value); }
    static Reg8 eecr() { return AVR8::in8(EECR); }
    static void eecr(Reg8 value) {AVR8::out8(EECR,value); }
    static Reg8 spsr() { return AVR8::in8(SPSR); }
    static void spsr(Reg8 value) {AVR8::out8(SPSR,value); }
    static Reg8 spcr() { return AVR8::in8(SPCR); }
    static void spcr(Reg8 value) {AVR8::out8(SPCR,value); }
    static Reg8 ucsra() { return AVR8::in8(UCSRA); }
    static void ucsra(Reg8 value) {AVR8::out8(UCSRA,value); }
    static Reg8 ucsrb() { return AVR8::in8(UCSRB); }
    static void ucsrb(Reg8 value) {AVR8::out8(UCSRB,value); }
    static Reg8 acsr() { return AVR8::in8(ACSR); }
    static void acsr(Reg8 value) {AVR8::out8(ACSR,value); }
    static Reg8 adcsra() { return AVR8::in8(ADCSRA); }
    static void adcsra(Reg8 value) {AVR8::out8(ADCSRA,value); }
};

__END_SYS

#endif