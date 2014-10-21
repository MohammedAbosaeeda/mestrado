// EPOS NOC Mediator Common Package

#ifndef __noc_h
#define __noc_h

#include <system/config.h>
#include <system/resource.h>
#include <utility/ostream.h>

__BEGIN_SYS

class NOC_Common
{
protected:
    class Address : public Implementation::Address{
    public:
        friend OStream & operator << (OStream & cout, const Address & a) {
            cout << "(" << a.router_x << "," << a.router_y << "," << (void*)a.local << ")";
            return cout;
        }
    };
    //typedef Implementation::Address Address;

    typedef struct{
        unsigned int local_addr;
        unsigned int router_x_addr;
        unsigned int router_y_addr;
        unsigned int net_x_size;
        unsigned int net_y_size;
        unsigned int data_width;
    } Info;

    NOC_Common():_info(){}

    Info _info;

public:
    Info const& info() const { return _info;}

};

__END_SYS

#ifdef __NOC_H
#include __NOC_H
#endif

#endif
