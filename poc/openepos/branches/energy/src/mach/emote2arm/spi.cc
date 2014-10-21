// OpenEPOS EMote2ARM_SPI Mediator Implementation

#include <machine.h>
#include <spi.h>

__BEGIN_SYS

EMote2ARM_SPI::EMote2ARM_SPI(spiConfig_t config) :
		_config(config) {
	CPU::out32(Machine::IO::GPIO_FUNC_SEL0,
			CPU::in32(Machine::IO::GPIO_FUNC_SEL0) | (1 << 8) //SPI_SS este é o CS?
					| (1 << 10) //SPI_MISO
					| (1 << 12) //SPI_MOSI
					| (1 << 14) //SPI_SCK
					);

	SPI_Open();
	SPI_SetConfig(&_config);
}

EMote2ARM_SPI::spiErr_t EMote2ARM_SPI::writeRegisterSync(unsigned int data) {

	return SPI_WriteSync(data);
}

unsigned int EMote2ARM_SPI::readRegisterSync() {

	unsigned int data[] = { 0 };
	//unsigned int address = (0x80 | registerAddress) << (32 -8);
	//   cout << "Address: " << hex <<address << endl;
	//  print_register();
	//e = SPI_WriteSync(address);
	//  SPI_WriteSync(address);
	// cout << "error on Write address to read: " << e << endl;
	//e = SPI::SPI_ReadSync(data);
	//  if (e) {
	//    cout << "Read Error " << e << endl;
// }
	//   cout << hex << "Values: {" << data[0] << ", " << data[1] << ", " << data[2] << "}" << endl;

	//  cout << "---- done readRegister" << endl;
	SPI_ReadSync(data);
	return data[0];
}

EMote2ARM_SPI::SPI_Open_func EMote2ARM_SPI::SPI_Open =
		reinterpret_cast<EMote2ARM_SPI::SPI_Open_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_Open_ADDR));

EMote2ARM_SPI::SPI_Close_func EMote2ARM_SPI::SPI_Close =
		reinterpret_cast<EMote2ARM_SPI::SPI_Close_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_Close_ADDR));

EMote2ARM_SPI::SPI_SetConfig_func EMote2ARM_SPI::SPI_SetConfig =
		reinterpret_cast<EMote2ARM_SPI::SPI_SetConfig_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_SetConfig_ADDR));

EMote2ARM_SPI::SPI_GetConfig_func EMote2ARM_SPI::SPI_GetConfig =
		reinterpret_cast<EMote2ARM_SPI::SPI_GetConfig_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_GetConfig_ADDR));

EMote2ARM_SPI::SPI_WriteSync_func EMote2ARM_SPI::SPI_WriteSync =
		reinterpret_cast<EMote2ARM_SPI::SPI_WriteSync_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_WriteSync_ADDR));

EMote2ARM_SPI::SPI_ReadSync_func EMote2ARM_SPI::SPI_ReadSync =
		reinterpret_cast<EMote2ARM_SPI::SPI_ReadSync_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_ReadSync_ADDR));

EMote2ARM_SPI::SPI_SetTxAsync_func EMote2ARM_SPI::SPI_SetTxAsync =
		reinterpret_cast<EMote2ARM_SPI::SPI_SetTxAsync_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_SetTxAsync_ADDR));

EMote2ARM_SPI::SPI_GetRxAsync_func EMote2ARM_SPI::SPI_GetRxAsync =
		reinterpret_cast<EMote2ARM_SPI::SPI_GetRxAsync_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_GetRxAsync_ADDR));

EMote2ARM_SPI::SPI_StartAsync_func EMote2ARM_SPI::SPI_StartAsync =
		reinterpret_cast<EMote2ARM_SPI::SPI_StartAsync_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_StartAsync_ADDR));

EMote2ARM_SPI::SPI_Abort_func EMote2ARM_SPI::SPI_Abort =
		reinterpret_cast<EMote2ARM_SPI::SPI_Abort_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_Abort_ADDR));

EMote2ARM_SPI::SPI_SetCallback_func EMote2ARM_SPI::SPI_SetCallback =
		reinterpret_cast<EMote2ARM_SPI::SPI_SetCallback_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_SetCallback_ADDR));

EMote2ARM_SPI::SPI_GetStatus_func EMote2ARM_SPI::SPI_GetStatus =
		reinterpret_cast<EMote2ARM_SPI::SPI_GetStatus_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_GetStatus_ADDR));

EMote2ARM_SPI::SPI_ISR_func EMote2ARM_SPI::SPI_ISR =
		reinterpret_cast<EMote2ARM_SPI::SPI_ISR_func>(reinterpret_cast<void *>(EMote2ARM_SPI::SPI_ISR_ADDR));

__END_SYS
