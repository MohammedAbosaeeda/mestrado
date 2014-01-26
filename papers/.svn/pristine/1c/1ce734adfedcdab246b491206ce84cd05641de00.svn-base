template<>
char Power_Manager<ATMega128_UART,
    Traits<ATMega128_UART>::shared,
    Traits<ATMega128_UART>::instances
>::get() {
    if(!((_op_mode == STANDBY) 
    || (_op_mode == FULL))) {
        if((_prev_op_mode == STANDBY)
        || (_prev_op_mode == FULL))
	    power(_prev_op_mode);
	else
	    power(FULL);
    }

    return ATMega128_UART::get();
}
