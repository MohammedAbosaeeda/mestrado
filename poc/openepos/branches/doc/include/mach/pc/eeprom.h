/*! @file
 *  @brief EPOS PC EEPROM Mediator Common Declarations
 *
 *  CVS Log for this file:
 *  \verbinclude include/mach/pc/eeprom_h.log
 */
#ifndef __pc_eeprom_h
#define __pc_eeprom_h

#include <eeprom.h>
#include "rtc.h"

__BEGIN_SYS

class PC_EEPROM: public EEPROM_Common, private MC146818
{
public:
    typedef EEPROM_Common::Address Address;

public:
    PC_EEPROM() {};

    unsigned char read(const Address & a) { return cmos_read(a); }
    void write(const Address & a, unsigned char d) { cmos_write(a, d); }

    int size() const { return cmos_size(); }
};

__END_SYS

#endif