// GEDF test with WCET

#include <utility/ostream.h>
#include <periodic_thread.h>
#include <clock.h>
#include <chronometer.h>
#include <display.h>
#include <semaphore.h>
#include <tsc.h>
#include "gedf_perf/include/math.h"
#include <timer.h>
#include <perf_mon.h>
#include "data_sizes.h"
#include <utility/random.h>

__USING_SYS

Display d;
OStream cout;
Semaphore s;

double *l2_cache_hit_impact(const volatile unsigned long long l2_hit[], const volatile unsigned long long cycles[], int size)
{
    double *avg = (double *) malloc(sizeof(double) * size);
    for(int i = 0; i < size; i++) {
        //avg[i] = (double) l2_hit[i] / cycles[i];
        avg[i] = (double) l2_hit[i];
    }
    return avg;
}

double *llc_hit_impact(const volatile unsigned long long llc_hit[], const volatile unsigned long long xnsp_hit[], const volatile unsigned long long cycles[], int size)
{
    double *avg = (double *) malloc(sizeof(double) * size);
    for(int i = 0; i < size; i++) {
        //avg[i] =  (double) (llc_hit[i] + xnsp_hit[i]) / cycles[i];
        avg[i] =  (double) (llc_hit[i] + xnsp_hit[i]);
    }
    return avg;
}

double *llc_miss_impact(const volatile unsigned long long llc_miss[], const volatile unsigned long long cycles[], int size)
{
    double *avg = (double *) malloc(sizeof(double) * size);
    for(int i = 0; i < size; i++) {
        //avg[i] = (double) llc_miss[i] / cycles[i];
        avg[i] = (double) llc_miss[i];
    }
    return avg;
}

//double mean(const volatile double t[], int size, double * h)
double mean(const volatile unsigned long long t[], int size, double * h)
{   
    double sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        //cout << "[" << i<< "] = " << t[i] << " ";
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    //cout << endl;
    return sum / size;
}

//double variance(const volatile double t[], int size, double mean)
double variance(const volatile unsigned long long t[], int size, double mean)
{
    double v = 0.0;
    for(int i = 0; i < size; i++) {
        double tmp = (mean - t[i]);
        v = v + (tmp * tmp);
        cout << "";
        //cout << "v = " << (unsigned int) v << " "; 
    }
    //cout << " v TOTAL = " << (unsigned int) v << endl;
    //cout << "\n";
    return (v / (double) (size -1));
}

double standard_deviation(double variance)
{
    //cout << "variance = " << variance;
    double r = sqrt(variance);
    //cout << " sqrt(var) = " << r << endl;
    return r;
}

double ts_to_us(double ts)
{
    return (ts * 1000000) / CPU::clock();
}

void print_perf_stats(void);
void reset_thread_counters();

#define ITERATIONS 1000 // number of times each thread will be executed
#define THREADS 1 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

// period, deadline (microseconds), factor to be multiplied by the loop that gives the WCET
unsigned int threads_parameters[][3] = {
{ 20000, 20000, 1 }, //~6ms
};

// 512B  10000
// 1KB   5000
// 4KB   2460
// 128KB 77
// 512KB 19
// 1MB   10
// 2MB   5
// 10MB  1

#define WSS MB_10
#define TSS MB_10
#define TSS_STEP ((WSS)/(TSS))
#define WRITE_RATE 0 //1 = 100%, 2 = 50%, 3 = 33%, 4 = 25%, 5 = 20%, 10 = 10%

int job(int, int); // function passed by each periodic thread

int access_array[WSS];

int main()
{
    d.clear();
    cout << "Cache miss measurement test\n";
    cout << "Threads " << THREADS << " WSS = " << WSS / 1024 << "KB TSS = " << TSS / 1024 << 
    "KB TSS_STEP = " << TSS_STEP << " WRITE_RATE = " << WRITE_RATE << endl;
    
    Chronometer chrono;
    /*int *array[THREADS];
    
    for(int i = 0; i <  THREADS; i++) {
        array[i] = new int[WSS];
        for(int j = 0; j < WSS; j++)
           array[i][j] = j;
    }*/
    
    Pseudo_Random rand;
    Clock clock;
    rand.seed(clock.now());
    for(int i = 0; i < WSS; i++) {
      access_array[i] = rand.random() % WSS;
    }
    
    
    for(int i = 0; i <  THREADS; i++) {
        //cout << "creating thread = " << i << endl;
        threads[i] = new Periodic_Thread(&job, 
                                         //array[i],
                                         (int)threads_parameters[i][2], //loop multiplicator
                                         i, //id
                                         threads_parameters[i][0], //period
                                         ITERATIONS); //number of iterations
        //cout << "
    }
    
    reset_thread_counters();
    
    chrono.start();
    for(int i = 0; i <  THREADS; i++) {
        threads[i]->join();
    }
    chrono.stop();
    
    cout << "GEDF test done in " << chrono.read() / 1000000 << " seconds \n";
    cout << "GEDF test done in " << chrono.read() / 1000000 << " seconds \n";
    
    Timer::unset_channel(Timer::ALARM); // stop tick counting
       
    print_perf_stats();
    print_perf_stats();
    
    while(1); // avoid restarting on real machines
    return 0;
}

