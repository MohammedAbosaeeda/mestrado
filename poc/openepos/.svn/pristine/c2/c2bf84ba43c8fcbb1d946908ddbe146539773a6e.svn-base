#include <ptp.h>
#include <tsc.h>
#include <uart.h>
#include <utility/ostream.h>
#include <thread.h>
#include <periodic_thread.h>
#include <gpio_pin.h>

__USING_SYS;

void led_g(bool on_off=true)
{
	//GPIO_Pin led(8);
	GPIO_Pin led(12);
	led.put(on_off);
}

OStream cout;
NIC * _nic;
PTP * _ptp;
UART uart(9600, 8 , 0 , 1);
unsigned long _timestamp;

int sendSyncMessage(PTP *ptp){
	while(true)
	{
		kout << "doState...\n";
		ptp->doState();
		kout << "doStateExecutado\n";
		Periodic_Thread::wait_next();
	}
}

void initProtocolParameters()
{
	_ptp->_ptp_parameters._sync_interval = 5000000; //microsecond
	_ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER; //Master
	_ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER;
	_ptp->_ptp_parameters._state = PTP::INITIALIZING;
//	_ptp._ptp_parameters. = 1;
}

int led_blink()
{
	while(1)
	{
		if(((ARM7_TSC::getMicroseconds()/1000000) % 2) == 0)
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
	ARM7_TSC::setTimeStamp(1380239762);

	_nic = new NIC();
	_ptp = new PTP();
	initProtocolParameters();
	_ptp->setNIC(_nic);
	_ptp->execute();

	Periodic_Thread sync_messager(&sendSyncMessage, _ptp, 90000000, -1);
	sync_messager.join();
	Alarm::delay(300000000);
	sync_messager.suspend();
//	Function_Handler handler_b(&(sendSyncMessage));
//	Alarm alarm_b(10000000, &handler_b, -1);

	Thread led_blinker(&led_blink);
	led_blinker.join();
	while(true){
		Alarm::delay(1000000);
		cout << "Microseconds " << ARM7_TSC::getMicroseconds() << "\n";
		cout << "TimeStamp " << ARM7_TSC::getTimeStamp() + (ARM7_TSC::getMicroseconds()/1000000) << "\n";
	}
	return 1;
}
