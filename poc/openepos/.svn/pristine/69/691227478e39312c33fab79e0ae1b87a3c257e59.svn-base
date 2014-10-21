// EPOS PrimeCell UART (PL011) Mediator Declarations

#ifndef __emote3_pl011_h__
#define __emote3_pl011_h__

#include <uart.h>
#include <cpu.h>
#include <mach/common/cortex_m3.h>

__BEGIN_SYS

// PrimeCell UART (PL011)
class PL011: protected Cortex_M3
{
private:
    typedef CPU::Log_Addr Log_Addr;
    typedef CPU::Reg8 Reg8;
    typedef CPU::Reg32 Reg32;

    static const unsigned int UNITS = Traits<UART>::UNITS;
    static const unsigned int CLOCK = Traits<UART>::CLOCK / 16;

public:
    // Register offsets
    enum {                              // Description                  Type    Value after reset
        DR          = 0x000,    // Data                         r/w 0x00000000
        RSR             = 0x004,        // Receive Status               r/w     0x00000000
        ECR             = 0x004,        // Error Clear                  r/w     0x00000000
        FR          = 0x018,    // Flag                         ro  0x00000090
        IBRD            = 0x024,    // Integer Baud-Rate Divisor    r/w 0x00000000
        FBRD        = 0x028,    // Fractional Baud-Rate Divisor r/w 0x00000000
        LCRH            = 0x02c,    // Line Control             r/w 0x00000000
        UCR         = 0x030,    // Control                  r/w 0x00000300
        IFLS        = 0x034,    // Interrupt FIFO Level Select  r/w 0x00000012
        UIM         = 0x038,    // Interrupt Mask           r/w 0x00000000
        RIS         = 0x03c,    // Raw Interrupt Status         ro  0x0000000f
        MIS         = 0x040,    // Masked Interrupt Status  ro  0x00000000
        ICR             = 0x044,        // Interrupt Clear              w1c     0x00000000
        DMACR           = 0x048,        // DMA Control                  rw      0x00000000
        PeriphID4   = 0xfd0,    // Peripheral Identification 4  ro  0x00000000
        PeriphID5   = 0xfd4,    // Peripheral Identification 5  ro  0x00000000
        PeriphID6   = 0xfd8,    // Peripheral Identification 6  ro  0x00000000
        PeriphID7   = 0xfdc,    // Peripheral Identification 7  ro  0x00000000
        PeriphID0   = 0xfe0,    // Peripheral Identification 0  ro  0x00000011
        PeriphID1   = 0xfe4,    // Peripheral Identification 1  ro  0x00000000
        PeriphID2   = 0xfe8,    // Peripheral Identification 2  ro  0x00000018
        PeriphID3   = 0xfec,    // Peripheral Identification 3  ro  0x00000001
        PCellID0    = 0xff0,    // PrimeCell Identification 0   ro  0x0000000d
        PCellID1    = 0xff4,    // PrimeCell Identification 1   ro  0x000000f0
        PCellID2    = 0xff8,    // PrimeCell Identification 2   ro  0x00000005
        PCellID3    = 0xffc,     // PrimeCell Identification 3   ro  0x000000b1

        /* emote3 specifics */
        ILPR        = 0x020,
        CTL         = 0x030,
        IM          = 0x038,
        DMACTL      = 0x048, //RW 32 0x00000000
        LCTL        = 0x090, //RW 32 0x00000000 
        LSS         = 0x094, //RO 32 0x00000000 
        LTIM        = 0x098, //RO 32 0x00000000 
        NINEBITADDR = 0x0A4, //RW 32 0x00000000 
        NINEBITAMASK= 0x0A8, //RW 32 0x000000FF 
        PP          = 0xFC0, //RO 32 0x00000003 
        CC          = 0xfc8  //RW 32 0x00000000
    };

    // Useful Bits in the Flag Register
    enum {                              // Description                  Type    Value after reset
        CTS             = 1 <<  0,      // Clear to Send                r/w     0
        DSR             = 1 <<  1,      // Data Set Ready               r/w     0
        DCD             = 1 <<  2,      // Data Carrier Detect          r/w     0
        BUSY            = 1 <<  3,      // Busy transmitting data       r/w     0
        RXFE            = 1 <<  4,      // Receive FIFO Empty           r/w     1
        TXFF            = 1 <<  5,      // Transmit FIFO Full           r/w     0
        RXFF            = 1 <<  6,      // Receive FIFO Full            r/w     0
        TXFE            = 1 <<  7,      // Transmit FIFO Empty          r/w     1
        RI              = 1 <<  8,      // Ring Indicator               r/w     0
    };

