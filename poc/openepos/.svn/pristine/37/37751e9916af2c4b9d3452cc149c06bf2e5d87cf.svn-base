// EPOS Implementation Design Utility Declarations

#ifndef __component_manager_h
#define __component_manager_h

#include <system/config.h>
#include <system/ctti.h>
#include <utility/list.h>
#include <system/types_sw.h>
#include <system/resource_table.h>
#include <component_controller.h>
#include <pcap.h>

__BEGIN_SYS

class Component_Manager{
public:
    class HW_Resource;

    typedef Simple_List<HW_Resource> HW_Resources;
    typedef HW_Resources::Element HW_Resource_Elem;

    typedef Implementation::Type_Id Type_Id;

    typedef unsigned int Domain;
    enum {
        HARDWARE,
        SOFTWARE
    };

    enum {
        MSG_BUFFER_SIZE = Traits<Component_Manager>::msg_buffer_size,
        RESOURCE_BUFFER_SIZE = Traits<Component_Manager>::resource_buffer_size
    };

    class HW_Resource{
    public:
        unsigned int buffer;
        Type_Id type_id;

        HW_Resource (unsigned int _buffer, Type_Id _type_id) :buffer(_buffer), type_id(_type_id){}

        friend OStream & operator << (OStream & cout, const HW_Resource & rsc) {
            //cout << "(BUFF=" << rsc.buffer << ")";
            return cout;
        }
    };

    typedef void (* SW_Dispatcher)(Component_Controller::agent_call_info&);

public:
    static Implementation::Channel_t dummy_channel;

    static HW_Resource_Elem* allocate(Type_Id id);
    static void deallocate(HW_Resource_Elem*);

    static void call(HW_Resource_Elem const* resource,
                     unsigned int op_id, unsigned int n_params, unsigned int n_return, unsigned int *data);

//private:
public://TODO for memory evaluation only
    static HW_Resources         _hw_resources;

    static void int_handler(unsigned int interrupt);

    static unsigned int rsp_flag;//TODO remove

public:
    static void init();
    static void init_ints();
    static inline void init_hw_resource(int type_id, unsigned int iid, unsigned int addr_x, unsigned int addr_y, unsigned int addr_local);
    static inline void init_sw_resource(int type_id, unsigned int iid, SW_Dispatcher disp, void* object);
    static int recfg(Domain domain);
};

__END_SYS

#endif
