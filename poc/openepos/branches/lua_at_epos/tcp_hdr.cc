#include <tcp.h>

__BEGIN_SYS

void TCP::Header::init()
{
	memset(this,0,20); 
}

void TCP::Header::init(u32 seq,u32 ack)   
{
	init();
	seq_num(seq);
	ack_num(ack);
}

// checksum veryfication and calculation

TCP::u16 TCP::Header::_checksum(IP::Address &src,IP::Address &dst,unsigned len)
{
	len += size();

	Pseudo_Header phdr;
	phdr.src_ip = src;
	phdr.dst_ip = dst;
	phdr.zero = 0;
	phdr.protocol = ID_TCP;
	phdr.length = CPU::htons(len);
	
	unsigned int sum = 0;

	u8 * ptr = reinterpret_cast<u8 *>(this);
	unsigned int i;
	
	for(i = 0; i < len-1; i+=2)
		sum += (((u16)(ptr[i+1]) & 0x00FF) << 8) | ptr[i];
	if(len & 1) {
		sum += ptr[len-1];
	}

	ptr = reinterpret_cast<u8 *>(&phdr);
	for(i = 0;i < sizeof(Pseudo_Header); i+=2)
		sum += (((u16)(ptr[i+1]) & 0x00FF) << 8) | ptr[i];

	while(sum >> 16)
		sum = (sum & 0xffff) + (sum >> 16);

	return ~sum;
}
void TCP::Header::checksum(IP::Address &src,IP::Address &dst,unsigned len)
{
	_chksum = _checksum(src,dst,len);
}

bool TCP::Header::validate_checksum(IP::Address &src,IP::Address &dst,unsigned len)
{
	u16 tmp = _checksum(src,dst,len);
	return tmp == 0x0000;
}

__END_SYS
