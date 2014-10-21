// EPOS-- MC13224v Radio (Transceiver) NIC Mediator Implementation

#include <utility/malloc.h>
#include <mach/mc13224v/machine.h>
#include <mach/mc13224v/radio.h>

__BEGIN_SYS

Transceiver Radio::device;

void Radio::init() {
    Transceiver::maca_init();

    device.set_channel(0);	/* 802.15.4 channel 11 */
    device.set_power(0x12);	/* 4.5dBm */
}

void Radio::set_event_handler(Transceiver::event_handler * handler) {
    device.set_event_handler(handler);
}

int Radio::send(unsigned char * data, unsigned int size) {
    volatile Transceiver::packet_t * p;

    do {
	p = device.get_free_packet();
    } while (!p);

    device.fill_packet(p, data, size);
    device.tx_packet(p);

    return size;
}

int Radio::receive(unsigned char * data) {
    volatile Transceiver::packet_t * p;
    do {
	device.check_maca();

	p = device.rx_packet();
    } while (!p);

    for (int i = 0; i < p->length; i++) {
	data[i] = p->data[i + 1];
    }
    int size = p->length;
    device.free_packet(p);

    return size;
}

void Radio::off() {
//    device.reset_state_machine();
}

void Radio::listen() {
    Transceiver::event_handler * ev = device.get_event_handler();
    ev(Transceiver::SFD_DETECTED);
}

void Radio::reset() {
    device.reset_maca();
}

bool Radio::cca() {
//    return device.cca_measurement(AT86RF230::ENERGY_ABOVE_THRESHOLD, 0);
}

__END_SYS

