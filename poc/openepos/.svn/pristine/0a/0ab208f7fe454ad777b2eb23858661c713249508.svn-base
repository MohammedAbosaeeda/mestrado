/*KESO--HEADER--KESO*/

#ifdef KESO_OMIT_SAFECHECKS
#  define KESO_ASSERT_NULLPOINTER(_obj,_method_,_bcPos_)
#  define KESO_CHECK_NULLPOINTER(_obj_,_method_,_bcPos_)
#else
#  define KESO_ASSERT_NULLPOINTER(_obj,_method_,_bcPos_)
#  if defined(KESO_PRODUCTION) || defined(NO_WRITE)
#    define KESO_CHECK_NULLPOINTER(_obj_,_method_,_bcPos_) if (unlikely(((object_t*)0)==(_obj_))) keso_throw_error();
#  else
#    define KESO_CHECK_NULLPOINTER(_obj_,_method_,_bcPos_) if (unlikely(((object_t*)0)==(_obj_))) keso_throw_nullpointer(_method_,_bcPos_);
void 	keso_throw_nullpointer(const char* method, int bcpos) NORETURN ;
#  endif
#endif

/*KESO--CFILE--KESO*/
#include "keso_support.h"

#ifndef KESO_OMIT_SAFECHECKS
#ifndef NO_WRITE
#ifndef KESO_PRODUCTION
__const__ static char msg_null[] = "null pointer exception\n";

void 	keso_throw_nullpointer(const char* method, int bcpos) {
	write(1,msg_null,sizeof(msg_null));
	exit(-1);
}
#endif
#endif
#endif
