// EPOS-- LEON2 UART Mediator Declarations

#ifndef __leon2_uart_h
#define __leon2_uart_h

#include <uart.h>
#include <cpu.h>

__BEGIN_SYS

class LEON2_UART: public UART_Common
{
private:

////    typedef CPU::IO_Port IO_Port;
    typedef unsigned int IO_Port;   //// mudar.

    //UART Registers
    enum {
      DATA_REG = Traits<LEON2_UART>::BASE_ADDRESS + 0x0,
      STAT_REG = Traits<LEON2_UART>::BASE_ADDRESS + 0x4,
      CTRL_REG = Traits<LEON2_UART>::BASE_ADDRESS + 0x8,
      SCAL_REG = Traits<LEON2_UART>::BASE_ADDRESS + 0xC
    };

    // Control bits
    enum {
        FLAG_RXEN    = 0x00001,
        FLAG_TXEN    = 0x00002,
        FLAG_RXIEN   = 0x00004,
        FLAG_TXIEN   = 0x00008,
        FLAG_ODDEN   = 0x00010,
        FLAG_PAREN   = 0x00020,
        FLAG_CTRTS   = 0x00040,
        FLAG_LOOPB   = 0x00080,
        FLAG_EXCLK   = 0x00100,

        FLAG_DEFAULT = (FLAG_RXEN | FLAG_TXEN
                        & ~FLAG_RXIEN & ~FLAG_TXIEN
                        & ~FLAG_ODDEN & ~FLAG_PAREN
                        & ~FLAG_CTRTS & ~FLAG_LOOPB
                        & ~FLAG_EXCLK),

        FLAG_CLEAR   = (FLAG_DEFAULT & ~FLAG_RXEN & ~FLAG_TXEN)
    };

    //Status bits
    enum {
        FLAG_DATARDY  = 0x00001,
        FLAG_STXRDY   = 0x00002,
        FLAG_TXREADY  = 0x00004,
        FLAG_BREAK    = 0x00008,
        FLAG_OVRUN    = 0x00010,
        FLAG_PARERR   = 0x00020,
        FLAG_FRMERR   = 0x00040,

        FLAG_STATCLR  = 0x00000
    };


public:
    LEON2_UART(unsigned int unit = 0) {
       *(reinterpret_cast<volatile unsigned int *>(STAT_REG)) = FLAG_STATCLR;
       *(reinterpret_cast<volatile unsigned int *>(CTRL_REG)) = FLAG_DEFAULT;
       config(19200, 8, 1, 1);
    }
    
    LEON2_UART(unsigned int baud, unsigned int data_bits, unsigned int parity, unsigned int stop_bits, unsigned int unit = 0) 
    {}

    void config(unsigned int baud, unsigned int data_bits,
		unsigned int parity, unsigned int stop_bits) {
/*---------------------------------------
      unsigned int ramate;
      rate = (((10 * Traits<CPU>::CLOCK) /(8*baud))- 5)/10;
      *(reinterpret_cast<volatile unsigned int *>(SCAL_REG)) = rate;
      if (parity) {
        *(reinterpret_cast<volatile unsigned int *>(CTRL_REG)) |= FLAG_PAREN;
         if (parity == ODD_PARITY) 
           *(reinterpret_cast<volatile unsigned int *>(CTRL_REG)) |= FLAG_ODDEN;
         else 
           *(reinterpret_cast<volatile unsigned int *>(CTRL_REG)) &= ~FLAG_ODDEN;
      } else 
        *(reinterpret_cast<volatile unsigned int *>(CTRL_REG)) &= ~FLAG_PAREN;
      return 1;
-------------------------------------------*/
    }


    void config(unsigned int * baud, unsigned int * data_bits,
		unsigned int * parity, unsigned int * stop_bits) {}

    char get() { 
        ////while(!rxd_full()); return rxd(); 
        while(!(*(reinterpret_cast<volatile unsigned int *>(STAT_REG)) & FLAG_DATARDY));
        return *(reinterpret_cast<volatile char *>(DATA_REG));
    }

    void put(char c) {
        //// while(!txd_empty()); txd(c); 
        while(!(*(reinterpret_cast<volatile unsigned int *>(STAT_REG)) & FLAG_TXREADY));
        *(reinterpret_cast<volatile char *>(DATA_REG)) = c;
        
    }

    void loopback(bool flag) {}

    static int init(System_Info * si) { return 0; }

private:
    static const IO_Port _ports[];
};

__END_SYS

#endif
