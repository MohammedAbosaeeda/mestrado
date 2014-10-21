/* AUTO GENERATED FILE */

package keso.driver.avr.atmega128;

import keso.core.*;

public class ATMega128 implements MemoryMappedObject {

	/* $Id: iom128.h,v 1.14 2004/12/30 18:45:13 arcanum Exp $ */
	public static final byte _AVR_IOM128_H_ = (byte) 1;

	/* This file should only be included from <avr/io.h>, never directly. */

	/* I/O registers */

	/* Input Pins, Port F */
	private static final short __PINF = (short) 0;

	/* Input Pins, Port E */
	private static final short __PINE = (short) 1;

	/* Data Direction Register, Port E */
	private static final short __DDRE = (short) 2;

	/* Data Register, Port E */
	private static final short __PORTE = (short) 3;

	/* ADC Data Register */

	/* for backwards compatibility */
	private static final short __ADCL = (short) 4;
	private static final short __ADCH = (short) 5;

	/* ADC Control and status register */
	private static final short __ADCSR = (short) 6;
	private static final short __ADCSRA = (short) 6;

	/* ADC Multiplexer select */
	private static final short __ADMUX = (short) 7;

	/* Analog Comparator Control and Status Register */
	private static final short __ACSR = (short) 8;

	/* USART0 Baud Rate Register Low */
	private static final short __UBRR0L = (short) 9;

	/* USART0 Control and Status Register B */
	private static final short __UCSR0B = (short) 10;

	/* USART0 Control and Status Register A */
	private static final short __UCSR0A = (short) 11;

	/* USART0 I/O Data Register */
	private static final short __UDR0 = (short) 12;

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

	/* Special Function I/O Register */
	private static final short __SFIOR = (short) 32;

	/* Watchdog Timer Control Register */
	private static final short __WDTCR = (short) 33;

	/* On-chip Debug Register */
	private static final short __OCDR = (short) 34;

	/* Timer2 Output Compare Register */
	private static final short __OCR2 = (short) 35;

	/* Timer/Counter 2 */
	private static final short __TCNT2 = (short) 36;

	/* Timer/Counter 2 Control register */
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

	/* Timer/Counter 0 Asynchronous Control & Status Register */
	private static final short __ASSR = (short) 48;

	/* Output Compare Register 0 */
	private static final short __OCR0 = (short) 49;

	/* Timer/Counter 0 */
	private static final short __TCNT0 = (short) 50;

	/* Timer/Counter 0 Control Register */
	private static final short __TCCR0 = (short) 51;

	/* MCU Status Register */
	private static final short __MCUSR = (short) 52;
	private static final short __MCUCSR = (short) 52;

	/* MCU general Control Register */
	private static final short __MCUCR = (short) 53;

	/* Timer/Counter Interrupt Flag Register */
	private static final short __TIFR = (short) 54;

	/* Timer/Counter Interrupt MaSK register */
	private static final short __TIMSK = (short) 55;

	/* External Interrupt Flag Register */
	private static final short __EIFR = (short) 56;

	/* External Interrupt MaSK register */
	private static final short __EIMSK = (short) 57;

	/* External Interrupt Control Register B */
	private static final short __EICRB = (short) 58;

	/* RAM Page Z select register */
	private static final short __RAMPZ = (short) 59;

	/* XDIV Divide control register */
	private static final short __XDIV = (short) 60;

	/* 0x3D..0x3E SP */

	/* 0x3F SREG */

	/* Extended I/O registers */

	/* Data Direction Register, Port F */
	private static final short __DDRF = (short) 97;

	/* Data Register, Port F */
	private static final short __PORTF = (short) 98;

	/* Input Pins, Port G */
	private static final short __PING = (short) 99;

	/* Data Direction Register, Port G */
	private static final short __DDRG = (short) 100;

	/* Data Register, Port G */
	private static final short __PORTG = (short) 101;

	/* Store Program Memory Control and Status Register */
	private static final short __SPMCR = (short) 104;
	private static final short __SPMCSR = (short) 104;

	/* External Interrupt Control Register A */
	private static final short __EICRA = (short) 106;

	/* External Memory Control Register B */
	private static final short __XMCRB = (short) 108;

	/* External Memory Control Register A */
	private static final short __XMCRA = (short) 109;

	/* Oscillator Calibration Register */
	private static final short __OSCCAL = (short) 111;

	/* 2-wire Serial Interface Bit Rate Register */
	private static final short __TWBR = (short) 112;

	/* 2-wire Serial Interface Status Register */
	private static final short __TWSR = (short) 113;

	/* 2-wire Serial Interface Address Register */
	private static final short __TWAR = (short) 114;

	/* 2-wire Serial Interface Data Register */
	private static final short __TWDR = (short) 115;

	/* 2-wire Serial Interface Control Register */
	private static final short __TWCR = (short) 116;

	/* Time Counter 1 Output Compare Register C */
	private static final short __OCR1CL = (short) 120;
	private static final short __OCR1CH = (short) 121;

	/* Timer/Counter 1 Control Register C */
	private static final short __TCCR1C = (short) 122;

	/* Extended Timer Interrupt Flag Register */
	private static final short __ETIFR = (short) 124;

	/* Extended Timer Interrupt Mask Register */
	private static final short __ETIMSK = (short) 125;

	/* Timer/Counter 3 Input Capture Register */
	private static final short __ICR3L = (short) 128;
	private static final short __ICR3H = (short) 129;

	/* Timer/Counter 3 Output Compare Register C */
	private static final short __OCR3CL = (short) 130;
	private static final short __OCR3CH = (short) 131;

