#include "global.h"
#include "irr.h"
#define KESO_EOFML ((irr_listel_t*) -1)
void printPointer(void *p) {
	char buf[8];
	unsigned int pos=8;
	unsigned int pi = (unsigned int) p;

	do {
		unsigned char digit = pi%16;
		pi/=16;

		if(digit>9) digit = digit-10+'a';
		else digit += '0';

		buf[--pos] = digit;
	} while(pos>0);
	write(1, buf, 8);
}

void printInt(unsigned int i) {
	char buf[10];
	unsigned short pos=10;
	
	do {
		buf[--pos] = '0'+(i%10);
		i/=10;
	} while(i>0);
	write(1, buf+pos, 10-pos);
}

void printRange(void *element, void *heap_begin, unsigned short size) {
	unsigned short bmint, bmbit;
	bmint = (unsigned short)(( (unsigned int) element - (unsigned int) heap_begin ) / 8);
	bmbit = (unsigned short)(( (unsigned int) element - (unsigned int) heap_begin ) % 8);
	printInt(bmint);
	write(1, "|", 1);
	printInt(bmbit);
	write(1, "-", 1);

	bmint =(unsigned short)(( (unsigned int) element - (unsigned int) heap_begin + (unsigned int) size - 1) / 8);
	bmbit =(unsigned short)(( (unsigned int) element - (unsigned int) heap_begin + (unsigned int) size - 1) % 8);
	printInt(bmint);
	write(1, "|", 1);
	printInt(bmbit);
}

void chkRef(object_t *ref) {
	irr_listel_t *element = (irr_listel_t*)domain_desc[1].heap.irr.freemem;
	irr_listel_t *heap_begin, *heap_end;
	heap_begin = domain_desc[1].heap.irr.heap_top;
	heap_end = (irr_listel_t*)((char*) heap_begin + domain_desc[1].heap.irr.heap_size);
	if(ref == NULL) return;
	if((unsigned int)ref<(unsigned int)heap_begin || (unsigned int)ref>=(unsigned int)heap_end) return;

	while(element!=KESO_EOFML) {
		heap_end = (irr_listel_t*)((char*) element + (element->size * 8));
		if((unsigned int)ref>=(unsigned int)element && (unsigned int)ref<(unsigned int)heap_end) {
			write(1, "IA: 0x", 6);
			printPointer(ref);
			write(1, "\n", 1);
			return;
		}
		element = (irr_listel_t*)element->next;
	}
}
