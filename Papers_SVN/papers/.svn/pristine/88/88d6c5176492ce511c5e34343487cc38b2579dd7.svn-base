/*
 * CMAC latency test
 *
 * App for measuring the throughput, packet loss rate and energy consumption
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
#include "flash.h"
//#include <avr/pgmspace.h>
#include <eeprom.h>


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
	DATA_SIZE = 64,
	N_SENSORS = 4,
	BYTES_TO_SEND = 1024*3,
	BURST_SIZE = 64,
	N_BURST = BYTES_TO_SEND/BURST_SIZE
};
const unsigned long BURST_INTERVAL = (1 << CMAC_States::IEEE802154_Beacon_Sync::DEFAULT_BEACON_ORDER)*15;
const unsigned int COORDINATOR_ADDRESS = 0x00AA;
const unsigned int SENSOR_ADDRESS[N_SENSORS] = {0x00BB, 0x00CC, 0x00DD, 0x00EE};


void blink_leds_forever(){
	while(true){
		LED_red();
		unsigned long delay = CMAC::alarm_time() + 100;
		while(CMAC::alarm_time() < delay);
		LED_yellow();
		delay = CMAC::alarm_time() + 100;
		while(CMAC::alarm_time() < delay);
		LED_green();
		delay = CMAC::alarm_time() + 100;
		while(CMAC::alarm_time() < delay);
	}
}


volatile bool button_pressed = false;

void button_int(unsigned int){
	button_pressed = true;
}


void coordinator_node(){

	Machine::int_vector(IC::IRQ_IRQ6, button_int);
	Machine::int_vector(IC::IRQ_IRQ7, button_int);

	IC::enable_external_int(IC::EXT_IRQ_6, IC::IRQ_RISING_EDGE);
	IC::enable_external_int(IC::EXT_IRQ_7, IC::IRQ_RISING_EDGE);

	OStream out;

	out << "Coordinator node\n";

	NIC nic;
	NIC::Address address;
	NIC::Protocol protocol;

	int result;

	char data[DATA_SIZE];
	unsigned int size = 0;

	unsigned int total_received_bytes;
	unsigned int received_bytes[N_SENSORS];

	total_received_bytes = 0;
	for (int i = 0; i < N_SENSORS; ++i) {
		received_bytes[i] = 0;
	}

	while(true){
		size = DATA_SIZE;
		result = nic.receive(&address, &protocol, data, size);
		if(result > 0){
			total_received_bytes += size;
			bool ok = false;
			for (int i = 0; i < N_SENSORS; ++i) {
				if(SENSOR_ADDRESS[i] == address){
					//out << "OK " << result << "\n";
					received_bytes[i] += result;
					ok = true;
					break;
				}
			}
			if(!ok){
				out << "Received from unknown sensor node address: " << static_cast<unsigned int>(address) << "\n";
			}
		}
		else{
			out << "Receive error: " << result << " - Trying again\n";
		}

		if(button_pressed) break;
	}

	out << "Received " << total_received_bytes << " bytes\n";
	out << "Statistics:\n";
	out << nic.statistics() << "\n";
	out << "Bytes received per sensor node: " << "\n";
	for (int i = 0; i < N_SENSORS; ++i) {
		out << SENSOR_ADDRESS[i] << ": " << received_bytes[i] << "\n";
	}
	out << "\n";

}

void write_to_eeprom(NIC::Statistics *stats){
	EEPROM eeprom;

	NIC::Address address(Traits<CMAC>::ADDRESS);
	unsigned char *ptr = reinterpret_cast<unsigned char*>(&address);
	for (unsigned int i = 0; i < sizeof(NIC::Address); ++i) {
		eeprom.write(i, ptr[i]);
	}

	ptr = reinterpret_cast<unsigned char*>(stats);
	for (unsigned int i = 0; i < sizeof(NIC::Statistics); ++i) {
		eeprom.write(i+sizeof(NIC::Address), ptr[i]);
	}
}

void sensor_node(){
	OStream out;

	out << "Sensor node\n";

	NIC nic;
	NIC::Protocol protocol;
	NIC::Address address(COORDINATOR_ADDRESS);

	int result;

	char data[DATA_SIZE];

	for (int var = 0; var < N_BURST; ++var) {

		unsigned long delay = CMAC::alarm_time() + BURST_INTERVAL;
		while(CMAC::alarm_time() < delay);

		//for (int j = 0; j < 4; ++j) {
		for (int j = 0; j < (BURST_SIZE/DATA_SIZE); ++j) {
			while(true){
				result = nic.send(address, protocol, data, DATA_SIZE/2);
				if(result == CMAC::TX_OK){
					break;
				}
				else{
					out << "Send error: " << result << " - Trying again\n";
				}

			}
		}
	}

	LED_all();
	NIC::Statistics stats = nic.statistics();
	write_to_eeprom(&stats);
	out << "Statistics:\n";
	out << nic.statistics() << "\n";
	out << "\n";
	LED_none();

	blink_leds_forever();

}

void flash(){

	OStream out;

	//Flash flash;
	EEPROM eeprom;

	NIC::Statistics stats;


	stats.rx_packets = 5;
	stats.rx_bytes = 2;
	stats.rx_time = 3;
	stats.tx_packets = 4;
	stats.tx_bytes = 5;
	stats.tx_time = 6;
	stats.dropped_packets = 7;
	stats.total_tx_packets = 8;

	out << stats << "\n";

	char *ptr = reinterpret_cast<char*>(&stats);
	//flash.write(0, reinterpret_cast<unsigned char*> (&stats), sizeof(NIC::Statistics), true);
	for (unsigned int i = 0; i < sizeof(NIC::Statistics); ++i) {
		eeprom.write(i, ptr[i]);
		//for (unsigned int var = 0; var < 0xFFF; ++var);
	}

	NIC::Statistics stats2;
	ptr = reinterpret_cast<char*>(&stats2);

	for (unsigned int i = 0; i < sizeof(NIC::Statistics); ++i) {
		//ptr[i] = pgm_read_byte(0xFA00+i);
		ptr[i] = eeprom.read(i);
	}

	out << stats2 << "\n";
/*
 *
 */
	/*
	eeprom.write(0x0000, 0x10);
	out << "1\n";
	for (unsigned int var = 0; var < 0xFFFF; ++var);
	eeprom.write(0x0001, 0x02);
	out << "2\n";
	for (unsigned int var = 0; var < 0xFFFF; ++var);
	eeprom.write(0x0002, 0x30);
	out << "3\n";
	for (unsigned int var = 0; var < 0xFFFF; ++var);
	eeprom.write(0x0003, 0x04);
	out << "4\n";
	for (unsigned int var = 0; var < 0xFFFF; ++var);
	eeprom.write(0x0004, 0x50);
	out << "5\n";
	for (unsigned int var = 0; var < 0xFFFF; ++var);
	*/

	out << "Done\n";
}



int main(){

	OStream out;
	out << "\nIn the main!\n";

//	Machine::int_vector(IC::IRQ_IRQ6, button_int);
//	Machine::int_vector(IC::IRQ_IRQ7, button_int);

//	IC::enable_external_int(IC::EXT_IRQ_6, IC::IRQ_RISING_EDGE);
//	IC::enable_external_int(IC::EXT_IRQ_6, IC::IRQ_RISING_EDGE);

//	while(!button_pressed);
//	button_pressed = false;

	LED_all();
	for (unsigned int i = 0; i < 0xFFFF; ++i);
	for (unsigned int i = 0; i < 0xFFFF; ++i);
	LED_none();

	//flash();

	//coordinator_node();
	sensor_node();

	while(true);

	return 0;
}