	/* Timer/Counter 3 Output Compare Register B */
	private static final short __OCR3BL = (short) 132;
	private static final short __OCR3BH = (short) 133;

	/* Timer/Counter 3 Output Compare Register A */
	private static final short __OCR3AL = (short) 134;
	private static final short __OCR3AH = (short) 135;

	/* Timer/Counter 3 Counter Register */
	private static final short __TCNT3L = (short) 136;
	private static final short __TCNT3H = (short) 137;

	/* Timer/Counter 3 Control Register B */
	private static final short __TCCR3B = (short) 138;

	/* Timer/Counter 3 Control Register A */
	private static final short __TCCR3A = (short) 139;

	/* Timer/Counter 3 Control Register C */
	private static final short __TCCR3C = (short) 140;

	/* USART0 Baud Rate Register High */
	private static final short __UBRR0H = (short) 144;

	/* USART0 Control and Status Register C */
	private static final short __UCSR0C = (short) 149;

	/* USART1 Baud Rate Register High */
	private static final short __UBRR1H = (short) 152;

	/* USART1 Baud Rate Register Low*/
	private static final short __UBRR1L = (short) 153;

	/* USART1 Control and Status Register B */
	private static final short __UCSR1B = (short) 154;

	/* USART1 Control and Status Register A */
	private static final short __UCSR1A = (short) 155;

	/* USART1 I/O Data Register */
	private static final short __UDR1 = (short) 156;

	/* USART1 Control and Status Register C */
	private static final short __UCSR1C = (short) 157;

	/* Interrupt vectors */

	/* Keep for compatibility */

	/* Keep for compatibility */

	/* Keep for compatibility */

	/* Keep for compatibility */

	/* Keep for compatibility */

	/* Keep for compatibility */
	public static final byte _VECTORS_SIZE = (byte) 140;

	/* 2-wire Control Register - TWCR */
	public static final byte TWINT = (byte) 7;
	public static final byte TWEA = (byte) 6;
	public static final byte TWSTA = (byte) 5;
	public static final byte TWSTO = (byte) 4;
	public static final byte TWWC = (byte) 3;
	public static final byte TWEN = (byte) 2;
	public static final byte TWIE = (byte) 0;

	/* 2-wire Address Register - TWAR */
	public static final byte TWA6 = (byte) 7;
	public static final byte TWA5 = (byte) 6;
	public static final byte TWA4 = (byte) 5;
	public static final byte TWA3 = (byte) 4;
	public static final byte TWA2 = (byte) 3;
	public static final byte TWA1 = (byte) 2;
	public static final byte TWA0 = (byte) 1;
	public static final byte TWGCE = (byte) 0;

	/* 2-wire Status Register - TWSR */
	public static final byte TWS7 = (byte) 7;
	public static final byte TWS6 = (byte) 6;
	public static final byte TWS5 = (byte) 5;
	public static final byte TWS4 = (byte) 4;
	public static final byte TWS3 = (byte) 3;
	public static final byte TWPS1 = (byte) 1;
	public static final byte TWPS0 = (byte) 0;

	/* External Memory Control Register A - XMCRA */
	public static final byte SRL2 = (byte) 6;
	public static final byte SRL1 = (byte) 5;
	public static final byte SRL0 = (byte) 4;
	public static final byte SRW01 = (byte) 3;
	public static final byte SRW00 = (byte) 2;
	public static final byte SRW11 = (byte) 1;

	/* External Memory Control Register B - XMCRA */
	public static final byte XMBK = (byte) 7;
	public static final byte XMM2 = (byte) 2;
	public static final byte XMM1 = (byte) 1;
	public static final byte XMM0 = (byte) 0;

	/* XDIV Divide control register - XDIV */
	public static final byte XDIVEN = (byte) 7;
	public static final byte XDIV6 = (byte) 6;
	public static final byte XDIV5 = (byte) 5;
	public static final byte XDIV4 = (byte) 4;
	public static final byte XDIV3 = (byte) 3;
	public static final byte XDIV2 = (byte) 2;
	public static final byte XDIV1 = (byte) 1;
	public static final byte XDIV0 = (byte) 0;

	/* RAM Page Z select register - RAMPZ */
	public static final byte RAMPZ0 = (byte) 0;

	/* External Interrupt Control Register A - EICRA */
	public static final byte ISC31 = (byte) 7;
	public static final byte ISC30 = (byte) 6;
	public static final byte ISC21 = (byte) 5;
	public static final byte ISC20 = (byte) 4;
	public static final byte ISC11 = (byte) 3;
	public static final byte ISC10 = (byte) 2;
	public static final byte ISC01 = (byte) 1;
	public static final byte ISC00 = (byte) 0;

	/* External Interrupt Control Register B - EICRB */
	public static final byte ISC71 = (byte) 7;
	public static final byte ISC70 = (byte) 6;
	public static final byte ISC61 = (byte) 5;
	public static final byte ISC60 = (byte) 4;
	public static final byte ISC51 = (byte) 3;
	public static final byte ISC50 = (byte) 2;
	public static final byte ISC41 = (byte) 1;
	public static final byte ISC40 = (byte) 0;

	/* Store Program Memory Control Register - SPMCSR, SPMCR */
	public static final byte SPMIE = (byte) 7;
	public static final byte RWWSB = (byte) 6;
	public static final byte RWWSRE = (byte) 4;
	public static final byte BLBSET = (byte) 3;
	public static final byte PGWRT = (byte) 2;
	public static final byte PGERS = (byte) 1;
	public static final byte SPMEN = (byte) 0;

