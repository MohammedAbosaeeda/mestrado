#include <keso_types.h>

int read (int desc, void *buf, size_t len) {
  asm volatile ("		\n\
    mov %d12,4		\n\
    j ___virtio_hnd		\n\
  ");
}
