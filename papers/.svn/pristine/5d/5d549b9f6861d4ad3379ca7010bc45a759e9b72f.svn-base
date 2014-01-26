// EPOS-- Chronometer Abstraction Test Program

#include <utility/ostream.h>
#include <alarm.h>
#include <chronometer.h>

__USING_SYS;

int main()
{
    Chronometer chrono;
    OStream cout;

    cout << "Chronometer test\n";

    cout << "Starting chronometer\n";

    chrono.start();

    Alarm::delay(1000000);

    chrono.stop();

    cout << "Chronometer stoped\n";

    cout << "Measured time: " << chrono.read() / 1000 << "ms\n";

    cout << "The end!\n";

    return 0;
}
