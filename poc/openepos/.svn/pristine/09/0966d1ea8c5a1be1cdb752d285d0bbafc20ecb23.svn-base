

#include "machine.h"
__USING_SYS


void led3_on() {  CPU::out8(Machine::IO::PORTB, (1 << 5));
              //    chron->start();
               }//   chron->lap();}
unsigned int led3_off() {
    CPU::out8(Machine::IO::PORTB, (Machine::IO::PORTB,
                                   Machine::IO::PORTB & ~(1 << 5)));


   return 1;//chron->read();
}

void led1_on() {  CPU::out8(Machine::IO::PORTB, (1 << 7));
              //    chron->start();
               }//   chron->lap();}


void led1_off() {
    CPU::out8(Machine::IO::PORTB, (Machine::IO::PORTB,
                                   Machine::IO::PORTB & ~(1 << 7)));



}

int main() {



    int a[50];
        for (int i = 0 ; i < 50 ;i++){
            a[i] = i;

        }

        // while(true) {

        led1_on();
        //	AVR.portB.clrBit(6);
        int aux;
        for (int i = 0 ; i < 50 ;i++){
            for (int j = 0 ; j < 50; j++){
                if (a[j] < a[j+1]) {
                    aux = a[j];
                    a[j] = a[j+1];
                    a[j+1] = aux;
                }
            }
        }

        led1_off();

        for (int i = 0 ; i < 50 ;i++){
            for (int j = 0 ; j < 50; j++){
                if (a[j] > a[j+1]) {
                    aux = a[j];
                    a[j] = a[j+1];
                    a[j+1] = aux;
                }
            }
        }

      //  led1_on();
}
