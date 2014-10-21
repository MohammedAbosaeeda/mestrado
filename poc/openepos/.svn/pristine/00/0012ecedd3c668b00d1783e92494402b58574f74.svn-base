// EPOS Unified Design Utility Declarations

#ifndef __component_manager_h
#define __component_manager_h

#include <system/config.h>
#include <system/ctti.h>
#include <noc.h>
#include <utility/list.h>
#include <system/types_sw.h>
#include <system/resource_table.h>

__BEGIN_SYS

class Component_Manager{
public:

    class HW_Resource;
    class SW_Resource;

    typedef Simple_List<HW_Resource> HW_Resources;
    typedef HW_Resources::Element HW_Resource_Elem;

    typedef Simple_List<SW_Resource> SW_Resources;
    typedef SW_Resources::Element SW_Resource_Elem;

    typedef Simple_List<HW_Resource> Waiting_Resp_List;
    typedef Waiting_Resp_List::Element Waiting_Resp_Elem;

    enum {
        MSG_BUFFER_SIZE = Traits<Component_Manager>::msg_buffer_size,
        RESOURCE_BUFFER_SIZE = Traits<Component_Manager>::resource_buffer_size
    };

    //FIXME channels removed to impleved performance. Now only one return value is supported
    //typedef Busy_Semaphore<RESOURCE_BUFFER_SIZE> Busy_Semaphore;
    //typedef Blocking_Channel<unsigned int, RESOURCE_BUFFER_SIZE> Blocking_Channel;


    class HW_Resource{
    public:
        //TODO handle possible race condition on objects of this type
        NOC::Address                                    address;
        Unified::Type_Id                                type_id;
        unsigned int                                    instance_id;
        Waiting_Resp_Elem                               *resp_elem;
        volatile unsigned int                           resp_value;
        volatile bool                                   resp_flag;

        HW_Resource (unsigned int router_x, unsigned int router_y, unsigned int local,
                     Unified::Type_Id _type_id, unsigned int _instance_id)
                :address(), type_id(_type_id), instance_id(_instance_id), resp_elem(0), resp_value(0), resp_flag(false){
            address.router_x = router_x;
            address.router_y = router_y;
            address.local = local;
        }

    public:
        /*friend OStream & operator << (OStream & cout, const HW_Resource & rsc) {
            cout << "(PHY=" << rsc.address << ",TID=" << rsc.type_id << ",IID=" << rsc.instance_id << ")";
            return cout;
        }*/
    };

    class SW_Resource{
    public:
        typedef void (* Dispatcher)(NOC::Address &src_phy, NOC::Packet &pkt,void*);
    public:
        Unified::Type_Id                                type_id;
        unsigned int                                    instance_id;
        Dispatcher                                      dispatcher;
        void*                                           dispatcher_obj;

        SW_Resource (Unified::Type_Id _type_id, unsigned int _instance_id, Dispatcher disp, void* object)
        :type_id(_type_id), instance_id(_instance_id), dispatcher(disp), dispatcher_obj(object){}

    public:
        friend OStream & operator << (OStream & cout, const SW_Resource & rsc) {
            cout << "(TID=" << rsc.type_id << ",IID=" << rsc.instance_id << ")";
            return cout;
        }
    };

public:
    static Channel_t dummy_channel;

    static HW_Resource_Elem* allocate(Unified::Type_Id id);
    static void deallocate(HW_Resource_Elem*);

    static void call(HW_Resource_Elem const* resource,
                          unsigned int op_id, unsigned int n_params, unsigned int n_return, unsigned int *data);

private:
    static HW_Resources         _hw_resources;
    static SW_Resources         _sw_resources;
    static Waiting_Resp_List    _waiting_resp_list;//TODO handle possible race condition on this list

    inline static void int_handler_print_type(unsigned int msg_type, NOC::Address &addr, NOC::Packet &pkt);
    inline static void int_handler_handle_call(NOC::Address &src_phy, NOC::Packet &pkt);
    inline static void int_handler_handle_resp(NOC::Address &src_phy, NOC::Packet &pkt);
    static void int_handler(unsigned int interrupt);

    template<class List>
    static inline void print_list(OStream &cout, List &l){
        cout << "[";
        for (typename List::Iterator it = l.begin(); it != l.end(); it++) {
            cout << *(it->object()) << ", ";
        }
        cout << "]";
    }

public:
    static void init();
    static inline void init_hw_resource(int type_id, unsigned int iid, unsigned int addr_x, unsigned int addr_y, unsigned int addr_local);
    static inline void init_sw_resource(int type_id, unsigned int iid, SW_Resource::Dispatcher disp, void* object);
};

__END_SYS


#endif
