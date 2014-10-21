// OpenEPOS Energy Management Utility Declarations

#ifndef __energy_h
#define __energy_h

#include <system/config.h>
#include <traits.h>

__BEGIN_SYS

void * wrap_kcalloc(unsigned int n, unsigned int bytes);

template<
	typename T,
	int MODES,
	bool pm_enabled = Traits<T>::power_management,
	bool acc_enabled = Traits<T>::energy_accounting
>
class Energy_Manager;

template<typename T, int MODES>
class Energy_Manager<T, MODES, false, false>
{
public:
    typedef void (Energy_Function)(T *);

    Energy_Manager(char start_mode = 0) {}

    void set_handler(char mode, Energy_Function handler) {}

    void power(T * obj, int mode) {}
    const int power() { return 0; }
};

template<typename T, int MODES>
class Energy_Manager<T, MODES, true, false>
{
public:
    typedef void (Energy_Function)(T *);

    Energy_Manager(int start_mode = 0)
    : _op_mode(start_mode)
    {}

    void set_handler(int mode, Energy_Function * handler) {
        _enter_mode[mode] = handler;
    }

    void power(T * obj, int mode) {
        if(_op_mode == mode) return;
        _op_mode = mode;
        _enter_mode[mode](obj);
    }

    int power() {return _op_mode;}

private:
    int _op_mode;
    Energy_Function * _enter_mode[MODES];
};

__END_SYS

#endif
