/* Mantis Sensing Application */
#include <inttypes.h>
#include "led.h"
#include "dev.h"
#include "com.h"
static comBuf send_pkt; 
void start (void) {
   send_pkt.size=1; 
   while(1) {
     dev_read(DEV_MICA2_TEMP, &send_pkt.data[0],1);
     com_send(IFACE_SERIAL, &send_pkt);
   }
}
