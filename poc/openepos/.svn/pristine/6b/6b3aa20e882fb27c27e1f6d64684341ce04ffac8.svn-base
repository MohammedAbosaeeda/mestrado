// OpenEPOS EMote2ARM_Flash Mediator Initialization

#include <mach/emote2arm/flash.h>

__BEGIN_SYS

void EMote2ARM_Flash::init()
{
    db<Init, EMote2ARM_Flash>(TRC) << "EMote2ARM_Flash::init()\n";

    power(FULL);

    _error = nvm_detect(_interface, &_type);
    if(_error != 0) {
        db<Init, EMote2ARM_Flash>(ERR) << "EMote2ARM_Flash: Failed to detect NVM! (error = " << _error << ")\n\r";
    }
    else if(_type == Flash::gNvmType_NoNvm_c) {
        db<Init, EMote2ARM_Flash>(WRN) << "EMote2ARM_Flash: No NVM detected!\n\r";
    }
    else {
        db<Init, EMote2ARM_Flash>(INF) << "EMote2ARM_Flash: Found a memory type " << _type << "\n\r";
        if(_type == Flash::gNvmType_SST_c) _block_size = 4 * 1024;
        else _block_size = 32 * 1024;
    }

    //TODO: realy needed?
    unlock_last_page();
}

__END_SYS
