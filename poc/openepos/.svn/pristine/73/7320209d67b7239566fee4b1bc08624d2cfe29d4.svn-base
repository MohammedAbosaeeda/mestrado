package keso.driver.can.i82527;


import keso.driver.avr.atmega8535.*;
import keso.driver.can.*;
import keso.core.*;

/**
 *
 * @author Ralf Ellner
 */
public final class ATMega8535CanDriver implements CanPort {
   
    /** Control Register, value after hardware reset is 0x01 */
    public final static byte CR             = (byte) 0x00;
    public final static byte CR_CCE         = (byte) 0x40;
    public final static byte CR_EIE         = (byte) 0x08;
    public final static byte CR_SIE         = (byte) 0x04;
    public final static byte CR_IE          = (byte) 0x02;
    public final static byte CR_INIT        = (byte) 0x01;
    
    /** Status Register */
    public final static byte SR             = (byte) 0x01;
    public final static byte SR_BOFF        = (byte) 0x80;
    public final static byte SR_WARN        = (byte) 0x40;
    public final static byte SR_WAKE        = (byte) 0x20;
    public final static byte SR_RXOK        = (byte) 0x10;
    public final static byte SR_TXOK        = (byte) 0x08;
    public final static byte SR_LEC         = (byte) 0x07;
    
    /** CPU Interface Register */
    public final static byte CPUIR          = (byte) 0x02;
    public final static byte CPUIR_RSTST    = (byte) 0x80; // Hardware Reset Status
    public final static byte CPUIR_DSC      = (byte) 0x40; // Divide System Clock
    public final static byte CPUIR_DMC      = (byte) 0x20; // Divide Memory Clock
    public final static byte CPUIR_PWD      = (byte) 0x10; // Power Down Mode enable
    public final static byte CPUIR_SLEEP    = (byte) 0x08; // Sleep Mode enable
    public final static byte CPUIR_MUX      = (byte) 0x04; // Multiplex for low speed ..
    public final static byte CPUIR_CEN      = (byte) 0x01; // Clockout enable
    
    
    /** High Speed Read Register */
    public final static byte HSRR           = (byte) 0x04;
    public final static byte HSRR_LOW       = (byte) 0x04;
    public final static byte HSRR_HIGH      = (byte) 0x05;
    
    
    /** Global Mask-Standard Register */
    public final static byte GMSR_0         = (byte) 0x06;
    public final static byte GMSR_1         = (byte) 0x07;
    public final static byte GMSR_END       = (byte) 0x07;
    
    /** Global Mask-Extended Register */
    public final static byte GMER_0         = (byte) 0x08;
    public final static byte GMER_1         = (byte) 0x09;
    public final static byte GMER_2         = (byte) 0x0a;
    public final static byte GMER_3         = (byte) 0x0b;
    public final static byte GMER_END       = (byte) 0x0b;
    
    
    /** Message 15 Mask Register */
    public final static byte M15MR          = (byte) 0x0c;
    public final static byte M15MR_0        = (byte) 0x0c;
    public final static byte M15MR_1        = (byte) 0x0d;
    public final static byte M15MR_2        = (byte) 0x0e;
    public final static byte M15MR_3        = (byte) 0x0f;
    public final static byte M15MR_END      = (byte) 0x0f;
    
    
    
    /** CLKOUT (Clockout) Register, CPU may write if CCE bit is 1 */
    public final static byte CLKOUTR        = (byte) 0x1f;
    
    /** Bus Configuration Register, CPU may write if CCE bit is 1 */
    public final static byte BCR            = (byte) 0x2f;
    public final static byte BCR_COBY       = (byte) 0x40;
    public final static byte BCR_POL        = (byte) 0x20;
    public final static byte BCR_DCT1       = (byte) 0x08;
    public final static byte BCR_DCR1       = (byte) 0x02;
    public final static byte BCR_DCR0       = (byte) 0x01;
    
