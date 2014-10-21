/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

#ifndef __KESO_ARGS_H__
#define __KESO_ARGS_H__ 1

#define __va_size(type) ((sizeof(type)+3) & ~0x3)

typedef char *va_list;

#define va_start(pvar, lastarg)                 ((pvar) = (char*)(void*)&(lastarg) + __va_size(lastarg))
#define va_end(pvar)
#define va_arg(pvar,type)                       \
        ((pvar) += __va_size(type),             \
         *((type *)((pvar) - __va_size(type))))

#endif
