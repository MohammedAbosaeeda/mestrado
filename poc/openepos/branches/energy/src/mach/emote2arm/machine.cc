// OpenEPOS EMote2ARM Machine Mediator Implementation

#include <machine.h>
#include <gpio_pin.h>

__BEGIN_SYS

void EMote2ARM::check_flash_erase() {

    GPIO_Pin pin(Traits<Machine>::flash_erase_checking_pin);

    if(pin.get()) // read pin state
    {
        unbrick();
    }
}

void EMote2ARM::unbrick() {
    OStream cout;

    cout << "This will reset the processor to original firmware.\n\r";

    EMote2ARM_Buck_Regulator::NVM_1P8V_enable();

    EMote2ARM_Flash::nvmErr_t err;
    EMote2ARM_Flash::nvmType_t type = EMote2ARM_Flash::gNvmType_NoNvm_c;
    err = EMote2ARM_Flash::nvm_detect(EMote2ARM_Flash::gNvmInternalInterface_c, &type);
    if(err != 0) {
        cout << "Failed to detect NVM! (error = " << err << ")\n\r";
    }
    else if(type == Flash::gNvmType_NoNvm_c) {
        cout << "No NVM detected!\n\r";
    }
    else {
        cout << "Found a memory type " << type << ". Trying to erase...\n\r";

        err = Flash::nvm_erase(Flash::gNvmInternalInterface_c, type, 0x1);

        if(err == 0) {
            cout << "Memory reset complete!\n\r";
        }
        else {
            cout << "Oops... we found an error (code = " << err << ")\n\r";
        }
    }

    while (true);
}

__END_SYS