    // Useful Bits in the Line Control
    enum {                              // Description                  Type    Value after reset
        BRK             = 1 <<  0,      // Send Break                   r/w     0
        PEN             = 1 <<  1,      // Parity Enable                r/w     0
        EPS             = 1 <<  2,      // Even Parity Select           r/w     0
        STP2            = 1 <<  3,      // Two Stop Bits Select         r/w     0
        FEN             = 1 <<  4,      // FIFOs Enable                 r/w     0
        WLEN5           = 0 <<  5,      // Word Length 5 bits           r/w     0
        WLEN6           = 1 <<  5,      // Word Length 6 bits           r/w     0
        WLEN7           = 2 <<  5,      // Word Length 7 bits           r/w     0
        WLEN8           = 3 <<  5,      // Word Length 8 bits           r/w     0
        SPS             = 1 <<  7       // Stick Parity Select          r/w     0
    };

    // Useful Bits in the Control Register
    enum {                              // Description                  Type    Value after reset
        UEN             = 1 <<  0,      // Enable                       r/w     0
        LBE             = 1 <<  7,      // Loop Back Enable             r/w     0
        TXE             = 1 <<  8,      // Transmit Enable              r/w     1
        RXE             = 1 <<  9       // Receive Enable               r/w     1
    };

    // Useful Bits in the Interrupt Mask Register
    enum {                              // Description                  Type    Value after reset
        UIMRX           = 1 <<  4,      // Receive                      r/w     0
        UIMTX           = 1 <<  5,      // Transmit                     r/w     0
        UIMRT           = 1 <<  6,      // Receive Time-Out             r/w     0
        UIMFE           = 1 <<  7,      // Framing Error                r/w     0
        UIMPE           = 1 <<  8,      // Parity Error                 r/w     0
        UIMBE           = 1 <<  9,      // Break Error                  r/w     0
        UIMOE           = 1 << 10,      // Overrun Error                r/w     0
        UIMALL          = 0
    };

public:
    PL011(unsigned int unit, unsigned int baud_rate, unsigned int data_bits, unsigned int parity, unsigned int stop_bits):
        _base(reinterpret_cast<Log_Addr *>(unit ? UART1_BASE : UART0_BASE)) {
        assert(unit < UNITS);
        config(baud_rate, data_bits, parity, stop_bits);
        //test_config(baud_rate, data_bits, parity, stop_bits); // Copied from TI's code. For initial tests only.
    }

    void config(unsigned int baud_rate, unsigned int data_bits, unsigned int parity, unsigned int stop_bits) {

        test_config_clock(); // Configure the machine clock. Should be somewhere else.

        /*
            From TI's Manual:
            SWRU319C – April 2012 – Revised May 2013

        18.5 Initialization and Configuration
            To enable and initialize the UART, the following steps are necessary:
            1. Enable the UART module using the SYS_CTRL_RCGCUART register (see SYS_CTRL_RCGCUART).
            2. Set the GPIO pin configuration through the Pxx_SEL registers for the desired output
            pins using the appropriate signal select value.
            3. To enable IO pads to drive outputs, the corresponding IPxx_OVER bits in IOC_Pxx_OVER register
            has to be configured to 0x8 (OE - Output Enable) (see Section 9.2.2.5).
            4. Connect the appropriate input signals to the UART module through the following registers:
            * IOC_UARTRXD_UART0 
            * IOC_UARTRXD_UART1 
            * IOC_UARTCTS_UART1 
            5. For more information on pin connections, see Section 9.1.1, I/O Muxing, of Chapter 9, General
            Purpose Input/Outputs (GPIO).
       */

        if(_base == reinterpret_cast<Log_Addr *>(UART0_BASE)) {
            //1. Enable the UART module using the SYS_CTRL_RCGCUART register.
            scr(RCGCUART) |= RCGCUART_UART0; // Enable clock for UART0 while in Running mode

            //2. Set the GPIO pin configuration through the Pxx_SEL registers for the desired output
            ioc(PA1_SEL) = 0;

            //3. To enable IO pads to drive outputs, the corresponding IPxx_OVER bits in IOC_Pxx_OVER register
            //   has to be configured to 0x8 (OE - Output Enable).
            ioc(PA1_OVER) = OE;
            ioc(PA0_OVER) = 0;

            //4. Connect the appropriate input signals to the UART module        
            // The value is calculated as: (port << 3) + pin
            ioc(UARTRXD_UART0) = (0 << 3) + 0;

            //5. Set GPIO pins A1 and A0 to peripheral mode
            gpioa(AFSEL) |= (AFSEL_ALTP0) + (AFSEL_ALTP1);

        } else {
            while(1)
            ;
        }

        // Disable UART        
        reg(LCRH) &= ~(FEN);             // Disable FIFOs
        reg(UCR) &= ~(UEN | TXE | RXE);  // Disable UART for configuration
        reg(ICR) = ~0;                   // Clear all interrupts
        reg(UIM) = UIMALL;               // Disable all interrupts
        /*
          TI's code does this instead:
          reg(UIM) &= ~0x1fff;           

          which preserves the bits:
          LME5IM - LIN mode edge 5 interrupt mask
          LME1IM - LIN mode edge 1 interrupt mask
          (UIM's reset value is 0)
        */

        // Baud rate setup
        Reg32 clock;
        switch(scr(CLOCK_STA) & 7)
        {
            /*
            * These are the possible values on SYS_CTRL_CLOCK_STA register,
            * should be replaced by Traits' CLOCK when the clock configuration
            * is done in its right place.
            */
            case 0: clock = 32000000; break;
            case 1: clock = 16000000; break;
            case 2: clock =  8000000; break;
            case 3: clock =  4000000; break;
            case 4: clock =  2000000; break;
            case 5: clock =  1000000; break;
            case 6: clock =   500000; break;
            case 7: clock =   250000; break;
        }
        Reg32 br = (((clock * 8) / baud_rate) + 1) / 2;
        reg(IBRD) = br / 64;
        reg(FBRD) = br % 64;
        /* old code:
        Reg32 br = CLOCK / (baud_rate / 300);   // Factor by the minimum BR to preserve meaningful bits of FBRD
        reg(IBRD) = br / 300;                   // IBRD = int(CLOCK / baud_rate)
        reg(FBRD) = br / 1000;                  // FBRD = int(0.1267 * 64 + 0.5) = 8
        */

        reg(LCRH) = WLEN8 | FEN;      // 8 bit word length (no parity bits, one stop bit, FIFOs)
        reg(UIM) &= ~(UIMTX | UIMRX); // Mask TX and RX interrupts for polling operation
        reg(FR) = 0;                  // Clear flags
        reg(UCR) |= UEN | TXE | RXE;  // Enable UART
        
        /*
        // Testing
        while(1)
        {
//           unsigned int volatile * const _dr = (unsigned int *) 0x4000c000;
            unsigned int volatile * const _fr = (unsigned int *) 0x4000c018;
            while(*_fr & (1 << 5));
//            while(reg(FR) & (1 << 5));
            reg(DR) = 'a';
//            *_dr = 'a';
            while(*_fr & (1 << 5));
//            while(reg(FR) & (1 << 5));
            reg(DR) = '\n';
//            *_dr = '\n';
        }
        */
    }

