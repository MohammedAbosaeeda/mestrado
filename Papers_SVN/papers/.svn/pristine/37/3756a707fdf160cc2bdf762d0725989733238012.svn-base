/*
 * cc1000test.cc
 *
 *  Created on: 05/01/2009
 *      Author: tiago
 */

#include <mach/common/at86rf230/at86rf230.h>
#include <mach/common/at86rf230/at86rf230_hal.h>
#include <machine.h>
#include <cpu.h>
#include <traits.h>
#include <alarm.h>
#include <semaphore.h>
#include <display.h>
#include <utility/ostream.h>
#include <utility/handler.h>

//using namespace System;
__USING_SYS

typedef IO_Map<Machine> IO;

enum {

	STATE_P_ON		= 0x00,
	STATE_BUSY_RX	= 0x01,
	STATE_BUSY_TX	= 0x02,
	STATE_RX_ON		= 0x06,
	STATE_TRX_OFF	= 0x08,
	STATE_PLL_ON	= 0x09,

	FORCE_TRX_OFF	= 0x03,
	RX_ON			= 0x06,
	PLL_ON			= 0x09,

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


typedef enum{
	IRQ_0  = 0, // ??
	IRQ_1,	// ??
	IRQ_2,	// ??
	IRQ_3,	// ??
	IRQ_4,	// ??
	IRQ_5,	// radio
	IRQ_6,	// button 1
	IRQ_7,  // button 2
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

Display display;
OStream os;

Semaphore int_rx_start(0);
Semaphore int_trx_end(0);
Semaphore int_pll_lock(0);
enum Interrupt{
		BAT_LOW		= 0x80,
		TRX_UR		= 0x40,
		TRX_END		= 0x08,
		RX_START	= 0x04,
		PLL_UNLOCK	= 0x02,
		PLL_LOCK	= 0x01,

		ALL			= 0xCF,
		UNKNOWN		= 0x00,
};
unsigned char int_count = 0;
void handler(unsigned int){

	unsigned char val = AT86RF230_HAL::readRegister(AT86RF230_HAL::IRQ_STATUS_REG);

	int_count = (int_count > 7)?0:(int_count+1);

	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) & ~LEDALL);
	CPU::out8(IO::PORTB, CPU::in8(IO::PORTB) | (int_count << 5));

	if		(val & RX_START) int_rx_start.v();
	else if (val & TRX_END) int_trx_end.v();
	else if (val & PLL_LOCK) int_pll_lock.v();

}

const unsigned char dataSize = 120;
unsigned char testData[dataSize];
unsigned char frameBuffer[130];
AT86RF230_HAL::Frame frame;

