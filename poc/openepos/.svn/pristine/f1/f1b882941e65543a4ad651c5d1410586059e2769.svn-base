/*KESO--HEADER--KESO*/

object_t* keso_dup_object(object_t* obj);

/*KESO--CFILE--KESO*/

__inline__ static void keso_memcpy(jbyte *des, jbyte *src, unsigned int size) {
	for (;size;size--) { *des++ = *src++; }
}

/*
 * keso_dup_object creates a deep copy of an object.
 * The function works iterative with a work list which is allocated
 * on the stack and twice KESO_DUP_OBJ_MAX large. It is therefore
 * recommended that KESO_DUP_OBJ_MAX is as small as possible.
 */
object_t* keso_dup_object(object_t* obj) {
	class_id_t cls;
	object_t* new_obj=(object_t*)0;
	unsigned int i, j, top, end;

	object_t* todo_list_from[KESO_DUP_OBJ_MAX]; 
	object_t** todo_list_to[KESO_DUP_OBJ_MAX];

	top = 0;
	end = 0;

	todo_list_from[end] = obj;
	todo_list_to[end] = &new_obj;
	end++;

	while (top<end) {
		object_t* obj = todo_list_from[top];
		if (obj==NULL) {
			top++;
			continue;
		}
		cls = obj->class_id;
		if (keso_isArrayClass(cls)) {
			/* TODO */
			*todo_list_to[top] = (object_t*)0;
			top++;
		} else if (keso_isMemoryClass(cls)) {
#ifdef KESO_HAS_DYN_SHARED_MEMORY 
			char* mem = KESO_MEMORY_ADDR(obj);

			if (keso_is_shared_memory(mem)) {
				/* create new proxy object for memory region */
				object_t *mem_obj = keso_allocObject(KESO_MEMORY_ID);

				/* increase reference counter */
				keso_require_shared_memory(mem);
				KESO_MEMORY_ADDR(mem_obj) = mem;
				KESO_MEMORY_SIZE(mem_obj) = KESO_MEMORY_SIZE(obj);

				*todo_list_to[top] = mem_obj;
			} else {
				/* immortal proxy object is shared
				 * without reference counter */
				*todo_list_to[top] = obj;
			}
			top++;
#else
			/* immortal proxy object is shared
			 * without reference counter */
			*todo_list_to[top] = obj;
			top++;
#endif
		} else {
			/* copy object */
			unsigned int roff = CLS_ROFF(cls);
			unsigned int size = CLASS(cls).size;
			object_t*  nobj;
			object_t** refs;

			nobj = *todo_list_to[top] = keso_allocObject(cls);
			top++;

			keso_memcpy(KESO_TOP_PTR(roff,nobj),KESO_TOP_PTR(roff,obj),size);

			refs=KESO_REF_PTR(roff,nobj);
			for (i=0;i<roff;i++) {

				if (refs[i]==NULL) continue;

				for (j=0;j<top && todo_list_from[j]!=refs[i] ;j++) ;

				if (j<top) {
					/* object was allready copied */
					refs[i]=*todo_list_to[j];
				} else {
					/* insert reference into the todo list */
					todo_list_from[end] = refs[i];
					todo_list_to[end] = &refs[i];
					end++;

					if (end==KESO_DUP_OBJ_MAX) {
						/* TODO: overrun */
						/* FIXME: overrun */
						return (object_t*)0;
					}
				}
			}
		}
	}

	return new_obj;
}

