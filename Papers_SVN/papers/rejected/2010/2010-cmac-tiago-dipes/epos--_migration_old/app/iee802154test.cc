/*
 * iee802154test.cc
 *
 */

#include <mach/common/iee_802_15_4.h>
#include <machine.h>
#include <cpu.h>
#include <alarm.h>
#include <utility/ostream.h>

using namespace System;

typedef IO_Map<Machine> IO;

enum {

	STATE_P_ON = 0x00,
	STATE_BUSY_RX = 0x01,
	STATE_BUSY_TX = 0x02,
	STATE_RX_ON = 0x06,
	STATE_TRX_OFF = 0x08,
	STATE_PLL_ON = 0x09,

	FORCE_TRX_OFF = 0x03,
	RX_ON = 0x06,
	PLL_ON = 0x09,

	LED1 = 0x80,
	LED2 = 0x40,
	LED3 = 0x20,
	LEDALL = 0xE0,
};
void LED_none() {
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL);
}
void LED_all() {
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LEDALL);
}
void LED_green() {
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED1);
}
void LED_yellow() {
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED2);
}
void LED_red() {
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3);
}
void LED_yellow_red() {
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3 | LED2);
}
void LED_yellow_green() {
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED2 | LED1);
}
void LED_green_red() {
	LED_none();
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | LED3 | LED1);
}

const unsigned char dataSize = 50;
unsigned char testData[dataSize];
unsigned char frameBuffer[127];

OStream os;

void sender() {

	IEE_802_15_4 protocol;
	protocol.reset();
	protocol.init(2);
	protocol._ack_needed = true;

	for (int i = 0; i < dataSize; ++i)
		testData[i] = i;

	testData[0] = 'A';
	testData[1] = 'N';
	testData[2] = 'D';
	testData[3] = 'R';
	testData[4] = 'E';
	testData[47] = 'e';
	testData[48] = 'i';
	testData[49] = 'Z';

	os << "Pronto - SENDER\n";

	while (true) {
		LED_green();
		Alarm::delay(3000000);
		os << "ENVIANDO\n";
		protocol.send(1, 0, testData, dataSize);
		os << "\n";
	}
}

void receiver() {
	IEE_802_15_4 protocol;

	protocol.reset();
	protocol.init(1);
	os << "Pronto - RECEIVER\n";

	while (true) {
		Alarm::delay(500000);
		os << "RECEBENDO\n";
		protocol.receive(0, 0, testData, dataSize, 0l);
		for (int var = 0; var < dataSize; ++var) {
			os << "data[" << var << "]:" << testData[var] << " ";

		}

	}

}

int main() {

	sender();
	//receiver();

	while (true)
		;

	return 0;
}
