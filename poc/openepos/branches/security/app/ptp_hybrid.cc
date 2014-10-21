#include <ptp.h>
#include <gpio_pin.h>
#include <rtc.h>
#include <uart.h>
#include <utility/ostream.h>
#include <thread.h>
#include <alarm.h>

__USING_SYS;

OStream cout;
NIC * _nic;
PTP * _ptp;
UART uart(9600, 8 , 0 , 1);
unsigned long _timestamp;
void led_g(bool on_off=true)
{
	//GPIO_Pin led(8);
	GPIO_Pin led(12);
	led.put(on_off);
}

void initProtocolParameters()
{
	// Iniciando e Configurando o PTP Clock
	_ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_HYBRID;
	_ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_HYBRID;
	_ptp->_ptp_parameters._state = PTP::INITIALIZING;
//	_ptp.ptp_id = 2;
}

int led_blink()
{
	while(1)
	{
		if(((ARM7_TSC::getMicroseconds()/1000000) % 5) == 0)
		{
			led_g(true);
		}
		else
		{
			led_g(false);
		}
	}
	return 0;
}

int main(int argc, char **argv)
{

	_nic = new NIC();
	_ptp = new PTP();
	initProtocolParameters();
	_ptp->setNIC(_nic);
	_ptp->execute();

	Thread led_blinker(&led_blink);
	led_blinker.join();
	while(true){
		Alarm::delay(1000000);
		kout << "Microseconds " << ARM7_TSC::getMicroseconds() << "\n";
		kout << "TimeStamp " << ARM7_TSC::getTimeStamp() + (ARM7_TSC::getMicroseconds()/1000000) << "\n";
		kout << "Dropped Packets: " << _nic->statistics().dropped_packets << endl;
	}
	return 1;
}
