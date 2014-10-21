#ifndef __resource_unified_h
#define __resource_unified_h

namespace Unified {

class Address{
public:

    enum{
        LOCAL_NN = 0,
        LOCAL_NE = 1,
        LOCAL_EE = 2,
        LOCAL_SE = 3,
        LOCAL_SS = 4,
        LOCAL_SW = 5,
        LOCAL_WW = 6,
        LOCAL_NW = 7,
    };


    unsigned int router_x;
    unsigned int router_y;
    unsigned int local;

    Address() :router_x(0), router_y(0), local(0){};
    Address(Address &o) :router_x(o.router_x), router_y(o.router_y), local(o.local){};
    Address(unsigned int _router_x, unsigned int _router_y, unsigned int _local) :router_x(_router_x), router_y(_router_y), local(_local){};

    bool operator==(const Address &a) const {
        return (router_x == a.router_x) && (router_y == a.router_y) && (local == a.local);
    }

    bool operator!=(const Address &a) const {
        return (router_x != a.router_x) || (router_y != a.router_y) || (local != a.local);
    }

    static unsigned int addr_to_id(Address const* addr, unsigned int net_x_size){
        unsigned int id = addr->local;
        id |= addr->router_x << 3;
        id |= addr->router_y << (3+net_x_size);

        return id;
    }

    static void id_to_addr(unsigned int id, Address * addr, unsigned int net_x_size){
        unsigned int tmp = 2;
        for (unsigned int i = 1; i < net_x_size; ++i) tmp <<= 1;
        tmp -= 1;

        addr->router_y = id >> (3+net_x_size);
        addr->router_x = (id >> 3) & tmp;
        addr->local = id & 0x7;
    }

};

}




#endif /* __resource_h */
