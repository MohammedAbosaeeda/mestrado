#include <../app/flash.h>
#include <../app/flash_util.h>

__USING_SYS

int Flash::write(uint16_t offset, uint8_t * data, uint8_t size, bool absolute_address)
{
	Flash_Util::write_c(offset, data, size, absolute_address);
}

