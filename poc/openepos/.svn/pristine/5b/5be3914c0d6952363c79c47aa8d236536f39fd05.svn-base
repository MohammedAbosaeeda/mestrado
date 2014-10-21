/*
 * scenario.h
 *
 *  Created on: Jun 28, 2012
 *      Author: tiago
 */

#ifndef SCENARIO_H_
#define SCENARIO_H_

#include "../system/types_hw.h"
#include "disparcher.h"
#include "static_alloc.h"

namespace System {

template<class T, class Dispatcher, class Allocator>
class HW_Scenario: public Dispatcher, public Allocator{
public:
    typedef typename Allocator::Idx_Type Idx_Type;

protected:
    HW_Scenario(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char _iid[Traits<T>::n_ids])
        :Dispatcher(rx_ch,tx_ch,_iid),
         Allocator()
    {

    }

};

template<class T, class Dispatcher>
class HW_Scenario<T,Dispatcher,void>: public Dispatcher{
protected:
    HW_Scenario(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char _iid[Traits<T>::n_ids])
        :Dispatcher(rx_ch,tx_ch,_iid)
    {

    }

};

};


#endif /* SCENARIO_H_ */
