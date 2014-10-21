/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

#include "keso_types.h"

#ifdef __BIG_ENDIAN
__huge_val_t __huge_val = { { 0x7f, 0xf0, 0, 0, 0, 0, 0, 0 } };
#else 
__huge_val_t __huge_val = { { 0, 0, 0, 0, 0, 0, 0xf0, 0x7f } };
#endif

jint keso_dcmpg(jdouble a, jdouble b) {
	if (a==b) return 0;
	if (a>b) return 1;
	if (b<a) return -1;
	return 1;
}

jint keso_dcmpl(jdouble a, jdouble b) {
	if (a==b) return 0;
	if (a>b) return 1;
	if (b<a) return -1;
	return -1;
}
