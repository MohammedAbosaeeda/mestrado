// EPOS-- Alarm Abstraction Test Program

#include <utility/ostream.h>
#include <nic.h>

__USING_SYS


OStream cout;

int main()
{
    cout << "Delay\n";

    unsigned long current = CMAC::alarm_time();

    for (int i = 0; i < 1; ++i) {
    	for (unsigned long var = 0; var < 0x0000FFFF; ++var);
	}

    current = CMAC::alarm_time() - current;

    cout << "Time: " << static_cast<int>(current) << "\n";

    cout << "Wait 10s\n";

    current = CMAC::alarm_time() + 10000;
    while(CMAC::alarm_time() < current);

    cout << "Done\n";

    return 0;
}

