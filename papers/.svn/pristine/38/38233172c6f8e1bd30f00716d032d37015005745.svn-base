#include <alarm.h>
#include <machine.h>
#include <utility/ostream.h>
#include <utility/random.h>

__USING_SYS

/**
 *  CPU::out8(Machine::IO::PORTB, 0x20) red led
 *  CPU::out8(Machine::IO::PORTB, 0x40) yellow led
 *  CPU::out8(Machine::IO::PORTB, 0x80) green led
 */

const int MAX = 50;

OStream out;

class Unslotted_CSMA_Contention {
public:
	static void execute() {

		out << "CMAC_States::Unslotted_CSMA_Contention - Checking channel\n";

		unsigned int nb = 0;
		unsigned int be = MIN_BE;
		while (nb < MAX_BACKOFFS) {

			out << "be: " << be << " nb: " << nb << "\n";

			//delay = random(2^BE -1)*Period in ms
			unsigned long random = Pseudo_Random::random();
			out << "Random: " << random << "\n";

			unsigned long doisNAbe = 2 << (be-1);

			out << "2^be: " << doisNAbe << "\n";

			unsigned long delay = static_cast<unsigned long> ((random % doisNAbe) * UNIT_BACKOFF_PERIOD);

			out << "CMAC_States::Unslotted_CSMA_Contention - Delay = " << delay << "\n";

			//CMAC::alarm_busy_delay(delay);

			//Clear Channel Assesment(CCA)
			bool aux = false;
			//CMAC::radio.CCA_measurement(aux);
			if (aux) {
			//	db<CMAC>(TRC) << "CMAC_States::Unslotted_CSMA_Contention - CHANNEL_IDLE\n";
				return;
			}
			nb = nb + 1;
			be = be + 1;
			if (be > MAX_BE)
				be = MAX_BE;
		}

		out << "CMAC_States::Unslotted_CSMA_Contention - CHANNEL_BUSY\n";
		//return CMAC::CHANNEL_BUSY;
	}

private:
	enum{
		MIN_BE = 3,
		MAX_BE = 5,
		MAX_BACKOFFS = 8,
		UNIT_BACKOFF_PERIOD = 15 //ms
	};
};

int main() {

	/*
	int rand = 110 % MAX;

	out << rand << "\n";


	for (int i = 0; i < 10; ++i) {
		out << Pseudo_Random::random(25) << "\n";
	}

	out << "Seed = 0\n";
	Pseudo_Random::seed(0);

	for (int i = 0; i < 10; ++i) {
		out << Pseudo_Random::random(25) << "\n";
	}

	out << "Seed = 1000\n";
	Pseudo_Random::seed(1000);

	for (int i = 0; i < 10; ++i) {
		out << Pseudo_Random::random(25) << "\n";
	}
	*/

	Pseudo_Random::seed(0);

	for (int i = 0; i < 5; ++i) {
		Unslotted_CSMA_Contention::execute();
	}



/*
    int i, j;

    while (1) {
        for (i = 5; i < 8; i++) {
            CPU::out8(Machine::IO::PORTB, (1 << i));
            Alarm::delay(500000);
        }
    }
*/
    return 0;
}
