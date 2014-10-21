// EPOS IEEE 1588 Protocol Implementation

// This work is licensed under the EPOS Software License v1.0.
// A copy of this license is available at the EPOS system source tree root.
// A copy of this license is also available online at:
// http://epos.lisha.ufsc.br/EPOS+Software+License+v1.0
// Note that EPOS Software License applies to both source code and executables.

#include <ptp.h>
#include <utility/ostream.h>

__BEGIN_SYS

// IEEE1588 Protocol class attributes
bool sendSync, synchronized;
long long offset, offsetmicro;
NIC::Protocol prot;
NIC::Address src;
// Methods

PTP::PTP()
{
}

PTP::~PTP()
{
}


void PTP::doState(){
	_ptp_parameters._message_activity = false;
	switch (_ptp_parameters._state)
	{
	case FAULTY:{
		toState(PTP::INITIALIZING);
		return;
	}
	case LISTENING:
		break;

	case HYBRID:{
		if(!_ptp_parameters._synchronized && _ptp_parameters._state == PTP::HYBRID){
			_ptp_parameters._clock_stratum = PTP_DataSet::STRATUM_SLAVE;
			initialize();
		}
		if(_ptp_parameters._original_clock_stratum == PTP_DataSet::STRATUM_HYBRID && _ptp_parameters._state == PTP::SLAVE && _ptp_parameters._synchronized){
			_ptp_parameters._clock_stratum = PTP_DataSet::STRATUM_HYBRID;
			initialize();
		}
		break;
	}
	case UNCALIBRATED:
	case PASSIVE:
	case SLAVE:{
		break;
	}
	case MASTER:{
		issueSync();
		break;
	}
	case DISABLED:
		break;

	default:
		break;
	}
}


bool PTP::initialize(){
	_ptp_parameters._parent_last_sync_sequence_number = -1;
	if(_ptp_parameters._clock_stratum == PTP_DataSet::STRATUM_MASTER){
		toState(MASTER);
	}
	else if(_ptp_parameters._clock_stratum == PTP_DataSet::STRATUM_HYBRID){
		toState(HYBRID);
	}
	else if(_ptp_parameters._slaveOnly || _ptp_parameters._clock_stratum == PTP_DataSet::STRATUM_SLAVE){
		toState(SLAVE);
	}
	return true;
}

void PTP::toState(char state){
	_ptp_parameters._message_activity = true;

	switch (state)
	{
	case INITIALIZING:
		_ptp_parameters._state = INITIALIZING;
		break;

	case FAULTY:
		_ptp_parameters._state = FAULTY;
		break;

	case DISABLED:
		_ptp_parameters._state = DISABLED;
		break;

	case LISTENING:
		_ptp_parameters._state = LISTENING;
		break;

	case HYBRID:
		_ptp_parameters._state = HYBRID;
		break;

	case MASTER:
		_ptp_parameters._state = MASTER;
		_ptp_parameters._t1_sync_tx_time._seconds = 0;
		_ptp_parameters._t1_sync_tx_time._nanoseconds = 0;
		_ptp_parameters._t3_delay_req_tx_time._seconds = 0;
		_ptp_parameters._t3_delay_req_tx_time._nanoseconds = 0;
		_ptp_parameters._sequence_number = 1;
		break;

	case PASSIVE:
		_ptp_parameters._state = PASSIVE;
		break;

	case UNCALIBRATED:
		_ptp_parameters._state = UNCALIBRATED;
		break;

	case SLAVE:
		_ptp_parameters._Q = 0;
		_ptp_parameters._R = 0;
		_ptp_parameters._t2_sync_rx_time._seconds = 0;
		_ptp_parameters._t2_sync_rx_time._nanoseconds = 0;
		_ptp_parameters._t4_delay_req_rx_time._seconds = 0;
		_ptp_parameters._t4_delay_req_rx_time._nanoseconds = 0;
		_ptp_parameters._sequence_number = 0;
		_ptp_parameters._state = SLAVE;
		break;

	default:
		break;
	}
}

void PTP::execute(){
	if(_ptp_parameters._state != INITIALIZING)
		doState();
	if(_ptp_parameters._state == INITIALIZING){
		initialize();

	}
}

