// EPOS SPM_Energy Interface
//
// Author: Arliones
// Documentation: $EPOS/doc/energy			Date: 29 Nov 2004

#ifndef __energy_h
#define __energy_h

#include <system/config.h>

__BEGIN_SYS
__BEGIN_INT

class SPM_Common
{
protected:
    SPM_Common() {}

public:
    enum Level {
        FULL = 0,
	ENERGY_AWARE = 1,
	STAND_BY = 2,
	OFF = 3
    };

    template<class Comp>
    class Component_Handler {
    public:
        Component_Handler();
        ~Component_Handler();

        Level energy_level();
        void energy_level(Level l);

        int available_levels();
    };
};

class SPM: public SPM_Common
{
public:
    SPM() __DEF;
    ~SPM() __DEF;

    void register(Compnent_Handler comp);
    void unregister(Component_Handler comp);    
};

class Atomic_SPM: public SPM_Common
{
public:
    Atomic_SPM() __DEF;
    ~Atomic_SPM() __DEF;

    void register(Compnent_Handler comp, Function action);
    void unregister(Component_Handler comp);
};

class Timed_SPM: public SPM_Common
{
public:
    Timed_SPM() __DEF;
    ~Timed_SPM() __DEF;

    void register(Compnent_Handler comp, Function action);
    void unregister(Component_Handler comp);

    unsigned int interval();
    void interval(unsigned int i);
};

class ARM_MMU: public MMU_Common
{
public:
    ARM_MMU() __DEF;
    ~ARM_MMU() __DEF;

    void flush_tlb();
    void flush_tlb(Log_Addr addr);

    static Phy_Addr alloc(int bytes);
    static void free(Phy_Addr addr, int bytes);

    static Phy_Addr physical(Log_Addr addr);
};

class LANai_MMU: public MMU_Common
{
public:
    LANai_MMU() __DEF;
    ~LANai_MMU() __DEF;

    void flush_tlb();
    void flush_tlb(Log_Addr addr);

    static Phy_Addr alloc(int bytes);
    static void free(Phy_Addr addr, int bytes);

    static Phy_Addr physical(Log_Addr addr);
};

class H8_MMU: public MMU_Common
{
public:
    H8_MMU() __DEF;
    ~H8_MMU() __DEF;

    void flush_tlb();
    void flush_tlb(Log_Addr addr);

    static Phy_Addr alloc(int bytes);
    static void free(Phy_Addr addr, int bytes);

    static Phy_Addr physical(Log_Addr addr);
};

class AVR8_MMU: public MMU_Common
{
public:
    AVR8_MMU() __DEF;
    ~AVR8_MMU() __DEF;

    void flush_tlb();
    void flush_tlb(Log_Addr addr);

    static Phy_Addr alloc(int bytes);
    static void free(Phy_Addr addr, int bytes);

    static Phy_Addr physical(Log_Addr addr);
};

__END_INT
__END_SYS

#ifdef __IA32_MMU_H
#include __IA32_MMU_H
#endif

#ifdef __PPC32_MMU_H
#include __PPC32_MMU_H
#endif

#ifdef __ARM_MMU_H
#include __ARM_MMU_H
#endif

#ifdef __LANAI_MMU_H
#include __LANAI_MMU_H
#endif

#ifdef __H8_MMU_H
#include __H8_MMU_H
#endif

#ifdef __AVR8_MMU_H
#include __AVR8_MMU_H
#endif

#endif
