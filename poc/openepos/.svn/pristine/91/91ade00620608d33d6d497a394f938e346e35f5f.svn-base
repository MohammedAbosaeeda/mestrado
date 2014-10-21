// Page coloring test

#include <utility/ostream.h>
#include <display.h>
#include <periodic_thread.h>
#include <rtc.h>
#include <chronometer.h>
#include <semaphore.h>
#include "data_sizes.h"

__USING_SYS

Display d;
OStream cout;
Semaphore s;

#define ITERATIONS 200 // number of times each thread will be executed
#define THREADS 18 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

#define ARRAY_SIZE KB_128
#define MEMORY_ACCESS 16384
#define WRITE_RATIO 4
int job(unsigned int, int); // function passed by each periodic thread

RTC::Microsecond wcet[THREADS];

// period (microsecond), deadline, execution time (microsecond), cpu (partitioned)
// 18 threads, total utilization of 4.583955 , 8 processors 
unsigned int lowest_priority_task = 17 ;
unsigned int threads_parameters[][4] = {
{ 50000 , 50000 , 12588 , 0 },
{ 25000 , 25000 , 2994 , 0 },
{ 200000 , 200000 , 84712 , 0 },
{ 200000 , 200000 , 20032 , 0 },
{ 25000 , 25000 , 6530 , 0 },
{ 100000 , 100000 , 10281 , 0 },
{ 100000 , 100000 , 11914 , 0 },
{ 100000 , 100000 , 25981 , 0 },
{ 200000 , 200000 , 94212 , 0 },
{ 25000 , 25000 , 7287 , 0 },
{ 200000 , 200000 , 84062 , 0 },
{ 25000 , 25000 , 5036 , 0 },
{ 50000 , 50000 , 13659 , 0 },
{ 100000 , 100000 , 20610 , 0 },
{ 100000 , 100000 , 19339 , 0 },
{ 200000 , 200000 , 30000 , 0 },
{ 200000 , 200000 , 70643 , 0 },
{ 200000 , 200000 , 77085 , 0 }
};

#define POLLUTE_BUFFER_SIZE KB_512
int pollute_cache(unsigned int repetitions, int id);

int main()
{
  //d.clear();
  cout << "Page coloring test\n";
  
  for(int i = 0; i <  THREADS; i++) {
    //s.p();
    //cout << "Creating thread[" << i << "] period = " << threads_parameters[i][0] << endl;
    wcet[i] = 0;
    if(i == lowest_priority_task) {
      threads[i] = new ((alloc_priority)(((i+2) % 10) + 2)) Periodic_Thread(&pollute_cache,
      //threads[i] = new (COLOR_1) Periodic_Thread(&pollute_cache,
                                       //(threads_parameters[i][2] / 234) * 2
                                    (unsigned int) (threads_parameters[i][2] / 1161) * 45, //repetitions related to WCET
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    ITERATIONS); //number of iterations
    } else {
      threads[i] = new ((alloc_priority)(((i+2) % 10) + 2)) Periodic_Thread(&job, 
      //threads[i] = new (COLOR_1) Periodic_Thread(&job, 
                                    (unsigned int) (threads_parameters[i][2] / 153) * 3, //repetitions related to WCET
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    ITERATIONS); //number of iterations
    }
    //s.v();
  }
  
  for(int i = 0; i <  THREADS; i++) {
    threads[i]->join();
  }
    
  for(int i = 0; i <  THREADS; i++) {
    delete threads[i];
  }
  
  cout << "Page coloring test done!\n";
  for(int i = 0; i <  THREADS; i++) {
    cout << "wcet[" << i << "] = " << wcet[i] << "\n";
  }
  
  while(1);
}

int pollute_cache(unsigned int repetitions, int id)
{
    int sum = 0;
    Chronometer c;
    
    int *pollute_buffer = new ((alloc_priority)(((id+2) % 10)+2)) int[POLLUTE_BUFFER_SIZE];
    //int *pollute_buffer = new (COLOR_1) int[POLLUTE_BUFFER_SIZE];
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      //s.p();
      //cout << "Pollute Thread\n";
      //s.v();
      
      c.start();
     
      for(int j = 0; j < repetitions; j++) {
        for(int i = 0; i < POLLUTE_BUFFER_SIZE; i += 64) {
            pollute_buffer[i] = i % 64;
            sum += pollute_buffer[i];
        }
      }
      c.stop();
      if(wcet[id] < c.read())
        wcet[id] = c.read();
      c.reset();
    }
    return sum;
}

int job(unsigned int repetitions, int id)
{
    int sum = 0;
    Chronometer c;

    //s.p();
    //cout << "Allocating memory thread " << id << "\n";
    //s.v();

    int *array = new ((alloc_priority)(((id+2) % 10)+2)) int[ARRAY_SIZE];
    //int *array = new (COLOR_1) int[ARRAY_SIZE];
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      s.p();
      cout << "Thread " << id << " ite = " << i << "\n";
      s.v();
      c.start();
      for(int j = 0; j < repetitions; j++) {
        for(int i = 0; i < MEMORY_ACCESS; i++) {
            int pos = i % ARRAY_SIZE;
            array[pos] = i + j;
            if((i % WRITE_RATIO) == 0)
              sum += array[pos];
        }
      }
      c.stop();
      if(wcet[id] < c.read())
        wcet[id] = c.read();
      c.reset();
    }
    
    return sum;
}
