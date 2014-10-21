/*KESO--HEADER--KESO*/

#ifdef NO_WRITE
#  define KESO_THROW(_obj_,_method_,_bcPos_) keso_throw_error()
#  define KESO_THROW_ERROR(_msg_) keso_throw_error()
void 	keso_throw_error() NORETURN ;
#else
#  define KESO_THROW(_obj_,_method_,_bcPos_) keso_throw_exception(_obj_,_method_,_bcPos_)
#  define KESO_THROW_ERROR(_msg_) keso_throw_error(_msg_)
void 	keso_throw_exception(object_t* obj, const char* method, int bcpos);
void* 	keso_throw_error(const char *msg);
#endif


#ifdef DEBUG
#  ifdef NO_WRITE
#    define KESO_ASSERT(__v__) if (!(__v__)) keso_throw_error()
#  else 
#    define KESO_ASSERT(__v__) if (!(__v__)) keso_fatal_error(__FILE__,__LINE__)
void keso_fatal_error(const char* msg, int line) NORETURN ;
#  endif
#else
#  define KESO_ASSERT(__v__)
#endif

jint  	keso_throw_method_not_implemented(const char* method, int bcpos); 
/*KESO--CFILE--KESO*/

#define stop() while (1) { __asm__ __volatile__ ("nop"); } 

#ifndef NO_WRITE 
__const__ static char msg_impl[] = "method not implemented exception\n";
__const__ static char msg_excp[] = "unknown exception\n";
__const__ static char msg_err[] = "fatal error\n";

#ifdef DEBUG
__const__ static char assert_failed[] = "\nFATAL: assertion failed in ";
void keso_fatal_error(const char* msg, int line) {
	write(1,assert_failed,sizeof(assert_failed));
	write(1,msg,sizeof(msg));
	write(1,":",1);
	stop();
}
#endif

jint 	keso_throw_method_not_implemented(const char* method, int bcpos) {
	write(1,msg_impl,sizeof(msg_impl));
	stop();
}

void 	keso_throw_exception(object_t* obj, const char* method, int bcpos) {
	write(1,msg_excp,sizeof(msg_excp));
	stop();
}

void* 	keso_throw_error(const char *msg) {
	write(1,msg,strlen(msg));
	stop();
}
#else
void 	keso_throw_error() {
	stop();
}
#endif
