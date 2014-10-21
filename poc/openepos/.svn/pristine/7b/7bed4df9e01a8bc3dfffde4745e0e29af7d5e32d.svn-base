/*
 * scenario_adapter.h
 *
 *  Created on: Jan 18, 2013
 *      Author: tiago
 */

#ifndef SCENARIO_ADAPTER_H_
#define SCENARIO_ADAPTER_H_

#include "../traits.h"
#include "meta.h"
#include "scenario.h"

namespace Implementation {

//Default declaration. Used when no enter/leave operations are needed
template<typename C>
class Scenario_Adapter :
    public C,
    public IF<Traits<C>::hardware,
              HW_Scenario<C, typename Traits<C>::Alloc_Obj_Type, typename Traits<C>::Alloc_Idx, Traits<C>::Alloc_Max>,
              SW_Scenario<C, typename Traits<C>::Alloc_Obj_Type, typename Traits<C>::Alloc_Idx, Traits<C>::Alloc_Max> >::Result
{
private:
    typedef typename IF<Traits<C>::hardware,
            HW_Scenario<C, typename Traits<C>::Alloc_Obj_Type, typename Traits<C>::Alloc_Idx, Traits<C>::Alloc_Max>,
            SW_Scenario<C, typename Traits<C>::Alloc_Obj_Type, typename Traits<C>::Alloc_Idx, Traits<C>::Alloc_Max> >::Result
        Scenario;

public:
    Scenario_Adapter(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid[Traits<C>::n_ids])
        :C(rx_ch,tx_ch,iid)//,
         //Scenario()
    { }

};

//Macro for creating a specialization using the default declaration
#define ADAPTER_BEGIN(name)\
template<>\
class Scenario_Adapter<name >:\
    public name,\
    public IF<Traits<name>::hardware,\
                HW_Scenario<name, Traits<name>::Alloc_Obj_Type, Traits<name>::Alloc_Idx, Traits<name>::Alloc_Max>,\
                SW_Scenario<name, Traits<name>::Alloc_Obj_Type, Traits<name>::Alloc_Idx, Traits<name>::Alloc_Max> >::Result\
{\
private:\
    typedef typename IF<Traits<C>::hardware,\
        HW_Scenario<C, typename Traits<C>::Alloc_Obj_Type, typename Traits<C>::Alloc_Idx, Traits<C>::Alloc_Max>,\
        SW_Scenario<C, typename Traits<C>::Alloc_Obj_Type, typename Traits<C>::Alloc_Idx, Traits<C>::Alloc_Max> >::Result\
    Scenario;\
public:\
    typedef Scenario::Link Link;\
    Scenario_Adapter(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char iid)\
        :name(rx_ch,tx_ch,iid)\
    {}\


#define ADAPTER_END };

};



#endif /* SCENARIO_ADAPTER_H_ */
