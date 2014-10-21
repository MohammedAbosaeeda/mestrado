// EPOS-- MC13224V Default Application

#include <machine.h>

__USING_SYS

typedef IO_Map<Machine> IO;

void wait() {
    for(unsigned int i = 0; i < 0x000fffff; i++);
}

void turn_leds_off() {
    unsigned int *GPIO_BASE = (unsigned int*)0x80000000;
    *GPIO_BASE = *GPIO_BASE & ~(1 << 23) & ~(1 << 24) & ~(1 << 25);
}

void turn_on_led1() {
    unsigned int *GPIO_BASE = (unsigned int*)0x80000000;
    *GPIO_BASE = 1 << 23;
}

void turn_on_led2() {
    unsigned int *GPIO_BASE = (unsigned int*)0x80000000;
    *GPIO_BASE = 1 << 24;
}

void turn_on_led3() {
    unsigned int *GPIO_BASE = (unsigned int*)0x80000000;
    *GPIO_BASE = 1 << 25;
}

inline void gpio_pad_dir_set(volatile unsigned long long int data) {
    CPU::out32(IO::GPIO_PAD_DIR_SET0, data & 0xffffffff);
    CPU::out32(IO::GPIO_PAD_DIR_SET1, data >> 32);
}

#define PAYLOAD_LEN 17
void fill_packet(volatile Transceiver::packet_t *p) {
    static char c = 0;
    p->length = PAYLOAD_LEN;
    p->offset = 0;

    p->data[0] = 'T';
    p->data[1] = 'x';
    p->data[2] = ' ';
    p->data[3] = 't';
    p->data[4] = 'e';
    p->data[5] = 's';
    p->data[6] = 't';
    p->data[7] = ' ';
    p->data[8] = 'H';
    p->data[9] = 'E';
    p->data[10] = 'L';
    p->data[11] = 'L';
    p->data[12] = 'W';
    p->data[13] = 'O';
    p->data[14] = 'R';
    p->data[15] = c;
    p->data[16] = 'D';

    c++;

    /* acks get treated differently, even in promiscuous mode */
    /* setting the third bit makes sure that we never send an ack */
    /* or any valid 802.15.4-2006 packet */
    //	p->data[0] |= (1 << 3); 
}

void print_packet(volatile Transceiver::packet_t *p) { 
    OStream cout;

    if (p) {
	cout << "len " << p->length << "\n";		

//	for (int i = 0; i < p->length; i++)
	int i = 16;
	    cout << i << " " << (int) p->data[i] << "\n";

	cout << "\n";
    }
}

int sink() {
    OStream cout;
    cout << "Sink\n";
    turn_on_led3();

    Transceiver * t;
    volatile Transceiver::packet_t * p;

    Transceiver::maca_init();

    while (true) {
	t->check_maca();

	if (p = t->rx_packet()) {
	    cout << "rx\n";
	    print_packet(p);
	    t->free_packet(p);
	}
    }

    return 0;
}

int source() {
    OStream cout;
    cout << "Source\n";
    turn_on_led1();

    Transceiver * t;
    volatile Transceiver::packet_t * p;

    Transceiver::maca_init();

    CPU::out32(IO::GPIO_FUNC_SEL2, 0x01 << ((44-16*2)*2));
    gpio_pad_dir_set(1ULL << 44);

    while (true) {
	t->check_maca();

	p = t->get_free_packet();

	if (p) {
	    cout << "got free packet\n";
	    fill_packet(p);

	    t->tx_packet(p);
	    cout << "tx\n";

	    for (int i = 0; i < 100000; i++);
	    for (int i = 0; i < 100000; i++);
	    for (int i = 0; i < 100000; i++);
	}
    }

    return 0;
}

int main() {
    turn_on_led1();
    wait();
    turn_on_led2();
    wait();
    turn_on_led3();
    wait();
    turn_leds_off();
    wait();

//    sink();
    source();
}