void PTP::handleOffset(){
	kout << _ptp_parameters._t1_sync_tx_time._nanoseconds << "\n"<<
			 _ptp_parameters._t2_sync_rx_time._nanoseconds <<  "\n"<<
			 _ptp_parameters._t3_delay_req_tx_time._nanoseconds <<  "\n"<<
			 _ptp_parameters._t4_delay_req_rx_time._nanoseconds << "\n";
	if(_ptp_parameters._t1_sync_tx_time._nanoseconds != 0 && _ptp_parameters._t2_sync_rx_time._nanoseconds != 0 && _ptp_parameters._t3_delay_req_tx_time._nanoseconds !=0 && _ptp_parameters._t4_delay_req_rx_time._nanoseconds !=0){
		calculateOffset();
		adjustTime(offset, offsetmicro);
		_ptp_parameters._sequence_number++;
	}else{
		kout << "drift counter\n";
		drift_counter++;
	}
	cleanClockParameters();

}

void PTP::cleanClockParameters(){
	_ptp_parameters._t1_sync_tx_time._seconds =0;
	_ptp_parameters._t2_sync_rx_time._seconds =0;
	_ptp_parameters._t3_delay_req_tx_time._seconds =0;
	_ptp_parameters._t4_delay_req_rx_time._seconds =0;

	_ptp_parameters._t1_sync_tx_time._nanoseconds =0;
	_ptp_parameters._t2_sync_rx_time._nanoseconds =0;
	_ptp_parameters._t3_delay_req_tx_time._nanoseconds =0;
	_ptp_parameters._t4_delay_req_rx_time._nanoseconds =0;
}

void PTP::calculateOffset(){
	offset = (((long long)_ptp_parameters._t2_sync_rx_time._seconds - (long long)_ptp_parameters._t1_sync_tx_time._seconds) - ((long long)_ptp_parameters._t4_delay_req_rx_time._seconds - (long long)_ptp_parameters._t3_delay_req_tx_time._seconds))/2;
	offsetmicro = (((long long)_ptp_parameters._t2_sync_rx_time._nanoseconds - (long long)_ptp_parameters._t1_sync_tx_time._nanoseconds) - ( (long long)_ptp_parameters._t4_delay_req_rx_time._nanoseconds - (long long)_ptp_parameters._t3_delay_req_tx_time._nanoseconds))/2;
	offset = offset * (-1);
	offsetmicro = offsetmicro * (-1);
	kout << "offset: " << offset << "\n";
	kout << "offset micro: " << offsetmicro << "\n";
}

void PTP::kalmannFilter(){

}

void PTP::adjustTime(long long offset , long long offsetmicro){
	if((offset < 1559140643) && (offset > -1559140643)){
		ARM7_TSC::setTimeStamp((ARM7_TSC::getTimeStamp() + offset));
		ARM7_TSC::setMicrosecondsSWClock(ARM7_TSC::getMicrosecondsSWClock() + (long)offsetmicro);
		synchronized = true;
	}
}

void PTP::msgPackHeader(){
	_msg._header.transportSpecificAndMessageType = 0;
	_msg._header.reserved1AndVersionPTP = Traits<PTP>::_version_number;
	_msg._header.messageLength = 0;
	_msg._header.domainNumber = _ptp_parameters._domain_number;
	_msg._header.sequenceId = 0;
	_msg._header.control = 0;
	_msg._header.sourcePortIdentity = Traits<PTP>::protocolId;
}

void PTP::msgPackSync()
{
	/* PTP Header */
	msgPackHeader();
	/* Message type */
	_msg._sync.head.transportSpecificAndMessageType  = 5;
	_msg._sync.head.messageLength =  sizeof(PTP_Message::MsgSync);
	_msg._sync.head.sequenceId = _ptp_parameters._sequence_number;
	_msg._sync.head.sourcePortIdentity = Traits<PTP>::protocolId;
	/* Timestamp: */
	_msg._sync.originTimestamp = _ptp_parameters._time;
	_msg._sync.originTimestamp._epoch_number =  _ptp_parameters._t1_sync_tx_time._epoch_number;
	_msg._sync.originTimestamp._seconds = _ptp_parameters._t1_sync_tx_time._seconds;
	_msg._sync.originTimestamp._nanoseconds =  _ptp_parameters._t1_sync_tx_time._nanoseconds;
}

