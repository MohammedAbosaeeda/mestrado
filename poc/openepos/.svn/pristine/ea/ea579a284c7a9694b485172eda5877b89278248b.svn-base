// EPOS-- Thread Test Program

#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>

__USING_SYS

const int iterations = 2;

int func_a(void);
int func_b(void);

Thread * a;
Thread * b;
Thread * m;

OStream cout;

int main()
{
    cout << "Thread test\n";

    m = Thread::self();

    cout << "I'm the first thread of the first task created in the system.\n";
    cout << "I'll now create two threads and then wait for them to finish ...\n";
    cout << "b will be created suspended ...\n";

    a = new Thread(&func_a);
    b = new Thread(&func_b, Thread::SUSPENDED);    

    cout << "a joing with main thread ...\n";
    int status_a = a->join();
    cout << "b joing with main thread ...\n";
    cout << "now I'll sleep for 500000 and resume b ...\n";
    Alarm::delay(500000);
    cout << "now I'll resume b ...\n";
    b->resume();
    
    int status_b = b->join();

    cout << "Thread A exited with status " << status_a 
	 << " and thread B exited with status " << status_b << "\n";

    delete a;
    delete b;

    cout << "I'm also done, bye!\n";
    delete m;

    return 0;
}

int func_a(void)
{
    for(int i = iterations; i > 0; i--) {
	for(int i = 0; i < 79; i++)
	    cout << "a";
	cout << "\n";
	Alarm::delay(500000);
    }

    return 'A';   
}

int func_b(void)
{
    for(int i = iterations; i > 0; i--) {
	for(int i = 0; i < 79; i++)
	    cout << "b";
	cout << "\n";
	Alarm::delay(500000);
    }

    return 'B';   
}
