package test_can;

import keso.core.*;
import keso.driver.can.*;
import keso.driver.can.i82527.*;
import keso.driver.avr.atmega8535.*;

import robertino.mdsa.*;

public class Receiver extends Task {

    
    public static void ledToggle() {
        if (ATMega8535.isBitClear(ATMega8535.PIND, ATMega8535.PIND5)) {
            ATMega8535.sbi(ATMega8535.PORTD, ATMega8535.PD5);
        } else { 
            ATMega8535.cbi(ATMega8535.PORTD, ATMega8535.PD5);
        }
    }
 
    private static void myWait() {
        for (short i = 0; i < 3000; ++i) {
        }
    }

    public void launch() {
        
        ATMega8535.cbi(ATMega8535.PORTD, ATMega8535.PD5);
        ATMega8535.sbi(ATMega8535.DDRD, ATMega8535.DDD5);
        
        ATMega8535CanDriver port = new ATMega8535CanDriver();
        port.setReceiveCanIdentifer(new CanIdentifer(100));
        port.setReceiveMask(new CanIdentifer(0xffffffff));
        
        MotorDriver.init();

        Usart serial = new Usart();
        
        for (;;) {

            ledToggle();

            serial.write(port.read());
        }
    }
}
