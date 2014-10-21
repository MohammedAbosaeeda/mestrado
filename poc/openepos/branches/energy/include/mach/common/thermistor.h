// OpenEPOS Thermistor Abstraction Declarations

#ifndef __thermistor_h
#define __thermistor_h

#include <adc.h>
#include <traits.h>
#include <utility/math.h>

#ifdef __ADC_H

__BEGIN_SYS

class Thermistor : public ADC {
protected:
	static const float adc_max_value;
	static const float to_celcius_factor;
	static const float ERROR;
	static const float div_resistor;
	static const float Avalue;
	static const float Bvalue;
	static const float Cvalue;

public:
	Thermistor(unsigned int unit = 0);

	int get();
	int sample();

private:
	static float convert_temperature(int value);
	static float resistance(int read);
};

__END_SYS

#endif

#endif
