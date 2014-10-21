int close (int desc) {
  asm volatile ("	\n\
    mov %d12,2		\n\
    j ___virtio_hnd	\n\
  ");
}