void PTP::msgPackAnnounce(){

	/* PTP Header */
	msgPackHeader();
	/* Message type */
	_msg._announce.head.transportSpecificAndMessageType = 8;
	/* Length */
	_msg._announce.head.messageLength =  sizeof(PTP_Message::MsgAnnounce); /* Length */
}

void PTP::msgPackDelayReq()
{
	msgPackHeader();
	_msg._delay_req.head.transportSpecificAndMessageType = 1;
	_msg._delay_req.head.sourcePortIdentity = Traits<PTP>::protocolId;
	_msg._delay_req.head.sequenceId = _ptp_parameters._sequence_number;
	/* Timestamp: */
	_msg._delay_req.originTimestamp._epoch_number =  _ptp_parameters._t3_delay_req_tx_time._epoch_number;
	_msg._delay_req.originTimestamp._seconds = _ptp_parameters._t3_delay_req_tx_time._seconds;
	_msg._delay_req.originTimestamp._nanoseconds =  _ptp_parameters._t3_delay_req_tx_time._nanoseconds;
}


void PTP::msgPackDelayReq_Aux()
{
	msgPackHeader();
	_msg._delay_req.head.transportSpecificAndMessageType = 11;
	_msg._delay_req.head.sourcePortIdentity = Traits<PTP>::protocolId;
	_msg._delay_req.head.sequenceId = _ptp_parameters._sequence_number;
	/* Timestamp: */
	_msg._delay_req.originTimestamp._epoch_number =  _ptp_parameters._t2_sync_rx_time._epoch_number;
	_msg._delay_req.originTimestamp._seconds = _ptp_parameters._t2_sync_rx_time._seconds;
	_msg._delay_req.originTimestamp._nanoseconds =  _ptp_parameters._t2_sync_rx_time._nanoseconds;
}

void PTP::msgPackDelayResp()
{
	/* PTP Header */
	msgPackHeader();
	/* Message type, length, flags, Sequence, Control, log mean message interval */
	_msg._delay_resp.head.transportSpecificAndMessageType = 9;
	_msg._delay_resp.head.sourcePortIdentity = Traits<PTP>::protocolId;
	_msg._delay_resp.head.sequenceId = _ptp_parameters._sequence_number - 1; // -1 because we increment the variable after a sync
	/* Timestamp */
	_msg._delay_resp.requestingPortIdentity = _ptp_parameters._tempReceivedSource;
	_msg._delay_resp.receiveTimestamp._epoch_number =  _ptp_parameters._t4_delay_req_rx_time._epoch_number;
	_msg._delay_resp.receiveTimestamp._seconds =  _ptp_parameters._t4_delay_req_rx_time._seconds;
	_msg._delay_resp.receiveTimestamp._nanoseconds =  _ptp_parameters._t4_delay_req_rx_time._nanoseconds;
	/* requestingPortId, copy from Delay Request header */
}

void PTP::msgPackFollowUp()
{
	//to be done at MAC leve or even physical
}

void PTP::handle(){
	unsigned long timestamp;
	char data[_nic->mtu()];
	receive(data,sizeof(data));
	PTP_Message::Header * receivedHeader = (PTP_Message::Header*)data;
	messageType = receivedHeader->transportSpecificAndMessageType;

	switch(messageType)
	{
	case SYNC_EVENT:{
		_ptp_parameters._t2_sync_rx_time._seconds = ARM7_TSC::getTimeStamp();
		_ptp_parameters._t2_sync_rx_time._nanoseconds = ARM7_TSC::getMicroseconds();
		handleSync(data);
		break;
	}
	case FOLLOW_UP_EVENT:{
		handleFollowUp();
		break;
	}
	case DELAY_REQ_EVENT:{
		_ptp_parameters._t4_delay_req_rx_time._seconds = ARM7_TSC::getTimeStamp();
		_ptp_parameters._t4_delay_req_rx_time._nanoseconds = ARM7_TSC::getMicroseconds();
		handleDelayReq(data);
		break;
	}
	case DELAY_RESP_EVENT:{
		handleDelayResp(data);
		break;
	}
	}
	if(_ptp_parameters._state == PTP::HYBRID && messageType == HYBRID_DELAY_REQ_EVENT){
		handleDelayReq(data);
	}
	delete(receivedHeader);
}

