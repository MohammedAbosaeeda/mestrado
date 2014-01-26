/*
 * SerialInterface.h
 *
 *  Created on: 15/10/2008
 *      Author: tiago
 */

#include <uart.h>
#include <cpu.h>
#include <machine.h>
#include <alarm.h>
#include <uart.h>

#ifndef SERIALINTERFACE_H_
#define SERIALINTERFACE_H_

__USING_SYS

class SerialInterface{

private:
	UART modem_uart;
	UART debug_uart;
	static const bool debug_mode = false;

public:
	SerialInterface()
		:modem_uart(), debug_uart()
	{

	}

	int getLine(char* buffer, int bufferSize){

		char lastChar = 0;
		int count = 0;

		for(; count < bufferSize; ++count) buffer[count] = 0;
		count = 0;

		while((lastChar != '\n') && (count < bufferSize)){
			lastChar = modem_uart.get();
			buffer[count++] = lastChar;
		}
		buffer[count++] = 0;

		if(debug_mode){
			debug_uart.put('$');
			debug_uart.put('R');
			debug_uart.put('X');
			debug_uart.put('=');
			for(int i = 0; buffer[i] != 0;++i) debug_uart.put(buffer[i]);
			debug_uart.put('\n');
		}
		return count;
	}


	int putLine(char* buffer){

		int i = 0;
		while(buffer[i] != 0) modem_uart.put(buffer[i++]);
		modem_uart.put('\r');
		modem_uart.put('\n');

		if(debug_mode){
			debug_uart.put('$');
			debug_uart.put('T');
			debug_uart.put('X');
			debug_uart.put('=');
			for(int i = 0; buffer[i] != 0;++i) debug_uart.put(buffer[i]);
			debug_uart.put('\n');
		}

		return i+2;
	}

	int put(char* buffer){

		int i = 0;
		while(buffer[i] != 0) modem_uart.put(buffer[i++]);

		if(debug_mode){
			debug_uart.put('$');
			debug_uart.put('T');
			debug_uart.put('X');
			debug_uart.put('=');
			for(int i = 0; buffer[i] != 0;++i) debug_uart.put(buffer[i]);
			debug_uart.put('\n');
		}

		return i;
	}




};

#endif /* SERIALINTERFACE_H_ */