    /** Bit Timing Registers, CPU may write if CCE bit is 1 */
    public final static byte BTR0           = (byte) 0x3f;
    public final static byte BTR1           = (byte) 0x4f;
    
    /** Interrupt Register */
    public final static byte IR             = (byte) 0x5f;
    
    /**
     * Port 1 Input/Output Configuration Bits,
     * CPU may write if CCE bit is 1
     */
    public final static byte P1CONF         = (byte) 0x9f;
    
    /**
     * Port 2 Input/Output Configuration Bits,
     * CPU may write if CCE bit is 1
     */
    public final static byte P2CONF         = (byte) 0xaf;
    
    /** Port 1 Data In  */
    public final static byte P1IN           = (byte) 0xbf;
    
    /** Port 2 Data In  */
    public final static byte P2IN           = (byte) 0xcf;
    
    /** Port 1 Data Out  */
    public final static byte P1OUT          = (byte) 0xdf;
    
    /** Port 2 Data Out  */
    public final static byte P2OUT          = (byte) 0xef;
    
    /** Serial Reset Address */
    public final static byte SRA            = (byte) 0xff;
   
    public final static byte M_1            = (byte) 0x10; 
    public final static byte M_15           = (byte) 0xf0;
      
    /** Message Object Control 0 Register */
    public final static byte M_C0R                  = (byte) 0x00;
    public final static byte M_C0R_RESET_ALL        = (byte) 0x55;

        
    public final static byte M_C0R_MSGVAL_IMASK     = (byte) 0x3f; // 0011 1111
    public final static byte M_C0R_MSGVAL_RESET     = (byte) 0x7f; // 0111 1111
    public final static byte M_C0R_MSGVAL_SET       = (byte) 0xbf; // 1011 1111
    public final static byte M_C0R_MSGVAL_TEST      = (byte) 0x80; // 1000 0000
    
    public final static byte M_C0R_TXIE_IMASK       = (byte) 0xcf; // 1100 1111
    public final static byte M_C0R_TXIE_RESET       = (byte) 0xdf; // 1101 1111
    public final static byte M_C0R_TXIE_SET         = (byte) 0xef; // 1110 1111
    public final static byte M_C0R_TXIE_TEST        = (byte) 0x20; // 0010 0000
    
    public final static byte M_C0R_RXIE_IMASK       = (byte) 0xf3; // 1111 0011
    public final static byte M_C0R_RXIE_RESET       = (byte) 0xf7; // 1111 0111
    public final static byte M_C0R_RXIE_SET         = (byte) 0xfb; // 1111 1011
    public final static byte M_C0R_RXIE_TEST        = (byte) 0x08; // 0000 1000
    
    public final static byte M_C0R_INTPND_IMASK     = (byte) 0xfc; // 1111 1100
    public final static byte M_C0R_INTPND_RESET     = (byte) 0xfd; // 1111 1101
    public final static byte M_C0R_INTPND_SET       = (byte) 0xfe; // 1111 1110
    public final static byte M_C0R_INTPND_TEST      = (byte) 0x02; // 0000 0010
    
    /** Message Object Control 1 Register */
    public final static byte M_C1R                  = (byte) 0x01;
    public final static byte M_C1R_RESET_ALL        = (byte) 0x55;
    
    public final static byte M_C1R_RMTPND_IMASK     = (byte) 0x3f; // 0011 1111
    public final static byte M_C1R_RMTPND_RESET     = (byte) 0x7f; // 0111 1111
    public final static byte M_C1R_RMTPND_SET       = (byte) 0xbf; // 1011 1111
    public final static byte M_C1R_RMTPND_TEST      = (byte) 0x80; // 1000 0000
    
    public final static byte M_C1R_TXRQST_IMASK     = (byte) 0xcf; // 1100 1111
    public final static byte M_C1R_TXRQST_RESET     = (byte) 0xdf; // 1101 1111
    public final static byte M_C1R_TXRQST_SET       = (byte) 0xef; // 1110 1111
    public final static byte M_C1R_TXRQST_TEST      = (byte) 0x20; // 0010 0000
    
