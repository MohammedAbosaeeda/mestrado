#include <cpu.h>
#include <machine.h>
#include "flash_util.h"

__USING_SYS

int main() {
    unsigned char r = 0;
    EEPROM eeprom;

    r = eeprom.read(0x7f);

    for (unsigned int i = 0; i < 10; i++) {
        eeprom.write(i, 0x10);
        r = eeprom.read(i);
    }

    return 0;
}