	/* External Interrupt MaSK register - EIMSK */
	public static final byte INT7 = (byte) 7;
	public static final byte INT6 = (byte) 6;
	public static final byte INT5 = (byte) 5;
	public static final byte INT4 = (byte) 4;
	public static final byte INT3 = (byte) 3;
	public static final byte INT2 = (byte) 2;
	public static final byte INT1 = (byte) 1;
	public static final byte INT0 = (byte) 0;

	/* External Interrupt Flag Register - EIFR */
	public static final byte INTF7 = (byte) 7;
	public static final byte INTF6 = (byte) 6;
	public static final byte INTF5 = (byte) 5;
	public static final byte INTF4 = (byte) 4;
	public static final byte INTF3 = (byte) 3;
	public static final byte INTF2 = (byte) 2;
	public static final byte INTF1 = (byte) 1;
	public static final byte INTF0 = (byte) 0;

	/* Timer/Counter Interrupt MaSK register - TIMSK */
	public static final byte OCIE2 = (byte) 7;
	public static final byte TOIE2 = (byte) 6;
	public static final byte TICIE1 = (byte) 5;
	public static final byte OCIE1A = (byte) 4;
	public static final byte OCIE1B = (byte) 3;
	public static final byte TOIE1 = (byte) 2;
	public static final byte OCIE0 = (byte) 1;
	public static final byte TOIE0 = (byte) 0;

	/* Timer/Counter Interrupt Flag Register - TIFR */
	public static final byte OCF2 = (byte) 7;
	public static final byte TOV2 = (byte) 6;
	public static final byte ICF1 = (byte) 5;
	public static final byte OCF1A = (byte) 4;
	public static final byte OCF1B = (byte) 3;
	public static final byte TOV1 = (byte) 2;
	public static final byte OCF0 = (byte) 1;
	public static final byte TOV0 = (byte) 0;

	/* Extended Timer Interrupt MaSK register - ETIMSK */
	public static final byte TICIE3 = (byte) 5;
	public static final byte OCIE3A = (byte) 4;
	public static final byte OCIE3B = (byte) 3;
	public static final byte TOIE3 = (byte) 2;
	public static final byte OCIE3C = (byte) 1;
	public static final byte OCIE1C = (byte) 0;

	/* Extended Timer Interrupt Flag Register - ETIFR */
	public static final byte ICF3 = (byte) 5;
	public static final byte OCF3A = (byte) 4;
	public static final byte OCF3B = (byte) 3;
	public static final byte TOV3 = (byte) 2;
	public static final byte OCF3C = (byte) 1;
	public static final byte OCF1C = (byte) 0;

	/* MCU general Control Register - MCUCR */
	public static final byte SRE = (byte) 7;
	public static final byte SRW = (byte) 6;
	public static final byte SRW10 = (byte) 6;
	public static final byte SE = (byte) 5;
	public static final byte SM1 = (byte) 4;
	public static final byte SM0 = (byte) 3;
	public static final byte SM2 = (byte) 2;
	public static final byte IVSEL = (byte) 1;
	public static final byte IVCE = (byte) 0;

	/* MCU Status Register - MCUSR, MCUCSR */
	public static final byte JTD = (byte) 7;
	public static final byte JTRF = (byte) 4;
	public static final byte WDRF = (byte) 3;
	public static final byte BORF = (byte) 2;
	public static final byte EXTRF = (byte) 1;
	public static final byte PORF = (byte) 0;

	/* Timer/Counter Control Register (generic) */
	public static final byte FOC = (byte) 7;
	public static final byte WGM0 = (byte) 6;
	public static final byte COM1 = (byte) 5;
	public static final byte COM0 = (byte) 4;
	public static final byte WGM1 = (byte) 3;
	public static final byte CS2 = (byte) 2;
	public static final byte CS1 = (byte) 1;
	public static final byte CS0 = (byte) 0;

	/* Timer/Counter 0 Control Register - TCCR0 */
	public static final byte FOC0 = (byte) 7;
	public static final byte WGM00 = (byte) 6;
	public static final byte COM01 = (byte) 5;
	public static final byte COM00 = (byte) 4;
	public static final byte WGM01 = (byte) 3;
	public static final byte CS02 = (byte) 2;
	public static final byte CS01 = (byte) 1;
	public static final byte CS00 = (byte) 0;

	/* Timer/Counter 2 Control Register - TCCR2 */
	public static final byte FOC2 = (byte) 7;
	public static final byte WGM20 = (byte) 6;
	public static final byte COM21 = (byte) 5;
	public static final byte COM20 = (byte) 4;
	public static final byte WGM21 = (byte) 3;
	public static final byte CS22 = (byte) 2;
	public static final byte CS21 = (byte) 1;
	public static final byte CS20 = (byte) 0;

	/* Timer/Counter 0 Asynchronous Control & Status Register - ASSR */
	public static final byte AS0 = (byte) 3;
	public static final byte TCN0UB = (byte) 2;
	public static final byte OCR0UB = (byte) 1;
	public static final byte TCR0UB = (byte) 0;

	/* Timer/Counter Control Register A (generic) */
	public static final byte COMA1 = (byte) 7;
	public static final byte COMA0 = (byte) 6;
	public static final byte COMB1 = (byte) 5;
	public static final byte COMB0 = (byte) 4;
	public static final byte COMC1 = (byte) 3;
	public static final byte COMC0 = (byte) 2;
	public static final byte WGMA1 = (byte) 1;
	public static final byte WGMA0 = (byte) 0;

