package keso.driver.avr.atmega8535;

import keso.driver.serialport.*;
import keso.core.*;


/**
 *
 * @author Ralf Ellner
 */
public final class Usart implements SerialPort {
    
    private final static int DefaultBaudRate = 38400;   
    private final static byte DefaultFrameSize = SerialPort.DATABITS_8; 
    private final static byte DefaultParityMode = SerialPort.PARITY_NONE;
    private final static byte DefaultStopBits = SerialPort.STOPBITS_ONE;
    private final static byte DefaultTransferMode = SerialPort.TRANSFERMODE_ASYNC;
    private final static byte DefaultSynchronousClockMode = SerialPort.SYNC_CLOCK_MODE_RISING_EDGE;
    
    /*
     *
     * NOTE: UCSRC and UBRRH share the same memory location!
     * 
     */ 
    private static byte ucsrc;

    /*
     * Flags used in interrupts - must be made volatile
     */
    private static boolean receiveComplete;
    private static Memory receiveBuffer;
    private static short receiveLength;
    private static short receiveIndex;
    private static boolean receivePacketStart;
    
    private static boolean transmitComplete;    
    private static Memory transmitBuffer;
    private static short transmitLength;
    private static short transmitIndex;
    
    
    public Usart() {
        this(DefaultBaudRate, DefaultFrameSize, DefaultParityMode, DefaultStopBits, DefaultTransferMode, DefaultSynchronousClockMode);
    }
    
    
    public Usart(int baudRate) {
        this(baudRate, DefaultFrameSize, DefaultParityMode, DefaultStopBits, DefaultTransferMode, DefaultSynchronousClockMode);
    }
    
    public Usart(int baudRate, byte dataSize, byte parityMode, byte stopBits) {
        this(baudRate, dataSize, parityMode, stopBits, DefaultTransferMode, DefaultSynchronousClockMode);
    }
    
    public Usart(int baudRate, byte dataSize, byte parityMode, byte stopBits, byte transferMode, byte clockMode) {
        ucsrc = (byte) (1 << ATMega8535.URSEL);
        
        this.setBaudrate(baudRate);
        this.setFrameSize(dataSize);
        this.setParityMode(parityMode);
        this.setStopBits(stopBits);
        this.setSynchronousClockMode(clockMode);
        this.setTransferMode(transferMode);

        receiveComplete = false;
        transmitComplete = true;
        
        // set receiver and transmitter active, enable interrupts
        ATMega8535.registers.UCSRB.set((1 << ATMega8535.RXEN) | (1 << ATMega8535.TXEN));

    }
    
    
       
