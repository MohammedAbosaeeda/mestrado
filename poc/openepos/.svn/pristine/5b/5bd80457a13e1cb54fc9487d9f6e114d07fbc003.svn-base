// OpenEPOS Microphone Abstraction Declarations

#ifndef __microphone_h
#define __microphone_h

#include <adc.h>

#ifdef __ADC_H

__BEGIN_SYS

class Microphone : public ADC
{
public:
	Microphone(unsigned int unit = 0) : ADC(Traits<Microphone>::ADC_CHANNEL)
	{
		ADC::get(); // discard first read
	}

	virtual ~Microphone() {}
};

__END_SYS

#endif

#endif
