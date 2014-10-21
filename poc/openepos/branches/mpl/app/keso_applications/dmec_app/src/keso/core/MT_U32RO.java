/**(c)

  Copyright (C) 2006 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

final public class MT_U32RO extends MT {

	private MT_U32RO self;

	public MT_U32RO() { throw new Error("not instanceable"); }

	public int get() { return self.get(); }

	public void set(int i) { throw new RuntimeException("write access denied"); }

	public void and(int i) { throw new RuntimeException("write access denied"); }

	public void or(int i) { throw new RuntimeException("write access denied"); }

	public void xor(int i) { throw new RuntimeException("write access denied"); }

	public boolean equals(Object o) { return (self.hashCode()==((MT_U32RO)o).hashCode()); }

	public int hashCode() { return self.hashCode(); }

	public String toString() { return "U32RO: "+self.get(); }
}
