#include <component_manager.h>

__BEGIN_SYS

Component_Manager::HW_Resources Component_Manager::_hw_resources;
Component_Manager::SW_Resources Component_Manager::_sw_resources;
Component_Manager::Waiting_Resp_List Component_Manager::_waiting_resp_list;

Component_Manager::HW_Resource_Elem* Component_Manager::allocate(Unified::Type_Id id){

    Component_Manager::HW_Resource_Elem* resource = 0;
    for (HW_Resources::Iterator it = _hw_resources.begin(); it != _hw_resources.end(); it++) {
        resource = it;
        if(resource->object()->type_id == id){
           break;
        }
    }

    if(resource){
        _hw_resources.remove(resource);

        db<Component_Manager>(TRC) << "Component_Manager::allocate(" << id << "): Resource allocated" << resource->object()->address << "\n";
    }
    else{
        db<Component_Manager>(WRN) << "Component_Manager::allocate(" << id << "): No resource available\n";
    }

    return resource;
}

void Component_Manager::deallocate(Component_Manager::HW_Resource_Elem* resource){

    if(resource) {

        //FIXME maybe this is necessary ?
        //Waiting_Resp_Elem* aux = resource->object()->resp_elem;
        //_waiting_resp_list.remove(aux);

        _hw_resources.insert(resource);

        db<Component_Manager>(TRC) << "Component_Manager::deallocate(" << resource->object()->address << "): Resource deallocated\n";
    }
}

void Component_Manager::call(Component_Manager::HW_Resource_Elem const* resource,
                   unsigned int op_id, unsigned int n_params, unsigned int n_return, unsigned int *data){

    db<Component_Manager>(TRC) << "Component_Manager::call("
                     << resource->object() << ", "
                     << op_id << ", " <<
                     n_params << ", " <<
                     n_return << ", " << (void*)data << "(";
    for (unsigned int i = 0; i < n_params; ++i) db<Component_Manager>(TRC) << (void*)data[i] << ",";
    db<Component_Manager>(TRC) <<  "))\n";

    NOC::Address* addr = &resource->object()->address;
    NOC::Packet pkt;
    Comp_Msg::set_msg_type(pkt, MSG_TYPE_CALL);
    Comp_Msg::set_type_id(pkt, resource->object()->type_id);
    Comp_Msg::set_instance_id(pkt, resource->object()->instance_id);
    Comp_Msg::set_op_id(pkt, op_id);

    db<Component_Manager>(TRC) << "Component_Manager::call(): Sending Call header\n";

    _waiting_resp_list.insert(resource->object()->resp_elem);

    NOC &_noc = NOC::get_instance();
    //_noc.lock();

    _noc.send_header(addr);
    _noc.send(&pkt);

    Comp_Msg::set_msg_type(pkt, MSG_TYPE_CALL_DATA);
    for (unsigned int i = 0; i < n_params; ++i){
        db<Component_Manager>(TRC) << "Component_Manager::call(): Sending Call data " << (void*)data[i] << "\n";
        Comp_Msg::set_data(pkt,data[i]);
        _noc.send(&pkt);
    }
    db<Component_Manager>(TRC) << "Component_Manager::call(): Call sent\n";
    //_noc.unlock();

    for (unsigned int i = 0; i < n_return; ++i) {
        db<Component_Manager>(TRC) << "Component_Manager::call(): Wait response " << i << "\n";
        while(!(resource->object()->resp_flag));
        resource->object()->resp_flag = false;
        unsigned int resp_pkt = resource->object()->resp_value;

        db<Component_Manager>(TRC) << "Component_Manager::call(): Reading response " << (void*)(resp_pkt) << "\n";
        data[i] = resp_pkt;
    }

    _waiting_resp_list.remove(resource->object()->resp_elem);
}

