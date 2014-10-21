// OpenEPOS Internal Type Management System

typedef __SIZE_TYPE__ size_t;

#ifndef __types_h
#define __types_h

inline void * operator new(size_t s, void * a) { return a; }
inline void * operator new[](size_t s, void * a) { return a; }

__BEGIN_SYS

// Dummy class for incomplete architectures and machines 
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
class ARMv4TDMI;

// Hardware Mediators - FPUs
class IA32_FPU;
class ARMv4TDMI_FPU;

// Hardware Mediators - Time-Stamp Counters
class IA32_TSC;
class ARMv4TDMI_TSC;

// Hardware Mediators - Memory Management Units
class IA32_MMU;
class ARMv4TDMI_MMU;

// Hardware Mediators - Performance Monitoring Units
class IA32_PMU;

// Hardware Mediators - Machines
class PC;
class EMote2ARM;

// Hardware Mediators - Busses
class PC_PCI;

// Hardware Mediators - Interrupt Controllers
class PC_IC;
class EMote2ARM_IC;

// Hardware Mediators - Timers
class PC_Timer;
class EMote2ARM_Timer_0;
class EMote2ARM_Timer_1;
class EMote2ARM_Timer_2;
class EMote2ARM_Timer_3;

// Hardware Mediators - Real Time Clocks
class PC_RTC;
class EMote2ARM_RTC;

// Hardware Mediators - EEPROMs
class PC_EEPROM;

// Hardware Mediators - FLASHs
class EMote2ARM_Flash;

// Hardware Mediators - UARTs
class PC_UART;
class EMote2ARM_UART;

// Hardware Mediators - SPIs
class PC_SPI;
class EMote2ARM_SPI;

// Hardware Mediators - One_Wire
class EMote2ARM_One_Wire;

// Hardware Mediators - I2Cs
class EMote2ARM_I2C;

// Hardware Mediators - Displays
class Serial_Display;
class PC_Display;
class EMote2ARM_Display;

// Hardware Mediators - NICs
class PC_Ethernet;
class PCNet32;
class C905;
class E100;
class EMote2ARM_Radio;
class Radio_Wrapper;
class EMote2ARM_NIC;
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

// Hardware Mediators - GPIOs
class EMote2ARM_GPIO_Pin;

// Hardware Mediators - ADCs
class EMote2ARM_ADC;
class EMote2ARM_Battery_ADC;

// Hardware Mediators - Battery
class EMote2ARM_Battery;

// Hardware Mediators - Sensors
class PC_Sensor;
class EMote2ARM_Temperature_Sensor;
class EMote2ARM_Audio_Sensor;
class EMote2ARM_Accelerometer;
class ADXL202;
class ADXL345;
class Microphone;
class Thermistor;

// Hardware Mediators - Encryption
class EMote2ARM_AES_Controller;
class EMote2ARM_ASM_Controller;

// Abstractions	- Process
class Thread;
class Task;
class Active;

// Abstractions - Scheduler
template <typename> class Scheduler;
namespace Scheduling_Criteria
{
    class Priority;
    class FCFS;
    class Round_Robin;
    class RM;
    class EDF;
    class EARM;
    class EAEDF;
    class CPU_Affinity;
    class PEDF;
    class PRM;
    class GEDF;
    class GRM;
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
class Single_Core_Alarm;
class SMP_Alarm;
class Chronometer;

// Abstractions - Service
template <int> class Service;
class Services;

// Abstractions - Service - Network service
template <typename, typename> class ARP;
template <typename, typename> class ADHOP;

// Abstractions - Communication - Protocols
class Ethernet;
class Network;
class ELP;
class Router;
class IP;
class ICMP;
class UDP;
class TCP;
class RTP;

// Abstractions - Communication - List of neighbors
class Neighborhood;

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

// System Components IDs
// The order in this enumeration defines many things in the system (e.g. init)
typedef unsigned int Type_Id;
enum 
{
    CPU_ID,
	FPU_ID,
    TSC_ID,
    MMU_ID,
	PMU_ID,

    MACHINE_ID,
    PCI_ID,
    NOC_ID,
    IC_ID,
    TIMER_ID,
    RTC_ID,
    EEPROM_ID,
    FLASH_ID,
    UART_ID,
    USART_ID,
    SPI_ID,
    ONE_WIRE_ID,
    I2C_ID,
    DISPLAY_ID,
    NIC_ID,
    RADIO_ID,
    GPIO_PIN_ID,
    ADC_ID,
    BATTERY_ID,
    TEMPERATURE_SENSOR_ID,
    PHOTO_SENSOR_ID,
    ACCELEROMETER_ID,

    THREAD_ID,
    TASK_ID,
    ACTIVE_ID,

    SEGMENT_ID,
    ADDRESS_SPACE_ID,

    MUTEX_ID,
    SEMAPHORE_ID,
    CONDITION_ID,

    CLOCK_ID,
    ALARM_ID,
    CHRONOMETER_ID,

    NETWORK_ID,
    IP_ID,
    ARP_ID,
    UDP_ID,
    CAN_ID,

    UNKNOWN_TYPE_ID,
    LAST_TYPE_ID = UNKNOWN_TYPE_ID - 1
};

__END_SYS

#endif
