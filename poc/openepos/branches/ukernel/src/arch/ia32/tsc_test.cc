// EPOS IA32_TSC Test Program

#include <utility/ostream.h>
#include <tsc.h>

using namespace EPOS;

int main()
{
    OStream cout;

    cout << "IA32_TSC test\n";

    IA32_TSC tsc;

    return 0;
}
