// EPOS-- SoftMIPS UART Mediator Implementation

#include <mach/softmips/uart.h>

__BEGIN_SYS

   /*SoftMIPS_UART::SoftMIPS_UART(unsigned int unit = 0) : _nr(unit) {
		_uart[_nr] = this;
		
		if(_nr == 0) {
			Machine::int_vector(INT0, my_handler_0);
			_addr = 0x20000000;
		} else {
			Machine::int_vector(INT0, my_handler_1);
			_addr = 0x20000000; //aqui deveria ser a com2, mas deixemos assim
   		}
    }

    SoftMIPS_UART::SoftMIPS_UART(unsigned int baud, unsigned int data_bits,
		  unsigned int parity, unsigned int stop_bits,
		  unsigned int unit = 0) 
	: _addr(0x0)
	{
		if(_uart[0] == 0) { _nr = 0; _addr = 0x20000000; }
		else { _nr = 1; _addr = 0x20000000; };
		
		_uart[_nr] = this;
			
		if(_nr == 0)
			Machine::int_vector(INT0, my_handler_0);
		else
			Machine::int_vector(INT0, my_handler_1);
    }

    ~SoftMIPS_UART() {
		int intr = (_nr == 0 ? INT0 : INT1);
	
		Machine::int_vector(intr, 0);
    }
SoftMIPS_UART *SoftMIPS_UART::_uart[2] = { (SoftMIPS_UART *)0, (SoftMIPS_UART *)0 };

int SoftMIPS_UART::get(unsigned char *rec_char)
{
	_inp.read(rec_char);
	return 1;
}

int SoftMIPS_UART::put(unsigned char sen_char)
{
	unsigned char byte;

	_out.write(sen_char);

	while (io_flags() & IS_READY)
	{
		if (_out.read_nb(&byte))
			io_data(byte);
		else
			break;
	}

	return 1;
}

void SoftMIPS_UART::my_handler()
{
	unsigned char	byte = 0;

	switch (io_flags() & (IS_READY|HAS_DATA))
	{
		case (IS_READY|HAS_DATA):
		case  HAS_DATA:
			_inp.write_nb(io_data());
			break;
		case  IS_READY:
			if (_out.read_nb(&byte))
				io_data(byte);
			break;
		default:
			break;
	}
}

void SoftMIPS_UART::my_handler_0()
{
	CPU::int_enable();
	SoftMIPS_UART::_uart[0]->my_handler();
	CPU::int_disable();
}

void SoftMIPS_UART::my_handler_1()
{
	CPU::int_enable();
	SoftMIPS_UART::_uart[1]->my_handler();
	CPU::int_disable();
}
*/

SoftMIPS_UART *SoftMIPS_UART::_uart[2] = { (SoftMIPS_UART *)0, (SoftMIPS_UART *)0 };

__END_SYS
