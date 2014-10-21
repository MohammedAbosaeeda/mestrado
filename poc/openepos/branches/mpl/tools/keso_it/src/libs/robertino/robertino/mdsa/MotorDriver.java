package robertino.mdsa;

import keso.driver.avr.atmega8535.*;
import keso.core.*;


/**
 *
 * @author Ralf Ellner
 */
public final class MotorDriver {
    
    private static final byte DIRECTION_LEFT = 0;
    private static final byte DIRECTION_RIGHT = 1;
    private static final byte TICK_FACTOR = (byte) 1;       // depends upon used crystal - 7.xxx MHz: 1, 14.xxx MHz: 2
    private static final short MAX_MOTOR_CURRENT = (short) 500;     // max allowed current value measured by the ad cnverter
    public static final short MAX_TICKS_PER_LOOP = (short) 600;     // Max value that can be regulated
    public static final short STUCK_ERROR_LEVEL = (short) 60;       // if error is larger than this value the wheel is stuck
    
    
    
    private static short desiredTicksPerLoop;
    private static short outputPower;
    private static boolean stuck;

    /*
     *
     *  Pin definitions
     *
     *  PB0: Direction info of LS71845 H-Bridge     (INPUT)
     *  PB1: Clock of motor encoder                 (INPUT)
     *  PB2: Brake                                  (OUTPUT)
     *  PB3: Direction output                       (OUTPUT)
     *
     *  
     */
    public static void init() {
        
        /*
         * 
         * Init the H-Bridge
         *
         */
        
        // Direction output
        //ATMega8535.cbi(ATMega8535.PORTB, ATMega8535.PB3);   // Set initial direction of motor
        //ATMega8535.sbi(ATMega8535.DDRB, ATMega8535.DDB3);   // Set direction pin as output
        ATMega8535.registers.DDRB.setBit(ATMega8535.DDB3);      // Set direction pin as output
        
        // Brake
        //ATMega8535.cbi(ATMega8535.PORTB, ATMega8535.PB2);   // Switch brake off
        //ATMega8535.sbi(ATMega8535.DDRB, ATMega8535.DDB2);   // Set brake pin as output
        ATMega8535.registers.DDRB.setBit(ATMega8535.DDB2);      // Set brake pin as output
        
        // Direction info of LS71845
        //ATMega8535.sbi(ATMega8535.PORTB, ATMega8535.PB0);   // Enable pullup resistor
        //ATMega8535.cbi(ATMega8535.DDRB, ATMega8535.DDB0);   // Set direction info pin as input
        ATMega8535.registers.PORTB.setBit(ATMega8535.PB0);      // Enable pullup resistor
   

        
        /* 
         *
         * Init the PWM
         *
         */
        
        /* clear timer/counter 2 */
        ATMega8535.registers.TCNT2.set(0x00);
        
        // no prescaling
        //ATMega8535.cbi(ATMega8535.TCCR2, ATMega8535.CS22); // Clock Select Bits for Prescaling
        //ATMega8535.cbi(ATMega8535.TCCR2, ATMega8535.CS21); // Clock Select Bits for Prescaling
        ATMega8535.registers.TCCR2.set((1 <<ATMega8535.CS20) | (1 << ATMega8535.WGM21) | (1 << ATMega8535.WGM20) | (1 << ATMega8535.COM21));
        
        
        // fast pwm mode
        //ATMega8535.sbi(ATMega8535.TCCR2, ATMega8535.WGM21); // Clock Select Bits for Prescaling
        //ATMega8535.sbi(ATMega8535.TCCR2, ATMega8535.WGM20); // Clock Select Bits for Prescaling
        
        // non-inverted pwm
        //ATMega8535.sbi(ATMega8535.TCCR2, ATMega8535.COM21); // Clock Select Bits for Prescaling
        //ATMega8535.cbi(ATMega8535.TCCR2, ATMega8535.COM20); // Clock Select Bits for Prescaling
        
        ATMega8535.registers.OCR2.set(0x00); // set output compare value for PWM
        
        /*  DDD7 is also OC2, it must be configured as output */
        ATMega8535.registers.DDRD.setBit(ATMega8535.DDD7);  /* output: pwm */
        

        /*
         *
         * Init the Counter
         *
         */

        // rot. speed clock input
        //ATMega8535.sbi(ATMega8535.PORTB, ATMega8535.PB1);   // Enable pullup
        //ATMega8535.cbi(ATMega8535.DDRB, ATMega8535.DDB1);   // Set clock pin as input
        ATMega8535.registers.PORTB.setBit(ATMega8535.PB1);      // Enable pullup
        
        // Set timer clock source to pin PB1, falling edge
        ATMega8535.registers.TCCR1B.setBit(ATMega8535.CS12); 
        ATMega8535.registers.TCCR1B.setBit(ATMega8535.CS11);
        
        //ATMega8535.cbi(ATMega8535.TCCR1B, ATMega8535.CS10);


        
        /*
         *
         * Set initial speed and direction
         *
         */
        setDesiredSpeed((short) 0);
        outputPower = (short) 0;
        stuck = false;

        /*
         *
         *
         * Init timer0 interrupts
         *
         * Motor speed is controlled in interrupt handler.
         * Interrupt frequency is 450 Hz = control loop frequency
         * 
         */

        ATMega8535.registers.TCCR0.setBit(ATMega8535.CS00);        
        ATMega8535.registers.TCCR0.setBit(ATMega8535.CS01);
        //ATMega8535.cbi(ATMega8535.TCCR0, ATMega8535.CS02); 
        ATMega8535.registers.TIMSK.setBit(ATMega8535.TOIE0);
        
    }




