unsigned char bitsearch_reverse(MACHINEWORD m) {
	unsigned long n;
	__asm__ __volatile__ (
			"bsr %0, %1" : "=r"(n) : "r"(m)
	);
	return n;
}

