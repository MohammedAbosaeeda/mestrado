int unlink (const char *name) {
  asm volatile ("		\n\
    mov %d12,7		\n\
    j ___virtio_hnd		\n\
  ");
}
