#include <utility/ostream.h>
#include <chronometer.h>
#include <thread.h>
#include <display.h>
//#include <perf_mon.h>

__USING_SYS

OStream cout;

Thread * a;
Thread * b;
Display d;
Chronometer c1, c2, c3;

const unsigned int REP = 10000;
const unsigned int ROWS = 8;
const unsigned int COLS = 8;

//volatile unsigned int array0[ROWS][COLS];
//volatile unsigned int array1[ROWS][COLS];
//volatile unsigned int **array0;
//volatile unsigned int **array1;
volatile unsigned int *array0;
volatile unsigned int *array1;

void init_arrays()
{
    for(unsigned int j = 0; j < ROWS*COLS; j++)
    //for(unsigned int k = 0; k < COLS; k++)
            array0[j] = 2;

    //for(unsigned int j = 0; j < ROWS; j++)
    for(unsigned int k = 0; k < COLS*ROWS; k++)
            array1[k] = 2;
}

bool check_arrays()
{
    return true;
}

int func0(void)
{
    register unsigned int sum0;
#ifdef __PARALLEL
    register unsigned int sum1;
#endif
   
    c1.start();
    
    for(unsigned int i = 0; i <= REP; i++) {
    for(unsigned int j = 0; j < ROWS*COLS; j++) {
        sum0 = 0;
        
#ifdef __PARALLEL
        sum1 = 0;
#endif
        
#ifdef __READ
        for(unsigned int k = 0; k < COLS*ROWS; k++) {
#ifdef __PARALLEL
        if(k % 2 == 0) {
#endif
            //sum0 += array0[j][k];
            sum0 += array0[k];
#ifdef __PARALLEL
            //sum1 += array1[j][k];
            sum1 += array1[k];
        }
#endif
        }
#endif

#ifdef __WRITE
        for(unsigned int k = 0; k < COLS*ROWS; k++) {
#ifdef __PARALLEL
        if(k % 2 == 0) {
#endif
            //array0[j][0] = sum0;
            array0[j] = sum0;
#ifdef __PARALLEL
            //array1[j][0] = sum1;
            array1[j] = sum1;
        }
#endif
        }
#endif
    }
    }
    
    c1.stop();  
    return 0;
}

int func1(void)
{   
#ifdef __PARALLEL
    register unsigned int sum0;
#endif
    register unsigned int sum1;
        
    c2.start();

    for(unsigned int i = 0; i <= REP; i++) {
    for(unsigned int j = 0; j < ROWS*COLS; j++) {
#ifdef __PARALLEL
        sum0 = 0;
#endif
        sum1 = 0;

#ifdef __READ
        for(unsigned int k = 0; k < COLS*ROWS; k++) {
#ifdef __PARALLEL
        if(k % 2 == 0) {
        //sum0 += array0[j][k];
        sum0 += array0[k];
#endif
        //sum1 += array1[j][k];
        sum1 += array1[k];
#ifdef __PARALLEL
        }
#endif
        }
#endif

#ifdef __WRITE
        for(unsigned int k = 0; k < COLS*ROWS; k++) {
#ifdef __PARALLEL
        if(k % 2 == 0) {
            //array0[j][0] = sum0;
            array0[j] = sum0;
#endif
            //array1[j][0] = sum1;
            array1[j] = sum1;
#ifdef __PARALLEL
        }
#endif
        }
#endif
    }
    }
    
    c2.stop();
    return 0;
}


