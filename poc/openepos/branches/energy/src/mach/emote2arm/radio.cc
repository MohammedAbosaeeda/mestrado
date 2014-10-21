// OpenEPOS EMote2ARM_Radio Mediator Implementation

#include <radio.h>
#include <machine.h>
#include <mach/emote2arm/buck_regulator.h>
#include <system/kmalloc.h>

__BEGIN_SYS

EMote2ARM_Transceiver * Radio_Wrapper::device = 0;

typedef IO_Map<Machine> IO;

void Radio_Wrapper::init() {
    device = new(kmalloc(sizeof(EMote2ARM_Transceiver))) EMote2ARM_Transceiver();

    CPU::out32(IO::CRM_SYS_CNTL, 0x00000001);
    CPU::out32(IO::CRM_VREG_CNTL, 0x00000f5c);
    for (volatile unsigned int i = 0; i < 0x161a8; i++) { continue; }

    EMote2ARM_Transceiver::maca_init();

    device->set_channel(0); /* 802.15.4 channel 11 */
    device->set_power(0x12); /* 4.5dBm */
}

void Radio_Wrapper::set_event_handler(EMote2ARM_Transceiver::event_handler * handler) {
    device->set_event_handler(handler);
}

int Radio_Wrapper::send(unsigned char * data, unsigned int size) {
    volatile EMote2ARM_Transceiver::packet_t * p;

    do {
        p = device->get_free_packet();
    } while (!p);

    device->fill_packet(p, data, size);
    device->tx_packet(p);
    device->post_tx();

    return size;
}

int Radio_Wrapper::receive(unsigned char * data) {
    volatile EMote2ARM_Transceiver::packet_t * p;
    do {
        p = device->rx_packet();
    } while (!p);

    for (int i = 0; i < p->length; i++) {
        data[i] = p->data[i + 1];
    }

    int size = p->length;
    device->free_packet(p);

    return size;
}

void Radio_Wrapper::off() {
    device->off();
}

void Radio_Wrapper::listen() {
    device->post_receive();
}

void Radio_Wrapper::reset() {
    device->reset_maca();
}

bool Radio_Wrapper::cca() {
    return device->cca_measurement();
}

unsigned int Radio_Wrapper::lqi() {
    return device->get_lqi();
}

unsigned int Radio_Wrapper::rssi() {
    return 0;
}

__END_SYS

