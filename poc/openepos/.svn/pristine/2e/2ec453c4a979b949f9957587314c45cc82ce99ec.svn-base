// EPOS Periodic Thread Abstraction Test Program

#include <utility/ostream.h>
#include <periodic_thread.h>
#include <chronometer.h>
#include <semaphore.h>

__USING_SYS

#define THREADS 8
Periodic_Thread * threads[THREADS];
const int iterations = 10;
const long period_a = 100; // ms
const long period_b = 200; // ms
const long period_c = 400; // ms

int func(char);
int func_a(void);
int func_b(void);
int func_c(void);
long max(long a, long b, long c) { return ((a >= b) && (a >= c)) ? a : ((b >= a) && (b >= c) ? b : c); }

OStream cout;
Semaphore s;

int main()
{
    cout << "Periodic Thread Abstraction Test\n";

    cout << "\nThis test consists in creating three periodic threads as follows:\n";
    cout << "  Thread 1 prints \"a\" every " << period_a << " ms;\n";
    cout << "  Thread 2 prints \"b\" every " << period_b << " ms;\n";
    cout << "  Thread 3 prints \"c\" every " << period_c << " ms.\n";

    /*Periodic_Thread thread_a(&func, 'a',period_a * 1000, iterations);
    Periodic_Thread thread_b(&func, 'b', period_b * 1000, iterations);
    Periodic_Thread thread_c(&func, 'c', period_c * 1000, iterations);*/
    long period = 100;
    for(int i = 0; i <  THREADS; i = i + 2) {
        threads[i] = new Periodic_Thread(&func, (char)('a' + i), period * 1000, iterations);
        threads[i+1] = new Periodic_Thread(&func, (char)('a' + i + 1), period * 1000, iterations);
        period *= 2;
    }

    cout << "Threads have been created. I'll wait for them to finish...\n\n";

    Chronometer chrono;
    chrono.start();

    for(int i = 0; i <  THREADS; i++)
      threads[i]->join();
    
    /*int status_a = thread_a.join();
    int status_b = thread_b.join();
    int status_c = thread_c.join();*/

    chrono.stop();

    /*cout << "\n\nThread A exited with status " << status_a 
  	 << ", thread B exited with status " << status_b
  	 << " and thread C exited with status " << status_c << ".\n";*/

    cout << "\nThe estimated time to run the test was " << max(period_a, period_b, period_c) * iterations << " ms. The measured time was " << chrono.read() / 1000 <<" ms!\n";

    cout << "I'm also done, bye!\n";

    while(1);
    return 0;
}

int func_a()
{
    cout << "A";
    for(int i = 0; i < iterations; i++) {
	Periodic_Thread::wait_next();
	cout << "a";
    }
    cout << "A";
    return 'A';   
}

int func_b(void)
{
    cout << "B";
    for(int i = 0; i < iterations; i++) {
	Periodic_Thread::wait_next();
	cout << "b";
    }
    cout << "B";
    return 'B';   
}

int func_c(void)
{
    cout << "C";
    for(int i = 0; i < iterations; i++) {
	Periodic_Thread::wait_next();
	cout << "c";
    }
    cout << "C";
    return 'C';   
}

int func(char c)
{
    s.p();
    cout << "S" << c;
    s.v();
    for(int i = 0; i < iterations; i++) {
    s.p();
    cout << c << Machine::cpu_id();
    s.v();
    Periodic_Thread::wait_next();
    }
    cout << "E" << c;
    return c;   
}
