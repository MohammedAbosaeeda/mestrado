// GEDF test with WCET

#include <utility/ostream.h>
#include <periodic_thread.h>
#include <clock.h>
#include <chronometer.h>
#include <display.h>
#include <semaphore.h>
#include <tsc.h>
#include "gedf/include/math.h"
#include <timer.h>
#include <perf_mon.h>

__USING_SYS

Display d;
OStream cout;
Semaphore s;

double mean(const volatile TSC::Time_Stamp t[], int size, TSC::Time_Stamp * h)
{   
    TSC::Time_Stamp sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return sum / size;
}

double mean(double t[], int size, double * h)
{   
    double sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return sum / size;
}

TSC::Time_Stamp find_highest(TSC::Time_Stamp t[], int size)
{
    TSC::Time_Stamp wc = 0;
    for(int i = 0; i <  size; i++) {
        if(t[i] > wc) 
          wc = t[i];
    }
    return wc;
}

double variance(const volatile TSC::Time_Stamp t[], int size, double mean)
{
    double v = 0.0;
    for(int i = 0; i < size; i++) {
        double tmp = (mean - t[i]);
        v = v + (tmp * tmp);
        cout << "";
    }
    return (v / (double) (size -1));
}

double variance(double t[], int size, double mean)
{
    double v = 0.0;
    for(int i = 0; i < size; i++) {
        double tmp = (mean - t[i]);
        v = v + (tmp * tmp);
        cout << "";
    }
    return (v / (double) (size -1));
}

double standard_deviation(double variance)
{
    double r = sqrt(variance);
    return r;
}

double ts_to_us(double ts)
{
    return (ts * 1000000) / CPU::clock();
}

void print_alarm(void);
void print_thread_stats(void);
void print_perf_stats(void);
void collect_thread_stats(int);
void print_all_thread_stats(void);
void reset_thread_counters(void);

#define ITERATIONS 500 // number of times each thread will be executed
#define THREADS 15 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

// period (microsecond), deadline, execution time (microsecond)
// 15 threads, total utilization of 0.9947831 , 8 processors 
unsigned int threads_parameters[][3] = {
{ 23000 , 23000 , 810 },
{ 30000 , 30000 , 440 },
{ 22000 , 22000 , 1838 },
{ 20000 , 20000 , 1094 },
{ 4000 , 4000 , 435 },
{ 14000 , 14000 , 1084 },
{ 15000 , 15000 , 1425 },
{ 31000 , 31000 , 2513 },
{ 20000 , 20000 , 1277 },
{ 25000 , 25000 , 1849 },
{ 5000 , 5000 , 84 },
{ 6000 , 6000 , 556 },
{ 23000 , 23000 , 2377 },
{ 22000 , 22000 , 1018 },
{ 25000 , 25000 , 2547 },
};

#define NUMS 50
#define TEST_REPETITIONS 10
int loop_once(void); // loop calculation
int job(unsigned int, int); // function passed by each periodic thread
int loop_for(unsigned int, int);

