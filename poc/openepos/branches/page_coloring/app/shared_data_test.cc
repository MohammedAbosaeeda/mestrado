#include <utility/ostream.h>
#include <display.h>
#include <periodic_thread.h>
#include <rtc.h>
#include <chronometer.h>
#include <semaphore.h>
#include <utility/random.h>
#include <clock.h>
#include "data_sizes.h"

__USING_SYS

Display d;
OStream cout;
//Semaphore s;
Clock clock;

#define ITERATIONS 200 // number of times each thread will be executed ~40seg
#define TEST_REPETITIONS 1
#define THREADS 1 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

#define ARRAY_SIZE KB_256
#define MEMORY_ACCESS 16384
#define WRITE_RATIO 4
int job(unsigned int, int); // function passed by each periodic thread
int run(int test);

static const bool same_color = false;

RTC::Microsecond *wcet[THREADS];

// period (microsecond), deadline, execution time (microsecond), cpu (partitioned)
// 17 threads, total utilization of 5.972483 , 8 processors 
unsigned int lowest_priority_task = 16 ;
unsigned int threads_parameters[][4] = {
{ 50000 , 50000 , 32329 , 0 },
{ 50000 , 50000 , 5260 , 1 },
{ 50000 , 50000 , 12295 , 2 },
{ 200000 , 200000 , 62727 , 3 },
{ 100000 , 100000 , 49286 , 4 },
{ 200000 , 200000 , 48083 , 5 },
{ 200000 , 200000 , 22563 , 6 },
{ 100000 , 100000 , 17871 , 7 },
{ 25000 , 25000 , 15211 , 1 },
{ 200000 , 200000 , 129422 , 6 },
{ 200000 , 200000 , 52910 , 7 },
{ 100000 , 100000 , 14359 , 5 },
{ 25000 , 25000 , 14812 , 2 },
{ 50000 , 50000 , 33790 , 3 },
{ 25000 , 25000 , 7064 , 5 },
{ 100000 , 100000 , 20795 , 7 },
{ 200000 , 200000 , 42753 , 4 }
};

RTC::Microsecond exec_time;

int main()
{
  //d.clear();  
  
  exec_time = 0;
  
  for(int i = 0; i < THREADS; i++)
    wcet[i] = new ((alloc_priority)(i+2)) unsigned long(sizeof(RTC::Microsecond) * ITERATIONS);
  
  for(int i = 0; i <  TEST_REPETITIONS; i++) {
      cout << "Starting test " << i << "\n";
      run(i);
  }
  
  cout << "Page coloring test P-EDF done!\n";
  cout << "Worst-case exec time = " << exec_time / 1000000 << " seconds!\n";
  
  for(int i = 0; i < THREADS; i++)
      delete wcet[i];
  

  while(1);
}

int run(int test)
{
    Chronometer chrono;
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
        
    for(int i = 0; i <  THREADS; i++) 
        for(int j = 0; j < ITERATIONS; j++)
            wcet[i][j] = 0;
        
    for(int i = 0; i <  THREADS; i++) {
    //s.p();
    //cout << "Creating thread[" << i << "] period = " << threads_parameters[i][0] 
    //<< " wcet = " << threads_parameters[i][2] << " affinity = " << threads_parameters[i][3] << endl;
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&job, 
                                    (unsigned int) (threads_parameters[i][2] / 775) * 20, //repetitions related to WCET
                                    //(unsigned int) 3,
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    Thread::Criterion((RTC::Microsecond) threads_parameters[i][0], (RTC::Microsecond) threads_parameters[i][0], threads_parameters[i][3]),
                                    ITERATIONS //number of iterations
                                    ); 
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
  
  if(chrono.read() > exec_time)
      exec_time = chrono.read();
  
  cout << "Page coloring test " << test << " done in " << chrono.read() / 1000000 << " seconds!\n";

}

int job(unsigned int repetitions, int id)
{
    int sum = 0;
    Chronometer c;
    Pseudo_Random * rand;
    int *array;
    
    if(same_color)
        rand = new (COLOR_2) Pseudo_Random();
    else
        rand = new ((alloc_priority)(id+2)) Pseudo_Random();
        
    rand->seed(clock.now() + id);

    if(same_color)
        array = new (COLOR_2) int[ARRAY_SIZE];
    else
        array = new ((alloc_priority)(id+2)) int[ARRAY_SIZE];
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      c.reset();
      
      //if(id == 0)
      //    cout << "Thread " << id << " ite = " << i << " start\n";
      
      c.start();
      
      for(int j = 0; j < repetitions; j++) {
        for(int k = 0; k < MEMORY_ACCESS; k++) {
            int pos = rand->random() % (ARRAY_SIZE - 1);            
            sum += array[pos];
            if((k % WRITE_RATIO) == 0)
              array[pos] = k + j;
        }
      }
      
      c.stop();
      
      //if(id == 0 && c.read() > 100000)
      //    cout << "Thread " << id << " ite = " << i << " finished in " << c.read() << "\n";
      
      wcet[id][i] = c.read();
      
      //if(wcet[id] < c.read())
      //  wcet[id] = c.read();
      c.reset();
      
    }
    
    delete rand;
    delete array;
    
    //s.p();
    //cout << "Thread " << id << " done\n";
    //s.v(); 
    
    return sum;
}