// PC_LOADER
//
// Desc: LOADER is created by INIT as the first process when the system
//	 is configured to support multiple tasks. It creates all
//	 application tasks from the image received in ??? and then waits
//	 wait for them to finish.
//
// Author: guto
// Documentation: $EPOS/doc/loader			Date: 22 May 2003

#include <utility/ostream.h>
#include <display.h>

__USING_SYS

int main() 
{
    OStream cout;

    cout << "I'm just a dummy test application.\n";
    cout << "Since I have nothing better to do, I'll start an ubound memory test (that is, I'll even test the memory your computer doesn't have :-)!!\n";
     cout << "Testing memory: ";

     Display disp;
     for(char * ptr = (char *)(1024*1024);
	 ptr < (char *)0xffffffff;
	 ptr+= 64) {
	 disp.position(-1, 16);
	 cout << (void *)ptr;
	 *ptr = 'G';
	 if(*ptr != 'G') {
	     cout << "\nLast memory position at " << ptr << "!\n";
	     break;
	 }
     }

    return 0;
}
