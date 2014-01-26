#include <kni.h>
#include <stdio.h>

KNIEXPORT KNI_RETURNTYPE_VOID
Java_simplemath_Adder_sum() {
    jint a = KNI_GetParameterAsInt(1);
    jint b = KNI_GetParameterAsInt(2);
    KNI_ReturnInt(a + b);
}

