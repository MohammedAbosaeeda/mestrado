// EPOS Alarm Abstraction Initialization

#include <component_manager.h>
#include <system/kmalloc.h>
#include <add.h>

__BEGIN_SYS

void Component_Manager::init()
{
    NOC &_noc = NOC::get_instance();
    _noc.receive_int(&Component_Manager::int_handler);

    db<Component_Manager>(INF) << "Component_Manager::init(): Adding the following resources:\n";
    if(Traits<Unified::Add>::hardware){
        db<Component_Manager>(INF) << "\tAdd:\n";
        init_hw_resource(Unified::ADD_ID, Unified::Resource_Table<Unified::Add,0>::IID[0], Unified::Resource_Table<Unified::Add,0>::X, Unified::Resource_Table<Unified::Add,0>::Y, Unified::Resource_Table<Unified::Add,0>::LOCAL);
    }
    if(Traits<Unified::Mult>::hardware){
        db<Component_Manager>(INF) << "\tMult:\n";
        init_hw_resource(Unified::MULT_ID, Unified::Resource_Table<Unified::Mult,0>::IID[0], Unified::Resource_Table<Unified::Mult,0>::X, Unified::Resource_Table<Unified::Mult,0>::Y, Unified::Resource_Table<Unified::Mult,0>::LOCAL);
    }

    db<Component_Manager>(INF) << "Component_Manager::init(): Adding the following software resources:\n";
    if(Traits<Unified::Mult>::hardware && !Traits<Unified::Add>::hardware){
        db<Component_Manager>(INF) << "\tAdd:\n";
        DISPATCHER(Add) *aux = new(kmalloc(sizeof(DISPATCHER(Add)))) Add_Dispatcher(const_cast<unsigned char*>(&Unified::Resource_Table<Unified::Mult,0>::IID[1]));
        init_sw_resource(Unified::ADD_ID, Unified::Resource_Table<Unified::Add,0>::IID[0], &DISPATCHER(Add)::dispatch, (void*)aux);
    }
}

inline void Component_Manager::init_hw_resource(int type_id, unsigned int iid, unsigned int addr_x, unsigned int addr_y, unsigned int addr_local){
    db<Component_Manager>(INF) << "\t\tTID=" << type_id << ",IID=" << iid << ",PHY_X=" << addr_x << ",PHY_Y=" << addr_y << ",PHY_LOCAL=" << addr_local << "\n" ;

    HW_Resource *rsc = new(kmalloc(sizeof(HW_Resource))) HW_Resource(addr_x,addr_y,addr_local, (Unified::Type_Id)type_id, iid);

    Waiting_Resp_Elem* resp_elem = new(kmalloc(sizeof(Waiting_Resp_Elem))) Waiting_Resp_Elem(rsc);
    rsc->resp_elem = resp_elem;

    HW_Resource_Elem *rsc_elem = new(kmalloc(sizeof(HW_Resource_Elem))) HW_Resource_Elem(rsc);

    _hw_resources.insert(rsc_elem);
}

inline void Component_Manager::init_sw_resource(int type_id, unsigned int iid, SW_Resource::Dispatcher disp, void* object){
    db<Component_Manager>(INF) << "\t\tTID=" << type_id << ",IID=" << iid << "\n" ;
    SW_Resource *rsc = new(kmalloc(sizeof(SW_Resource))) SW_Resource((Unified::Type_Id)type_id, iid, disp, object);
    SW_Resource_Elem *rsc_elem = new(kmalloc(sizeof(SW_Resource_Elem))) SW_Resource_Elem(rsc);
    _sw_resources.insert(rsc_elem);
}

__END_SYS
