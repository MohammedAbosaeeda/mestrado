// EPOS UDP Protocol Implementation

#include <udp.h>

__BEGIN_SYS

// Class attributes
UDP::List UDP::_received;


// Methods
int UDP::send(const Port & from, const Address & to, const void * d, unsigned int s)
{
    const unsigned char * data = reinterpret_cast<const unsigned char *>(d);
    unsigned int size = (s > sizeof(Data)) ? sizeof(Data) : s;

    db<UDP>(TRC) << "UDP::send(f=" << from << ",t=" << to << ",d=" << data << ",s=" << size << ")" << endl;

    Buffer * pool = IP::alloc(to.ip(), IP::UDP, sizeof(Header), size);
    if(!pool)
        return 0;

    unsigned int headers = sizeof(Header);
    for(Buffer::Element * el = pool->link(); el; el = el->next()) {
        Buffer * buf = el->object();
        Packet * packet = buf->frame()->data<Packet>();

        db<UDP>(INF) << "UDP::send:buf=" << buf << " => " << *buf<< endl;

        if(el == pool->link()) {
            Message * message = packet->data<Message>();
            new(packet->data<void>()) Header(from, to.port(), size);
            message->sum(packet->from(), packet->to(), data);
            memcpy(message->data<void>(), data, buf->size() - sizeof(Header) - sizeof(IP::Header));
            data += buf->size() - sizeof(Header) - sizeof(IP::Header);

            db<UDP>(INF) << "UDP::send:msg=" << message << " => " << *message << endl;
        } else {
            memcpy(packet->data<void>(), data, buf->size() - sizeof(IP::Header));
            data += buf->size() - sizeof(IP::Header);
        }

        headers += sizeof(IP::Header);
    }

    return IP::send(pool) - headers; // implicitly releases the pool
}


int UDP::receive(Buffer * pool, void * d, unsigned int s)
{
    unsigned char * data = reinterpret_cast<unsigned char *>(d);

    db<UDP>(TRC) << "UDP::receive(buf=" << pool << ",d=" << d << ",s=" << s << ")" << endl;

    Buffer::Element * head = pool->link();
    Packet * packet = head->object()->frame()->data<Packet>();
    Message * message = packet->data<Message>();
    unsigned int size = 0;

    for(Buffer::Element * el = head; el && (size <= s); el = el->next()) {
        Buffer * buf = el->object();

        db<UDP>(INF) << "UDP::receive:buf=" << buf << " => " << *buf << endl;

        packet = buf->frame()->data<Packet>();

        unsigned int len = buf->size() - sizeof(IP::Header);
        if(el == head) {
            len -= sizeof(Header);
            memcpy(data, message->data<void>(), len);

            db<UDP>(INF) << "UDP::receive:msg=" << message << " => " << *message << endl;
        } else
            memcpy(data, packet->data<void>(), len);

        db<UDP>(INF) << "UDP::receive:len=" << len << endl;

        data += len;
        size += len;
    }

    pool->nic()->free(pool);

    if(!message->check()) {
        db<UDP>(WRN) << "UDP::update: wrong message checksum!" << endl;
        size = 0;
    }

    return size;
}


void UDP::update(IP::Observed * ip, int port, Buffer * pool)
{
    db<UDP>(TRC) << "UDP::update(buf=" << pool << ")" << endl;

    Packet * packet = pool->frame()->data<Packet>();
    Message * message = packet->data<Message>();

    db<UDP>(INF) << "UDP::update:msg=" << message << " => " << *message << endl;

    if(!notify(message->to(), pool))
        pool->nic()->free(pool);
}


void UDP::Message::sum(const IP::Address & from, const IP::Address & to, const void * data)
{
    _checksum = 0;
    if(Traits<UDP>::checksum) {
        Pseudo_Header pseudo(from, to, length());
        unsigned long sum = 0;

        const unsigned char * ptr = reinterpret_cast<const unsigned char *>(&pseudo);
        for(unsigned int i = 0; i < sizeof(Pseudo_Header); i += 2)
            sum += (ptr[i] << 8) | ptr[i+1];

        ptr = reinterpret_cast<const unsigned char *>(header());
        for(unsigned int i = 0; i < sizeof(Header); i += 2)
            sum += (ptr[i] << 8) | ptr[i+1];

        ptr = reinterpret_cast<const unsigned char *>(data);
        unsigned int size = length() - sizeof(Header);
        for(unsigned int i = 0; i < size - 1; i += 2)
            sum += (ptr[i] << 8) | ptr[i+1];
        if(size & 1)
            sum += ptr[size - 1];

        while(sum >> 16)
            sum = (sum & 0xffff) + (sum >> 16);

        _checksum = htons(~sum);
    }
}

__END_SYS

