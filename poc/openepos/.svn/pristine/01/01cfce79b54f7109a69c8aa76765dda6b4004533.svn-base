package test_rpc;

import keso.core.*;
import keso.driver.avr.atmega8535.*;

public class TestServiceUser extends Task {

    public TestServiceUser() {
         // enable pullups to prevent floating inputs
        /*
		ATMega8535.registers.PORTA.set((byte) 0xff);
        ATMega8535.registers.PORTB.set((byte) 0xff);
        ATMega8535.registers.PORTC.set((byte) 0xff);
        ATMega8535.registers.PORTD.set((byte) 0xfc);
        */
        ATMega8535.registers.DDRD.setBit(ATMega8535.DDD5);
    }

    public void launch() {

        InterruptService.enableAll();
        
        for (int i = 0; i < 10000; ++i) {
            ;
        }

        for (;;) {

            /*
             * This is used for performance measurement of remote procedure calls with an 
             * oscillosscope attached to the pin 5 of PORTD.
             */  
            ledToggle();    
            
            ((TestService) PortalService.lookup("TestService")).test();
            //((TestService) PortalService.lookup("TestService")).test((byte) 0xa0, (byte) 0xb0);
            //((TestService) PortalService.lookup("TestService")).test(0x0, 0x0);
            //((TestService) PortalService.lookup("TestService")).test(0x0, 0x0, 0x0, 0x0);

           
        }
    }

   private static void ledToggle() {
        if (ATMega8535.registers.PORTD.isBitSet(ATMega8535.PD5)) {
            ATMega8535.registers.PORTD.clearBit(ATMega8535.PD5);
        } else {
            ATMega8535.registers.PORTD.setBit(ATMega8535.PD5);
        } 
    }


}
