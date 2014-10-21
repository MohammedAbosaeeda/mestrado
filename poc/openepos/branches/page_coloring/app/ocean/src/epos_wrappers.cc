#include <utility/random.h>
#include <machine.h>
#include <clock.h>
#include "../epos/epos_wrappers.h"
#include <utility/ostream.h>

System::OStream cout;

void exit(int s)
{
    System::Machine::reboot();
}

