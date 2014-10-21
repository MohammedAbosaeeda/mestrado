// EPOS-- MC13224V Flash Mediator Declarations

#ifndef __mc13224v_flash_h
#define __mc13224v_flash_h

__BEGIN_SYS

class MC13224V_Flash
{
private:
    static const unsigned int NVM_DETECT_ADDR  = 0x00006CB9;
    static const unsigned int NVM_READ_ADDR    = 0x00006D69;
    static const unsigned int NVM_SETSVAR_ADDR = 0x00007085;

    MC13224V_Flash() {}
    ~MC13224V_Flash() {}

public:
    typedef enum {
	gNvmType_NoNvm_c,
	gNvmType_SST_c,
	gNvmType_ST_c,
	gNvmType_ATM_c,
	gNvmType_Max_c
    } nvmType_t;

    typedef enum {
	gNvmErrNoError_c = 0,
	gNvmErrInvalidInterface_c,
	gNvmErrInvalidNvmType_c,
	gNvmErrInvalidPointer_c,
	gNvmErrWriteProtect_c,
	gNvmErrVerifyError_c,
	gNvmErrAddressSpaceOverflow_c,
	gNvmErrBlankCheckError_c,
	gNvmErrRestrictedArea_c,
	gNvmErrMaxError_c
    } nvmErr_t;

    typedef enum {
	gNvmInternalInterface_c,
	gNvmExternalInterface_c,
	gNvmInterfaceMax_c
    } nvmInterface_t;

    typedef nvmErr_t (*nvm_detect_func)(nvmInterface_t nvmInterface,nvmType_t* pNvmType);
    static nvm_detect_func nvm_detect;

    typedef nvmErr_t (*nvm_read_func)(nvmInterface_t nvmInterface , nvmType_t nvmType , void *pDest, unsigned int address, unsigned int numBytes);
    static nvm_read_func nvm_read;

    typedef void (*nvm_setsvar_func)(unsigned int zero_for_awesome);
    static nvm_setsvar_func nvm_setsvar;
};

__END_SYS

#endif

