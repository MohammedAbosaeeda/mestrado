#include <alarm.h>
#include <machine.h>
#include <cpu.h>
#include <../app/tsg/SerialInterface.h>
#include <../app/tsg/ModemInterface.h>
#include "led_debug.h"

#ifndef ESTACAO_H_
#define ESTACAO_H_

__USING_SYS

#define LED_YELLOW 350
#define LED_GREEN 351
#define LED_BOTH 352
#define LED_NONE 353
//"Temperatura: xxC, Pressão: xxxxhPa, Umidade: xx%"
//static char TEST_MSG[64] = "Temp.: xxC, Pressao: xxxxkPa, Umidade: xx%";
static char TEMP_MSG[] = "Temp.: 00C, ";
//static char PRESS_MSG[] = "Pressao.: 000kPa, ";
static char HUMI_MSG[] = "Umidade: 00%";
//static char TEST_MSG[64] = "Temperaturax xxxC Umidadex xxx Pressaox xxxatm";

class Estacao{

private:
	//sensors data goes here
	int temperature; //in Cº
	int humidity; //in %
	//int pressure; //in atm

	// creates an instance of the ADC1 from ATMega128
	//ATMega128_ADC adc0; // humity
	//ATMega128_ADC adc1; // temperature
	//ATMega128_ADC adc3; // pressure


	ModemInterface modem;

	//TSG_serial_interface *debug_uart;


public:

	Estacao()
	:modem()
	{
		temperature = 0;
		humidity = 0;
	}

	void start(){

		TSG_led_debug::debug(TSG_led_debug::IDLE);

		//testa os leds
		/*
		lightLed(LED_YELLOW);
		Alarm::delay(1000000);

		lightLed(LED_GREEN);
		Alarm::delay(1000000);

		lightLed(LED_BOTH);
		Alarm::delay(1000000);

		lightLed(LED_NONE);
		Alarm::delay(1000000);

		 */

		/*char numBuffer[16] = "+554888222134";
		char *msgBuffer = modem.getBuffer();

		int idx = modem.prepareSMS(numBuffer);

//static char TEST_MSG[64] = "Temp.: xxC, Pressao: xxxxkPa, Umidade: xx%";
//static char TEMP_MSG[] = "Temp.: 00C, ";
//static char PRESS_MSG[] = "Pressao.: 0000kPa, ";
//static char HUMI_MSG[] = "Umidade: 00C%";

		readSensors();

		int i = 0;
		while(TEMP_MSG[i] != 0) msgBuffer[idx++] = TEMP_MSG[i++];
		intToString(temperature, msgBuffer, idx-5);

		i = 0;
		while(PRESS_MSG[i] != 0) msgBuffer[idx++] = PRESS_MSG[i++];
		int offset = 9;
		if(pressure >= 100) offset = 8;
		else offset = 7;
		intToString(pressure, msgBuffer, idx-offset);

		i = 0;
		while(HUMI_MSG[i] != 0) msgBuffer[idx++] = HUMI_MSG[i++];
		intToString(humidity, msgBuffer, idx-3);

		modem.sendSMS(idx);*/

		/*char numBuffer[16] = "+554899515195", msgBuffer[64] = "Teste";

		modem.sendSMS(numBuffer, msgBuffer);*/


		//while(true) modem.SMSReceived(numBuffer);


		/*char buffer[16];
		for(int i = 0; i < 16; ++i) buffer[i] = 0;

		while (1) {
			readSensors();
			debug_uart->put_s("Temp: ");
			intToString(temperature, buffer, 0);
			debug_uart->put_s(buffer);
			debug_uart->put_s("\nPress: ");
			intToString(pressure, buffer, 0);
			debug_uart->put_s(buffer);
			debug_uart->put_s("\n");

			//Alarm::delay(100000);
		}*/


		char numBuffer[16];

		while(true){

			int status = modem.SMSReceived(numBuffer);

			if(status == SMS_MSG){
				TSG_led_debug::debug(TSG_led_debug::SMS_RECEIVED);
				sms(numBuffer);
			}
			else if(status == ERROR_MSG){
				error();
			}
			else if(status == OK_MSG){
				ok();
			}
			else if(status == OTHER_MSG){
				other();
			}

			/*if(debug_uart->get_c() == 'h'){
				readSensors();
				int aux = intToString(humidity, numBuffer, 0);
				numBuffer[aux] = 0;
				debug_uart->put_s(numBuffer);
			}*/
			TSG_led_debug::debug(TSG_led_debug::IDLE);
		}

	}

private:

