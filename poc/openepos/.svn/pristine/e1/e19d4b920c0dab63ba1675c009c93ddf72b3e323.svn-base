/*KESO--HEADER--KESO*/
#ifdef KESO_OMIT_SAFECHECKS
#  define KESO_CHECK_ARRAY(_obj_,_index_,_method_,_bcPos_)  
#  define KESO_CHECK_ARR_REF(_obj_,_index_,_method_,_bcPos_)
#  define KESO_CHK_BOUNDS(_clazz_, _mem_, _a_size_, _method_, _bcPos_)
#  define KESO_CHK_CBOUNDS(_m_size_, _a_size_, _method_, _bcPos_)
#else 
#  ifdef INLINE_ARRAY_CHK
#    define KESO_CHECK_ARRAY(_obj_,_index_,_method_,_bcPos_)\
	if (unlikely(((array_t*)_obj_)->size<=(array_size_t)(_index_))) keso_throw_index_out_of_bounds(_method_,_bcPos_)
#    define KESO_CHECK_ARR_REF(_obj_,_index_,_method_,_bcPos_) keso_check_array((object_t*)_obj_,_index_,_method_,_bcPos_)
#  else
#    define KESO_CHECK_ARRAY(_obj_,_index_,_method_,_bcPos_) keso_check_array((object_t*)_obj_,_index_,_method_,_bcPos_) 
#    define KESO_CHECK_ARR_REF(_obj_,_index_,_method_,_bcPos_) keso_check_array((object_t*)_obj_,_index_,_method_,_bcPos_)
#  endif
#  define KESO_CHK_BOUNDS(_clazz_, _mem_, _a_size_, _method_, _bcPos_)\
	if (unlikely((array_size_t)(_a_size_)>=((_clazz_*)_mem_)->_size)) keso_throw_index_out_of_bounds(_method_,_bcPos_) 
#  define KESO_CHK_CBOUNDS(_m_size_, _a_size_, _method_, _bcPos_)\
	if (unlikely((array_size_t)(_a_size_)>=(array_size_t)(_m_size_))) keso_throw_index_out_of_bounds(_method_,_bcPos_) 
#endif

#ifndef NO_WRITE
void 	keso_throw_index_out_of_bounds(const char* method, int bcpos) NORETURN ; 
#else 
#define keso_throw_index_out_of_bounds(_m_,_pos_) keso_throw_error()
#endif


/*KESO--CFILE--KESO*/
#include "keso_support.h"

#ifndef NO_WRITE
__const__ static char msg_ioob[] = "index out of bounds exception\n";
void 	keso_throw_index_out_of_bounds(const char* method, int bcpos) {
	write(1,msg_ioob,sizeof(msg_ioob));
	exit(-1);
}
#endif

