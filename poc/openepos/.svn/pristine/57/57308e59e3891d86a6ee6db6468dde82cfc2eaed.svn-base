// CEDF test with WCET

#include <utility/ostream.h>
#include <periodic_thread.h>
#include <clock.h>
#include <chronometer.h>
#include <display.h>
#include <semaphore.h>
#include <tsc.h>
#include <timer.h>

__USING_SYS

Display d;
OStream cout;
Semaphore s;

unsigned long long mean(const volatile TSC::Time_Stamp t[], int size, TSC::Time_Stamp * h)
{   
    TSC::Time_Stamp sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return (unsigned long long) (sum / size) * 1000;
}

unsigned long long mean(unsigned long long t[], int size, unsigned long long * h)
{   
    unsigned long long sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return (unsigned long long) (sum / size) * 1000;
}

unsigned long long mean_wo_mul(unsigned long long t[], int size, unsigned long long * h)
{   
    unsigned long long sum = 0;
    *h = 0;
    for(int i = 0; i < size; i++) {
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return (unsigned long long) (sum / size);
}

TSC::Time_Stamp find_highest(TSC::Time_Stamp t[], int size)
{
    TSC::Time_Stamp wc = 0;
    int j = 0;
    for(int i = 0; i <  size; i++) {
        if(t[i] > wc) { 
          wc = t[i];
          j = i;
        }
    }
    
    return wc;
}

unsigned long long variance(unsigned long long t[], int size, unsigned long long mean)
{
    unsigned long long v = 0;
    mean = (unsigned long long ) mean / 1000;
    for(int i = 0; i < size; i++) {
        unsigned long long tmp = (mean - (t[i]));
        v = v + (tmp * tmp);
        cout << "";
    }
    return (unsigned long long) (v / (size -1));
}

/*
double standard_deviation(double variance)
{
    //cout << "variance = " << variance;
    double r = sqrt(variance);
    //cout << " sqrt(var) = " << r << endl;
    return r;
}*/

/*double ts_to_us(double ts)
{
    return (ts * 1000000) / CPU::clock();
}*/

void print_alarm(void);
void print_thread_stats(void);
void print_perf_stats(void);
void collect_thread_stats(int);
void print_all_thread_stats(void);
void reset_thread_counters(void);

#define ITERATIONS 500 // number of times each thread will be executed
#define THREADS 125 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

/*unsigned int threads_parameters[][4] = {
{ 29000 , 29000 , 1672 , 0 },
{ 6000 , 6000 , 297 , 1 },
{ 13000 , 13000 , 611 , 2 },
{ 4000 , 4000 , 187 , 3 },
{ 22000 , 22000 , 996 , 3 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 }
};*/

/*unsigned int threads_parameters[][4] = {
{ 4000 , 4000 , 435 , 0 },
{ 14000 , 14000 , 1084 , 0 },
{ 20000 , 20000 , 1094 , 0 },
{ 5000 , 5000 , 84 , 0 },
{ 23000 , 23000 , 2377 , 1 },
{ 31000 , 31000 , 2513 , 1 },
{ 25000 , 25000 , 1849 , 1 },
{ 25000 , 25000 , 2547 , 2 },
{ 22000 , 22000 , 1838 , 2 },
{ 20000 , 20000 , 1277 , 2 },
{ 30000 , 30000 , 440 , 2 },
{ 15000 , 15000 , 1425 , 3 },
{ 6000 , 6000 , 556 , 3 },
{ 22000 , 22000 , 1018 , 3 },
{ 23000 , 23000 , 810 , 3 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
};*/

/*unsigned int threads_parameters[][4] = {
{ 14000 , 14000 , 1382 , 0 },
{ 29000 , 29000 , 1879 , 0 },
{ 27000 , 27000 , 1077 , 0 },
{ 26000 , 26000 , 932 , 0 },
{ 17000 , 17000 , 462 , 0 },
{ 32000 , 32000 , 286 , 0 },
{ 10000 , 10000 , 51 , 0 },
{ 18000 , 18000 , 1672 , 1 },
{ 28000 , 28000 , 1934 , 1 },
{ 6000 , 6000 , 258 , 1 },
{ 12000 , 12000 , 416 , 1 },
{ 27000 , 27000 , 592 , 1 },
{ 31000 , 31000 , 634 , 1 },
{ 29000 , 29000 , 2654 , 2 },
{ 4000 , 4000 , 289 , 2 },
{ 21000 , 21000 , 831 , 2 },
{ 15000 , 15000 , 567 , 2 },
{ 25000 , 25000 , 515 , 2 },
{ 5000 , 5000 , 89 , 2 },
{ 10000 , 10000 , 799 , 3 },
{ 5000 , 5000 , 366 , 3 },
{ 11000 , 11000 , 580 , 3 },
{ 15000 , 15000 , 439 , 3 },
{ 4000 , 4000 , 112 , 3 },
{ 31000 , 31000 , 421 , 3 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
};*/

//50 tasks
/*unsigned int threads_parameters[][4] = {
{ 15000 , 15000 , 1462 , 0 },
{ 4000 , 4000 , 313 , 0 },
{ 7000 , 7000 , 542 , 0 },
{ 16000 , 16000 , 1190 , 0 },
{ 13000 , 13000 , 879 , 0 },
{ 5000 , 5000 , 305 , 0 },
{ 17000 , 17000 , 865 , 0 },
{ 23000 , 23000 , 804 , 0 },
{ 23000 , 23000 , 774 , 0 },
{ 7000 , 7000 , 172 , 0 },
{ 27000 , 27000 , 412 , 0 },
{ 17000 , 17000 , 244 , 0 },
{ 20000 , 20000 , 1928 , 1 },
{ 14000 , 14000 , 1275 , 1 },
{ 22000 , 22000 , 1660 , 1 },
{ 18000 , 18000 , 1316 , 1 },
{ 14000 , 14000 , 911 , 1 },
{ 12000 , 12000 , 680 , 1 },
{ 21000 , 21000 , 1026 , 1 },
{ 19000 , 19000 , 777 , 1 },
{ 10000 , 10000 , 293 , 1 },
{ 27000 , 27000 , 612 , 1 },
{ 6000 , 6000 , 92 , 1 },
{ 29000 , 29000 , 429 , 1 },
{ 4000 , 4000 , 383 , 2 },
{ 28000 , 28000 , 2588 , 2 },
{ 12000 , 12000 , 903 , 2 },
{ 17000 , 17000 , 1214 , 2 },
{ 13000 , 13000 , 859 , 2 },
{ 12000 , 12000 , 691 , 2 },
{ 6000 , 6000 , 285 , 2 },
{ 18000 , 18000 , 773 , 2 },
{ 10000 , 10000 , 282 , 2 },
{ 16000 , 16000 , 328 , 2 },
{ 6000 , 6000 , 122 , 2 },
{ 31000 , 31000 , 308 , 2 },
{ 23000 , 23000 , 117 , 2 },
{ 27000 , 27000 , 2578 , 3 },
{ 7000 , 7000 , 663 , 3 },
{ 26000 , 26000 , 1955 , 3 },
{ 6000 , 6000 , 428 , 3 },
{ 10000 , 10000 , 634 , 3 },
{ 8000 , 8000 , 485 , 3 },
{ 29000 , 29000 , 1332 , 3 },
{ 9000 , 9000 , 319 , 3 },
{ 29000 , 29000 , 851 , 3 },
{ 8000 , 8000 , 212 , 3 },
{ 14000 , 14000 , 279 , 3 },
{ 14000 , 14000 , 164 , 3 },
{ 11000 , 11000 , 40 , 3 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
};*/

//75 tasks
/*unsigned int threads_parameters[][4] = {
{ 25000 , 25000 , 2477 , 0 },
{ 9000 , 9000 , 831 , 0 },
{ 7000 , 7000 , 627 , 0 },
{ 18000 , 18000 , 1566 , 0 },
{ 23000 , 23000 , 1872 , 0 },
{ 9000 , 9000 , 707 , 0 },
{ 14000 , 14000 , 1017 , 0 },
{ 16000 , 16000 , 998 , 0 },
{ 25000 , 25000 , 1198 , 0 },
{ 18000 , 18000 , 780 , 0 },
{ 23000 , 23000 , 858 , 0 },
{ 17000 , 17000 , 618 , 0 },
{ 29000 , 29000 , 894 , 0 },
{ 14000 , 14000 , 353 , 0 },
{ 24000 , 24000 , 423 , 0 },
{ 9000 , 9000 , 141 , 0 },
{ 31000 , 31000 , 372 , 0 },
{ 20000 , 20000 , 139 , 0 },
{ 23000 , 23000 , 83 , 0 },
{ 3000 , 3000 , 292 , 1 },
{ 20000 , 20000 , 1864 , 1 },
{ 4000 , 4000 , 367 , 1 },
{ 28000 , 28000 , 2382 , 1 },
{ 32000 , 32000 , 2673 , 1 },
{ 14000 , 14000 , 1085 , 1 },
{ 6000 , 6000 , 428 , 1 },
{ 25000 , 25000 , 1676 , 1 },
{ 8000 , 8000 , 356 , 1 },
{ 21000 , 21000 , 894 , 1 },
{ 7000 , 7000 , 273 , 1 },
{ 23000 , 23000 , 782 , 1 },
{ 22000 , 22000 , 720 , 1 },
{ 6000 , 6000 , 143 , 1 },
{ 32000 , 32000 , 625 , 1 },
{ 19000 , 19000 , 293 , 1 },
{ 29000 , 29000 , 288 , 1 },
{ 7000 , 7000 , 63 , 1 },
{ 27000 , 27000 , 95 , 1 },
{ 22000 , 22000 , 2130 , 2 },
{ 7000 , 7000 , 659 , 2 },
{ 19000 , 19000 , 1743 , 2 },
{ 4000 , 4000 , 339 , 2 },
{ 13000 , 13000 , 1059 , 2 },
{ 7000 , 7000 , 550 , 2 },
{ 11000 , 11000 , 825 , 2 },
{ 29000 , 29000 , 1699 , 2 },
{ 32000 , 32000 , 1844 , 2 },
{ 18000 , 18000 , 711 , 2 },
{ 3000 , 3000 , 111 , 2 },
{ 10000 , 10000 , 337 , 2 },
{ 26000 , 26000 , 724 , 2 },
{ 18000 , 18000 , 457 , 2 },
{ 6000 , 6000 , 122 , 2 },
{ 20000 , 20000 , 311 , 2 },
{ 14000 , 14000 , 164 , 2 },
{ 23000 , 23000 , 145 , 2 },
{ 4000 , 4000 , 21 , 2 },
{ 26000 , 26000 , 2514 , 3 },
{ 4000 , 4000 , 379 , 3 },
{ 4000 , 4000 , 358 , 3 },
{ 4000 , 4000 , 357 , 3 },
{ 20000 , 20000 , 1585 , 3 },
{ 8000 , 8000 , 621 , 3 },
{ 12000 , 12000 , 908 , 3 },
{ 27000 , 27000 , 1581 , 3 },
{ 26000 , 26000 , 1351 , 3 },
{ 4000 , 4000 , 161 , 3 },
{ 4000 , 4000 , 157 , 3 },
{ 20000 , 20000 , 698 , 3 },
{ 32000 , 32000 , 1036 , 3 },
{ 10000 , 10000 , 220 , 3 },
{ 5000 , 5000 , 105 , 3 },
{ 16000 , 16000 , 223 , 3 },
{ 18000 , 18000 , 219 , 3 },
{ 6000 , 6000 , 54 , 3 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
};*/

/*unsigned int threads_parameters[][4] = {
{ 28000 , 28000 , 2793 , 0 },
{ 20000 , 20000 , 1916 , 0 },
{ 8000 , 8000 , 766 , 0 },
{ 15000 , 15000 , 1225 , 0 },
{ 20000 , 20000 , 1615 , 0 },
{ 17000 , 17000 , 1310 , 0 },
{ 13000 , 13000 , 921 , 0 },
{ 22000 , 22000 , 1490 , 0 },
{ 22000 , 22000 , 1415 , 0 },
{ 28000 , 28000 , 1599 , 0 },
{ 7000 , 7000 , 372 , 0 },
{ 27000 , 27000 , 1343 , 0 },
{ 20000 , 20000 , 950 , 0 },
{ 7000 , 7000 , 317 , 0 },
{ 19000 , 19000 , 849 , 0 },
{ 21000 , 21000 , 854 , 0 },
{ 7000 , 7000 , 267 , 0 },
{ 3000 , 3000 , 92 , 0 },
{ 13000 , 13000 , 272 , 0 },
{ 5000 , 5000 , 100 , 0 },
{ 10000 , 10000 , 180 , 0 },
{ 4000 , 4000 , 43 , 0 },
{ 32000 , 32000 , 320 , 0 },
{ 22000 , 22000 , 113 , 0 },
{ 28000 , 28000 , 118 , 0 },
{ 5000 , 5000 , 497 , 1 },
{ 21000 , 21000 , 2024 , 1 },
{ 14000 , 14000 , 1256 , 1 },
{ 11000 , 11000 , 974 , 1 },
{ 18000 , 18000 , 1428 , 1 },
{ 14000 , 14000 , 1089 , 1 },
{ 6000 , 6000 , 418 , 1 },
{ 17000 , 17000 , 1160 , 1 },
{ 11000 , 11000 , 728 , 1 },
{ 22000 , 22000 , 1218 , 1 },
{ 4000 , 4000 , 216 , 1 },
{ 8000 , 8000 , 387 , 1 },
{ 29000 , 29000 , 1383 , 1 },
{ 3000 , 3000 , 140 , 1 },
{ 11000 , 11000 , 487 , 1 },
{ 10000 , 10000 , 403 , 1 },
{ 29000 , 29000 , 1086 , 1 },
{ 12000 , 12000 , 388 , 1 },
{ 6000 , 6000 , 122 , 1 },
{ 28000 , 28000 , 547 , 1 },
{ 22000 , 22000 , 339 , 1 },
{ 6000 , 6000 , 83 , 1 },
{ 15000 , 15000 , 137 , 1 },
{ 12000 , 12000 , 81 , 1 },
{ 20000 , 20000 , 81 , 1 },
{ 20000 , 20000 , 1972 , 2 },
{ 25000 , 25000 , 2425 , 2 },
{ 27000 , 27000 , 2565 , 2 },
{ 26000 , 26000 , 2124 , 2 },
{ 7000 , 7000 , 571 , 2 },
{ 22000 , 22000 , 1628 , 2 },
{ 15000 , 15000 , 1094 , 2 },
{ 13000 , 13000 , 898 , 2 },
{ 20000 , 20000 , 1272 , 2 },
{ 5000 , 5000 , 290 , 2 },
{ 18000 , 18000 , 952 , 2 },
{ 32000 , 32000 , 1590 , 2 },
{ 17000 , 17000 , 798 , 2 },
{ 17000 , 17000 , 792 , 2 },
{ 4000 , 4000 , 173 , 2 },
{ 30000 , 30000 , 1223 , 2 },
{ 27000 , 27000 , 1030 , 2 },
{ 8000 , 8000 , 247 , 2 },
{ 12000 , 12000 , 245 , 2 },
{ 11000 , 11000 , 221 , 2 },
{ 18000 , 18000 , 330 , 2 },
{ 19000 , 19000 , 213 , 2 },
{ 16000 , 16000 , 154 , 2 },
{ 23000 , 23000 , 112 , 2 },
{ 24000 , 24000 , 106 , 2 },
{ 32000 , 32000 , 3131 , 3 },
{ 19000 , 19000 , 1859 , 3 },
{ 25000 , 25000 , 2331 , 3 },
{ 30000 , 30000 , 2460 , 3 },
{ 31000 , 31000 , 2529 , 3 },
{ 23000 , 23000 , 1791 , 3 },
{ 15000 , 15000 , 1086 , 3 },
{ 8000 , 8000 , 538 , 3 },
{ 15000 , 15000 , 931 , 3 },
{ 22000 , 22000 , 1308 , 3 },
{ 5000 , 5000 , 257 , 3 },
{ 19000 , 19000 , 966 , 3 },
{ 14000 , 14000 , 663 , 3 },
{ 10000 , 10000 , 459 , 3 },
{ 29000 , 29000 , 1292 , 3 },
{ 20000 , 20000 , 809 , 3 },
{ 21000 , 21000 , 770 , 3 },
{ 19000 , 19000 , 650 , 3 },
{ 25000 , 25000 , 506 , 3 },
{ 19000 , 19000 , 361 , 3 },
{ 26000 , 26000 , 400 , 3 },
{ 7000 , 7000 , 95 , 3 },
{ 23000 , 23000 , 201 , 3 },
{ 28000 , 28000 , 208 , 3 },
{ 8000 , 8000 , 19 , 3 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
};
*/

//125 tasks
unsigned int threads_parameters[][4] = {
{ 3000 , 3000 , 299 , 0 },
{ 16000 , 16000 , 1488 , 0 },
{ 13000 , 13000 , 1196 , 0 },
{ 4000 , 4000 , 353 , 0 },
{ 7000 , 7000 , 616 , 0 },
{ 24000 , 24000 , 1980 , 0 },
{ 7000 , 7000 , 570 , 0 },
{ 32000 , 32000 , 2315 , 0 },
{ 30000 , 30000 , 2102 , 0 },
{ 17000 , 17000 , 1136 , 0 },
{ 20000 , 20000 , 1247 , 0 },
{ 22000 , 22000 , 1362 , 0 },
{ 13000 , 13000 , 671 , 0 },
{ 28000 , 28000 , 1424 , 0 },
{ 18000 , 18000 , 838 , 0 },
{ 14000 , 14000 , 589 , 0 },
{ 12000 , 12000 , 485 , 0 },
{ 13000 , 13000 , 497 , 0 },
{ 29000 , 29000 , 1080 , 0 },
{ 29000 , 29000 , 1045 , 0 },
{ 5000 , 5000 , 156 , 0 },
{ 11000 , 11000 , 341 , 0 },
{ 13000 , 13000 , 305 , 0 },
{ 20000 , 20000 , 432 , 0 },
{ 8000 , 8000 , 167 , 0 },
{ 15000 , 15000 , 190 , 0 },
{ 29000 , 29000 , 352 , 0 },
{ 9000 , 9000 , 70 , 0 },
{ 3000 , 3000 , 18 , 0 },
{ 8000 , 8000 , 36 , 0 },
{ 16000 , 16000 , 27 , 0 },
{ 28000 , 28000 , 2751 , 1 },
{ 23000 , 23000 , 2180 , 1 },
{ 18000 , 18000 , 1634 , 1 },
{ 12000 , 12000 , 1072 , 1 },
{ 29000 , 29000 , 2528 , 1 },
{ 29000 , 29000 , 2422 , 1 },
{ 9000 , 9000 , 722 , 1 },
{ 22000 , 22000 , 1655 , 1 },
{ 22000 , 22000 , 1484 , 1 },
{ 3000 , 3000 , 202 , 1 },
{ 15000 , 15000 , 957 , 1 },
{ 31000 , 31000 , 1853 , 1 },
{ 31000 , 31000 , 1671 , 1 },
{ 25000 , 25000 , 1267 , 1 },
{ 31000 , 31000 , 1377 , 1 },
{ 21000 , 21000 , 917 , 1 },
{ 17000 , 17000 , 663 , 1 },
{ 22000 , 22000 , 856 , 1 },
{ 32000 , 32000 , 1189 , 1 },
{ 14000 , 14000 , 502 , 1 },
{ 25000 , 25000 , 791 , 1 },
{ 4000 , 4000 , 119 , 1 },
{ 4000 , 4000 , 96 , 1 },
{ 18000 , 18000 , 403 , 1 },
{ 12000 , 12000 , 220 , 1 },
{ 12000 , 12000 , 178 , 1 },
{ 19000 , 19000 , 219 , 1 },
{ 18000 , 18000 , 195 , 1 },
{ 5000 , 5000 , 26 , 1 },
{ 19000 , 19000 , 57 , 1 },
{ 18000 , 18000 , 28 , 1 },
{ 8000 , 8000 , 780 , 2 },
{ 21000 , 21000 , 2005 , 2 },
{ 29000 , 29000 , 2662 , 2 },
{ 6000 , 6000 , 529 , 2 },
{ 19000 , 19000 , 1665 , 2 },
{ 6000 , 6000 , 501 , 2 },
{ 5000 , 5000 , 392 , 2 },
{ 32000 , 32000 , 2456 , 2 },
{ 27000 , 27000 , 1838 , 2 },
{ 15000 , 15000 , 997 , 2 },
{ 17000 , 17000 , 1112 , 2 },
{ 8000 , 8000 , 468 , 2 },
{ 15000 , 15000 , 776 , 2 },
{ 18000 , 18000 , 919 , 2 },
{ 3000 , 3000 , 142 , 2 },
{ 16000 , 16000 , 659 , 2 },
{ 29000 , 29000 , 1182 , 2 },
{ 8000 , 8000 , 308 , 2 },
{ 7000 , 7000 , 260 , 2 },
{ 4000 , 4000 , 138 , 2 },
{ 12000 , 12000 , 413 , 2 },
{ 12000 , 12000 , 314 , 2 },
{ 19000 , 19000 , 485 , 2 },
{ 15000 , 15000 , 345 , 2 },
{ 30000 , 30000 , 566 , 2 },
{ 5000 , 5000 , 74 , 2 },
{ 29000 , 29000 , 327 , 2 },
{ 14000 , 14000 , 110 , 2 },
{ 12000 , 12000 , 88 , 2 },
{ 7000 , 7000 , 30 , 2 },
{ 32000 , 32000 , 34 , 2 },
{ 11000 , 11000 , 1072 , 3 },
{ 24000 , 24000 , 2294 , 3 },
{ 22000 , 22000 , 1995 , 3 },
{ 29000 , 29000 , 2599 , 3 },
{ 24000 , 24000 , 2069 , 3 },
{ 26000 , 26000 , 2211 , 3 },
{ 4000 , 4000 , 313 , 3 },
{ 21000 , 21000 , 1590 , 3 },
{ 13000 , 13000 , 909 , 3 },
{ 9000 , 9000 , 589 , 3 },
{ 30000 , 30000 , 1936 , 3 },
{ 31000 , 31000 , 1831 , 3 },
{ 26000 , 26000 , 1433 , 3 },
{ 27000 , 27000 , 1337 , 3 },
{ 14000 , 14000 , 630 , 3 },
{ 12000 , 12000 , 523 , 3 },
{ 20000 , 20000 , 780 , 3 },
{ 23000 , 23000 , 892 , 3 },
{ 9000 , 9000 , 327 , 3 },
{ 31000 , 31000 , 1124 , 3 },
{ 18000 , 18000 , 594 , 3 },
{ 19000 , 19000 , 529 , 3 },
{ 9000 , 9000 , 223 , 3 },
{ 6000 , 6000 , 134 , 3 },
{ 29000 , 29000 , 471 , 3 },
{ 8000 , 8000 , 120 , 3 },
{ 13000 , 13000 , 162 , 3 },
{ 14000 , 14000 , 156 , 3 },
{ 27000 , 27000 , 159 , 3 },
{ 31000 , 31000 , 69 , 3 },
{ 6000 , 6000 , 12 , 3 },
{ 17000 , 17000 , 18 , 3 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
{ 0, 0, 0, 0 },
};

#define TEST_REPETITIONS 101

#define NUMS 50
int loop_once(void); // loop calculation
int job(unsigned int, int); // function passed by each periodic thread
int loop_for(unsigned int, int);

int run(int test);

int main()
{
  d.clear();
  cout << "CEDF test with " << THREADS << " threads\n";
  
  for(int i = 0; i < TEST_REPETITIONS; i++) {
      
      //cout << "Scheduler schedulable = " << Thread::_scheduler.schedulables() << "\n";
      cout << "Starting test " << i << "\n";
      run(i);
  }
  
  cout << "CEDF total test done clock = " << IA32::clock() << "\n";
    
  print_all_thread_stats();
  
  while(1);
}

Chronometer chrono;

int run(int test)
{
    
    reset_thread_counters();
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
    
    for(int i = 0; i <  THREADS; i++) {
        threads[i] = new Periodic_Thread(&job, 
                        threads_parameters[i][2], //WCET
                        i, //ID
                        threads_parameters[i][0], //period
                        Thread::Criterion((RTC::Microsecond) threads_parameters[i][0], (RTC::Microsecond) threads_parameters[i][0], threads_parameters[i][3]),
                        ITERATIONS); //number of iterations
    }
        
    chrono.reset();
    chrono.start();
    for(int i = 0; i <  THREADS; i++) {
        threads[i]->join();
    }
    chrono.stop();
    
    cout << "test done!\n";
    
    //Timer::unset_channel(Timer::ALARM); // stop tick counting
    //Alarm::delete_timer();
    
    //cout << "Total number of context switches = " << Thread::_n_context_switches << endl;
    
    for(int i = 0; i < THREADS; i++) {
        delete threads[i];
    }
    
    collect_thread_stats(test);
    
    //print_alarm();
    //print_alarm();
    
    cout << "PEDF test (" << test << ") done in " << chrono.read() / 1000000 << " seconds \n";
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
    
    
    return 0;
}

/*
void print_alarm(void)
{
    double m, var, s;
    TSC::Time_Stamp wcet;
    //cout << "elapsed[" << 0 << "] = " << SMP_Alarm::_elapsed[0] << endl;
    for(int i = 0; i < Traits<Machine>::MAX_CPUS; i++) {
      if(SMP_Alarm::_tick_couting[i]) {
      m = mean(SMP_Alarm::_tsc[i], SMP_Alarm::_tick_couting[i], &wcet);
      cout << "[" << i << "]" << " ticks = " << SMP_Alarm::_tick_couting[i] << " mean=" << m << " mean us=" << ts_to_us(m);
      cout << " WCET=" << wcet << " WCET us=" << ts_to_us(wcet);
      var = variance((TSC::Time_Stamp *) SMP_Alarm::_tsc[i], SMP_Alarm::_tick_couting[i], m);
      s = standard_deviation(var);
      cout << " VAR = " << var;
      cout << " STD=" << s << endl;
      } else {
          cout << "CPU " << i << " did not have any alarm\n";
      }
    }
}
*/

void reset_thread_counters()
{
    Thread::_sleep_counter = 0;
    Thread::_reschedule_counter = 0;
    Thread::_wakeup_counter = 0;
    for(int i = 0; i < Thread::MAX_COUNTING; i++) {
        Thread::_sleep_tsc[i] = 0;
        Thread::_wakeup_tsc[i] = 0;
        Thread::_reschedule_tsc[i] = 0;
    }
}

/*void print_all_thread_stats()
{
  double total_wcet_sleep = find_highest(stats.wcet_sleep, TEST_REPETITIONS);
  double tmp;
  double total_mean_sleep = mean(stats.mean_sleep, TEST_REPETITIONS, &tmp);
  double mean_stdev_sleep =  mean(stats.stdev_sleep, TEST_REPETITIONS, &tmp);
  
  //cout << "Sleep   WCET = " << total_wcet_sleep << " AVG = " << total_mean_sleep << " STD = " << mean_stdev_sleep << "\n";
  
  double total_wcet_sleep2 = find_highest(stats.wcet_sleep2, TEST_REPETITIONS);
  double total_mean_sleep2 = mean(stats.mean_sleep2, TEST_REPETITIONS, &tmp);
  double mean_stdev_sleep2 =  mean(stats.stdev_sleep2, TEST_REPETITIONS, &tmp);
  
  cout << "Sleep   WCET = " << total_wcet_sleep2 << " AVG = " << total_mean_sleep2 << " STD = " << mean_stdev_sleep2 << "\n";
  
  double total_wcet_wakeup = find_highest(stats.wcet_wakeup, TEST_REPETITIONS);
  double total_mean_wakeup = mean(stats.mean_wakeup, TEST_REPETITIONS, &tmp);
  double mean_stdev_wakeup =  mean(stats.stdev_wakeup, TEST_REPETITIONS, &tmp);
  
  cout << "Wakeup  WCET = " << total_wcet_wakeup << " AVG = " << total_mean_wakeup << " STD = " << mean_stdev_wakeup << "\n";
  
  double total_wcet_resched = find_highest(stats.wcet_resched, TEST_REPETITIONS);
  double total_mean_resched = mean(stats.mean_resched, TEST_REPETITIONS, &tmp);
  double mean_stdev_resched =  mean(stats.stdev_resched, TEST_REPETITIONS, &tmp);
  
  cout << "Resched WCET = " << total_wcet_resched << " AVG = " << total_mean_resched << " STD = " << mean_stdev_resched << "\n"; 
}*/

/*void collect_thread_stats(int test)
{
    double var_sleep, var_wakeup, var_resched;
    
    stats.mean_sleep[test] = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &stats.wcet_sleep[test]);
    var_sleep = variance(Thread::_sleep_tsc, Thread::_sleep_counter, stats.mean_sleep[test]);
    stats.stdev_sleep[test] = standard_deviation(var_sleep);
    
    //cout << "sleep   WCET = " << stats.wcet_sleep[test] 
    //<< " AVG = " <<  stats.mean_sleep[test]
    //<< " STD = " << stats.stdev_sleep[test] <<"\n";
    
    stats.mean_sleep2[test] = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &stats.wcet_sleep2[test]);
    var_sleep = variance(Thread::_sleep_tsc, Thread::_sleep_counter, stats.mean_sleep2[test]);
    stats.stdev_sleep2[test] = standard_deviation(var_sleep);
    
    stats.wcet_sleep2[test] = find_highest((long long unsigned int *) Thread::_sleep_tsc, Thread::_sleep_counter);
         
    cout << "sleep   WCET = " << stats.wcet_sleep2[test] 
    << " AVG = " <<  stats.mean_sleep2[test]
    << " STD = " << stats.stdev_sleep2[test] <<"\n";
    
    stats.mean_wakeup[test] = mean(Thread::_wakeup_tsc, Thread::_wakeup_counter, &stats.wcet_wakeup[test]);
    var_wakeup = variance(Thread::_wakeup_tsc, Thread::_wakeup_counter, stats.mean_wakeup[test]);
    stats.stdev_wakeup[test] = standard_deviation(var_wakeup);
       
    cout << "wakeup  WCET = " << stats.wcet_wakeup[test] 
    << " AVG = " <<  stats.mean_wakeup[test]
    << " STD = " << stats.stdev_wakeup[test] <<"\n";
    
    stats.mean_resched[test] = mean(Thread::_reschedule_tsc, Thread::_reschedule_counter, &stats.wcet_resched[test]);
    var_resched = variance(Thread::_reschedule_tsc, Thread::_reschedule_counter, stats.mean_resched[test]);
    stats.stdev_resched[test] = standard_deviation(var_resched);
    
    cout << "resched WCET = " << stats.wcet_resched[test] 
    << " AVG = " <<  stats.mean_resched[test]
    << " STD = " << stats.stdev_resched[test] <<"\n";
    
}*/

/*
void print_thread_stats(void)
{
    double m_sleep, m_wakeup, var_sleep, 
           var_wakeup, s_sleep, s_wakeup,
           m_resched, var_resched, s_resched;
    TSC::Time_Stamp wcet_sleep, wcet_wakeup, wcet_resched;
    
    m_sleep = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &wcet_sleep);
    var_sleep = variance(Thread::_sleep_tsc, Thread::_sleep_counter, m_sleep);
    s_sleep = standard_deviation(var_sleep);
    
    cout << "Total sleep = " << Thread::_sleep_counter << " Total wakeup = " 
         << Thread::_wakeup_counter << " Total resched = " << Thread::_reschedule_counter << endl;
    
    cout << "sleep m = " << m_sleep << " sleep var = " << var_sleep << " std sleep = " << s_sleep << " wcet = " << wcet_sleep << endl;
    
    m_wakeup = mean(Thread::_wakeup_tsc, Thread::_wakeup_counter, &wcet_wakeup);
    var_wakeup = variance(Thread::_wakeup_tsc, Thread::_wakeup_counter, m_wakeup);
    s_wakeup = standard_deviation(var_wakeup);
    
    cout << " wakeup m = " << m_wakeup << " wakeup var = " << var_wakeup << " std wakeup = " << s_wakeup << " wcet = " << wcet_wakeup << endl;
    
    m_resched = mean(Thread::_reschedule_tsc, Thread::_reschedule_counter, &wcet_resched);
    var_resched = variance(Thread::_reschedule_tsc, Thread::_reschedule_counter, m_resched);
    s_resched = standard_deviation(var_resched);
    
    cout << " resched m = " << m_resched << " resched var = " << var_resched << " std resched = " << s_resched << " wcet = " << wcet_resched << endl;
}*/

/*
void print_perf_stats(void)
{
    for(int i = 0; i < THREADS; i++) {
      cout << "thread[" << i << "] counter = " << threads[i]->_perf_counter;
      unsigned long long sum = 0;
      for(int j = 0; j < threads[i]->_perf_counter; j++) {
           sum += threads[i]->_buffer.cache_misses[j];
      }
      double mean = sum / threads[i]->_perf_counter;
      cout << " AVG cache misses = " << mean << endl;
    }
}*/

int loop_once(void)
{
    int i, j = 0;
    //int r = 1;
    for(i = 0; i < NUMS; i++) {
        //j += 3 - (NUMS - i);
        //r *= j - i;
        j += 3;
    }
    return j;
}

int loop_for(unsigned int exec_time, int id) 
{
    /*unsigned long loop_end = 0, loop_start;
    int tmp = 0;
    Chronometer c;
    
    c.start();
    
    unsigned long elapsed = 0;

    while(elapsed < exec_time) {
        loop_start = c.read();
        tmp += loop_once();
        loop_end = c.read();
        elapsed = elapsed + (loop_end - loop_start);
    }
    //s.p();
    //cout << "elapsed " << id << " (" << Machine::cpu_id() << ") = " << elapsed << " exec_time = " << exec_time << endl;
    //s.v();
    //c.stop();
    //c.reset();
    
    return tmp;*/
    return loop_once();
}

int job(unsigned int exec_time, int id)
{
    //cout << "Starting thread " << id << endl;
    int sum = 0;
    //Chronometer c;
    for(int i = 0; i <  ITERATIONS; i++) {
      //if(id == 2 && i == 0) start_time = chrono.read();
      Periodic_Thread::wait_next();
      //if((i % 100) == id || i == 499) {
      //s.p();
      //cout << "(Thread id = " << id << "(" << (threads_parameters[id][0] >> 8) << ") CPU => " << Machine::cpu_id() << " ite = " << i << ")" << endl;
      //<< " time = " << chrono.read() - start_time << ")" << endl;
      //s.v();
      //}
      //c.reset();
      //c.start();
      sum += loop_for(exec_time, id);
      //c.stop();
      //cout << "loop time = " << c.read() << endl;
      //s.p();
      //cout << " (Thread " << id << " end)\n";
      //s.v();
    }
    //s.p();
    //cout << "thread " << (threads_parameters[id][0] >> 8) << " END \n";
    //s.v();
    return sum;
}

typedef struct {
  TSC::Time_Stamp mean_sleep[TEST_REPETITIONS];
  TSC::Time_Stamp stdev_sleep[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_sleep[TEST_REPETITIONS];
  
  TSC::Time_Stamp mean_sleep2[TEST_REPETITIONS];
  TSC::Time_Stamp stdev_sleep2[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_sleep2[TEST_REPETITIONS];
  
  TSC::Time_Stamp mean_wakeup[TEST_REPETITIONS];
  TSC::Time_Stamp stdev_wakeup[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_wakeup[TEST_REPETITIONS];
  
  TSC::Time_Stamp mean_resched[TEST_REPETITIONS];
  TSC::Time_Stamp stdev_resched[TEST_REPETITIONS];
  TSC::Time_Stamp wcet_resched[TEST_REPETITIONS];
} thread_stats;

thread_stats stats;

void print_all_thread_stats()
{
  unsigned long long total_wcet_sleep = find_highest(stats.wcet_sleep, TEST_REPETITIONS);
  unsigned long long tmp;
  unsigned long long total_mean_sleep = mean_wo_mul(stats.mean_sleep, TEST_REPETITIONS, &tmp);
  unsigned long long mean_stdev_sleep =  mean_wo_mul(stats.stdev_sleep, TEST_REPETITIONS, &tmp);
  
  //cout << "Sleep   WCET = " << total_wcet_sleep << " AVG = " << total_mean_sleep << " STD = " << mean_stdev_sleep << "\n";
  
  unsigned long long total_wcet_sleep2 = find_highest(stats.wcet_sleep2, TEST_REPETITIONS);
  unsigned long long total_mean_sleep2 = mean_wo_mul(stats.mean_sleep2, TEST_REPETITIONS, &tmp);
  unsigned long long mean_stdev_sleep2 =  mean_wo_mul(stats.stdev_sleep2, TEST_REPETITIONS, &tmp);
  
  cout << "Sleep   WCET = " << total_wcet_sleep2 << " AVG = " << total_mean_sleep2 << " VAR = " << mean_stdev_sleep2 << "\n";
  
  unsigned long long total_wcet_wakeup = find_highest(stats.wcet_wakeup, TEST_REPETITIONS);
  unsigned long long total_mean_wakeup = mean_wo_mul(stats.mean_wakeup, TEST_REPETITIONS, &tmp);
  unsigned long long mean_stdev_wakeup =  mean_wo_mul(stats.stdev_wakeup, TEST_REPETITIONS, &tmp);
  
  cout << "Wakeup  WCET = " << total_wcet_wakeup << " AVG = " << total_mean_wakeup << " VAR = " << mean_stdev_wakeup << "\n";
  
  unsigned long long total_wcet_resched = find_highest(stats.wcet_resched, TEST_REPETITIONS);
  unsigned long long total_mean_resched = mean_wo_mul(stats.mean_resched, TEST_REPETITIONS, &tmp);
  unsigned long long mean_stdev_resched =  mean_wo_mul(stats.stdev_resched, TEST_REPETITIONS, &tmp);
  
  cout << "Resched WCET = " << total_wcet_resched << " AVG = " << total_mean_resched << " VAR = " << mean_stdev_resched << "\n"; 
}

void collect_thread_stats(int test)
{
    unsigned long long var_sleep, var_wakeup, var_resched;
    
    stats.mean_sleep[test] = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &stats.wcet_sleep[test]);
    var_sleep = variance((long long unsigned int *) Thread::_sleep_tsc, Thread::_sleep_counter, stats.mean_sleep[test]);
    //stats.stdev_sleep[test] = standard_deviation(var_sleep);
    stats.stdev_sleep[test] = var_sleep;
    
    //cout << "sleep   WCET = " << stats.wcet_sleep[test] 
    //<< " AVG = " <<  stats.mean_sleep[test]
    //<< " STD = " << stats.stdev_sleep[test] <<"\n";
    
    stats.mean_sleep2[test] = mean(Thread::_sleep_tsc, Thread::_sleep_counter, &stats.wcet_sleep2[test]);
    var_sleep = variance((long long unsigned int *) Thread::_sleep_tsc, Thread::_sleep_counter, stats.mean_sleep2[test]);
    //stats.stdev_sleep2[test] = standard_deviation(var_sleep);
    stats.stdev_sleep2[test] = var_sleep;
    
    stats.wcet_sleep2[test] = find_highest((long long unsigned int *) Thread::_sleep_tsc, Thread::_sleep_counter);
         
    cout << "sleep   WCET = " << stats.wcet_sleep2[test] 
    << " AVG = " <<  stats.mean_sleep2[test]
    << " VAR = " << stats.stdev_sleep2[test] <<"\n";
    
    stats.mean_wakeup[test] = mean(Thread::_wakeup_tsc, Thread::_wakeup_counter, &stats.wcet_wakeup[test]);
    var_wakeup = variance((long long unsigned int *) Thread::_wakeup_tsc, Thread::_wakeup_counter, stats.mean_wakeup[test]);
    //stats.stdev_wakeup[test] = standard_deviation(var_wakeup);
    stats.stdev_wakeup[test] = var_wakeup;
       
    cout << "wakeup  WCET = " << stats.wcet_wakeup[test] 
    << " AVG = " <<  stats.mean_wakeup[test]
    << " VAR = " << stats.stdev_wakeup[test] <<"\n";
    
    stats.mean_resched[test] = mean(Thread::_reschedule_tsc, Thread::_reschedule_counter, &stats.wcet_resched[test]);
    var_resched = variance((long long unsigned int *) Thread::_reschedule_tsc, Thread::_reschedule_counter, stats.mean_resched[test]);
    //stats.stdev_resched[test] = standard_deviation(var_resched);
    stats.stdev_resched[test] = var_resched;
    
    cout << "resched WCET = " << stats.wcet_resched[test] 
    << " AVG = " <<  stats.mean_resched[test]
    << " VAR = " << stats.stdev_resched[test] <<"\n";
    
}