/* AUTO GENERATED FILE */

package keso.driver.avr.atmega8535;

import keso.core.*;

public class ATMega8535 implements MemoryMappedObject {

	/* $Id: iom8535.h,v 1.6 2004/11/02 18:43:56 arcanum Exp $ */

	/* avr/iom8535.h - definitions for ATmega8535 */
	public static final byte _AVR_IOM8535_H_ = (byte) 1;

	/* This file should only be included from <avr/io.h>, never directly. */

	/* I/O registers */

	/* TWI stands for "Two Wire Interface" or "TWI Was I2C(tm)" */
	private static final short __TWBR = (short) 0;
	private static final short __TWSR = (short) 1;
	private static final short __TWAR = (short) 2;
	private static final short __TWDR = (short) 3;

	/* ADC Data register */
	private static final short __ADCL = (short) 4;
	private static final short __ADCH = (short) 5;

	/* ADC Control and Status Register */
	private static final short __ADCSRA = (short) 6;

	/* ADC MUX */
	private static final short __ADMUX = (short) 7;

	/* Analog Comparator Control and Status Register */
	private static final short __ACSR = (short) 8;

	/* USART Baud Rate Register */
	private static final short __UBRRL = (short) 9;

	/* USART Control and Status Register B */
	private static final short __UCSRB = (short) 10;

	/* USART Control and Status Register A */
	private static final short __UCSRA = (short) 11;

	/* USART I/O Data Register */
	private static final short __UDR = (short) 12;

	/* SPI Control Register */
	private static final short __SPCR = (short) 13;

	/* SPI Status Register */
	private static final short __SPSR = (short) 14;

	/* SPI I/O Data Register */
	private static final short __SPDR = (short) 15;

	/* Input Pins, Port D */
	private static final short __PIND = (short) 16;

	/* Data Direction Register, Port D */
	private static final short __DDRD = (short) 17;

	/* Data Register, Port D */
	private static final short __PORTD = (short) 18;

	/* Input Pins, Port C */
	private static final short __PINC = (short) 19;

	/* Data Direction Register, Port C */
	private static final short __DDRC = (short) 20;

	/* Data Register, Port C */
	private static final short __PORTC = (short) 21;

	/* Input Pins, Port B */
	private static final short __PINB = (short) 22;

	/* Data Direction Register, Port B */
	private static final short __DDRB = (short) 23;

	/* Data Register, Port B */
	private static final short __PORTB = (short) 24;

	/* Input Pins, Port A */
	private static final short __PINA = (short) 25;

	/* Data Direction Register, Port A */
	private static final short __DDRA = (short) 26;

	/* Data Register, Port A */
	private static final short __PORTA = (short) 27;

	/* 0x1C..0x1F EEPROM */

	/* USART Baud Rate Register HI         */

	/* USART Control and Status Register C */
	private static final short __UBRRH = (short) 32;

	/* Watchdog Timer Control Register */
	private static final short __WDTCR = (short) 33;

	/* Asynchronous mode Status Register */
	private static final short __ASSR = (short) 34;

	/* Timer/Counter2 Output Compare Register */
	private static final short __OCR2 = (short) 35;

	/* Timer/Counter 2 */
	private static final short __TCNT2 = (short) 36;

	/* Timer/Counter 2 Control Register */
	private static final short __TCCR2 = (short) 37;

	/* T/C 1 Input Capture Register */
	private static final short __ICR1L = (short) 38;
	private static final short __ICR1H = (short) 39;

	/* Timer/Counter1 Output Compare Register B */
	private static final short __OCR1BL = (short) 40;
	private static final short __OCR1BH = (short) 41;

	/* Timer/Counter1 Output Compare Register A */
	private static final short __OCR1AL = (short) 42;
	private static final short __OCR1AH = (short) 43;

	/* Timer/Counter 1 */
	private static final short __TCNT1L = (short) 44;
	private static final short __TCNT1H = (short) 45;

	/* Timer/Counter 1 Control and Status Register */
	private static final short __TCCR1B = (short) 46;

	/* Timer/Counter 1 Control Register */
	private static final short __TCCR1A = (short) 47;