inline void Component_Manager::int_handler_print_type(unsigned int msg_type, NOC::Address &addr, NOC::Packet &pkt){
    switch (msg_type) {
    case MSG_TYPE_CALL:
        db<Component_Manager>(TRC) << "Component_Manager::int_handler(): Received(" << (void*)pkt.data[0] << "," << (void*)pkt.data[1] <<  ") CALL pkt from - " << addr << "\n"; break;
    case MSG_TYPE_CALL_DATA:
        db<Component_Manager>(TRC) << "Component_Manager::int_handler(): Received(" << (void*)pkt.data[0] << "," << (void*)pkt.data[1] <<  ") CALL_DATA pkt from - " << addr << "\n"; break;
    case MSG_TYPE_RESP:
        db<Component_Manager>(WRN) << "Component_Manager::int_handler(): Received(" << (void*)pkt.data[0] << "," << (void*)pkt.data[1] <<  ") RESP pkt from - " << addr << "\n"; break;
    case MSG_TYPE_RESP_DATA:
        db<Component_Manager>(TRC) << "Component_Manager::int_handler(): Received(" << (void*)pkt.data[0] << "," << (void*)pkt.data[1] <<  ") RESP_DATA pkt from - " << addr << "\n"; break;
    case MSG_TYPE_ERROR:
        db<Component_Manager>(ERR) << "Component_Manager::int_handler(): Received(" << (void*)pkt.data[0] << "," << (void*)pkt.data[1] <<  ") ERROR pkt from - " << addr << "\n"; break;
    default:
        db<Component_Manager>(WRN) << "Component_Manager::int_handler(): Received(" << (void*)pkt.data[0] << "," << (void*)pkt.data[1] <<  ") unknown pkt type from - " << addr << "\n";
        db<Component_Manager>(WRN) << "\tmsg.header.msg_type=" << Comp_Msg::get_msg_type(pkt) << "\n"
                << "\theader.type_id=" << Comp_Msg::get_type_id(pkt) << "\n"
                << "\tmsg.header.inst_id=" << Comp_Msg::get_instance_id(pkt) << "\n"
                << "\tmsg.payload.op_id=" << Comp_Msg::get_op_id(pkt) << "\n"
                << "\tmsg.payload.data=" << Comp_Msg::get_data(pkt) << "\n";
        break;

    }
}

inline void Component_Manager::int_handler_handle_call(NOC::Address &src_phy, NOC::Packet &pkt){
    SW_Resource *rsc = 0;
    bool found = false;
    unsigned int type_id = Comp_Msg::get_type_id(pkt);
    unsigned int instance_id = Comp_Msg::get_instance_id(pkt);
    for (SW_Resources::Iterator it = _sw_resources.begin(); it != _sw_resources.end(); it++) {
        rsc = it->object();
        if(rsc->type_id == type_id){
            if(rsc->instance_id == instance_id){
                found = true;
                break;
            }
        }
    }

    if(found){
        db<Component_Manager>(TRC) << "Component_Manager::int_handler(): Resource found, rsc=" << rsc << "\n";
        rsc->dispatcher(src_phy, pkt, rsc->dispatcher_obj);
    }
    else
        db<Component_Manager>(WRN) << "Component_Manager::int_handler(): Resource related to CALL or CALL_DATA msg not found\n";
}

inline void Component_Manager::int_handler_handle_resp(NOC::Address &src_phy, NOC::Packet &pkt){
    HW_Resource *rsc = 0;
    bool found = false;
    unsigned int type_id = Comp_Msg::get_type_id(pkt);
    unsigned int instance_id = Comp_Msg::get_instance_id(pkt);
    for (Waiting_Resp_List::Iterator it = _waiting_resp_list.begin(); it != _waiting_resp_list.end(); it++) {
        rsc = it->object();
        if(rsc->address == src_phy){
            if(rsc->type_id == type_id){
                if(rsc->instance_id == instance_id){
                    found = true;
                    break;
                }
            }
        }
    }

    if(found){
        db<Component_Manager>(TRC) << "Component_Manager::int_handler(): Resource found, rsc=" << rsc << "\n";
        rsc->resp_value = Comp_Msg::get_data(pkt);
        rsc->resp_flag = true;
    }
    else
        db<Component_Manager>(WRN) << "Component_Manager::int_handler(): Resource related to RESP_DATA msg not found\n";

}

void Component_Manager::int_handler(unsigned int interrupt){
    NOC &_noc = NOC::get_instance();
    while(_noc.receive_available()) {

    //_noc.lock();

    NOC::Address addr;
    _noc.receive_header(&addr);

    NOC::Packet pkt;
    _noc.receive(&pkt);

    unsigned int msg_type = Comp_Msg::get_msg_type(pkt);

    int_handler_print_type(msg_type, addr, pkt);

    switch (msg_type) {
        case MSG_TYPE_CALL:
        case MSG_TYPE_CALL_DATA:
            int_handler_handle_call(addr, pkt);
            break;
        case MSG_TYPE_RESP: break;
        case MSG_TYPE_RESP_DATA:
            int_handler_handle_resp(addr, pkt);
            break;
        case MSG_TYPE_ERROR: break;
        default: break;
    }

    //_noc.unlock();
    }
}

__END_SYS
