#include <utility/time.h>
#include <utility/stdlib.h>
#include <utility/ctype.h>
#include <utility/malloc.h>
#include <clock.h>
#include <rtc.h>

__USING_SYS

Clock c;
struct tm ts;
unsigned int dpm[12] = {31,28,31,30,31,30,31,31,30,31,30,31};
char * a[7] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
char * A[7] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
char * b[12] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
char * B[12] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
int timezone;
unsigned int decimal;

time_t time(time_t* tp)
{
	time_t now = c.now();
	if (tp != 0)
		*tp = now;
	return now;
}

time_t difftime(time_t time2, time_t time1)
{
	return time2-time1;
}

time_t mktime(struct tm* tp)
{
    if((!(tp->tm_year % 4) && (tp->tm_year % 100)) || !(tp->tm_year % 400))
		dpm[1] = 29;

	struct tm tp2 = *tp;
	(tp2.tm_mon)++;
	
	if ((tp->tm_year) < 70)					/* EPOS only works with numbers between 1970 and 2069. */
		tp2.tm_year = 70;					//											           //
	else if ((tp->tm_year) > 169)			//											           //
		tp2.tm_year = 69;					/* EPOS only works with numbers between 1970 and 2069. */

	RTC::Date d(tp2.tm_year, tp2.tm_mon, tp2.tm_mday, tp2.tm_hour, tp2.tm_min, tp2.tm_sec);
	return d;
}

struct tm* gmtime(const time_t* tp)
{
	RTC::Date d(*tp);
	ts.tm_year = d.year(); //TODO Qual o range desse ano? 1970-2069? Se sim, e se eu criar um Date fora do range?
	// Espera-se que valor seja positivo. Ex: 1990, 2010, etc... TODO
	ts.tm_mon = d.month()-1;
	ts.tm_mday = d.day();
	ts.tm_hour = d.hour();
	ts.tm_min = d.minute();
	ts.tm_sec = d.second();
    if((!(ts.tm_year % 4) && (ts.tm_year % 100)) || !(ts.tm_year % 400))
		dpm[1] = 29;
	int i = 0;
	int j = ts.tm_mon;
	int sum = 0;
	for (; i < j; i++) sum += dpm[i];
	sum += ts.tm_mday;
	ts.tm_yday = sum;
	ts.tm_isdst = 0;
	time_t t = *tp;
	i = 60*60*24;
	while (t%i != 0) t--;
	t /= i;
	j = 0;
	while (t%7 != 0){
		t--;
		j++;
	}
	j = j + 4;
	if (j > 6) j = j - 7;
	ts.tm_wday = j;
	ts.tm_tzone = "";
	timezone = 0;
	decimal = 0;
	return &ts;
}

