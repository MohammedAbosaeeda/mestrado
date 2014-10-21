/*
 * proxy.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef PROXY_H_
#define PROXY_H_

#include "../traits.h"
#include "serializer.h"

namespace Implementation {

template<class Component>
class Proxy;

template<class Component,
         class Platform,
         bool hardware>
class Proxy_Common;

//Helpers
#define PROXY_BEGIN(name)\
template<>\
class Proxy<name> :\
        public Proxy_Common<name, Traits<Sys>::Platform, Traits<Sys>::hardware_domain> {\
public:\
    typedef Proxy_Common<name, Traits<Sys>::Platform, Traits<Sys>::hardware_domain> Base;\
    Proxy(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<name>::n_ids])\
        :Base(rx_ch,tx_ch,iid){\
     }
#define PROXY_END };

};

#ifdef HIGH_LEVEL_SYNTHESIS
#include "../../hw/framework/catapult/proxy.h"
#else
//EPOS_SoC software proxy
#include "../../sw/include/framework/proxy.h"
#endif



#endif /* PROXY_H_ */
