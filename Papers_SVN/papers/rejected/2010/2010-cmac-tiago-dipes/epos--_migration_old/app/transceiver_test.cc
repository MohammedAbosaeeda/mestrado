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
#include <utility/ostream.h>
#include <utility/handler.h>

__USING_SYS

Semaphore receive_sem(0);

void print_result(Transceiver::result_t result){
	OStream out;
	if(result == Transceiver::SUCCESS)
		out << "SUCCESS\n";
	else if(result == Transceiver::BUSY)
		out << "BUSY\n";
	else if(result == Transceiver::FAILED)
		out << "FAILED\n";
	else if(result == Transceiver::FAILED_OPERATING_MODE)
		out << "FAILED_OPERATING_MODE\n";
	else if(result == Transceiver::TIME_OUT)
		out << "TIME_OUT\n";


}

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


void radio_event_handler(Transceiver::event_t event){
	OStream os;
	os << "called radio_event_handler\n";
	if(event == Transceiver::FRAME_RECEIVED){
		os << "radio_event_handler - FRAME_RECEIVED\n";
		receive_sem.v();
	}
	else if(event == Transceiver::FRAME_TRANSMITED){
		os << "radio_event_handler - FRAME_TRANSMITED\n";
	}
	else{
		os << "radio_event_handler - Nothing to handle\n";
	}
}

void transmiter(){

	OStream out;

	Transceiver radio;
	radio.set_event_handler(&radio_event_handler);

	Transceiver::result_t result;

	out << "\nTransmiter\n\n";

	char data[30] = "E ae ?\n";

	for (int i = 0; i < 10; ++i) {
		LED_green();
		out << "called send\n";
		result = radio.send(reinterpret_cast<unsigned char*>(&data[0]), 30);
		print_result(result);
		out << "Sent data: " << data << "\n";

		LED_none();
		for (unsigned int var = 0; var < 0xFFFF; ++var);
		for (unsigned int var = 0; var < 0xFFFF; ++var);
		for (unsigned int var = 0; var < 0xFFFF; ++var);
	}

}

void send_one(){

	Transceiver radio;

	Transceiver::result_t result;

	char data[30] = "E ae ?\n";
	LED_green();
	radio.send(reinterpret_cast<unsigned char*>(&data[0]), 30);
	LED_none();
}

void ultimate_transmiter(){

	OStream out;

	Transceiver radio;
	radio.set_event_handler(&radio_event_handler);

	Transceiver::result_t result;

	out << "\nUltimate Transmiter\n\n";

	char data[30] = "E ae ?\n";

	for (;;) {
		for (unsigned int var = 0; var < 0xFFFF; ++var);
		LED_green();
		out << "called send\n";
		result = radio.send(reinterpret_cast<unsigned char*>(&data[0]), 30);
		print_result(result);
		out << "Sent data: " << data << "\n";
		LED_none();
		for (unsigned int var = 0; var < 0xFFFF; ++var);
	}

}

void cca_test(){
	OStream out;

	Transceiver radio;
//	radio.set_event_handler(&radio_event_handler);

	Transceiver::result_t result;

	out << "\nCCA test\n\n";

	bool cca = false;

	while(true){
//		result = radio.CCA_measurement(cca, Transceiver::CARRIER_SENSE_ONLY, 0); DOES NOT WORK
		result = radio.CCA_measurement(cca, Transceiver::ENERGY_ABOVE_THRESHOLD, 0);
		if(result != Transceiver::SUCCESS){
			out << "cca FAILED\n";
		}
		else if(cca){
//			out << "cca OK\n";
		}
		else{
			out << "cca BUSY\n";
		}
	}
}

void receiver(){

	OStream out;

	out << "\nReceiver\n\n";

	Transceiver radio;
	radio.set_event_handler(&radio_event_handler);

	Transceiver::result_t result;

	char data[30];

	int size;

	while(true){
		LED_green();
		out << "called rx_on\n";
		result = radio.rx_on();
		print_result(result);

		out << "listening\n";
		LED_yellow();
		receive_sem.p();

		out << "called rx_off\n";
		result = radio.rx_off();
		print_result(result);

		out << "called receive\n";
		LED_red();
		result = radio.receive(reinterpret_cast<unsigned char*>(&data[0]), size);
		print_result(result);
		out << "Received data: " << data << " Size: "<< size << "\n";
	}


}

int main(){

	//OStream out;
	//out << "\nIn the main!\n";

	//LED_all();
	//for (unsigned int i = 0; i < 0xFFFF; ++i);
	//for (unsigned int i = 0; i < 0xFFFF; ++i);
	//LED_none();

	while(true)
		send_one();

//	ultimate_transmiter();
//	cca_test();

	while(true);

	return 0;
}
