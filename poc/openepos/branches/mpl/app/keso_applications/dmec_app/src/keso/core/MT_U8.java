/**(c)

  Copyright (C) 2006 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

final public class MT_U8 extends MT {

	private MT_U8 self;

	public MT_U8() { throw new Error("not instanceable"); }

	public int get() { return self.get(); }

	public void set(int i) { self.set(i); }

	public void and(int i) { self.and(i); }

	public void or(int i) { self.or(i); }

	public void xor(int i) { self.xor(i); }

	public void setBit(int i) { self.setBit(i); }

	public void clearBit(int i) { self.clearBit(i); }

	public boolean isBitSet(int i) { return self.isBitSet(i); }

	public boolean isBitClear(int i) { return self.isBitClear(i); }

	public boolean equals(Object o) { return (self.hashCode()==((MT_U8)o).hashCode()); }

	public int hashCode() { return self.hashCode(); }

	public String toString() { return "U8: "+self.get(); }
}