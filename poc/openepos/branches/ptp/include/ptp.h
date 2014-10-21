// EPOS IEEE 1588 Protocol Declarations

// This work is licensed under the EPOS Software License v1.0.
// A copy of this license is available at the EPOS system source tree root.
// A copy of this license is also available online at:
// http://epos.lisha.ufsc.br/EPOS+Software+License+v1.0
// Note that EPOS Software License applies to both source code and executables.

#ifndef __ptp_h
#define __ptp_h

#include <utility/random.h>
#include <utility/observer.h>
#include <nic.h>
#include <alarm.h>
#include <rtc.h>
#include <traits.h>

__BEGIN_SYS

class PTP: Conditional_Observer
{
public:
	typedef unsigned char   u8;
	typedef unsigned short u16;
	typedef unsigned long  u32;
	typedef signed long  s32;

	class PTP_DataSet
	{
	public:

		PTP_DataSet() {
			_clock_stratum = 0;
		}

		class PortIdentity
		{
		public:
			char _clockIdentity[8];
			u16 _portNumber;
		}__attribute__((packed));

		class ClockQuality
		{
		public:
			u8 _clockClass;
			char _clockAccuracy;
			u16 _offsetScaledLogVariance;
		} __attribute__((packed));

		class TimeRepresentation
		{
		public:
			u16 _epoch_number;
			u32 _seconds;
			u32  _nanoseconds;
		} __attribute__((packed));

		enum {
			STRATUM_MASTER = 4,
			STRATUM_SLAVE = 255,
			STRATUM_HYBRID = 100,
			STRATUM_LISTENING = 99
		};

		bool _slaveOnly;
		char _announceInterval;
		unsigned int _original_clock_stratum;
		unsigned int _clock_stratum;
		long _sync_interval;
		u32 _correctionField;
		u32 _sequence_number;
		u8 _domain_number;
		TimeRepresentation _offset_from_master;
		TimeRepresentation _one_way_delay;
		TimeRepresentation _mean_path_delay;
		char _time_source;
		u8  _state;
		u16 _stepsRemoved;
		u16 _parent_last_sync_sequence_number;
		u16 _last_delay_req_tx_sequence_number;
		u16 _last_general_event_sequence_number;
		u16 _last_sync_tx_sequence_number;
		u16 _record_update;
		u16 _grandmaster_sequence_number;
		TimeRepresentation _master_to_slave_delay;
		TimeRepresentation _slave_to_master_delay;
		TimeRepresentation _t1_sync_tx_time;
		TimeRepresentation _t2_sync_rx_time;
		TimeRepresentation _t3_delay_req_tx_time;
		TimeRepresentation _t4_delay_req_rx_time;
		TimeRepresentation _sync_correction;
		TimeRepresentation _t1_sync_delta_time;  /**< time between transmitted sync messages */
		TimeRepresentation _t2_sync_delta_time;  /**< time between received    sync messages */
		short _Q;
		short _R;
		bool _sentDelayReq;
		u16 _sentDelayReqSequenceId;
		bool _sentSync;         /**< Sync Transmitted from Application */
		bool _message_activity;
		bool _synchronized;
		int _hybrid_resync_period;
		u32 _tempReceivedSource;
		u32 _offset_hybrid;
		TimeRepresentation _time;
	};

	class PTP_Message
	{
	public:
		class Header
		{
		public:
			u8 transportSpecificAndMessageType;      // 00       1
			u8 reserved1AndVersionPTP;               // 01       1
			u16 messageLength;                        // 02       2
			u8 domainNumber;                         // 04       1
			char reserved;
			u8 flagField;
			u32 correctionField; //nanoseconds multiplied by 216
			u8 reserved2;
			u16 sequenceId;                           // 05       2
			u8 control;                              // 07       1
			u32 sourcePortIdentity;						// 11       4
		} __attribute__((packed));

		class MsgSync
		{
		public:
			PTP_Message::Header head;					// 0       12
			PTP_DataSet::TimeRepresentation originTimestamp;			// 11      10
		} __attribute__((packed));

		class MsgDelayReq
		{
		public:// Offset  Length (bytes)
			PTP_Message::Header head;
			PTP_DataSet::TimeRepresentation originTimestamp;
			// 34       10
		} __attribute__((packed));

		class MsgAnnounce
		{
		public:
			PTP_Message::Header head;
			PTP_DataSet::TimeRepresentation originTimestamp;
			u16 currentUTCOffset;
			u8 reserved;
			u8 grandmasterPriority1;
			PTP_DataSet::ClockQuality         grandmasterClockQuality;
			u8 grandmasterPriority2;
			char grandmasterIdentity[8];
			u16 stepsRemoved;
			u8 timeSource;
		} __attribute__((packed));

		class MsgFollowUp
		{
		public:
			PTP_DataSet::TimeRepresentation preciseOriginTimestamp;
		} __attribute__((packed));

		class MsgDelayResp
		{
		public:
			PTP_Message::Header head;
			PTP_DataSet::TimeRepresentation receiveTimestamp;
			u32 requestingPortIdentity;
		} __attribute__((packed));

		class TLV {
			u16 tlvType;
			u16 lengthField;
			u32 valueField;
		} __attribute__((packed));

		class MsgManagement
		{
		public:
			PTP_Message::Header head;
			u32 targetPortIdentity;
			u8 startingBoundaryHops;
			u8 boundaryHops;
			u8 actionField; //4 bits of actionField
			char reserved;
			TLV managementTLV;
		} __attribute__((packed));

		PTP_Message(){

		}
		~PTP_Message(){

		}
		Header _header;
		MsgSync _sync;
		PTP_DataSet::TimeRepresentation * _time;
		MsgAnnounce  _announce;
		MsgFollowUp  _follow;
		MsgDelayResp  _delay_resp;
		MsgDelayReq  _delay_req;
		MsgManagement  _management;

	private:
		static PTP_Message * msg_instance;
	};

public:

	enum {
		INITIALIZING = 0,
		FAULTY = 1,
		DISABLED = 2,
		LISTENING = 3,
		PRE_MASTER = 4,
		MASTER = 5,
		PASSIVE = 6,
		HYBRID = 7,
		UNCALIBRATED = 8,
		SLAVE = 9
	};

	enum {
		SYNC_EVENT = 5,
		FOLLOW_UP_EVENT = 8,
		DELAY_REQ_EVENT = 1,
		HYBRID_DELAY_REQ_EVENT = 11,
		DELAY_RESP_EVENT = 9
	        };

	PTP();
	~PTP();
	void execute();
	bool initialize();
	void doState();
	void toState(char state);
	void setNIC(NIC * n);
	void update(Conditionally_Observed * o, int p);
	void msgPackHeader();
	void msgPackSync();
	void msgPackAnnounce();
	void msgPackDelayReq();
	void msgPackDelayReq_Aux();
	void msgPackFollowUp();
	void msgPackDelayResp();
	void handle();
	int send(char * msg, int size);
	int receive(char * data, int size);
	void handleSync(char * data);
	void handleFollowUp();
	void handleDelayReq(char * data);
	void handleDelayResp(char * data);
	void handleAnnounce();
	void issueSync();
	void issueFollowup();
	void issueDelayReq();
	void issueDelayResp();
	void issueAnnounce();
	void handleOffset();
	void calculateOffset();
	void cleanClockParameters();
	void adjustTime(long long offset , long long offsetmicro);
	void kalmannFilter();
	long long drift_counter;
	int messageType;
	PTP_Message _msg;
	PTP_DataSet _ptp_parameters;
	NIC * _nic;
};

__END_SYS

#endif
