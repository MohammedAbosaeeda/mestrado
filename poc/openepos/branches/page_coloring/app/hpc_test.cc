#include <utility/ostream.h>
#include <chronometer.h>
#include <thread.h>
#include <display.h>
#include <utility/random.h>
#include <clock.h>
#include <mmu.h>
#include <perf_mon.h>
#include <semaphore.h>
#include <alarm.h>

__USING_SYS

OStream cout;
Thread * a;
Thread * b;
Display d;
Chronometer c1, c2, c3;
PMU::Reg64 total_perf;
Semaphore s; // control the output of perf when __PARALLEL and __BESTCASE
Perf_Mon perf; //used in main

const unsigned int REP = 10000;
const unsigned int ARRAY_SIZE = 16; //16 * 4 = 64 bytes (each int 4 bytes)
const unsigned int NUMBER_ARRAY = 1024; //8 * 64 = 512 bytes
const float SHARED_FACTOR = 1; // % of shared data
const unsigned int SHARED_NUMBER = (unsigned int) NUMBER_ARRAY * SHARED_FACTOR;

volatile unsigned int **shared_array;
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
    array2 = (volatile unsigned int**) new (ALLOC_P_NORMAL) unsigned int *[NUMBER_ARRAY];
    
    // alloc shared data arrays - cache will be disabled
    for(unsigned int i = 0; i < SHARED_NUMBER; i++) {
        array2[i] = new (ALLOC_WR) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) {
            array2[i][j] = 4;
        }
    }

    //alloc non-shared data arrays - cache will be enabled
    for(unsigned int i = SHARED_NUMBER; i < NUMBER_ARRAY; i++) {
        array2[i] = new (ALLOC_P_NORMAL) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) 
            array2[i][j] = 1;
    }
    
#endif
    shared_array = (volatile unsigned int**) new (ALLOC_P_NORMAL) unsigned int *[NUMBER_ARRAY];
    
    // alloc shared data arrays - cache will be disabled
    for(unsigned int i = 0; i < SHARED_NUMBER; i++) {
        shared_array[i] = new (ALLOC_WR) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) {
            shared_array[i][j] = 4;
        }
    }    
    
    //alloc non-shared data arrays - cache will be enabled
    for(unsigned int i = SHARED_NUMBER; i < NUMBER_ARRAY; i++) {
        shared_array[i] = new (ALLOC_P_NORMAL) unsigned int[ARRAY_SIZE];
        for(unsigned int j = 0; j < ARRAY_SIZE; j++) 
            shared_array[i][j] = 1;
    }
}

void delete_arrays()
{
    for(int i = 0; i < NUMBER_ARRAY; i++) {
        delete [] shared_array[i];
    }
    delete [] shared_array;
#ifdef __BESTCASE
    for(int i = 0; i < NUMBER_ARRAY; i++) {
        delete [] array2[i];
    }
    delete [] array2;
#endif
}

int func0(void)
{
    //Perf_Mon perf0;
    //perf0.l1_data_cache_snooped(0);
    //perf0.mem_load_uops_llc_hit_retired();
    //perf0.pipeline_slots_retired_per_cycle();
    //perf0.contested_access();
    
    register unsigned int sum0 = 0;
    
    //s.p();
    //cout << "Func0 running CPU(" << Machine::cpu_id() << ")" << endl;
    //s.v();
    
    c1.start();
    
    for(unsigned int i = 0; i < REP; i++) {
        //cpu_unhalted_ovf += Intel_PMU_Version3::rdmsr(PMU::FIXED_CTR1);
        /*if(i == 1000 | i == 2000 | i == 3000 | i == 4000 | i == 5000 |
          i == 6000 | i == 7000 | i == 8000 | i == 9000) {
          //s.p();
          perf0.overflow_control(true);
          //s.v();
        }*/
        
        /*if(i == 4000 || i == 8000){
            //for(int i = 0; i < Traits<Machine>::MAX_CPUS; i++) {
              //cout << "CPU(" << i << ") DS = " << Thread::_data_sharing[i] << " CA = " << Thread::_contested_accesses[i] << endl;
            //}
            cout << "Metrics = " << (void *) Thread::_metrics_bitmap << endl;
        }*/
        
        for(unsigned int j = 0; j < NUMBER_ARRAY; j++) {
            sum0 = 0;
            
            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                sum0 += shared_array[access_array0[j]][l];
            }

            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                shared_array[access_array0[j]][l] = sum0;
            }
        }      
    }
    
    c1.stop();
/*#ifndef __SEQUENTIAL
    s.p();
    //cout << "0 data cache snooped = " << perf0.l1_data_cache_snooped() << "\n";
    //cout << "0 get_mem_load_uops_llc_hit_retired = " << perf0.get_mem_load_uops_llc_hit_retired() << "\n";
    //cout << "0 get_pipeline_slots_retired_per_cycle = " << perf0.get_pipeline_slots_retired_per_cycle() << "\n";
    cout << "CORE 0 ";
    perf0.get_contested_access();
    s.v();
#else
    //total_perf = perf0.l1_data_cache_snooped();
    //total_perf = perf0.get_mem_load_uops_llc_hit_retired();
    cout << "FUNC 0 ";
    perf0.get_contested_access();
    PMU::disable(PMU::EVTSEL0);
    PMU::disable(PMU::EVTSEL1);
    Intel_PMU_Version3::disable_fixed_ctr0();
    Intel_PMU_Version3::disable_fixed_ctr1();
    //cout << "0 data cache snooped = " << total_perf << "\n";
    //cout << "0 get_mem_load_uops_llc_hit_retired = " << total_perf << "\n";
    //cout << "0 get_pipeline_slots_retired_per_cycle = " << perf0.get_pipeline_slots_retired_per_cycle() << "\n";
#endif */
    return 0;
}

