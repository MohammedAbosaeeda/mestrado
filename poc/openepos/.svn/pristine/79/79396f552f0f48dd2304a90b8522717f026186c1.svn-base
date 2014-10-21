// Page coloring test

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
Semaphore s;
Clock clock;

RTC::Microsecond find_highest(RTC::Microsecond t[], int size)
{
    RTC::Microsecond wc = 0;
    int j = 0;
    for(int i = 0; i <  size; i++) {
        if(t[i] > wc) { 
          wc = t[i];
          j = i;
        }
    }
    
    return wc;
}

RTC::Microsecond mean(RTC::Microsecond t[], int size, RTC::Microsecond * h)
{   
    RTC::Microsecond sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return (RTC::Microsecond) (sum / size);
}

RTC::Microsecond variance(RTC::Microsecond t[], int size, RTC::Microsecond mean)
{
    RTC::Microsecond v = 0;
    mean = (RTC::Microsecond) mean;
    for(int i = 0; i < size; i++) {
        RTC::Microsecond tmp = (mean - (t[i]));
        v = v + (tmp * tmp);
        cout << "";
    }
    return (RTC::Microsecond) (v / (size -1));
}

#define ITERATIONS 200 // number of times each thread will be executed ~40seg
#define TEST_REPETITIONS 1
#define THREADS 1 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

#define ARRAY_SIZE KB_128
#define MEMORY_ACCESS 16384
#define WRITE_RATIO 4
int job(unsigned int, int); // function passed by each periodic thread

static const bool same_color = false;

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
int run(int test);
void collect_wcet(int test);
void print_stats(void);

typedef struct {
    RTC::Microsecond mean[TEST_REPETITIONS];
    RTC::Microsecond var[TEST_REPETITIONS];
    RTC::Microsecond wcet[TEST_REPETITIONS];
} wcet_stats;

//wcet_stats stats[THREADS];

int main()
{
  //d.clear();  
  //for(int i = 0; i < THREADS; i++)
  //  wcet[i] = new ((alloc_priority)(i+2)) unsigned long(sizeof(RTC::Microsecond) * ITERATIONS);
  
  for(int i = 0; i <  TEST_REPETITIONS; i++) {
      cout << "Starting test " << i << "\n";
      run(i);
  }
  
  cout << "Page coloring test P-EDF done!\n";
  //print_stats();
  
  //for(int i = 0; i < THREADS; i++)
  //   delete wcet[i];
  

  while(1);
}

int run(int test)
{
    Chronometer chrono;
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
        
    for(int i = 0; i <  THREADS; i++) 
            wcet[i] = 0;
        
    for(int i = 0; i <  THREADS; i++) {
    //s.p();
    //cout << "Creating thread[" << i << "] period = " << threads_parameters[i][0] 
    //<< " wcet = " << threads_parameters[i][2] << " affinity = " << threads_parameters[i][3] << endl;
    
    if(i == lowest_priority_task) {
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&pollute_cache,
                                    //(threads_parameters[i][2] / 5717) * 40, //repetitions related to WCET
                                    (unsigned int) (threads_parameters[i][2] / 1730) * 10,
                                    //(unsigned int) 45,
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    Thread::Criterion((RTC::Microsecond) threads_parameters[i][0], (RTC::Microsecond) threads_parameters[i][0], threads_parameters[i][3]),
                                    ITERATIONS //number of iterations
                                    ); 
    } else {
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&job, 
                                    //(unsigned int) (threads_parameters[i][2] / 775) * 20, //repetitions related to WCET
                                    (unsigned int) (threads_parameters[i][2] / 535) * 3,
                                    //(unsigned int) 3,
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    Thread::Criterion((RTC::Microsecond) threads_parameters[i][0], (RTC::Microsecond) threads_parameters[i][0], threads_parameters[i][3]),
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
  
  //collect_wcet(test);
  
  cout << "Page coloring test " << test << " done in " << chrono.read() / 1000000 << " seconds!\n";
  
  //for(int i = 0; i <  THREADS; i++) {
  //  cout << "wcet[" << i << "] = " << find_highest(wcet[i], ITERATIONS) << "\n";
  //}
    
  for(int i = 0; i <  THREADS; i++) {
    delete threads[i];
  }

}

int pollute_cache(unsigned int repetitions, int id)
{
    int sum = 0;
    Chronometer c;
    Pseudo_Random * rand;
    int *pollute_buffer;
    
    if(same_color)
        rand = new (COLOR_2) Pseudo_Random();
    else
        rand = new ((alloc_priority)(id+2)) Pseudo_Random();
    
    rand->seed(clock.now() + id);
    
    if(same_color)
        pollute_buffer = new (COLOR_2) int[POLLUTE_BUFFER_SIZE];
    else
        pollute_buffer = new ((alloc_priority)(id+2)) int[POLLUTE_BUFFER_SIZE];
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      //s.p();
      //cout << "Pollute Thread\n";
      //s.v();
      
      c.start();
     
      for(int j = 0; j < repetitions; j++) {
        for(int k = (rand->random() % (POLLUTE_BUFFER_SIZE - 1) ) % 1000; k < POLLUTE_BUFFER_SIZE; k += 64) {
        //for(int i = 0; i < POLLUTE_BUFFER_SIZE; i += 64) {
            pollute_buffer[k] = j % 64;
            sum += pollute_buffer[k];
        }
      }
      
      c.stop();
      
      //wcet[id][i] = c.read();
      
      if(wcet[id] < c.read())
        wcet[id] = c.read();
      c.reset();
    }
    
    delete rand;
    delete pollute_buffer;
    
    //cout << "Thread " << id << " done\n";
    return sum;
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
    //s.p();
    //cout << "Allocating memory thread " << id << "\n";
    //s.v();

    if(same_color)
        array = new (COLOR_2) int[ARRAY_SIZE];
    else
        array = new ((alloc_priority)(id+2)) int[ARRAY_SIZE];
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      c.reset();
      
      //if(id == 4 && i < 3)
      //    cout << "Thread " << id << " ite = " << i << " start\n";
      
      //s.p();
      //cout << "Thread " << id << " ite = " << i << "\n";
      //s.v();
      c.start();
      
      for(int j = 0; j < repetitions; j++) {
        for(int k = 0; k < MEMORY_ACCESS; k++) {
            int pos = rand->random() % (ARRAY_SIZE - 1);
            //if(id == 2 && i % 10000 == 0) cout << "pos = " << pos << "\n"; //retorna sempre 0 ou 16386
            array[pos] = k + j;
            if((k % WRITE_RATIO) == 0)
              sum += array[pos];
        }
      }
      
      c.stop();
      
      //if(id == 4 && i < 3)
      //    cout << "Thread " << id << " ite = " << i << " finished in " << c.read() << "\n";
      
      //wcet[id][i] = c.read();
      
      if(wcet[id] < c.read())
        wcet[id] = c.read();
      
      c.reset();
      
    }
    
    cout << "WC = " << wcet[id] << "\n";
    
    delete rand;
    delete array;
    //s.p();
    //cout << "Thread " << id << " done\n";
    //s.v(); 
    
    return sum;
}
