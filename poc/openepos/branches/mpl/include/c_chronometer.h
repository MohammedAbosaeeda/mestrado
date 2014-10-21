/*
 * c_chronometer.h
 *
 *  Created on: Mar 18, 2012
 *      Author: mateus
 */

#ifndef C_CHRONOMETER_H_
#define C_CHRONOMETER_H_

/* C/C++ Wrapper for Chronometer */

void* newChronometer();

void chronometer_start(void* chrono);
void chronometer_stop(void* chrono);
unsigned long chronometer_read(void* chrono);

#endif /* C_CHRONOMETER_H_ */
