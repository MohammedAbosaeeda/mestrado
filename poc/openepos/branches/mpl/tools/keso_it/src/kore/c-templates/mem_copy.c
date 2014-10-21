/*KESO--HEADER--KESO*/

void keso_memcpy(jbyte *des, jbyte *src, unsigned int size);

/*KESO--CFILE--KESO*/

void keso_memcpy(jbyte *des, jbyte *src, unsigned int size) {
	for (;size;size--) {
       *des++ = *src++;
    }
}

