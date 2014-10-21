/*! @file
 *  @brief EPOS Default application for ML310 Machine
 *
 *  CVS Log for this file:
 *  \verbinclude app/ml310_app_cc.log
 */
#include <utility/ostream.h>
#include <display.h>
#include <machine.h>

__USING_SYS

OStream cout;

int main()
{
  cout << "EPOS LOADED\n";

  cout << "LEDs are flashing now!!!\n";
  unsigned int * led = (unsigned int *)(Traits<Machine>::LEDS_BASEADDR);
  for (unsigned int i = 0; i < 0x0100 ; i++){ 
    *led = ~i;
    for(unsigned int x = 0; x < 0x007FFFFF; x++);
  }

  return 0;
}
