#include <system.h>
#include <sentient.h>
#include <uart.h>
#include <timepiece.h>

using namespace System;

System sys;
Thermometer therm;
UART uart;

void alarm_handler() {
    uart.put(therm.get());
}

int main() {
    Handler_Function handler(&alarm_handler);
    Alarm alarm(1000000, &handler);

    while(1) {
	sys.power(STANDBY);
    }
}
