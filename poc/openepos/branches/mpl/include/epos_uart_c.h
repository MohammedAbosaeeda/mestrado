/*
 * epos_uart_c.h
 *
 * This is the C header for the C wrapper for EPOS UART mediator.
 *
 */

#ifndef EPOS_UART_C_H_
#define EPOS_UART_C_H_

typedef void EPOS_UART;

/* EPOS_UART * new_EPOS_UART(unsigned int unit); 
    This function will not be implemented by now.
*/

EPOS_UART * new_EPOS_UART_vdefault();

EPOS_UART * new_EPOS_UART(unsigned int baud, unsigned int data_bits, unsigned int parity, unsigned int stop_bits, unsigned int unit);

/*
void EPOS_UART_config(unsigned int baud, unsigned int data_bits, unsigned int parity, unsigned int stop_bits);
    This function will not be implemented by now.
*/


/* 
void EPOS_UART_config_v2(unsigned int * baud, unsigned int * data_bits, unsigned int * parity, unsigned int * stop_bits);
    This function will not be implemented by now.
*/


char EPOS_UART_get(EPOS_UART * u);

void EPOS_UART_put(EPOS_UART * u, char c);

/* void EPOS_UART_loopback(bool flag);
    This function will not be implemented by now.
*/

/* void EPOS_UART_power(unsigned char ps);
    This function will not be implemented by now.
*/

/* unsigned char EPOS_UART_power();
    This function will not be implemented by now.
*/

void delete_EPOS_UART(EPOS_UART * u);

#endif /* EPOS_UART_C_H_ */


