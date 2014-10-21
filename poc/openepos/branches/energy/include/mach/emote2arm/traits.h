// OpenEPOS EMote2ARM Machine Traits

#ifndef __emote2arm_traits_h
#define __emote2arm_traits_h

#include <system/config.h>
#include <system/types.h>

// Mediators - Machine - EMote2ARM

__BEGIN_SYS

class EMote2ARM_Common;

template <> struct Traits<EMote2ARM_Common>: public Traits<void>
{
};

template <> struct Traits<EMote2ARM>: public Traits<EMote2ARM_Common>
{
	static const unsigned int MAX_CPUS = 1;
	static const unsigned int CLOCK = 24000000;
	static const unsigned int APPLICATION_STACK_SIZE = 1024;
	static const unsigned int APPLICATION_HEAP_SIZE = 3072;
	static const unsigned int SYSTEM_HEAP_SIZE = 2*APPLICATION_HEAP_SIZE
	           + 4096; // so flash can work
	static const bool SMP = false;

	static const unsigned int PRIORITY_HEAP_BASE_ADDR = 0;
	static const unsigned int PRIORITY_HEAP_SIZE = 4;
	static const unsigned int PRIORITY_HEAP_TOP_ADDR = PRIORITY_HEAP_BASE_ADDR + PRIORITY_HEAP_SIZE - 1;

	/*
	 * EPOSMote Versions:
     *  - EPOSMoteI Alfa:   0x1a;
     *  - EPOSMoteI Beta:   0x1b;
     *  - EPOSMoteI Final:  0x1f;
     *  - EPOSMoteII Alfa:  0x2a;
     *  - EPOSMoteII Beta:  0x2b;
     *  - EPOSMoteII Final: 0x2f;
	 */
    static const unsigned int emote_version = 0x2f;
	static const bool flash_erase_checking = true;
//    static const unsigned int flash_erase_checking_pin = 11; // OpenEPOSMoteII-ARM7 Beta
    static const unsigned int flash_erase_checking_pin = 24; // OpenEPOSMoteII-ARM7
};

template <> struct Traits<EMote2ARM_Battery>: public Traits<EMote2ARM_Common>
{
    static const bool enabled = true;
    static const bool buck_enabled = false;
    static const unsigned int buck_voltage_threshold = 2500; // in mV
    static const unsigned int frequency = 1; // in Hz
};

template <> struct Traits<EMote2ARM_IC>: public Traits<EMote2ARM_Common>
{
    static const bool enabled = true;
    static const bool debugged = false;
};

template <> struct Traits<EMote2ARM_Flash>: public Traits<EMote2ARM_Common>
{
    static const bool enabled = true;
};

template <> struct Traits<EMote2ARM_UART>: public Traits<EMote2ARM_Common>
{
};

template <> struct Traits<EMote2ARM_SPI>: public Traits<EMote2ARM_Common>
{
};

template <> struct Traits<EMote2ARM_One_Wire>: public Traits<EMote2ARM_Common>
{
};

template <> struct Traits<EMote2ARM_I2C>: public Traits<EMote2ARM_Common>
{
    static const bool enabled = true;
    static const bool debugged = true;

    static const bool BROADCAST = false;
    static const unsigned char FREQUENCY_DIVIDER = 0x20;
};

template <> struct Traits<EMote2ARM_Timer_0>: public Traits<EMote2ARM_Common>
{
    static const int FREQUENCY = 1000; // default system timer, in Hz
};

template <> struct Traits<EMote2ARM_Timer_1>: public Traits<EMote2ARM_Common>
{
	static const int FREQUENCY = 1000;
};

template <> struct Traits<EMote2ARM_Timer_2>: public Traits<EMote2ARM_Common>
{
	static const int FREQUENCY = 1000;
};

template <> struct Traits<EMote2ARM_Timer_3>: public Traits<EMote2ARM_Common>
{
	static const int FREQUENCY = 1000;
};

template <> struct Traits<EMote2ARM_RTC>: public Traits<EMote2ARM_Common>
{
	static const unsigned int EPOCH_DAYS = 1;
};

template <> struct Traits<EMote2ARM_Display>: public Traits<EMote2ARM_Common>
{
	static const bool on_serial = true;
};

template <> struct Traits<EMote2ARM_NIC>: public Traits<void>
{
    static const bool enabled = true;

    typedef LIST<EMote2ARM_Radio> NICS;

    static const unsigned int RADIO_UNITS = NICS::Count<EMote2ARM_Radio>::Result;
};

template <> struct Traits<EMote2ARM_ADC>: public Traits<void>
{
    enum Power_Modes
    {
        FULL        = 0,
        LIGHT       = 1,
        STANDBY     = 2,
        OFF         = 3
    };
    static const int RESOLUTION = 12; //bits
};

template <> struct Traits<Thermistor> : public Traits<void>
{
	static const int ADC_CHANNEL = 3;
	static const int CELCIUS = 1; // 0 for KELVIN
	static const float ERROR = 3.7f; // systematic error

	static const float DIV_RESISTANCE = 20000; // circuit-specific

	// Parameters bellow are used in the Stainhart-Hart model and are device-specific
    static const float Av = 0.0010291947674225f;
    static const float Bv = 0.000239127518397795f;
    static const float Cv = 0.000000156627714973031f;
};

template <> struct Traits<EMote2ARM_Temperature_Sensor>: public Traits<void>
{
    typedef LIST<Thermistor> SENSORS;
};

template <> struct Traits<Microphone> : public Traits<void>
{
	static const int ADC_CHANNEL = 3;
};

template <> struct Traits<EMote2ARM_Audio_Sensor>: public Traits<void>
{
    typedef LIST<Microphone> SENSORS;

};

template <> struct Traits<EMote2ARM_Accelerometer>: public Traits<void>
{
    typedef LIST<ADXL345> SENSORS;

};

__END_SYS

#endif

