// EPOSSOC Dummy Application
//
// Author: Hugo
// Documentation: $EPOS/doc/loader			Date: 23 May 2011

#include <utility/ostream.h>
#include <machine.h>
#include <thread.h>
#include <periodic_thread.h>
#include <semaphore.h>
#include <components/dummy_caller.h>

__USING_SYS

OStream cout;

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);
volatile unsigned int * REAL_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS+4);

enum {
    VECTOR_SIZE = 8,
    MEM_BLOCK_SIZE = 128,
    MEM_BLOCK_SIZE_STACK = 16
};

int ADD_VECTOR[VECTOR_SIZE][3] = {
  {0,0,0}, {1,1,2}, {-1,-1,-2}, {-1,1,0}, {1,-1,0},
  {1111111,2222222,3333333},
  {-1111111,2222222,1111111},
  {1111111,-2222222,-1111111}
};

int MULT_VECTOR[VECTOR_SIZE][3] = {
  {0,0,0}, {1,1,1}, {-1,-1,1}, {-1,1,-1}, {1,-1,-1},
  {11111,22222,246908642},
  {-11111,22222,-246908642},
  {11111,-22222,-246908642}
};

int DIV_VECTOR[VECTOR_SIZE][3] = {
  {1,1,1}, {1,1,1}, {-1,-1,1}, {-1,1,-1}, {1,-1,-1},
  {22222,11111,2},
  {22222,-11111,-2},
  {987654321,123456789,8}
};

unsigned int time_add = 0, time_mult = 0, time_div = 0;
unsigned int time_add_nm = 0, time_mult_nm = 0, time_div_nm = 0;
unsigned int time_branch = 0;
unsigned int time_timer_int = 0;
unsigned int time_stack_mem_w=0,time_stack_mem_r=0,time_stack_mem_wr=0;
unsigned int time_global_mem_w=0,time_global_mem_r=0,time_global_mem_wr=0;
unsigned int time_heap_mem_w=0,time_heap_mem_r=0,time_heap_mem_wr=0;
unsigned int time_print=0;
unsigned int time_comp=0;

unsigned int time_basic=0;
unsigned int time_basic_add=0, time_basic_add_imm=0, time_basic_mult=0, time_basic_div=0,
             time_basic_pic_read=0,time_basic_pic_write=0,
             time_basic_uart_read=0,
             time_basic_mem_read=0,time_basic_mem_write=0;


unsigned int basic_timer_address = Traits<EPOSSOC_Timer>::BASE_ADDRESS;
unsigned int basic_timer_prev = Traits<EPOSSOC_Timer>::BASE_ADDRESS;

void basic_add(){
    cout << "\tADD test\n";
    unsigned int a = 20;
    unsigned int b = 10;
    unsigned int c = 0;
    ASMV("basic_add_begin:\n"
            "lw  $24,0(%4)\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "add %0, %2, %3\n"
            "lw  $25,0(%4)\n"
            "subu %1, $25, $24\n"
            : "=&r" (c), "=&r" (time_basic_add)
            : "r" (a), "r" (b), "r" (basic_timer_address)
            : "$24", "$25");
}

void basic_add_imm(){
    cout << "\tADDI test\n";
    unsigned int a = 20;
    unsigned int c = 0;
    ASMV("basic_add_imm_begin:\n"
            "lw  $24,0(%4)\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "addi %0, %2, %3\n"
            "lw  $25,0(%4)\n"
            "subu %1, $25, $24\n"
            : "=&r" (c), "=&r" (time_basic_add_imm)
            : "r" (a), "i" (10), "r" (basic_timer_address)
            : "$24", "$25");
}

void basic_mult(){
    cout << "\tMULT test\n";
    unsigned int a = 20;
    unsigned int b = 10;
    unsigned int c = 0;
    ASMV("basic_mult_begin:\n"
            "lw  $24,0(%4)\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "mult %2, %3\n"
            "mflo %0\n"
            "lw  $25,0(%4)\n"
            "subu %1, $25, $24\n"
            : "=&r" (c), "=&r" (time_basic_mult)
            : "r" (a), "r" (b), "r" (basic_timer_address)
            : "$24", "$25");
}

void basic_div(){
    cout << "\tDIV test\n";
    unsigned int a = 20;
    unsigned int b = 10;
    unsigned int c = 0;
    ASMV("basic_div_begin:\n"
            "lw  $24,0(%4)\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "div %2, %3\n"
            "mflo %0\n"
            "lw  $25,0(%4)\n"
            "subu %1, $25, $24\n"
            : "=&r" (c), "=&r" (time_basic_div)
            : "r" (a), "r" (b), "r" (basic_timer_address)
            : "$24", "$25");
}

