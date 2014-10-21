extern "C" {
#include <epos_uart_c.h>

#define MKLCODE 0 // For measurements in IA32

/* Insert by mkl tool BEGIN */
#if MKLCODE
extern unsigned long long Pb1;
extern unsigned long long Pb2;
extern unsigned long long SumPb;

#endif
/* Insert by mkl tool END */

}

#define DEVICE_TIME_MEASUREMENT_ON 0 /* MKL: instrumenting for time measurement in AVR8. */

#define TIME_OVERHERAD_MEASUREMENT_ON 0 /* MKL: instrumenting for time measurement in AVR8. */

#include <utility/malloc.h>

#include <uart.h>

#include <tsc.h> /* Insert by mkl tool for measurements in IA32 */


#if DEVICE_TIME_MEASUREMENT_ON
#include <machine.h>
#include <mach/atmega1281/mesh_bean2_led_master.h>
using namespace System;
#endif



EPOS_UART * new_EPOS_UART_vdefault()
{
    EPOS_UART * u = 0;
    
    u = new System::UART();
    
    return u;
}


EPOS_UART * new_EPOS_UART(unsigned int baud, unsigned int data_bits, unsigned int parity, unsigned int stop_bits, unsigned int unit)
{
    EPOS_UART * u = 0;

    u = new System::UART(baud, data_bits, parity, stop_bits, unit);

    return u;
}

char EPOS_UART_get(EPOS_UART * u)
{    
    return static_cast<System::UART *>(u)->get(); 
}

void EPOS_UART_put(EPOS_UART * u, char c)
{
/* Insert by mkl tool BEGIN */
#if MKLCODE
    Pb1 = System::IA32_TSC::time_stamp();
#endif
/* Insert by mkl tool END */


#if DEVICE_TIME_MEASUREMENT_ON /* To measure time in AVR8 */
    MeshBean2_LED_Master::turn_on_rr1_led();
    for (int i = 0; i < 1000; i++) {
#endif

#if TIME_OVERHERAD_MEASUREMENT_ON
    ASMV("nop"::);
#endif

#if !TIME_OVERHERAD_MEASUREMENT_ON
    static_cast<System::UART *>(u)->put(c);
#endif

#if DEVICE_TIME_MEASUREMENT_ON /* To measure time in AVR8 */
    }
    MeshBean2_LED_Master::turn_off_rr1_led();
#endif


/* Insert by mkl tool BEGIN */
#if MKLCODE
    Pb2 = System::IA32_TSC::time_stamp();
    SumPb = SumPb + (Pb2 - Pb1);
#endif
/* Insert by mkl tool END */

}

void delete_EPOS_UART(EPOS_UART * u) 
{
    delete ((System::UART *) u);
}

