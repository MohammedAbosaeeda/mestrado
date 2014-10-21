// EPOS-- SoftMIPS UART Mediator Declarations

#ifndef __softmips_uart_h
#define __softmips_uart_h

//#include "buffer.h"
#include <uart.h>
#include <arch/mips32/plasma.h>

__BEGIN_SYS

class SoftMIPS_UART: public UART_Common
{
private:
    /*enum
    {
	IS_READY = 0x40000000,
	HAS_DATA = 0x80000000,
    };

    enum
    {
	INT0 = 4,
	INT1 = 5,
	};*/

public:
	SoftMIPS_UART(unsigned int unit = 0) : _nr(unit)
	{
		//_uart[_nr] = this;
		
		//_addr = 0x20000000;
	}
    SoftMIPS_UART(unsigned int baud, unsigned int data_bits,
		  unsigned int parity, unsigned int stop_bits,
		  unsigned int unit = 0) {}
    ~SoftMIPS_UART() {}

    char get() {}
    void put(char c)
	{
    	while((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS) & IRQ_UART_WRITE_AVAILABLE) == 0);
	
		*reinterpret_cast<volatile unsigned int *>(UART_WRITE) = c;
	}

    void loopback(bool flag) {}

    static int init(System_Info * si) { return 0; }

private:
    /*unsigned long io_flags() {
	return *((volatile unsigned long *)(_addr + 0x4));
    };

    unsigned char io_data() {
	return *((volatile unsigned char *)_addr);
    };

    void io_data(unsigned char vl) {
	*((volatile unsigned char *)_addr) = vl;
	};*/

private:
    unsigned long _addr;
    int	_nr;

    static SoftMIPS_UART *_uart[];
};

__END_SYS

#endif
