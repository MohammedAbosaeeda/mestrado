/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

#include "keso_mini_c.h"
#include "keso_types.h"
#include "keso_args.h"

#define MAXBUF (sizeof(long int) * 8)	/* enough for binary */

unsigned long strlen(const char *string)
{
	const char *ret = string;
	while (*string++);
	return string - 1 - ret;
}

