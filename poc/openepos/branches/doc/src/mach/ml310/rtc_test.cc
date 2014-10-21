/*! @file
 *  @brief EPOS ML310 RTC Test Program
 *
 *  CVS Log for this file:
 *  \verbinclude src/mach/ml310/rtc_test_cc.log
 */
#include <utility/ostream.h>
#include <rtc.h>

__USING_SYS

int main()
{
    OStream cout;

    cout << "ML310_RTC test\n";

    RTC timer;

    return 0;
}