	/* Special Function IO Register */
	private static final short __SFIOR = (short) 48;

	/* Oscillator Calibration Register */
	private static final short __OSCCAL = (short) 49;

	/* Timer/Counter 0 */
	private static final short __TCNT0 = (short) 50;

	/* Timer/Counter 0 Control Register */
	private static final short __TCCR0 = (short) 51;

	/* MCU Control and Status Register */
	private static final short __MCUCSR = (short) 52;

	/* MCU Control Register */
	private static final short __MCUCR = (short) 53;

	/* TWI Control Register */
	private static final short __TWCR = (short) 54;

	/* Store Program Memory Control Register */
	private static final short __SPMCR = (short) 55;

	/* Timer/Counter Interrupt Flag register */
	private static final short __TIFR = (short) 56;

	/* Timer/Counter Interrupt MaSK register */
	private static final short __TIMSK = (short) 57;

	/* General Interrupt Flag Register */
	private static final short __GIFR = (short) 58;

	/* General Interrupt MaSK register */
	private static final short __GICR = (short) 59;

	/* Timer/Counter 0 Output Compare Register */
	private static final short __OCR0 = (short) 60;

	/* 0x3D..0x3E SP */

	/* 0x3F SREG */

	/* Interrupt vectors */
	public static final byte _VECTORS_SIZE = (byte) 42;

	/* General Interrupt Control Register */
	public static final byte INT1 = (byte) 7;
	public static final byte INT0 = (byte) 6;
	public static final byte INT2 = (byte) 5;
	public static final byte IVSEL = (byte) 1;
	public static final byte IVCE = (byte) 0;

	/* General Interrupt Flag Register */
	public static final byte INTF1 = (byte) 7;
	public static final byte INTF0 = (byte) 6;
	public static final byte INTF2 = (byte) 5;

	/* Timer/Counter Interrupt MaSK register */
	public static final byte OCIE2 = (byte) 7;
	public static final byte TOIE2 = (byte) 6;
	public static final byte TICIE1 = (byte) 5;
	public static final byte OCIE1A = (byte) 4;
	public static final byte OCIE1B = (byte) 3;
	public static final byte TOIE1 = (byte) 2;
	public static final byte OCIE0 = (byte) 1;
	public static final byte TOIE0 = (byte) 0;

	/* Timer/Counter Interrupt Flag register */
	public static final byte OCF2 = (byte) 7;
	public static final byte TOV2 = (byte) 6;
	public static final byte ICF1 = (byte) 5;
	public static final byte OCF1A = (byte) 4;
	public static final byte OCF1B = (byte) 3;
	public static final byte TOV1 = (byte) 2;
	public static final byte OCF0 = (byte) 1;
	public static final byte TOV0 = (byte) 0;

	/* Store Program Memory Control Register */
	public static final byte SPMIE = (byte) 7;
	public static final byte RWWSB = (byte) 6;
	public static final byte RWWSRE = (byte) 4;
	public static final byte BLBSET = (byte) 3;
	public static final byte PGWRT = (byte) 2;
	public static final byte PGERS = (byte) 1;
	public static final byte SPMEN = (byte) 0;

	/* TWI Control Register */
	public static final byte TWINT = (byte) 7;
	public static final byte TWEA = (byte) 6;
	public static final byte TWSTA = (byte) 5;
	public static final byte TWSTO = (byte) 4;
	public static final byte TWWC = (byte) 3;
	public static final byte TWEN = (byte) 2;
	public static final byte TWIE = (byte) 0;

	/* MCU Control Register */
	public static final byte SM2 = (byte) 7;
	public static final byte SE = (byte) 6;
	public static final byte SM1 = (byte) 5;
	public static final byte SM0 = (byte) 4;
	public static final byte ISC11 = (byte) 3;
	public static final byte ISC10 = (byte) 2;
	public static final byte ISC01 = (byte) 1;
	public static final byte ISC00 = (byte) 0;

	/* MCU Control and Status Register */
	public static final byte ISC2 = (byte) 6;
	public static final byte WDRF = (byte) 3;
	public static final byte BORF = (byte) 2;
	public static final byte EXTRF = (byte) 1;
	public static final byte PORF = (byte) 0;