    public final static byte M_C1R_MSGLST_IMASK     = (byte) 0xf3; // 1111 0011 for recv
    public final static byte M_C1R_MSGLST_RESET     = (byte) 0xf7; // 1111 0111 for recv
    public final static byte M_C1R_MSGLST_SET       = (byte) 0xfb; // 1111 1011 for recv
    public final static byte M_C1R_MSGLST_TEST      = (byte) 0x08; // 0000 1000 for recv
    
    public final static byte M_C1R_CPUUPD_IMASK     = (byte) 0xf3; // 1111 0011 for xmit
    public final static byte M_C1R_CPUUPD_RESET     = (byte) 0xf7; // 1111 0111 for xmit
    public final static byte M_C1R_CPUUPD_SET       = (byte) 0xfb; // 1111 1011 for xmit
    public final static byte M_C1R_CPUUPD_TEST      = (byte) 0x08; // 0000 1000 for xmit
    
    public final static byte M_C1R_NEWDAT_IMASK     = (byte) 0xfc; // 1111 1100
    public final static byte M_C1R_NEWDAT_RESET     = (byte) 0xfd; // 1111 1101
    public final static byte M_C1R_NEWDAT_SET       = (byte) 0xfe; // 1111 1110
    public final static byte M_C1R_NEWDAT_TEST      = (byte) 0x02; // 0000 0010
    
    
    /** Message Object Arbitation Registers */
    public final static byte M_AR_0                 = (byte) 0x02;
    public final static byte M_AR_1                 = (byte) 0x03;
    public final static byte M_AR_2                 = (byte) 0x04;
    public final static byte M_AR_3                 = (byte) 0x05;
    public final static byte M_AR_END               = (byte) 0x05;
    
    /** Message Configuration Registers */
    public final static byte M_CONFR                = (byte) 0x06;
    public final static byte M_CONFR_DLC            = (byte) 0xf0; // data length code (mask)
    public final static byte M_CONFR_DIR            = (byte) 0x08; // direction, set==transmit
    public final static byte M_CONFR_XTD            = (byte) 0x04; // extended frame format
    
    /** set: receive, standard frame format, data length code == 0 */
    public final static byte M_CONFR_RX_SFF_DLC0    = (byte) 0x00;
    public final static byte M_CONFR_TX_SFF_DLC0    = (byte) 0x08; // INIT = M_CONFR_DIR;
    
    /** Message bytes */
    public final static byte M_DB                   = (byte) 0x07;
    public final static byte M_DB_0                 = (byte) 0x07;
    public final static byte M_DB_1                 = (byte) 0x08;
    public final static byte M_DB_2                 = (byte) 0x09;
    public final static byte M_DB_3                 = (byte) 0x0a;
    public final static byte M_DB_4                 = (byte) 0x0b;
    public final static byte M_DB_5                 = (byte) 0x0c;
    public final static byte M_DB_6                 = (byte) 0x0d;
    public final static byte M_DB_7                 = (byte) 0x0e;
    
    
    public final static byte NUM_MESSAGE_OBJECTS    = (byte) 15;
    private final static MessageObject[] messageObjects = new MessageObject[NUM_MESSAGE_OBJECTS];
   