void reset_thread_counters()
{
   for(int j = 0; j < THREADS; j++) {
      threads[j]->_buffer->counter = 0;
      for(int i = 0; i < Perf_Mon::MAX_DATA; i++) {
          threads[j]->_buffer->l2_hit[i] = 0;
          threads[j]->_buffer->llc_hit[i] = 0;
          threads[j]->_buffer->xnsp_hit[i] = 0;
          threads[j]->_buffer->llc_miss[i] = 0;
          threads[j]->_buffer->cycles[i] = 0;
      }
   }
}

void print_perf_stats(void)
{
    double l2_hit_mean, l2_hit_wc, l2_hit_std;
    double llc_hit_mean, llc_hit_wc, llc_hit_std;
    double snoop_hit_mean, snoop_hit_wc, snoop_hit_std;
    double llc_miss_mean, llc_miss_wc, llc_miss_std;
  
    for(int i = 0; i < THREADS; i++) {
      //cout << "thread[" << i << "] counter = " << threads[i]->_buffer->counter << endl;
      if(threads[i]->_buffer->counter) {
        double var;
        l2_hit_mean = mean(threads[i]->_buffer->l2_hit, threads[i]->_buffer->counter, &l2_hit_wc);
        var = variance(threads[i]->_buffer->l2_hit, threads[i]->_buffer->counter, l2_hit_mean);
        l2_hit_std = standard_deviation(var);
        
        llc_hit_mean = mean(threads[i]->_buffer->llc_hit, threads[i]->_buffer->counter, &llc_hit_wc);
        var = variance(threads[i]->_buffer->llc_hit, threads[i]->_buffer->counter, llc_hit_mean);
        llc_hit_std = standard_deviation(var);
        
        snoop_hit_mean = mean(threads[i]->_buffer->xnsp_hit, threads[i]->_buffer->counter, &snoop_hit_wc);
        var = variance(threads[i]->_buffer->xnsp_hit, threads[i]->_buffer->counter, snoop_hit_mean);
        snoop_hit_std = standard_deviation(var);
        
        llc_miss_mean = mean(threads[i]->_buffer->llc_miss, threads[i]->_buffer->counter, &llc_miss_wc);
        var = variance(threads[i]->_buffer->llc_miss, threads[i]->_buffer->counter, llc_miss_mean);
        llc_miss_std = standard_deviation(var);
      }
      
      cout << "L2 HIT WC = " << l2_hit_wc << " AVG = " << l2_hit_mean << " STD = " << l2_hit_std;
      cout << " LLC HIT WC = " << llc_hit_wc << " AVG = " << llc_hit_mean << " STD = " << llc_hit_std;
      cout << " SNOOP HIT WC = " << snoop_hit_wc << " AVG = " << snoop_hit_mean << " STD = " << snoop_hit_std;
      cout << " LLC MISS WC = " << llc_miss_wc << " AVG = " << llc_miss_mean << " STD = " << llc_miss_std << endl;
    }
    
}
/*
void print_perf_stats(void)
{
    double total_avg_l2_hit_impact = 0;
    double total_wc_l2_hit_impact = 0;
    double total_std_l2_hit_impact = 0;
    
    double total_avg_llc_hit_impact = 0;
    double total_wc_llc_hit_impact = 0;
    double total_std_llc_hit_impact = 0;
    
    double total_avg_llc_miss_impact = 0;
    double total_wc_llc_miss_impact = 0;
    double total_std_llc_miss_impact = 0;
    
    for(int i = 0; i < THREADS; i++) {
      //cout << "thread[" << i << "] counter = " << threads[i]->_buffer->counter << endl;
      if(threads[i]->_buffer->counter) {
        double *l2_HI = l2_cache_hit_impact(threads[i]->_buffer->l2_hit, threads[i]->_buffer->cycles, threads[i]->_buffer->counter);
        double *llc_HI = llc_hit_impact(threads[i]->_buffer->llc_hit, threads[i]->_buffer->xnsp_hit, threads[i]->_buffer->cycles, threads[i]->_buffer->counter);
        double *llc_MI = llc_miss_impact(threads[i]->_buffer->llc_miss, threads[i]->_buffer->cycles, threads[i]->_buffer->counter);
        double avg_l2_hit_impact;
        double l2_hi_std;
        double avg_llc_hit_impact;
        double llc_hi_std;
        double avg_llc_miss_impact;
        double llc_mi_std;
        double worst_case;
        double var;
        
        avg_l2_hit_impact = mean(l2_HI, threads[i]->_buffer->counter, &worst_case);
        var = variance(l2_HI, threads[i]->_buffer->counter, avg_l2_hit_impact);
        l2_hi_std = standard_deviation(var);
        
        total_avg_l2_hit_impact += avg_l2_hit_impact;
        total_std_l2_hit_impact += l2_hi_std;
        if(worst_case > total_wc_l2_hit_impact)
            total_wc_l2_hit_impact = worst_case;
        
        avg_llc_hit_impact = mean(llc_HI, threads[i]->_buffer->counter, &worst_case);
        var = variance(llc_HI, threads[i]->_buffer->counter, avg_llc_hit_impact);
        llc_hi_std = standard_deviation(var);

        total_avg_llc_hit_impact += avg_llc_hit_impact;
        total_std_llc_hit_impact += llc_hi_std;
        if(worst_case > total_wc_llc_hit_impact)
            total_wc_llc_hit_impact = worst_case;
        
        avg_llc_miss_impact = mean(llc_MI, threads[i]->_buffer->counter, &worst_case);
        var = variance(llc_MI, threads[i]->_buffer->counter, avg_llc_miss_impact);
        llc_mi_std = standard_deviation(var);

        total_avg_llc_miss_impact += avg_llc_miss_impact;
        total_std_llc_miss_impact += llc_mi_std;
        if(worst_case > total_wc_llc_miss_impact)
            total_wc_llc_miss_impact = worst_case; 
        
        delete l2_HI;
        delete llc_HI;
        delete llc_MI;
      }
    }
    //cout << "TOTAL L2 HI = " << total_avg_l2_hit_impact << " TOTAL STD = " << total_std_l2_hit_impact << endl;
    cout << "L2 HI = " << total_avg_l2_hit_impact / THREADS << " STD " << total_std_l2_hit_impact / THREADS << " WC = " << total_wc_l2_hit_impact;
    cout << " LLC HI = " << total_avg_llc_hit_impact / THREADS << " STD " << total_std_llc_hit_impact / THREADS << " WC = " << total_wc_llc_hit_impact;
    cout << " LLC MI = " << total_avg_llc_miss_impact / THREADS << " STD " << total_std_llc_miss_impact / THREADS << " WC = " << total_wc_llc_miss_impact << endl;
}
*/
int job(int factor, int id)
{
    Perf_Mon perf;
    //Pseudo_Random rand;
    //Clock clock;
    //rand.seed(clock.now());
    //int *array = new int[WSS];
    int array[WSS];
    //for(int i = 0; i < WSS; i++)
    //    array[i] = i;
    //Chronometer c;
    int sum = 0;
    int sum_total = 0;
    for(int i = 0; i <  ITERATIONS; i++) {
      //s.p();
      //cout << "Thread " << id << " => " << Machine::cpu_id() << " iterations = " << i << "\n";
      //s.v();
      Periodic_Thread::wait_next();
      //cout << "ITERATION = " << i << endl;
      int write = 0;
      //c.reset();
      //c.start();
      sum = i;
      
      perf.reset_pmc0();
      perf.reset_pmc1();
      perf.reset_pmc2();
      perf.reset_pmc3();
      perf.pebs_test();
      
      asm("wbinvd");
      
      for(int k = 0; k < factor; k++) {
        perf.reset_fixed_ctr1();
        for(int j = 0; j < WSS; j++) {
          
          sum += array[access_array[j]];
          
          /*if((WRITE_RATE != 0) && (write % WRITE_RATE) == 0) {
            array[j] = sum * i - 1;
            write++;
          }*/
          
        }
        
        if(k == 0) {
            //cout << "cycles = " << perf.get_cpu_clk_unhalted_core() << "\n";
            perf.get_pebs_test(threads[id]->_buffer, false);
        }
      }
      
      sum_total += sum;
      //c.stop();
      //cout << "time(" << i << ") = " << c.read() << endl;
    }
    //s.p();
    //cout << "Thread " << id << " => " << Machine::cpu_id() << " ended\n";
    //s.v();
    
    return sum_total;
}
