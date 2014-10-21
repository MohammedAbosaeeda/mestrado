// GEDF test with WCET

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
    int i;
    
    if(size == 0) return 0;
    
    for(i = 0; i < size; i++) {
        if(t[i] == 0) continue;
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return (unsigned long long) (sum / (i+1)) * 1000;
}

unsigned long long mean(unsigned long long t[], int size, unsigned long long * h)
{   
    unsigned long long sum = 0;
    *h = 0;
    int i;
    
    if(size == 0) return 0;
    
    for(i = 0; i < size; i++) {
        if(t[i] == 0) continue;
        sum += t[i];
        if(t[i] > *h) 
          *h = t[i];
    }
    return (unsigned long long) (sum / (i+1)) * 1000;
}

unsigned long long mean_wo_mul(unsigned long long t[], int size, unsigned long long * h)
{   
    unsigned long long sum = 0;
    *h = 0;
    int i;
    
    if(size == 0) return 0;
    
    for(i = 0; i < size; i++) {
        if(t[i] == 0) continue;
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
    
    if(size == 0) return 0;
    
    for(int i = 0; i <  size; i++) {
        if(t[i] > wc) { 
          wc = t[i];
          j = i;
        }
    }
    
    return wc;
}

unsigned long long variance(const volatile TSC::Time_Stamp t[], int size, unsigned long long mean)
{
    unsigned long long v = 0;
    int i;
    
    if(size == 0) return 0;
    
    mean = (unsigned long long ) mean / 1000;
    for(i = 0; i < size; i++) {
        if(t[i] == 0) continue;
        unsigned long long tmp = (mean - (t[i]));
        v = v + (tmp * tmp);
        cout << "";
    }
    return (unsigned long long) (v / ((i+1) -1));
}

void collect_alarm_stats(int);
void print_all_alarm_stats(void);
void reset_alarm_counters(void);

#define ITERATIONS 500 // number of times each thread will be executed
#define THREADS 100 // number of periodic threads
Periodic_Thread * threads[THREADS]; // periodics threads that will be created

// period (microsecond), deadline, execution time (microsecond)
// 5 threads, total utilization of 0.2377893 , 8 processors 
/*unsigned int threads_parameters[][3] = {
{ 4000 , 4000 , 187 },
{ 6000 , 6000 , 297 },
{ 29000 , 29000 , 1672 },
{ 13000 , 13000 , 611 },
{ 22000 , 22000 , 996 },
};
//#define TEST_REPETITIONS 100
*/

// period (microsecond), deadline, execution time (microsecond)
// 15 threads, total utilization of 0.9947831 , 8 processors 
/*unsigned int threads_parameters[][3] = {
{ 23000 , 23000 , 810 },
{ 30000 , 30000 , 440 },
{ 22000 , 22000 , 1838 },
{ 20000 , 20000 , 1094 },
{ 4000 , 4000 , 435 },
{ 14000 , 14000 , 1084 },
{ 15000 , 15000 , 1425 },
{ 31000 , 31000 , 2513 },
{ 20000 , 20000 , 1277 },
{ 25000 , 25000 , 1849 },
{ 5000 , 5000 , 84 },
{ 6000 , 6000 , 556 },
{ 23000 , 23000 , 2377 },
{ 22000 , 22000 , 1018 },
{ 25000 , 25000 , 2547 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 }
};
//#define TEST_REPETITIONS 101
*/

// period (microsecond), deadline, execution time (microsecond)
// 25 threads, total utilization of 1.117542 , 8 processors 
/*unsigned int threads_parameters[][3] = {
{ 5000 , 5000 , 89 },
{ 25000 , 25000 , 515 },
{ 10000 , 10000 , 799 },
{ 27000 , 27000 , 592 },
{ 15000 , 15000 , 567 },
{ 27000 , 27000 , 1077 },
{ 26000 , 26000 , 932 },
{ 4000 , 4000 , 112 },
{ 17000 , 17000 , 462 },
{ 18000 , 18000 , 1672 },
{ 4000 , 4000 , 289 },
{ 10000 , 10000 , 51 },
{ 32000 , 32000 , 286 },
{ 29000 , 29000 , 1879 },
{ 31000 , 31000 , 634 },
{ 11000 , 11000 , 580 },
{ 28000 , 28000 , 1934 },
{ 29000 , 29000 , 2654 },
{ 15000 , 15000 , 439 },
{ 5000 , 5000 , 366 },
{ 12000 , 12000 , 416 },
{ 31000 , 31000 , 421 },
{ 21000 , 21000 , 831 },
{ 6000 , 6000 , 258 },
{ 14000 , 14000 , 1382 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 }
};
//#define TEST_REPETITIONS 101
*/

// period (microsecond), deadline, execution time (microsecond)
// 50 threads, total utilization of 2.523393 , 8 processors 
/*unsigned int threads_parameters[][3] = {
{ 23000 , 23000 , 774 },
{ 4000 , 4000 , 383 },
{ 31000 , 31000 , 308 },
{ 12000 , 12000 , 691 },
{ 29000 , 29000 , 1332 },
{ 13000 , 13000 , 859 },
{ 6000 , 6000 , 92 },
{ 9000 , 9000 , 319 },
{ 7000 , 7000 , 172 },
{ 13000 , 13000 , 879 },
{ 6000 , 6000 , 428 },
{ 6000 , 6000 , 122 },
{ 20000 , 20000 , 1928 },
{ 27000 , 27000 , 412 },
{ 28000 , 28000 , 2588 },
{ 27000 , 27000 , 2578 },
{ 7000 , 7000 , 542 },
{ 16000 , 16000 , 328 },
{ 7000 , 7000 , 663 },
{ 14000 , 14000 , 279 },
{ 10000 , 10000 , 634 },
{ 23000 , 23000 , 117 },
{ 11000 , 11000 , 40 },
{ 17000 , 17000 , 865 },
{ 14000 , 14000 , 164 },
{ 10000 , 10000 , 293 },
{ 12000 , 12000 , 680 },
{ 18000 , 18000 , 1316 },
{ 16000 , 16000 , 1190 },
{ 15000 , 15000 , 1462 },
{ 5000 , 5000 , 305 },
{ 29000 , 29000 , 429 },
{ 17000 , 17000 , 1214 },
{ 26000 , 26000 , 1955 },
{ 4000 , 4000 , 313 },
{ 27000 , 27000 , 612 },
{ 22000 , 22000 , 1660 },
{ 12000 , 12000 , 903 },
{ 8000 , 8000 , 212 },
{ 19000 , 19000 , 777 },
{ 14000 , 14000 , 911 },
{ 14000 , 14000 , 1275 },
{ 21000 , 21000 , 1026 },
{ 23000 , 23000 , 804 },
{ 10000 , 10000 , 282 },
{ 18000 , 18000 , 773 },
{ 17000 , 17000 , 244 },
{ 29000 , 29000 , 851 },
{ 8000 , 8000 , 485 },
{ 6000 , 6000 , 285 },
};
//necessario colocar if dentro de sleep, wakeup e reschedule
*/

// period (microsecond), deadline, execution time (microsecond)
// 75 threads, total utilization of 3.756025 , 8 processors 
/*unsigned int threads_parameters[][3] = {
{ 23000 , 23000 , 858 },
{ 22000 , 22000 , 2130 },
{ 19000 , 19000 , 293 },
{ 7000 , 7000 , 550 },
{ 20000 , 20000 , 1864 },
{ 32000 , 32000 , 2673 },
{ 28000 , 28000 , 2382 },
{ 7000 , 7000 , 273 },
{ 18000 , 18000 , 219 },
{ 25000 , 25000 , 2477 },
{ 31000 , 31000 , 372 },
{ 4000 , 4000 , 157 },
{ 14000 , 14000 , 164 },
{ 8000 , 8000 , 356 },
{ 3000 , 3000 , 292 },
{ 18000 , 18000 , 780 },
{ 13000 , 13000 , 1059 },
{ 16000 , 16000 , 998 },
{ 4000 , 4000 , 161 },
{ 4000 , 4000 , 358 },
{ 9000 , 9000 , 707 },
{ 7000 , 7000 , 63 },
{ 7000 , 7000 , 627 },
{ 23000 , 23000 , 1872 },
{ 14000 , 14000 , 1085 },
{ 26000 , 26000 , 2514 },
{ 29000 , 29000 , 288 },
{ 6000 , 6000 , 122 },
{ 29000 , 29000 , 1699 },
{ 8000 , 8000 , 621 },
{ 17000 , 17000 , 618 },
{ 10000 , 10000 , 337 },
{ 6000 , 6000 , 54 },
{ 32000 , 32000 , 625 },
{ 18000 , 18000 , 1566 },
{ 4000 , 4000 , 379 },
{ 25000 , 25000 , 1676 },
{ 26000 , 26000 , 724 },
{ 24000 , 24000 , 423 },
{ 23000 , 23000 , 83 },
{ 20000 , 20000 , 698 },
{ 18000 , 18000 , 457 },
{ 27000 , 27000 , 1581 },
{ 20000 , 20000 , 139 },
{ 9000 , 9000 , 831 },
{ 26000 , 26000 , 1351 },
{ 6000 , 6000 , 143 },
{ 9000 , 9000 , 141 },
{ 23000 , 23000 , 145 },
{ 25000 , 25000 , 1198 },
{ 32000 , 32000 , 1036 },
{ 20000 , 20000 , 1585 },
{ 14000 , 14000 , 353 },
{ 11000 , 11000 , 825 },
{ 18000 , 18000 , 711 },
{ 19000 , 19000 , 1743 },
{ 10000 , 10000 , 220 },
{ 3000 , 3000 , 111 },
{ 4000 , 4000 , 357 },
{ 12000 , 12000 , 908 },
{ 23000 , 23000 , 782 },
{ 22000 , 22000 , 720 },
{ 16000 , 16000 , 223 },
{ 5000 , 5000 , 105 },
{ 21000 , 21000 , 894 },
{ 27000 , 27000 , 95 },
{ 4000 , 4000 , 367 },
{ 20000 , 20000 , 311 },
{ 14000 , 14000 , 1017 },
{ 6000 , 6000 , 428 },
{ 32000 , 32000 , 1844 },
{ 29000 , 29000 , 894 },
{ 7000 , 7000 , 659 },
{ 4000 , 4000 , 21 },
{ 4000 , 4000 , 339 },
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
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
};
//#define TEST_REPETITIONS 101
//necessario if em sleep, wakeup e reschedule
*/

// period (microsecond), deadline, execution time (microsecond)
unsigned int threads_parameters[][3] = {
{ 13000 , 13000 , 921 },
{ 20000 , 20000 , 950 },
{ 8000 , 8000 , 247 },
{ 25000 , 25000 , 2331 },
{ 6000 , 6000 , 122 },
{ 15000 , 15000 , 1094 },
{ 15000 , 15000 , 1086 },
{ 21000 , 21000 , 770 },
{ 10000 , 10000 , 403 },
{ 22000 , 22000 , 339 },
{ 5000 , 5000 , 100 },
{ 4000 , 4000 , 173 },
{ 27000 , 27000 , 2565 },
{ 8000 , 8000 , 387 },
{ 20000 , 20000 , 1972 },
{ 3000 , 3000 , 140 },
{ 28000 , 28000 , 118 },
{ 6000 , 6000 , 418 },
{ 8000 , 8000 , 766 },
{ 14000 , 14000 , 1089 },
{ 19000 , 19000 , 966 },
{ 19000 , 19000 , 849 },
{ 27000 , 27000 , 1343 },
{ 7000 , 7000 , 267 },
{ 19000 , 19000 , 361 },
{ 32000 , 32000 , 1590 },
{ 12000 , 12000 , 245 },
{ 20000 , 20000 , 1615 },
{ 5000 , 5000 , 290 },
{ 7000 , 7000 , 571 },
{ 12000 , 12000 , 81 },
{ 17000 , 17000 , 792 },
{ 11000 , 11000 , 221 },
{ 15000 , 15000 , 137 },
{ 26000 , 26000 , 2124 },
{ 32000 , 32000 , 3131 },
{ 19000 , 19000 , 213 },
{ 5000 , 5000 , 497 },
{ 28000 , 28000 , 2793 },
{ 15000 , 15000 , 931 },
{ 4000 , 4000 , 43 },
{ 15000 , 15000 , 1225 },
{ 18000 , 18000 , 330 },
{ 18000 , 18000 , 952 },
{ 20000 , 20000 , 81 },
{ 22000 , 22000 , 113 },
{ 17000 , 17000 , 798 },
{ 22000 , 22000 , 1308 },
{ 14000 , 14000 , 1256 },
{ 26000 , 26000 , 400 },
{ 28000 , 28000 , 547 },
{ 13000 , 13000 , 898 },
{ 19000 , 19000 , 650 },
{ 29000 , 29000 , 1383 },
{ 20000 , 20000 , 809 },
{ 24000 , 24000 , 106 },
{ 29000 , 29000 , 1292 },
{ 20000 , 20000 , 1916 },
{ 29000 , 29000 , 1086 },
{ 13000 , 13000 , 272 },
{ 28000 , 28000 , 208 },
{ 22000 , 22000 , 1628 },
{ 4000 , 4000 , 216 },
{ 25000 , 25000 , 506 },
{ 18000 , 18000 , 1428 },
{ 21000 , 21000 , 854 },
{ 11000 , 11000 , 728 },
{ 7000 , 7000 , 95 },
{ 7000 , 7000 , 372 },
{ 32000 , 32000 , 320 },
{ 5000 , 5000 , 257 },
{ 17000 , 17000 , 1160 },
{ 11000 , 11000 , 974 },
{ 10000 , 10000 , 459 },
{ 30000 , 30000 , 2460 },
{ 16000 , 16000 , 154 },
{ 6000 , 6000 , 83 },
{ 21000 , 21000 , 2024 },
{ 30000 , 30000 , 1223 },
{ 22000 , 22000 , 1490 },
{ 23000 , 23000 , 112 },
{ 14000 , 14000 , 663 },
{ 12000 , 12000 , 388 },
{ 22000 , 22000 , 1218 },
{ 8000 , 8000 , 19 },
{ 20000 , 20000 , 1272 },
{ 22000 , 22000 , 1415 },
{ 3000 , 3000 , 92 },
{ 23000 , 23000 , 1791 },
{ 23000 , 23000 , 201 },
{ 17000 , 17000 , 1310 },
{ 28000 , 28000 , 1599 },
{ 25000 , 25000 , 2425 },
{ 27000 , 27000 , 1030 },
{ 11000 , 11000 , 487 },
{ 7000 , 7000 , 317 },
{ 10000 , 10000 , 180 },
{ 8000 , 8000 , 538 },
{ 31000 , 31000 , 2529 },
{ 19000 , 19000 , 1859 },
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
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 }
};
//#define TEST_REPETITIONS 101
//necessario if em sleep. wakeup e reschedule

