#include <alarm.h>
#include <machine.h>
#include <ic.h>
#include <chronometer.h>

__USING_SYS

typedef IO_Map<Machine> IO;

enum {
	LED1			= 0x80,
	LED2			= 0x40,
	LED3 			= 0x20,
	LEDALL			= 0xE0,
};
void LED_none(){ CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL); }
void LED_all(){ CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LEDALL); }
void LED_green(){
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED1);
}
void LED_yellow(){
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED2);
}
void LED_red(){
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3);
}
void LED_yellow_red(){
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3 | LED2);
}
void LED_yellow_green(){
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED2 | LED1);
}
void LED_green_red(){
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3 | LED1);
}

volatile bool aux = false;

Chronometer cr;

OStream os;

ATMega1281_Timer_3 timer;


int counter = 0;
void handler(unsigned int){
	++counter;
	if(counter == 10){
		cr.stop();
		os << "Measured time: " << (cr.read() / 1000)/counter << "ms\n";
		counter = 0;
		cr.reset();
		timer.reset();
		cr.start();
	}

}

/*void led(unsigned int) {
	CPU::int_disable();
	if(aux)
		LED_all();
	else
		LED_none();

	aux = !aux;

	CPU::int_enable();
}*/

int main() {

	os << "Blink leds\n";

	Machine::int_vector(Machine::irq2int(IC::IRQ_TIMER3_COMPA), handler);

	timer.frequency(1);
	timer.enable();

	cr.start();

	//os << "Tempo: " << cr.read() << "\n";
	while(1);
	return 0;
}