int PTP::send(char * msg, int size){
	return _nic->send(NIC::BROADCAST, (NIC::Protocol) Traits<PTP>::protocolId, msg, size);
}

int PTP::receive(char * data, int size){
	return _nic->receive(&src, &prot, data, size);
}

//HANDLERS

void PTP::handleSync(char * data){
	PTP_Message::MsgSync syncMessage = *(PTP_Message::MsgSync*)data;
	bool sync_source_ok;
	u16 sequence_delta;
	bool current_sequence;
	switch(_ptp_parameters._state)
	{
	case PTP::FAULTY:
	case PTP::INITIALIZING:
	case PTP::LISTENING:
	case PTP::HYBRID:
		sequence_delta = syncMessage.head.sequenceId - _ptp_parameters._sequence_number;
		if(sequence_delta == 0)
		{
			kout << "handleSync: Possible duplicate sequence %u received!, ignoring"<< syncMessage.head.sequenceId << "\n";
			return;
		}
		if  (sequence_delta == 1){
			_ptp_parameters._sequence_number = syncMessage.head.sequenceId;
			_ptp_parameters._t1_sync_tx_time._seconds = syncMessage.originTimestamp._seconds;
			_ptp_parameters._t1_sync_tx_time._nanoseconds = syncMessage.originTimestamp._nanoseconds;
		}
		break;
	case PTP::DISABLED:
		kout << "handleSync: FAULTY, INITIALIZING or DISABLED \n";
		return;
	case PTP::UNCALIBRATED:
	case PTP::SLAVE:
		sequence_delta = syncMessage.head.sequenceId - _ptp_parameters._sequence_number;
		kout << "sequence delta" << sequence_delta << "\n";
		if(sequence_delta == 0)
		{
			kout << "handleSync: Possible duplicate sequence %u received!, ignoring"<< syncMessage.head.sequenceId << "\n";
			return;
		}
		if  (sequence_delta == 1){
			_ptp_parameters._sequence_number = syncMessage.head.sequenceId;
			_ptp_parameters._t1_sync_tx_time._seconds = syncMessage.originTimestamp._seconds;
			_ptp_parameters._t1_sync_tx_time._nanoseconds = syncMessage.originTimestamp._nanoseconds;
			issueDelayReq();
		}
		break;
	}
}

void PTP::handleFollowUp(){
	//TODO
}

void PTP::handleDelayReq(char * data)
{
	switch(_ptp_parameters._state)
	{
	case MASTER:{
		PTP_Message::MsgDelayReq req = *(PTP_Message::MsgDelayReq*)data;
		if(req.head.sequenceId ==( _ptp_parameters._sequence_number + 1)){
			_ptp_parameters._sequence_number = req.head.sequenceId;
			_ptp_parameters._tempReceivedSource = req.head.sourcePortIdentity;
			issueDelayResp();
		}
		break;
	}
	case HYBRID:{
		PTP_Message::MsgDelayReq req = *(PTP_Message::MsgDelayReq*)data;
		if(req.head.sequenceId == (_ptp_parameters._sequence_number + 1)){
			_ptp_parameters._sequence_number = req.head.sequenceId;
			if(req.head.transportSpecificAndMessageType == 11){
				_ptp_parameters._t2_sync_rx_time._seconds = req.originTimestamp._seconds;
				_ptp_parameters._t2_sync_rx_time._nanoseconds = req.originTimestamp._nanoseconds;
			}
			if(req.head.transportSpecificAndMessageType == 1){
				_ptp_parameters._t3_delay_req_tx_time._seconds = req.originTimestamp._seconds;
				_ptp_parameters._t3_delay_req_tx_time._nanoseconds = req.originTimestamp._nanoseconds;
			}
		}
		break;
	}
	case SLAVE:
		break;
	default:
		return;
	}
}

