// OpenEPOS EMote2_Startup Test Program

#include <mach/common/emote2_startup.h>
#include <alarm.h>
#include <utility/ostream.h>

#ifdef __UART_H
#ifdef __GPIO_PIN_H
#ifdef __SENSOR_H

__USING_SYS

// This is an interactive test.
// You will need a serial terminal in a PC connected
// to the EPOSMote Startup's USB port.

int main() {
	OStream cout;

	EMote2_Startup startup;
	static const int delay = 1000000;

	cout << "temperature: " << startup.temperature() << "\n";

	cout << "Type some character: ";
	startup.put_char(startup.get_char());
	cout << "\n";

	Alarm::delay(delay);
	cout << "Rgb\n";
	startup.set_rgb(1, 0, 0);
	Alarm::delay(delay);
	cout << "rGb\n";
	startup.set_rgb(0, 1, 0);
	Alarm::delay(delay);
	cout << "rgB\n";
	startup.set_rgb(0, 0, 1);
	Alarm::delay(delay);
	cout << "RGB\n";
	startup.set_rgb(1, 1, 1);
	Alarm::delay(delay);
	cout << "rgb\n";
	startup.clear_rgb();
	Alarm::delay(delay);
	cout << "Green\n";
	startup.set_green();
	Alarm::delay(delay);
	startup.clear_green();
	cout << "Red\n";
	startup.set_red();
	Alarm::delay(delay);
	startup.clear_red();

	cout << "press button 1... ";
	while (!startup.get_button1())
		;
	cout << "ok.\n";
	cout << "press button 2... ";
	while (!startup.get_button2())
		;
	cout << "ok.\n";

	cout << "I'm done.";
	while (true)
		;
}

#endif
#endif
#endif
