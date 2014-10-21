// EPOS Mult Abstraction Test Program

#include <components/dummy_caller.h>
#include <machine.h>
#include <periodic_thread.h>

//#define SIM_TIMER
//#define TIMER_INT_SCHED_TEST


__USING_SYS

OStream cout;

volatile unsigned int * TIMER_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS);
volatile unsigned int * REAL_REG = reinterpret_cast<volatile unsigned int *>(Traits<EPOSSOC_Timer>::BASE_ADDRESS+4);

enum{
    TIMES = 10
};

int timer_sched_thread(int idx){
    for (int i = 0; i < 5; ++i) {
        cout << "\t\tThread " << idx << " iter " << i << "\n";
        Periodic_Thread::wait_next();
    }
    return 0;
}

void comp_controller_test();

void timer_sched_basic_test(){

    cout << "Timer/Interrupt test\n";
    cout << "\tWaiting 3 periodic threads execute 5 times\n";

    Periodic_Thread *t0 = new Periodic_Thread(timer_sched_thread, 0, 10000);
    Periodic_Thread *t1 = new Periodic_Thread(timer_sched_thread, 1, 10000);
    Periodic_Thread *t2 = new Periodic_Thread(timer_sched_thread, 2, 10000);

    t0->join();
    t1->join();
    t2->join();

    cout << "\tDone\n";
}

const bool caller_harware = Implementation::Traits<Implementation::Dummy_Caller>::hardware;
const bool callee_harware = Implementation::Traits<Implementation::Dummy_Callee>::hardware;

#define CALL_PROLOGUE(func_name)\
    return_acc = 0;\
    cout << "Testing '"<< func_name << "'\n";\
    for (int i = 0; i < TIMES; ++i) {\
        unsigned int arg = *TIMER_REG

#define CALL_EPILOGUE(internal_acc)\
        unsigned int ret_time = *TIMER_REG - arg;\
        return_acc += ret_time;\
    }\
    if(caller_harware && !callee_harware){\
        cout << "SW->HW->SW average latency: " << (return_acc/TIMES)-17 << "\n";\
        cout << "SW->HW->SW average latency (no return): " << caller.internal_acc()/TIMES << "\n\n"; \
    }\
    else if(caller_harware && callee_harware){\
        cout << "SW->HW average latency: " << (return_acc/TIMES)-17 << "\n\n";\
    }
#define CALL_EPILOGUE2()\
        unsigned int ret_time = *TIMER_REG - arg;\
        return_acc += ret_time;\
    }\
    if(caller_harware && !callee_harware){\
        cout << "SW->HW->SW average latency: " << (return_acc/TIMES)-17 << "\n\n";\
    }\
    else if(caller_harware && callee_harware){\
        cout << "SW->HW average latency: " << (return_acc/TIMES)-17 << "\n\n";\
    }

