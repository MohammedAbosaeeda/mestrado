template<>
void Power_Manager<ATMega128_UART,
    Traits<ATMega128_UART>::shared,
    Traits<ATMega128_UART>::instances
>::put(char c) {
    if(!((_op_mode == LIGHT)
    || (_op_mode == FULL))) {
        if((_prev_op_mode == LIGHT)
        || (_prev_op_mode == FULL))
	    power(_prev_op_mode);
	else
	    power(FULL);
    }

    ATMega128_UART::put(c);
}
