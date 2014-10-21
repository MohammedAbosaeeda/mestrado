// OpenEPOS EMote2ARM_GPIO_Pin Mediator Declarations

#ifndef __emote2arm_gpio_pin_h
#define __emote2arm_gpio_pin_h

#include <gpio_pin.h>
#include <machine.h>

__BEGIN_SYS

class EMote2ARM_GPIO_Pin: public GPIO_Pin_Common {
private:
    EMote2ARM_GPIO_Pin();

    typedef Machine::IO IO;

public:
    enum GPIO_Functions {
        FUNC_DFLT = 0,
        FUNC_ALT1 = 1,
        FUNC_ALT2 = 2,
        FUNC_ALT3 = 3
    };
    EMote2ARM_GPIO_Pin(int pin, GPIO_Functions func = FUNC_DFLT);
    ~EMote2ARM_GPIO_Pin() {}

    void put(bool value) { value ? set(_pin) : clear(_pin); }
    void set() { set(_pin); }
    void clear() { clear(_pin); }
    bool get() { return get(_pin); }

    void output() { output(_pin); }
    void input() { input(_pin); }
    void function(GPIO_Functions func) { function(_pin, func); }

private:
    static void input(int pin);
    static void output(int pin);
    static void function(int pin, GPIO_Functions func);
    static void set(int pin);
    static void clear(int pin);
    static bool get(int pin);

    int _pin;
};

__END_SYS

#endif
