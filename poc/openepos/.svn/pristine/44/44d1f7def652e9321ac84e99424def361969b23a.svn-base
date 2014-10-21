#include <utility/ostream.h>
#include <chronometer.h>
#include <thread.h>
#include <display.h>
#include <utility/random.h>
#include <clock.h>
#include <mmu.h>
#include <perf_mon.h>
#include <semaphore.h>

__USING_SYS

OStream cout;
Thread * a;
Thread * b;
Display d;
Chronometer c1, c2, c3;
PMU::Reg64 total_perf;
Semaphore s; // control the output of perf when __PARALLEL and __BESTCASE

const unsigned int REP = 10000;
const unsigned int ARRAY_SIZE = 16; //16 * 4 = 64 bytes (each int 4 bytes)
const unsigned int NUMBER_ARRAY = 196608; //8 * 64 = 512 bytes
const float SHARED_FACTOR = 1; // % of shared data
#ifdef __BESTCASE
const unsigned int SHARED_NUMBER = (unsigned int) (NUMBER_ARRAY / 2) * SHARED_FACTOR;
#else
const unsigned int SHARED_NUMBER = (unsigned int) NUMBER_ARRAY * SHARED_FACTOR;
#endif

volatile unsigned int **shared_array;
//unsigned int **private_array0;
//unsigned int **private_array1;
unsigned int access_array0[NUMBER_ARRAY];
unsigned int access_array1[NUMBER_ARRAY];

#ifdef __BESTCASE
volatile unsigned int **array2;
#endif

void sort_array_access()
{
    Pseudo_Random rand;
    Clock clock;
    rand.seed(clock.now());
            
    for(int i = 0; i < SHARED_NUMBER; i++) {
        access_array0[i] = rand.random() % NUMBER_ARRAY;
    }
    
    for(int i = 0; i < SHARED_NUMBER; i++) {
        access_array1[i] = rand.random() % NUMBER_ARRAY;
    }
}

void init_arrays()
{
#ifdef __BESTCASE
    array2 = (volatile unsigned int**) new (ALLOC_P_NORMAL) unsigned int *[NUMBER_ARRAY/2];
    
    // alloc shared data arrays - cache will be disabled
    for(unsigned int i = 0; i < SHARED_NUMBER; i++) {
        array2[i] = new (ALLOC_WR) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) {
            array2[i][j] = 4;
        }
    }

    //alloc non-shared data arrays - cache will be enabled
    /*for(unsigned int i = SHARED_NUMBER; i < NUMBER_ARRAY; i++) {
        array2[i] = new (ALLOC_P_NORMAL) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) 
            array2[i][j] = 1;
    }*/
    
    //alloc non-shared data arrays - cache will be enabled
    /*if(SHARED_NUMBER != NUMBER_ARRAY) {
        private_array0 = new unsigned int *[NUMBER_ARRAY - SHARED_NUMBER];
        private_array1 = new unsigned int *[NUMBER_ARRAY - SHARED_NUMBER];
        for(unsigned int i = 0; i < (NUMBER_ARRAY - SHARED_NUMBER); i++) {
              private_array0[i] = new unsigned int[ARRAY_SIZE];
              private_array1[i] = new unsigned int[ARRAY_SIZE];
        }
    }*/
    
    shared_array = (volatile unsigned int**) new (ALLOC_P_NORMAL) unsigned int *[NUMBER_ARRAY/2];
    
#else
    shared_array = (volatile unsigned int**) new (ALLOC_P_NORMAL) unsigned int *[NUMBER_ARRAY];
#endif
    
    // alloc shared data arrays - cache will be disabled
    for(unsigned int i = 0; i < SHARED_NUMBER; i++) {
        shared_array[i] = new (ALLOC_WR) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) {
            shared_array[i][j] = 4;
        }
    }    
    
    /*//alloc non-shared data arrays - cache will be enabled
    for(unsigned int i = SHARED_NUMBER; i < NUMBER_ARRAY; i++) {
        shared_array[i] = new (ALLOC_P_NORMAL) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) 
            shared_array[i][j] = 1;
    }*/
    
    //alloc non-shared data arrays - cache will be enabled
    /*if(SHARED_NUMBER != NUMBER_ARRAY) {
        private_array0 = new unsigned int *[NUMBER_ARRAY - SHARED_NUMBER];
        private_array1 = new unsigned int *[NUMBER_ARRAY - SHARED_NUMBER];
        for(unsigned int i = 0; i < (NUMBER_ARRAY - SHARED_NUMBER); i++) {
              private_array0[i] = new unsigned int[ARRAY_SIZE];
              private_array1[i] = new unsigned int[ARRAY_SIZE];
        }
    }*/
}

