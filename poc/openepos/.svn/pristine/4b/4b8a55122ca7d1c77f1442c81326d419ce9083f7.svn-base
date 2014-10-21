// EPOS-- Battery Lifetime Estimate Abstraction Declarations

#ifndef __battery_lifetime_h
#define __battery_lifetime_h

#include <battery.h>
#include <alarm.h>
#include <tsc.h>


__BEGIN_SYS


class Battery_Lifetime 
{

public:
    typedef unsigned int Battery_Charge;
    typedef unsigned long Second;

public:

    Battery_Lifetime(Second seconds) 
        : _threshold(Traits<Battery_Lifetime>::threshold), 
	_threshold_2(Traits<Battery_Lifetime>::threshold / 4),  //25 %
        _last_energy(true), 
        _duration(seconds) {} 

    void init() 
    {
	_battery_start = (Battery_Charge) remaining_charge();	
	Alarm::reset_n_quantums();	
    }

    void system_lifetime(Second seconds)
    {
	_duration = seconds;
    }
	
    Second system_lifetime()
    {
	return _duration;
    }

    bool has_energy()
    {

	db<Battery_Lifetime>(TRC) << "Battery_Lifetime::has_energy()\n";

	if(!Traits<Battery_Lifetime>::enabled)
	    return true;

	if(!_duration)
	    return true;

	_threshold--;

	if(!_threshold){
	    bool energy = estimate();

	    if(_last_energy == energy){
		if(energy) {
		    _threshold = Traits<Battery_Lifetime>::threshold + _threshold_2;
		} else {
		    _threshold = Traits<Battery_Lifetime>::threshold - _threshold_2;
		}
	    } else {
	        _threshold = Traits<Battery_Lifetime>::threshold;
	    }

	    _last_energy = energy;
	}
	return _last_energy;
    }

private:

    unsigned int remaining_charge();

    /*
    Microsecond read() { return ticks() * 1000000 / frequency(); }
    Time convert(Second & seconds) { return (Time)seconds * frequency(); } // / 1000000;} 
    */

    // Em segundos
    Second time_interval(){ return (((Second)(Alarm::reset_n_quantums())) * Traits<Thread>::M_QUANTUM) / 1000; }

    bool estimate()
    {
	Second time_end = time_interval();
		
	if(time_end >= _duration){
	    _duration = 0;
	    return true;
	}
		
	_duration -= time_end;
		
	Battery_Charge battery_end = (Battery_Charge) remaining_charge();		

	if(battery_end <= BOUNDARY){
	    return false;
	}

	Second estimate_time;

	if(battery_end < _battery_start){
	    if((_battery_start - battery_end) <= 0 || time_end <= 0){
		return true;
	    }
	    estimate_time = ((Second)((battery_end - BOUNDARY) / (_battery_start - battery_end))) * (time_end);
	} else {
	    _battery_start = battery_end;
	    return true;
	}
		
	_battery_start = battery_end;

	if(estimate_time < _duration)
	    return false;

	return true;
    }

private:
    Battery_Charge _battery_start;
    static const unsigned int BOUNDARY = Traits<Battery_Lifetime>::boundary;
    Second _duration;	
    unsigned int _threshold;
    unsigned int _threshold_2;
    bool _last_energy;
};


__END_SYS

#endif
