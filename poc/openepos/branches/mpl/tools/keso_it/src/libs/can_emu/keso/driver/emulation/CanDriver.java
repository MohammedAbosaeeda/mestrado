package keso.driver.emulation;

import keso.driver.can.*;
import keso.core.*;

/**
 * @author Chrisitan Wawersich 
 */
public final class CanDriver implements CanPort {

	public final static byte NUM_MESSAGE_OBJECTS    = (byte) 15;
	private final static MessageObject[] messageObjects = new MessageObject[NUM_MESSAGE_OBJECTS];

	public CanDriver() {

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

		private byte bufferIndex;
		private byte currentPacketLength;

		private CanEventHandler evtHandler;

		MessageObject(byte id, boolean transmit, boolean useExtID) {
		}

		public void installEventHandler(CanEventHandler handler) {
			InterruptService.disableAll();
			this.evtHandler = handler;
			InterruptService.enableAll();
		}

		private void handleInterrupt() {
			// reset the interrupt pending bit

			if (evtHandler != null) {
				evtHandler.packetReceived();
			}
		}

		/**
		 * Set the CAN ID of the MOB.
		 */
		public void setIdentifier(int id) {
			// TODO: Set CAN-Identifier.
		}

		/**
		 * Set the receive mask. A zero bit in the mask indicates a don't
		 * care bit in the CAN ID.
		 * @require MOB must be opened with READ_MULTI.
		 */
		public void setReceiveMask(int mask) {
			// TODO maybe the global mask could be touched in some cases
		}

		/**
		 * Returns the CAN ID of the packet currently located in the MOB buffer.
		 * @require !releaseReceiveBuffer().
		 */
		public int getPacketIdentifier() {
			return 0;
		}

		/**
		 * Read the next byte from the MOB buffer. The caller has to check before reading if
		 * the buffer does contain one more byte.
		 */ 
		public byte read() {
			return (byte) 0;           
		}

		/**
		 * Read the content from the MOB buffer into memory. The remaining
		 * bytes in the MOB buffer are read (up to maxLength bytes).
		 * @param buffer The buffer to write to.
		 * @param offset Offset in the buffer.
		 * @param maxLength Maximum number of bytes to write into buffer.
		 * @return Number of bytes written to buffer.
		 */
		public byte readPacket(Memory buffer, short offset, byte maxLength) {
			byte bytesRead = 0;
			return bytesRead;
		}

		/**
		 * Mark the MOB receive buffer as writable.
		 */
		public void releaseReceiveBuffer() {
		}

		/**
		 * Write one byte to the MOB buffer. The caller has to ensure that there is enough space
		 * left in the buffer.
		 */
		public void write(byte data) {
		}

		/**
		 * Write the content of a buffer to the MOB buffer.
		 * @param buffer The buffer to read from.
		 * @param offset Offset in the buffer.
		 * @param maxLength Maximum number of bytes to read from the buffer.
		 * @return Number of bytes read from the buffer.
		 */
		public byte writePacket(Memory buffer, short offset, byte maxLength) {
			byte bytesWritten = 0;
			return bytesWritten;
		}    

		/**
		 * Write the content of the MOB buffer to the CAN bus.
		 */
		public void flush() {
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