	/* Timer/Counter 1 Control and Status Register A - TCCR1A */
	public static final byte COM1A1 = (byte) 7;
	public static final byte COM1A0 = (byte) 6;
	public static final byte COM1B1 = (byte) 5;
	public static final byte COM1B0 = (byte) 4;
	public static final byte COM1C1 = (byte) 3;
	public static final byte COM1C0 = (byte) 2;
	public static final byte WGM11 = (byte) 1;
	public static final byte WGM10 = (byte) 0;

	/* Timer/Counter 3 Control and Status Register A - TCCR3A */
	public static final byte COM3A1 = (byte) 7;
	public static final byte COM3A0 = (byte) 6;
	public static final byte COM3B1 = (byte) 5;
	public static final byte COM3B0 = (byte) 4;
	public static final byte COM3C1 = (byte) 3;
	public static final byte COM3C0 = (byte) 2;
	public static final byte WGM31 = (byte) 1;
	public static final byte WGM30 = (byte) 0;

	/* Timer/Counter Control and Status Register B (generic) */
	public static final byte ICNC = (byte) 7;
	public static final byte ICES = (byte) 6;
	public static final byte WGMB3 = (byte) 4;
	public static final byte WGMB2 = (byte) 3;
	public static final byte CSB2 = (byte) 2;
	public static final byte CSB1 = (byte) 1;
	public static final byte CSB0 = (byte) 0;

	/* Timer/Counter 1 Control and Status Register B - TCCR1B */
	public static final byte ICNC1 = (byte) 7;
	public static final byte ICES1 = (byte) 6;
	public static final byte WGM13 = (byte) 4;
	public static final byte WGM12 = (byte) 3;
	public static final byte CS12 = (byte) 2;
	public static final byte CS11 = (byte) 1;
	public static final byte CS10 = (byte) 0;

	/* Timer/Counter 3 Control and Status Register B - TCCR3B */
	public static final byte ICNC3 = (byte) 7;
	public static final byte ICES3 = (byte) 6;
	public static final byte WGM33 = (byte) 4;
	public static final byte WGM32 = (byte) 3;
	public static final byte CS32 = (byte) 2;
	public static final byte CS31 = (byte) 1;
	public static final byte CS30 = (byte) 0;

	/* Timer/Counter Control Register C (generic) */
	public static final byte FOCA = (byte) 7;
	public static final byte FOCB = (byte) 6;
	public static final byte FOCC = (byte) 5;

	/* Timer/Counter 3 Control Register C - TCCR3C */
	public static final byte FOC3A = (byte) 7;
	public static final byte FOC3B = (byte) 6;
	public static final byte FOC3C = (byte) 5;

	/* Timer/Counter 1 Control Register C - TCCR1C */
	public static final byte FOC1A = (byte) 7;
	public static final byte FOC1B = (byte) 6;
	public static final byte FOC1C = (byte) 5;

	/* On-chip Debug Register - OCDR */
	public static final byte IDRD = (byte) 7;
	public static final byte OCDR7 = (byte) 7;
	public static final byte OCDR6 = (byte) 6;
	public static final byte OCDR5 = (byte) 5;
	public static final byte OCDR4 = (byte) 4;
	public static final byte OCDR3 = (byte) 3;
	public static final byte OCDR2 = (byte) 2;
	public static final byte OCDR1 = (byte) 1;
	public static final byte OCDR0 = (byte) 0;

	/* Watchdog Timer Control Register - WDTCR */
	public static final byte WDCE = (byte) 4;
	public static final byte WDE = (byte) 3;
	public static final byte WDP2 = (byte) 2;
	public static final byte WDP1 = (byte) 1;
	public static final byte WDP0 = (byte) 0;

	/* Special Function I/O Register - SFIOR */
	public static final byte TSM = (byte) 7;
	public static final byte ADHSM = (byte) 4;
	public static final byte ACME = (byte) 3;
	public static final byte PUD = (byte) 2;
	public static final byte PSR0 = (byte) 1;
	public static final byte PSR321 = (byte) 0;

	/* SPI Status Register - SPSR */
	public static final byte SPIF = (byte) 7;
	public static final byte WCOL = (byte) 6;
	public static final byte SPI2X = (byte) 0;

	/* SPI Control Register - SPCR */
	public static final byte SPIE = (byte) 7;
	public static final byte SPE = (byte) 6;
	public static final byte DORD = (byte) 5;
	public static final byte MSTR = (byte) 4;
	public static final byte CPOL = (byte) 3;
	public static final byte CPHA = (byte) 2;
	public static final byte SPR1 = (byte) 1;
	public static final byte SPR0 = (byte) 0;

	/* USART Register C (generic) */
	public static final byte UMSEL = (byte) 6;
	public static final byte UPM1 = (byte) 5;
	public static final byte UPM0 = (byte) 4;
	public static final byte USBS = (byte) 3;
	public static final byte UCSZ1 = (byte) 2;
	public static final byte UCSZ0 = (byte) 1;
	public static final byte UCPOL = (byte) 0;

	/* USART1 Register C - UCSR1C */
	public static final byte UMSEL1 = (byte) 6;
	public static final byte UPM11 = (byte) 5;
	public static final byte UPM10 = (byte) 4;
	public static final byte USBS1 = (byte) 3;
	public static final byte UCSZ11 = (byte) 2;
	public static final byte UCSZ10 = (byte) 1;
	public static final byte UCPOL1 = (byte) 0;