    // Used only by test_config_clock, copied from TI's code
    void __attribute__((naked))
        SysCtrlDelay(unsigned int ui32Count)
        {
            asm volatile("CHARLIES:    subs    r0, #1\n"
                    "    bne     CHARLIES\n"
                    "    bx      lr");
        }

    // Clock configuration copied from TI's code
    void test_config_clock() {
        unsigned int volatile * const _clock = (unsigned int *) 0x400D2000;//SYS_CTRL_CLOCK_CTRL
        *_clock |= 0x00200000;//1 << 21; // AMP_DET 

        unsigned int val = *_clock & ~((1 << 24) | (1 << 16) | (7)); //ui32Reg &= ~(SYS_CTRL_CLOCK_CTRL_OSC32K | SYS_CTRL_CLOCK_CTRL_OSC | SYS_CTRL_CLOCK_CTRL_SYS_DIV_M);
        val |= 1 << 24;// ui32Reg |= SYS_CTRL_CLOCK_CTRL_OSC32K;

        *_clock = val;

        unsigned int volatile * const _sta = (unsigned int *) 0x400d2004;//UART0_BASE + CC;
        val = *_sta;
        unsigned int i = 0;
        while(((*_sta & 0x00010000) != 0) && i < 0x0000ffff)
        {
            SysCtrlDelay(16);
            i++;
        }

        *_clock &= ~(0x00000700);
    }

