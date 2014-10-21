/*
 * glboal.h
 *
 *  Created on: Jul 6, 2012
 *      Author: tiago
 */

#include "global.h"

namespace Global {

bool _can_finish = true;
sc_event _can_finish_event;

sc_time  *_clk_period[CLK_DOMAINS] = {0};
sc_clock *_clk[CLK_DOMAINS] = {0};

unsigned int _global_timer = 0;

long int get_real_time_ms(){
    struct timeval now;
    gettimeofday(&now, NULL);
    long int ms_now = now.tv_sec * 1000 + now.tv_usec / 1000;
    return ms_now;
}

};
