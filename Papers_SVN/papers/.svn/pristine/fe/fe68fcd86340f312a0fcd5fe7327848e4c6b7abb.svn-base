#include <alarm.h>
#include <machine.h>

__USING_SYS

int main() {
    OStream cout;
    Temperature_Sensor t_sensor;
    Humidity_Sensor h_sensor;

    while (1) {
        cout << "Temperature: " << t_sensor.sample() << "C\n";
        cout << "Humidity_Sensor: " << h_sensor.sample() << "%\n";
        cout << "\n";
        Alarm::delay(1000000);
    }

    return 0;
}

