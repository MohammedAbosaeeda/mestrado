/*
 * scenario.h
 *
 *  Created on: Jun 29, 2012
 *      Author: tiago
 */

#ifndef SCENARIO_H_
#define SCENARIO_H_

#include "static_alloc.h"
#include "dynamic_alloc.h"

__BEGIN_SYS

template<class T, class Allocator>
class SW_Scenario: public Allocator{
public:
    typedef typename Allocator::Idx_Type Idx_Type;

protected:
    SW_Scenario(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char _iid[Traits<T>::n_ids])
        :Allocator()
    {

    }

};

template<class T>
class SW_Scenario<T,void> {
protected:
    SW_Scenario(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char _iid[Traits<T>::n_ids])
    {

    }

};


__END_SYS


#endif /* SCENARIO_H_ */