int main()
{
    //comp_controller_test();

    ///*

    //Component_Manager::init_ints();
    //EPOSSOC_IC::enable(EPOSSOC_IC::IRQ_COMP_CONTRL);

    CPU::int_enable();//WTF ????

    //removes scheduler jiter
    EPOSSOC_IC::disable(EPOSSOC_IC::IRQ_TIMER);

    unsigned int sim_time = 0;
    unsigned int real_time = 0;
#ifdef SIM_TIMER
    sim_time = *REAL_REG;
#endif
    real_time = *TIMER_REG;

    cout << "\nCall latency test\n\n";

    Dummy_Caller caller(Component_Manager::dummy_channel,Component_Manager::dummy_channel,0);

    unsigned int return_acc = 0;

    CALL_PROLOGUE("void func()");
        caller.func_0_0();
    CALL_EPILOGUE2();

    CALL_PROLOGUE("uint func()");
        caller.func_1_0();
    CALL_EPILOGUE2();

    CALL_PROLOGUE("void func(uint)");
        caller.func_0_1(arg);
    CALL_EPILOGUE(func_0_1_acc);

    CALL_PROLOGUE("uint func(uint)");
        unsigned int ret = caller.func_1_1(arg);
        if(ret!=arg) {cout << "ERROR\n";break;}
    CALL_EPILOGUE(func_1_1_acc);

    CALL_PROLOGUE("uint func(uint,uint)");
        unsigned int ret = caller.func_1_2(0,arg);
        if(ret!=arg) {cout << "ERROR\n";break;}
    CALL_EPILOGUE(func_1_2_acc);

    CALL_PROLOGUE("uint func(uint,uint,uint,uint)");
        unsigned int ret = caller.func_1_4(0,0,0,arg);
        if(ret!=arg) {cout << "ERROR\n";break;}
    CALL_EPILOGUE(func_1_4_acc);

    CALL_PROLOGUE("uint func(uint,uint,uint,uint,uint,uint,uint,uint)");
        unsigned int ret = caller.func_1_8(0,0,0,0,0,0,0,arg);
        if(ret!=arg) {cout << "ERROR\n";break;}
    CALL_EPILOGUE(func_1_8_acc);

    Implementation::safe_pkt_t pkt;
    CALL_PROLOGUE("uint func(char[16],uint)");
        unsigned int ret = caller.func_1_pkt_1(pkt,arg);
        if(ret!=arg) {cout << "ERROR\n";break;}
    CALL_EPILOGUE(func_1_pkt_1_acc);



    real_time = *TIMER_REG - real_time;
#ifdef SIM_TIMER
    sim_time = *REAL_REG - sim_time;
#endif
    cout << "\nApp real time " << real_time << " cycles\n";
    cout << "App sim time " << sim_time << " ms\n";

#ifdef TIMER_INT_SCHED_TEST
    //cout << "\n";
    //timer_sched_basic_test();
#endif

    cout << "\nThe End\n";
    *((volatile unsigned int*)0xFFFFFFFC) = 0;

    return 0;
}

namespace Comp_Controller_Test{

enum{
        CALLEE_DISPATCHER = 0xABCDEFAB
    };

    unsigned int caller_proxy_buff;


    unsigned int callee_agent_buff;
    enum{
        CALLE_STATE_RX_CALL,
        CALLE_STATE_RX_CALL_DATA,
        CALLE_STATE_DO_CALL,
    };
    unsigned int call_agent_state;

    void proxy_call_func_tsc(){
        unsigned int arg = *TIMER_REG;
        cout << "Sending call 'unsigned int func_tsc("<<arg<<")'\n";
        Component_Controller::send_call(caller_proxy_buff,
                          0//OP_FUNC_TSC
                          );
        Component_Controller::send_call_data(caller_proxy_buff,
                               1, &arg);

        cout << "Waiting return\n";
        unsigned int ret;
        Component_Controller::receive_return_data(caller_proxy_buff, 1, &ret);
        cout << "Call 'unsigned int func_tsc(arg)' returned " << ret << "\n";
        cout << "SW->HW->SW call return: " << (*TIMER_REG)-ret << " cycles\n";
    }

    void proxy_call_func_tsc_and_data(){
        unsigned int arg[5];
        unsigned char *data = reinterpret_cast<unsigned char*>(arg);
        for (int i = 0; i < 16; ++i) data[i] = i;
        arg[4] = *TIMER_REG;

        cout << "Sending call 'unsigned int func_tsc_and_data(char[16])'\n";
        Component_Controller::send_call(caller_proxy_buff,
                          1//OP_FUNC_TSC_AND_DATA
                          );
        Component_Controller::send_call_data(caller_proxy_buff,
                               5, arg);

        cout << "Waiting return\n";
        unsigned int ret;
        Component_Controller::receive_return_data(caller_proxy_buff, 1, &ret);
        cout << "Call 'unsigned int func_tsc(char[16])' returned " << ret << "\n";
        cout << "SW->HW->SW call return: " << (*TIMER_REG)-ret << " cycles\n";
    }


    unsigned int int_op_id = 25;//random
    unsigned int int_data[5];
    unsigned int int_idx = 0;