void basic_bus_read(unsigned int address, unsigned int & time_var){
    unsigned int data = 0;
    ASMV("basic_bus_read_begin:\n"
            "lw  $24,0(%3)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,0(%2)\n"
            "lw  $25,0(%3)\n"
            "subu %1, $25, $24\n"
            : "=&r" (data), "=&r" (time_var)
            : "r" (address), "r" (basic_timer_address)
            : "%0", "$24", "$25");
}

void basic_bus_write(unsigned int address, unsigned int data, unsigned int & time_var){
    ASMV("basic_bus_write_begin:\n"
            "lw  $24,0(%3)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,0(%2)\n"
            "lw  $25,0(%3)\n"
            "subu %0, $25, $24\n"
            : "=&r" (time_var)
            : "r" (data), "r" (address), "r" (basic_timer_address)
            : "$24", "$25");
}

void basic_mem_read(){
    cout << "\tMEM read test\n";
    unsigned char data[128];
    unsigned int address = (unsigned int)data;
    unsigned int data_aux = 0;
    ASMV("basic_mem_read_begin:\n"
            "lw  $24,0(%3)\n"
            "lw  %0,0(%2)\n"
            "lw  %0,4(%2)\n"
            "lw  %0,8(%2)\n"
            "lw  %0,12(%2)\n"
            "lw  %0,16(%2)\n"
            "lw  %0,20(%2)\n"
            "lw  %0,24(%2)\n"
            "lw  %0,28(%2)\n"
            "lw  %0,32(%2)\n"
            "lw  %0,36(%2)\n"
            "lw  %0,40(%2)\n"
            "lw  %0,44(%2)\n"
            "lw  %0,48(%2)\n"
            "lw  %0,52(%2)\n"
            "lw  %0,56(%2)\n"
            "lw  %0,60(%2)\n"
            "lw  %0,64(%2)\n"
            "lw  %0,68(%2)\n"
            "lw  %0,72(%2)\n"
            "lw  %0,76(%2)\n"
            "lw  %0,80(%2)\n"
            "lw  %0,84(%2)\n"
            "lw  %0,88(%2)\n"
            "lw  %0,92(%2)\n"
            "lw  %0,96(%2)\n"
            "lw  $25,0(%3)\n"
            "subu %1, $25, $24\n"
            : "=&r" (data_aux), "=&r" (time_basic_mem_read)
            : "r" (address), "r" (basic_timer_address)
            : "%0", "$24", "$25");
}

void basic_mem_write(){
    cout << "\tMEM write test\n";
    unsigned char data[128];
    unsigned int address = (unsigned int)data;
    unsigned int data_aux = 50;
    ASMV("basic_mem_write_begin:\n"
            "lw  $24,0(%3)\n"
            "sw  %1,0(%2)\n"
            "sw  %1,4(%2)\n"
            "sw  %1,8(%2)\n"
            "sw  %1,12(%2)\n"
            "sw  %1,16(%2)\n"
            "sw  %1,20(%2)\n"
            "sw  %1,24(%2)\n"
            "sw  %1,28(%2)\n"
            "sw  %1,32(%2)\n"
            "sw  %1,36(%2)\n"
            "sw  %1,40(%2)\n"
            "sw  %1,44(%2)\n"
            "sw  %1,48(%2)\n"
            "sw  %1,52(%2)\n"
            "sw  %1,56(%2)\n"
            "sw  %1,60(%2)\n"
            "sw  %1,64(%2)\n"
            "sw  %1,68(%2)\n"
            "sw  %1,72(%2)\n"
            "sw  %1,76(%2)\n"
            "sw  %1,80(%2)\n"
            "sw  %1,84(%2)\n"
            "sw  %1,88(%2)\n"
            "sw  %1,92(%2)\n"
            "sw  %1,96(%2)\n"
            "lw  $25,0(%3)\n"
            "subu %0, $25, $24\n"
            : "=&r" (time_basic_mem_write)
            : "r" (data_aux), "r" (address), "r" (basic_timer_address)
            : "$24", "$25");
}

