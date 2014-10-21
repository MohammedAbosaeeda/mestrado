package keso.driver.avr.atmega8535;

/**
 *
 * @author Ralf Ellner
 */
public final class Adc {
    
    static {
        
        ATMega8535.registers.ADCSRA.set((1 << ATMega8535.ADEN) | (1 << ATMega8535.ADPS2) | (1 << ATMega8535.ADPS1) | (1 << ATMega8535.ADPS0));
        
    }
    
    public static short getValue(byte channel) {
        
        short ret;
        
        // select input channel
        ATMega8535.registers.ADMUX.set((ATMega8535.registers.ADMUX.get() & 0xf8) | (channel & 0x07));
        
        // start conversion
        // a conversion takes 13 ADC-cycles, i.e. 0.1ms for 115.2kHz ADC-clock
        // the very first conversion after switching on the ADS takes 25 ADC cycles
        ATMega8535.registers.ADCSRA.setBit(ATMega8535.ADSC);
        
        // wait for conversion to finish
        while ((ATMega8535.registers.ADCSRA.get() & (1 << ATMega8535.ADIF)) == 0);
        
        // read ADCL and then ADCH
        ret = (short) ATMega8535.registers.ADCL.get();
        ret |= (ATMega8535.registers.ADCH.get() << 8);
        
        return ret;
    
    }
    
}
