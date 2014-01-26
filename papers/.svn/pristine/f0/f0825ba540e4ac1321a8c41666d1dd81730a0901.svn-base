/*
 * spiTest.cc
 *
 *  Created on: 15/01/2009
 *      Author: tiago
 */

using namespace System;

#include <spi.h>
#include <machine.h>
#include <cpu.h>
#include <traits.h>

typedef IO_Map<Machine> IO;


enum {
	REG_READ		= 0x80,
	REG_WRITE		= 0xC0,

	TRX_STATUS		= 0x01,
	TRX_STATE		= 0x02,

	P_ON			= 0x00,
	TRX_OFF			= 0x08,
	PLL_ON			= 0x09,

	FORCE_TRX_OFF	= 0x03,

	LED1			= 0x80,
	LED2			= 0x40,
	LED3 			= 0x20,
	LEDALL			= 0xE0,
};



void delay(unsigned int s){
	for(unsigned int i = 1; i <= s; ++i)
		for(unsigned int i = 0; i <= 20; ++i)
			for(unsigned int i = 0; i <= 9999; ++i);
}


void LED_none(){
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) & ~LEDALL);
}

void LED_all(){
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) | LEDALL);
}

void LED_green(){
	LED_none();
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) | LED1);
}

void LED_yellow(){
	LED_none();
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) | LED2);
}

void LED_red(){
	LED_none();
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) | LED3);
}

void LED_output(char c){

	char mask1 = 0x1F;
	char mask2 = 0xE0;
	char mask3 = 0x60;

	LED_none();
	LED_all();
	delay(1);


	AVR8::out8(IO::PORTB, (AVR8::in8(IO::PORTB) & mask1) | ((c << 5) & mask2));
	delay(1);
	LED_none();
	LED_all();
	delay(1);

	AVR8::out8(IO::PORTB, (AVR8::in8(IO::PORTB) & mask1) | ((c << 2) & mask2));
	delay(1);
	LED_none();
	LED_all();
	delay(1);

	AVR8::out8(IO::PORTB, (AVR8::in8(IO::PORTB) & mask1) | ((c >> 1) & mask3));
	delay(1);
	LED_none();
	LED_all();
	delay(1);

}


/*
Mega1281 AT86RF230
PA7 -> RST
PB0 -> SEL
PB4 -> SLP_TR
PD6 -> CLKM
PE5 -> IRQ
 */
char SPI_dummy;
enum {
	SS_PIN       = 0,
	SCK_PIN      = 1,
	MOSI_PIN     = 2,
	MISO_PIN     = 3,
	SLP_TR		 = 4,
	RST			 = 7,
};
enum {
	// SPCR
	SPIE     = 7,
	SPE      = 6,
	DORD     = 5,
	MSTR     = 4,
	CPOL     = 3,
	CPHA     = 2,
	SPR1     = 1,
	SPR0     = 0,
	// SPSR
	SPIF     = 7,
	WCOL     = 6,
	SPI2X    = 0,
};

void SPI_init(){

    //Set SS, CLK and MOSI as output.
	AVR8::out8(IO::DDRB, AVR8::in8(IO::DDRB) |
			((1 << SS_PIN) | (1 << SCK_PIN) | (1 << MOSI_PIN)));
	//Set SS and CLK high
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) |
				((1 << SS_PIN) | (1 << SCK_PIN)));
	AVR8::out8(IO::SPCR, (1<<SPE)|(1<<MSTR));//Enable SPI module and master operation.
	AVR8::out8(IO::SPSR, (1 << SPI2X)); //Enable doubled SPI speed in master mode.

}

void SPI_SS_high(){
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) | (1<<SS_PIN));
}

void SPI_SS_low(){
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) & ~(1<<SS_PIN));
}

void SPI_transmit(unsigned char toSlave, unsigned char &fromSlave){
	AVR8::out8(IO::SPDR, toSlave);
	while(!(AVR8::in8(IO::SPSR) & (1<<SPIF)));
	fromSlave = AVR8::in8(IO::SPDR);
}

void RADIO_init(){

	delay(1); //time to enter state P_ON

	/*IO Specific Initialization.*/
	AVR8::out8(IO::DDRB, AVR8::in8(IO::DDRB) | (1 << SLP_TR)); //Enable SLP_TR as output.
	AVR8::out8(IO::DDRA, AVR8::in8(IO::DDRA) | (1 << RST)); //Enable RST as output.

	/*SPI Specific Initialization.*/
	SPI_init();

	/*reset the radio*/
	AVR8::out8(IO::PORTA, AVR8::in8(IO::PORTA) & ~(1 << RST));  //set RST low
	AVR8::out8(IO::PORTB, AVR8::in8(IO::PORTB) & ~(1 << SLP_TR)); //set SLP_TR low
	delay(1); //time to reset
	AVR8::out8(IO::PORTA, AVR8::in8(IO::PORTA) | (1 << RST)); //set RST high


}


/*void test1(){
	LED_all();

	delay(1000000*10);

	LED_none();

	char c;

	SPI spi;
	SPI_SS_high();


	LED_yellow();

	delay(1000000*10);

	SPI_SS_low();
	spi.put(REG_WRITE | TRX_STATE); while(!spi.complete());
	spi.get();
	spi.put(FORCE_TRX_OFF); while(!spi.complete());
	spi.get();
	SPI_SS_high();

	//delay(1000);

	SPI_SS_low();
	spi.put(REG_READ | TRX_STATUS); while(!spi.complete());
	c = spi.get();

	spi.put(c); while(!spi.complete());//dummy
	c = spi.get();
	SPI_SS_high();

	c &= 0x1F;

	if (c == TRX_OFF)
		LED_green();
	else
		LED_red();
}*/

void test2(){
	LED_all();

	RADIO_init();

	LED_none();

	unsigned char fromSlave;

	SPI_SS_low();
	SPI_transmit((REG_READ | 0x0E), fromSlave);
	SPI_transmit(fromSlave, fromSlave);
	SPI_SS_high();

	if (fromSlave == 0XFF)
		LED_green();
	else if (fromSlave == 0)
		LED_yellow();
	else
		LED_red();

	delay(1);
	LED_none();
	delay(1);

	SPI_SS_low();
	SPI_transmit((REG_WRITE | 0x0E), fromSlave);
	SPI_transmit(0x35, fromSlave);
	SPI_SS_high();

	//delay(1);

	SPI_SS_low();
	SPI_transmit((REG_READ | 0x0E), fromSlave);
	SPI_transmit(fromSlave, fromSlave);
	SPI_SS_high();

	if (fromSlave == 0x35)
		LED_green();
	else if (fromSlave == 0)
		LED_yellow();
	else
		LED_red();
}

void test3(){
	SPI_init();

	LED_none();

	unsigned char fromSlave;

	while(true){
		LED_yellow();
		delay(2);
		LED_green();
		SPI_SS_low();
		SPI_transmit((REG_READ | 0x0E), fromSlave);
		SPI_transmit(fromSlave, fromSlave);
		SPI_SS_high();
		delay(1);
		LED_red();
		delay(1);
	}

}

int main(){

	test2();

	//test3();

	while(1);
	return 0;
}