void basic_tests(){

    cout << "Basic tests\n";
    basic_add();
    basic_add_imm();
    basic_mult();
    basic_div();

    cout << "\tPIC read test\n";
    basic_bus_read(Traits<EPOSSOC_IC>::BASE_ADDRESS, time_basic_pic_read);

    cout << "\tPIC write test\n";
    basic_bus_write(Traits<EPOSSOC_IC>::BASE_ADDRESS,
                    *reinterpret_cast<unsigned int*>(Traits<EPOSSOC_IC>::BASE_ADDRESS),
                    time_basic_pic_write);

    cout << "\tUART read test\n";
    basic_bus_read(Traits<EPOSSOC_UART>::BASE_ADDRESS, time_basic_uart_read);

    //basic_mem_read();
    //basic_mem_write();
    cout << "\tMEM read test\n";
    basic_bus_read((unsigned int)(new unsigned int), time_basic_mem_read);
    cout << "\tMEM write test\n";
    basic_bus_write((unsigned int)(new unsigned int), 0, time_basic_mem_write);

    time_basic = time_basic_add+time_basic_add_imm+time_basic_pic_read+time_basic_pic_write+
                 time_basic_div+time_basic_mem_read+time_basic_mem_write+time_basic_mult+
                 time_basic_uart_read;
}


void print_test(){

    unsigned int tmp = 0;


    cout << "Print test\n";

    tmp = *TIMER_REG;
    cout << "aaa" << "bbbb" << "ccccccccccccccccc" << tmp << "|"<< 1937 << "|1234567890\n";
    cout << "aaa" << "bbbb" << "ccccccccccccccccc" << tmp << "|"<< 1937 << "|1234567890\n";
    cout << "aaa" << "bbbb" << "ccccccccccccccccc" << tmp << "|"<< 1937 << "|1234567890\n";
    cout << "aaa" << "bbbb" << "ccccccccccccccccc" << tmp << "|"<< 1937 << "|1234567890\n";
    time_print = *TIMER_REG - tmp;
}


void integer_test(){

    cout << "Integer test\n";

    unsigned int tmp = 0;

    cout << "\tAdd test\n";
    tmp = *TIMER_REG;
    for (int i = 0; i < VECTOR_SIZE; ++i) {
        int a = ADD_VECTOR[i][0];
        int b = ADD_VECTOR[i][1];
        int result = a + b;
        int expected = ADD_VECTOR[i][2];
        if(result != expected){
            cout << "\tError - " << a << " + " << b << " = " << result << ". It should be " << expected << "\n";
            break;
        }
    }
    time_add = *TIMER_REG - tmp;

    cout << "\tMULT test\n";
    tmp = *TIMER_REG;
    for (unsigned int i = 0; i < VECTOR_SIZE; ++i) {
        int a = MULT_VECTOR[i][0];
        int b = MULT_VECTOR[i][1];
        int result = a * b;
        int expected = MULT_VECTOR[i][2];
        if(result == expected){
            a = b; //oK
        }
        else{
            cout << "\tError - " << a << " * " << b << " = " << result << ". It should be " << expected << "\n";
            break;
        }
    }
    time_mult = *TIMER_REG - tmp;

    cout << "\tDiv test\n";
    tmp = *TIMER_REG;
    for (int i = VECTOR_SIZE-1; i >= 0; --i) {
        int a = DIV_VECTOR[i][0];
        int b = DIV_VECTOR[i][1];
        int result = a / b;
        int expected = DIV_VECTOR[i][2];
        if(result != expected){
            cout << "\tError - " << a << " / " << b << " = " << result << ". It should be " << expected << "\n";
            break;
        }
    }
    time_div = *TIMER_REG - tmp;
}


void integer_test_no_mem(){

    cout << "Integer test: no memory access\n";

    unsigned int tmp = 0;

    unsigned int acc = 0;

    cout << "\tAdd test\n";
    tmp = *TIMER_REG;
    for (int i = 0; i < VECTOR_SIZE; ++i) {
        for (int j = 0; j < VECTOR_SIZE; ++j) {
            acc += i + j;
        }
    }
    time_add_nm = *TIMER_REG - tmp;
    cout << "\t\tacc=" << acc << "\n";

    cout << "\tMULT test\n";
    tmp = *TIMER_REG;
    for (int i = 0; i < VECTOR_SIZE; ++i) {
        for (int j = 0; j < VECTOR_SIZE; ++j) {
            acc += i * j;
        }
    }
    time_mult_nm = *TIMER_REG - tmp;
    cout << "\t\tacc=" << acc << "\n";

    cout << "\tDiv test\n";
    tmp = *TIMER_REG;
    for (int i = 1; i < VECTOR_SIZE; ++i) {
        for (int j = 1; j < VECTOR_SIZE; ++j) {
            acc = acc / j;
        }
    }
    time_div_nm = *TIMER_REG - tmp;
    cout << "\t\tacc=" << acc << "\n";
}

