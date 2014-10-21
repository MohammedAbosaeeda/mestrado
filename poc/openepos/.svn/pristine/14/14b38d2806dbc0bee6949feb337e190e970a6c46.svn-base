#include <utility/ostream.h>
#include <thread.h>
#include <tsc.h>
#include <cpu.h>
#include <display.h>
#include <rtc.h>
#include "ipi_overhead/include/math.h"
#include <ic.h>

__USING_SYS

TSC ts;
void print_time(void);
void find_highest(void);
void ipi_test(unsigned int i);
OStream cout;

Display d;
bool sync = false;

#define ITERATIONS 10000000
unsigned int time[ITERATIONS];
unsigned int iter = 0;

int main(void)
{
  d.clear();
  cout << "Test to measure the IPI time\n";
  cout << "CPU clock = " << CPU::clock() << endl;
  
  CPU::int_disable();
  IC::int_vector(IC::INT_RESCHEDULER, &ipi_test);
  IC::enable(IC::INT_RESCHEDULER);
  CPU::int_enable();
  
  for(int i = 0; i < ITERATIONS; i ++) {
    TSC::Time_Stamp start = TSC::time_stamp();
    IC::ipi_send(1, IC::INT_RESCHEDULER);
    time[i] = TSC::time_stamp() - start;
  }

  find_highest();
  find_highest();
  while(1);
  return 0;
}

void ipi_test(unsigned int i)
{
    return;
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
    double max_in_us, avg_in_us;
    for(int i = 1; i < ITERATIONS; i++) {
        if(time[i] > max)
            max = time[i];
        acc += time[i];
    }
    
    avg = (acc / (ITERATIONS - 1));
    max_in_us = (double) ( (double) (max * 1000000) / CPU::clock());
    avg_in_us = (double) ( (double) (avg * 1000000) / CPU::clock());
    
    double var = variance(time, ITERATIONS, (double) avg);
    double s = standard_deviation(var);
    double s_us = (s * 1000000) / CPU::clock();
    
    cout << "\nMax = " << max << " avg = " << avg << " std = " << s;
    cout << " max in us = " << max_in_us;
    cout << " avg in us = " << avg_in_us;
    cout << " std in us = " << s_us << endl;
}