	/* USART0 Register C - UCSR0C */
	public static final byte UMSEL0 = (byte) 6;
	public static final byte UPM01 = (byte) 5;
	public static final byte UPM00 = (byte) 4;
	public static final byte USBS0 = (byte) 3;
	public static final byte UCSZ01 = (byte) 2;
	public static final byte UCSZ00 = (byte) 1;
	public static final byte UCPOL0 = (byte) 0;

	/* USART Status Register A (generic) */
	public static final byte RXC = (byte) 7;
	public static final byte TXC = (byte) 6;
	public static final byte UDRE = (byte) 5;
	public static final byte FE = (byte) 4;
	public static final byte DOR = (byte) 3;
	public static final byte UPE = (byte) 2;
	public static final byte U2X = (byte) 1;
	public static final byte MPCM = (byte) 0;

	/* USART1 Status Register A - UCSR1A */
	public static final byte RXC1 = (byte) 7;
	public static final byte TXC1 = (byte) 6;
	public static final byte UDRE1 = (byte) 5;
	public static final byte FE1 = (byte) 4;
	public static final byte DOR1 = (byte) 3;
	public static final byte UPE1 = (byte) 2;
	public static final byte U2X1 = (byte) 1;
	public static final byte MPCM1 = (byte) 0;

	/* USART0 Status Register A - UCSR0A */
	public static final byte RXC0 = (byte) 7;
	public static final byte TXC0 = (byte) 6;
	public static final byte UDRE0 = (byte) 5;
	public static final byte FE0 = (byte) 4;
	public static final byte DOR0 = (byte) 3;
	public static final byte UPE0 = (byte) 2;
	public static final byte U2X0 = (byte) 1;
	public static final byte MPCM0 = (byte) 0;

	/* USART Control Register B (generic) */
	public static final byte RXCIE = (byte) 7;
	public static final byte TXCIE = (byte) 6;
	public static final byte UDRIE = (byte) 5;
	public static final byte RXEN = (byte) 4;
	public static final byte TXEN = (byte) 3;
	public static final byte UCSZ = (byte) 2;
	public static final byte UCSZ2 = (byte) 2;
	public static final byte RXB8 = (byte) 1;
	public static final byte TXB8 = (byte) 0;

	/* USART1 Control Register B - UCSR1B */
	public static final byte RXCIE1 = (byte) 7;
	public static final byte TXCIE1 = (byte) 6;
	public static final byte UDRIE1 = (byte) 5;
	public static final byte RXEN1 = (byte) 4;
	public static final byte TXEN1 = (byte) 3;
	public static final byte UCSZ12 = (byte) 2;
	public static final byte RXB81 = (byte) 1;
	public static final byte TXB81 = (byte) 0;

	/* USART0 Control Register B - UCSR0B */
	public static final byte RXCIE0 = (byte) 7;
	public static final byte TXCIE0 = (byte) 6;
	public static final byte UDRIE0 = (byte) 5;
	public static final byte RXEN0 = (byte) 4;
	public static final byte TXEN0 = (byte) 3;
	public static final byte UCSZ02 = (byte) 2;
	public static final byte RXB80 = (byte) 1;
	public static final byte TXB80 = (byte) 0;

	/* Analog Comparator Control and Status Register - ACSR */
	public static final byte ACD = (byte) 7;
	public static final byte ACBG = (byte) 6;
	public static final byte ACO = (byte) 5;
	public static final byte ACI = (byte) 4;
	public static final byte ACIE = (byte) 3;
	public static final byte ACIC = (byte) 2;
	public static final byte ACIS1 = (byte) 1;
	public static final byte ACIS0 = (byte) 0;

	/* ADC Control and status register - ADCSRA */
	public static final byte ADEN = (byte) 7;
	public static final byte ADSC = (byte) 6;
	public static final byte ADFR = (byte) 5;
	public static final byte ADIF = (byte) 4;
	public static final byte ADIE = (byte) 3;
	public static final byte ADPS2 = (byte) 2;
	public static final byte ADPS1 = (byte) 1;
	public static final byte ADPS0 = (byte) 0;

	/* ADC Multiplexer select - ADMUX */
	public static final byte REFS1 = (byte) 7;
	public static final byte REFS0 = (byte) 6;
	public static final byte ADLAR = (byte) 5;
	public static final byte MUX4 = (byte) 4;
	public static final byte MUX3 = (byte) 3;
	public static final byte MUX2 = (byte) 2;
	public static final byte MUX1 = (byte) 1;
	public static final byte MUX0 = (byte) 0;

	/* Port A Data Register - PORTA */
	public static final byte PA7 = (byte) 7;
	public static final byte PA6 = (byte) 6;
	public static final byte PA5 = (byte) 5;
	public static final byte PA4 = (byte) 4;
	public static final byte PA3 = (byte) 3;
	public static final byte PA2 = (byte) 2;
	public static final byte PA1 = (byte) 1;
	public static final byte PA0 = (byte) 0;

	/* Port A Data Direction Register - DDRA */
	public static final byte DDA7 = (byte) 7;
	public static final byte DDA6 = (byte) 6;
	public static final byte DDA5 = (byte) 5;
	public static final byte DDA4 = (byte) 4;
	public static final byte DDA3 = (byte) 3;
	public static final byte DDA2 = (byte) 2;
	public static final byte DDA1 = (byte) 1;
	public static final byte DDA0 = (byte) 0;

