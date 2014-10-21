// EPOS LEON2_UART Test Program
//
// Author: fauze
// Documentation: $EPOS/doc/uart			Date: 12 May 2005

#include <utility/ostream.h>
#include <uart.h>
#include <framework.h>

__USING_SYS

int main()
{
    OStream cout;

    cout << "LEON2_UART test\n";

    LEON2_UART uart;

    return 0;
}
