package robertino.mdsa;

import keso.driver.avr.atmega8535.*;
import keso.core.*;


public class MDSA implements MDSAService, Service {
    
    public static final byte AD_CHANNEL_DISTANCE_SENSOR_LEFT = (byte) 1;
    public static final byte AD_CHANNEL_DISTANCE_SENSOR_RIGHT = (byte) 5;
    
    public MDSA() {
        //ATMega8535.registers.PORTD.clearBit(ATMega8535.PD5);
        ATMega8535.registers.DDRD.setBit(ATMega8535.DDD5);
        MotorDriver.init();
        
        InterruptService.enableAll();
    }

    
    public static void ledToggle() {
        /*
        if (ATMega8535.registers.PIND.isBitClear(ATMega8535.PIND5)) {
            ATMega8535.registers.PORTD.setBit(ATMega8535.PD5);
        } else { 
            ATMega8535.registers.PORTD.clearBit(ATMega8535.PD5);
        }
        */
        ATMega8535.registers.PIND.setBit(ATMega8535.PIND5);
    }


    /*
     * Service methods
     *
     */
    public void setMotorSpeed(short value) {
        ledToggle();
        MotorDriver.setDesiredSpeed(value);
    }

    public short readADValue(byte channel) {
        ledToggle();
        InterruptService.disableAll();
        short value = Adc.getValue(channel);
        InterruptService.enableAll();
        return value;  
    }
   
    public boolean isMotorStuck() {
        return MotorDriver.isStuck();
    }
   
             
}
