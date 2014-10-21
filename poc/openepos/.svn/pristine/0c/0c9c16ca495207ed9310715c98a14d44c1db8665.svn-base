package test_can;

import keso.core.*;
import keso.driver.can.*;
import keso.driver.can.i82527.*;
import keso.driver.avr.atmega8535.*;

import robertino.mdsa.*;

public class Sender extends Task {

    
    public static void ledToggle() {
        if (ATMega8535.isBitClear(ATMega8535.PIND, ATMega8535.PIND5)) {
            ATMega8535.sbi(ATMega8535.PORTD, ATMega8535.PD5);
        } else { 
            ATMega8535.cbi(ATMega8535.PORTD, ATMega8535.PD5);
        }
    }
    
    private static void myWait() {
        for (short i = 0; i < 30000; ++i) {
            for (byte j = 0; j < 10; ++j) {
            }
        }
    }
    
    public void launch() {
         
        ATMega8535.cbi(ATMega8535.PORTD, ATMega8535.PD5);
        ATMega8535.sbi(ATMega8535.DDRD, ATMega8535.DDD5);
        
        ATMega8535CanDriver port = new ATMega8535CanDriver();

        port.setTransmitCanIdentifer(new CanIdentifer(100));
        
        MotorDriver.init();

        byte i = (byte) 0;
        

        
        for (;;) {
            myWait();
            MotorDriver.setDesiredSpeed(++i); 
            
            port.write((byte) 0xaa);
            port.write((byte) 4);
            port.write((byte) 0);
            port.write((byte) 2);
            port.write((byte) i);
            port.write((byte) 0); 
             
            port.flush();
            
            ledToggle();
        }
    }
}