    //private static Usart uart;

    
    /**
     * Initialize the SPI interface, select the Can-chip and
     * set its registers
     */
    public ATMega8535CanDriver() {
       
        
        
        //messageObjects = new MessageObject[NUM_MESSAGE_OBJECTS];
        
        // Init the MCU Ports connected to the i82527
        // Interrupt line
        ATMega8535.registers.PORTD.setBit(ATMega8535.PD2);      // pull up
        
        // Enable can interrupt (INT0)
        ATMega8535.registers.MCUCR.setBit(ATMega8535.ISC01);        // falling edge
        ATMega8535.registers.GICR.setBit(ATMega8535.INT0);          // enable interrupt
        
        
        //ATMega8535.sbi(ATMega8535.PORTD, ATMega8535.PD2); /* activate pull-up resistor */
        //ATMega8535.cbi(ATMega8535.DDRD, ATMega8535.DDD2); /* input: CAN controller interrupt */
        
        // Chip reset
        ATMega8535.registers.DDRD.setBit(ATMega8535.DDD6);
        ATMega8535.registers.PORTD.clearBit(ATMega8535.PD6);  /* clear   == /reset on */

        // Reset pulse must be longer than 1ms.
        // FIXME This is certainly longer than 1ms.
        for (short i = 0; i < (short) 8000; ++i) {
        }
        
        ATMega8535.registers.PORTD.setBit(ATMega8535.PD6);  /* set   == /reset off */

        Spi.chipSelect();
        
        //FIXME this is for testing only
        //uart = new Usart();

        
        /*
         * CPUIR: After heardware reset DSC, DMC and CEN must be set.
         *   DSC set: system clock SCLK = XTAL/2,
         *   DMC set: memory clock MCLK = SCLK/2
         *   CEN set: Clockout signal is enabled
         * especially the 82527 must not be in hardware reset stuck:
         *   RSTST clear
         */
        
        /*
         * set   DSC, DMC
         * reset CEN
         */
        writeRegister((byte) CPUIR, (byte) (CPUIR_DSC | CPUIR_DMC));
        
        /*
         * enable write acess to configuration registers,
         * keep the INIT bit set.
         */
        writeRegister((byte) CR, (byte) (CR_CCE | CR_INIT));
        
        /*
         * Set global mask for exact match at all bit positions.
         * GMSR and GMER registers are on successive addresses.
         * Set message 15 mask for exact match at all bit positions.
         */
        for (byte address = GMSR_0; address <= GMER_END; ++address) {
            writeRegister((byte) address, (byte) 0xff);
        }
        
        for (byte address = M15MR_0; address <= M15MR_END; ++address) {
            writeRegister((byte) address, (byte) 0xff);
        }
        
        /* Bus Configuration */
        writeRegister((byte) BCR, (byte) (BCR_COBY | BCR_DCT1 | BCR_DCR1));
        
        /*
         * The bit timing.
         * SCLK runs at 8 MHz.
         * Set Can-speed to 125kbit.
         * The time quanta is set to 1/2 us  -  BRP=3.
         * TSEG1=4
         * TSEG2=1
         * in BTR0 set SJW == 00b; BRP == 000011b == 3
         * in BTR1 set SPL == 0b; TSEG2==001b;TSEG1==1100b==12
         */
        writeRegister((byte) BTR0, (byte) 0x03);
        writeRegister((byte) BTR1, (byte) 0x1C);
        
        /*
         * Mark all MOBs as invalid and disable interrupt generation
         */
        for (byte mob = 0; mob < 15; ++mob) {
           writeRegister((byte) (M_1 + mob * 0x10), (byte) (M_C0R_RESET_ALL));
        }
        
        /*
         * SR -- the status register
         * after hardware reset the bits are undefined, clear them.
         */
        writeRegister((byte) SR, (byte) 0x00);
        
        /*
         * Initialisation is finished.
         * reset/clear all bits of the control register,
         * especially the INIT bit except the global interrupt enable bit.
         */
        writeRegister((byte) CR, (byte) CR_IE);

    }


        

