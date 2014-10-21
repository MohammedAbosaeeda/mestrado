
#include "ptp.h"
#include <tsc.h>
#include <uart.h>
#include <utility/ostream.h>
#include <thread.h>

__USING_SYS;

OStream cout;
NIC * _nic;
PTP * _ptp;
UART uart(9600, 8 , 0 , 1);
unsigned long _timestamp;

void sendSyncMessage(){
	_ptp->doState();
	kout << "doStateExecutado\n";
}

void initProtocolParameters()
{
	_ptp->_ptp_parameters._sync_interval = 5000000; //microsecond
	_ptp->_ptp_parameters._clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER; //Master
	_ptp->_ptp_parameters._original_clock_stratum = PTP::PTP_DataSet::STRATUM_MASTER;
	_ptp->_ptp_parameters._state = PTP::INITIALIZING;
//	_ptp._ptp_parameters. = 1;
}

int main(int argc, char **argv)
{
	ARM7_TSC::setTimeStamp(1380239762);

	_nic = new NIC();
	_ptp = new PTP();
	initProtocolParameters();
	_ptp->setNIC(_nic);
	_ptp->execute();

	Function_Handler handler_b(&(sendSyncMessage));
	Alarm alarm_b(10000000, &handler_b, -1);

	while(true){
		Alarm::delay(2000000);
		cout << "Microseconds " << ARM7_TSC::getMicroseconds() << "\n";
		cout << "TimeStamp " << ARM7_TSC::getTimeStamp() + (ARM7_TSC::getMicroseconds()/1000000) << "\n";
	}
	return 1;
}
