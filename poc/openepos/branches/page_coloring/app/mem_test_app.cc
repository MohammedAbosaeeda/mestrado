// Page Coloring test 

#include <utility/ostream.h>
#include <display.h>
#include <periodic_thread.h>
#include <rtc.h>
#include <chronometer.h>
#include <semaphore.h>

__USING_SYS

Display d;
OStream cout;
Semaphore s;

#define WSS 4094*256
#define X_WSS (WSS/4094)
#define REPETITIONS 20
#define ITERATIONS 1000
#define THREADS 16
Periodic_Thread * threads[THREADS];
RTC::Microsecond wcet[THREADS];

int job(unsigned int, int);
  
int main()
{
  d.clear();
  cout << "Page coloring test\n";
    
  for(int i = 0; i <  THREADS; i++) {
    wcet[i] = 0;
    threads[i] = new Periodic_Thread(&job, 
                      (unsigned int) 10000, //WCET
                      i, //ID
                      100000, //period
                      ITERATIONS); //number of iterations
  }
  
  for(int i = 0; i <  THREADS; i++) {
    threads[i]->join();
  }
      
  //for(int i = 0; i <  THREADS; i++) {
  //  delete threads[i];
  //}
  
  cout << "Page coloring test done!\n";
  for(int i = 0; i <  THREADS; i++) {
    cout << "wcet[" << i << "] = " << wcet[i] << "\n";
  }
  
  while(1);
}

int job(unsigned int exec_time, int id)
{
    int sum = 0;
    Chronometer c;
    
    char *array[X_WSS];
    int colors = 4;
    
    s.p();
    for(int i = 0; i < X_WSS; i++) {
        array[i] = new ((alloc_priority) (1 + (i % colors) + id * colors)) char[4092];
    }
    s.v();    
    
    for(int i = 0; i <  ITERATIONS; i++) {
      Periodic_Thread::wait_next();
      //s.p();
      //cout << "Thread " << id << "\n";
      //s.v();
      c.start();
      for(int j = 0; j < REPETITIONS; j++) {
        for(int i = 0; i < X_WSS; i++) {
          for(int k = 0; k < 4092; k++) {
            array[i][k] = i + k;
            sum += array[i][k];
          }
        }
      }
      c.stop();
      //cout << "Exec Time = " << c.read() << "\n";
      if(wcet[id] < c.read())
        wcet[id] = c.read();
      c.reset();
    }
    
    return sum;
}