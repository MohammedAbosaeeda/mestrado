/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

/**
 * Instances of the Memory class allow access to memory areas
 * allocated by the MemoryService.
 */
public final class Memory {
	/**
	 * Start address of the memory block.
	 */
	/* private int addr; */

	/**
	 * Size of the memory block.
	 */
	/* private int size; */

	/**
	 * Memory objects may only be created by the MemoryService.
	 */
	private Memory() {}

	/**
	 * Read a byte at the specified offset from the beginning of
	 * the represented memory block.
	 *
	 * @param offset the offset into the memory block to read from
	 * @return the read byte
	 */
	public byte get8(int offset) { return 0; }

	/**
	 * Write a byte at the specified offset from the beginning of
	 * the represented memory block.
	 *
	 * @param offset the offset into the memory block to write to
	 * @param value the value that will be written
	 */
	public void set8(int offset, byte value) { }

	/**
	 * Read a 16-bit value at the specified offset from the beginning of
	 * the represented memory block.
	 *
	 * @param offset the offset into the memory block to read from
	 * @return the read value
	 */
	public short get16(int offset) { return 0; }

	/**
	 * Write a 16-bit value at the specified offset from the beginning of
	 * the represented memory block.
	 *
	 * @param offset the offset into the memory block to write to
	 * @param value the value that will be written
	 */
	public void set16(int offset, short value) { }

	/**
	 * Read a 32-bit value at the specified offset from the beginning of
	 * the represented memory block.
	 *
	 * @param offset the offset into the memory block to read from
	 * @return the read value
	 */
	public int get32(int offset) { return 0; }

	/**
	 * Write a 32-bit value at the specified offset from the beginning of
	 * the represented memory block.
	 *
	 * @param offset the offset into the memory block to write to
	 * @param value the value that will be written
	 */
	public void set32(int offset, int value) {  }

	public void and8(int offset, int mask) { }
	public void and16(int offset, int mask) { }
	public void and32(int offset, int mask) { }
	public void or8(int offset, int mask) { }
	public void or16(int offset, int mask) { }
	public void or32(int offset, int mask) { }
	public void xor8(int offset, int mask) { }
	public void xor16(int offset, int mask) { }
	public void xor32(int offset, int mask) { }
    
	protected void finalize() throws Throwable { }

    /**
     * @return The size of the memory location
     */
    public int getSize() {
        return -1;
    }

    /**
     * This function will NOT allocate new Memory but return a new Memory object
     * with a different start address and a different size but in the same location as the 
     * original Memory. It may be used if only a part of an Memory block shall be accessible by
     * another method or class.
     *
     * @param offset The offset of the new start address relative to the old start address. 
     * addressOffset must be >= 0 && < mem.size.
     * @param length The size of the returned memory. newSize must be > 0 &&  <= (mem.size - addressOffset)
     *
     * @return The new memory object. null if any of the above conditions were not satisfied.
     */
    public Memory getPart(int offset, int length) {
        return null;
    }
}