// period (microsecond), deadline, execution time (microsecond)
// 125 threads, total utilization of 5.892654 , 8 processors 
/*unsigned int threads_parameters[][3] = {
{ 31000 , 31000 , 1124 },
{ 21000 , 21000 , 1590 },
{ 8000 , 8000 , 308 },
{ 16000 , 16000 , 27 },
{ 18000 , 18000 , 838 },
{ 29000 , 29000 , 2662 },
{ 29000 , 29000 , 2422 },
{ 29000 , 29000 , 327 },
{ 11000 , 11000 , 1072 },
{ 5000 , 5000 , 74 },
{ 23000 , 23000 , 892 },
{ 17000 , 17000 , 18 },
{ 20000 , 20000 , 780 },
{ 13000 , 13000 , 497 },
{ 12000 , 12000 , 413 },
{ 4000 , 4000 , 313 },
{ 6000 , 6000 , 134 },
{ 19000 , 19000 , 57 },
{ 32000 , 32000 , 2456 },
{ 12000 , 12000 , 88 },
{ 18000 , 18000 , 1634 },
{ 6000 , 6000 , 529 },
{ 3000 , 3000 , 202 },
{ 13000 , 13000 , 305 },
{ 4000 , 4000 , 119 },
{ 8000 , 8000 , 120 },
{ 28000 , 28000 , 1424 },
{ 17000 , 17000 , 663 },
{ 26000 , 26000 , 2211 },
{ 32000 , 32000 , 2315 },
{ 9000 , 9000 , 722 },
{ 18000 , 18000 , 28 },
{ 17000 , 17000 , 1136 },
{ 9000 , 9000 , 223 },
{ 3000 , 3000 , 18 },
{ 4000 , 4000 , 96 },
{ 3000 , 3000 , 142 },
{ 20000 , 20000 , 432 },
{ 19000 , 19000 , 1665 },
{ 14000 , 14000 , 156 },
{ 13000 , 13000 , 909 },
{ 7000 , 7000 , 30 },
{ 22000 , 22000 , 1484 },
{ 9000 , 9000 , 589 },
{ 19000 , 19000 , 485 },
{ 22000 , 22000 , 856 },
{ 25000 , 25000 , 791 },
{ 15000 , 15000 , 190 },
{ 24000 , 24000 , 2294 },
{ 12000 , 12000 , 485 },
{ 15000 , 15000 , 345 },
{ 32000 , 32000 , 34 },
{ 22000 , 22000 , 1995 },
{ 6000 , 6000 , 501 },
{ 8000 , 8000 , 36 },
{ 29000 , 29000 , 1182 },
{ 5000 , 5000 , 26 },
{ 16000 , 16000 , 1488 },
{ 29000 , 29000 , 2528 },
{ 7000 , 7000 , 260 },
{ 25000 , 25000 , 1267 },
{ 30000 , 30000 , 566 },
{ 13000 , 13000 , 162 },
{ 13000 , 13000 , 671 },
{ 5000 , 5000 , 392 },
{ 8000 , 8000 , 468 },
{ 20000 , 20000 , 1247 },
{ 12000 , 12000 , 178 },
{ 15000 , 15000 , 997 },
{ 27000 , 27000 , 1838 },
{ 31000 , 31000 , 1853 },
{ 24000 , 24000 , 2069 },
{ 9000 , 9000 , 327 },
{ 29000 , 29000 , 352 },
{ 30000 , 30000 , 1936 },
{ 31000 , 31000 , 1377 },
{ 27000 , 27000 , 159 },
{ 26000 , 26000 , 1433 },
{ 13000 , 13000 , 1196 },
{ 30000 , 30000 , 2102 },
{ 32000 , 32000 , 1189 },
{ 15000 , 15000 , 776 },
{ 12000 , 12000 , 523 },
{ 14000 , 14000 , 589 },
{ 9000 , 9000 , 70 },
{ 12000 , 12000 , 314 },
{ 16000 , 16000 , 659 },
{ 29000 , 29000 , 1080 },
{ 15000 , 15000 , 957 },
{ 5000 , 5000 , 156 },
{ 12000 , 12000 , 220 },
{ 8000 , 8000 , 780 },
{ 4000 , 4000 , 138 },
{ 14000 , 14000 , 110 },
{ 21000 , 21000 , 917 },
{ 14000 , 14000 , 502 },
{ 8000 , 8000 , 167 },
{ 6000 , 6000 , 12 },
{ 18000 , 18000 , 195 },
{ 19000 , 19000 , 529 },
{ 19000 , 19000 , 219 },
{ 24000 , 24000 , 1980 },
{ 31000 , 31000 , 1671 },
{ 18000 , 18000 , 919 },
{ 18000 , 18000 , 403 },
{ 29000 , 29000 , 471 },
{ 3000 , 3000 , 299 },
{ 21000 , 21000 , 2005 },
{ 12000 , 12000 , 1072 },
{ 31000 , 31000 , 1831 },
{ 22000 , 22000 , 1362 },
{ 29000 , 29000 , 1045 },
{ 4000 , 4000 , 353 },
{ 22000 , 22000 , 1655 },
{ 27000 , 27000 , 1337 },
{ 28000 , 28000 , 2751 },
{ 31000 , 31000 , 69 },
{ 23000 , 23000 , 2180 },
{ 7000 , 7000 , 570 },
{ 14000 , 14000 , 630 },
{ 11000 , 11000 , 341 },
{ 18000 , 18000 , 594 },
{ 29000 , 29000 , 2599 },
{ 7000 , 7000 , 616 },
{ 17000 , 17000 , 1112 },
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
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
{ 0, 0, 0 },
};*/