    /*
     * CAN Interrupt handler
     *
     */ 
    public static void canInterrupt() {
	    MessageObject[] msgobj = messageObjects;

        // TODO read the interrupt source and handle it by the corresponding message object
        byte source = readRegister((byte) IR);

        //uart.write(source);
        
        if (source == 2) {  // MOB15
            if (msgobj[14] != null) {
                msgobj[14].handleInterrupt();
            }
        } else if (source > 2) {    //normal MOBs
            source -= 3;
            if (msgobj[source] != null) {
                msgobj[source].handleInterrupt();
            }
        }
    }
    


    
    /**
     * Read an byte value from a register using the SPI interface.
     * @param adr a register address.
     * @return the value read from the register.
     */
    private static byte readRegister(byte adr) {
        
        // must be interrupt safe
        InterruptService.suspendAll();
         
        byte echo = 0;
        /* address of register or memory in 82527 */
        echo = Spi.transfer(adr);
        
        /* serial control uint8: read (MSB==0) a single (0x01) byte value */
        echo = Spi.transfer((byte) 0x01);
        
        /* send dummy value, read a byte value */
        echo = Spi.transfer((byte) 0x00);
        
        // reenable interrupts
        InterruptService.resumeAll();

        //uart.write((byte) 0xcd);
        //uart.write(adr);
        //uart.write(echo);
        
        return echo;
    }
    
    /**
     * Put an byte value into a register using the SPI interface.
     * @param adr a register address.
     * @param val the value to be written into the register.
     */
    private static void writeRegister(byte adr, byte val) {
        byte echo;
        
        //uart.write((byte) 0xdc);
        //uart.write(adr);
        //uart.write(val);

        
        // must be interrupt safe
        InterruptService.suspendAll();

        /* address of register or memory in 82527 */
        echo = Spi.transfer((byte) adr);
        
        /* serial control byte: write (MSB==1) a single (0x01) byte value */
        echo = Spi.transfer((byte) 0x81);
        
        /* write the uint8 value */
        echo = Spi.transfer((byte) val);

        // reenable interrupts
        InterruptService.resumeAll();
    }
    
        

    /**
     * Open a message object
     */
    public CanMessageObject openMessageObject(byte mode, boolean useExtendedID) {
	    MessageObject[] msgobj = messageObjects;

        if (mode == CanPort.READ_MULTI) {
                if (msgobj[14] == null) {
                    // only supported by msgobj 15. It is reserved for this special purpose
                    msgobj[14] = new MessageObject((byte) 14, false, useExtendedID);
                    return msgobj[14];
                }

        } else if (mode == CanPort.READ_SINGLE || mode == CanPort.WRITE) {
                // search for free msgobj don't use #15
                for (byte i = (byte) 0; i < 13; ++i) {
                    if (msgobj[i] == null) {
                        // found free msgobj
                        msgobj[i] = new MessageObject(i, mode == CanPort.WRITE, useExtendedID);
                        return msgobj[i];
                    }
                }
        }

        return null;
    }

    /**
     * Set the baudrate of the CAN bus
     */
    public void setBaudrate(int value) {
        // TODO this is not implemented by this can driver
        // Baudrate is initialized in constructor and is always 125000
        return;
    }
    
    /**
     * This class implements a can message object
     *
     */
    public class MessageObject implements CanMessageObject {

        private final byte regBase;
        private byte bufferIndex;
        private byte currentPacketLength;
        
        private CanEventHandler evtHandler;
        
        MessageObject(byte id, boolean transmit, boolean useExtID) {
            regBase = (byte) (M_1 + 0x10 * id);
            bufferIndex = (byte) 0;
            currentPacketLength = -1;
            
            // MsgVal = 0, TxIE = 0, RxIE = 1, IntPnd = 0
            writeRegister((byte) (regBase + M_C0R), (byte) (M_C0R_MSGVAL_RESET & M_C0R_TXIE_RESET & M_C0R_RXIE_SET & M_C0R_INTPND_RESET));
            
            if (transmit) {
                // RmtPnd = 0, TxRqst = 0, CPUUpd = 1, NewDat = 0
                writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_RMTPND_RESET &
                             M_C1R_TXRQST_RESET &
                             M_C1R_CPUUPD_SET &
                             M_C1R_NEWDAT_RESET));
                
