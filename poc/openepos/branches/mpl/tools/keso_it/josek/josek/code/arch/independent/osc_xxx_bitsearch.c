unsigned char bitsearch_reverse(MACHINEWORD m) {
	char n;
	for (n = 8 * sizeof(MACHINEWORD); n >= 0; n--) {
		if (m & (1 << n)) return n;
	}
	return 0;
}

