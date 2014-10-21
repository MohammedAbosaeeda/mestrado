// EPOS-- MC13224V Default Application

#include <thread.h>

__USING_SYS

OStream cout;

unsigned int iterations = 2000;

int test_thread1(){
	for(int i = 1; i <= iterations; i++){
		cout << "test_thread 1: " << i << "\n";
	}
	cout << "test_thread 1 exiting...\n";
}

int test_thread2(){
	for(int i = 1; i <= iterations; i++){
		cout << "test_thread 2: " << i << "\n";
	}
	cout << "test_thread 2 exiting...\n";
}

int main() {

	Thread *m = new Thread(&test_thread2);
	Thread *n = new Thread(&test_thread1);

	m->join();
	n->join();

    cout << "Back to the main thread! I'm done!\n";

	return 0;
}