    // Config confirmed to work, copied from TI's code.
    void test_config(unsigned int baud_rate, unsigned int data_bits, unsigned int parity, unsigned int stop_bits) {
        test_config_clock();

        unsigned int volatile * const _lcrh = (unsigned int *) 0x4000c02c;
        unsigned int volatile * const _ucr = (unsigned int *) 0x4000c030;//UART0_BASE + CC;
        unsigned int volatile * const _icr = (unsigned int *) 0x4000c044;//UART0_BASE + CC;
        unsigned int volatile * const _uim = (unsigned int *) 0x4000c038;//UART0_BASE + CC;
        scr(RCGCUART) |= RCGCUART_UART0; // Enable clock for UART0 while in Running mode
        *_lcrh &= ~(FEN);                    // Disable FIFOs)
        *_ucr &= ~(UEN | TXE | RXE);                       // Disable UART for configuration
        *_icr = ~0;                          // Clear all interrupts
        *_uim &= ~0x1fff;

        unsigned int volatile * const _cc = (unsigned int *) 0x4000cfc8;//UART0_BASE + CC;
        *_cc = 1;
        unsigned int volatile * const _pa1sel = (unsigned int *) 0x400d4004;//IOC_BASE + PA1_SEL;
        *_pa1sel = 0;
        unsigned int volatile * const _gpiodir = (unsigned int *) 0x400da400;//IOC_BASE + UARTRXD_UART0;
        *_gpiodir &= ~3;

        unsigned int volatile * const _afsel = (unsigned int *) 0x400d9420;//IOC_BASE + UARTRXD_UART0;
        *_afsel |= 3;

        unsigned int volatile * const _pa1over = (unsigned int *) 0x400d4084;//IOC_BASE + PA1_OVER;
        *_pa1over = 0x8;//OE;
        unsigned int volatile * const _pa0over = (unsigned int *) 0x400d4080;//IOC_BASE + PA0_OVER;
        *_pa0over = 0;
        unsigned int volatile * const _uartrxd_uart0 = (unsigned int *) 0x400d4100;//IOC_BASE + UARTRXD_UART0;
        *_uartrxd_uart0 = (0 << 3) + 0;
        unsigned int volatile * const _sta = (unsigned int *) 0x400d2004;//UART0_BASE + CC;
        unsigned int sys_div = *_sta & 7;
        Reg32 clock;
        switch (sys_div)
        {
            case 0:
                clock = 32000000; break;
            case 1:
                clock = 16000000; break;
            case 2:
                clock =  8000000; break;
            case 3:
                clock =  4000000; break;
            case 4:
                clock =  2000000; break;
            case 5:
                clock =  1000000; break;
            case 6:
                clock =   500000; break;
            case 7:
                clock =   250000; break;
        }
        Reg32 br = (((clock * 8) / baud_rate) + 1) / 2;   // Factor by the minimum BR to preserve meaningful bits of FBRD
        unsigned int volatile * const _ibrd = (unsigned int *) 0x4000c024;//UART0_BASE + CC;
        unsigned int volatile * const _fbrd = (unsigned int *) 0x4000c028;//UART0_BASE + CC;
        *_ibrd = br / 64;                   // IBRD = int(CLOCK / baud_rate)
        *_fbrd = br % 64;                  // FBRD = int(0.1267 * 64 + 0.5) = 8
        *_lcrh = WLEN8 | FEN;                // 8 bit word length (no parity bits, one stop bit, FIFOs)
//      reg(LCRH) = WLEN8 | FEN;
        if((*_lcrh & 0xff)!= 0x70)
        {
            while(1);
        }
        *_ucr |= UEN | TXE | RXE;            // Enable UART
        unsigned int volatile * const _fr = (unsigned int *) 0x4000c018;
        *_fr = 0;

        while(1)
        {
            unsigned int volatile * const _dr = (unsigned int *) 0x4000c000;
            while(*_fr & (1 << 5));
            *_dr = 'a';
            while(*_fr & (1 << 5));
            *_dr = '\n';
        }
    }

    void config(unsigned int * baud_rate, unsigned int * data_bits, unsigned int * parity, unsigned int * stop_bits) {
        *data_bits = (reg(LCRH) & WLEN8) ? 8 : (reg(LCRH) & WLEN7) ? 7 : (reg(LCRH) & WLEN6) ? 6 : 5;
        *parity = (reg(LCRH) & PEN) ? (reg(LCRH) & EPS) ? UART_Common::EVEN : UART_Common::ODD : UART_Common::NONE;
        *stop_bits = (reg(LCRH) & STP2) ? 2 : 1;
    }

    Reg8 rxd() { return reg(DR); }
    void txd(Reg8 c) { reg(DR) = c; }

    void int_enable(bool receive = true, bool send = true, bool line = true, bool modem = true) {
        reg(UIM) &= ~((receive ? UIMRX : 0) | (send ? UIMTX : 0));
    }
    void int_disable(bool receive = true, bool send = true, bool line = true, bool modem = true) {
        reg(UCR) |= (receive ? UIMRX : 0) | (send ? UIMTX : 0);
    }

    void reset() {
        // There is no software reset on the PL011, so the best we can do is refresh its configuaration
        unsigned int b, db, p, sb;
        config(&b, &db, &p, &sb);
        config(b, db, p, sb);
    }

    void loopback(bool flag) {
        if(flag)
            reg(UCR) |= int(LBE);
        else
            reg(UCR) &= ~LBE;
    }

    bool rxd_ok() { 
        return !(reg(FR) & RXFE); 
    }
    bool txd_ok() { 
        return !(reg(FR) & TXFF); 
    }

private:
    Log_Addr & reg(unsigned int o) { return _base[o / sizeof(Log_Addr)]; }

private:
    Log_Addr * _base;
};

__END_SYS

#endif
