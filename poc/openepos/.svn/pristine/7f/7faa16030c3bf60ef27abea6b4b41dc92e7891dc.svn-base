// EPOS-- Battery Monitor Mediator Declarations

#ifndef __atmega128_battery_h
#define __atmega128_battery_h

#include <thread.h>
#include <system/config.h>
#include <utility/malloc.h>

__BEGIN_SYS

class ATMega128_Battery : public Battery_Common
{
protected:
    typedef IO_Map<Machine> IO;
    typedef AVR8::Reg8 Reg8;
    typedef AVR8::Reg16 Reg16;

    static const unsigned long CLOCK = Traits<Machine>::CLOCK;


    /*External voltage reference*/
    static const unsigned long ADC_V_REF = 1252352000; // 1.223 * 1024 * 1.000.000

    enum {
        CHANNEL  = 7,
        PORT     = IO::PORTA,
        DDR      = IO::DDRA,
        BAT_MON  = 0x20,
        SYSTEM_REF = 0,// (para adc interno)
        SINGLE_CONVERSION_MODE = 0// (para adc interno)
    };

public:
    static void init();

    static bool enable_all() 
    { 
        enable_sensor();
        return enable();  
    }

    static void disable_all()
    { 
        disable_sensor();
        disable(); 
    }


    // Consumption Monitoring
	static unsigned long long charge()
	{
		_remaining_charge = Traits<Battery>::FIVE_PERCENT_UA_MS * convert(sample()) / Traits<Battery>::MA_HR;
		return _remaining_charge;
	}

    static bool data_ready() { return finished(); } // (para adc)

    static unsigned int convert(long v)
    {
        return (unsigned int)(ADC_V_REF / (v * 1000));
    }

    static int sample() 
    {
    	//TODO: Samplear mais vezes??
        enable_sensor();
        for(unsigned int i = 0; i < 0xfff; i++);
        while (!enable());
        while (!data_ready()); 
        int v = get();
        disable();
        disable_sensor();
        return v;
    }

    static Reg8 port(){ return AVR8::in8(PORT); }
    static void port(Reg8 value){ AVR8::out8(PORT,value); }   
    static Reg8 ddr(){ return AVR8::in8(DDR); }
    static void ddr(Reg8 value){ AVR8::out8(DDR,value); }

/*
// Consumption Monitoring
  static void remaining_charge_decrement(unsigned long * c){
    _remaining_charge = _remaining_charge - *c;
	
	if((_last_charge - ((unsigned long)(_remaining_charge >> 32))) >= ((unsigned long)(_threshold)))
		do_sample();
  }
*/
 
 
/*
// Consumption Monitoring
  static unsigned long long * remaining_charge(){
   
    return &_remaining_charge;
  }   
*/	

    static void disable_in_use() 
    { 
        disable();
        disable_sensor();
    }
	
    static unsigned int voltage(){
        return (unsigned int) convert(sample());
    }

private:

    static void enable_sensor() {
        ddr(ddr() | BAT_MON);
        port(port() | BAT_MON);
    }
    
    static void disable_sensor() {
        port(port() & ~BAT_MON);
        ddr(ddr() & ~BAT_MON);
    }

// funcoes ADC
 
    typedef unsigned long Hertz;
    
    enum {
        // ADMUX
        REFS1 = 7,
        REFS0 = 6,
        ADLAR = 5,
        MUX4  = 4,
        MUX3  = 3,
        MUX2  = 2,
        MUX1  = 1,
        MUX0  = 0,
        //ADCSRA
        ADEN  = 7,
        ADSC  = 6,
        ADFR  = 5,
        ADIF  = 4,
        ADIE  = 3,
        ADPS2 = 2,
        ADPS1 = 1,
        ADPS0 = 0,
    };

    static void config(unsigned char channel, unsigned char reference,
	unsigned char trigger, Hertz frequency) 
    {
	_admux = (channel << MUX0) | (reference << REFS0);
        unsigned char ps = 7;
        while(((CLOCK >> ps) < frequency) && (ps > 0)) ps--;

        _adcsra = (trigger << ADFR) | (ps << ADPS0);
  }

    static int get() {
        return adchl(); 
    }

    static bool finished() { return (adcsra() & (1 << ADIF)); }

    static bool enable() {
        if(_in_use) return false;
        _in_use = true;
        config();
        adcsra(adcsra() | (1 << ADEN) | (1 << ADSC)); 
        return true;
    }

    static void disable() { 
        _in_use = false;
        adcsra(adcsra() & ~(1 << ADEN) & ~(1 << ADSC)); 
    }
 
    static void reset();

    static void int_enable() { adcsra(adcsra() | (1 << ADIE)); }
    static void int_disable() { adcsra(adcsra() & ~(1 << ADIE)); }
 
private:
    static Reg8 admux(){ return AVR8::in8(IO::ADMUX); }
    static void admux(Reg8 value){ AVR8::out8(IO::ADMUX,value); } 
    static Reg8 adcsra(){ return AVR8::in8(IO::ADCSRA); }
    static void adcsra(Reg8 value){ AVR8::out8(IO::ADCSRA,value); }
    static Reg16 adchl(){ return AVR8::in16(IO::ADCL); }
    static void adchl(Reg16 value){ AVR8::out16(IO::ADCL,value); }

    static void config() 
    {
        admux(_admux);
        adcsra(_adcsra);
    }

private:

    static Reg8 _admux;
    static Reg8 _adcsra;
    static bool _in_use;
    static bool _initialized;
    // Consumption Monitoring
    static unsigned long long _remaining_charge;
};


__END_SYS

#endif

