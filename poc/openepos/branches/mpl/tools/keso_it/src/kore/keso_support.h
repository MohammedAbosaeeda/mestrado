/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

#ifndef __KESO_SUPPORT_H_
#define __KESO_SUPPORT_H_

#include "keso_types.h"

#ifndef NO_WRITE
int write(int fd, const char *buf, int count);
#endif

jint keso_dcmpg(jdouble a, jdouble b) __attribute__ ((const)) ;
jint keso_dcmpl(jdouble a, jdouble b) __attribute__ ((const)) ;

jint keso_fcmpg(jfloat a, jfloat b) __attribute__ ((const)) ;
jint keso_fcmpl(jfloat a, jfloat b) __attribute__ ((const)) ;

jdouble keso_fmod(jdouble a, jdouble b) __attribute__ ((const)) ;
jfloat keso_fmodf(jfloat a, jfloat b) __attribute__ ((const)) ; 

#endif
