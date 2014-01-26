#include <alarm.h>
#include <display.h>
#include <machine.h>

__USING_SYS

int main() {
    Display d;
    int i;

    d.puts("This is a test!\n");

    while (1) {
        for (i = 5; i < 8; i++) {
            CPU::out8(Machine::IO::PORTB, (1 << i));
            d.puts("Test!\n");
            Alarm::delay(500000);
        }
    }

    return 0;
}
