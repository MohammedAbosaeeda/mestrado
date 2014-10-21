// EPOS Internal Type Management System

#ifndef __types_id_h
#define __types_id_h

namespace Unified {

// System Components IDs
// The order in this enumeration defines many things in the system (e.g. init)
typedef unsigned int Type_Id;
enum 
{
    CPU_ID,
    TSC_ID,
    MMU_ID,

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
    DISPLAY_ID,
    NIC_ID,
    RADIO_ID,
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

    COMPONENT_MANAGER_ID,
    ADD_ID,
    MULT_ID,

    UNKNOWN_TYPE_ID,
    LAST_TYPE_ID = UNKNOWN_TYPE_ID - 1
};

};

#endif
