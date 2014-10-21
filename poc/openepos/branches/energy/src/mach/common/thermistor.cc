// OpenEPOS Termistor Mediator Implementation

#include <mach/common/thermistor.h>

#ifdef __ADC_H

__BEGIN_SYS

const float Thermistor::adc_max_value = (1 << Traits<ADC>::RESOLUTION) - 1;
const float Thermistor::to_celcius_factor = Traits<Thermistor>::CELCIUS * 273.15;
const float Thermistor::ERROR = Traits<Thermistor>::ERROR;
const float Thermistor::div_resistor = Traits<Thermistor>::DIV_RESISTANCE;
const float Thermistor::Avalue = Traits<Thermistor>::Av;
const float Thermistor::Bvalue = Traits<Thermistor>::Bv;
const float Thermistor::Cvalue = Traits<Thermistor>::Cv;

Thermistor::Thermistor(unsigned int unit)
 : ADC(Traits<Thermistor>::ADC_CHANNEL)
{
	ADC::get(); // discard first read
}

int Thermistor::get()
{
	return resistance(ADC::get());
}

int Thermistor::sample()
{
	return convert_temperature(ADC::get()) - to_celcius_factor;
}

float Thermistor::convert_temperature(int value)
{
	// Steinhart-Hart Model: T = 1 / (A + B*logR + C*(logR^3))
	float log_R = Math::logf(resistance(value));
	return (1.0f
			/ (Avalue + Bvalue * log_R + Cvalue * log_R * log_R * log_R)
		   )
		   + ERROR;
}

float Thermistor::resistance(int read) {
	return (((float) adc_max_value / (float) (adc_max_value - read)) - 1.0f)
			* (float) div_resistor;
}


__END_SYS

#endif
