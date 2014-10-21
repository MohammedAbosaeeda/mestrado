/*
 * adapter.h
 *
 *  Created on: Jun 29, 2012
 *      Author: tiago
 */

#ifndef ADAPTER_H_
#define ADAPTER_H_

#include "scenario.h"

__BEGIN_SYS

template<class T>
class Scenario_Adapter: public T, public SW_Scenario<T, void>{
protected:
    Scenario_Adapter(Channel_t &rx_ch, Channel_t &tx_ch, unsigned char _iid[Traits<T>::n_ids])
        :T(rx_ch, tx_ch, _iid),
         SW_Scenario<T, void>(rx_ch, tx_ch, _iid){}
};

__END_SYS

#endif /* ADAPTER_H_ */