	/* Timer/Counter 0 Control Register */
	public static final byte FOC0 = (byte) 7;
	public static final byte WGM00 = (byte) 6;
	public static final byte COM01 = (byte) 5;
	public static final byte COM00 = (byte) 4;
	public static final byte WGM01 = (byte) 3;
	public static final byte CS02 = (byte) 2;
	public static final byte CS01 = (byte) 1;
	public static final byte CS00 = (byte) 0;

	/* Special Function IO Register */
	public static final byte ADTS2 = (byte) 7;
	public static final byte ADTS1 = (byte) 6;
	public static final byte ADTS0 = (byte) 5;
	public static final byte ADHSM = (byte) 4;
	public static final byte ACME = (byte) 3;
	public static final byte PUD = (byte) 2;
	public static final byte PSR2 = (byte) 1;
	public static final byte PSR10 = (byte) 0;

	/* Timer/Counter 1 Control Register */
	public static final byte COM1A1 = (byte) 7;
	public static final byte COM1A0 = (byte) 6;
	public static final byte COM1B1 = (byte) 5;
	public static final byte COM1B0 = (byte) 4;
	public static final byte FOC1A = (byte) 3;
	public static final byte FOC1B = (byte) 2;
	public static final byte WGM11 = (byte) 1;
	public static final byte WGM10 = (byte) 0;

	/* Timer/Counter 1 Control and Status Register */
	public static final byte ICNC1 = (byte) 7;
	public static final byte ICES1 = (byte) 6;
	public static final byte WGM13 = (byte) 4;
	public static final byte WGM12 = (byte) 3;
	public static final byte CS12 = (byte) 2;
	public static final byte CS11 = (byte) 1;
	public static final byte CS10 = (byte) 0;

	/* Timer/Counter 2 Control Register */
	public static final byte FOC2 = (byte) 7;
	public static final byte WGM20 = (byte) 6;
	public static final byte COM21 = (byte) 5;
	public static final byte COM20 = (byte) 4;
	public static final byte WGM21 = (byte) 3;
	public static final byte CS22 = (byte) 2;
	public static final byte CS21 = (byte) 1;
	public static final byte CS20 = (byte) 0;

	/* Asynchronous mode Status Register */
	public static final byte AS2 = (byte) 3;
	public static final byte TCN2UB = (byte) 2;
	public static final byte OCR2UB = (byte) 1;
	public static final byte TCR2UB = (byte) 0;

	/* Watchdog Timer Control Register */
	public static final byte WDCE = (byte) 4;
	public static final byte WDE = (byte) 3;
	public static final byte WDP2 = (byte) 2;
	public static final byte WDP1 = (byte) 1;
	public static final byte WDP0 = (byte) 0;

	/* USART Control and Status Register C */
	public static final byte URSEL = (byte) 7;
	public static final byte UMSEL = (byte) 6;
	public static final byte UPM1 = (byte) 5;
	public static final byte UPM0 = (byte) 4;
	public static final byte USBS = (byte) 3;
	public static final byte UCSZ1 = (byte) 2;
	public static final byte UCSZ0 = (byte) 1;
	public static final byte UCPOL = (byte) 0;

	/* Data Register, Port A */
	public static final byte PA7 = (byte) 7;
	public static final byte PA6 = (byte) 6;
	public static final byte PA5 = (byte) 5;
	public static final byte PA4 = (byte) 4;
	public static final byte PA3 = (byte) 3;
	public static final byte PA2 = (byte) 2;
	public static final byte PA1 = (byte) 1;
	public static final byte PA0 = (byte) 0;

	/* Data Direction Register, Port A */
	public static final byte DDA7 = (byte) 7;
	public static final byte DDA6 = (byte) 6;
	public static final byte DDA5 = (byte) 5;
	public static final byte DDA4 = (byte) 4;
	public static final byte DDA3 = (byte) 3;
	public static final byte DDA2 = (byte) 2;
	public static final byte DDA1 = (byte) 1;
	public static final byte DDA0 = (byte) 0;