void PTP::handleDelayResp(char * data)
{
	PTP_Message::MsgDelayResp delayResponseMessage = *(PTP_Message::MsgDelayResp*)data;
	if(_ptp_parameters._sequence_number == delayResponseMessage.head.sequenceId + 1){
		_ptp_parameters._sequence_number = delayResponseMessage.head.sequenceId;
		_ptp_parameters._t4_delay_req_rx_time._seconds = delayResponseMessage.receiveTimestamp._seconds;
		_ptp_parameters._t4_delay_req_rx_time._nanoseconds = delayResponseMessage.receiveTimestamp._nanoseconds;
		handleOffset();
	}
}

void PTP::handleAnnounce()
{
	PTP_Message::MsgAnnounce announce;
	u16   sequence_delta;
	switch(_ptp_parameters._state)
	{
	case PTP::FAULTY:
	case PTP::INITIALIZING:
	case PTP::DISABLED:
		kout << "handleAnnounce: FAULTY, INITIALIZING or DISABLED, disregard\n";
		return;
	case PTP::UNCALIBRATED:
	case PTP::HYBRID:
	case PTP::SLAVE:
		kout << "handleAnnounce: SLAVE, HYBRID or UNCALIBRATED:\n";
		_ptp_parameters._record_update = true;
		//		_ptp_parameters._parent_last_announce_sequence_number = announce.head.sequenceId;
		break;
	case PTP::MASTER:
	default:
		_ptp_parameters._record_update = true;
		kout << "handleAnnounce: call add foreign\n";
		break;
	}
}

/* ISSUE Methods*/

void PTP::issueSync()
{
	_ptp_parameters._sentSync= true;
	char * msg;
	msg = (char *)&(_msg._sync);
	_ptp_parameters._t1_sync_tx_time._seconds = ARM7_TSC::getTimeStamp();
	_ptp_parameters._t1_sync_tx_time._nanoseconds = ARM7_TSC::getMicroseconds();
	msgPackSync();
	if(send(msg, sizeof(PTP_Message::MsgSync)) == 11)
		_ptp_parameters._sequence_number++;
	free(msg);
}

void PTP::issueFollowup(){
	char * msg;
	msg = (char *)&(_msg._follow);
	msgPackFollowUp();
	if(send(msg, sizeof(PTP_Message::MsgSync)))
		_ptp_parameters._sequence_number++;
	free(msg);
}

void PTP::issueAnnounce(){
	//	char * msg;
	//	++_ptp_parameters._last_announce_tx_sequence_number;
	//	_ptp_parameters._grandmaster_sequence_number = _ptp->_ptp_parameters->_last_announce_tx_sequence_number;
	//	msg = (char *)&(_msg->_announce);
	//	_msg->msgPackAnnounce(_ptp);
	//	for(int i=0;i<2;i++){
	//		send(msg, sizeof(IEEE1588_Msg::MsgAnnounce));
	//		_msg->msgPackAnnounce(_ptp);
	//	}
	//	free(msg);
}

void PTP::issueDelayResp(){
	_ptp_parameters._sequence_number++;
	char * msg;
	msg = (char *)&(_msg._delay_resp);
	msgPackDelayResp();
	for(int i=0;i<2;i++){
		send(msg, sizeof(PTP_Message::MsgDelayResp));
		msgPackDelayResp();
	}
	delete(msg);
}

void PTP::issueDelayReq()
{
	_ptp_parameters._sentDelayReq = true;
	_ptp_parameters._sequence_number++;
	char * msg;
	msg = (char *)&(_msg._delay_req);
	msgPackDelayReq_Aux();
	for(int i=0;i<2;i++){
		send(msg, sizeof(PTP_Message::MsgDelayReq));
		msgPackDelayReq_Aux();
	}
	_ptp_parameters._t3_delay_req_tx_time._seconds = ARM7_TSC::getTimeStamp();
	_ptp_parameters._t3_delay_req_tx_time._nanoseconds = ARM7_TSC::getMicroseconds();
	msgPackDelayReq();
	for(int i=0;i<2;i++){
		send(msg, sizeof(PTP_Message::MsgDelayReq));
		msgPackDelayReq();
	}
	delete(msg);
}


void PTP::setNIC(NIC * n){
	_nic = n;
	_nic->attach(this,Traits<PTP>::protocolId);
}

void PTP::update(Conditionally_Observed * o, int p){
	handle();
}
__END_SYS

