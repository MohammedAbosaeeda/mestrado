//EPOS Compile-time Type Information

#ifndef __ctti_h
#define __ctti_h

#include <system/config.h>
#include "../../../components/include/system/ctti.h"

namespace Unified {

// Type -> Id

template<> struct Type2Id<System::CPU>      { enum { ID = CPU_ID }; };
template<> struct Type2Id<System::TSC>      { enum { ID = TSC_ID }; };
template<> struct Type2Id<System::MMU>      { enum { ID = MMU_ID }; };

template<> struct Type2Id<System::Machine>  { enum { ID = MACHINE_ID }; };
template<> struct Type2Id<System::PCI>      { enum { ID = PCI_ID }; };
template<> struct Type2Id<System::NOC>       { enum { ID = NOC_ID }; };
template<> struct Type2Id<System::IC>       { enum { ID = IC_ID }; };
template<> struct Type2Id<System::Timer>    { enum { ID = TIMER_ID }; };
template<> struct Type2Id<System::RTC>      { enum { ID = RTC_ID }; };
template<> struct Type2Id<System::EEPROM>   { enum { ID = EEPROM_ID }; };
template<> struct Type2Id<System::Flash>   { enum { ID = FLASH_ID }; };
template<> struct Type2Id<System::UART>     { enum { ID = UART_ID }; };
template<> struct Type2Id<System::USART>     { enum { ID = USART_ID }; };
template<> struct Type2Id<System::SPI>      { enum { ID = SPI_ID }; };
template<> struct Type2Id<System::Display>  { enum { ID = DISPLAY_ID }; };
template<> struct Type2Id<System::NIC>      { enum { ID = NIC_ID }; };
template<> struct Type2Id<System::Radio>      { enum { ID = RADIO_ID }; };
template<> struct Type2Id<System::ADC>      { enum { ID = ADC_ID }; };
template<> struct Type2Id<System::Battery>      { enum { ID = BATTERY_ID }; };
template<> struct Type2Id<System::Temperature_Sensor>{ enum { ID = TEMPERATURE_SENSOR_ID }; };
template<> struct Type2Id<System::Photo_Sensor> { enum { ID = PHOTO_SENSOR_ID }; };
template<> struct Type2Id<System::Accelerometer> { enum { ID = ACCELEROMETER_ID }; };

template<> struct Type2Id<System::Thread>   { enum { ID = THREAD_ID }; };
template<> struct Type2Id<System::Task>     { enum { ID = TASK_ID }; };
template<> struct Type2Id<System::Active>   { enum { ID = ACTIVE_ID }; };

template<> struct Type2Id<System::Segment>  { enum { ID = SEGMENT_ID }; };
template<> struct Type2Id<System::Address_Space>{ enum { ID = ADDRESS_SPACE_ID }; };

template<> struct Type2Id<System::Mutex>    { enum { ID = MUTEX_ID }; };
template<> struct Type2Id<System::Semaphore>    { enum { ID = SEMAPHORE_ID }; };
template<> struct Type2Id<System::Condition>    { enum { ID = CONDITION_ID }; };

template<> struct Type2Id<System::Clock>    { enum { ID = CLOCK_ID }; };
template<> struct Type2Id<System::Alarm>    { enum { ID = ALARM_ID }; };
template<> struct Type2Id<System::Chronometer>  { enum { ID = CHRONOMETER_ID }; };

template<> struct Type2Id<System::Network>  { enum { ID = NETWORK_ID }; };
template<> struct Type2Id<System::IP>  { enum { ID = IP_ID }; };
template<> struct Type2Id<System::ARP<void,void> >  { enum { ID = ARP_ID }; };
template<> struct Type2Id<System::UDP>  { enum { ID = UDP_ID }; };
template<> struct Type2Id<System::CAN>  { enum { ID = CAN_ID }; };

template<> struct Type2Id<System::Component_Manager>  { enum { ID = COMPONENT_MANAGER_ID }; };


// Id -> Type

template<> struct Id2Type<CPU_ID>   { typedef System::CPU TYPE; };
template<> struct Id2Type<TSC_ID>   { typedef System::TSC TYPE; };
template<> struct Id2Type<MMU_ID>   { typedef System::MMU TYPE; };

template<> struct Id2Type<MACHINE_ID>   { typedef System::Machine TYPE; };
template<> struct Id2Type<PCI_ID>   { typedef System::PCI TYPE; };
template<> struct Id2Type<NOC_ID>   { typedef System::NOC TYPE; };
template<> struct Id2Type<IC_ID>    { typedef System::IC TYPE; };
template<> struct Id2Type<TIMER_ID> { typedef System::Timer TYPE; };
template<> struct Id2Type<RTC_ID>   { typedef System::RTC TYPE; };
template<> struct Id2Type<EEPROM_ID>    { typedef System::EEPROM TYPE; };
template<> struct Id2Type<FLASH_ID>    { typedef System::Flash TYPE; };
template<> struct Id2Type<UART_ID>  { typedef System::UART TYPE; };
template<> struct Id2Type<USART_ID>  { typedef System::USART TYPE; };
template<> struct Id2Type<SPI_ID>   { typedef System::SPI TYPE; };
template<> struct Id2Type<DISPLAY_ID>   { typedef System::Display TYPE; };
template<> struct Id2Type<NIC_ID>   { typedef System::NIC TYPE; };
template<> struct Id2Type<RADIO_ID>   { typedef System::Radio TYPE; };
template<> struct Id2Type<ADC_ID>   { typedef System::ADC TYPE; };
template<> struct Id2Type<BATTERY_ID>   { typedef System::Battery TYPE; };
template<> struct Id2Type<TEMPERATURE_SENSOR_ID> { typedef System::Temperature_Sensor TYPE; };
template<> struct Id2Type<PHOTO_SENSOR_ID> { typedef System::Photo_Sensor TYPE; };
template<> struct Id2Type<ACCELEROMETER_ID> { typedef System::Accelerometer TYPE; };

template<> struct Id2Type<THREAD_ID>    { typedef System::Thread TYPE; };
template<> struct Id2Type<TASK_ID>  { typedef System::Task TYPE; };
template<> struct Id2Type<ACTIVE_ID>    { typedef System::Active TYPE; };

template<> struct Id2Type<ADDRESS_SPACE_ID>{ typedef System::Address_Space TYPE; };
template<> struct Id2Type<SEGMENT_ID>   { typedef System::Segment TYPE; };

template<> struct Id2Type<MUTEX_ID> { typedef System::Mutex TYPE; };
template<> struct Id2Type<SEMAPHORE_ID> { typedef System::Semaphore TYPE; };
template<> struct Id2Type<CONDITION_ID> { typedef System::Condition TYPE; };

template<> struct Id2Type<CLOCK_ID> { typedef System::Clock TYPE; };
template<> struct Id2Type<ALARM_ID> { typedef System::Alarm TYPE; };
template<> struct Id2Type<CHRONOMETER_ID>{ typedef System::Chronometer TYPE; };

template<> struct Id2Type<NETWORK_ID>   { typedef System::Network TYPE; };
template<> struct Id2Type<IP_ID>   { typedef System::IP TYPE; };
template<> struct Id2Type<ARP_ID>   { typedef System::ARP<void,void> TYPE; };
template<> struct Id2Type<UDP_ID>   { typedef System::UDP TYPE; };
template<> struct Id2Type<CAN_ID>   { typedef System::CAN TYPE; };

template<> struct Id2Type<COMPONENT_MANAGER_ID>   { typedef System::Component_Manager TYPE; };


//Unified components redeclations
//TODO these may change
template<> struct Type2Id<Sched<System::Thread*, System::Traits<System::Thread*>::Criterion > > : public Type2Id<Sched<void,void> >{};

}

#endif /* CTTI_H_ */
