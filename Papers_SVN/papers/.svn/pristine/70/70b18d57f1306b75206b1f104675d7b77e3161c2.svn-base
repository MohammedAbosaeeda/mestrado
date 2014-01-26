/*
 * buttontest.cc
 *
 *  Created on: 26/01/2009
 *      Author: tiago
 */

#include <machine.h>
#include <cpu.h>
#include <traits.h>
#include <alarm.h>
#include <semaphore.h>
#include <display.h>

//using namespace System;
__USING_SYS

typedef IO_Map<Machine> IO;

enum {
	LED1			= 0x80,
	LED2			= 0x40,
	LED3 			= 0x20,
	LEDALL			= 0xE0,
};

int aux = 0;
void handler(unsigned int){

	//CPU::int_disable();

	if(aux == 0){
		++aux;
		CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL);
		CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3);
	}
	else if(aux == 1){
		++aux;
		CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL);
		CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED2);
	}
	else if(aux == 2){
		aux = 0;
		CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL);
		CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED1);
	}

	//CPU::int_enable();

}

typedef enum{
	EXT_IRQ_0  = 0,
	EXT_IRQ_1,
	EXT_IRQ_2,
	EXT_IRQ_3,
	EXT_IRQ_4,
	EXT_IRQ_5,	// radio
	EXT_IRQ_6,	// button 1
	EXT_IRQ_7,  // button 2
} irqNumber_t;

// interrupt activation condition.
typedef enum
{
	IRQ_LOW_LEVEL,     // The low level generates an interrupt request.
	IRQ_ANY_EDGE,      // Any edge generates an interrupt request.
	IRQ_FALLING_EDGE,  // Falling edge generates an interrupt request.
	IRQ_RISING_EDGE    // Rising edge generates an interrupt request.
} irqMode_t;

void enable_external_int(irqNumber_t irqNumber, irqMode_t irqMode){
	// IRQ pin is input
	CPU::out8(IO::DDRE, CPU::in8(IO::DDRE) & ~(1 << irqNumber));
	CPU::out8(IO::PORTE, CPU::in8(IO::PORTE) | (1 << irqNumber));
	unsigned char ui8ShiftCount = (irqNumber - IRQ_4) << 1;
	// Clear previous settings of corresponding interrupt sense control
	CPU::out8(IO::EICRB, CPU::in8(IO::EICRB) & ~(3 << ui8ShiftCount));
	// Setup corresponding interrupt sence control
	CPU::out8(IO::EICRB, CPU::in8(IO::EICRB) | ((irqMode & 0x03) << ui8ShiftCount));
	// Clear the INTn interrupt flag
	CPU::out8(IO::EIFR, CPU::in8(IO::EIFR) & ~(1 << irqNumber));
	// Enable external interrupt request
	CPU::out8(IO::EIMSK, CPU::in8(IO::EIMSK) | (1 << irqNumber));
}

int main(){

	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL);

	Alarm::delay(250000);

	Machine::int_vector(IC::IRQ_IRQ6, &handler);
	Machine::int_vector(IC::IRQ_IRQ7, &handler);

	enable_external_int(IRQ_6, IRQ_RISING_EDGE);
	enable_external_int(IRQ_7, IRQ_RISING_EDGE);


	while(true);

	/*IC::enable(IC::IRQ_IRQ6);
	IC::enable(IC::IRQ_IRQ7);

	Display out;

	while(true){
		if(CPU::in8(IO::PORTE) & (1 << 6))
			out.puts("IRQ6 ON\n");
		else
			out.puts("IRQ6 OFF\n");
		if(CPU::in8(IO::PORTE) & (1 << 7))
			out.puts("IRQ7 ON\n");
		else
			out.puts("IRQ7 OFF\n");
		out.puts("\n");

		Alarm::delay(250000);
	}*/

	return 0;
}
