// EPOS EPOSSOC_Component_Controller Implementation

#include <mach/epossoc/component_controller.h>


__BEGIN_SYS

unsigned int EPOSSOC_Component_Controller::alloc_proxy(unsigned int phy_x,
                                                       unsigned int phy_y,
                                                       unsigned int phy_local,
                                                       unsigned int type_id,
                                                       unsigned int instance_id){
    db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_proxy("
                  << (void*) phy_x << ", "
                  << (void*) phy_y << ", "
                  << (void*) phy_local << ", "
                  << (void*) type_id << ", "
                  << (void*) instance_id << ")\n";
    while(!ctrl_cmd_idle());
    ctrl_cmd(CMD_ALLOC_PROXY);
    while(!ctrl_cmd_idle());
    unsigned int buff = ctrl_cmd_result();
    if(buff != CMD_RESULT_ERR){
        buff_proxy_phy_x(buff, phy_x);
        buff_proxy_phy_y(buff, phy_y);
        buff_proxy_phy_local(buff, phy_local);

        buff_type_id(buff, type_id);
        buff_instance_id(buff, instance_id);

        //check
        if(buff_proxy_phy_x(buff) != phy_x) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_proxy: ERR: phy_x check err \n";
            if(buff_proxy_phy_y(buff) != phy_y) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_proxy: ERR: phy_y check err \n";
            if(buff_proxy_phy_local(buff) != phy_local) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_proxy: ERR: phy_local check err \n";
            if(buff_type_id(buff) != type_id) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_proxy: ERR: type_id check err \n";
            if(buff_instance_id(buff) != instance_id) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_proxy: ERR: instance_id check err \n";
    }
    else{
        db<EPOSSOC_Component_Controller>(ERR) << "--ERR: couldn't allocate buffer\n";
    }
    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void*)buff << "\n";

    return buff;
}

unsigned int EPOSSOC_Component_Controller::alloc_agent(unsigned int type_id,
                                                       unsigned int instance_id,
                                                       unsigned int dispatcher_address,
                                                       unsigned int dispatcher_object_address){
    db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_agent("
                << (void*) type_id << ", "
                << (void*) instance_id << ", "
                << (void*) dispatcher_address << ", "
                << (void*) dispatcher_object_address << ")\n";
    while(!ctrl_cmd_idle());
    ctrl_cmd(CMD_ALLOC_AGENT);
    while(!ctrl_cmd_idle());
    unsigned int buff = ctrl_cmd_result();
    if(buff != CMD_RESULT_ERR){

        buff_type_id(buff, type_id);
        buff_instance_id(buff, instance_id);
        buff_agent_disp_addr(buff, dispatcher_address);
        buff_agent_disp_obj_addr(buff, dispatcher_object_address);

        //check
        if(buff_type_id(buff) != type_id) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_agent: ERR: type_id check err \n";
            if(buff_instance_id(buff) != instance_id) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_agent: ERR: instance_id check err \n";
            if(buff_agent_disp_addr(buff) != dispatcher_address) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_agent: -ERR: dispatcher_address check err \n";
            if(buff_agent_disp_obj_addr(buff) != dispatcher_object_address) db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::alloc_agent: ERR: dispatcher_object_address check err \n";
    }
    else{
        db<EPOSSOC_Component_Controller>(ERR) << "--ERR: couldn't allocate buffer\n";
    }
    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void*)buff << "\n";

    return buff;
}

void EPOSSOC_Component_Controller::send_return_data(unsigned int buff, unsigned int n_ret, unsigned int *data){
    db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::send_return_data("
                << (void*) buff << ", "
                << (void*) n_ret << ", "
                << "data(";
        for (unsigned int i = 0; i < n_ret; ++i)
            db<EPOSSOC_Component_Controller>(TRC) << (void*)data[i] << ", ";
        db<EPOSSOC_Component_Controller>(TRC) << "))\n";

    while(buff_tx(buff));
    buff_msg_type(buff, Implementation::MSG_TYPE_RESP_DATA);

    for (unsigned int i = 0; i < n_ret; ++i) {
        buff_data_tx(buff, data[i]);
        buff_tx(buff,true);
        while(buff_tx(buff));
    }
}

unsigned int EPOSSOC_Component_Controller::receive_call(unsigned int buff){
    db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::receive_call("
                  << (void*) buff << ")\n";
    unsigned int op_id = 0xFFFFFFFF;
    if(buff_rx(buff)){
        unsigned int msg_type = buff_msg_type(buff);
        if(msg_type == Implementation::MSG_TYPE_CALL){
            op_id = buff_data_rx(buff);
        }
        else{
            db<EPOSSOC_Component_Controller>(ERR) << "EPOSSOC_Component_Controller::receive_call: ERR- Received msg of type " << (void*)msg_type << " is not a call\n";
        }
        buff_rx(buff, false);
    }
    else{
        db<EPOSSOC_Component_Controller>(ERR) << "EPOSSOC_Component_Controller::receive_call: ERR- No received data on buffer " << (void*)buff << "\n";
    }
    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void*)op_id << "\n";
    return op_id;
}

unsigned int EPOSSOC_Component_Controller::receive_call_data(unsigned int buff){
    db<EPOSSOC_Component_Controller>(TRC) << "EPOSSOC_Component_Controller::receive_call_data("
                << (void*) buff << ")\n";
    unsigned int call_data = 0;
    if(buff_rx(buff)){
        unsigned int msg_type = buff_msg_type(buff);
        if(msg_type == Implementation::MSG_TYPE_CALL_DATA){
            call_data = buff_data_rx(buff);
        }
        else{
            db<EPOSSOC_Component_Controller>(ERR) << "EPOSSOC_Component_Controller::receive_call_data: ERR- Received msg of type " << (void*)msg_type << " is not call data\n";
        }
        buff_rx(buff, false);
    }
    else{
        db<EPOSSOC_Component_Controller>(ERR) << "EPOSSOC_Component_Controller::receive_call_data: ERR- No received data on buffer " << (void*)buff << "\n";
    }
    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void*)call_data << "\n";
    return call_data;
}


void EPOSSOC_Component_Controller::enable_agent_receive_int(IC::Interrupt_Handler h){
    IC::disable(IC::IRQ_COMP_CONTRL);
    IC::int_vector(IC::IRQ_COMP_CONTRL, h);
    IC::enable(IC::IRQ_COMP_CONTRL);
}
void EPOSSOC_Component_Controller::disable_agent_receive_int(){
    IC::disable(IC::IRQ_COMP_CONTRL);
}

__END_SYS

