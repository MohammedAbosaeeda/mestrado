#include <display.h>
#include <machine.h>
#include <uart.h>

__USING_SYS

int main() {
    Display d;
    UART u;

    CPU::out8(Machine::IO::DDRA, 0x7);
    CPU::out8(Machine::IO::PORTA, ~1);

    d.puts("Listening the serial port\n");

    unsigned char c = 0;
    while (1) {
        c = u.get();

        if (c == 's') {
            d.puts("teste\n");
        }
    }
}

