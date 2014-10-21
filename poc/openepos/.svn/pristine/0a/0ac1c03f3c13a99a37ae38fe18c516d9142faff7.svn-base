#include <utility/ostream.h>
#include <thread.h>
#include <tsc.h>
#include <cpu.h>
#include <display.h>
#include <rtc.h>
#include "context_switch_overhead/include/math.h"

__USING_SYS

Thread *a,*b;
int func_a(void);
int func_b(void);
void print_time(void);
void find_highest(void);
OStream cout;

Display d;
bool sync = false;

#define ITERATIONS 5000000
unsigned int time[ITERATIONS];
unsigned int iter = 0;

int main(void)
{
  d.clear();
  cout << "Test to measure the context switch time\n";
  cout << "CPU clock = " << CPU::clock() << endl;
  
  b = new Thread(&func_b, Thread::READY, Thread::Criterion((RTC::Microsecond) 300, 1));
  a = new Thread(&func_a, Thread::READY, Thread::Criterion((RTC::Microsecond) 200, 1));
  //b = new Thread(&func_b, Thread::READY, Thread::Criterion(3, 1));
  //a = new Thread(&func_a, Thread::READY, Thread::Criterion(2, 1));
  
  a->join();
  b->join();
  
  cout << "Test done!\n";
  cout << "Total number of context switches = " << Thread::_n_context_switches << endl;
  float t = 0.039;
  cout << "\ntest = " << t << endl;
  //print_time();
  find_highest();
  while(1);
  return 0;
}

int func_a(void)
{
    cout << "func_a CPU ID = " << Machine::cpu_id() << endl;
    //TSC::Time_Stamp now;
    sync = true;
    for(int i = 0; i < ITERATIONS; i++) {
        for(int j = 0; j < 0xffffff; j++) ;
        Thread::yield();
        //now = TSC::time_stamp();
        //cout << "Time b = " << (now - b->_context_switch_time) << " ";
    }      
}

int func_b(void)
{
    cout << "func_b CPU ID = " << Machine::cpu_id() << endl;
    TSC::Time_Stamp now;
    while(!sync);
    for(int i = 0; i < ITERATIONS; i++) {
        now = TSC::time_stamp();
        //cout << "Time a = " << (now - a->_context_switch_time) << " ";
        time[iter++] = now - a->_context_switch_time;
        Thread::yield();  
    }      
}

void print_time(void)
{
    for(int i = 0; i < ITERATIONS; i++) {
        cout << "Time (" << i << ") = " << time[i] << " ";
    }
}

double variance(const unsigned int t[], int size, double mean)
{
    double v = 0.0;
    for(int i = 0; i < size; i++) {
        double tmp = (mean - t[i]);
        v = v + (tmp * tmp);
        cout << "";
        //cout << "v = " << (unsigned int) v << " "; 
    }
    //cout << " v TOTAL = " << (unsigned int) v << System::endl;
    //cout << "\n";
    return (v / (double) (size -1));
}

double standard_deviation(double variance)
{
    //cout << "variance = " << variance;
    double r = sqrt(variance);
    //cout << " sqrt(var) = " << r << System::endl;
    return r;
}

void find_highest(void)
{
    unsigned int max = time[1];
    unsigned int acc = 0;
    unsigned int avg = 0;
    float max_in_us, avg_in_us;
    for(int i = 1; i < ITERATIONS; i++) {
        if(time[i] > max)
            max = time[i];
        acc += time[i];
    }
    
    avg = (acc / (ITERATIONS - 1));
    max_in_us = (float) ( (float) (max * 1000000) / CPU::clock());
    avg_in_us = (float) ( (float) (avg * 1000000) / CPU::clock());
    
    double var = variance(time, ITERATIONS, (double) avg);
    double s = standard_deviation(var);
    
    cout << "\nMax = " << max << " avg = " << avg;
    cout << " max in us = " << max_in_us;
    cout << " avg in us = " << avg_in_us;
    cout << " variance = " << var << " std = " << s << endl;
}