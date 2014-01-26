/* EPOS Sensing Application */

#include <sentient.h>
#include <uart.h>

Temperature_Sentient t;
UART u;

int main() 
{
  while(1)
    u.send_byte(t.read());
}
