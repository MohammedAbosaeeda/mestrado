/*KESO--HEADER--KESO*/

void keso_memrevcpy(jbyte *des, jbyte *src, unsigned int size); 

/*KESO--CFILE--KESO*/

void keso_memrevcpy(jbyte *des, jbyte *src, unsigned int size) {
	des += size - 1;
    for (;size;size--) {
       *des-- = *src++;
    }
}


