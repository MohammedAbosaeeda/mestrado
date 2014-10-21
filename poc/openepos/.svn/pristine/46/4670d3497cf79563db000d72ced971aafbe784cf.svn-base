// EPOS Internal Type Management System

typedef __SIZE_TYPE__ size_t;

#ifndef __types_h
#define __types_h

inline void * operator new(size_t s, void * a) { return a; }
inline void * operator new[](size_t s, void * a) { return a; }

#include "../../../components/include/system/types.h"

__BEGIN_SYS

// Dummy class for incomplete stuff
template<int>
class Dummy;

// Utilities
class Debug;
class Lists;
class Spin;
class Heap;

// System parts
class Boot;
class Setup;
class Init;
class System;

// Hardware Mediators - CPUs
class IA32;
class AVR8;
class PPC32;
class MIPS32;
class ARM7;

/*
namespace Cpu {
    template<bool, bool>
    class AVR8_CPU;
};*/

// Hardware Mediators - Time-Stamp Counters
class IA32_TSC;
class AVR8_TSC;
class PPC32_TSC;
class MIPS32_TSC;
class ARM7_TSC;

// Hardware Mediators - Memory Management Units
class IA32_MMU;
class AVR8_MMU;
class PPC32_MMU;
class MIPS32_MMU;
class ARM7_MMU;

// Hardware Mediators - Machines
class PC;
class ATMega16;
class ATMega128;
class ATMega1281;
class AT90CAN128;
class ML310;
class PLASMA;
class AXI4LITE;
class MC13224V;
class IntegratorCP;

// Hardware Mediators - Busses
class PC_PCI;
class ML310_PCI;
class AXI4LITE_NOC;

// Hardware Mediators - Interrupt Controllers
class PC_IC;
class ATMega16_IC;
class ATMega128_IC;
class ATMega1281_IC;
class AT90CAN128_IC;
class ML310_IC;
class PLASMA_IC;
class AXI4LITE_IC;
class MC13224V_IC;
class IntegratorCP_IC;

// Hardware Mediators - Timers
class PC_Timer;
class ATMega16_Timer_1;
class ATMega16_Timer_2;
class ATMega16_Timer_3;
class ATMega128_Timer_1;
class ATMega128_Timer_2;
class ATMega128_Timer_3;
class ATMega1281_Timer_1;
class ATMega1281_Timer_2;
class ATMega1281_Timer_3;
class AT90CAN128_Timer_1;
class AT90CAN128_Timer_2;
class AT90CAN128_Timer_3;
class ML310_Timer;
class PLASMA_Timer;
class AXI4LITE_Timer;
class MC13224V_Timer_0;
class MC13224V_Timer_1;
class MC13224V_Timer_2;
class MC13224V_Timer_3;
class IntegratorCP_Timer;

// Hardware Mediators - CAN
class AT90CAN128_CAN;

// Hardware Mediators - Real Time Clocks
class PC_RTC;
class ATMega16_RTC;
class ATMega128_RTC;
class ATMega1281_RTC;
class AT90CAN128_RTC;
class ML310_RTC;
class PLASMA_RTC;
class AXI4LITE_RTC;
class MC13224V_RTC;
class IntegratorCP_RTC;

// Hardware Mediators - EEPROMs
class PC_EEPROM;
class ATMega16_EEPROM;
class ATMega128_EEPROM;
class ATMega1281_EEPROM;

// Hardware Mediators - FLASHs
class ATMega128_Flash;
class ATMega1281_Flash;
class MC13224V_Flash;
class IntegratorCP_Flash;

// Hardware Mediators - UARTs
class PC_UART;
class ATMega16_UART;
class ATMega128_UART;
class ATMega1281_UART;
class AT90CAN128_UART;
class ML310_UART;
class PLASMA_UART;
class AXI4LITE_UART;
class MC13224V_UART;
class IntegratorCP_UART;

// Hardware Mediators - USARTs
class ATMega1281_USART;

// Hardware Mediators - SPIs
class PC_SPI;
class ATMega16_SPI;
class ATMega128_SPI;
class ATMega1281_SPI;

// Hardware Mediators - Displays
class Serial_Display;
class PC_Display;
class ATMega16_Display;
class ATMega128_Display;
class ATMega1281_Display;
class AT90CAN128_Display;
class MC13224V_Display;

