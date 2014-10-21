// OpenEPOS EMote2ARM_ASM_Controller Test Program

#include <mach/emote2arm/asm_controller.h>

__BEGIN_SYS

EMote2ARM_ASM_Controller *EMote2ARM_ASM_Controller::_instance = 0;

EMote2ARM_ASM_Controller *EMote2ARM_ASM_Controller::get_instance() {
	if (_instance == 0)
		_instance = new EMote2ARM_ASM_Controller();

	return _instance;
}

void EMote2ARM_ASM_Controller::initialize() {
	// turn the ASM module on
	CPU::out32(IO::ASM_CONTROL1, (1 << ON));
	// enable self-test mode
	CPU::out32(IO::ASM_CONTROL1,
			CPU::in32(IO::ASM_CONTROL1) | (1 << SELF_TEST));
	// initiates self-test
	CPU::out32(IO::ASM_CONTROL0, CPU::in32(IO::ASM_CONTROL0) | (1 << START));

	// test duration is 3330 clocks
	for (unsigned int i = 0; i < 3330; i++)
		ASMV("nop");

	// check test result
	if (bit_is_set(CPU::in32(IO::ASM_STATUS), TEST_PASS))
		db<EMote2ARM_ASM_Controller>(TRC)
				<< "EMote2ARM_ASM_Controller::initialize - self-test passed\n";
	else
		db<EMote2ARM_ASM_Controller>(ERR)
				<< "EMote2ARM_ASM_Controller::initialize - self-test error\n";

	// disable self-test mode
	CPU::out32(IO::ASM_CONTROL1,
			CPU::in32(IO::ASM_CONTROL1) & ~(1 << SELF_TEST));
	// changes module mode from "boot" to "normal"
	CPU::out32(IO::ASM_CONTROL1,
			CPU::in32(IO::ASM_CONTROL1) | (1 << NORMAL_MODE));
	// data will be encrypted
	CPU::out32(IO::ASM_CONTROL1, CPU::in32(IO::ASM_CONTROL1) & ~(1 << BYPASS));
	// clear all memory in ASM
	CPU::out32(IO::ASM_CONTROL0, CPU::in32(IO::ASM_CONTROL0) | (1 << CLEAR));
	// clear the IRQ
	CPU::out32(IO::ASM_CONTROL0,
			CPU::in32(IO::ASM_CONTROL0) | (1 << CLEAR_IRQ));
}

void EMote2ARM_ASM_Controller::set_key(const char *key) {
	//Key must have 128 bits (16 bytes)
	db<EMote2ARM_ASM_Controller>(TRC)
			<< "EMote2ARM_ASM_Controller::set_key - Key = " << key << "\n";

	unsigned int aux = *((unsigned int *) &key[0]);
	CPU::out32(IO::ASM_KEY0, aux);

	aux = *((unsigned int *) &key[4]);
	CPU::out32(IO::ASM_KEY1, aux);

	aux = *((unsigned int *) &key[8]);
	CPU::out32(IO::ASM_KEY2, aux);

	aux = *((unsigned int *) &key[12]);
	CPU::out32(IO::ASM_KEY3, aux);
}

void EMote2ARM_ASM_Controller::set_data(const char *data) {
	//Data must have 128 bits
	db<EMote2ARM_ASM_Controller>(TRC)
			<< "EMote2ARM_ASM_Controller::set_data - Data = " << data << "\n";

	unsigned int aux = *((unsigned int *) &data[0]);
	CPU::out32(IO::ASM_DATA0, aux);

	aux = *((unsigned int *) &data[4]);
	CPU::out32(IO::ASM_DATA1, aux);

	aux = *((unsigned int *) &data[8]);
	CPU::out32(IO::ASM_DATA2, aux);

	aux = *((unsigned int *) &data[12]);
	CPU::out32(IO::ASM_DATA3, aux);
}

void EMote2ARM_ASM_Controller::set_counter() {
	CPU::out32(IO::ASM_CTR0, 0x33333333);
	CPU::out32(IO::ASM_CTR1, 0x22222222);
	CPU::out32(IO::ASM_CTR2, 0x11111111);
	CPU::out32(IO::ASM_CTR3, 0x00000000);
}

bool EMote2ARM_ASM_Controller::encrypt(char *encrypted_data, char *mac) {
	//Starts
	CPU::out32(IO::ASM_CONTROL0, CPU::in32(IO::ASM_CONTROL0) | (1 << START));

	//Waits for completion
	while (!(bit_is_set(CPU::in32(IO::ASM_STATUS), DONE)))
		continue;

	db<EMote2ARM_ASM_Controller>(TRC)
			<< "EMote2ARM_ASM_Controller::encrypt - Encryption completed\n";

	*((unsigned int *) &encrypted_data[0]) = CPU::in32(IO::ASM_CTR0_RESULT);
	*((unsigned int *) &encrypted_data[4]) = CPU::in32(IO::ASM_CTR1_RESULT);
	*((unsigned int *) &encrypted_data[8]) = CPU::in32(IO::ASM_CTR2_RESULT);
	*((unsigned int *) &encrypted_data[12]) = CPU::in32(IO::ASM_CTR3_RESULT);

	if (mac) {
		*((unsigned int *) &mac[0]) = CPU::in32(IO::ASM_CBC0_RESULT);
		*((unsigned int *) &mac[4]) = CPU::in32(IO::ASM_CBC1_RESULT);
		*((unsigned int *) &mac[8]) = CPU::in32(IO::ASM_CBC2_RESULT);
		*((unsigned int *) &mac[12]) = CPU::in32(IO::ASM_CBC3_RESULT);
	}

	// clear all ASM_CBC_RESULT
	CPU::out32(IO::ASM_CONTROL0, CPU::in32(IO::ASM_CONTROL0) | (1 << CLEAR));
	return true;
}

bool EMote2ARM_ASM_Controller::check_mac(const char *mac) {
	//Starts
	CPU::out32(IO::ASM_CONTROL0, CPU::in32(IO::ASM_CONTROL0) | (1 << START));

	//Waits for completion
	while (!(bit_is_set(CPU::in32(IO::ASM_STATUS), DONE)))
		continue;

	bool result = ((*((unsigned int *) &mac[0])
			== CPU::in32(IO::ASM_CBC0_RESULT))
			&& (*((unsigned int *) &mac[4]) == CPU::in32(IO::ASM_CBC1_RESULT))
			&& (*((unsigned int *) &mac[8]) == CPU::in32(IO::ASM_CBC2_RESULT))
			&& (*((unsigned int *) &mac[12]) == CPU::in32(IO::ASM_CBC3_RESULT)));

	db<EMote2ARM_ASM_Controller>(TRC)
			<< "EMote2ARM_ASM_Controller::check_mac - Check completed ("
			<< result << ")\n";

	// clear all ASM_CBC_RESULT
	CPU::out32(IO::ASM_CONTROL0, CPU::in32(IO::ASM_CONTROL0) | (1 << CLEAR));
	return result;
}

__END_SYS