typedef struct {
  double mean_sleep[TEST_REPETITIONS];
  double stdev_sleep[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_sleep[TEST_REPETITIONS];
  
  double mean_sleep2[TEST_REPETITIONS];
  double stdev_sleep2[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_sleep2[TEST_REPETITIONS];
  
  double mean_wakeup[TEST_REPETITIONS];
  double stdev_wakeup[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_wakeup[TEST_REPETITIONS];
  
  double mean_resched[TEST_REPETITIONS];
  double stdev_resched[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_resched[TEST_REPETITIONS];
} thread_stats;

thread_stats stats;

int run(int test);

int main()
{
  d.clear();
  cout << "GEDF test with " << THREADS << " threads\n";
  
  for(int i = 0; i < TEST_REPETITIONS; i++) {
      
      cout << "Scheduler schedulable = " << Thread::_scheduler.schedulables() << "\n";
      
      run(i);
  }
  
  cout << "GEDF total test done clock = " << IA32::clock() << "\n";
    
  print_all_thread_stats();
  
  while(1);
}

//RTC::Microsecond start_time;
Chronometer chrono;

int run(int test)
{
    
    reset_thread_counters();
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
    
   	for(int i = 0; i <  THREADS; i++) {
        threads[i] = new Periodic_Thread(&job, 
                                         threads_parameters[i][2], //WCET
                                         i, //ID
                                         threads_parameters[i][0], //period
                                         ITERATIONS); //number of iterations
    }
 
    chrono.reset();
    chrono.start();
    for(int i = 0; i <  THREADS; i++) {
        threads[i]->join();
    }
    chrono.stop();
    
    for(int i = 0; i < THREADS; i++) {
        delete threads[i];
    }
    
    collect_thread_stats(test);
   
    cout << "GEDF test (" << test << ") done in " << chrono.read() / 1000000 << " seconds \n";
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
    
    
    return 0;
}

void reset_thread_counters()
{
    Thread::_sleep_counter = 0;
    Thread::_reschedule_counter = 0;
    Thread::_wakeup_counter = 0;
    for(int i = 0; i < Thread::MAX_COUNTING; i++) {
        Thread::_sleep_tsc[i] = 0;
        Thread::_wakeup_tsc[i] = 0;
        Thread::_reschedule_tsc[i] = 0;
    }
}

void print_all_thread_stats()
{
  double total_wcet_sleep = find_highest(stats.wcet_sleep, TEST_REPETITIONS);
  double tmp;
  double total_mean_sleep = mean(stats.mean_sleep, TEST_REPETITIONS, &tmp);
  double mean_stdev_sleep =  mean(stats.stdev_sleep, TEST_REPETITIONS, &tmp);
  
  double total_wcet_sleep2 = find_highest(stats.wcet_sleep2, TEST_REPETITIONS);
  double total_mean_sleep2 = mean(stats.mean_sleep2, TEST_REPETITIONS, &tmp);
  double mean_stdev_sleep2 =  mean(stats.stdev_sleep2, TEST_REPETITIONS, &tmp);
  
  cout << "Sleep   WCET = " << total_wcet_sleep2 << " AVG = " << total_mean_sleep2 << " STD = " << mean_stdev_sleep2 << "\n";
  
  double total_wcet_wakeup = find_highest(stats.wcet_wakeup, TEST_REPETITIONS);
  double total_mean_wakeup = mean(stats.mean_wakeup, TEST_REPETITIONS, &tmp);
  double mean_stdev_wakeup =  mean(stats.stdev_wakeup, TEST_REPETITIONS, &tmp);
  
  cout << "Wakeup  WCET = " << total_wcet_wakeup << " AVG = " << total_mean_wakeup << " STD = " << mean_stdev_wakeup << "\n";
  
  double total_wcet_resched = find_highest(stats.wcet_resched, TEST_REPETITIONS);
  double total_mean_resched = mean(stats.mean_resched, TEST_REPETITIONS, &tmp);
  double mean_stdev_resched =  mean(stats.stdev_resched, TEST_REPETITIONS, &tmp);
  
  cout << "Resched WCET = " << total_wcet_resched << " AVG = " << total_mean_resched << " STD = " << mean_stdev_resched << "\n"; 
}

void collect_thread_stats(int test)
{
    double var_sleep, var_wakeup, var_resched;
    
    stats.mean_sleep[test] = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &stats.wcet_sleep[test]);
    var_sleep = variance(Thread::_sleep_tsc, Thread::_sleep_counter, stats.mean_sleep[test]);
    stats.stdev_sleep[test] = standard_deviation(var_sleep);
    
    stats.mean_sleep2[test] = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &stats.wcet_sleep2[test]);
    var_sleep = variance(Thread::_sleep_tsc, Thread::_sleep_counter, stats.mean_sleep2[test]);
    stats.stdev_sleep2[test] = standard_deviation(var_sleep);
    
    stats.wcet_sleep2[test] = find_highest((long long unsigned int *) Thread::_sleep_tsc, Thread::_sleep_counter);
         
    cout << "sleep   WCET = " << stats.wcet_sleep2[test] 
    << " AVG = " <<  stats.mean_sleep2[test]
    << " STD = " << stats.stdev_sleep2[test] <<"\n";
    
    stats.mean_wakeup[test] = mean(Thread::_wakeup_tsc, Thread::_wakeup_counter, &stats.wcet_wakeup[test]);
    var_wakeup = variance(Thread::_wakeup_tsc, Thread::_wakeup_counter, stats.mean_wakeup[test]);
    stats.stdev_wakeup[test] = standard_deviation(var_wakeup);
       
    cout << "wakeup  WCET = " << stats.wcet_wakeup[test] 
    << " AVG = " <<  stats.mean_wakeup[test]
    << " STD = " << stats.stdev_wakeup[test] <<"\n";
    
    stats.mean_resched[test] = mean(Thread::_reschedule_tsc, Thread::_reschedule_counter, &stats.wcet_resched[test]);
    var_resched = variance(Thread::_reschedule_tsc, Thread::_reschedule_counter, stats.mean_resched[test]);
    stats.stdev_resched[test] = standard_deviation(var_resched);
    
    cout << "resched WCET = " << stats.wcet_resched[test] 
    << " AVG = " <<  stats.mean_resched[test]
    << " STD = " << stats.stdev_resched[test] <<"\n";
    
}

int loop_once(void)
{
    int i, j = 0;
    for(i = 0; i < NUMS; i++) {
        j += 3;
    }
    return j;
}

int loop_for(unsigned int exec_time, int id) 
{
    return loop_once();
}

int job(unsigned int exec_time, int id)
{
    int sum = 0;
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      sum += loop_for(exec_time, id);
    }
    return sum;
}
