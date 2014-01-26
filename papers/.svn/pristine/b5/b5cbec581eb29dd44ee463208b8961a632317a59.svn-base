#include <alarm.h>
#include <mach/common/iee_802_15_4.h>
#include <machine.h>
#include <nic.h>
#include <uart.h>

__USING_SYS

struct Msg {
    int id;
    int humidity;
    int temperature;
};

void sensor(unsigned char id) {
    Humidity_Sensor    hum_sensor;
    Temperature_Sensor temp_sensor;

    IEE_802_15_4 nic;
    nic.init(id);

    unsigned char src, prot;
    unsigned int size;

    Msg msg;

    int i;
    while (true) {
        for (i = 5; i < 8; i++) {
            CPU::out8(Machine::IO::PORTB, (1 << i));

            msg.id          = id;
            msg.humidity    = hum_sensor.sample();
            msg.temperature = temp_sensor.sample();

            nic.send(0, 0, &msg, sizeof(msg));

            Alarm::delay(100000);
        }
    }
}

int sink(){
    IEE_802_15_4 nic;
    nic.init(0);

    Msg msg;

    UART uart;
    uart.power(UART::FULL);

    unsigned char src, prot;
    int i;

    while (true) {
        while (!(nic.receive(&src, &prot, &msg, sizeof(msg), 0l)));

        for(i = 0; i < sizeof(msg); i++)
            uart.put(reinterpret_cast<char*>(&msg)[i]);
    }
}

int main() {
    sensor(1);
   // sink();
}

