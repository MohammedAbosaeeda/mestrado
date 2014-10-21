#ifndef HANDLE_EPOS_SOC_SW_H_
#define HANDLE_EPOS_SOC_SW_H_

#include <master_chronometer.h>
#include <component_manager.h>
#include <system/kmalloc.h>
#include <utility/spin.h>
#include "../../../unified/framework/handle.h"
#include "../../../unified/framework/proxy.h"
#include "../../../unified/framework/scenario_adapter.h"

using System::Master_Chronometer;
using System::db;
using System::ERR;
using System::Machine;
using System::Component_Manager;
using System::Spin;

namespace Implementation {

template<typename Component>
class Handle_Common<Component, Configurations::EPOS_SOC_Catapult, false> {
public:
    typedef unsigned int Domain;
    enum {
        HARDWARE,
        SOFTWARE
    };

    long ticks_calc_recfg_time;
    long ticks_acquire;
    long ticks_alloc_hw_res;
    long ticks_recfg;
    long ticks_get_state;
    long ticks_change_domain_hw;
    long ticks_set_state;
    long ticks_method;
    long ticks_release;

public:
    Handle_Common(Channel_t &rx_ch, Channel_t &tx_ch, 
            unsigned char iid[Traits<Component>::n_ids]) {
        _comp = new(System::kmalloc(sizeof(Scenario_Adapter<Component>))) 
            Scenario_Adapter<Component>(rx_ch, tx_ch, iid);

        _proxy = new(System::kmalloc(sizeof(Proxy<Component>))) 
            Proxy<Component>(rx_ch, tx_ch, iid);
        
        _domain = HARDWARE;
    }

    ~Handle_Common(){ }

protected:
    template<unsigned int OP, typename RET>  // no arguments, return
    RET call_r() {
        RET result;
        long recfg_time;

        Master_Chronometer::instance()->reset();
        Master_Chronometer::instance()->start();
        recfg_time = calc_recfg_time();
        Master_Chronometer::instance()->stop();
        ticks_calc_recfg_time = Master_Chronometer::instance()->ticks();

        if(recfg_time + Traits<Component>::cipher_hw_cyles <
                Traits<Component>::cipher_sw_cyles && _domain != HARDWARE) {
            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            _spin.acquire();
            Master_Chronometer::instance()->stop();
            ticks_acquire = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            //alloc_hw_res();
            Master_Chronometer::instance()->stop();
            ticks_alloc_hw_res = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            Component_Manager::recfg(HARDWARE);
            Master_Chronometer::instance()->stop();
            ticks_recfg = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            get_state();
            ticks_get_state = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            _domain = HARDWARE;
            Master_Chronometer::instance()->stop();
            ticks_change_domain_hw = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            set_state();
            ticks_set_state = Master_Chronometer::instance()->ticks();
        }

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            result = _proxy->call_r<OP, RET>();
            ticks_method = Master_Chronometer::instance()->ticks();

        if(recfg_time + Traits<Component>::cipher_hw_cyles <
                Traits<Component>::cipher_sw_cyles && _domain != HARDWARE) {
            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            _spin.release();
            ticks_release = Master_Chronometer::instance()->ticks();
        }

        return result;
        //} else {
            //Master_Chronometer::instance()->reset();
            //Master_Chronometer::instance()->start();
            //result = _comp->do_dtmf_detection();
            //ticks_method = Master_Chronometer::instance()->ticks();
            //return result;
        //}
    }

    template<unsigned int OP, typename RET, typename ARG0>  // one argument, return
    RET call_r(ARG0 &arg0) {
        RET result;
        long recfg_time;

        Master_Chronometer::instance()->reset();
        Master_Chronometer::instance()->start();
        recfg_time = calc_recfg_time();
        Master_Chronometer::instance()->stop();
        ticks_calc_recfg_time = Master_Chronometer::instance()->ticks();

        if(recfg_time + Traits<Component>::cipher_hw_cyles <
                Traits<Component>::cipher_sw_cyles && _domain != HARDWARE) {
            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            _spin.acquire();
            Master_Chronometer::instance()->stop();
            ticks_acquire = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            //alloc_hw_res();
            Master_Chronometer::instance()->stop();
            ticks_alloc_hw_res = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            Component_Manager::recfg(HARDWARE);
            Master_Chronometer::instance()->stop();
            ticks_recfg = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            get_state();
            ticks_get_state = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            _domain = HARDWARE;
            Master_Chronometer::instance()->stop();
            ticks_change_domain_hw = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            set_state();
            ticks_set_state = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            result = _proxy->call_r<OP, RET, ARG0>(arg0);
            ticks_method = Master_Chronometer::instance()->ticks();

            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            _spin.release();
            ticks_release = Master_Chronometer::instance()->ticks();

            return result;
        } else {
            Master_Chronometer::instance()->reset();
            Master_Chronometer::instance()->start();
            result = _comp->dummy_cipher(arg0);
            ticks_method = Master_Chronometer::instance()->ticks();
            return result;
        }
    }

    template<unsigned int OP, typename RET, typename ARG0, typename ARG1>  // two arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1) {
        if(_domain == SOFTWARE) {
            //return (_comp->mac(arg0, arg1));
        } else if (_domain == HARDWARE) {
            return (_proxy->call_r<OP, unsigned int>(arg0, arg1));
        }
    }

    template<unsigned int OP, typename ARG0>  // one argument, no return
    void call(ARG0 &arg0){
        if(_domain == SOFTWARE) {
            _comp->add_key_2(arg0);
        } else if (_domain == HARDWARE) {
            _proxy->call<OP, ARG0>(arg0);
        }
    }

protected:
    Scenario_Adapter<Component> * _comp;
    Proxy<Component> * _proxy;
    Domain _domain;

private:
    bool calc_feasability() {
        if (Traits<Component>::cipher_hw_cyles + Traits<Component>::recfg_cyles
                < Traits<Component>::cipher_sw_cyles)
            return true;
        else
            return false;
    }

    long calc_recfg_time() {
        //return Traits<Component>::bitstream_size/Traits<Component_Manager>::bandwidth;
        return 0;
    }

    bool get_state() {
        return true;
    }

    bool set_state() {
        return true;
    }

private:
    Spin _spin;

};

};

#endif /* HANDLE_EPOS_SOC_SW_H_ */
