#include <utility/string.h>

int memcmp(const void * a1, const void * a2, unsigned int size)
{
     unsigned char *_a1 = (unsigned char *) a1;
     unsigned char *_a2 = (unsigned char *) a2;
     unsigned int i = size;
     for (; i > 0; i--){
     	if ((*_a1) != (*_a2))
     		return ((*_a1) - (*_a2));
     	_a1++;
     	_a2++;
     }
     return 0;
}

void * memcpy(void * to, const void * from, unsigned int size)
{
     char * _to = (char *)to;
     const char * _from = (const char *)from;
     unsigned int i = 0;
     for (; i < size; i++){
     	*_to = *_from;
     	_to++;
     	_from++;
     }
     return to;
}

void * memset(void * block, int c, unsigned int size)
{
	unsigned char * _block = (unsigned char *)block;
	unsigned char _c = (unsigned char)c;
	unsigned int i;
	for (i = 0; i < size; i++)
		*_block = _c;
	return block;
}

int strcmp(const char * s1, const char * s2)
{
     unsigned char *_s1 = (unsigned char *) s1;
     unsigned char *_s2 = (unsigned char *) s2;
     while (true){
     	if ((*_s1) != (*_s2))
     		return ((*_s1) - (*_s2));
     	_s1++;
     	_s2++;
     	if ((*_s1 == '\0')&&(*_s2 == '\0')) break;
     }
     return 0;
}

int strncmp(const char * s1, const char * s2, unsigned int size)
{
     unsigned char *_s1 = (unsigned char *) s1;
     unsigned char *_s2 = (unsigned char *) s2;
     unsigned int i = size;
     for (; i > 0; i--){
     	if ((*_s1) != (*_s2))
     		return ((*_s1) - (*_s2));
     	_s1++;
     	_s2++;
     }
     return 0;
}

char * strcpy(char * to, const char * from)
{
     char * _to = to;
     const char * _from = from;
     while(true){
     	*_to = *_from;
		if (*_to == '\0') break;
     	_to++;
     	_from++;
     }
     return to;
}

char * strncpy(char * to, const char * from, unsigned int size)
{
     char * _to = to;
     const char * _from = from;
     unsigned int i = 0;
     while ((i < size)&&(*_from != '\0')) {
        *_to = *_from;
     	_to++;
     	_from++;
     	i++;
     }
     while (i < size){
     	*_to = '\0';
     	_to++;
     	i++;
     }
     return to;
}

unsigned int strlen(const char * s)
{
	const char * _s = s;
	unsigned int counter = 0;
	while(*_s != '\0'){
		_s++;
		counter++;
	}
	return counter;
}

char * strstr (const char *haystack, const char *needle)
{
	if (*needle == '\0') return (char *)haystack;
	const char * _haystack = haystack;
	const char * _needle = needle;
	char * result;
	while (true){
		while (*_haystack != *_needle){
			if (*_haystack == '\0'){
				result = 0;
				break;
			}
			_haystack++;
		}
		result = (char *)_haystack;
		while (*_haystack == *_needle){
			if (*_needle == '\0')
				return result;
			_haystack++;
			_needle++;
		}
		_needle = needle;
	}
	return result;
}

char * strchr (const char *string, int c)
{
	const char * _string = string;
	while (*_string != c){
		_string++;
		if (*_string == '\0') return 0;
	}
	return (char *)_string;
}

char * strrchr (const char *string, int c)
{
	const char * _string = string;
	int size = 0;
	while (*_string != '\0'){
		_string++;
		size++;
	}
	size++;
	int i;
	for (i = size; i > 0; i--){
		if (*_string == c)
			return (char *)_string;
		_string--;
	}
	return 0;
}

char * strcat (char * to, const char * from)
{
	char * _to = to;
	const char * _from = from;
	while (*_to != '\0') _to++;
	while (*_from != '\0'){
		*_to = *_from;
		_to++;
		_from++;
	}
	*_to = '\0';
	return to;
}

unsigned int strcspn (const char *string, const char *stopset)
{
	const char * _string = string;
	int length = 0;
	while (*_string != '\0'){
		if (strchr(stopset,*_string) != 0) return length;
		length++;
		_string++;
	}
	return length;
}

char * strncat (char * to, const char * from, unsigned int size)
{
	char * _to = to;
	const char * _from = from;
	while (*_to != '\0') _to++;
	unsigned int _size = 0;
	while (*_from != '\0'){
		if (_size == size) break;
		*_to = *_from;
		_to++;
		_from++;
		_size++;
	}
	*_to = '\0';
	return to;
}

void * memchr (const void *block, int c, unsigned int size)
{
	const unsigned char * _block = (const unsigned char *)block;
	unsigned char _c = (unsigned char)c;
	unsigned int _size = 0;
	while ((*_block != '\0')&&(_size < size)){
		if (*_block == _c) return (void *)_block;
		_block++;
		_size++;
	}
	return 0;
}

char * strpbrk (const char *string, const char *stopset)
{
	const char * _string = string;
	while (*_string != '\0'){
		if (strchr(stopset,*_string) != 0) return (char *)_string;
		_string++;
	}
	return 0;
}
