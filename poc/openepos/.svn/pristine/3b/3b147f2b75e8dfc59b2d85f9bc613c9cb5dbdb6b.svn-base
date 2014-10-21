/* 
 * File:   network.h
 * Author: sergio
 *
 * Created on May 29, 2014, 3:46 PM
 */



#ifndef NETWORK_H
#define	NETWORK_H

#include <radio.h>
#include "cartesian.h"

namespace network {
    class Address: public Radio_Common::Address {
    public:
        Address() {}
        Address(unsigned char a[2]) 
            : Radio_Common::Address(a[1],a[0]) {}
        Address(unsigned short a)
            : Radio_Common::Address(a & 0xff,
                                    (a>>8) & 0xff)
        {}
        Address(unsigned char a0, unsigned char a1)
            : Radio_Common::Address(a1, a0)
        {}
        void set(char a[2]){
            _address[0]=a[1];
            _address[1]=a[0];
        }
        void set(short a){
            _address[0]=a & 0xff;
            _address[1]=(a>>8) & 0xff;
        }
        void set(char a0,char a1){
            _address[0]=a1;
            _address[1]=a0;
        }
        unsigned char get(int index){
            return _address[index];
        }
        unsigned char* get(){
            return _address;
        }
    };
    
    Address my_address(0x00,0x00);
        
    void sendPacket(Packet p,cartesian::Region* r);
    
}



#endif	/* NETWORK_H */