void halTest(){

	display.puts("Initializing radio");

	AT86RF230_HAL::init();

	display.puts("   OK\n");

	unsigned char value;


	// read/write register test

	display.puts("State -> TRX_OFF");

	AT86RF230_HAL::writeRegister(AT86RF230_HAL::TRX_STATE_REG, FORCE_TRX_OFF);

	value = AT86RF230_HAL::readRegister(AT86RF230_HAL::TRX_STATUS_REG);

	value &= 0x1F;

	if (value == STATE_TRX_OFF)
		display.puts("   OK\n");
	else
		display.puts("   FAILED\n");

	display.puts("Checking IRQ mask");

	value = AT86RF230_HAL::readRegister(AT86RF230_HAL::IRQ_MASK_REG);

	if (value == 0xFF)
		display.puts("   OK\n");
	else
		display.puts("   FAILED\n");

	Alarm::delay(500000);


	///setup interrupts

	display.puts("Setup interrupt handler");

	Machine::int_vector(IC::IRQ_IRQ5, &handler);
	enable_external_int(IRQ_5, IRQ_RISING_EDGE);

	display.puts("   OK\n");


	// frame buffer, receive/transmit and interrupt test

	bool transmiter = true;

	display.puts("Preparing frame");

	for(int i = 0; i < dataSize; ++i)
		testData[i] = i;

	display.puts("   OK\n");


	if(transmiter){

		display.puts("Writing to frame buffer");

		frame.frame_length = dataSize;
		for(int i = 0; i < dataSize; ++i){
			frame.data[i] = testData[i];
		}

		AT86RF230_HAL::writeFrameBuffer(&frame);

		display.puts("   OK\n");

		display.puts("State -> PLL_ON");

		AT86RF230_HAL::writeRegister(AT86RF230_HAL::TRX_STATE_REG, PLL_ON);

		//Alarm::delay(125000);
		int_pll_lock.p();

		value = AT86RF230_HAL::readRegister(AT86RF230_HAL::TRX_STATUS_REG);

		value &= 0x1F;

		if (value == STATE_PLL_ON)
			display.puts("   OK\n");
		else
			display.puts("   FAILED\n");


		display.puts("STARTING TX\n");

		AT86RF230_HAL::setSLP_TRhigh();
		AT86RF230_HAL::setSLP_TRlow();

		display.puts("State -> BUSY_TX");

		value = AT86RF230_HAL::readRegister(AT86RF230_HAL::TRX_STATUS_REG);

		value &= 0x1F;

		if (value != STATE_BUSY_TX)
			display.puts("   OK\n");
		else
			display.puts("   FAILED\n");


		//Alarm::delay(125000);
		int_trx_end.p();

		display.puts("State -> PLL_ON");

		value = AT86RF230_HAL::readRegister(AT86RF230_HAL::TRX_STATUS_REG);

		value &= 0x1F;

		if (value == STATE_PLL_ON)
			display.puts("   OK\n");
		else
			display.puts("   FAILED\n");

		display.puts("TX FINISHED\n");


	}
	else{ //receiver

		display.puts("State -> RX_ON");

		AT86RF230_HAL::writeRegister(AT86RF230_HAL::TRX_STATE_REG, RX_ON);

		//Alarm::delay(125000);
		int_pll_lock.p();

		value = AT86RF230_HAL::readRegister(AT86RF230_HAL::TRX_STATUS_REG);

		value &= 0x1F;

		if (value == STATE_RX_ON)
			display.puts("   OK\n");
		else
			display.puts("   FAILED\n");


		int_rx_start.p();

		display.puts("STARTING TX\n");

		int_trx_end.p();

		display.puts("TX FINISHED\n");

		display.puts("Reading from frame buffer");

		AT86RF230_HAL::readFrameBuffer(&frame);

		display.puts("   OK\n");

		display.puts("Checking data");

		bool ok = true;
		for(int i = 0; i < dataSize && ok; ++i){
			ok = frame.data[i] == testData[i];
		}

		if(ok)
			display.puts("   OK\n");
		else
			display.puts("   FAILED\n");

	}

	display.puts("\nTHE END\n\n");

}

unsigned char ckp_count = 0;
void checkpoint(){
	ckp_count = (ckp_count > 7)?0:(ckp_count+1);

	CPU::out8(Machine::IO::PORTB, CPU::in8(Machine::IO::PORTB) & ~(0xE0));
	CPU::out8(Machine::IO::PORTB, CPU::in8(Machine::IO::PORTB) | (ckp_count << 5));
}

