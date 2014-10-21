#include <keso_types.h>

int write (int desc, void *buf, size_t len) {
  asm volatile ("	\n\
    mov %d12,5		\n\
    j ___virtio_hnd	\n\
  ");
}