// Hardware Mediators - NICs
class PC_Ethernet;
class PCNet32;
class C905;
class E100;
class ATMega128_NIC;
class AT90CAN128_NIC;
class ATMega1281_NIC;
class ATMega128_Radio;
class ATMega1281_Radio;
class MC13224V_Radio;
class Radio_Wrapper;
class ML310_NIC;
class PLASMA_NIC;
class AXI4LITE_NIC;
class MC13224V_NIC;
class IntegratorCP_NIC;
template<typename> class CMAC;
template<typename> class CMAC_State;

template<typename> class Empty;
template<typename> class Sync_Empty;
template<typename> class Pack_Empty;
template<typename> class Contention_Empty;
template<typename> class Tx_Empty;
template<typename> class Ack_Rx_Empty;
template<typename> class Lpl_Empty;
template<typename> class Rx_Empty;
template<typename> class Unpack_Empty;
template<typename> class Ack_Tx_Empty;

template<typename> class Synchronous_Sync;
template<typename> class Asynchronous_Sync;
template<typename> class Rx_Contention;
template<typename> class Tx_Contention;
template<typename> class Backoff;
template<typename> class CSMA;

template<typename> class Generic_Active;
template<typename> class Generic_Tx;
template<typename> class Generic_Rx;
template<typename> class Generic_Lpl;

template<typename> class IEEE802154_Beacon_Sync;
template<typename> class IEEE802154_Pack;
template<typename> class IEEE802154_Unpack;
template<typename> class IEEE802154_Ack_Rx;
template<typename> class IEEE802154_Ack_Tx;
template<typename> class IEEE802154_Slotted_CSMA_Contention;

// Hardware Mediators - ADCs
class AT90CAN128_ADC;
class ATMega16_ADC;
class ATMega128_ADC;
class ATMega1281_ADC;
class AVR_ADC;
class MC13224V_ADC;
class MC13224V_Battery_ADC;

// Hardware Mediators - Battery
class ATMega1281_Battery;
class MC13224V_Battery;

// Hardware Mediators - Sensors
class PC_Sensor;
class ATMega128_Temperature_Sensor;
class ATMega128_Photo_Sensor;
class ATMega128_Accelerometer;
class ATMega1281_Temperature_Sensor;
class ATMega1281_Humidity_Sensor;
class MTS300;
class MTS300_Temperature;
class MTS300_Photo;
class ADXL202;
class Dummy_Accel;
class SHT11;
class SHT11_Temperature;
class SHT11_Humidity;
class MC13224V_Temperature_Sensor;
class ERTJ1VG103FA;
class MC13224V_ADXL345;

// Hardware Mediators - Encryption
class AES_Controller;
class ASM_Controller;

// Abstractions	- Process
class Thread;
class Task;
class Active;

// Abstractions - Scheduling Criteria
template <typename,typename> class Scheduler;
namespace Scheduling_Criteria
{
    //HW/SW criteria
    class Priority;
    class Round_Robin;
};
namespace Scheduling_Criteria
{
    //SW-only criteria
    class FCFS;
    class RM;
    class EDF;
    class EARM;
    class EAEDF;
    class CPU_Affinity;
};

// Abstractions	- Memory
class Segment;
class Address_Space;

// Abstractions	- Synchronization
class Synchronizer;
class Mutex;
class Semaphore;
class Condition;

// Abstractions	- Time
class Clock;
class Alarm;
class Chronometer;

// Abstractions	- Communication
class Ethernet;
class Network;
class IP;
class UDP;
class TCP;
class ICMP;
class RTP;

// Abstractions - Communication - Layer integration
class Neighboring;

// Abstractions - Service
template <int> class Service;
class Services;

// Abstractions - Service - Network service
template <typename, typename> class ARP;
template <typename, typename> class ADHOP;
template <typename, typename> class BCast;

// Abstractions	- Sentients
class Photo_Sentient;
class Temperature_Sentient;

// Abstractions - IEEE 1451
class IEEE1451_NCAP;
class IEEE1451_TIM;
class IEEE1451_Transducer;

// Abstractions - SIP
class SIP_Body;
class SIP_Header;
class SIP_Manager;
class SIP_Message;
class SIP_Transaction;
class SIP_User_Agent;

// Abstractions - Component Management
class Component_Manager;

__END_SYS

#endif
