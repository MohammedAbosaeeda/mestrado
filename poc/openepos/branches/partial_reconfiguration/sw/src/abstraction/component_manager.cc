#include <component_manager.h>
#include <master_chronometer.h>

__BEGIN_SYS

Component_Manager::HW_Resources Component_Manager::_hw_resources;

unsigned int Component_Manager::rsp_flag;

Component_Manager::HW_Resource_Elem* Component_Manager::allocate(Component_Manager::Type_Id id) {
    Component_Manager::HW_Resource_Elem* resource = 0;
    for (HW_Resources::Iterator it = _hw_resources.begin(); it != _hw_resources.end(); it++) {
        resource = it;
        if(resource->object()->type_id == id)
           break;
    }

    if(resource){
        _hw_resources.remove(resource);

        db<Component_Manager>(TRC) << "Component_Manager::allocate(" << id <<
            "): Resource allocated\n";
    }
    else{
        db<Component_Manager>(WRN) << "Component_Manager::allocate(" << id <<
            "): No resource available\n";
    }

    return resource;
}

void Component_Manager::deallocate(Component_Manager::HW_Resource_Elem* resource) {
    if(resource)
        _hw_resources.insert(resource);
}

void Component_Manager::call(Component_Manager::HW_Resource_Elem const* resource, 
        unsigned int op_id, unsigned int n_params, unsigned int n_return,
        unsigned int *data) {
    db<Component_Manager>(TRC) << "Component_Manager::call(" << 
        resource->object() << ", " << op_id << ", " << n_params << ", " <<
        n_return << ", " << (void*)data << "(";

    for (unsigned int i = 0; i < n_params; ++i) 
        db<Component_Manager>(TRC) << (void*)data[i] << ",";

    db<Component_Manager>(TRC) << "))\n";

    unsigned int buffer = resource->object()->buffer;

    Component_Controller::send_call(buffer, op_id);
    Component_Controller::send_call_data(buffer, n_params, data);
    db<Component_Manager>(TRC) << "Component_Manager::call(): Call sent\n";

    db<Component_Manager>(TRC) << "Component_Manager::call(): Receiving return data\n";
    Component_Controller::receive_return_data(buffer, n_return, data);
}

void Component_Manager::int_handler(unsigned int interrupt) {
    //Component_Controller::disable_agent_receive_int();
    db<Component_Manager>(TRC) << "Component_Manager::int_handler(): begin\n";

    Component_Controller::agent_call_info call_info;

    while(Component_Controller::agent_has_call(call_info)) {
        SW_Dispatcher dispatcher = (SW_Dispatcher)call_info.dispatcher_address;

        dispatcher(call_info);
    }
    //Component_Controller::enable_agent_receive_int(&int_handler);
    db<Component_Manager>(TRC) << "Component_Manager::int_handler(): end\n";
}

int Component_Manager::recfg(Component_Manager::Domain domain) {
    PCAP pcap;
    volatile unsigned int * gpio = (volatile unsigned int *)(Traits<Machine>::LEDS_ADDRESS);
    const unsigned int BITFILE_ADDRESS = 0x00200000;
    const unsigned int BITFILE_LENGTH = 0x7404;

    // We need a data structure to indicate that the NoC nodes are occupied,
    // There should be some sort of lock before reconfiguration

    if(domain == HARDWARE) {
        // Here we should allocate the NoC nodes

        *gpio = (0<<31) | (0<<7);
        pcap.transfer(BITFILE_ADDRESS, BITFILE_LENGTH>>2);
        *gpio = (1<<31) | (1<<7);

        //resource->object()->domain = Component_Manager::HARDWARE;
        return (HARDWARE);
    } else if (domain == SOFTWARE) {
        // Here we should release the NoC nodes

        //resource->object()->domain = Component_Manager::SOFTWARE;
        return (SOFTWARE);
    } else
        db<Component_Manager>(WRN) << "Component_Manager::recfg(): invalid domain\n";
}

__END_SYS
