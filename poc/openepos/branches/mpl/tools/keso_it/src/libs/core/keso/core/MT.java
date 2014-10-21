/**(c)

  Copyright (C) 2006 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

/**
 * Instances of memory types (MT) class allow access to physical memory 
 * allocated and mapped by the MemoryService.
 */
abstract public class MT {
	private MT self;
	public String toString() {
		return "MT:";
	}
}