    /*
     *
     * TOV0 Interrupt handler controlling the motor speed
     *
     */
    private static void control() {
        
        short motorCurrentInLastLoop = Adc.getValue((byte) 0);     // read motor current for last loop   
        
        short ticksInLastLoop = (short) (getTicks() * TICK_FACTOR);   // Encoder pulses during last loop.
        
        /*
         * ticksInLastLoop should be max. 600. The motor does about 7800 rpm when idle at 12 V. The encoder gives 
         * 512 pulses per motor revolution. This results in 7800 * 512 / 60 = 66560 pulses per second.
         * The encoder gives two signals that are phase shifted by 1/2. On any edge of the two signals the clock
         * input is pulled low for about 1 us and the counter1 is incremented. Thus the resulting pulse frequency
         * is four times higher: 66560 * 4 = 266240 pulses per second.
         * The loop is executed at a frequency of 450 Hz. 266240 / 450 ~= 600 pulses per loop.
         *
         */

        resetTicks();   // Reset encoder pulses to zero for next loop

        if (motorCurrentInLastLoop > MAX_MOTOR_CURRENT) {     // check for over current
            
            stuck = true;
            
            if (outputPower < 0) {
                ++outputPower;
            } else if (outputPower > 0) {
                --outputPower;
            }
        } else {

            if (getDirection() != DIRECTION_LEFT) {
                ticksInLastLoop *= -1;                  // check actual direction
            }

            short error = (short) (desiredTicksPerLoop - ticksInLastLoop);

            if (error < 0) {
                if (error < -4) {       // large error - compensate more
                    outputPower += (error >> 2);

                    if (error < (-1 * STUCK_ERROR_LEVEL)) {
                        stuck = true;
                    } else {
                        stuck = false;
                    }
                    
                } else {
                    --outputPower;
                }
            } else if (error > 0) {
                if (error > 4) {        // large error - compensate more
                    outputPower += (error >> 2);

                    if (error > STUCK_ERROR_LEVEL) {
                        stuck = true;
                    } else {
                        stuck = false;
                    }

                } else {
                    ++outputPower;
                }
            }

        }

        if (outputPower < 0) {
            setDirection(DIRECTION_RIGHT);
            
            if (outputPower < -255) {
                outputPower = -255;
            }

            setPower((byte) (-1 * outputPower));
        } else {
            setDirection(DIRECTION_LEFT);
            
            if (outputPower > 255) {
                outputPower = (short) 255;
            }

            setPower((byte) outputPower);
        }

    }
    

    public static boolean isStuck() {
        return stuck;
    }
    
    public static void setDesiredSpeed(short value) {
       
        // Limit value
        if (value > MAX_TICKS_PER_LOOP) {
            value = MAX_TICKS_PER_LOOP;
        } else if (value < (-1 * MAX_TICKS_PER_LOOP)) {
            value = (short) (-1 * MAX_TICKS_PER_LOOP);
        }

        byte sreg = (byte) ATMega8535.registers.SREG.get();    // save status register 
        InterruptService.disableAll();   // desiredTicksPerLoop is used in ISR - write must be made atomic
        desiredTicksPerLoop = value;

        ATMega8535.registers.SREG.set(sreg);     // reenable interrupts if they were enabled
    }
    
    
    private static void setDirection(byte value) {
        if (value == DIRECTION_LEFT) {
            ATMega8535.registers.PORTB.clearBit(ATMega8535.PB3);
        } else {
            ATMega8535.registers.PORTB.setBit(ATMega8535.PB3);
        }
    }

    
    private static byte getDirection() {
        if (ATMega8535.registers.PINB.isBitSet(ATMega8535.PINB0)) {
            return DIRECTION_RIGHT;
        }
        return DIRECTION_LEFT;
    }

    
    private static void setPower(byte value) {
        
        while (ATMega8535.registers.ASSR.isBitSet(ATMega8535.OCR2UB));    // Wait until OCR2 can be written
        
        if (value != 0) {
            ATMega8535.registers.PORTB.clearBit(ATMega8535.PB2);   // switch brake off
            ATMega8535.registers.OCR2.set(value);
        } else {
            ATMega8535.registers.PORTB.setBit(ATMega8535.PB2);   // switch brake on
            ATMega8535.registers.OCR2.set((byte) 0xff);
        }
    }    
    
    
    private static short getTicks() {

        short result = (short) ATMega8535.registers.TCNT1L.get();
        result |= (short) (ATMega8535.registers.TCNT1H.get() << 8);

        return result;

    }

    private static void resetTicks() {

        ATMega8535.registers.TCNT1H.set(0); 
        ATMega8535.registers.TCNT1L.set(0);

    }
    
}
