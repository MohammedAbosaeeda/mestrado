#include <adc.h>
#include <alarm.h>
#include <display.h>
#include <machine.h>

__USING_SYS

Display display;

// prints an integer
void puti(int v) {
    unsigned long major = 1000;
    unsigned char digit;

    while (major >= 10) {
        digit = '0';
        while (v >= major) {
            digit++;
            v -= major;
        }
        display.putc(digit);
        major /= 10;
    }
    display.putc('0' + v);
}

int main() {
    // creates an instance of the ADC1 from ATMega1281
    ATMega1281_ADC adc1(adc1.SINGLE_ENDED_ADC1, 8000000 >> 7); 

    // turn on the red LED 
    CPU::out8(Machine::IO::PORTB, 0x20);

    // infinite loop getting and printing the ADC samples
    display.puts("Testing ADC1:\n");
    while (1) {
        Alarm::delay(100000);
        display.puts("ADC sample = ");
        puti(adc1.sample());
        display.puts("\n");
        Alarm::delay(100000);
    }

    return 0;
}
