/*! @file
 *  @brief EPOS SPI Mediator Common Package
 *
 *  CVS Log for this file:
 *  \verbinclude include/spi_h.log
 */
#ifndef __spi_h
#define __spi_h

#include <system/config.h>

__BEGIN_SYS

class SPI_Common
{
protected:
    SPI_Common() {}
};

__END_SYS

#ifdef __SPI_H
#include __SPI_H
#endif

#endif
