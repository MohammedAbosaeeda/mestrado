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
 * This class enables access to physical memory from Java applications.
 */
public final class MemoryService {

	public final static int NONBLOCK = 0;
	public final static int BLOCK    = -1;
	public final static int UNPRED   = -2;

	/**
	 * Device Memory can be used to access physical memory at a specified address.
	 * This is necessary e.g. to access memory mapped device registers.
	 * This function allocates an immortal Memory object to access device memory.
	 * For the program this means, that subsequent calls will always return the
	 * same object.
	 *
	 * @param start start address of the memory area.
	 * @param size size in bytes of the memory area.
	 * @return A memory object for accessing the specified memory area.
	 */
	public static Memory allocStaticDeviceMemory(int start, int size) { return null; }

	/**
	 * Device Memory can be used to access physical memory at a specified address
	 * via a memory mapped object.
	 *
	 * This is necessary e.g. to access memory mapped device registers.
	 * This function allocates an immortal Memory object to access device memory.
	 * For the program this means, that subsequent calls will always return the
	 * same object.
	 *
	 * @param start start address of the memory area.
	 * @param type the class name of type.
	 * @return A memory mapped object for accessing the specified memory area.
	 */
	public static MemoryMappedObject mapStaticDeviceMemory(int start, String type) { return null; }

	/**
	 * Device Memory can be used to access physical memory at a specified address.
	 * This is necessary e.g. to access memory mapped device registers.
	 * This function allocates a Memory object on the domain's heap at runtime.
	 *
	 * @param start start address of the memory area.
	 * @param size size in bytes of the memory area.
	 * @return A memory object for accessing the specified memory area.
	 *
	 * HINT:
	 * Prefer the use of allocStaticDeviceMemory or mapStaticDeviceMemory to create
	 * a smaller application.
	 */
	public static Memory allocDynamicDeviceMemory(int start, int size) { return null; }

	/**
	 * This function will allocate a memory block that can be used like physical
	 * memory. In contrast to device memory it is not possible to define a start
	 * address. The memory area of the specified size will be allocated out side
	 * the domain heap.
	 *
	 * This function allocates an immortal Memory object and an immortal memory area.
	 *
	 * @param size size of the requested memory area
	 * @return a Memory object for accessing the allocated memory area
	 *
	 */
	public static Memory allocStaticMemory(int size) { return null; }

	/**
	 * This function will create an memory object for a empty memory range. 
	 *
	 * This function allocates an immortal and empty memory object as handle
	 * for later memory access. It is the same as allocStaticMemory(0). The Handle
	 * can be used togehter with adjustMemory(...) to access memory ranges.
	 */
	public static Memory allocStaticMemoryHandle() { return null; }

	/**
	 * This function changes the memory address and range of a memory object. 
	 *
	 * @param dst the memory object which is adjusted.
	 * @param src the memory object which provides access to the source memory. 
	 * @param offset the offset inside the source memory.
	 * @param length the length of the memory range.
	 */
	public static int adjustMemory(Memory src, int soff, Memory dst, int len) {
		return 0;
	}

	/**
	 * Release the memory range.
	 */
	public static void releaseMemory(Memory mem) { }

	/**
	 * copy the data from src memory into the dst memory.
	 *
	 * @param src  Memory source
	 * @param soff Offset in source memory
	 * @param dst  Memory destinateion
	 * @param doff Offset in destination memory
	 * @param len  length
	 *
	 * HINT:
	 * Use memory ranges if possible.
	 */
	public static void copy(Memory src, int soff, Memory dst, int doff, int len) { }	

	/**
	 * This function will allocate a memory block that can be used like physical
	 * memory. In contrast to device memory, a memory area of the specified size
	 * will be allocated, but not on the domain heap. This memory area can be
	 * used as shared memory.
	 *
	 * The function has a maximum complexity O(n) to find a free block and to
	 * defragment the shared memory area. Where n depends on the fragmentation
	 * of the memory.  Therefore do not allocate shared memory in a time
	 * critical execution path.
	 *
	 * @param size size of the requested memory area
	 *
	 * @param timeout specify the behavior of the core shared memory allocation
	 * method if not enough free memory space exists to satisfy the request in
	 * constant time. 
	 *
	 * If timeout has the value:
	 *
	 * MemoryService.NOBLOCK: The method will return instantly with a NULL Pointer
	 * 			  if not enought memory space is available.
	 *
	 * value greater 0:	  The method perform garbage collection and wait until
	 * 			  enought space is available or timeout seconds elapsed.
	 *                	  (TODO: not implemented yet)
	 *
	 * MemoryService.UNPRED:  The method perform garbage collection until enought 
	 *                        space available or perform garbage collection. This may need
	 *                        a unpredictable amount of time and still may fail.
	 *
	 * MemoryService.BLOCK:   The method perform garbage collection or waits until enought 
	 *                        space is availablei. This may block for ever, but never
	 *                        returns a null pointer.
	 *                        (TODO: not implemented yet)
	 *
	 * @return a Memory object for accessing the allocated memory area
	 * 
	 * HINT:
	 * Prefer the use of allocStaticMemory or allocStaticMemoryHandle to create
	 * a smaller application.
	 *
	 */
	public static Memory allocMemory(int size, int timeout) { return null; }
}