/*void mediatorTest1(){

	display.puts("Initializing radio");

	AT86RF230 radio;

	if(radio.getState() == AT86RF230::TRX_STATUS_TRX_OFF)
		display.puts("   OK\n");
	else
		display.puts("   FAILED\n");


	//receive/transmit

	bool transmiter = false;

	display.puts("Preparing frame");

	for(int i = 0; i < dataSize; ++i)
		testData[i] = i;

	display.puts("   OK\n");

	AT86RF230::result_t result;

	checkpoint();//1

	if(transmiter){


		frame.frame_length = dataSize;
		for(int i = 0; i < dataSize; ++i){
			frame.data[i] = testData[i];
		}

		checkpoint();//2

		display.puts("STARTING TX\n");

		result = radio.send(testData, dataSize);

		checkpoint();//3

		if(result == AT86RF230::SUCCESS)
			display.puts("TX FINISHED\n");
		else
			display.puts("TX FAILED\n");

		checkpoint();//4


	}
	else{ //receiver

		checkpoint();//2

		display.puts("STARTING RX\n");

		result = radio.

		result = radio.receive(fr, 0);

		checkpoint();//3

		if(result == AT86RF230::SUCCESS)
			display.puts("RX FINISHED\n");
		else
			display.puts("RX FAILED\n");

		checkpoint();//4

		display.puts("Checking data");

		bool ok = true;
		for(int i = 0; i < dataSize && ok; ++i){
			ok = frame.data[i] == testData[i];
		}

		if(ok)
			display.puts("   OK\n");
		else
			display.puts("   FAILED\n");

		checkpoint();//5

	}

	display.puts("\nTHE END\n\n");
}

void receiveData(AT86RF230 &radio);
void sendACK(AT86RF230 &radio);
void sendData(AT86RF230 &radio);
void receiveACK(AT86RF230 &radio);

void mediatorTest2(){

	display.puts("Initializing radio");

	AT86RF230 radio;

	if(radio.getState() == AT86RF230::TRX_STATUS_TRX_OFF)
		display.puts("   OK\n");
	else
		display.puts("   FAILED\n");


	for(int i = 0; i < dataSize; ++i)
		testData[i] = i;

	bool receiver = false;
	int iterations = 20;

	for(int i = 0; i < iterations; ++i){

		if(receiver){
			display.puts("#RECEIVER MODE#\n");
			receiveData(radio);
			Alarm::delay(500000);
			sendACK(radio);
			receiver = false;
			display.puts("#FINISHED#\n\n");
		}
		else{
			Alarm::delay(2000000);
			display.puts("#TRANSMITER MODE#\n");
			sendData(radio);
			Alarm::delay(250000);
			receiveACK(radio);
			receiver  = true;
			display.puts("#FINISHED#\n\n");
		}

	}
}

void receiveData(AT86RF230 &radio){

	AT86RF230::result_t result;

	display.puts("Listening for data...\n");

	result = radio.receive(&frame, 0);

	if(result == AT86RF230::SUCCESS)
		display.puts("Data received\n");
	else{
		display.puts("Data receiving failed\n");
		while(true);
	}

	display.puts("Checking data");

	bool ok = true;
	for(int i = 0; i < dataSize && ok; ++i){
		ok = frame.data[i] == testData[i];
	}

	if(ok)
		display.puts("   OK\n");
	else{
		display.puts("   FAILED\n");
		while(true);
	}
}

void sendACK(AT86RF230 &radio){

	AT86RF230::result_t result;

	frame.frame_length = 3;
	frame.data[0] = 'A';
	frame.data[1] = 'C';
	frame.data[2] = 'K';

	display.puts("Sending ACK");

	result = radio.send(&frame);

	if(result == AT86RF230::SUCCESS)
		display.puts("  OK\n");
	else{
		display.puts("  FAILED\n");
		while(true);
	}
}

void sendData(AT86RF230 &radio){

	AT86RF230::result_t result;

	frame.frame_length = dataSize;
	for(int i = 0; i < dataSize; ++i){
		frame.data[i] = testData[i];
	}

	display.puts("Sending data");

	result = radio.send(&frame);

	if(result == AT86RF230::SUCCESS)
		display.puts("   OK\n");
	else{
		display.puts("   FAILED\n");
		while(true);
	}
}

void receiveACK(AT86RF230 &radio){

	AT86RF230::result_t result;

	display.puts("Waiting ACK...\n");

	result = radio.receive(&frame, 0);

	if(result == AT86RF230::SUCCESS)
		display.puts("Data received\n");
	else{
		display.puts("Data receiving failed\n");
		while(true);
	}

	display.puts("Checking ACK");

	if((frame.frame_length == 3) && (frame.data[0] == 'A') && (frame.data[1] == 'C')
			&& (frame.data[2] == 'K'))

		display.puts("   OK\n");
	else{
		display.puts("   FAILED\n");
		while(true);
	}
}*/