#define NUMS 50
#define TEST_REPETITIONS 101
int loop_once(void); // loop calculation
int job(unsigned int, int); // function passed by each periodic thread
int loop_for(unsigned int, int);

int run(int test);

int main()
{
  d.clear();
  cout << "GEDF test with " << THREADS << " threads\n";
  
  for(int i = 0; i < TEST_REPETITIONS; i++) {
      
      //cout << "Scheduler schedulable = " << Thread::_scheduler.schedulables() << "\n";
      
      run(i);
  }
  
  cout << "GEDF total test done clock = " << IA32::clock() << "\n";
    
  print_all_alarm_stats();
  
  while(1);
}

Chronometer chrono;

int run(int test)
{
    
    reset_alarm_counters();
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
    
    for(int i = 0; i <  THREADS; i++) {
        threads[i] = new Periodic_Thread(&job, 
                                         threads_parameters[i][2], //WCET
                                         i, //ID
                                         threads_parameters[i][0], //period
                                         ITERATIONS); //number of iterations
    }
        
    chrono.reset();
    chrono.start();
    for(int i = 0; i <  THREADS; i++) {
        threads[i]->join();
    }
    chrono.stop();
    
    
    //Timer::unset_channel(Timer::ALARM); // stop tick counting
    //Alarm::delete_timer();
    
    //cout << "Total number of context switches = " << Thread::_n_context_switches << endl;
    
    for(int i = 0; i < THREADS; i++) {
        delete threads[i];
    }
    
    collect_alarm_stats(test);
    
    //print_alarm();
    //print_alarm();
    
    cout << "GEDF test (" << test << ") done in " << chrono.read() / 1000000 << " seconds \n";
    
    for(unsigned int i = 0; i < 0xffffffff; i++)
        for(unsigned int j = 0; j < 0xffffffff; j++) ;
    
    
    return 0;
}

