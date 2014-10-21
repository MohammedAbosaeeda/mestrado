/*! @file
 *  @brief EPOS PC Display Mediator Test Program
 *
 *  CVS Log for this file:
 *  \verbinclude src/mach/pc/display_test_cc.log
 */
#include <utility/ostream.h>
#include <display.h>

__USING_SYS

int main()
{
    OStream cout;

    cout << "PC_Display test\n";

    PC_Display display;

    return 0;
}
