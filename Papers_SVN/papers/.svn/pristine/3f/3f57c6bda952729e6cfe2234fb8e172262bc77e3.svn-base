#ifndef __flash_util_h
#define __flash_util_h

#include <avr/boot.h>
#include <avr/pgmspace.h>
#include <avr/interrupt.h>

__USING_SYS

class Flash_Util {

    private:
    static const unsigned int _base_address = 0xFA00;

    public:
        Flash_Util() {}

	static int write_c(uint16_t offset, uint8_t *data, uint8_t size, bool absolute_address) BOOTLOADER_SECTION __attribute__ ((noinline)) {
            uint8_t buffer_tmp[256];
            uint16_t i, j, w, page_addr;
            uint8_t sreg, temp;

	    uint16_t address;
	    if (!absolute_address)
		address = _base_address + offset;
	    else
		address = offset;

            page_addr = address & ~(SPM_PAGESIZE - 1);

            boot_spm_busy_wait();

            for (i = 0; i < SPM_PAGESIZE; i++)
                buffer_tmp[i] = pgm_read_byte(page_addr + i);

            sreg = SREG;
            cli();

            boot_page_erase_safe(page_addr);

            boot_spm_busy_wait();

	    j = 0;
	    for (i = 0; i < SPM_PAGESIZE; i += 2) {
		if ((((page_addr + i) == address) || ((page_addr + i + 1) == address)) && (j != size)) {
		    if (j + 2 <= size) {
			temp = *data++;
			w = (*data++ << 8) | temp;
			address += 2;
			j += 2;
		    } else {
			w =  (buffer_tmp[i] << 8) | *data++;
			address += 2;
			j += 1;
		    }
		} else {
		    w = (buffer_tmp[i+1] << 8) | (buffer_tmp[i]);
		}
		boot_page_fill(page_addr + i, w);
	    }

            boot_page_write(address);

            boot_spm_busy_wait();

            boot_rww_enable();

            SREG = sreg;

            return 1;
	}

	/*
        static int write_c(uint16_t address, uint8_t data) BOOTLOADER_SECTION __attribute__ ((noinline)) {
            uint8_t buffer_tmp[256];
            uint16_t i, w, page_addr;
            uint8_t sreg;

            page_addr = address & ~(SPM_PAGESIZE - 1);

            boot_spm_busy_wait();

            for ( i = 0; i < SPM_PAGESIZE; i++ )
                buffer_tmp[i] = pgm_read_byte(page_addr + i);

            sreg = SREG;
            cli();

            boot_page_erase_safe(address);

            boot_spm_busy_wait();

            for ( i = 0; i < SPM_PAGESIZE; i += 2 ) {
                // writes words
                if ( (page_addr + i) == address) {
                    w = (buffer_tmp[i+1] << 8) | (data);
                } else if ( (page_addr + i + 1) == address) {
                    w = (data << 8) | (buffer_tmp[i]);
                } else {
                    w = (buffer_tmp[i+1] << 8) | (buffer_tmp[i]);
                }
                boot_page_fill(page_addr + i, w);
            }
            boot_page_write(address);

            boot_spm_busy_wait();

            boot_rww_enable();

            SREG = sreg;

            return 1;
        }	
	*/
};

#endif
