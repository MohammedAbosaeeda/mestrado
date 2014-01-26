/*
 * cc1000test.cc
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


void transmiter(){

	OStream out;

	NIC nic;
	NIC::Address address(0x00AA);
	NIC::Protocol protocol;

	out << "\nTransmiter\n\n";

	char data[30] = "E ae ?\n";

	int result;

	int count = 0;
	unsigned long current = 0;
	unsigned long avg = 0;

	while(true){
		//LED_green();
		out << "Called send\n";
		current = CMAC::alarm_time();
		result = nic.send(address, protocol, data, 30);
		current = CMAC::alarm_time() - current;
		if(result == CMAC::TX_OK){
			++count;
			avg += current;
			out << "Sent data: " << data << " - Time: " << current << " - Avg: " << avg/count << "\n";
		}
		else{
			out << "Send error: " << result << "\n";
		}

		//LED_none();
	}

}

void receiver(){

	int count = 0;

	OStream out;

	out << "\nReceiver\n\n";

	NIC nic;
	NIC::Address address(0x00AA);
	NIC::Protocol protocol;

	char data[64];

	while(true){
		//LED_green();
		out << "Called receive\n";
		int result = nic.receive(&address, &protocol, data, 64);
		if(result > 0)
			out << "Received data: " << data << "\n";
		else{
			out << "Received error: " << result << "\n";
		}
		//out << ++count << "\n";
		//LED_none();
	}


}

void transmiter_receiver(){

	OStream out;

	out << "\nTransmiter-Receiver\n\n";

	NIC nic;
	NIC::Address address(0);
	NIC::Protocol protocol;

	char rx_data[64];
	char tx_data[30] = "E ae ?\n";
	int result;

	bool aux = true;

	while(true){
		if(aux){
			LED_green();
			out << "Called send\n";
			result = nic.send(address, protocol, tx_data, 30);
			if(result > 0)
				out << "Sent data: " << tx_data << "\n";
			else
				out << "Send error: " << result << "\n";

			LED_none();
		}
		else{
			LED_green();
			out << "Called receive\n";
			result = nic.receive(&address, &protocol, rx_data, 64);
			if(result > 0)
				out << "Received data: " << rx_data << "\n";
			else
				out << "Receive error: " << result << "\n";
			LED_none();
		}

		aux = !aux;
	}
}

int main(){

	OStream out;
	out << "\nIn the main!\n";

	LED_all();
	for (unsigned int i = 0; i < 0xFFFF; ++i);
	for (unsigned int i = 0; i < 0xFFFF; ++i);
	LED_none();

//	transmiter_receiver();
	transmiter();
//	receiver();

	while(true);

	return 0;
}
