// EPOS Alarm Abstraction Initialization

#include <component_manager.h>
#include <system/kmalloc.h>
#include <components/add.h>
#include <components/dummy_callee.h>
#include <components/rsp_eth.h>

__BEGIN_SYS

void Component_Manager::init_ints()
{
    Component_Controller::enable_agent_receive_int(&int_handler);
    CPU::int_enable();
}

void Component_Manager::init()
{

    db<Component_Manager>(INF) << "Component_Manager::init(): Adding the following resources:\n";
    if(Traits<Implementation::Add>::hardware){
        db<Component_Manager>(INF) << "\tAdd:\n";
        init_hw_resource(Implementation::ADD_ID, Implementation::Resource_Table<Implementation::Add,0>::IID[0], Implementation::Resource_Table<Implementation::Add,0>::X, Implementation::Resource_Table<Implementation::Add,0>::Y, Implementation::Resource_Table<Implementation::Add,0>::LOCAL);
    }
    if(Traits<Implementation::Mult>::hardware){
        db<Component_Manager>(INF) << "\tMult:\n";
        init_hw_resource(Implementation::MULT_ID, Implementation::Resource_Table<Implementation::Mult,0>::IID[0], Implementation::Resource_Table<Implementation::Mult,0>::X, Implementation::Resource_Table<Implementation::Mult,0>::Y, Implementation::Resource_Table<Implementation::Mult,0>::LOCAL);
    }
    if(Traits<Implementation::ADPCM_Codec>::hardware){
        db<Component_Manager>(INF) << "\tADPCM_Codec:\n";
        init_hw_resource(Implementation::ADPCM_CODEC_ID, Implementation::Resource_Table<Implementation::ADPCM_Codec,0>::IID[0], Implementation::Resource_Table<Implementation::ADPCM_Codec,0>::X, Implementation::Resource_Table<Implementation::ADPCM_Codec,0>::Y, Implementation::Resource_Table<Implementation::ADPCM_Codec,0>::LOCAL);
    }
    if(Traits<Implementation::DTMF_Detector>::hardware){
        db<Component_Manager>(INF) << "\tDTMF_Detector:\n";
        init_hw_resource(Implementation::DTMF_DETECTOR_ID, Implementation::Resource_Table<Implementation::DTMF_Detector,0>::IID[0], Implementation::Resource_Table<Implementation::DTMF_Detector,0>::X, Implementation::Resource_Table<Implementation::DTMF_Detector,0>::Y, Implementation::Resource_Table<Implementation::DTMF_Detector,0>::LOCAL);
    }
    if(Traits<Implementation::Sched<void,void> >::hardware){
        db<Component_Manager>(INF) << "\tSched_Thread:\n";
        init_hw_resource(Implementation::SCHED_ID, Implementation::Resource_Table<Implementation::Sched<void,void>,0>::IID[0], Implementation::Resource_Table<Implementation::Sched<void,void>,0>::X, Implementation::Resource_Table<Implementation::Sched<void,void>,0>::Y, Implementation::Resource_Table<Implementation::Sched<void,void>,0>::LOCAL);
    }
    if(Traits<Implementation::Dummy_Callee>::hardware){
        db<Component_Manager>(INF) << "\tDummy_Callee:\n";
        init_hw_resource(Implementation::DUMMY_CALLEE_ID, Implementation::Resource_Table<Implementation::Dummy_Callee,0>::IID[0], Implementation::Resource_Table<Implementation::Dummy_Callee,0>::X, Implementation::Resource_Table<Implementation::Dummy_Callee,0>::Y, Implementation::Resource_Table<Implementation::Dummy_Callee,0>::LOCAL);
    }
    if(Traits<Implementation::Dummy_Caller>::hardware){
        db<Component_Manager>(INF) << "\tDummy_Caller:\n";
        init_hw_resource(Implementation::DUMMY_CALLER_ID, Implementation::Resource_Table<Implementation::Dummy_Caller,0>::IID[0], Implementation::Resource_Table<Implementation::Dummy_Caller,0>::X, Implementation::Resource_Table<Implementation::Dummy_Caller,0>::Y, Implementation::Resource_Table<Implementation::Dummy_Caller,0>::LOCAL);
    }


    if(Traits<Implementation::RSP_ETH>::hardware){
        db<Component_Manager>(INF) << "\tRSP_ETH:\n";
        init_hw_resource(Implementation::RSP_ETH_ID, Implementation::Resource_Table<Implementation::RSP_ETH,0>::IID[0], Implementation::Resource_Table<Implementation::RSP_ETH,0>::X, Implementation::Resource_Table<Implementation::RSP_ETH,0>::Y, Implementation::Resource_Table<Implementation::RSP_ETH,0>::LOCAL);
    }
    if(Traits<Implementation::RSP_AES>::hardware){
        db<Component_Manager>(INF) << "\tRSP_AES:\n";
        init_hw_resource(Implementation::RSP_AES_ID, Implementation::Resource_Table<Implementation::RSP_AES,0>::IID[0], Implementation::Resource_Table<Implementation::RSP_AES,0>::X, Implementation::Resource_Table<Implementation::RSP_AES,0>::Y, Implementation::Resource_Table<Implementation::RSP_AES,0>::LOCAL);
    }
    if(Traits<Implementation::RSP_ADPCM>::hardware){
        db<Component_Manager>(INF) << "\tRSP_ADPCM:\n";
        init_hw_resource(Implementation::RSP_ADPCM_ID, Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::IID[0], Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::X, Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::Y, Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::LOCAL);
    }
    if(Traits<Implementation::RSP_DTMF>::hardware){
        db<Component_Manager>(INF) << "\tRSP_DTMF:\n";
        init_hw_resource(Implementation::RSP_DTMF_ID, Implementation::Resource_Table<Implementation::RSP_DTMF,0>::IID[0], Implementation::Resource_Table<Implementation::RSP_DTMF,0>::X, Implementation::Resource_Table<Implementation::RSP_DTMF,0>::Y, Implementation::Resource_Table<Implementation::RSP_DTMF,0>::LOCAL);
    }


    db<Component_Manager>(INF) << "Component_Manager::init(): Adding the following software resources:\n";
    if(Traits<Implementation::Mult>::hardware && !Traits<Implementation::Add>::hardware){
        db<Component_Manager>(INF) << "\tAdd:\n";
        Implementation::Agent<Implementation::Add> *aux = new(kmalloc(sizeof(Implementation::Agent<Implementation::Add>))) Implementation::Agent<Implementation::Add>(dummy_channel,dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::Mult,0>::IID[1]));
        init_sw_resource(Implementation::ADD_ID, Implementation::Resource_Table<Implementation::Add,0>::IID[0], &Implementation::Agent<Implementation::Add>::static_top_level, (void*)aux);
    }
    if(Traits<Implementation::Dummy_Caller>::hardware && !Traits<Implementation::Dummy_Callee>::hardware){
        db<Component_Manager>(INF) << "\tDummy_Callee:\n";
        Implementation::Agent<Implementation::Dummy_Callee> *aux = new(kmalloc(sizeof(Implementation::Agent<Implementation::Dummy_Callee>))) Implementation::Agent<Implementation::Dummy_Callee>(dummy_channel,dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::Dummy_Caller,0>::IID[1]));
        init_sw_resource(Implementation::DUMMY_CALLEE_ID, Implementation::Resource_Table<Implementation::Dummy_Callee,0>::IID[0], &Implementation::Agent<Implementation::Dummy_Callee>::static_top_level, (void*)aux);
    }


    if(Traits<Implementation::RSP_DTMF>::hardware && !Traits<Implementation::RSP_Controller>::hardware){
        db<Component_Manager>(INF) << "\tRSP_Controller:\n";
        Implementation::Agent<Implementation::RSP_Controller> *aux = new(kmalloc(sizeof(Implementation::Agent<Implementation::RSP_Controller>))) Implementation::Agent<Implementation::RSP_Controller>(dummy_channel,dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_DTMF,0>::IID[1]));
        init_sw_resource(Implementation::RSP_CONTROLLER_ID, Implementation::Resource_Table<Implementation::RSP_Controller,0>::IID[0], &Implementation::Agent<Implementation::RSP_Controller>::static_top_level, (void*)aux);
    }
    if(Traits<Implementation::RSP_ADPCM>::hardware && !Traits<Implementation::RSP_DTMF>::hardware){
        db<Component_Manager>(INF) << "\tRSP_DTMF:\n";
        Implementation::Agent<Implementation::RSP_DTMF> *aux = new(kmalloc(sizeof(Implementation::Agent<Implementation::RSP_DTMF>))) Implementation::Agent<Implementation::RSP_DTMF>(dummy_channel,dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::IID[1]));
        init_sw_resource(Implementation::RSP_DTMF_ID, Implementation::Resource_Table<Implementation::RSP_DTMF,0>::IID[0], &Implementation::Agent<Implementation::RSP_DTMF>::static_top_level, (void*)aux);
    }
    if(Traits<Implementation::RSP_AES>::hardware && !Traits<Implementation::RSP_ADPCM>::hardware){
        db<Component_Manager>(INF) << "\tRSP_ADPCM:\n";
        Implementation::Agent<Implementation::RSP_ADPCM> *aux = new(kmalloc(sizeof(Implementation::Agent<Implementation::RSP_ADPCM>))) Implementation::Agent<Implementation::RSP_ADPCM>(dummy_channel,dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_AES,0>::IID[1]));
        init_sw_resource(Implementation::RSP_ADPCM_ID, Implementation::Resource_Table<Implementation::RSP_ADPCM,0>::IID[0], &Implementation::Agent<Implementation::RSP_ADPCM>::static_top_level, (void*)aux);
    }
    if(Traits<Implementation::RSP_ETH>::hardware && !Traits<Implementation::RSP_AES>::hardware){
        db<Component_Manager>(INF) << "\tRSP_AES:\n";
        Implementation::Agent<Implementation::RSP_AES> *aux = new(kmalloc(sizeof(Implementation::Agent<Implementation::RSP_AES>))) Implementation::Agent<Implementation::RSP_AES>(dummy_channel,dummy_channel,const_cast<unsigned char*>(&Implementation::Resource_Table<Implementation::RSP_ETH,0>::IID[1]));
        init_sw_resource(Implementation::RSP_AES_ID, Implementation::Resource_Table<Implementation::RSP_AES,0>::IID[0], &Implementation::Agent<Implementation::RSP_AES>::static_top_level, (void*)aux);
    }

    init_ints();
}

