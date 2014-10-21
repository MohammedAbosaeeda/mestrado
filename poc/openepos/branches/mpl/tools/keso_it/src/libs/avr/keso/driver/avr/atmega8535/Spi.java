package keso.driver.avr.atmega8535;

/**
 *
 * @author Ralf Ellner
 */
public final class Spi {
    
    /**
     * Initialize the SPI interface.
     */
    static {
        // NOTE: This must be done before selecting the master mode 
		// as it will otherwise be disabled again
		ATMega8535.registers.PORTB.setBit(ATMega8535.PB4);      // SS high
        ATMega8535.registers.DDRB.setBit(ATMega8535.DDB4);      // SS as output

	   	ATMega8535.registers.DDRB.setBit(ATMega8535.DDB7);      // SCK as output 
        ATMega8535.registers.DDRB.setBit(ATMega8535.DDB5);      // MOSI as output
        
        ATMega8535.registers.SPCR.set((byte) ((1 << ATMega8535.MSTR ) | (1 << ATMega8535.SPE)));       // Enable SPI
    }
    

    public static void chipSelect() {
        ATMega8535.registers.PORTB.clearBit(ATMega8535.PB4);     // Set SS to zero (chip select)
    }

    
    
    /**
     * Write a byte value to the SPI interface and read a byte value back.
     * @param data Data to write to the SPI interface.
     * @return the value read from the SPI interface.
     */
    public static byte transfer(byte data) {
        ATMega8535.registers.SPDR.set(data);
        
        while (!(ATMega8535.registers.SPSR.isBitSet(ATMega8535.SPIF)));
        
        return (byte) ATMega8535.registers.SPDR.get();
    }
    
}
