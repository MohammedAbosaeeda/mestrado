/*! @file
 *  @brief EPOS ML310 Test Program
 *
 *  CVS Log for this file:
 *  \verbinclude src/mach/ml310/machine_test_cc.log
 */
#include <utility/ostream.h>
#include <machine.h>

__USING_SYS

int main()
{
    OStream cout;

    cout << "ML310 test\n";

    ML310 machine;

    return 0;
}
