#include <keso_types.h>

off_t lseek (int desc, off_t offset, int whence) {
  asm volatile ("		\n\
    mov %d12,3		\n\
    j ___virtio_hnd		\n\
  ");
}