int func1(void)
{
    //Perf_Mon perf1;
    //perf1.l1_data_cache_snooped(0);
    //perf1.mem_load_uops_llc_hit_retired();
    //perf1.pipeline_slots_retired_per_cycle();
    //perf1.contested_access();

    register unsigned int sum0 = 0;
    
    //s.p();
    //cout << "Func1 running CPU(" << Machine::cpu_id() << ")" << endl;
    //s.v();
    
    c2.start();
#ifdef __BESTCASE

    for(unsigned int i = 0; i < REP; i++) {
        /*if(i == 1000 | i == 2000 | i == 3000 | i == 4000 | i == 5000 |
          i == 6000 | i == 7000 | i == 8000 | i == 9000) {
          //s.p();
          perf1.overflow_control(false);
          //s.v();
        }*/
      
        for(unsigned int j = 0; j < NUMBER_ARRAY; j++) {
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
       /* if(i == 1000 | i == 2000 | i == 3000 | i == 4000 | i == 5000 |
          i == 6000 | i == 7000 | i == 8000 | i == 9000) {
          //s.p();
          perf1.overflow_control(false);
          //s.v();
        }*/
        for(unsigned int j = 0; j < NUMBER_ARRAY; j++) {
            sum0 = 0;
            
            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                sum0 += shared_array[access_array1[j]][l];
            }
        
            for(unsigned int l = 0; l < ARRAY_SIZE; l++) {
                shared_array[access_array1[j]][l] = sum0;
            }
        }
    }
    
#endif

    c2.stop();
/*#ifndef __SEQUENTIAL
    s.p();
    cout << "CORE 1 ";
    perf1.get_contested_access();
    //cout << "1 data cache snooped = " << perf1.l1_data_cache_snooped() << "\n";
    //cout << "1 get_mem_load_uops_llc_hit_retired = " << perf1.get_mem_load_uops_llc_hit_retired() << "\n";
    //cout << "1 get_pipeline_slots_retired_per_cycle = " << perf1.get_pipeline_slots_retired_per_cycle() << "\n";
    s.v();
#else
    //total_perf = total_perf + perf1.l1_data_cache_snooped();
    //total_perf = total_perf + perf1.get_mem_load_uops_llc_hit_retired();
    cout << "FUNC 1 ";
    perf1.get_contested_access();
    //PMU::disable(PMU::EVTSEL0);
   // cout << "1 data cache snooped = " << total_perf << "\n";
   //cout << "1 get_mem_load_uops_llc_hit_retired = " << total_perf << "\n";
   //cout << "1 get_pipeline_slots_retired_per_cycle = " << perf1.get_pipeline_slots_retired_per_cycle() << "\n";
#endif */
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

    //perf.contested_access();
    
    init_arrays();
    sort_array_access();
    
    cout << NUMBER_ARRAY << " arrays of " << (ARRAY_SIZE * 4) << " bytes each - Total size = " 
         << (ARRAY_SIZE * 4 * NUMBER_ARRAY) << " SHARED_NUMBER = " << SHARED_NUMBER << endl;
#ifdef __PARALLEL
    a = new Thread(&func0, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE, true);
#else
    a = new Thread(&func0, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE, false);
#endif 
    
#ifdef __SEQUENTIAL
    total_perf = 0;
    int status_a = a->join();
    cout << "Task 0 finished!\n";
#endif
    
#ifdef __PARALLEL
    b = new Thread(&func1, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE, true);
#else
    b = new Thread(&func1, Thread::READY, Thread::NORMAL, Traits<Machine>::APPLICATION_STACK_SIZE, false);
#endif
    
#ifndef __SEQUENTIAL
    int status_a = a->join();
    cout << "Task 0 finished!\n";
#endif
    
    int status_b = b->join();
    cout << "Task 1 finished!\n";

#ifdef __SEQUENTIAL
    cout << "Time: " << c1.read() + c2.read() << "\n";
    //cout << "data cache snooped = " << total_perf << "\n";
    //cout << "MAIN get_mem_load_uops_llc_hit_retired = " << total_perf << "\n";
#else
    cout << "Time: " << ((c1.read() > c2.read()) ? c1.read() : c2.read()) << "\n";
#endif
    
    /*unsigned char test = 0x1;
    for(int  i = 1; i < 8; i++) {
    CPU::bts((unsigned int*)&test, i);
    cout << "test value = " << test;
    CPU::btr((unsigned int*)&test, i);
    cout << " new value = " << test << endl;
    }*/
    
    //cout << "MAIN ";
    //perf.get_contested_access();
    
    delete_arrays();
    
    delete a;
    delete b;
    
    while(1); // avoid restarting
    return 0;
}