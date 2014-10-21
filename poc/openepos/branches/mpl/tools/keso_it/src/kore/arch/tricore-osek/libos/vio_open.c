int open (char *path, int flags, int mode) {
  asm volatile ("		\n\
    mov %d12,1		\n\
    j ___virtio_hnd		\n\
  ");
}
