#ifndef __KESO_CLASS_STORE_H_
#define __KESO_CLASS_STORE_H_ 1

#include "keso_types.h"

#ifdef DEBUG
class_t _assert_class(int class_id);
char* _assert_fields(int class_id);
#define OBJCLASS(_obj_) (_assert_class((((object_t*)_obj_)->class_id)-1))
#define CLASS(_class_id_) (_assert_class((_class_id_)-1))
#define SFIELD(_class_id_) (_assert_fields((_class_id_)-1))
#else
#define OBJCLASS(_obj_) (_assert_class((((object_t*)_obj_)->class_id)-1))
#define CLASS(_class_id_) (global_class_store[(_class_id_)-1])
#define SFIELD(_class_id_) (global_class_fields[(_class_id_)-1])
#endif

#endif