struct tm* localtime(const time_t* tp)
{
	ts = *(gmtime(tp));
	if (timezone > 0){
		if (decimal > 0){
			ts.tm_min = ts.tm_min + decimal;
			if (ts.tm_min > 59){
				ts.tm_min = ts.tm_min - 60;
				ts.tm_hour++;
				if (ts.tm_hour > 23){
					ts.tm_hour = ts.tm_hour - 24;
					ts.tm_mday++;
					if (ts.tm_mday > dpm[ts.tm_mon]){
						ts.tm_mday = 1;
						ts.tm_mon++;
						if (ts.tm_mon > 11){
							ts.tm_mon = 1;
							ts.tm_year++;
						}
					}
				}
			}
		}
		ts.tm_hour = ts.tm_hour + timezone;
		if (ts.tm_hour > 23){
			ts.tm_hour = ts.tm_hour - 24;
			ts.tm_mday++;
			if (ts.tm_mday > dpm[ts.tm_mon]){
				ts.tm_mday = 1;
				ts.tm_mon++;
				if (ts.tm_mon > 11){
					ts.tm_mon = 1;
					ts.tm_year++;
				}
			}
		}
	}else if (timezone < 0){
		int newMin, newHour, newMDay, newMon;
		if (decimal > 0){
			newMin = ts.tm_min - decimal;
			if (newMin < 0){
				ts.tm_min = 60 + newMin;
				newHour = ts.tm_hour - 1;
				if (newHour < 0){
					ts.tm_hour = 23;
					newMDay = ts.tm_mday - 1;
					if (newMDay < 0){
						newMon = ts.tm_mon - 1;
						if (newMon < 0){
							ts.tm_mon = 12;
							ts.tm_year--;
						}else
							ts.tm_mon = newMon;
						ts.tm_mday = dpm[ts.tm_mon];
					}else
						ts.tm_mday = newMDay;
				}else
					ts.tm_hour = newHour;
			}else
				ts.tm_min = newMin;
		}
		newHour = ts.tm_hour + timezone;
		if (newHour < 0){
			ts.tm_hour = 24 + newHour;
			newMDay = ts.tm_mday - 1;
			if (newMDay < 0){
				newMon = ts.tm_mon - 1;
				if (newMon < 0){
					ts.tm_mon = 12;
					ts.tm_year--;
				}else
					ts.tm_mon = newMon;
				ts.tm_mday = dpm[ts.tm_mon];
			}else
				ts.tm_mday = newMDay;
		}else
			ts.tm_hour = newHour;
	}
	return &ts;
}

