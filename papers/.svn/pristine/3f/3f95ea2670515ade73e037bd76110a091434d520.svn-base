/*
 * ModemInterface.h
 *
 *  Created on: 15/10/2008
 *      Author: tiago
 */

#include <../app/tsg/SerialInterface.h>
#include <alarm.h>
#include <thread.h>
#include "led_debug.h"

#ifndef MODEMINTERFACE_H_
#define MODEMINTERFACE_H_

__USING_SYS

#define SMS_MSG 0
#define ERROR_MSG 2
#define OTHER_MSG 3
#define OK_MSG 4
#define PROMPT_MSG 5

#define MODEM_BUFFER_SIZE 64

static char CMD_AT[] = "AT";
static char CMD_TXT_FORMAT[] = "AT+CMGF=1";
static char CMD_SMS_FORMAT[] = "AT+CNMI=,2";
static char CMD_SND_SMS[] = "AT+CMGS=";
static char CMD_ACK[] = "AT+CNMA";

class ModemInterface{

private:
	char BUFFER[MODEM_BUFFER_SIZE];
	SerialInterface serial;


public:

	ModemInterface()
		:serial()
	{

		//initialize the modem - keep sending AT until the modem sends OK
		TSG_led_debug::debug(TSG_led_debug::SENDING_CMD_AT);
		serial.putLine(CMD_AT);
		while(true) if(this->SMSReceived(0) == OK_MSG) break;
		TSG_led_debug::debug(TSG_led_debug::OK);

		TSG_led_debug::debug(TSG_led_debug::SENDING_CMD_TXT_AND_SMS_FORMAT);
		//sets the commands format to text format
		serial.putLine(CMD_TXT_FORMAT);
		while(true) if(this->SMSReceived(0) == OK_MSG) break;

		//sets the sms notification format
		serial.putLine(CMD_SMS_FORMAT);
		while(true) if(this->SMSReceived(0) == OK_MSG) break;

		TSG_led_debug::debug(TSG_led_debug::OK);

		//3 OKs
		//char count = 0;
		//while(count != 3) if(this->SMSReceived(0) == OK_MSG) ++count;
	}

	char * getBuffer(){
		return BUFFER;
	}

	int prepareSMS(char *number){
		int i,idxBuf = 0;

		i = 0;
		while(CMD_SND_SMS[i] != 0) BUFFER[idxBuf++] = CMD_SND_SMS[i++];

		BUFFER[idxBuf++] = '\"';
		i = 0;
		while(number[i] != 0) BUFFER[idxBuf++] = number[i++];
		BUFFER[idxBuf++] = '\"';

		BUFFER[idxBuf++] = '\r';
		BUFFER[idxBuf++] = '\n"'; //it WILL NOT work without the "

		return idxBuf;
	}


	/*
	 * Sends a msg to the specified number. The number needs to be in the format "+554832244820"
	 */
	void sendSMS(int idxBuf){
	//void sendSMS(char *number, char *msg){

		//assemble the send sms cmd like the example below:
		//AT+CMGS="+554899515195"$0D$0AxxxxxxxxxxxxxxxxxxxxxxxxxMensagem.$1A

		/*int i,idxBuf = 0;

		i = 0;
		while(CMD_SND_SMS[i] != 0) BUFFER[idxBuf++] = CMD_SND_SMS[i++];

		BUFFER[idxBuf++] = '\"';
		i = 0;
		while(number[i] != 0) BUFFER[idxBuf++] = number[i++];
		BUFFER[idxBuf++] = '\"';

		BUFFER[idxBuf++] = '\r';
		BUFFER[idxBuf++] = '\n"';*/

		/*i = 0;
		while(msg[i] != 0) BUFFER[idxBuf++] = msg[i++];*/

		BUFFER[idxBuf++] = static_cast<char>(26); //ctr-z

		BUFFER[idxBuf++] = 0;

		serial.put(BUFFER);



		//=============================================================
		//=============================================================


		/*serial.putLine("AT+CMGS=\"+554899515195\"");
		while(this->SMSReceived(0, 0) != PROMPT_MSG);
		serial.put(msg);
		BUFFER[0] = static_cast<char>(26); //ctr-z
		BUFFER[1] = 0;
		serial.putLine(BUFFER);*/

		//========================================================================

		/*serial.put(CMD_SND_SMS);

		serial.put("\"");
		serial.put(number);
		serial.put("\"");

		serial.put("\r\n ");

		serial.put(msg);

		BUFFER[0] = static_cast<char>(26); //ctr-z
		BUFFER[1] = 0;

		serial.put(BUFFER);*/


	}