int main()
{
    d.clear();
#ifdef __SEQUENTIAL
    cout << "SMP Cache Test: SEQUENTIAL ";
#endif
#ifdef __BESTCASE
    cout << "SMP Cache Test: BEST-CASE ";
#endif
#ifdef __PARALLEL
    cout << "SMP Cache Test: PARALLEL ";
#endif
#ifdef __READ
    cout << "READ";
#endif
#if defined __READ && defined __WRITE
    cout << "/";
#endif
#ifdef __WRITE
    cout << "WRITE";
#endif
    cout <<" COLS = " << COLS << " ROWS = " <<  ROWS << "\n\n";
    
    
    //while(1);
    /*array0 = (volatile unsigned int**) new (ALLOC_WR) unsigned int*[COLS];
    for (int i = 0; i < COLS; ++i)
      array0[i] = (volatile unsigned int*) new (ALLOC_WR) unsigned int[ROWS];
    
    array1 = (volatile unsigned int**) new (ALLOC_WR) unsigned int*[COLS];
    for (int i = 0; i < COLS; ++i)
      array1[i] = (volatile unsigned int*) new (ALLOC_WR) unsigned int[ROWS];*/
    array0 = new (ALLOC_P_NORMAL)  unsigned int[ROWS * COLS];
    array1 = new (ALLOC_P_NORMAL)  unsigned int[ROWS * COLS];
    
    //while(1);
    
#ifdef __SEQUENTIAL
    //perf.bus_snoops(Intel_Core_Micro_PMU::HIT);
#endif    
    cout << "Starting task 0 ... ";
    c3.start();
#ifndef __SEQUENTIAL
#ifdef __PARALLEL
    //a = new Thread(&func0);
    a = new Thread(&func0, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE);
#else
    a = new Thread(&func0, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE);
    
    //a = new Thread(&func0);
#endif
#else
    func0();
#endif

#ifndef __SEQUENTIAL
    cout << " done!\n";
#endif

#ifdef __SEQUENTIAL
    cout << "Task 0 finished!\n";
#endif

    cout << "Starting task 1 ... ";
#ifndef __SEQUENTIAL
#ifdef __PARALLEL
    //b = new Thread(&func1);
    b = new Thread(&func1, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE);
#else
    //b = new Thread(&func1);
    b = new Thread(&func1, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE);
#endif
#else
    func1();
#endif

#ifndef __SEQUENTIAL
    cout << " done!\n";
#endif

#ifndef __SEQUENTIAL
    int status_a = a->join();
    cout << "Task 0 finished!\n";
#endif
    
#ifndef __SEQUENTIAL
    int status_b = b->join();
    c3.stop();
    cout << "Task 1 finished!\n";
#endif
    
#ifdef __SEQUENTIAL
    //cout << "\nNumber of Events 1 = " << (void *)PMU::rdpmc(Intel_Core_Duo_PMU::PMC0) << "\n";
#endif
    
#ifdef __SEQUENTIAL
    kout << "Time: " << c1.read() + c2.read() << "\n";
#else
    kout << "Time: " << ((c1.read() > c2.read()) ? c1.read() : c2.read()) << "\n";
#endif
    
    kout << "C1 Time: " << c1.read() << 
    " C2 Time: " << c2.read() << " C3 Time: " 
    << c3.read() << "\n";
    
    delete a;
    delete b;
    
    /*for (int i = 0; i < COLS; ++i) {
      delete [] array0[i];
      delete [] array1[i];
    }*/
  delete [] array0;
  delete [] array1;
    
    while(1);

    return 0;
}

/*int main()
{

    Thread * a;
    //Thread * b;
    cout << "Heap test\n";
    
    cout << "Allocing 4KB of memory!\n";
    data = new (ALLOC_P_HIGH) unsigned int[1024]; // alloc 4KB    
    cout << "Allocated!\n";    
    data[1] = 10;    
    cout << "Data[1] = " << data[1] << " address = " << (void *) data << " address 2 = " << (void *) &data[1] << "\n";    
    test = new (ALLOC_WR) unsigned int[5];
    delete test;
    delete data;
    
    cout << "Creating data\n";
    //data = new (ALLOC_P_NORMAL) unsigned int[ROWS];
    
    a = new Thread(&func0);
    //b = new Thread(&func1);
    
    cout << "Threads created\n";
    
    //int status_a = a->join();
    //int status_b = b->join();
    
    //cout << "Time: " << ((c1.read() > c2.read()) ? c1.read() : c2.read()) << "\n";
    
    //delete a;
    //delete b;
    //delete data;
    
    return 0;
}*/