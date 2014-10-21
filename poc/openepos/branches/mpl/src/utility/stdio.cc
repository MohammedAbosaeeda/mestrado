#include <utility/stdio.h>
#include <utility/stdlib.h>

int nprintf(char *str, const char *format, int n)
{
	const char * va_list[1];
	va_list[0] = itoa(n,10);
	return sprintf(str,format,va_list);
}

//va_list contains the variable number of arguments, as an array of strings.
int sprintf(char* s, const char* fmt, const char ** va_list)
{
	int aux = 0;
	int i = 0;
	int j = 0;
	int k;
	int flag = 0;
	for (;;){
		while ((fmt[i] != '%')&&(fmt[i] != '\0')&&(!flag)){
			s[j] = fmt[i];
			j++;
			i++;
		}
		if (fmt[i] == '\0'){
			s[j] = '\0';
			break;
		}
		if (!flag) i++;
		switch(fmt[i]){
			case 'l': {
				flag = 1;
				break;
			}
			case 's':
			case 'c': 
			case 'p':
			case 'd': {
				flag = 0;
				k = 0;
				while (va_list[aux][k] != '\0'){
					s[j] = va_list[aux][k];
					j++;
					k++;
				}
				aux++;
				break;
			}
			case '%': {
				flag = 0;
				s[j] = '%';
				j++;
				break;
			}
			default: {
				flag = 0;
				s[j] = '%';
				j++;
				s[j] = fmt[i];
				j++;
				break;
			}
		}
		i++;
	}
	return j;
}