	/* Port A Input Pins - PINA */
	public static final byte PINA7 = (byte) 7;
	public static final byte PINA6 = (byte) 6;
	public static final byte PINA5 = (byte) 5;
	public static final byte PINA4 = (byte) 4;
	public static final byte PINA3 = (byte) 3;
	public static final byte PINA2 = (byte) 2;
	public static final byte PINA1 = (byte) 1;
	public static final byte PINA0 = (byte) 0;

	/* Port B Data Register - PORTB */
	public static final byte PB7 = (byte) 7;
	public static final byte PB6 = (byte) 6;
	public static final byte PB5 = (byte) 5;
	public static final byte PB4 = (byte) 4;
	public static final byte PB3 = (byte) 3;
	public static final byte PB2 = (byte) 2;
	public static final byte PB1 = (byte) 1;
	public static final byte PB0 = (byte) 0;

	/* Port B Data Direction Register - DDRB */
	public static final byte DDB7 = (byte) 7;
	public static final byte DDB6 = (byte) 6;
	public static final byte DDB5 = (byte) 5;
	public static final byte DDB4 = (byte) 4;
	public static final byte DDB3 = (byte) 3;
	public static final byte DDB2 = (byte) 2;
	public static final byte DDB1 = (byte) 1;
	public static final byte DDB0 = (byte) 0;

	/* Port B Input Pins - PINB */
	public static final byte PINB7 = (byte) 7;
	public static final byte PINB6 = (byte) 6;
	public static final byte PINB5 = (byte) 5;
	public static final byte PINB4 = (byte) 4;
	public static final byte PINB3 = (byte) 3;
	public static final byte PINB2 = (byte) 2;
	public static final byte PINB1 = (byte) 1;
	public static final byte PINB0 = (byte) 0;

	/* Port C Data Register - PORTC */
	public static final byte PC7 = (byte) 7;
	public static final byte PC6 = (byte) 6;
	public static final byte PC5 = (byte) 5;
	public static final byte PC4 = (byte) 4;
	public static final byte PC3 = (byte) 3;
	public static final byte PC2 = (byte) 2;
	public static final byte PC1 = (byte) 1;
	public static final byte PC0 = (byte) 0;

	/* Port C Data Direction Register - DDRC */
	public static final byte DDC7 = (byte) 7;
	public static final byte DDC6 = (byte) 6;
	public static final byte DDC5 = (byte) 5;
	public static final byte DDC4 = (byte) 4;
	public static final byte DDC3 = (byte) 3;
	public static final byte DDC2 = (byte) 2;
	public static final byte DDC1 = (byte) 1;
	public static final byte DDC0 = (byte) 0;

	/* Port C Input Pins - PINC */
	public static final byte PINC7 = (byte) 7;
	public static final byte PINC6 = (byte) 6;
	public static final byte PINC5 = (byte) 5;
	public static final byte PINC4 = (byte) 4;
	public static final byte PINC3 = (byte) 3;
	public static final byte PINC2 = (byte) 2;
	public static final byte PINC1 = (byte) 1;
	public static final byte PINC0 = (byte) 0;

	/* Port D Data Register - PORTD */
	public static final byte PD7 = (byte) 7;
	public static final byte PD6 = (byte) 6;
	public static final byte PD5 = (byte) 5;
	public static final byte PD4 = (byte) 4;
	public static final byte PD3 = (byte) 3;
	public static final byte PD2 = (byte) 2;
	public static final byte PD1 = (byte) 1;
	public static final byte PD0 = (byte) 0;

	/* Port D Data Direction Register - DDRD */
	public static final byte DDD7 = (byte) 7;
	public static final byte DDD6 = (byte) 6;
	public static final byte DDD5 = (byte) 5;
	public static final byte DDD4 = (byte) 4;
	public static final byte DDD3 = (byte) 3;
	public static final byte DDD2 = (byte) 2;
	public static final byte DDD1 = (byte) 1;
	public static final byte DDD0 = (byte) 0;

	/* Port D Input Pins - PIND */
	public static final byte PIND7 = (byte) 7;
	public static final byte PIND6 = (byte) 6;
	public static final byte PIND5 = (byte) 5;
	public static final byte PIND4 = (byte) 4;
	public static final byte PIND3 = (byte) 3;
	public static final byte PIND2 = (byte) 2;
	public static final byte PIND1 = (byte) 1;
	public static final byte PIND0 = (byte) 0;

	/* Port E Data Register - PORTE */
	public static final byte PE7 = (byte) 7;
	public static final byte PE6 = (byte) 6;
	public static final byte PE5 = (byte) 5;
	public static final byte PE4 = (byte) 4;
	public static final byte PE3 = (byte) 3;
	public static final byte PE2 = (byte) 2;
	public static final byte PE1 = (byte) 1;
	public static final byte PE0 = (byte) 0;

	/* Port E Data Direction Register - DDRE */
	public static final byte DDE7 = (byte) 7;
	public static final byte DDE6 = (byte) 6;
	public static final byte DDE5 = (byte) 5;
	public static final byte DDE4 = (byte) 4;
	public static final byte DDE3 = (byte) 3;
	public static final byte DDE2 = (byte) 2;
	public static final byte DDE1 = (byte) 1;
	public static final byte DDE0 = (byte) 0;

	/* Port E Input Pins - PINE */
	public static final byte PINE7 = (byte) 7;
	public static final byte PINE6 = (byte) 6;
	public static final byte PINE5 = (byte) 5;
	public static final byte PINE4 = (byte) 4;
	public static final byte PINE3 = (byte) 3;
	public static final byte PINE2 = (byte) 2;
	public static final byte PINE1 = (byte) 1;
	public static final byte PINE0 = (byte) 0;

