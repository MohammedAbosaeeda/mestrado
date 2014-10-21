// This is a temporary file to develop the Cortex_M3 Port

#include <mach/common/cortex_m3.h>

__BEGIN_SYS

void _int_enable() {
    ASM("mov r0, #1 \n"
        "msr primask, r0 \n"
        "msr faultmask, r0");
//    ASM("cpsie f");
    Cortex_M3::scs(Cortex_M3::STCTRL) |= Cortex_M3::INTEN;
}

void _int_disable() {
    Cortex_M3::scs(Cortex_M3::STCTRL) &= ~Cortex_M3::INTEN;
//    ASM("cpsid f");
    ASM("mov r0, #0 \n"
        "msr primask, r0 \n"
        "msr faultmask, r0");
}

__END_SYS
