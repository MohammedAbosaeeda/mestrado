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
//Semaphore s;
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
#define TEST_REPETITIONS 50 //50 test repetitions
#define THREADS 12 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

#define ARRAY_SIZE KB_256
#define MEMORY_ACCESS 16384
#define WRITE_RATIO 4
int job(unsigned int, int); // function passed by each periodic thread

static const bool same_color = true;

RTC::Microsecond *wcet[THREADS];

// period (microsecond), deadline, execution time (microsecond), cpu (clustered)
// 12 threads, total utilization of 4.596702 , 8 processors 
unsigned int lowest_priority_task = 8 ;
unsigned int threads_parameters[][4] = {
{ 50000 , 50000 , 13437 , 0 },
{ 100000 , 100000 , 38509 , 0 },
{ 25000 , 25000 , 2552 , 0 },
{ 100000 , 100000 , 11993 , 0 },
{ 50000 , 50000 , 21230 , 0 },
{ 100000 , 100000 , 64127 , 1 },
{ 100000 , 100000 , 53944 , 1 },
{ 100000 , 100000 , 63266 , 2 },
{ 200000 , 200000 , 115145 , 2 },
{ 50000 , 50000 , 22018 , 3 },
{ 50000 , 50000 , 15066 , 3 },
{ 25000 , 25000 , 4135 , 3 },
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

wcet_stats stats[THREADS];

RTC::Microsecond exec_time;

int main()
{
  //d.clear();  
  
	cout << "C-EDF page coloring test\n";
 
	exec_time = 0;

	for(int i = 0; i < THREADS; i++)
		wcet[i] = new ((alloc_priority)(i+2)) unsigned long(sizeof(RTC::Microsecond) * ITERATIONS);

	for(int i = 0; i <  TEST_REPETITIONS; i++) {
	  cout << "Starting test " << i << "\n";
	  run(i);
	}

	cout << "Page coloring test C-EDF done!\n";
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
    
    if(i == lowest_priority_task) {
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&pollute_cache,
                                    //(threads_parameters[i][2] / 5717) * 40, //repetitions related to WCET
                                    (unsigned int) (threads_parameters[i][2] / 1730) * 6, //task set 2 = * 6
                                    //(unsigned int) 45,
                                    i, //ID
                                    threads_parameters[i][0], //period
                                    Thread::Criterion((RTC::Microsecond) threads_parameters[i][0], (RTC::Microsecond) threads_parameters[i][0], threads_parameters[i][3]),
                                    ITERATIONS //number of iterations
                                    ); 
    } else {
        unsigned int time;
        
        /*if(i >= 9 && i <= 13) //for C-EDF task set 2
            time = (unsigned int) ((threads_parameters[i][2] / 540) * 2);
        else */
        
        if(i == 7) //for C-EDF task set 1 and 3
            time = (unsigned int) ((threads_parameters[i][2] / 540) * 1);
        else 
            time = (unsigned int) ((threads_parameters[i][2] / 540) * 3);
        
        threads[i] = new ((alloc_priority)(i+2)) Periodic_Thread(&job, 
                                    //(unsigned int) (threads_parameters[i][2] / 775) * 20, //repetitions related to WCET
                                    (unsigned int) time,
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
      
      c.start();
     
      for(int j = 0; j < repetitions; j++) {
        for(int k = (rand->random() % (POLLUTE_BUFFER_SIZE - 1) ) % 1000; k < POLLUTE_BUFFER_SIZE; k += 64) {
            pollute_buffer[k] = j % 64;
            sum += pollute_buffer[k];
        }
      }
      
      c.stop();
      
      wcet[id][i] = c.read();
      
      //if(wcet[id] < c.read())
      //  wcet[id] = c.read();
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
