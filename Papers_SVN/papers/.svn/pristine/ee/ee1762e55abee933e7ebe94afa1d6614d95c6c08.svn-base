template<>
void Power_Manager<ATMega128_UART,
    Traits<ATMega128_UART>::shared,
    Traits<ATMega128_UART>::instances
>::power(char mode) {

    _prev_op_mode = _op_mode;
    _op_mode = mode;
    switch(mode) {
    case FULL:
        this->tx_enable();
	this->rx_enable();
	break;
    case LIGHT: //Send-Only
	this->tx_enable();
	this->rx_finish();
        this->rx_disable();
	break;
    case STANDBY: //Receive-Only
	this->tx_flush();
        this->tx_disable();
	this->rx_enable();
	break;
    case OFF:
	this->tx_flush();
        this->tx_disable();
	this->rx_finish();
	this->rx_disable();
	break;
    }
}

