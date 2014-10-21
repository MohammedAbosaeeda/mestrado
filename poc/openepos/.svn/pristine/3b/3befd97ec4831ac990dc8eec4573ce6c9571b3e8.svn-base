package test_rpc;

import keso.core.*;
import keso.driver.avr.atmega8535.*;


public class TestServiceProvider extends Task implements TestService, Service {

    public TestServiceProvider() {
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

        /*
        Usart port = new Usart(38400);
        Memory mem = MemoryService.allocStaticMemory(16);

        for (byte i = (byte) 0; i < 16; ++i) {
            mem.set8(i, i);
        }

        port.write(mem, (short) 16);
        
        for (int i = 0; i < 100000000; ++i) {
            ;
        }
        */
        
        PortalService.handlePackets("TestService", "TestNetwork");
    } 

    private static void ledToggle() {
        if (ATMega8535.registers.PORTD.isBitSet(ATMega8535.PD5)) {
            ATMega8535.registers.PORTD.clearBit(ATMega8535.PD5);
        } else {
            ATMega8535.registers.PORTD.setBit(ATMega8535.PD5);
        } 
    }

    
    /* Service interface methods */
    public void test() {
        ledToggle();
    }

    public byte test(byte value0, byte value1) {
        ledToggle();
        return value0; // ^ value1;
    }
    
    public int test(int value0, int value1) {
        ledToggle();
        return value0; // ^ value1;
    }

    public int test(int value0, int value1, int value2, int value3) {
        ledToggle();
        return value0; // ^ value1 ^ value2 ^ value3;
    }
} 


