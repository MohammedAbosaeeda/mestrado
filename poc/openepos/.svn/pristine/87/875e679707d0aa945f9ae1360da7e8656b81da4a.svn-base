/*KESO--HEADER--KESO*/

void dump_bitmap();
void dump_obj(object_t* addr); 

/*KESO--CFILE--KESO*/
#include <stdio.h>

void dump_bitmap() {
	int i;
	for (i=0;i<(COFFEE_HEAPDESC.numslots>>5);i++) {
		fprintf(stderr," %X",bitmap[i]);
	}
	fprintf(stderr,"\n");
}

void dump_obj(object_t* addr) {
	class_id_t clsid;
	unsigned int bit,color,i,slot;

	if (addr==NULL) { 
		fprintf(stderr,"addr : (null)\n");
		return ;
	}

	fprintf(stderr,"addr : 0x%x\n",addr);
	clsid = addr->class_id;
	if (clsid<1 || (KESO_CLASSSTORE_SIZE+1)<clsid) {
		fprintf(stderr,"class: %d (invalid)\n",clsid);
	} else {
		fprintf(stderr,"class: %d",clsid);
		if (keso_isArrayClass(clsid)) {
			if (keso_isObjectArrayClass(clsid)) {
				fprintf(stderr," (obj array)\n");
			} else {
				fprintf(stderr," (array)\n");
			}
			fprintf(stderr,"size : %d byte",sizeof(array_t)+CLASS(clsid).size*((array_t*)addr)->size);
		} else {
			if (keso_isMemoryClass(clsid)) {
				fprintf(stderr," (memory proxy)\n");
			} else {
				fprintf(stderr," (obj)\n");
			}
			fprintf(stderr,"size : %d byte",CLASS(clsid).size);
		}
		fprintf(stderr," %d slots\n",objSize(addr));
	}

	slot = COFFEE_ADDR2SLOT(addr);
	if (slot<0 || slot>=COFFEE_HEAPDESC.numslots) {
		fprintf(stderr,"bmap : (not on heap)\n");
	} else {
		fprintf(stderr,"bmap : %d ",slot);
		for (i=0;i<objSize(addr);i++) {
			int bm_int = slot+i >> 5;
			int bm_bit = 1 << ((slot+i) & 0x1f);
			fprintf(stderr,"%d",bitmap[bm_int]&bm_bit);
		}
		fprintf(stderr,"\n");
	}
	color = OBJCOLORED(addr);
	fprintf(stderr,"cbit : %s\n",(color?"grey/black":"white"));
}