	/* Port F Data Register - PORTF */
	public static final byte PF7 = (byte) 7;
	public static final byte PF6 = (byte) 6;
	public static final byte PF5 = (byte) 5;
	public static final byte PF4 = (byte) 4;
	public static final byte PF3 = (byte) 3;
	public static final byte PF2 = (byte) 2;
	public static final byte PF1 = (byte) 1;
	public static final byte PF0 = (byte) 0;

	/* Port F Data Direction Register - DDRF */
	public static final byte DDF7 = (byte) 7;
	public static final byte DDF6 = (byte) 6;
	public static final byte DDF5 = (byte) 5;
	public static final byte DDF4 = (byte) 4;
	public static final byte DDF3 = (byte) 3;
	public static final byte DDF2 = (byte) 2;
	public static final byte DDF1 = (byte) 1;
	public static final byte DDF0 = (byte) 0;

	/* Port F Input Pins - PINF */
	public static final byte PINF7 = (byte) 7;
	public static final byte PINF6 = (byte) 6;
	public static final byte PINF5 = (byte) 5;
	public static final byte PINF4 = (byte) 4;
	public static final byte PINF3 = (byte) 3;
	public static final byte PINF2 = (byte) 2;
	public static final byte PINF1 = (byte) 1;
	public static final byte PINF0 = (byte) 0;

	/* Port G Data Register - PORTG */
	public static final byte PG4 = (byte) 4;
	public static final byte PG3 = (byte) 3;
	public static final byte PG2 = (byte) 2;
	public static final byte PG1 = (byte) 1;
	public static final byte PG0 = (byte) 0;

	/* Port G Data Direction Register - DDRG */
	public static final byte DDG4 = (byte) 4;
	public static final byte DDG3 = (byte) 3;
	public static final byte DDG2 = (byte) 2;
	public static final byte DDG1 = (byte) 1;
	public static final byte DDG0 = (byte) 0;

	/* Port G Input Pins - PING */
	public static final byte PING4 = (byte) 4;
	public static final byte PING3 = (byte) 3;
	public static final byte PING2 = (byte) 2;
	public static final byte PING1 = (byte) 1;
	public static final byte PING0 = (byte) 0;

	/* Constants */
	public static final byte SPM_PAGESIZE = (byte) 256;
	public static final byte RAMEND = (byte) 0;
	public static final byte XRAMEND = (byte) 0;
	public static final byte E2END = (byte) 0;
	public static final byte FLASHEND = (byte) 0;

	/* _AVR_IOM128_H_ */
	/* Memory maped IO registers */

	/* Address offset 0 */
	public MT_U8 PINF;

	/* Address offset 1 */
	public MT_U8 PINE;

	/* Address offset 2 */
	public MT_U8 DDRE;

	/* Address offset 3 */
	public MT_U8 PORTE;

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
	public MT_U8 UBRR0L;

	/* Address offset 10 */
	public MT_U8 UCSR0B;

	/* Address offset 11 */
	public MT_U8 UCSR0A;

	/* Address offset 12 */
	public MT_U8 UDR0;

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
	public MT_U8 SFIOR;

	/* Address offset 33 */
	public MT_U8 WDTCR;

	/* Address offset 34 */
	public MT_U8 OCDR;

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
	public MT_U8 ASSR;

	/* Address offset 49 */
	public MT_U8 OCR0;

	/* Address offset 50 */
	public MT_U8 TCNT0;

	/* Address offset 51 */
	public MT_U8 TCCR0;

	/* Address offset 52 */
	public MT_U8 MCUCSR;

	/* Address offset 53 */
	public MT_U8 MCUCR;

	/* Address offset 54 */
	public MT_U8 TIFR;

	/* Address offset 55 */
	public MT_U8 TIMSK;

	/* Address offset 56 */
	public MT_U8 EIFR;

	/* Address offset 57 */
	public MT_U8 EIMSK;

	/* Address offset 58 */
	public MT_U8 EICRB;

	/* Address offset 59 */
	public MT_U8 RAMPZ;

	/* Address offset 60 */
	public MT_U8 XDIV;

	/* Address offset 61 */
	public MT_U8 SPL;

	/* Address offset 62 */
	public MT_U8 SPH;

	/* Address offset 63 */
	public MT_U8 SREG;

	/* Address offset 64 */
	private MT_U8 REGISTER_64;

	/* Address offset 65 */
	private MT_U8 REGISTER_65;

	/* Address offset 66 */
	private MT_U8 REGISTER_66;

	/* Address offset 67 */
	private MT_U8 REGISTER_67;

	/* Address offset 68 */
	private MT_U8 REGISTER_68;

	/* Address offset 69 */
	private MT_U8 REGISTER_69;

	/* Address offset 70 */
	private MT_U8 REGISTER_70;

	/* Address offset 71 */
	private MT_U8 REGISTER_71;

	/* Address offset 72 */
	private MT_U8 REGISTER_72;

	/* Address offset 73 */
	private MT_U8 REGISTER_73;

	/* Address offset 74 */
	private MT_U8 REGISTER_74;

	/* Address offset 75 */
	private MT_U8 REGISTER_75;

	/* Address offset 76 */
	private MT_U8 REGISTER_76;

	/* Address offset 77 */
	private MT_U8 REGISTER_77;

	/* Address offset 78 */
	private MT_U8 REGISTER_78;

	/* Address offset 79 */
	private MT_U8 REGISTER_79;

	/* Address offset 80 */
	private MT_U8 REGISTER_80;

	/* Address offset 81 */
	private MT_U8 REGISTER_81;