inline void Component_Manager::init_hw_resource(int type_id, unsigned int iid, unsigned int addr_x, unsigned int addr_y, unsigned int addr_local){
    db<Component_Manager>(INF) << "\t\tTID=" << type_id << ",IID=" << iid << ",PHY_X=" << addr_x << ",PHY_Y=" << addr_y << ",PHY_LOCAL=" << addr_local << "\n" ;

    unsigned int buffer =
    Component_Controller::alloc_proxy(addr_x,addr_y,addr_local, //phy
                                      (unsigned int)type_id, iid);
    if(buffer != Component_Controller::CMD_RESULT_ERR){
        HW_Resource *rsc = new(kmalloc(sizeof(HW_Resource))) HW_Resource(buffer, (Type_Id)type_id);
        HW_Resource_Elem *rsc_elem = new(kmalloc(sizeof(HW_Resource_Elem))) HW_Resource_Elem(rsc);
        _hw_resources.insert(rsc_elem);
    }
}

inline void Component_Manager::init_sw_resource(int type_id, unsigned int iid, SW_Dispatcher disp, void* object){
    db<Component_Manager>(INF) << "\t\tTID=" << type_id << ",IID=" << iid << "\n" ;

    Component_Controller::alloc_agent((unsigned int)type_id, iid,
                                      (unsigned int)disp,
                                      (unsigned int)object);
}

__END_SYS
