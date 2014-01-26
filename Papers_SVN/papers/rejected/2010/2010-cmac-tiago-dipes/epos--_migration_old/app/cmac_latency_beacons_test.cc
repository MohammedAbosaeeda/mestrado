/*
 * CMAC latency test
 *
 * Measure the round trip time of a packet between two nodes
 *
 *  Created on: 05/01/2009
 *      Author: tiago
 */

#include <machine.h>
#include <cpu.h>
#include <traits.h>
#include <semaphore.h>
#include <display.h>
#include <transceiver.h>
#include <alarm.h>


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


enum{
	DATA_SIZE = 32,
	N_ITERATIONS = 10,
};

unsigned long source_send(){
	OStream out;

	NIC nic;
	NIC::Address address;
	NIC::Protocol protocol;

	char data[DATA_SIZE] = "E ae ?";

	int result;

	while(true){
		result = nic.send(address, protocol, data, DATA_SIZE);
		if(result == CMAC::TX_OK){
			out << "Send OK\n";
			break;
		}
		else{
			out << "Send error: " << result << " - Trying again\n";
		}
	}

}

void source(){

	OStream out;

	out << "Starting source node\n";

	unsigned long acc = 0;

	for(int i = 0; i < N_ITERATIONS; ++i){
		out << "\nSending packet " << i << "\n";
		source_send();
	}
}

void sink(){

	OStream out;

	out << "Starting sink node\n";

	NIC nic;
	NIC::Address address;
	NIC::Protocol protocol;

	int result;

	char data[nic.mtu()];

	while(true){
		result = nic.receive(&address, &protocol, data, nic.mtu());
		if(result == CMAC::RX_OK){
			out << "Receive OK\n";
		}
		else{
			out << "Receive error: " << result << " - Trying again\n";
		}
	}
}


int main(){

	OStream out;
	out << "\nIn the main!\n";

	LED_all();
	for (unsigned int i = 0; i < 0xFFFF; ++i);
	for (unsigned int i = 0; i < 0xFFFF; ++i);
	LED_none();

	source();
	//sink();

	while(true);

	return 0;
}