int brach_test_helper(int a, int b, int c,int d, int e, int f){
    int ret = 200;

    unsigned int tmp = *TIMER_REG;
    if(a > b) {
        if(d <= e) ret = 1;
        else if(e >= f) ret = 2;
        else if (a == d) ret = 3;
        else ret = 0;
    }
    else if (a < c) {
        if(d <= e) ret = 4;
        else if(e >= f) ret = 5;
        else if (a == d) ret = 6;
        else ret = 7;
    }
    else if (b > c) {
        if(d <= e) ret = 8;
        else if(e >= f) ret = 9;
        else if (a == d) ret = 10;
        else ret = 11;
    }
    else {
        if(d <= e) ret = 12;
        else if(e >= f) ret = 13;
        else if (a == d) ret = 14;
        else ret = 15;
    }
    time_branch += *TIMER_REG - tmp;

    return ret;
}

void branch_test(){
    cout << "Branch test\n";
    time_branch = 0;
    int acc = 0;
    for (int i = 0; i < VECTOR_SIZE; ++i) {
        for (int j = 0; j < VECTOR_SIZE; ++j) {
            for (int k = 0; k < VECTOR_SIZE; ++k) {
                acc += brach_test_helper(i, j, k,i,j,k);
            }
        }
    }
    cout << "\tacc=" << acc << "\n";
}


int timer_sched_thread(int idx){
    for (int i = 0; i < 2; ++i) {
        //cout << "\t\tThread " << idx << " iter " << i << "\n";
        Periodic_Thread::wait_next();
    }
    return 0;
}

void timer_sched_basic_test(){

    cout << "Timer/Interrupt test\n";
    cout << "\tWaiting 3 periodic threads execute 2 times\n";

    unsigned int tmp = *TIMER_REG;
    Periodic_Thread *t0 = new Periodic_Thread(timer_sched_thread, 0, 10000);
    Periodic_Thread *t1 = new Periodic_Thread(timer_sched_thread, 1, 10000);
    Periodic_Thread *t2 = new Periodic_Thread(timer_sched_thread, 2, 10000);

    t0->join();
    t1->join();
    t2->join();
    tmp = *TIMER_REG - tmp;

    cout << "\tDone\n";

    time_timer_int = tmp;
}

void mem_test_helper(int *memory, int SIZE,
                     unsigned int *time_w,
                     unsigned int *time_r,
                     unsigned int *time_rw
                     ){
    unsigned int tmp = 0;

    cout << "\t\tWrite\n";
    tmp = *TIMER_REG;
    for(int i = 0; i < SIZE; i += 1){
        memory[i] = i;
    }
    *time_w = *TIMER_REG - tmp;

    cout << "\t\tRead\n";
    tmp = *TIMER_REG;
    for(int i = 0; i < SIZE; i += 1){
        if(memory[i] != i){
            cout << "\t\tRead - Error\n";
            break;
        }
    }
    *time_r = *TIMER_REG - tmp;

    cout << "\t\tRead after write\n";
    tmp = *TIMER_REG;
    for(int i = 0, j = SIZE; i < SIZE; i += 1, --j){
        memory[i] = j;
        if(memory[i] != j){
            cout << "\t\tRead after write - Error\n";
            break;
        }
    }
    *time_rw = *TIMER_REG - tmp;
}

int mem_blk_global[MEM_BLOCK_SIZE];

void mem_test(){
    cout << "Memory test\n";

    cout << "\tStack memory\n";
    int mem_blk_stack[MEM_BLOCK_SIZE_STACK];
    mem_test_helper(mem_blk_stack, MEM_BLOCK_SIZE_STACK,
                    &time_stack_mem_w, &time_stack_mem_r, &time_stack_mem_wr);

    cout << "\tGlobal memory\n";
    mem_test_helper(mem_blk_global, MEM_BLOCK_SIZE,
                    &time_global_mem_w, &time_global_mem_r, &time_global_mem_wr);

    cout << "\tHeap memory\n";
    int *mem_blk_heap = new int[MEM_BLOCK_SIZE];
    mem_test_helper(mem_blk_heap, MEM_BLOCK_SIZE,
                    &time_heap_mem_w, &time_heap_mem_r, &time_heap_mem_wr);

}

