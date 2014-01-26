#include <display.h>
#include <machine.h>

__USING_SYS

int main() {
    Display d;
    EEPROM eeprom;

    d.puts("Warning: this is a test!\n");
    d.puts("Reading and writing the EEPROM\n");

    unsigned char r = 0;

    for (unsigned int i = 0; i < 10; i++) {
        d.puts("reading\n");
        r = eeprom.read(0x0);
        d.puts("writing\n");
        eeprom.write(i, 0x0);
    }
}