    public void setBaudrate(int baudRate) {
        
        int ubrr = SystemService.getIntegerConstant("Frequency") / (16 * baudRate) -1;
        
        ATMega8535.registers.UBRRH.set((ubrr >> 8) & (~(1 << ATMega8535.URSEL))); // don't set the URSEL bit because UCSRC would be accessed
        ATMega8535.registers.UBRRL.set(ubrr & 0xff);
    }
    
    
    public void setTransferMode(byte mode) {
        
        if (mode == SerialPort.TRANSFERMODE_SYNC) {
            ucsrc |= (1 << ATMega8535.UMSEL);
        } else {    // assume ASYNC
            ucsrc &= ~(1 << ATMega8535.UMSEL);
        }
        
        ATMega8535.registers.UBRRH.set(ucsrc);
    }
    
    
    public void setParityMode(byte mode) {
        
        ucsrc &= ~((1 << ATMega8535.UPM1) | (1 << ATMega8535.UPM0));  // Clear bits (no parity)
        
        if (mode == SerialPort.PARITY_EVEN) {
            ucsrc |= (1 << ATMega8535.UPM1);
        } else if (mode == SerialPort.PARITY_ODD) {
            ucsrc |= ((1 << ATMega8535.UPM1) | (1 << ATMega8535.UPM0));
        } else {
            // other modes unsupported assume PARITY_NONE
        }
        
        ATMega8535.registers.UBRRH.set(ucsrc);
    }
    
    
    public void setStopBits(byte stopBits) {
        
        if (stopBits == SerialPort.STOPBITS_TWO) {
            ucsrc |= (1 << ATMega8535.USBS);
        } else {    // assume _ONE - _ONE_POINT_FIVE not supported
            ucsrc &= ~(1 << ATMega8535.USBS);
        } 
        
        ATMega8535.registers.UBRRH.set(ucsrc);
    }
    
    
    public void setFrameSize(byte size) {
        
        // Clear Bits
        ucsrc &= ~((1 << ATMega8535.UCSZ1) | (1 << ATMega8535.UCSZ0));
        ATMega8535.registers.UCSRB.clearBit(ATMega8535.UCSZ2);
        
        switch (size) {
            case SerialPort.DATABITS_5:
                break;
            case SerialPort.DATABITS_6:
                ucsrc |= (1 << ATMega8535.UCSZ0);
                break;
            case SerialPort.DATABITS_7:
                ucsrc |= (1 << ATMega8535.UCSZ1);
                break;
            case SerialPort.DATABITS_9:
                ATMega8535.registers.UCSRB.setBit(ATMega8535.UCSZ2);
                ucsrc |= ((1 << ATMega8535.UCSZ0) | (1 << ATMega8535.UCSZ1));
                break;
            default:    // DATABITS_8
                ucsrc |= ((1 << ATMega8535.UCSZ0) | (1 << ATMega8535.UCSZ1));
                break;
        }
        
        ATMega8535.registers.UBRRH.set(ucsrc);
    }
    
    
    public void setSynchronousClockMode(byte mode) {
        
        if (mode == SerialPort.SYNC_CLOCK_MODE_FALLING_EDGE) {
            ucsrc |= (1 << ATMega8535.CPOL);
        } else {    // assume RISING_EDGE
            ucsrc &= ~(1 << ATMega8535.CPOL);
        }
        
        ATMega8535.registers.UBRRH.set(ucsrc);
    }
    
    
    public boolean hasFrameError() {
        return ATMega8535.registers.UCSRA.isBitSet(ATMega8535.FE);
    }
    
    
    public boolean hasParityError() {
        return ATMega8535.registers.UCSRA.isBitSet(ATMega8535.PE);
    }
    
    
    public boolean hasDataOverRun() {
        return ATMega8535.registers.UCSRA.isBitSet(ATMega8535.DOR);
    }
    
    
    public void enablePacketMode() {
        setFrameSize(DATABITS_9);
    }

   
    public byte read() {
        while (ATMega8535.registers.UCSRA.isBitClear(ATMega8535.RXC));
        return (byte) ATMega8535.registers.UDR.get();
    }
    
   
    public void readPacket(Memory data, short length, boolean needStart) {
        
        if (length <= 0) {
            return;
        }
        
        
        InterruptService.disableAll();
        receiveComplete = false;
        receiveBuffer = data;
        receiveLength = length;
        receiveIndex = (short) 0;
        receivePacketStart = needStart;
        InterruptService.enableAll();

        ATMega8535.registers.UCSRB.setBit(ATMega8535.RXCIE);

        while (!receiveComplete);
    }

    
    
    
    private void receiveCompleteInterrupt() {
        
        if (receiveBuffer == null) {    // The data byte gets lost!
            ATMega8535.registers.UCSRA.clearBit(ATMega8535.RXC); // ISR will be called again after return if bit is still set!
            return;
        }

        if (receivePacketStart) {
            // First byte of the packet must have the ninth bit set
            if (receiveIndex == 0 && ATMega8535.registers.UCSRB.isBitClear(ATMega8535.RXB8)) {
                ATMega8535.registers.UCSRA.clearBit(ATMega8535.RXC); // ISR will be called again after return if bit is still set!
                return;
            } else {
                receivePacketStart = false;
            }
        }
        
        receiveBuffer.set8(receiveIndex++, (byte) ATMega8535.registers.UDR.get());

        if (receiveIndex >= receiveLength) {
            ATMega8535.registers.UCSRB.clearBit(ATMega8535.RXCIE);
            receiveComplete = true;
            receiveBuffer = null;
        }
    }

   
    public void writePacket(Memory data, short length) {
        
        if (length <= 0) {
            return;
        }
        
        while (!transmitComplete);  // wait until it is safe to write
        
        transmitComplete = false;
        transmitBuffer = data;
        transmitLength = length;
        transmitIndex = 0;
        
        // enable the interrupt
        ATMega8535.registers.UCSRB.setBit(ATMega8535.UDRIE);

    }
        

    private void transmitCompleteInterrupt() {
        if (transmitIndex >= transmitLength) {
            // disable the interrupt and prevent a infinite interrupt loop
            ATMega8535.registers.UCSRB.clearBit(ATMega8535.UDRIE);
            transmitComplete = true;
            return;
        }

        // First data byte must have the ninth bit set in the first data byte
        if (transmitIndex == 0) {
            ATMega8535.registers.UCSRB.setBit(ATMega8535.TXB8);
        } else {
            ATMega8535.registers.UCSRB.clearBit(ATMega8535.TXB8);
        }
        
        byte data = transmitBuffer.get8(transmitIndex);
        ++transmitIndex;
        ATMega8535.registers.UDR.set(data);
    }




    public void write(byte data) {
        while (ATMega8535.registers.UCSRA.isBitClear(ATMega8535.UDRE));
        ATMega8535.registers.UDR.set(data);
    }
    
           
       
    public Memory allocTransmitBuffer(short size, int timeout) {
        //FIXME
        //return MemoryService.allocMemory(size, timeout);
        return MemoryService.allocStaticMemory(32);
    }

    public Memory allocReceiveBuffer(short size, int timeout) {
        //FIXME
        //return MemoryService.allocMemory(size, timeout);
        return MemoryService.allocStaticMemory(32);
    }
}
