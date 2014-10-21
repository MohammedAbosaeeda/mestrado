// OpenEPOS EMote2ARM Mediators Configuration

#ifndef __emote2arm_config_h
#define __emote2arm_config_h

#define __CPU_H				__HEADER_ARCH(cpu)
#define __FPU_H				__HEADER_ARCH(fpu)
#define __TSC_H				__HEADER_ARCH(tsc)
#define __MMU_H				__HEADER_ARCH(mmu)

#define __PCI_H				__HEADER_MACH(pci)
#define __EEPROM_H			__HEADER_MACH(eeprom)
#define __NIC_H				__HEADER_MACH(nic)
#define __MACH_H			__HEADER_MACH(machine)
#define __IC_H				__HEADER_MACH(ic)
#define __TIMER_H			__HEADER_MACH(timer)
#define __UART_H			__HEADER_MACH(uart)
#define __FLASH_H			__HEADER_MACH(flash)
#define __GPIO_PIN_H        __HEADER_MACH(gpio_pin)
#define __ADC_H             __HEADER_MACH(adc)
#define __BATTERY_H         __HEADER_MACH(battery)
#define __SENSOR_H          __HEADER_MACH(sensor)
#define __SPI_H             __HEADER_MACH(spi)
#define __ONE_WIRE_H        __HEADER_MACH(one_wire)
#define __I2C_H             __HEADER_MACH(i2c)

__BEGIN_SYS

typedef ARMv4TDMI		CPU;
typedef ARMv4TDMI_FPU	FPU;
typedef ARMv4TDMI_MMU	MMU;
typedef ARMv4TDMI_TSC	TSC;

typedef EMote2ARM		    	Machine;
typedef EMote2ARM_IC		    IC;
typedef EMote2ARM_Flash 		Flash;
typedef EMote2ARM_Timer_0		Alarm_Timer;
typedef EMote2ARM_Timer_1		Scheduler_Timer;
typedef EMote2ARM_Timer_2    	Timer_CMAC;
typedef EMote2ARM_Timer_3       TSC_Timer;
typedef Alarm_Timer		    	Timer;
typedef EMote2ARM_UART			UART;
typedef EMote2ARM_SPI           SPI;
typedef EMote2ARM_One_Wire      One_Wire;
typedef EMote2ARM_I2C           I2C;
typedef Serial_Display			Display;
typedef EMote2ARM_NIC			NIC;
typedef EMote2ARM_ADC			ADC;
typedef EMote2ARM_GPIO_Pin      GPIO_Pin;
typedef EMote2ARM_Battery_ADC	Battery_ADC;
typedef EMote2ARM_Battery    	Battery;
typedef EMote2ARM_Temperature_Sensor		Temperature_Sensor;
typedef EMote2ARM_Accelerometer Accelerometer;

__END_SYS

#endif
