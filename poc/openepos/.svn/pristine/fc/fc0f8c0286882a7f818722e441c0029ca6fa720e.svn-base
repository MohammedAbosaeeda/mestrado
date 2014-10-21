package keso.driver.can;

import keso.core.io.*;
import keso.core.*;

public final class CanPacketStream implements PacketStream, CanEventHandler {
    
    private final static byte PACKET_START_MASK     = (byte) 0x80; 
    private final static byte PACKET_END_MASK       = (byte) 0x40; 
    private final static byte PACKET_SEQUENCE_MASK  = (byte) 0x3f;
    private final static byte CAN_PACKET_LENGTH     = (byte) 8;
    private final static byte PACKET_HEADER_LENGTH  = (byte) 1;
    private final static byte EVENT_MASK            = (byte) 0x1;


    
    class PacketBuffer {
        
        public final Memory data;
        public final int packetID;
        public short length;
        public byte sequenceInfo;
        //public boolean locked;
        
        public PacketBuffer(int id, Memory data) {
            this.data = data;
            packetID = id;
        }

        public void lock() {
//            InterruptService.disableAll();
//            locked = true;
//            InterruptService.enableAll();
        }

        public void unlock() {
//            InterruptService.disableAll();
//            locked = false;
//            InterruptService.enableAll();
        }
    }

    
    private CanPort port;
    private PacketBuffer[] receiveBuffers;
    private PacketBuffer currentBuffer;
    private CanMessageObject receiveMObj;    
    private CanMessageObject transmitMObj;
    private Task waitingTask;
    private Memory transmitBuffer;
    private int bufferMask;
    private int receiveId; 
    
    private static volatile boolean __event;
    
    //private keso.driver.avr.atmega8535.Usart uart;
    
    public CanPacketStream(CanPort port, int rxId, int rxMask, int bufferMask, byte idBits, byte numRxBuffers) {

        //uart = new keso.driver.avr.atmega8535.Usart();
        
        this.port = port;
        
        boolean useExtIDs = idBits > CanPort.STANDARD_ID_BITS;
        
        receiveMObj = port.openMessageObject(CanPort.READ_MULTI, useExtIDs);
        transmitMObj = port.openMessageObject(CanPort.WRITE, useExtIDs);

        this.bufferMask = bufferMask;
        receiveMObj.setIdentifier(rxId);
        this.receiveId = rxId;
        receiveMObj.setReceiveMask(rxMask);

        receiveBuffers = new PacketBuffer[numRxBuffers];

        receiveMObj.installEventHandler(this);

        __event = false;
    }

    
    public Memory allocTransmitBuffer(short size, int timeout) {
        transmitBuffer = transmitMObj.allocTransmitBuffer(size, timeout);
        return transmitBuffer;
    }
    
    
    public boolean allocReceiveBuffer(int id, short size, int timeout) {
        
        Memory data = receiveMObj.allocReceiveBuffer(size, timeout);        
        
        if (data != null) {
            int index = id & bufferMask;

            // check index
            if (index < 0 || index >= receiveBuffers.length) {
                return false;
            } else {
                receiveBuffers[index] = new PacketBuffer(id, data);
                return true;
            }
        } else {
            return false;
        }
    }
        
            
    public Memory read(int usTimeout) {
        waitingTask = TaskService.getTaskID();     
        //TODO setup timeout
        //uart.write((byte) 0x44);
        
        while (!__event);
        __event = false;
        
        //EventService.waitEvent(EVENT_MASK);
        //EventService.clearEvent(EVENT_MASK);
        //uart.write((byte) 0x55);
        //TODO check for timeout
    	PacketBuffer curBuffer = currentBuffer;
        if (curBuffer != null) {
            curBuffer.lock();
            return curBuffer.data;
        } else {
            return null;
        }
    }        
           
    
    /**
     *
     * This method will be called within rx interrupt context.
     *
     */
    public void packetReceived() {

        //uart.write((byte) 0x00);
        
        //byte packetLength = receiveMObj.getPacketLength();
        byte sequenceInfo = receiveMObj.read();
        int packetID = receiveMObj.getPacketIdentifier();

        currentBuffer = receiveBuffers[packetID & bufferMask];

        if (currentBuffer != null) {
            if ((sequenceInfo & PACKET_START_MASK) != 0) {  // first packet
                currentBuffer.sequenceInfo = sequenceInfo;
                currentBuffer.length = 0;
            } else if (((currentBuffer.sequenceInfo + 1) & PACKET_SEQUENCE_MASK) ==
                        (sequenceInfo & PACKET_SEQUENCE_MASK)) {
                currentBuffer.sequenceInfo = sequenceInfo;
            } else {    // sequence nr. does not match (lost packet)
                receiveMObj.releaseReceiveBuffer(); // discard packet
                //uart.write((byte) 0x11);
                return;
            }

            // read the content of the MOB buffer            
            currentBuffer.length += receiveMObj.readPacket(currentBuffer.data, 
                                                           currentBuffer.length,
                                                           (byte) (currentBuffer.data.getSize() - currentBuffer.length));

            receiveMObj.releaseReceiveBuffer();
            
            if ((currentBuffer.sequenceInfo & PACKET_END_MASK) != 0) {
                if (waitingTask != null) {
                    //EventService.setEvent(waitingTask, EVENT_MASK);
                    __event = true;
                    //uart.write((byte) 0xff);
                } else {
                    //FIXME packet received with no task waiting for it
                    //EventService.setEvent(waitingTask, EVENT_MASK);
                    __event = true;
                    //uart.write((byte) 0x22);
                }
            }
        }
    }
    
    
    public int getPacketID() {
        if (currentBuffer != null) {
            return currentBuffer.packetID;
        } else {
            return -1;
        }
    }
    
    
    public short getPacketLength() {
        if (currentBuffer != null) {
            return currentBuffer.length;
        } else {
            return -1;
        }
    }
    
    
    public void releaseReceiveBuffer() {
        if (currentBuffer != null) {
            currentBuffer.unlock();
            currentBuffer = null;
        }
    }
    
    public boolean write(int id, short length, int usTimeout) {
        
        //uart.write((byte) 0x33);
        
        //TODO setup timeout
        
        transmitMObj.setIdentifier(id);
        
        short offset = (short) 0;
        byte sequenceNr = (byte) 0;
       
        if (length == 0) {
            
            sequenceNr |= (PACKET_START_MASK | PACKET_END_MASK);
            transmitMObj.write(sequenceNr); 
            transmitMObj.flush();

        } else {
            while (length > 0) {
                if (offset == 0) {  // first packet
                    sequenceNr |= PACKET_START_MASK;
                }
                if (length <= (CAN_PACKET_LENGTH - PACKET_HEADER_LENGTH)) { // last packet
                    sequenceNr |= PACKET_END_MASK;
                }

                //FIXME howto handle memory!
                transmitMObj.write(sequenceNr);     // transmit sequence nr.

                byte txLength = transmitMObj.writePacket(transmitBuffer, offset, (byte) (length > CAN_PACKET_LENGTH ? CAN_PACKET_LENGTH : length));     // transmit data
                offset += txLength;
                length -= txLength;

                sequenceNr = (byte) ((sequenceNr + 1) & PACKET_SEQUENCE_MASK);

                transmitMObj.flush();
            }
        }

        return true;
    }

  
}
