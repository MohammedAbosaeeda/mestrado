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

#define ITERATIONS 200 // number of times each thread will be executed
#define TEST_REPETITIONS 2
#define THREADS 23 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

#define ARRAY_SIZE KB_64
#define MEMORY_ACCESS 16384
#define WRITE_RATIO 4
int job(unsigned int, int, int); // function passed by each periodic thread

//static const bool same_color = true;

RTC::Microsecond *wcet[THREADS];

unsigned int threads_parameters[][5] = {
{ 100000 , 100000 , 9000  , 0, 1 },  //t0
{ 150000 , 150000 , 60000 , 0, 1 },  //t1
{ 50000  , 50000  , 15000 , 0, 1 },  //t2
{ 50000  , 50000  , 5000  , 0, 1 },  //t3
{ 200000 , 200000 , 19000 , 0, 1 },  //t4
{ 50000  , 50000  , 26000 , 1, 2 },  //t5
{ 50000  , 50000  , 27000 , 1, 3 },  //t6
{ 200000 , 200000 , 67222 , 2, 3 },  //t7

{ 100000 , 100000 , 9000  , 3, 4 },  //t0
{ 150000 , 150000 , 60000 , 3, 4 },  //t1
{ 50000  , 50000  , 15000 , 3, 4 },  //t2
{ 50000  , 50000  , 5000  , 3, 4 },  //t3
{ 200000 , 200000 , 19000 , 3, 4 },  //t4
{ 50000  , 50000  , 26000 , 4, 5 },  //t5
{ 50000  , 50000  , 27000 , 4, 6 },  //t6
{ 200000 , 200000 , 67222 , 5, 6 },  //t7

{ 100000 , 100000 , 9000  , 6, 7 },  //t0
{ 150000 , 150000 , 60000 , 6, 7 },  //t1
{ 50000  , 50000  , 15000 , 6, 7 },  //t2
{ 50000  , 50000  , 5000  , 6, 7 },  //t3
{ 200000 , 200000 , 19000 , 6, 8 },  //t4
{ 50000  , 50000  , 26000 , 7, 9 },  //t5
{ 50000  , 50000  , 27000 , 7, 9 },  //t6
};

int run(int test);
void collect_wcet(int test);
void print_stats(void);

typedef struct {
    RTC::Microsecond mean[TEST_REPETITIONS];
    RTC::Microsecond var[TEST_REPETITIONS];
    RTC::Microsecond wcet[TEST_REPETITIONS];
} wcet_stats;

wcet_stats stats[THREADS];

RTC::Microsecond exec_time;

int main()
{
  //d.clear();  
  
  exec_time = 0;
  
  for(int i = 0; i < THREADS; i++)
    wcet[i] = new ((alloc_priority) (threads_parameters[i][4])) unsigned long(sizeof(RTC::Microsecond) * ITERATIONS);
  
  for(int i = 0; i <  TEST_REPETITIONS; i++) {
      cout << "Starting test " << i << "\n";
      run(i);
  }
  
  cout << "Page coloring test P-EDF done!\n";
  cout << "Worst-case exec time = " << exec_time / 1000000 << " seconds!\n";
  
  print_stats();
  
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
    
    
       
    threads[i] = new ((alloc_priority) 0) Periodic_Thread(&job, 
      //(unsigned int) (threads_parameters[i][2] / 775) * 20, //repetitions related to WCET
      (unsigned int) ((threads_parameters[i][2] / 540) * 5),
      //(unsigned int) 3,
      (int) i, // thread ID
      (int) (threads_parameters[i][4]), //color ID
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
  
  collect_wcet(test);
  
  //for(int i = 0; i <  THREADS; i++) {
  //  cout << "wcet[" << i << "] = " << find_highest(wcet[i], ITERATIONS) << "\n";
  //}
    
  for(int i = 0; i <  THREADS; i++) {
    delete threads[i];
  }
  
  if(chrono.read() > exec_time)
      exec_time = chrono.read();
  
  cout << "Page coloring test " << test << " done in " << chrono.read() / 1000000 << " seconds!\n";

}

void collect_wcet(int test)
{
    for(int i = 0; i < THREADS; i++) {
        RTC::Microsecond wc, m, var;
        m = mean(wcet[i], ITERATIONS, &wc);
        var = variance((RTC::Microsecond *) wcet[i], ITERATIONS, m);
        stats[i].mean[test] = m;
        stats[i].wcet[test] = wc;
        stats[i].var[test] = var;
    }
    
    //cout << "m = " << stats[6].mean[test] << " wc = " 
    //<< stats[6].wcet[test] << " var = " << stats[6].var[test] << "\n";
    
    /*for(int i = 0; i < ITERATIONS; i++)
        cout << wcet[0][i] << " ";
    cout << "\n";*/
}

void print_stats(void)
{
    for(int i = 0; i < THREADS; i++) {
        RTC::Microsecond wc, m, var, tmp, wc_m, wc_var;
        if(TEST_REPETITIONS > 1) {
            wc = find_highest(stats[i].wcet, TEST_REPETITIONS);
            wc_m = mean(stats[i].wcet, TEST_REPETITIONS, &tmp);
            wc_var = variance(stats[i].wcet, TEST_REPETITIONS, wc_m);
            m = mean(stats[i].mean, TEST_REPETITIONS, &tmp);
            var = mean(stats[i].var, TEST_REPETITIONS, &tmp);
        } else {
            wc = stats[i].wcet[0];
            wc_m = stats[i].wcet[0];
            wc_var = 0;
            m = stats[i].mean[0];
            var = 0;
        }
        
        cout << "Thread " << i << " wc = " << wc << " m = " << m
        << " var = " << var << " wc m = " << wc_m << " wc var = " 
        << wc_var << "\n";
    }
}


int job(unsigned int repetitions, int id, int color)
{
    int sum = 0;
    Chronometer c;
    Pseudo_Random * rand;
    int *array;
    
    rand = new ((alloc_priority) color) Pseudo_Random();
        
    rand->seed(clock.now() + id);

    array = new ((alloc_priority) color) int[ARRAY_SIZE];
    
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
    
    s.p();
    cout << "Thread " << id << " done\n";
    s.v(); 
    
    return sum;
}
