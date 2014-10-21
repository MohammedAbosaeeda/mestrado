/*KESO--HEADER--KESO*/
jint keso_fcmpl(jfloat a, jfloat b);

/*KESO--CFILE--KESO*/
jint keso_fcmpl(jfloat a, jfloat b) {
	if (a==b) return 0;
	if (a>b) return 1;
	if (b<a) return -1;
	return -1;
}
