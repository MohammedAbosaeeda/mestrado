/*
 * c_chronometer.cc
 *
 *  Created on: Mar 18, 2012
 *      Author: mateus
 */

/* C/C++ Wrapper for Chronometer */
#include "../../include/chronometer.h"

extern "C"
{
    #include "../../include/c_chronometer.h"
}

using namespace System;

void* newChronometer()
{
	return static_cast<void*>(new Chronometer());
}


void chronometer_start(void* chrono)
{
	static_cast<Chronometer*>(chrono)->start();
}


void chronometer_stop(void* chrono)
{
	static_cast<Chronometer*>(chrono)->stop();
}


unsigned long chronometer_read(void* chrono)
{
	return static_cast<Chronometer*>(chrono)->read();
}

