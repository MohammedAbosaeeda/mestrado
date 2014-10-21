// EPOS-- Thread Test Program

#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>

__USING_SYS

int func_a(void);
int func_b(void);

Thread a = Thread(&func_a);
Thread b = Thread(&func_b); 

OStream cout;

int main()
{
    cout << "Thread test\n";

    cout << "I'm the first thread of the first task created in the system.\n";
    cout << "I'll now create two threads and then wait for them to finish ...\n";

    int status_a = a.join();
    int status_b = b.join();

    cout << "Thread A exited with status " << status_a 
	 << " and thread B exited with status " << status_b << "\n";

    cout << "I'm also done, bye!\n";

    return 0;
}
int func_a(void)
{
	cout << "I am AAA\n";
    return 'A';   
}

int func_b(void)
{
    cout << "I am BBB\n";
    return 'B';   
}