	/* Input Pins, Port A */
	public static final byte PINA7 = (byte) 7;
	public static final byte PINA6 = (byte) 6;
	public static final byte PINA5 = (byte) 5;
	public static final byte PINA4 = (byte) 4;
	public static final byte PINA3 = (byte) 3;
	public static final byte PINA2 = (byte) 2;
	public static final byte PINA1 = (byte) 1;
	public static final byte PINA0 = (byte) 0;

	/* Data Register, Port B */
	public static final byte PB7 = (byte) 7;
	public static final byte PB6 = (byte) 6;
	public static final byte PB5 = (byte) 5;
	public static final byte PB4 = (byte) 4;
	public static final byte PB3 = (byte) 3;
	public static final byte PB2 = (byte) 2;
	public static final byte PB1 = (byte) 1;
	public static final byte PB0 = (byte) 0;

	/* Data Direction Register, Port B */
	public static final byte DDB7 = (byte) 7;
	public static final byte DDB6 = (byte) 6;
	public static final byte DDB5 = (byte) 5;
	public static final byte DDB4 = (byte) 4;
	public static final byte DDB3 = (byte) 3;
	public static final byte DDB2 = (byte) 2;
	public static final byte DDB1 = (byte) 1;
	public static final byte DDB0 = (byte) 0;

	/* Input Pins, Port B */
	public static final byte PINB7 = (byte) 7;
	public static final byte PINB6 = (byte) 6;
	public static final byte PINB5 = (byte) 5;
	public static final byte PINB4 = (byte) 4;
	public static final byte PINB3 = (byte) 3;
	public static final byte PINB2 = (byte) 2;
	public static final byte PINB1 = (byte) 1;
	public static final byte PINB0 = (byte) 0;

	/* Data Register, Port C */
	public static final byte PC7 = (byte) 7;
	public static final byte PC6 = (byte) 6;
	public static final byte PC5 = (byte) 5;
	public static final byte PC4 = (byte) 4;
	public static final byte PC3 = (byte) 3;
	public static final byte PC2 = (byte) 2;
	public static final byte PC1 = (byte) 1;
	public static final byte PC0 = (byte) 0;

	/* Data Direction Register, Port C */
	public static final byte DDC7 = (byte) 7;
	public static final byte DDC6 = (byte) 6;
	public static final byte DDC5 = (byte) 5;
	public static final byte DDC4 = (byte) 4;
	public static final byte DDC3 = (byte) 3;
	public static final byte DDC2 = (byte) 2;
	public static final byte DDC1 = (byte) 1;
	public static final byte DDC0 = (byte) 0;

	/* Input Pins, Port C */
	public static final byte PINC7 = (byte) 7;
	public static final byte PINC6 = (byte) 6;
	public static final byte PINC5 = (byte) 5;
	public static final byte PINC4 = (byte) 4;
	public static final byte PINC3 = (byte) 3;
	public static final byte PINC2 = (byte) 2;
	public static final byte PINC1 = (byte) 1;
	public static final byte PINC0 = (byte) 0;

	/* Data Register, Port D */
	public static final byte PD7 = (byte) 7;
	public static final byte PD6 = (byte) 6;
	public static final byte PD5 = (byte) 5;
	public static final byte PD4 = (byte) 4;
	public static final byte PD3 = (byte) 3;
	public static final byte PD2 = (byte) 2;
	public static final byte PD1 = (byte) 1;
	public static final byte PD0 = (byte) 0;

	/* Data Direction Register, Port D */
	public static final byte DDD7 = (byte) 7;
	public static final byte DDD6 = (byte) 6;
	public static final byte DDD5 = (byte) 5;
	public static final byte DDD4 = (byte) 4;
	public static final byte DDD3 = (byte) 3;
	public static final byte DDD2 = (byte) 2;
	public static final byte DDD1 = (byte) 1;
	public static final byte DDD0 = (byte) 0;

	/* Input Pins, Port D */
	public static final byte PIND7 = (byte) 7;
	public static final byte PIND6 = (byte) 6;
	public static final byte PIND5 = (byte) 5;
	public static final byte PIND4 = (byte) 4;
	public static final byte PIND3 = (byte) 3;
	public static final byte PIND2 = (byte) 2;
	public static final byte PIND1 = (byte) 1;
	public static final byte PIND0 = (byte) 0;

