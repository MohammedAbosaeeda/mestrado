/*KESO--HEADER--KESO*/

object_t* keso_mt_escape(class_id_t id, void* base);

/*KESO--CFILE--KESO*/

object_t* keso_mt_escape(class_id_t id, void* base) {
	/* object_t* obj = keso_allocObject(id); */
	object_t* obj = KESO_ALLOC(id,CLASS(id).size,CLS_ROFF(id)); 
	KESO_OBJ_TO_MT(obj)->base = base;
	return obj; 
}

