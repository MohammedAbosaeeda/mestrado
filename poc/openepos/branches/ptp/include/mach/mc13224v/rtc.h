// EPOS-- MC13224V RTC Mediator Declarations

#ifndef __mc13224v_rtc_h
#define __mc13224v_rtc_h

#include <rtc.h>
#include <cpu.h>
#include <uart.h>
#include <utility/string.h>
#include <utility/malloc.h>
#include <utility/ostream.h>

__BEGIN_SYS

static unsigned int _Y2;
static unsigned int _M2;
static unsigned int _D2;
static unsigned int _h2;
static unsigned int _m2;
static unsigned int _s2;

// GPS ME1000RW RTC
class ME1000RW
{
public:
	enum MessageType {
		GGA, GLL, GSA, GSV, RMC, VTG
	};

private:
	UART uart;
	char msg[100];
	char utc[20];
	char data[20];
	char * date;
	int offsetCorrection;
	long time_h,time_m,time_s, data_d, data_m, data_a;
	long time_follow;

public:

	static char* parse(char* message) {
		MessageType type = get_type(message);
		char* result;
		char * date;
		switch(type) {
		case RMC : result = (char*)(malloc(18*sizeof(char)));
		date = parse_rmc(message);
		strcpy(result, date);
		break;
		}
		free(date);
		return result;
	}

	static char* parse_rmc(char* message) {
		const int TIME_SIZE = 10;
		const int DATE_SIZE = 6;
		const int TOTAL_SIZE = TIME_SIZE+DATE_SIZE+2;
		char* date = (char*)(malloc(TOTAL_SIZE*sizeof(char)));
		for(int i=7; i<17; i++) {
			date[i-7] = message[i];
		}
		int i = TIME_SIZE;
		date[i] = ' ';

		int comma_count = 0;
		while (comma_count < 9) {
			if(*message == ',')
				comma_count++;
			message++;
		}

		i++;
		while (*message != ',' & i < TOTAL_SIZE-1) {
			date[i] = *message;
			message++;
			i++;
		}
		date[TOTAL_SIZE-1] = '\0';

		return date;
	}

	static MessageType get_type(char* message) {
		char s[4];
		s[0] = message[3];
		s[1] = message[4];
		s[2] = message[5];
		s[3] = '\0';
		MessageType type;
		if(strcmp(s,"GGA") == 0)
			type = GGA;
		if(strcmp(s,"GLL") == 0)
			type = GLL;
		if(strcmp(s,"GSA") == 0)
			type = GSA;
		if(strcmp(s,"GSV") == 0)
			type = GSV;
		if(strcmp(s,"RMC") == 0)
			type = RMC;
		if(strcmp(s,"VTG") == 0)
			type = VTG;
		return type;
	}
};


class MC13224V_RTC: public RTC_Common, private ME1000RW
{
private:
	static const unsigned int EPOCH_YEAR = Traits<MC13224V_RTC>::EPOCH_YEAR;
	static const unsigned int EPOCH_DAYS = Traits<MC13224V_RTC>::EPOCH_DAYS;
	static const unsigned int EPOCH_DAY = Traits<MC13224V_RTC>::EPOCH_DAY;

public:
	MC13224V_RTC() {}

	static Date date();
	static void date(const Date & d);
	static Second seconds_since_epoch() {
		return date().to_offset(EPOCH_DAYS);
	}
	char * parseNMEA(char* message){
		char * result = parse(message);
		return result;
	}
};

__END_SYS

#endif