	/* SPI Status Register */
	public static final byte SPIF = (byte) 7;
	public static final byte WCOL = (byte) 6;
	public static final byte SPI2X = (byte) 0;

	/* SPI Control Register */
	public static final byte SPIE = (byte) 7;
	public static final byte SPE = (byte) 6;
	public static final byte DORD = (byte) 5;
	public static final byte MSTR = (byte) 4;
	public static final byte CPOL = (byte) 3;
	public static final byte CPHA = (byte) 2;
	public static final byte SPR1 = (byte) 1;
	public static final byte SPR0 = (byte) 0;

	/* USART Control and Status Register A */
	public static final byte RXC = (byte) 7;
	public static final byte TXC = (byte) 6;
	public static final byte UDRE = (byte) 5;
	public static final byte FE = (byte) 4;
	public static final byte DOR = (byte) 3;
	public static final byte PE = (byte) 2;
	public static final byte U2X = (byte) 1;
	public static final byte MPCM = (byte) 0;

	/* USART Control and Status Register B */
	public static final byte RXCIE = (byte) 7;
	public static final byte TXCIE = (byte) 6;
	public static final byte UDRIE = (byte) 5;
	public static final byte RXEN = (byte) 4;
	public static final byte TXEN = (byte) 3;
	public static final byte UCSZ2 = (byte) 2;
	public static final byte RXB8 = (byte) 1;
	public static final byte TXB8 = (byte) 0;

	/* Analog Comparator Control and Status Register */
	public static final byte ACD = (byte) 7;
	public static final byte ACBG = (byte) 6;
	public static final byte ACO = (byte) 5;
	public static final byte ACI = (byte) 4;
	public static final byte ACIE = (byte) 3;
	public static final byte ACIC = (byte) 2;
	public static final byte ACIS1 = (byte) 1;
	public static final byte ACIS0 = (byte) 0;

	/* ADC Multiplexer Selection Register */
	public static final byte REFS1 = (byte) 7;
	public static final byte REFS0 = (byte) 6;
	public static final byte ADLAR = (byte) 5;
	public static final byte MUX4 = (byte) 4;
	public static final byte MUX3 = (byte) 3;
	public static final byte MUX2 = (byte) 2;
	public static final byte MUX1 = (byte) 1;
	public static final byte MUX0 = (byte) 0;

	/* ADC Control and Status Register */
	public static final byte ADEN = (byte) 7;
	public static final byte ADSC = (byte) 6;
	public static final byte ADATE = (byte) 5;
	public static final byte ADIF = (byte) 4;
	public static final byte ADIE = (byte) 3;
	public static final byte ADPS2 = (byte) 2;
	public static final byte ADPS1 = (byte) 1;
	public static final byte ADPS0 = (byte) 0;

	/* TWI (Slave) Address Register */
	public static final byte TWGCE = (byte) 0;

	/* TWI Status Register */
	public static final byte TWS7 = (byte) 7;
	public static final byte TWS6 = (byte) 6;
	public static final byte TWS5 = (byte) 5;
	public static final byte TWS4 = (byte) 4;
	public static final byte TWS3 = (byte) 3;
	public static final byte TWPS1 = (byte) 1;
	public static final byte TWPS0 = (byte) 0;

	/* Constants */
	public static final byte SPM_PAGESIZE = (byte) 64;
	public static final byte RAMEND = (byte) 0;
	public static final byte XRAMEND = (byte) 0;
	public static final byte E2END = (byte) 0;
	public static final byte FLASHEND = (byte) 0;

	/* _AVR_IOM8535_H_ */
	/* Memory maped IO registers */

	/* Address offset 0 */
	public MT_U8 TWBR;

	/* Address offset 1 */
	public MT_U8 TWSR;

	/* Address offset 2 */
	public MT_U8 TWAR;

	/* Address offset 3 */
	public MT_U8 TWDR;

	/* Address offset 4 */
	public MT_U8 ADCL;

