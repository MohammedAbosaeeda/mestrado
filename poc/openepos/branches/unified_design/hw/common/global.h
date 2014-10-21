/*
 * glboal.h
 *
 *  Created on: Jul 6, 2012
 *      Author: tiago
 */

#ifndef GLOBAL_H_
#define GLOBAL_H_

#include <systemc.h>
#include <ostream>
#include <sys/time.h>


namespace Global {

    const unsigned int CLK_DOMAINS = 1;

    const unsigned int CLK_HZ[CLK_DOMAINS] = {50*1000*1000};//, 100};
    //const unsigned int CLK_HZ[CLK_DOMAINS] = {1000000};//, 100};

    const unsigned int CLK_PERIOD_PS[CLK_DOMAINS] =
                                        {1000000000000ll/CLK_HZ[0]};/*,
                                        1000000000000ll/CLK_HZ[1],};*/


    template<class T>
    inline void delay(T *current_object, unsigned int _time, sc_time_unit _unit){
        sc_time time(_time, _unit);
        sc_core::wait(time, current_object->simcontext());
    }

    inline unsigned int get_sim_time_ms(){
        return (unsigned int)(sc_time_stamp().to_seconds()/1000.0);
    }


    template<unsigned int domain>
    inline unsigned int get_sim_time_ticks(){
        return (unsigned int)(sc_time_stamp().to_seconds()/(1.0/CLK_HZ[domain]));
    }

    extern unsigned int _global_timer;

    inline unsigned int get_global_timer_ticks(){
        return _global_timer;
    }

    inline unsigned int update_global_timer_ticks(unsigned int val){
        _global_timer = val;
    }

    template<unsigned int domain>
    inline unsigned int ticks_to_ps(unsigned int ticks){
        return ticks*CLK_PERIOD_PS[domain];
    }


    template<unsigned int domain, class T>
    inline void delay_cycles(T *current_object, unsigned int cycles){
        delay(current_object, CLK_PERIOD_PS[domain]*cycles, SC_PS);
    }


    extern bool _can_finish;
    extern sc_event _can_finish_event;

    template<class T>
    inline void safe_finish(T *current_object, bool val){
        if(!_can_finish && val) _can_finish_event.notify();
        _can_finish = val;
    }

    template<class T>
    inline void safe_finish(T *current_object){
        while(!_can_finish) sc_core::wait(_can_finish_event, current_object->simcontext());
        sc_stop();
    }

    extern sc_time  *_clk_period[CLK_DOMAINS];
    extern sc_clock *_clk[CLK_DOMAINS];

    template<unsigned int clk_domain>
    inline sc_clock& clk_signal(){
        if(_clk[clk_domain] == 0){
            _clk_period[CLK_DOMAINS] = new sc_time(Global::CLK_PERIOD_PS[0], SC_PS);
            _clk[CLK_DOMAINS] = new sc_clock("global_clk", *_clk_period[CLK_DOMAINS]);
        }
        return *_clk[CLK_DOMAINS];
    }


    long int get_real_time_ms();


};


#endif /* TIMING_H_ */