void mediatorTest3(){

	os << "Initializing radio";

	AT86RF230 radio;

	if(radio.getState() == AT86RF230::TRX_STATUS_TRX_OFF)
		os << "   OK\n";
	else
		os << "   FAILED\n";


	//receive/transmit

	bool transmiter = false;

	os << "Preparing frame";

	for(int i = 0; i < dataSize; ++i)
		testData[i] = i;

	os << "   OK\n";

	AT86RF230::result_t result;

	while(true){

		if(transmiter){

			Alarm::delay(2000000);
			os << "STARTING TX\n";

			LED_all();
			result = radio.send(testData, dataSize);

			if(result == AT86RF230::SUCCESS)
				os << "TX FINISHED\n";
			else{
				os << "TX FAILED\n";
				while(true);//Alarm::delay(2000000);
			}
			LED_none();

		}
		else{ //receiver

			os << "STARTING RX\n";

			result = radio.rx_on();

			if(result != AT86RF230::SUCCESS)
				os << "RX FAILED : TRX_OFF -> RX_ON\n";
			else{

				while(!radio.dataReceived());
				radio.rx_off();
				/*bool aux = false;
				while(!aux){
					aux = AT86RF230_HAL::readRegister(AT86RF230_HAL::IRQ_STATUS_REG) == 0x0C;
					Alarm::delay(100000);
				}
				radio.forceValidState();*/

				int size = 0;

				result = radio.receive(frameBuffer, size);

				if(result == AT86RF230::SUCCESS)
					os << "RX FINISHED\n";
				else
					os << "RX FAILED : RECEIVING\n";

				os << "Received " << size << " bytes: ";
				for(int i = 0; i < size; ++i){
					os << static_cast<int>(frameBuffer[i]) << " ";
				}
				os << "\nChecking data";

				bool ok = true;
				for(int i = 0; i < dataSize && ok; ++i){
					ok =  frameBuffer[i] == testData[i];
				}

				if(ok)
					os << "   OK\n";
				else
					os << "   FAILED\n";
			}
		}

		os << "\nTHE END\n\n";

	}
}

void ccaTest(){
	os << "Initializing radio";

	AT86RF230 radio;

	if(radio.getState() == AT86RF230::TRX_STATUS_TRX_OFF)
		os << "   OK\n";
	else
		os << "   FAILED\n";

	AT86RF230::result_t result;

	while(true){

		Alarm::delay(125000);

		bool ccaResult = true;

		result = radio.CCA_measurement(ccaResult, AT86RF230::ENERGY_ABOVE_THRESHOLD);

		if(result != AT86RF230::SUCCESS){
			os << "CCA measurement failed -> ";
			if(result == AT86RF230::BUSY)
				os << "BUSY\n";
			else if(result == AT86RF230::FAILED)
				os << "FAILED\n";
			else
				os << "UNKNOWN\n";

			if(radio.forceValidState() != AT86RF230::SUCCESS){
				os << "EPIC FAIL";
				while(true);
			}

			Alarm::delay(1000000);

		}
		else{
			if(ccaResult)
				os << "CCA Status -> Idle\n";
			else
				os << "CCA Status -> BUSYBUSYBUSY\n";
		}

		LED_none();

	}
}

/*Semaphore fullTXsem(1);
bool fullTXaux = false;
void fullTransmiterHandler(){
	fullTXsem.p();
	fullTXaux = !fullTXaux;
	fullTXsem.v();
}*/
void fullTransmiter(){
	os << "Initializing radio";

	AT86RF230 radio;

	if(radio.getState() == AT86RF230::TRX_STATUS_TRX_OFF)
		os << "   OK\n";
	else{
		os << "   FAILED\n";
		LED_red();
		while(true);
	}


	os << "Preparing frame";

	for(int i = 0; i < dataSize; ++i)
		testData[i] = i;

	os << "   OK\n";

	AT86RF230::result_t result;

	/*Handler_Function handler_a(&fullTransmiterHandler);
	Alarm alarm_a(5000000, &handler_a, 100);*/

	while(true){
		os << "STARTING TX\n";

		LED_all();
		result = radio.send(testData, dataSize);

		if(result == AT86RF230::SUCCESS)
			os << "TX FINISHED\n";
		else{
			os << "TX FAILED\n";
			LED_yellow();
			while(true);//Alarm::delay(2000000);
		}
		LED_none();

		/*while(true){
			fullTXsem.p();
			if(fullTXaux) break;
			fullTXsem.v();
		}*/
	}
}

void alarmHandler(){
	os << "OStream!\n";
}


int main(){

	frame.data = frameBuffer;

	//halTest();

	//mediatorTest1();
	//mediatorTest2(); //dando muito pau
	//mediatorTest3();
	//ccaTest();
	fullTransmiter();

	/*AT86RF230_HAL::init();
	while(true){
		AT86RF230_HAL::readFrameBuffer(&frame);
		os << static_cast<int>(frame.frame_length) << " " << static_cast<int>(frame.lqi) <<"\n";
		os << AT86RF230_HAL::readRegister(AT86RF230_HAL::IRQ_STATUS_REG) << "\n";
		Alarm::delay(100000);
	}*/


	while(true);

	return 0;
}
