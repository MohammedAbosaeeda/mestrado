/*
 * stub.h
 *
 *  Created on: Jun 27, 2012
 *      Author: tiago
 */

#ifndef STUB_H_
#define STUB_H_

#include <system/types_sw.h>
#include <component_manager.h>
#include <system/ctti.h>

__BEGIN_SYS


template<class T>
class Stub {
protected:
    Stub() :_resource(0) {
        _resource = Component_Manager::allocate(Unified::Type2Id<T>::ID);
        if(!_resource){
            db<T>(ERR) << "Cannot allocate resource. Type ID: " << (unsigned int)Unified::Type2Id<T>::ID << "\n";
            Machine::panic();
        }
    }
    ~Stub(){
        Component_Manager::deallocate(_resource);
    }

private:
    Component_Manager::HW_Resource_Elem *_resource;

protected:
    template<unsigned int OP, class RET>  // no arguments, return
    RET call_r(){
        unsigned int data[1];
        Component_Manager::call(_resource, OP, 0, 1, data);
        return (RET)data[0];
    }

    template<unsigned int OP>  // no arguments, no return
    void call(){
        Component_Manager::call(_resource, OP, 0, 0, 0);
    }

    template<unsigned int OP, class RET, class ARG0>  // one argument, return
    RET call_r(ARG0 &arg0){
        unsigned int data[1] = {(unsigned int)arg0};
        Component_Manager::call(_resource, OP, 1, 1, data);
        return (RET)data[0];
    }

    template<unsigned int OP, class RET, class ARG0, class ARG1>  // two arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1){
        unsigned int data[2] = {(unsigned int)arg0, (unsigned int)arg1};
        Component_Manager::call(_resource, OP, 2, 1, data);
        return (RET)data[0];
    }

    template<unsigned int OP, class RET, class ARG0, class ARG1, class ARG2, class ARG3>  // 4 arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1, ARG2 &arg2, ARG3 &arg3){
        unsigned int data[4] = {(unsigned int)arg0, (unsigned int)arg1, (unsigned int)arg2, (unsigned int)arg3};
        Component_Manager::call(_resource, OP, 4, 1, data);
        return (RET)data[0];
    }

    template<unsigned int OP, class RET, class ARG0, class ARG1, class ARG2, class ARG3, class ARG4, class ARG5, class ARG6, class ARG7>  // 8 arguments, return
    RET call_r(ARG0 &arg0, ARG1 &arg1, ARG2 &arg2, ARG3 &arg3, ARG4 &arg4, ARG5 &arg5, ARG6 &arg6, ARG7 &arg7){
        unsigned int data[8] = {(unsigned int)arg0, (unsigned int)arg1, (unsigned int)arg2, (unsigned int)arg3, (unsigned int)arg4, (unsigned int)arg5, (unsigned int)arg6, (unsigned int)arg7};
        Component_Manager::call(_resource, OP, 8, 1, data);
        return (RET)data[0];
    }

    template<unsigned int OP, class ARG0>  // one argument, no return
    void call(ARG0 &arg0){
        unsigned int data[1] = {(unsigned int)arg0};\
        Component_Manager::call(_resource, OP, 1, 0, data);
    }

    template<unsigned int OP, class ARG0, class ARG1>  // two arguments, no return
    void call(ARG0 &arg0, ARG1 &arg1){
        unsigned int data[2] = {(unsigned int)arg0, (unsigned int)arg1};
        Component_Manager::call(_resource, OP, 2, 0, data);
    }
};


#define PROXY_BEGIN(name)\
class PROXY(name) : public Stub<Unified::name> {\
public:\
    typedef Stub<Unified::name> Base;\
    typedef Scenario_Adapter<Unified::name> Component;\
\
    PROXY(name)() :Base() {}\

#define PROXY_END };



__END_SYS



#endif /* STUB_H_ */
