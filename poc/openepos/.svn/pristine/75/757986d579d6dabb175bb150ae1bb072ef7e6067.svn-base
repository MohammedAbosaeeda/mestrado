/*KESO--HEADER--KESO*/

jint keso_castD2I(jdouble value) __attribute__ ((const)) ;
jlong keso_castD2L(jdouble value) __attribute__ ((const)) ;

jint keso_dcmpg(jdouble a, jdouble b) __attribute__ ((const)) ;
jint keso_dcmpl(jdouble a, jdouble b) __attribute__ ((const)) ;

/*KESO--CFILE--KESO*/

#ifdef __BIG_ENDIAN
__huge_val_t __huge_val = { { 0x7f, 0xf0, 0, 0, 0, 0, 0, 0 } };
#else 
__huge_val_t __huge_val = { { 0, 0, 0, 0, 0, 0, 0xf0, 0x7f } };
#endif

jint keso_castD2I(jdouble value) {
	return (jint)value;
}

jlong keso_castD2L(jdouble value) {
	return (jlong)value;
}

jint keso_dcmpg(jdouble a, jdouble b) {
	if (a>b) return 1;
	if (a==b) return 0;
	if (a<b) return -1;
	return 1;
}

jint keso_dcmpl(jdouble a, jdouble b) {
	if (a>b) return 1;
	if (a==b) return 0;
	if (a<b) return -1;
	return -1;
}
