#include <utility/ctype.h>

int isalnum(int c)
{
	return (isalpha(c) || isdigit(c));
}

int isalpha(int c)
{
	return (isupper(c) || islower(c));
}

int iscntrl(int c)
{
	return ((c >= 0) && ((c <= 0x1F) || (c == 0x7f))) ;
}

int isdigit(int c)
{
	return (c >= '0' && c <= '9');
}

int isgraph(int c)
{
	return (c != ' ' && isprint(c));
}

int islower(int c)
{
	return (c >=  'a' && c <= 'z');
}

int isprint(int c)
{
	return (c >= ' ' && c <= '~');
}

int ispunct(int c)
{
	return ((c > ' ' && c <= '~') && !isalnum(c));
}

int isspace(int c)
{
	return (c ==  ' ' || c == '\f' || c == '\n' || c == '\r' || c == '\t' || c == '\v');
}

int isupper(int c)
{
	return (c >=  'A' && c <= 'Z');
}

int isxdigit(int c)
{
	return (isxupper(c) || isxlower(c));
}

int tolower(int c)
{
	return (isupper(c) ? ( c - 'A' + 'a') : (c));
}

int toupper(int c)
{
	return (islower(c) ? (c - 'a' + 'A') : (c));
}

int isxlower(int c)
{
	return (isdigit(c) || (c >= 'a' && c <= 'f'));
}

int isxupper(int c)
{
	return (isdigit(c) || (c >= 'A' && c <= 'F'));
}