void delete_arrays()
{
#ifdef __BESTCASE
    for(int i = 0; i < NUMBER_ARRAY/2; i++) {
#else
    for(int i = 0; i < NUMBER_ARRAY; i++) {
#endif
        delete [] shared_array[i];
    }
    delete [] shared_array;
#ifdef __BESTCASE
    for(int i = 0; i < NUMBER_ARRAY/2; i++) {
        delete [] array2[i];
    }
    delete [] array2;
#endif
}

int func0(void)
{
    Perf_Mon perf0;
    //perf0.l1_data_cache_snooped(0);
    perf0.mem_load_uops_llc_hit_retired();

    register unsigned int sum0 = 0;
    
    c1.start();
    
    for(unsigned int i = 0; i < REP; i++) {
#ifdef __BESTCASE
        for(unsigned int j = 0; j < NUMBER_ARRAY/2; j++) {
#else
        for(unsigned int j = 0; j < NUMBER_ARRAY; j++) {
#endif
            sum0 = 0;
            
            //for(unsigned int k = 0; k < SHARED_NUMBER; k++) {
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    //sum0 += shared_array[access_array0[k]][l];
                    sum0 += shared_array[access_array0[j]][l];
                }
            //}
            
            //for(unsigned int k = 0; k < SHARED_NUMBER; k++) {
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    //shared_array[access_array0[k]][l] = sum0;
                    shared_array[access_array0[j]][l] = sum0;
                }
#ifdef __BESTCASE
        }
        for(unsigned int j = 0; j < NUMBER_ARRAY/2; j++) {
            sum0 = 0;
            
            //for(unsigned int k = 0; k < SHARED_NUMBER; k++) {
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    //sum0 += shared_array[access_array0[k]][l];
                    sum0 += shared_array[access_array0[j]][l];
                }
            //}
            
            //for(unsigned int k = 0; k < SHARED_NUMBER; k++) {
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    //shared_array[access_array0[k]][l] = sum0;
                    shared_array[access_array0[j]][l] = sum0;
                }
#endif
            //}
            
           /* for(unsigned int k = SHARED_NUMBER; k < SHARED_NUMBER + ((NUMBER_ARRAY - SHARED_NUMBER) / 2); k++) {
                //cout << "func0 access array[" << k << "]\n";
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    sum0 += shared_array[k][l];
                }
            }
            
            for(unsigned int k = SHARED_NUMBER; k < SHARED_NUMBER + ((NUMBER_ARRAY - SHARED_NUMBER) / 2); k++) {
                //cout << "func0 access array[" << k << "]\n";
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    shared_array[k][l] = sum0;
                }
            }*/
            
            /*if(SHARED_NUMBER != NUMBER_ARRAY) {
                for(unsigned int k = 0; k < (NUMBER_ARRAY - SHARED_NUMBER); k++) {
                    for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                      sum0 += private_array0[k][l];
                      private_array0[k][l] = sum0;
                    }
                }
            }*/
        }      
    }
    
    c1.stop();
#ifndef __SEQUENTIAL
    s.p();
    //cout << "0 data cache snooped = " << perf0.l1_data_cache_snooped() << "\n";
    cout << "0 get_mem_load_uops_llc_hit_retired = " << perf0.get_mem_load_uops_llc_hit_retired() << "\n";
    s.v();
#else
    //total_perf = perf0.l1_data_cache_snooped();
    total_perf = perf0.get_mem_load_uops_llc_hit_retired();
    PMU::disable(PMU::EVTSEL0);
    //cout << "0 data cache snooped = " << total_perf << "\n";
    cout << "0 get_mem_load_uops_llc_hit_retired = " << total_perf << "\n";
#endif 
    return 0;
}