    void int_handler(unsigned int){

        cout << "INT begin\n";
        Component_Controller::disable_agent_receive_int();
        Component_Controller::agent_call_info call_info;

        while(Component_Controller::agent_has_call(call_info)) {
            if(call_info.dispatcher_address != CALLEE_DISPATCHER){
                cout << "--ERR: Dispatcher doesn't match, dispatcher=" << call_info.dispatcher_address << ", it should be " << CALLEE_DISPATCHER << "\n";
                break;
            }

            if(call_agent_state == CALLE_STATE_RX_CALL){
                int_op_id = Component_Controller::receive_call(callee_agent_buff);
                cout << "Callee received call with op_id = " << int_op_id << "\n";
                if((int_op_id == 0) || (int_op_id == 1)) { //op_func_tsc/op_func_tsc_and_data
                    call_agent_state = CALLE_STATE_RX_CALL_DATA;
                }
                else{
                    cout << "-- ERR: Wrong operation, op_id=" << int_op_id << ", it should be 0 or 1\n";
                }
            }
            else if(call_agent_state == CALLE_STATE_RX_CALL_DATA){
                int_data[int_idx] = Component_Controller::receive_call_data(callee_agent_buff);
                cout << "Callee received call with data = " << (void*)(int_data[int_idx]) << "\n";
                ++int_idx;

                if( ((int_op_id == 0) && (int_idx == 1)) ||
                        ((int_op_id == 1) && (int_idx == 5)) ){

                    int_idx = 0;

                    if(int_op_id == 0){
                        unsigned int diff = (*TIMER_REG) - int_data[0];
                        cout << "SW->HW->SW call: " << diff << " cycles\n";

                        unsigned int tsc = (*TIMER_REG);
                        cout << "Returning " << tsc << "\n";
                        int_data[0] = tsc;
                        Component_Controller::send_return_data(callee_agent_buff, 1, int_data);
                    }
                    else{
                        unsigned int diff = (*TIMER_REG) - int_data[4];
                        cout << "SW->HW->SW call: " << diff << " cycles\n";

                        unsigned int tsc = (*TIMER_REG);
                        cout << "Returning " << tsc << "\n";
                        int_data[0] = tsc;
                        Component_Controller::send_return_data(callee_agent_buff, 1, int_data);
                    }

                    call_agent_state = CALLE_STATE_RX_CALL;
                }


            }
        }
        Component_Controller::enable_agent_receive_int(&int_handler);
        cout << "INT end\n";
    }


    void exec_testbench(){

        cout << "\n\nComponent controller test\n\n";

        cout << "Allocating caller proxy buffer\n";
        caller_proxy_buff = Component_Controller::alloc_proxy(0,0,3, //phy
                                                6,//type_id
                                                0 //iid
                                                );
        cout << "Using buffer " << caller_proxy_buff << "\n";

        cout << "Allocating a dummy proxy buffer\n";
        unsigned int val = Component_Controller::alloc_proxy(0,0,0,0,0);
        cout << "Returned " << val << "\n";

        cout << "Allocating a dummy agent buffer\n";
        val = Component_Controller::alloc_agent(1,0,0,0);
        cout << "Returned " << val << "\n";

        cout << "Allocating callee agent buffer\n";
        callee_agent_buff = Component_Controller::alloc_agent(5,//type_id
                                                0, //iid
                                                CALLEE_DISPATCHER, //dispatcher_addr
                                                CALLEE_DISPATCHER);
        cout << "Using buffer " << callee_agent_buff << "\n";

        cout << "Allocating another dummy proxy buffer. Should return an error\n";
        val = Component_Controller::alloc_proxy(0,0,0,0,0);
        cout << "Returned " << val << "\n";


        cout << "Setting up interrupts\n";
        call_agent_state = CALLE_STATE_RX_CALL;
        Component_Controller::enable_agent_receive_int(&int_handler);

        proxy_call_func_tsc();

        proxy_call_func_tsc_and_data();

        proxy_call_func_tsc();

        proxy_call_func_tsc_and_data();

        cout << "Done\n";

    }

};

void comp_controller_test(){
    IC::disable(IC::IRQ_NOC);
    Comp_Controller_Test::exec_testbench();
}
