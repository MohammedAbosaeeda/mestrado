#include <mach/common/iee_802_15_4.h>
#include <alarm.h>
#include <transceiver.h>
#include <stdlib.h>
#include <math.h>
#include <utility/debug.h>
#include <utility/crc.h>

namespace System {

/***************************************************************
 *
 * PROTOCOL PUBLIC INTERFACE
 *
 ***************************************************************
 * */

OStream os;
unsigned int IEE_802_15_4::myAddress;
bool IEE_802_15_4::_ack_needed;

IEE_802_15_4::IEE_802_15_4() {
}

const IEE_802_15_4::Address & IEE_802_15_4::address() {
}

const IEE_802_15_4::Statistics & IEE_802_15_4::statistics() {
}

void IEE_802_15_4::reset() {
}

void IEE_802_15_4::config(int frequency, int power) {
}

unsigned int IEE_802_15_4::mtu() const {
	return 0;
}

void IEE_802_15_4::init(unsigned int n) {
	myAddress = n;
	srand(myAddress);//Different seed to any node
	macDSN = rand();
	os << "init()->macDSN: " << macDSN << "\n";
}
int IEE_802_15_4::send(const Address & dst, const Protocol & prot,
		const void *data, unsigned int size) {
	return csma_ca_send(dst, prot, data, size);
}

int IEE_802_15_4::receive(Address * src, Protocol * prot, void * data,
		unsigned int size) {
    return IEE_802_15_4::receive(src, prot, data, 0);
}
int IEE_802_15_4::receive(Address * src, Protocol * prot, void * data,
		unsigned int size, long receivingTime) {
	Transceiver::result_t result;
	if (_rx_available) {
		_rx_available = false;
//		os << "RX Available! \n";
		result = radio.rx_on();
//		if (result == Tranceiver::SUCCESS)
//			os << "RX on! \n";
//		else
//			os << "RX on failed \n";
		if (receivingTime == 0) {
//			os << "Wait Infinito \n";
			//while (!radio.dataReceived()); //TODO change to the new radio interfce
		} else {
//			os << "Wait " << receivingTime << "\n";
			Alarm::delay(receivingTime);
//			os << "deu delay \n";
			/*if (!radio.dataReceived()) { //TODO change to the new radio interface
				while ((radio.rx_off() == radio.BUSY)) {
					//					os << "BUSY \n";
				}
				//				os << "Nao recebeu ack no tempo " << receivingTime << "\n";
				return 0;//TODO return error
			}*/
//			os << "fim do else, recebeu algo";
		}
		radio.rx_off();
		int frameBufferSize = 0;
		result = radio.receive(buffer, frameBufferSize);
//		os << "Result \n";
		if (result == Transceiver::SUCCESS) {
//			os << "RX Sucess! \n";
			unsigned short crc = CRC::crc16((reinterpret_cast<char*> (buffer)),
					frameBufferSize - 2);
			unsigned char *framePtr = reinterpret_cast<unsigned char*> (&crc);
			if (buffer[frameBufferSize - 2] == framePtr[0]
					&& buffer[frameBufferSize - 1] == framePtr[1]) {
//				os << "CRC ok! \n";
				int frameSize;
				if (frameBufferSize == 5) {
//					os << "Ack recebido... Conferindo Sequence Number... \n";
					frameSize = IEE_802_15_4::FRAME_ACK_SIZE;
					IEE_802_15_4::frame_ack_s
							*frame_ack_ =
									reinterpret_cast<IEE_802_15_4::frame_ack_s*> (buffer);
//					os << "Conferindo! macDSN:" << macDSN
//							<< " SequenceNumber: "
//							<< frame_ack_->sequenceNumber << "\n";
					if (frame_ack_->sequenceNumber == macDSN) {
//						os << "Confere!\n";
						return 1;//success
					}
				} else { //DATA
					frameSize = IEE_802_15_4::FRAME_DATA_SIZE;
					IEE_802_15_4::frame_data_s
							*frame_data_ =
									reinterpret_cast<IEE_802_15_4::frame_data_s*> (buffer);
					if (frame_data_->destinationAddress == myAddress) {
//						os << "Mensagem pra min! \n";
						size = frameBufferSize
								- IEE_802_15_4::FRAME_CHECK_SEQUENCE_SIZE
								- frameSize;

//                                              memcpy(data, (void*)buffer[frameSize], size + 1);
						for (unsigned int var = 0; var < size; ++var) {
							reinterpret_cast<char *>(data)[var] = buffer[frameSize + var];
						}

						if (frame_data_->ackRequest == ACK_REQUEST_ON) {
//							os << "Preciso enviar ACK!sequence:"
//									<< frame_data_->sequenceNumber << "\n";
							_send_ack(frame_data_->sequenceNumber);
						}
                                                return 1;

					} else {
//						os << "Mensagem pra outro! \n";
						return 0;
					}
				}
			} else {
//				os << "CRC fail! \n";
			}
		} else {
//			os << "RX fail! \n";
		}
	}
	_rx_available = true;
	return 0;
}

/***************************************************************
 *
 * PROTOCOL PRIVATE INTERFACE
 *
 ***************************************************************
 * */

int IEE_802_15_4::csma_ca_send(const Address & dst, const Protocol & prot,
		const void *data, unsigned int size) {
	macDSN++;
	os << "csma_ca_send\n";
	NB = 0;
	BE = macMinBE;
	if (_tx_available) {
		if (!slotted) {
			while (NB < macMaxCSMABackoffs) {
				srand(myAddress);//Different seed to any node
				int delay = static_cast<int> (fmod(rand(), (pow(2, BE) - 1))
						* aUnitBackoffPeriod);
				delay = (delay < 1000) ? 1000 : delay;
				os << "delay: " << delay << "\n";
				Alarm::delay(delay);//random(2^BE -1)*Period
				os << "tentativa " << NB << "\n";
				//Clear Channel Assesment
				bool aux = false;
				radio.CCA_measurement(aux);
				if (aux) {
					os << "canal livre, enviando...\n";
					_send_data(dst, prot, data, size);
					if (_ack_needed) {
						os << "enviado, aguardando por ack...\n";
						if (receive(0, 0, 0, 0, 120000l) == 1)//TODO receber por 120ms, e agora?
							return 1;//Success
						aux = false;
						os << "ack nao recebido, reiniciando envio CSMA...\n";
					}
				} /*else {*/
					os << "canal ocupado ou Ack nao recebido\n";
					NB = NB + 1;
					BE = BE + 1;
					if (BE > aMaxBE)
						BE = aMaxBE;
				//}
			}
		} else { //Slotted
			return 0;
		}
	}
	os << "csma_ca_send falhou\n";
	return 0;//Failure
}

int IEE_802_15_4::_send_data(const Address & dst, const Protocol & prot,
		const void *data, unsigned int size) {

	os << "IN > _send\n";
	if (_ack_needed)
		IEE_802_15_4::frame_data.ackRequest = IEE_802_15_4::ACK_REQUEST_ON;
	else
		IEE_802_15_4::frame_data.ackRequest = IEE_802_15_4::ACK_REQUEST_OFF;

	IEE_802_15_4::frame_data.destinationAddress = dst;
	IEE_802_15_4::frame_data.destinationAddressingMode
			= IEE_802_15_4::ADRESSING_MODE_SHORT_ADDRESS;
	IEE_802_15_4::frame_data.sourceAddressingMode
			= IEE_802_15_4::ADRESSING_MODE_SHORT_ADDRESS;
	IEE_802_15_4::frame_data.sourceAddress = myAddress;

	IEE_802_15_4::frame_data.frameType = IEE_802_15_4::FRAME_TYPE_DATA;
	IEE_802_15_4::frame_data.framePending = IEE_802_15_4::FRAME_PENDING_OFF;
	IEE_802_15_4::frame_data.intraPan = IEE_802_15_4::INTRA_PAN_SAME_PAN;
	IEE_802_15_4::frame_data.securityEnable
			= IEE_802_15_4::SECURITY_ENABLED_OFF;

	IEE_802_15_4::frame_data.sequenceNumber = macDSN;//Necessary for ack

	os << "_send -> Montando Frame\n";
	os << "endereco de envio: " << myAddress << "\n";
	int frameSize = FRAME_DATA_SIZE;
	int totalSize = frameSize + size;
	os << "totalSize: " << totalSize << "\n";
	os << "frameSize: " << FRAME_DATA_SIZE << "\n";
	os << "size: " << size << "\n";

	unsigned char *framePtr =
			reinterpret_cast<unsigned char*> (&(IEE_802_15_4::frame_data));
	for (int var = 0; var < frameSize; ++var) {
		buffer[var] = framePtr[var];
	}

	const char *data2 = static_cast<const char*> (data);
	int var2 = 0;
	if (totalSize < IEE_802_15_4::MAX_FRAME_FULL_SIZE) {
		for (int var = frameSize; var < totalSize; ++var, ++var2) {
			buffer[var] = data2[var2];
		}
	}

	os << "_send -> CRC \n";
	unsigned short crc =
			CRC::crc16(reinterpret_cast<char*> (buffer), totalSize);
	unsigned char *framePtr2 = reinterpret_cast<unsigned char*> (&crc);
	totalSize = totalSize + IEE_802_15_4::FRAME_CHECK_SEQUENCE_SIZE;
	buffer[totalSize - 2] = framePtr2[0];
	buffer[totalSize - 1] = framePtr2[1];

	if (totalSize < IEE_802_15_4::MAX_FRAME_FULL_SIZE) {
		os << "_send -> Radio.SEND \n";
		radio.send(buffer, totalSize);
		os << "_send -> Sent. \n";
	}

	return 0;
}

int IEE_802_15_4::_send_ack(int senderMacDSN) {

	IEE_802_15_4::frame_ack.sequenceNumber = senderMacDSN;
	IEE_802_15_4::frame_ack.frameType = IEE_802_15_4::FRAME_TYPE_ACK;
	IEE_802_15_4::frame_ack.securityEnable = IEE_802_15_4::SECURITY_ENABLED_OFF;
	IEE_802_15_4::frame_ack.framePending = IEE_802_15_4::FRAME_PENDING_OFF;
	IEE_802_15_4::frame_ack.ackRequest = IEE_802_15_4::ACK_REQUEST_OFF;
	IEE_802_15_4::frame_ack.intraPan = IEE_802_15_4::INTRA_PAN_SAME_PAN;
	IEE_802_15_4::frame_ack.destinationAddressingMode
			= IEE_802_15_4::ADRESSING_MODE_SHORT_ADDRESS;
	IEE_802_15_4::frame_ack.sourceAddressingMode
			= IEE_802_15_4::ADRESSING_MODE_SHORT_ADDRESS;
	int totalFrameSize = IEE_802_15_4::FRAME_ACK_SIZE
			+ IEE_802_15_4::FRAME_CHECK_SEQUENCE_SIZE;
	unsigned char ackBuffer[totalFrameSize];
	unsigned char *framePtr =
			reinterpret_cast<unsigned char*> (&(IEE_802_15_4::frame_ack));
	for (int var = 0; var < IEE_802_15_4::FRAME_ACK_SIZE; ++var) {
		ackBuffer[var] = framePtr[var];
	}

	os << "Pacote ACK montado. \n";
	unsigned short crc = CRC::crc16(reinterpret_cast<char*> (ackBuffer),
			IEE_802_15_4::FRAME_ACK_SIZE);
	unsigned char *framePtr2 = reinterpret_cast<unsigned char*> (&crc);
	ackBuffer[totalFrameSize - 2] = framePtr2[0];
	ackBuffer[totalFrameSize - 1] = framePtr2[1];

	os << "Enviando Ack. \n";
	radio.send(ackBuffer, totalFrameSize);
	os << "Ack Enviado. \n";

}

volatile bool IEE_802_15_4::_tx_available = true;
volatile bool IEE_802_15_4::_rx_available = false;

Transceiver IEE_802_15_4::radio;

//Variables of CSMA-CA
bool IEE_802_15_4::slotted = false; //TODO Colocar tudo isso em constants
int IEE_802_15_4::aUnitBackoffPeriod = 20000;//FIXME 20 value confirmed, but is to low for EPOS
int IEE_802_15_4::NB = 0;
int IEE_802_15_4::BE = 0;
int IEE_802_15_4::macMinBE = 3;
int IEE_802_15_4::aMaxBE = 5;
int IEE_802_15_4::macMaxCSMABackoffs = 8;//FIXME confirm this value
unsigned char IEE_802_15_4::macDSN;//This is inittiated with a random value


unsigned char IEE_802_15_4::buffer[IEE_802_15_4::MAX_FRAME_FULL_SIZE];

//Configs
bool IEE_802_15_4::_im_coordinator = false;

//volatile Frame IEE_802_15_4::_tx_frame;


}

