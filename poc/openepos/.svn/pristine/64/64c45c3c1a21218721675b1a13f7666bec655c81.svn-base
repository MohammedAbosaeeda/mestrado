#include <inttypes.h>
#include <avr/io.h>

#ifndef __EVENT_H__
#define __EVENT_H__

#define E_OK 0

typedef unsigned char EventMaskType;
typedef unsigned char TaskRefType;
typedef unsigned char StatusType;

__inline__ StatusType SetEvent(TaskRefType task_name, EventMaskType mask);

__inline__ StatusType ClearEvent(TaskRefType task_name, EventMaskType mask);


#endif