	void error(){
		TSG_led_debug::debug(TSG_led_debug::ERROR);
		while(true);
	}

	void ok(){
		//cout << "---OK" << endl;
	}

	void other(){
		//cout << "---Other" << endl;
	}

	bool confirm(){
		return true;
	}

	//void sms(char *numBuffer, char *msgBuffer){
	void sms(char *numBuffer){

		//debug_uart->put_s("-->Sending SMS\n Number: ");
		//debug_uart->put_s(numBuffer);
		//debug_uart->put_s("\n");

		static char *buff1;

		//flush util the OK from the ack msg
		int status = modem.SMSReceived(buff1);
		while(status != OK_MSG){
			status = modem.SMSReceived(buff1);
			if(status == ERROR_MSG){
				error();
				return;
			}
		}
		TSG_led_debug::debug(TSG_led_debug::OK);
		//there still is a OTHER_MSG
		//status = modem.SMSReceived(buff1);
		//if(status != OTHER_MSG){
		//	error();
		//	return;
		//}


		//waits for the msg confirmation
		//press button on AVR
		if(!confirm())
			return;

		//creates the msg - Rodrigo
		/*int i;
		for (i = 0; true ; ++i) {
			msgBuffer[i] = TEST_MSG[i];
			if(msgBuffer[i] == 0) break;
		}*/

		//sends the msg
		//modem.sendSMS(numBuffer, msgBuffer);

		TSG_led_debug::debug(TSG_led_debug::PREPARING_MSG);

		//creates and send the message
		int idx = modem.prepareSMS(numBuffer);
		char *msgBuffer = modem.getBuffer();

		//static char TEST_MSG[64] = "Temp.: xxC, Pressao: xxxxkPa, Umidade: xx%";
		//static char TEMP_MSG[] = "Temp.: 00C, ";
		//static char PRESS_MSG[] = "Pressao.: 0000kPa, ";
		//static char HUMI_MSG[] = "Umidade: 00C%";

		readSensors();

		int i = 0;
		while(TEMP_MSG[i] != 0) msgBuffer[idx++] = TEMP_MSG[i++];
		intToString(temperature, msgBuffer, idx-5);

		//i = 0;
		//while(PRESS_MSG[i] != 0) msgBuffer[idx++] = PRESS_MSG[i++];
		//int offset = 9;
		//if(pressure >= 100) offset = 8;
		//else offset = 7;
		//intToString(pressure, msgBuffer, idx-offset);

		i = 0;
		while(HUMI_MSG[i] != 0) msgBuffer[idx++] = HUMI_MSG[i++];
		intToString(humidity, msgBuffer, idx-3);
		TSG_led_debug::debug(TSG_led_debug::OK);

		TSG_led_debug::debug(TSG_led_debug::SENDING_SMS);
		modem.sendSMS(idx);


		//flush util the the OK or ERROR returned. In the case of an error, asks for an confirmation to resend the msg
		while(true){
			status = modem.SMSReceived(buff1);
			if(status == OK_MSG) break;

			if(status == ERROR_MSG){
				error();
				/*if(!confirm())
					break;
				else
					modem.sendSMS(numBuffer, msgBuffer);*/
				break;

			}
		}
		TSG_led_debug::debug(TSG_led_debug::OK);


	}

	void readSensors(){
		temperature = 0;
		humidity = 0;
	}

	int intToString(int num, char * string, int index) {

		int i = 0,t = 0, j = 0;

		char istring[10] = "";

		while (num >= 10) {
			t = num % 10;
			num /= 10;
			istring[i++] = ('0'+t);
		}

		istring[i++] = ('0'+num);
		int tt = index;
		for (j = 0; j < i; j++){
			string[tt++] = istring[i-j-1];
		}

		return i;
	}

	/*
	void lightLed(int led){
		switch(led){
		case LED_YELLOW:
			CPU::out8(Machine::IO::PORTB, 0xFC);
		break;
		case LED_GREEN:
			CPU::out8(Machine::IO::PORTB, 0xF9);
		break;
		case LED_BOTH:
			CPU::out8(Machine::IO::PORTB, 0xF8);
		break;
		case LED_NONE:
			CPU::out8(Machine::IO::PORTB, 0xFE);
		break;
		default:
		break;
		}
	}
	 */




};

#endif

