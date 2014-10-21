// EPOS-- MC13224V Flash Mediator Declarations

#include <machine.h>
#include <mach/mc13224v/flash.h>

__BEGIN_SYS

MC13224V_Flash::nvm_detect_func MC13224V_Flash::nvm_detect = reinterpret_cast<nvm_detect_func>(reinterpret_cast<void *>(NVM_DETECT_ADDR));

MC13224V_Flash::nvm_read_func MC13224V_Flash::nvm_read = reinterpret_cast<nvm_read_func>(reinterpret_cast<void *>(NVM_READ_ADDR));

MC13224V_Flash::nvm_setsvar_func MC13224V_Flash::nvm_setsvar = reinterpret_cast<nvm_setsvar_func>(reinterpret_cast<void *>(NVM_SETSVAR_ADDR));

__END_SYS