                if (useExtID) {
                    // DLC = 0, Dir = tx, Xdt = 1 
                    writeRegister((byte) (regBase + M_CONFR), (byte) (M_CONFR_DIR | M_CONFR_XTD));
                } else {
                    // DLC = 0, Dir = tx, Xdt = 0 
                    writeRegister((byte) (regBase + M_CONFR), (byte) (M_CONFR_DIR));
                }
            } else {
                // RmtPnd = 0, TxRqst = 0, MsgLst = 0, NewDat = 0
                writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_RESET_ALL));

                if (useExtID) {
                    // DLC = 0, Dir = rx, Xdt = 1 
                    writeRegister((byte) (regBase + M_CONFR), (byte) (M_CONFR_XTD));
                } else {
                    // DLC = 0, Dir = rx, Xdt = 0 
                    writeRegister((byte) (regBase + M_CONFR), (byte) 0x00);
                }

            }
        }


        public void installEventHandler(CanEventHandler handler) {
            InterruptService.disableAll();
            this.evtHandler = handler;
            InterruptService.enableAll();
        }
        

        private void handleInterrupt() {
            // reset the interrupt pending bit
            writeRegister((byte) (regBase + M_C0R), (byte) M_C0R_INTPND_RESET);
           
            if (evtHandler != null) {
                evtHandler.packetReceived();
            }
        }

        
        private void setStandardIdentifier(byte regBase, int value) {
            writeRegister((byte) (regBase), (byte) (value >>> 3));
            writeRegister((byte) (regBase + 1), (byte) (value << 5));
            writeRegister((byte) (regBase + 2), (byte) 0x00);
            writeRegister((byte) (regBase + 3), (byte) 0x00);
        }

        
        private void setExtendedIdentifier(byte regBase, int value) {
            writeRegister((byte) (regBase), (byte) (value >>> 21));
            writeRegister((byte) (regBase + 1), (byte) (value >>> 13));
            writeRegister((byte) (regBase + 2), (byte) (value >>> 5));
            writeRegister((byte) (regBase + 3), (byte) (value << 3));
        } 

        
        private boolean useExtendedFrames() {
            return ((readRegister((byte) (regBase + M_CONFR)) & M_CONFR_XTD) != 0);
        }
        

        private void markValid() {
            writeRegister((byte) (regBase + M_C0R), (byte) (M_C0R_MSGVAL_SET));
        }


        private void markInvalid() {
            writeRegister((byte) (regBase + M_C0R), (byte) (M_C0R_MSGVAL_RESET));
        } 

        
        
        public void setIdentifier(int id) {
            markInvalid();    
            if (useExtendedFrames()) {
                setExtendedIdentifier((byte) (regBase + M_AR_0), id);
            } else {
                setStandardIdentifier((byte) (regBase + M_AR_0), id);
            }
            markValid();            
        }



        
        public void setReceiveMask(int mask) {
            
            // TODO maybe the global mask could be touched in some cases
            
            // only valid for msgobj 15
            if (regBase == M_15) {
                
                markInvalid();
                if (useExtendedFrames()) {
                    setExtendedIdentifier((byte) M15MR_0, mask);
                } else {
                    setStandardIdentifier((byte) M15MR_0, mask);
                }
                markValid();    
            
            }
        }

        
        
        
        public int getPacketIdentifier() {
            if (useExtendedFrames()) {
                int result = readRegister((byte) (regBase + M_AR_0)) << 21;
                result |= readRegister((byte) (regBase + M_AR_1)) << 13;
                result |= readRegister((byte) (regBase + M_AR_2)) << 5;
                result |= readRegister((byte) (regBase + M_AR_3)) >>> 3;
                return result;
            } else {
                short result = (short) ((readRegister((byte) (regBase + M_AR_0))) << 3);
                result |= (short) ((readRegister((byte) (regBase + M_AR_1)) >>> 5) & 0x07);
                return result;
            }
        }



        
        private byte getPacketLength() {
            if (currentPacketLength == -1) {
                currentPacketLength = (byte) ((readRegister((byte) (regBase + M_CONFR)) >>> 4) & 0x0f);
            }     
            return currentPacketLength;
        }
            



        
        private void setPacketLength(byte len) {
            byte reg = (byte) (readRegister((byte) (regBase + M_CONFR)) & 0x0f);
            writeRegister((byte) (regBase + M_CONFR), (byte) (reg | (len << 4)));
        } 



        
        private void waitUntilPacketIsAvailable() {
            while ((readRegister((byte) (M_15 + M_C1R)) & M_C1R_NEWDAT_TEST) == 0);
        }
       

        
        public byte read() {
           
           if (bufferIndex < getPacketLength()) {
                byte val = readRegister((byte) (regBase + M_DB + bufferIndex));
                ++bufferIndex;
                return val;
            }
            
            return (byte) 0;           
        }
    

        
        public byte readPacket(Memory buffer, short offset, byte maxLength) {
            
            byte packetLength = getPacketLength();
            byte bytesRead = 0;
            
            while (bufferIndex < packetLength && bytesRead < maxLength) {
                buffer.set8(offset + bytesRead, readRegister((byte) (regBase + M_DB + bufferIndex)));
                ++bufferIndex;
                ++bytesRead;
            }

            return bytesRead;
        }



        
        public void releaseReceiveBuffer() {
            // everything is read from the buffer
            writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_NEWDAT_RESET &
                         M_C1R_RMTPND_RESET));
            bufferIndex = (byte) 0;
            currentPacketLength = (byte) -1;
        }
    


        
        private void waitUntilPacketIsTransmitted() {
            while ((readRegister((byte) (regBase + M_C1R)) & M_C1R_TXRQST_TEST) != 0);
        }
        

        public void write(byte data) {
            
            if (bufferIndex == 0) {
                // wait until it is safe to write to the transmit buffer
                waitUntilPacketIsTransmitted();
                writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_CPUUPD_SET &
                                M_C1R_NEWDAT_SET));
            }

            if (bufferIndex < CanPort.MAX_MESSAGE_LENGTH) {
                writeRegister((byte) (regBase + M_DB + bufferIndex), data);
                ++bufferIndex;
            }
            
        }

