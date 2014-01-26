#include <display.h>
#include <eeprom.h>

__USING_SYS

int main() {
    Display d;
    UART u;
    EEPROM  e;
    unsigned char c;
    unsigned char value = 'a';

    d.puts("EEPROM Test\n");

    d.puts("\n>>>>>>>>>>\n");
    d.puts("Reading position 0x00: ");
    c = e.read(0x00);
    d.puts("\nvalue = ");
    u.put(c);
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\nWritting at position 0x00 the value: ");
    u.put(value);
    e.write(0x00, value);
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\nReading position 0x00: ");
    c = e.read(0x00);
    d.puts("\nvalue = ");
    u.put(c);
    d.puts("\n>>>>>>>>>>\n");

    value = 'b';

    d.puts("\n>>>>>>>>>>\n");
    d.puts("Reading position 0x00: ");
    c = e.read(0x00);
    d.puts("\nvalue = ");
    u.put(c);
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\nWritting at position 0x00 the value: ");
    u.put(value);
    e.write(0x01, value);
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\n>>>>>>>>>>\n");
    d.puts("\nReading position 0x00: ");
    c = e.read(0x00);
    d.puts("\nvalue = ");
    u.put(c);
    d.puts("\n>>>>>>>>>>\n");

    return 0;
}