	/* Address offset 82 */
	private MT_U8 REGISTER_82;

	/* Address offset 83 */
	private MT_U8 REGISTER_83;

	/* Address offset 84 */
	private MT_U8 REGISTER_84;

	/* Address offset 85 */
	private MT_U8 REGISTER_85;

	/* Address offset 86 */
	private MT_U8 REGISTER_86;

	/* Address offset 87 */
	private MT_U8 REGISTER_87;

	/* Address offset 88 */
	private MT_U8 REGISTER_88;

	/* Address offset 89 */
	private MT_U8 REGISTER_89;

	/* Address offset 90 */
	private MT_U8 REGISTER_90;

	/* Address offset 91 */
	private MT_U8 REGISTER_91;

	/* Address offset 92 */
	private MT_U8 REGISTER_92;

	/* Address offset 93 */
	private MT_U8 REGISTER_93;

	/* Address offset 94 */
	private MT_U8 REGISTER_94;

	/* Address offset 95 */
	private MT_U8 REGISTER_95;

	/* Address offset 96 */
	private MT_U8 REGISTER_96;

	/* Address offset 97 */
	public MT_U8 DDRF;

	/* Address offset 98 */
	public MT_U8 PORTF;

	/* Address offset 99 */
	public MT_U8 PING;

	/* Address offset 100 */
	public MT_U8 DDRG;

	/* Address offset 101 */
	public MT_U8 PORTG;

	/* Address offset 102 */
	private MT_U8 REGISTER_102;

	/* Address offset 103 */
	private MT_U8 REGISTER_103;

	/* Address offset 104 */
	public MT_U8 SPMCSR;

	/* Address offset 105 */
	private MT_U8 REGISTER_105;

	/* Address offset 106 */
	public MT_U8 EICRA;

	/* Address offset 107 */
	private MT_U8 REGISTER_107;

	/* Address offset 108 */
	public MT_U8 XMCRB;

	/* Address offset 109 */
	public MT_U8 XMCRA;

	/* Address offset 110 */
	private MT_U8 REGISTER_110;

	/* Address offset 111 */
	public MT_U8 OSCCAL;

	/* Address offset 112 */
	public MT_U8 TWBR;

	/* Address offset 113 */
	public MT_U8 TWSR;

	/* Address offset 114 */
	public MT_U8 TWAR;

	/* Address offset 115 */
	public MT_U8 TWDR;

	/* Address offset 116 */
	public MT_U8 TWCR;

	/* Address offset 117 */
	private MT_U8 REGISTER_117;

	/* Address offset 118 */
	private MT_U8 REGISTER_118;

	/* Address offset 119 */
	private MT_U8 REGISTER_119;

	/* Address offset 120 */
	public MT_U8 OCR1CL;

	/* Address offset 121 */
	public MT_U8 OCR1CH;

	/* Address offset 122 */
	public MT_U8 TCCR1C;

	/* Address offset 123 */
	private MT_U8 REGISTER_123;

	/* Address offset 124 */
	public MT_U8 ETIFR;

	/* Address offset 125 */
	public MT_U8 ETIMSK;

	/* Address offset 126 */
	private MT_U8 REGISTER_126;

	/* Address offset 127 */
	private MT_U8 REGISTER_127;

	/* Address offset 128 */
	public MT_U8 ICR3L;

	/* Address offset 129 */
	public MT_U8 ICR3H;

	/* Address offset 130 */
	public MT_U8 OCR3CL;

	/* Address offset 131 */
	public MT_U8 OCR3CH;

	/* Address offset 132 */
	public MT_U8 OCR3BL;

	/* Address offset 133 */
	public MT_U8 OCR3BH;

	/* Address offset 134 */
	public MT_U8 OCR3AL;

	/* Address offset 135 */
	public MT_U8 OCR3AH;

	/* Address offset 136 */
	public MT_U8 TCNT3L;

	/* Address offset 137 */
	public MT_U8 TCNT3H;

	/* Address offset 138 */
	public MT_U8 TCCR3B;

	/* Address offset 139 */
	public MT_U8 TCCR3A;

	/* Address offset 140 */
	public MT_U8 TCCR3C;

	/* Address offset 141 */
	private MT_U8 REGISTER_141;

	/* Address offset 142 */
	private MT_U8 REGISTER_142;

	/* Address offset 143 */
	private MT_U8 REGISTER_143;

	/* Address offset 144 */
	public MT_U8 UBRR0H;

	/* Address offset 145 */
	private MT_U8 REGISTER_145;

	/* Address offset 146 */
	private MT_U8 REGISTER_146;

	/* Address offset 147 */
	private MT_U8 REGISTER_147;

	/* Address offset 148 */
	private MT_U8 REGISTER_148;

	/* Address offset 149 */
	public MT_U8 UCSR0C;

	/* Address offset 150 */
	private MT_U8 REGISTER_150;

	/* Address offset 151 */
	private MT_U8 REGISTER_151;

	/* Address offset 152 */
	public MT_U8 UBRR1H;

	/* Address offset 153 */
	public MT_U8 UBRR1L;

	/* Address offset 154 */
	public MT_U8 UCSR1B;

	/* Address offset 155 */
	public MT_U8 UCSR1A;

	/* Address offset 156 */
	public MT_U8 UDR1;

	/* Address offset 157 */
	public MT_U8 UCSR1C;

	public final static ATMega128 registers = (ATMega128) MemoryService.mapStaticDeviceMemory(0x20, "keso/driver/avr/atmega128/ATMega128");

}
