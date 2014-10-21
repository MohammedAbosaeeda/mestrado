// SoftMIPS Default Application (Test)

#include <utility/ostream.h>

#include <timer.h>
#include <alarm.h>
#include <thread.h>

#include <arch/mips32/plasma.h>
__USING_SYS


const int iterations = 10;

int func_a(void);
int func_b(void);
int func_infinite_a();
int func_infinite_b();
int func_infinite_c();
int func_infinite_d();

Thread * a;
Thread * b;
Thread * m;

OStream cout;

void printchar(char ch)
{
	while((*reinterpret_cast<volatile unsigned int*>(IRQ_STATUS) & IRQ_UART_WRITE_AVAILABLE) == 0);
	
	*reinterpret_cast<volatile unsigned int *>(UART_WRITE) = ch;
}

void printhex2(int i)
{
	printchar("0123456789abcdef"[(i >> 4) & 15]);
	printchar("0123456789abcdef"[i & 15]);
}

void printhex4(int i)
{
	printhex2(i >> 8);
	printhex2(i & 255);
}

void printhex8(int i)
{
	printhex4(i >> 16);
	printhex4(i & 0x0000FFFF);
}

int main() 
{
	
    cout << "I'm just a dummy test application.\n";
	
	m = Thread::self();
	
	a = new Thread(&func_a);
	b = new Thread(&func_b);
	
	/*a = new Thread(&func_infinite_a);
	a = new Thread(&func_infinite_b);
	a = new Thread(&func_infinite_c);
	a = new Thread(&func_infinite_d);*/
	int status_a = a->join();
	int status_b = b->join();
	
	delete a;
	delete b;

	cout << "Mask = " << (void *) *reinterpret_cast<volatile unsigned int*>(0x20000010) << "\n";
	cout << "Status = " << (void *) *reinterpret_cast<volatile unsigned int*>(0x20000020) << "\n";
	cout << "Looooop infinito ------------------\n";
	//for(;;) cout << "I";
	delete m;
	//for(;;);
	//delete Thread::self();

    return 0;
}


int func_infinite_a()
{
	while (true) cout << 'a';
}
int func_infinite_b()
{
	while (true) cout << 'b';
}
int func_infinite_c()
{
	while (true) cout << 'c';
}
int func_infinite_d()
{
	while (true) cout << 'd';
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
