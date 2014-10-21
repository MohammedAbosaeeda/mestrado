// EPOS EPOSSOC Component Controller Implementation

#include <mach/epossoc/component_controller.h>

__BEGIN_SYS

// Allocate a proxy buffer in the Component_Controller. Returns the number of
// the allocated buffer.
unsigned int EPOSSOC_Component_Controller::alloc_proxy(unsigned int phy_x,
        unsigned int phy_y, unsigned int phy_local, unsigned int type_id,
        unsigned int instance_id) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::alloc_proxy(" << (void *)phy_x << ", "
        << (void *)phy_y << ", " << (void *)phy_local << ", " << (void *)type_id
        << ", " << (void *)instance_id << ")" << endl;

    while(!ctrl_cmd_idle());
    ctrl_cmd(CMD_ALLOC_PROXY);

    while(!ctrl_cmd_idle());
    unsigned int buff_num = ctrl_cmd_result();

    if(buff_num != CMD_RESULT_ERR) {
        // Set buffer registers
        buff_proxy_phy_x(buff_num, phy_x);
        buff_proxy_phy_y(buff_num, phy_y);
        buff_proxy_phy_local(buff_num, phy_local);
        buff_type_id(buff_num, type_id);
        buff_instance_id(buff_num, instance_id);
    } else
        db<EPOSSOC_Component_Controller>(WRN) << "Couldn't allocate buffer" << endl;

    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void *)buff_num << endl;

    return buff_num;
}

// Allocate an agent buffer in the Component_Controller. Returns the number of
// the allocated buffer.
unsigned int EPOSSOC_Component_Controller::alloc_agent(unsigned int type_id,
        unsigned int instance_id, unsigned int dispatcher_address,
        unsigned int dispatcher_object_address) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::alloc_agent(" << (void *)type_id << ", "
        << (void *)instance_id << ", " << (void *)dispatcher_address << ", "
        << (void *)dispatcher_object_address << ")" << endl;

    while(!ctrl_cmd_idle());
    ctrl_cmd(CMD_ALLOC_AGENT);

    while(!ctrl_cmd_idle());
    unsigned int buff_num = ctrl_cmd_result();

    if(buff_num != CMD_RESULT_ERR) {
        // Set buffer registers
        buff_type_id(buff_num, type_id);
        buff_instance_id(buff_num, instance_id);
        buff_agent_disp_addr(buff_num, dispatcher_address);
        buff_agent_disp_obj_addr(buff_num, dispatcher_object_address);
    } else
        db<EPOSSOC_Component_Controller>(WRN) << "Couldn't allocate buffer" << endl;

    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void *)buff_num << endl;

    return buff_num;
}

// Send return data to component associated with buffer buff_num
void EPOSSOC_Component_Controller::send_return_data(unsigned int buff_num,
        unsigned int n_ret, unsigned int * data) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::send_return_data(" << (void *)buff_num << ", "
        << (void *)n_ret << ", " << "data(";

    for (unsigned int i = 0; i < n_ret; ++i)
        db<EPOSSOC_Component_Controller>(TRC) << (void *)data[i] << ", ";

    db<EPOSSOC_Component_Controller>(TRC) << "))" << endl;

    while(buff_tx(buff_num));
    buff_msg_type(buff_num, Implementation::MSG_TYPE_RESP_DATA);

    for (unsigned int i = 0; i < n_ret; ++i) {
        buff_data_tx(buff_num, data[i]);
        buff_tx(buff_num,true);
        while(buff_tx(buff_num));
    }
}

// Return the operation id of the received call
unsigned int EPOSSOC_Component_Controller::receive_call(unsigned int buff_num) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::receive_call(" << (void *)buff_num << ")"
        << endl;

    unsigned int op_id = 0xFFFFFFFF;

    if(buff_rx(buff_num)) {
        unsigned int msg_type = buff_msg_type(buff_num);

        if(msg_type == Implementation::MSG_TYPE_CALL)
            op_id = buff_data_rx(buff_num);
        else
            db<EPOSSOC_Component_Controller>(WRN)
                << "Received msg of type " << (void *)msg_type
                << " is not a call" << endl;

        buff_rx(buff_num, false);
    } else
        db<EPOSSOC_Component_Controller>(WRN)
            << "No received data on buffer " << (void *)buff_num << endl;

    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void *)op_id << endl;

    return op_id;
}