void components_latency_test(){

    enum{
        TIMES = 5
    };

    cout << "Call latency test\n";

    Dummy_Caller caller(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);


    cout << "\tTesting 'uint func(uint)'\n";
    for (int i = 0; i < TIMES; ++i) {
        unsigned int aux = *TIMER_REG;
        aux = caller.func_tsc(aux);
        aux = *TIMER_REG - aux;
        cout << "\t\treturn time: " << aux << " cycles\n";
    }

    cout << "\tTesting 'uint func(char[16],uint)'\n";
    Implementation::safe_pkt_t pkt;
    for (int i = 0; i < TIMES; ++i) {
        unsigned int aux = *TIMER_REG;
        aux = caller.func_tsc_and_data(pkt,aux);
        aux = *TIMER_REG - aux;
        cout << "\t\treturn time: " << aux << " cycles\n";
    }

    cout << "\n\t'uint func(uint)' average latency: " << caller.get_acc_func_tsc()/TIMES;
    cout << "\n\t'uint func(char[16],uint)' average latency: " << caller.get_acc_func_tsc_and_data()/TIMES << "\n";

    time_comp += caller.get_acc_func_tsc();
    time_comp += caller.get_acc_func_tsc_and_data();
}

void vplat_test(){

    cout << "Vplat test\n";

    CPU::int_disable();
    IC::disable();

    print_test();
    basic_tests();
    integer_test();
    integer_test_no_mem();
    branch_test();
    mem_test();

    IC::enable(IC::IRQ_COMP_CONTRL);
    CPU::int_enable();
    components_latency_test();

    IC::enable(IC::IRQ_TIMER);
    timer_sched_basic_test();

    cout << "\nFinished. TImes:\n"
         << "\tPrint test: " << time_print << " cycles\n"
         << "\tBasic tests: " << time_basic << " cycles\n"
         << "\t\t add: " << time_basic_add << " cycles\n"
         << "\t\t addi: " << time_basic_add_imm << " cycles\n"
         << "\t\t mult: " << time_basic_mult << " cycles\n"
         << "\t\t div: " << time_basic_div << " cycles\n"
         << "\t\t pic_read: " << time_basic_pic_read << " cycles\n"
         << "\t\t pic_write: " << time_basic_pic_write << " cycles\n"
         << "\t\t uart_read: " << time_basic_uart_read << " cycles\n"
         << "\t\t mem_read: " << time_basic_mem_read << " cycles\n"
         << "\t\t mem_write: " << time_basic_mem_write << " cycles\n"
         << "\tInteger test\n"
         << "\t\tadd: " << time_add << " cycles\n"
         << "\t\tmult: " << time_mult << " cycles\n"
         << "\t\tdiv: " << time_div << " cycles\n"
         << "\tInteger test no mem\n"
         << "\t\tadd: " << time_add_nm << " cycles\n"
         << "\t\tmult: " << time_mult_nm << " cycles\n"
         << "\t\tdiv: " << time_div_nm << " cycles\n"
         << "\tbranch_test: " << time_branch << " cycles\n"
         << "\tMemory test\n"
         << "\t\tStack\n"
         << "\t\t\tRead: " << time_stack_mem_r << " cycles\n"
         << "\t\t\tWrite: " << time_stack_mem_w << " cycles\n"
         << "\t\t\tRead after Write: " << time_stack_mem_wr << " cycles\n"
         << "\t\tGlobal\n"
         << "\t\t\tRead: " << time_global_mem_r << " cycles\n"
         << "\t\t\tWrite: " << time_global_mem_w << " cycles\n"
         << "\t\t\tRead after Write: " << time_global_mem_wr << " cycles\n"
         << "\t\tHeap\n"
         << "\t\t\tRead: " << time_heap_mem_r << " cycles\n"
         << "\t\t\tWrite: " << time_heap_mem_w << " cycles\n"
         << "\t\t\tRead after Write: " << time_heap_mem_wr << " cycles\n"
         << "\tTimer/Interrupt/Thread test: " << time_timer_int << " cycles\n"
         << "\tComponents test: " << time_comp << " cycles\n"
         << "\tTOTAL: " << (//time_print +
                           time_basic +
                           time_add +
                           time_mult +
                           time_div +
                           time_add_nm +
                           time_mult_nm +
                           time_div_nm +
                           time_branch +
                           time_stack_mem_r +
                           time_stack_mem_w +
                           time_stack_mem_wr +
                           time_global_mem_r +
                           time_global_mem_w +
                           time_global_mem_wr +
                           time_heap_mem_r +
                           time_heap_mem_w +
                           time_heap_mem_wr +
                           //time_timer_int +
                           time_comp)
                         << " cycles\n";

}

int main()
{
  cout << "EPOS LOADED\n";

  vplat_test();

  cout << "\nThe End\n";
  cout << "kIiL";
  *((volatile unsigned int*)0xFFFFFFFC) = 0;
	
  return 0;
}