void reset_alarm_counters()
{
    for(int i = 0; i < Traits<Machine>::MAX_CPUS; i++) {
        Alarm::_tick_couting[i] = 0;
        for(int j = 0; j < Alarm::MAX_ELAPSED; j++) 
            Alarm::_tsc[i][j];
    }
}

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
  TSC::Time_Stamp mean[Traits<Machine>::MAX_CPUS][TEST_REPETITIONS];
  TSC::Time_Stamp stdev[Traits<Machine>::MAX_CPUS][TEST_REPETITIONS];
  TSC::Time_Stamp wcet[Traits<Machine>::MAX_CPUS][TEST_REPETITIONS];  
} alarm_stats;

alarm_stats stats;

void print_all_alarm_stats()
{
    for(int i = 0; i < Traits<Machine>::MAX_CPUS; i++) {
        unsigned long long total_wcet = find_highest((long long unsigned int *) &stats.wcet[i], TEST_REPETITIONS);
        unsigned long long tmp;
        unsigned long long total_mean = mean_wo_mul((long long unsigned int *) &stats.mean[i], TEST_REPETITIONS, &tmp);
        unsigned long long mean_stdev =  mean_wo_mul((long long unsigned int *) &stats.stdev[i], TEST_REPETITIONS, &tmp);
    
        cout << "WCET = " << total_wcet << " AVG = " << total_mean 
        << " VAR = " << mean_stdev << "\n"; 
    }
        
}

void collect_alarm_stats(int test)
{
    unsigned long long var;
    
    for(int i = 0; i < Traits<Machine>::MAX_CPUS; i++) {
        var = 0;
        stats.mean[i][test] = mean((TSC::Time_Stamp *) &Alarm::_tsc[i], Alarm::_tick_couting[i], &stats.wcet[i][test]);
        var = variance((long long unsigned int *) &Alarm::_tsc[i],  Alarm::_tick_couting[i], stats.mean[i][test]);
        stats.stdev[i][test] = var;
    
        cout << "CPU [" << i << "] WCET = " << stats.wcet[i][test] 
        << " AVG = " <<  stats.mean[i][test]
        << " VAR = " << stats.stdev[i][test] <<"\n";
    
    }
}