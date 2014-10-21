/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

#include "keso_support.h"
#include "keso_mini_c.h"

#if 0
__const__ static char msg_impl[] = "method not implemented exception\n";
__const__ static char msg_excp[] = "unknown exception\n";
__const__ static char msg_err[] = "fatal error\n";


void 	keso_throw_method_not_implemented(const char* method, int bcpos) {
#ifndef NO_WRITE 
	write(1,msg_impl,sizeof(msg_impl));
	exit(-1);
#endif
}

void 	keso_throw_exception(object_t* obj, const char* method, int bcpos) {
#ifndef NO_WRITE 
	write(1,msg_excp,sizeof(msg_excp));
	exit(-1);
#endif
}

void* 	keso_throw_error(const char *msg) {
#ifndef NO_WRITE 
	write(1,msg,strlen(msg));
	exit(-1);
#endif
}
#endif
