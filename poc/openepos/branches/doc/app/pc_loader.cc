/*! @file
 *  @brief EPOS Default application for PC Machine
 *
 *  CVS Log for this file:
 *  \verbinclude pc_loader_cc.log
 */
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
