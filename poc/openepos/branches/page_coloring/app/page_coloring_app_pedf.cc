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
#define THREADS 14 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

#define ARRAY_SIZE KB_512
#define MEMORY_ACCESS 16384
#define WRITE_RATIO 4
int job(unsigned int, int); // function passed by each periodic thread

static const bool same_color = true;

RTC::Microsecond wcet[THREADS];

// period (microsecond), deadline, execution time (microsecond), cpu (partitioned)
// 14 threads, total utilization of 5.504372 , 8 processors 
unsigned int lowest_priority_task = 7 ;
unsigned int threads_parameters[][4] = {
{ 50000 , 50000 , 27098 , 0 },
{ 25000 , 25000 , 5504 , 1 },
{ 100000 , 100000 , 68919 , 2 },
{ 100000 , 100000 , 64664 , 3 },
{ 50000 , 50000 , 9310 , 4 },
{ 200000 , 200000 , 105758 , 5 },
{ 200000 , 200000 , 29326 , 6 },
{ 200000 , 200000 , 67222 , 7 },
{ 50000 , 50000 , 21151 , 6 },
{ 50000 , 50000 , 6757 , 4 },
{ 50000 , 50000 , 34329 , 1 },
{ 50000 , 50000 , 8203 , 4 },
{ 100000 , 100000 , 44566 , 7 },
{ 25000 , 25000 , 8853 , 4 }
};

#define POLLUTE_BUFFER_SIZE KB_512
int pollute_cache(unsigned int repetitions, int id);

int main()
{
  //d.clear();
  Chronometer chrono;
  cout << "Page coloring test\n";
  
  for(int i = 0; i <  THREADS; i++) {
    //s.p();
    //cout << "Creating thread[" << i << "] period = " << threads_parameters[i][0] << endl;
    cout << "Creating thread[" << i << "] period = " << threads_parameters[i][0] 
    << " wcet = " << threads_parameters[i][2] << " affinity = " << threads_parameters[i][3] << endl;
    wcet[i] = 0;
    if(i == lowest_priority_task) {
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&pollute_cache,
                                    //(threads_parameters[i][2] / 5717) * 40, //repetitions related to WCET
                                    (unsigned int) (threads_parameters[i][2] / 1730) * 10,
                                    //(unsigned int) 45,
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    Thread::Criterion((int) threads_parameters[i][0], threads_parameters[i][3]),
                                    ITERATIONS //number of iterations
                                    ); 
    } else {
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&job, 
                                    //(unsigned int) (threads_parameters[i][2] / 775) * 20, //repetitions related to WCET
                                    (unsigned int) (threads_parameters[i][2] / 162) * 3,
                                    //(unsigned int) 20,
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    Thread::Criterion((int) threads_parameters[i][0], threads_parameters[i][3]),
                                    ITERATIONS //number of iterations
                                    ); 
    }
    //s.v();
  }
  
  chrono.start();
  for(int i = 0; i <  THREADS; i++) {
    threads[i]->join();
  }
  chrono.stop();
    
  for(int i = 0; i <  THREADS; i++) {
    delete threads[i];
  }
  
  cout << "Page coloring test done in " << chrono.read() / 1000000 << " seconds!\n";
  for(int i = 0; i <  THREADS; i++) {
    cout << "wcet[" << i << "] = " << wcet[i] << "\n";
  }
  
  while(1);
}

int pollute_cache(unsigned int repetitions, int id)
{
    int sum = 0;
    Chronometer c;
    int *pollute_buffer;
    
    if(same_color)
        pollute_buffer = new (COLOR_2) int[POLLUTE_BUFFER_SIZE];
    else
        pollute_buffer = new ((alloc_priority)(id+2)) int[POLLUTE_BUFFER_SIZE];
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
    cout << "Thread " << id << " done\n";
    return sum;
}

int job(unsigned int repetitions, int id)
{
    int sum = 0;
    Chronometer c;
    int *array;

    //s.p();
    //cout << "Allocating memory thread " << id << "\n";
    //s.v();

    if(same_color)
        array = new (COLOR_2) int[ARRAY_SIZE];
    else
        array = new ((alloc_priority)(id+2)) int[ARRAY_SIZE];
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      //s.p();
      //cout << "Thread " << id << " ite = " << i << "\n";
      //s.v();
      c.start();
      for(int j = 0; j < repetitions; j++) {
        for(int i = 0; i < MEMORY_ACCESS; i++) {
            //int pos = i % ARRAY_SIZE;
            int pos = i;
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
    
    cout << "Thread " << id << " done\n";
    return sum;
}