/*        
        public void write(byte data) {
            for (;;) {
                if (bufferIdx == 0) {
                    waitUntilPacketIsTransmitted();

                writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_CPUUPD_SET &
                                M_C1R_NEWDAT_SET));

                                   }
            
                if (bufferIdx < CanPort.MAX_MESSAGE_LENGTH) {
                    writeRegister((byte) (regBase + M_DB + bufferIdx), data);
                    ++bufferIdx;
                    break;
                    
                } else {
                    // buffer is full
                    setPacketLength(CanPort.MAX_MESSAGE_LENGTH); 
                    writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_CPUUPD_RESET &
                               M_C1R_TXRQST_SET));
                                        
                    bufferIdx = (byte) 0;
                    continue;
                }
            }        
        }
*/    

        

        
        public byte writePacket(Memory buffer, short offset, byte maxLength) {
            
            if (bufferIndex == 0) {
                waitUntilPacketIsTransmitted();    
                writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_CPUUPD_SET &
                                M_C1R_NEWDAT_SET));
            }

            byte bytesWritten = 0;

            while (bufferIndex < CanPort.MAX_MESSAGE_LENGTH && bytesWritten < maxLength) {
                writeRegister((byte) (regBase + M_DB + bufferIndex), buffer.get8(offset + bytesWritten));
                ++bufferIndex;
                ++bytesWritten;
            }
           
            return bytesWritten;
        }    
    


        
        public void flush() {
            if (bufferIndex != 0) { 
                setPacketLength(bufferIndex);
                writeRegister((byte) (regBase + M_C1R), (byte) (M_C1R_CPUUPD_RESET &
                               M_C1R_TXRQST_SET));
                                        
                bufferIndex = (byte) 0;                    
            }
        }

        public Memory allocTransmitBuffer(short size, int timeout) {
            // FIXME
            return MemoryService.allocStaticMemory(16);
        }

        public Memory allocReceiveBuffer(short size, int timeout) {
            // FIXME
            return MemoryService.allocStaticMemory(16);
        }
    }
}