// Return the received data stored in the buffer buf_num
unsigned int EPOSSOC_Component_Controller::receive_call_data(unsigned int buff_num) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::receive_call_data(" << (void *)buff_num << ")"
        << endl;

    unsigned int call_data = 0;

    if(buff_rx(buff_num)) {
        unsigned int msg_type = buff_msg_type(buff_num);

        if(msg_type == Implementation::MSG_TYPE_CALL_DATA)
            call_data = buff_data_rx(buff_num);
        else
            db<EPOSSOC_Component_Controller>(WRN)
                << "Received msg of type " << (void *)msg_type
                << " is not call data" << endl;

        buff_rx(buff_num, false);
    } else
        db<EPOSSOC_Component_Controller>(WRN)
            << "No received data on buffer " << (void *)buff_num << endl;

    db<EPOSSOC_Component_Controller>(TRC) << "= " << (void *)call_data << endl;

    return call_data;
}

void EPOSSOC_Component_Controller::send_call (unsigned int buff_num,
        unsigned int op_id) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::send_call(" << (void *) buff_num << ", "
        << (void *) op_id << ")" << endl;

    while(buff_tx(buff_num));

    buff_msg_type(buff_num, Implementation::MSG_TYPE_CALL);
    buff_data_tx(buff_num, op_id);

    buff_tx(buff_num, true);
}

void EPOSSOC_Component_Controller::send_call_data(unsigned int buff_num,
        unsigned int n_args, unsigned int * data) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::send_call_data(" << (void *) buff_num
        << ", " << (void *) n_args << ", " << "data(";

    for (unsigned int i = 0; i < n_args; ++i)
        db<EPOSSOC_Component_Controller>(TRC) << (void *)data[i] << ", ";

    db<EPOSSOC_Component_Controller>(TRC) << "))" << endl;

    while(buff_tx(buff_num));
    buff_msg_type(buff_num, Implementation::MSG_TYPE_CALL_DATA);

    for (unsigned int i = 0; i < n_args; ++i) {
        buff_data_tx(buff_num, data[i]);
        buff_tx(buff_num, true);
        while(buff_tx(buff_num));
    }
}

void EPOSSOC_Component_Controller::receive_return_data(unsigned int buff_num,
        unsigned int n_ret, unsigned int * data) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::receive_return_data(" << (void *) buff_num
        << ", " << (void *) n_ret << ")" << endl;

    for (unsigned int i = 0; i < n_ret; ++i) {
        while(!buff_rx(buff_num));

        unsigned int msg_type = buff_msg_type(buff_num);

        if(msg_type == Implementation::MSG_TYPE_RESP_DATA)
            data[i] = buff_data_rx(buff_num);
        else
            db<EPOSSOC_Component_Controller>(WRN)
                << "Received msg of type " << (void *)msg_type
                << " is not return data" << endl;

        buff_rx(buff_num, false);
    }

    db<EPOSSOC_Component_Controller>(TRC) << "=data(";

    for (unsigned int i = 0; i < n_ret; ++i)
        db<EPOSSOC_Component_Controller>(TRC) << (void*)data[i] << ", ";

    db<EPOSSOC_Component_Controller>(TRC) << ")" << endl;
}

bool EPOSSOC_Component_Controller::agent_has_call(agent_call_info &info) {
    db<EPOSSOC_Component_Controller>(TRC)
        << "Component_Controller::agent_has_call()" << endl;

    if(ctrl_status_agent_int()) {
        unsigned int buff_num = ctrl_status_agent_buffer();

        info.buffer = buff_num;
        info.dispatcher_address = buff_agent_disp_addr(buff_num);
        info.object_address = buff_agent_disp_obj_addr(buff_num);

        return true;
    } else
        return false;
}

void EPOSSOC_Component_Controller::enable_agent_receive_int(IC::Interrupt_Handler h) {
    IC::disable(IC::IRQ_COMP_CONTRL);
    IC::int_vector(IC::IRQ_COMP_CONTRL, h);
    IC::enable(IC::IRQ_COMP_CONTRL);
}

void EPOSSOC_Component_Controller::disable_agent_receive_int() {
    IC::disable(IC::IRQ_COMP_CONTRL);
}

__END_SYS
