#include <uart.h>

__USING_SYS

int main()
{
    UART* serial;

    /* Default parameters for AVR8.
       Does not work in QEMU IA32.
       In order to work in QEMU IA32 the UART unit must be 0.
    */
    serial = new UART(9600, 8, 0, 1, 1);

    for (int i = 0; i < 10000; i++) {
        serial->put('V');
        for (int s = 0; s < 0xFFFF; s++); // just to waste time
    }


    delete serial;
    return 0;
}