int func1(void)
{
    Perf_Mon perf1;
    //perf1.l1_data_cache_snooped(0);
    perf1.mem_load_uops_llc_hit_retired();

    register unsigned int sum0 = 0;
    c2.start();
#ifdef __BESTCASE

    for(unsigned int i = 0; i < REP; i++) {
        for(unsigned int j = 0; j < NUMBER_ARRAY/2; j++) {
            sum0 = 0;
            
            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                sum0 += array2[access_array1[j]][l];
            }
            
            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                array2[access_array1[j]][l] = sum0;
            }
        }
        
        for(unsigned int j = 0; j < NUMBER_ARRAY/2; j++) {
            sum0 = 0;
            
            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                sum0 += array2[access_array1[j]][l];
            }

            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                array2[access_array1[j]][l] = sum0;
            }
        }
    }
    
#else
    
    for(unsigned int i = 0; i < REP; i++) {
        for(unsigned int j = 0; j < NUMBER_ARRAY; j++) {
            sum0 = 0;
            
            //for(unsigned int k = 0; k < SHARED_NUMBER; k++) {
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    //sum0 += shared_array[access_array1[k]][l];
                    sum0 += shared_array[access_array1[j]][l];
                }
            //}
            
            //for(unsigned int k = 0; k < SHARED_NUMBER; k++) {
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    //shared_array[access_array1[k]][l] = sum0;
                    shared_array[access_array1[j]][l] = sum0;
                }
            //}
            
            /*for(unsigned int k = SHARED_NUMBER + ((NUMBER_ARRAY - SHARED_NUMBER) / 2); k < NUMBER_ARRAY; k++) {
                //cout << "func1 access array[" << k << "]\n";
                for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                    sum0 += shared_array[k][l];
                    shared_array[k][l] = sum0;
                }
            }*/
            
            /*if(SHARED_NUMBER != NUMBER_ARRAY) {
                for(unsigned int k = 0; k < (NUMBER_ARRAY - SHARED_NUMBER); k++) {
                    for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                      sum0 += private_array1[k][l];
                      private_array1[k][l] = sum0;
                    }
                }
            }*/
        }
    }
    
#endif

    c2.stop();
#ifndef __SEQUENTIAL
    s.p();
    //cout << "1 data cache snooped = " << perf1.l1_data_cache_snooped() << "\n";
    cout << "1 get_mem_load_uops_llc_hit_retired = " << perf1.get_mem_load_uops_llc_hit_retired() << "\n";
    s.v();
#else
    //total_perf = total_perf + perf1.l1_data_cache_snooped();
    total_perf = total_perf + perf1.get_mem_load_uops_llc_hit_retired();
    PMU::disable(PMU::EVTSEL0);
   // cout << "1 data cache snooped = " << total_perf << "\n";
   cout << "1 get_mem_load_uops_llc_hit_retired = " << total_perf << "\n";
#endif    
    return 1;
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
  
    init_arrays();
    sort_array_access();
    
    cout << NUMBER_ARRAY << " arrays of " << (ARRAY_SIZE * 4) << " bytes each - Total size = " 
         << (ARRAY_SIZE * 4 * NUMBER_ARRAY) << " SHARED_NUMBER = " << SHARED_NUMBER << endl;
        
    a = new Thread(&func0, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE, true);
#ifdef __SEQUENTIAL
    total_perf = 0;
    int status_a = a->join();
    cout << "Task 0 finished!\n";
#endif
    b = new Thread(&func1, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE, true);

#ifndef __SEQUENTIAL
    int status_a = a->join();
    cout << "Task 0 finished!\n";
#endif
    
    int status_b = b->join();
    cout << "Task 1 finished!\n";

#ifdef __SEQUENTIAL
    cout << "Time: " << c1.read() + c2.read() << "\n";
    //cout << "data cache snooped = " << total_perf << "\n";
    cout << "MAIN get_mem_load_uops_llc_hit_retired = " << total_perf << "\n";
#else
    s.p();
    cout << "Time: " << ((c1.read() > c2.read()) ? c1.read() : c2.read()) << "\n";
    s.v();
#endif
    
    delete_arrays();
    
    delete a;
    delete b;
    
    while(1); // avoid restarting
    return 0;
}