	/*
 	 * Checks if the current serial output line is a sms notification response. If it does,
	 * return 0 and fill the given buffers with the sms content, if it is a error msg return 1, else return 3.
	 */
	int SMSReceived(char *numBuffer){

		//for now, just need to parse the first part of the modem response:
		//+CMT: "+554899515195",

		int iBuf = 0;

		serial.getLine(BUFFER, MODEM_BUFFER_SIZE);

		if(
				(BUFFER[iBuf++] == '+') &&
				(BUFFER[iBuf++] == 'C') &&
				(BUFFER[iBuf++] == 'M') &&
				(BUFFER[iBuf++] == 'T') &&
				(BUFFER[iBuf++] == ':')
		){
			while(BUFFER[iBuf] == ' ') ++iBuf;
			if(BUFFER[iBuf++] == '"'){
				int i;
				for(i = 0; BUFFER[iBuf] != '"'; ++i)
					numBuffer[i] = BUFFER[iBuf++];
				numBuffer[i] = 0;

				if((BUFFER[iBuf++] == '"') && (BUFFER[iBuf++] == ',')){
					//continue here if further parse is necessary

					if(!formatNumber(numBuffer))
						return ERROR_MSG;
						//sends the acknowledgement msg
					serial.putLine(CMD_ACK);

					return SMS_MSG;
				}
			}
		}

		iBuf = 0;
		if(		(BUFFER[iBuf++] == 'E') &&
				(BUFFER[iBuf++] == 'R') &&
				(BUFFER[iBuf++] == 'R') &&
				(BUFFER[iBuf++] == 'O') &&
				(BUFFER[iBuf++] == 'R')
		){
			return ERROR_MSG;
		}

		iBuf = 0;
		if(		(BUFFER[iBuf++] == 'O') &&
				(BUFFER[iBuf++] == 'K')
		){
			return OK_MSG;
		}

		iBuf = 0;
		if(		(BUFFER[iBuf++] == '>')){
			return PROMPT_MSG;
		}

		return OTHER_MSG;
	}


private:

	bool formatNumber(char* num){
		int i = 0;
		char temp[10];
		int size = 0;
		while(num[i++] != 0) ++size;

		i = 0;
		//starts with +55
		if(		(num[i++] == '+') &&
				(num[i++] == '5') &&
				(num[i++] == '5')){
			//must have the two area digits + the eight digits number
			return (size == 13)?true:false;
		}

		i = 0;
		//starts with 0 means that the format is 0aannnnnnnn
		if (num[i++] == '0'){
			//format if it's valid
			if(size == 11){
				//char temp[10];
				for(int j = 0; j < 10; ++j,++i)
					temp[j] = num[i];
				num[0] = '+'; num[1] = '5'; num[2] = '5';
				i = 3;
				for(int j = 0; j < 10; ++j, ++i)
					num[i] = temp[j];
				num[i] = 0;
				return true;
			}
			else
				return false;
		}

		i = 0;
		//if doesn't start with 0 and there is no +55 then the format must be nnnnnnnn
		if(size == 8){
			//char temp[8];
			for(int j = 0; j < 8; ++j,++i)
				temp[j] = num[i];
			//assumes that the area code is 48
			num[0] = '+'; num[1] = '5'; num[2] = '5'; num[3] = '4'; num[4] = '8';
			i = 5;
			for(int j = 0; j < 8; ++j, ++i)
				num[i] = temp[j];
			num[i] = 0;
			return true;
		}

		i = 0;
		//if it doesn't start with + and there is 10 digits then the format must be aannnnnnnn
		if((size == 10) && (num[i] != '+')){
			//char temp[10];
			for(int j = 0; j < 10; ++j,++i)
				temp[j] = num[i];
			num[0] = '+'; num[1] = '5'; num[2] = '5';
			i = 3;
			for(int j = 0; j < 10; ++j, ++i)
				num[i] = temp[j];
			num[i] = 0;
			return true;
		}
		else
			return false;
	}


};



#endif /* MODEMINTERFACE_H_ */