unsigned int strftime(char* str, unsigned int size, const char* fmt, const struct tm* tp)
{
	char * s;
	if (str != 0)
		s = str;
	else
		s = (char*) malloc(sizeof(char) * (size+1));
	int i = 0;
	unsigned int j = 0;
	unsigned int k;
	unsigned int aux = 0;
	char * pad;
	int uppercase;
	char * s2;
	char c;
	int l, year;
	for (;;){
		pad = 0;
		uppercase = 0;
		while ((fmt[i] != '%')&&(fmt[i] != '\0')){
			s[j] = fmt[i];
			j++;
			if (j >= size) return 0;
			i++;
		}
		if (fmt[i] == '\0'){
			s[j] = '\0';
			break;
		}
		i++;
		k = 1;
		while (k){
			switch(fmt[i]){
			case '_':
				pad = " ";
				i++;
				break;
			case '-':
				pad = "";
				i++;
				break;
			case '0':
				pad = "0";
				i++;
				break;
			case '^':
				uppercase = 1;
				i++;
				break;
			default:
				k = 0;
				break;
			}
		}
		switch(fmt[i]){
		case 'a':
			k = 0;
			aux = tp->tm_wday;
			while (k < 3){
				s[j] = (uppercase ? (toupper(a[aux][k])) : (a[aux][k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'A':
			k = 0;
			aux = tp->tm_wday;
			while (A[aux][k] != '\0'){
				s[j] = (uppercase ? (toupper(A[aux][k])) : (A[aux][k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'b':
		case 'h':
			k = 0;
			aux = tp->tm_mon;
			while (k < 3){
				s[j] = (uppercase ? (toupper(b[aux][k])) : (b[aux][k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'B':
			k = 0;
			aux = tp->tm_mon;
			while (B[aux][k] != '\0'){
				s[j] = (uppercase ? (toupper(B[aux][k])) : (B[aux][k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'c':
			k = 0;
			aux = tp->tm_wday;
			while (k < 3){
				s[j] = (uppercase ? (toupper(a[aux][k])) : (a[aux][k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ' ';
			j++;
			if (j >= size) return 0;
			k = 0;
			aux = tp->tm_mon;
			while (k < 3){
				s[j] = (uppercase ? (toupper(b[aux][k])) : (b[aux][k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ' ';
			j++;
			if (j >= size) return 0;
			s2 = itoa(tp->tm_mday,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ' ';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_hour;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_min;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_sec;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ' ';
			j++;
			if (j >= size) return 0;
			s2 = itoa(tp->tm_year,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'C':
			aux = tp->tm_year;
			k = 1;
			while (true){
				if (aux > (k*100))
					k++;
				else
					break;
			}
			k--; // LIBC considera 2010 como século 20.
			s2 = itoa(k,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'd':
			aux = tp->tm_mday;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'D':
		case 'x':
			aux = tp->tm_mon;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = '/';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_mday;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = '/';
			j++;
			if (j >= size) return 0;
			aux = (tp->tm_year) % 100;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'e':
			s2 = itoa(tp->tm_mday,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'F':
			s2 = itoa(tp->tm_year,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = '-';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_mon;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = '-';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_mday;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'g':
			k = tp->tm_yday;
			l = 0;
			while ((k % 7) != 1){
				k++;
				l++;
			}
			k = tp->tm_yday;
			aux = tp->tm_wday + l;
			if (aux > 6) aux = aux - 7;
			if (aux < 5)
				l = k + aux - 1;
			else if (aux == 5)
				l = k - 3;
			else
				l = k - 2;
			year = tp->tm_year;
			if (l < 1)
				year--;
			else{
				k = l;
				l /= 7;
				if (l%7 != 0)
					l++;
				if (l == 53)
					if ((k + 31 - tp->tm_mday) < 368)
						year++;
			}
			if (year < 100){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			year %= 100;
			s2 = itoa(year,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'G':
			k = tp->tm_yday;
			l = 0;
			while ((k % 7) != 1){
				k++;
				l++;
			}
			k = tp->tm_yday;
			aux = tp->tm_wday + l;
			if (aux > 6) aux = aux - 7;
			if (aux < 5)
				l = k + aux - 1;
			else if (aux == 5)
				l = k - 3;
			else
				l = k - 2;
			year = tp->tm_year;
			if (l < 1)
				year--;
			else{
				k = l;
				l /= 7;
				if (l%7 != 0)
					l++;
				if (l == 53)
					if ((k + 31 - tp->tm_mday) < 368)
						year++;
			}
			s2 = itoa(year,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'H':
			aux = tp->tm_hour;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'I':
			aux = tp->tm_hour;
			if (aux == 0) aux = 24;
			if (aux > 12) aux -= 12;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'j':
			aux = tp->tm_yday;
			if (aux < 100){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
					if (aux < 10){
						s[j] = pad[0];
						j++;
						if (j >= size) return 0;
					}
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'k':
			s2 = itoa(tp->tm_hour,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'l':
			aux = tp->tm_hour;
			if (aux == 0) aux = 24;
			if (aux > 12) aux -= 12;
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'm':
			aux = tp->tm_mon;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'M':
			aux = tp->tm_min;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'n':
			s[j] = '\n';
			j++;
			if (j >= size) return 0;
			break;
		case 'p':
			if (tp->tm_hour < 12)
				s[j] = 'A';
			else
				s[j] = 'P';
			j++;
			if (j >= size) return 0;
			s[j] = 'M';
			j++;
			if (j >= size) return 0;
			break;
		case 'P':
			if (tp->tm_hour < 12)
				s[j] = 'a';
			else
				s[j] = 'p';
			j++;
			if (j >= size) return 0;
			s[j] = 'm';
			j++;
			if (j >= size) return 0;
			break;
		case 'r':
			aux = tp->tm_hour;
			if (aux < 12)
				c = 'A';
			else
				c = 'P';
			if (aux == 0) aux = 24;
			if (aux > 12) aux -= 12;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_min;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_sec;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ' ';
			j++;
			if (j >= size) return 0;
			s[j] = c;
			j++;
			if (j >= size) return 0;
			s[j] = 'M';
			j++;
			if (j >= size) return 0;
			break;
		case 'R':
			aux = tp->tm_hour;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_min;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 's':
			k = 0;
			s2 = itoa(mktime((struct tm*)tp),10);
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'S':
			aux = tp->tm_sec;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 't':
			s[j] = '\t';
			j++;
			if (j >= size) return 0;
			break;
		case 'T':
		case 'X':
			aux = tp->tm_hour;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_min;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			s[j] = ':';
			j++;
			if (j >= size) return 0;
			aux = tp->tm_sec;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'u':
			aux = tp->tm_wday;
			if (aux == 0)
				aux = 7;
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'U':
			k = tp->tm_yday;
			l = 0;
			while ((k % 7) != 1){
				k++;
				l++;
			}
			k = tp->tm_yday;
			aux = tp->tm_wday + l;
			if (aux > 6) aux = aux - 7;
			if (aux == 0) aux = 7;
			l = k + aux - 7;
			if (l < 1)
				l = 0;
			else{
				k = l;
				l /= 7;
				if (l%7 != 0)
					l++;
			}
			if (l < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(l,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'V':
			k = tp->tm_yday;
			l = 0;
			while ((k % 7) != 1){
				k++;
				l++;
			}
			k = tp->tm_yday;
			aux = tp->tm_wday + l;
			if (aux > 6) aux = aux - 7;
			int m;
			if (aux == 0)
				m = 6;
			else
				m = aux - 1;
			if (aux < 5)
				l = k + aux - 1;
			else if (aux == 5)
				l = k - 3;
			else
				l = k - 2;
			if (l < 1){
				aux = 365;
				if (!(((tp->tm_year) - 1)%4)) aux++;
				k = aux;
				l = 0;
				while ((k % 7) != 1){
					k++;
					l++;
				}
				k = aux;
				aux = m + l;
				if (aux > 6) aux = aux - 7;
				if (aux < 5)
					l = k + aux - 1;
				else if (aux == 5)
					l = k - 3;
				else
					l = k - 2;
				if (l >= 368)
					l = 53;
				else
					l = 52;
			}else{
				k = l;
				l /= 7;
				if (l%7 != 0)
					l++;
				if (l == 53)
					if ((k + 31 - tp->tm_mday) < 368)
						l = 1;
			}
			if (l < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(l,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'w':
			s2 = itoa(tp->tm_wday,10);
			s[j] = s2[0];
			j++;
			if (j >= size) return 0;
			break;
		case 'W':
			k = tp->tm_yday;
			l = 0;
			while ((k % 7) != 1){
				k++;
				l++;
			}
			k = tp->tm_yday;
			aux = tp->tm_wday + l;
			if (aux > 6) aux = aux - 7;
			if (aux < 2) aux = aux + 7;
			l = k + aux - 8;
			if (l < 1)
				l = 0;
			else{
				k = l;
				l /= 7;
				if (l%7 != 0)
					l++;
			}
			if (l < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(l,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'y':
			aux = (tp->tm_year) % 100;
			if (aux < 10){
				if (pad == 0) pad = "0";
				if (pad[0] != '\0'){
					s[j] = pad[0];
					j++;
					if (j >= size) return 0;
				}
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'Y':
			s2 = itoa(tp->tm_year,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'z':
			aux = timezone + tp->tm_isdst;
			if (aux < 0){
				s[j] = '-';
				aux = -aux;
			}else
				s[j] = '+';
			j++;
			if (j >= size) return 0;
			if (aux < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(aux,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			if (decimal < 10){
				s[j] = '0';
				j++;
				if (j >= size) return 0;
			}
			s2 = itoa(decimal,10);
			k = 0;
			while (s2[k] != '\0'){
				s[j] = s2[k];
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case 'Z':
			s2 = tp->tm_tzone;
			k = 0;
			while (s2[k] != '\0'){
				s[j] = (uppercase ? (toupper(s2[k])) : (s2[k]));
				j++;
				if (j >= size) return 0;
				k++;
			}
			break;
		case '%': {
			s[j] = '%';
			j++;
			if (j >= size) return 0;
			break;
		}
		default: {
			s[j] = '%';
			j++;
			if (j >= size) return 0;
			s[j] = fmt[i];
			j++;
			if (j >= size) return 0;
			break;
		}
		}
		i++;
	}
	return j;
}
