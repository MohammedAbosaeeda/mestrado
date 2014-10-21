// EPOS Add Abstraction Test Program

#include <dummy_callee.h>
#include <noc.h>

__USING_SYS

STUB(Dummy_Callee) callee_0;
//STUB(Dummy_Callee) callee_1;
//DISPATCHER(Dummy_Callee) callee_0(0);
//DISPATCHER(Dummy_Callee) callee_1(0);

int main()
{

//    NOC::Address addr; NOC::Packet pkt;
//    callee_0.local_dispatch(addr, pkt);
    //callee_1.local_dispatch(addr, pkt);

    callee_0.func_0(0);
    //callee_1.func_0(0);
    callee_0.func_0(0,0);
    //callee_0.func_0(0,0,0,0);
    //callee_0.func_0(0,0,0,0,0,0,0,0);

    return 0;
}
