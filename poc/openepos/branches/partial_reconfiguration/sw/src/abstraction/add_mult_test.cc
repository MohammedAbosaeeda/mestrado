// EPOS Add Abstraction Test Program

#include <components/add.h>
#include <components/mult.h>
#include <thread.h>
#include <semaphore.h>
#include <utility/ostream.h>

__USING_SYS

OStream cout;
Semaphore cout_sem(1);

void end_test(){

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;
}

void do_add(Add &add, unsigned int a, unsigned int b, unsigned int correct_result){
    cout_sem.p();

    cout << "Calling add.add(" << a << ", " << b << ")\n";
    cout_sem.v();
    unsigned int result = add.add(a,b);
    cout_sem.p();
    cout << "Result = " << (void*)result << ", should be " << (void*)correct_result;
    if(result != correct_result) {cout << " ###ERROR###\n"; end_test(); }
    cout << "\n";
    cout_sem.v();
}

void do_mult(Mult &mult, unsigned int a, unsigned int b, unsigned int correct_result){
    cout_sem.p();

    cout << "Calling mult.mult(" << a << ", " << b << ")\n";
    cout_sem.v();
    unsigned int result = mult.mult(a,b);
    cout_sem.p();
    cout << "Result = " << (void*)result << ", should be " << (void*)correct_result;
    if(result != correct_result) {cout << " ###ERROR###\n"; end_test(); }
    cout << "\n";
    cout_sem.v();
}

/*
void do_mult_square(Mult &mult, unsigned int a, unsigned int b, unsigned int correct_result){
    cout_sem.p();

    cout << "Calling mult.mult_square(" << a << ", " << b << ")\n";
    cout_sem.v();
    unsigned int result = mult.mult_square(a,b);
    cout_sem.p();
    cout << "Result = " << result << ", should be " << correct_result;
    if(result != correct_result) {cout << " ###ERROR###\n"; end_test(); }
    cout << "\n";
    cout_sem.v();
}*/

void serial_test(){


    Add add(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);
    Mult mult(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    cout << "\nSerial Test\n\n";

    do_add(add,0,0,0);
    do_add(add,40,0,40);
    do_add(add,0,40,40);
    do_mult(mult,0,0,0);
    do_mult(mult,0,10,0);
    do_add(add,1,39,40);
    do_add(add,39,1,40);
    //do_mult_square(mult,0,0,0);
    //do_mult_square(mult,0,10,0);
    do_add(add,20,20,40);
    do_add(add,35,5,40);
    do_mult(mult,10,0,0);
    do_mult(mult,1,10,10);
    //do_mult_square(mult,10,0,0);
    //do_mult_square(mult,1,10,100);
    do_mult(mult,10,1,10);
    do_mult(mult,10,5,50);
    //do_mult_square(mult,10,1,100);
    //do_mult_square(mult,10,5,2500);
    do_mult(mult,5,10,50);
    do_mult(mult,10,10,100);
    //do_mult_square(mult,5,10,2500);
    //do_mult_square(mult,10,10,10000);

}

int add_func(){
    Add add(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    do_add(add,0,0,0);
    do_add(add,40,0,40);
    do_add(add,0,40,40);
    do_add(add,1,39,40);
    do_add(add,39,1,40);
    do_add(add,20,20,40);
    do_add(add,35,5,40);

    cout_sem.p();
    cout << "add_func end\n";
    cout_sem.v();

    return 0;
}

int mult_func(){
    Mult mult(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    do_mult(mult,0,0,0);
    do_mult(mult,0,10,0);
    //do_mult_square(mult,0,0,0);
    //do_mult_square(mult,0,10,0);
    do_mult(mult,10,0,0);
    do_mult(mult,1,10,10);
    //do_mult_square(mult,10,0,0);
    //do_mult_square(mult,1,10,100);
    do_mult(mult,10,1,10);
    do_mult(mult,10,5,50);
    //do_mult_square(mult,10,1,100);
    //do_mult_square(mult,10,5,2500);
    do_mult(mult,5,10,50);
    do_mult(mult,10,10,100);
    //do_mult_square(mult,5,10,2500);
    //do_mult_square(mult,10,10,10000);

    cout_sem.p();
    cout << "mult_func end\n";
    cout_sem.v();

    return 0;
}

void parallel_test(){

    cout << "\nParallel Test\n\n";

    Thread *add = new Thread(&add_func);
    Thread *mult = new Thread(&mult_func);

    int status_add = add->join();
    int status_mult = mult->join();

    cout << "Thread ADD exited with status " << status_add
            << " and thread MULT exited with status " << status_mult << "\n";

    delete add;
    delete mult;
}


int main()
{

    serial_test();

    parallel_test();

    end_test();

    return 0;
}