	/* Address offset 5 */
	public MT_U8 ADCH;

	/* Address offset 6 */
	public MT_U8 ADCSRA;

	/* Address offset 7 */
	public MT_U8 ADMUX;

	/* Address offset 8 */
	public MT_U8 ACSR;

	/* Address offset 9 */
	public MT_U8 UBRRL;

	/* Address offset 10 */
	public MT_U8 UCSRB;

	/* Address offset 11 */
	public MT_U8 UCSRA;

	/* Address offset 12 */
	public MT_U8 UDR;

	/* Address offset 13 */
	public MT_U8 SPCR;

	/* Address offset 14 */
	public MT_U8 SPSR;

	/* Address offset 15 */
	public MT_U8 SPDR;

	/* Address offset 16 */
	public MT_U8 PIND;

	/* Address offset 17 */
	public MT_U8 DDRD;

	/* Address offset 18 */
	public MT_U8 PORTD;

	/* Address offset 19 */
	public MT_U8 PINC;

	/* Address offset 20 */
	public MT_U8 DDRC;

	/* Address offset 21 */
	public MT_U8 PORTC;

	/* Address offset 22 */
	public MT_U8 PINB;

	/* Address offset 23 */
	public MT_U8 DDRB;

	/* Address offset 24 */
	public MT_U8 PORTB;

	/* Address offset 25 */
	public MT_U8 PINA;

	/* Address offset 26 */
	public MT_U8 DDRA;

	/* Address offset 27 */
	public MT_U8 PORTA;

	/* Address offset 28 */
	public MT_U8 EECR;

	/* Address offset 29 */
	public MT_U8 EEDR;

	/* Address offset 30 */
	public MT_U8 EEAL;

	/* Address offset 31 */
	public MT_U8 EEAH;

	/* Address offset 32 */
	public MT_U8 UBRRH;

	/* Address offset 33 */
	public MT_U8 WDTCR;

	/* Address offset 34 */
	public MT_U8 ASSR;

	/* Address offset 35 */
	public MT_U8 OCR2;

	/* Address offset 36 */
	public MT_U8 TCNT2;

	/* Address offset 37 */
	public MT_U8 TCCR2;

	/* Address offset 38 */
	public MT_U8 ICR1L;

	/* Address offset 39 */
	public MT_U8 ICR1H;

	/* Address offset 40 */
	public MT_U8 OCR1BL;

	/* Address offset 41 */
	public MT_U8 OCR1BH;

	/* Address offset 42 */
	public MT_U8 OCR1AL;

	/* Address offset 43 */
	public MT_U8 OCR1AH;

	/* Address offset 44 */
	public MT_U8 TCNT1L;

	/* Address offset 45 */
	public MT_U8 TCNT1H;

	/* Address offset 46 */
	public MT_U8 TCCR1B;

	/* Address offset 47 */
	public MT_U8 TCCR1A;

	/* Address offset 48 */
	public MT_U8 SFIOR;

	/* Address offset 49 */
	public MT_U8 OSCCAL;

	/* Address offset 50 */
	public MT_U8 TCNT0;

	/* Address offset 51 */
	public MT_U8 TCCR0;

	/* Address offset 52 */
	public MT_U8 MCUCSR;

	/* Address offset 53 */
	public MT_U8 MCUCR;

	/* Address offset 54 */
	public MT_U8 TWCR;

	/* Address offset 55 */
	public MT_U8 SPMCR;

	/* Address offset 56 */
	public MT_U8 TIFR;

	/* Address offset 57 */
	public MT_U8 TIMSK;

	/* Address offset 58 */
	public MT_U8 GIFR;

	/* Address offset 59 */
	public MT_U8 GICR;

	/* Address offset 60 */
	public MT_U8 OCR0;

	/* Address offset 61 */
	public MT_U8 SPL;

	/* Address offset 62 */
	public MT_U8 SPH;

	/* Address offset 63 */
	public MT_U8 SREG;

	public final static ATMega8535 registers = (ATMega8535) MemoryService.mapStaticDeviceMemory(0x20, "keso/driver/avr/atmega8535/ATMega8535");

}
