/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.util;

final public class Bitmap {

	private int size;
	private int asize;
	private int bitmap[];

	public Bitmap(int size) {
		this.size = size;
		this.asize = (size+31)/32;	
		this.bitmap = new int[asize];
	}

	public void mark(int pos) {
		if (pos<0 || pos>=size) throw new IndexOutOfBoundsException("pos="+pos+" size="+size);
		int p = pos / 32;
		int b = pos % 32;
		bitmap[p] |= (1<<b);
	}

	public boolean test(int pos) {
		if (pos<0 || pos>=size) throw new IndexOutOfBoundsException("pos="+pos+" size="+size);
		int p = pos / 32;
		int b = pos % 32;
		return ((bitmap[p] & (1<<b))!=0);
	}

	public void clear() {
		bitmap = new int[asize];
	}

	public int getSize() {
		return asize;
	}

	public int value(int pos) {
		return bitmap[pos];
	}

	public String emit_flat() {
		String ret="";
		for (int i=0;i<bitmap.length;i++) {
			ret+="0x"+Integer.toHexString(bitmap[i])+",";
		}
		return ret;
	}

	public String emit() {
		String ret = "{";
		for (int i=0;i<bitmap.length;i++) {
			if (i>0) ret+=",";
			ret+=bitmap[i];
		}
		ret+="}";
		return ret;
	}

	public boolean equals(Object obj) {
		if (obj==null || !(obj instanceof Bitmap)) return false;
		Bitmap bit = (Bitmap) obj;
		if (bit.bitmap.length!=bitmap.length) return false;
		for (int i=0;i<bitmap.length;i++) 
			if (bit.bitmap[i]!=bitmap[i]) return false;
		return true;
	}

	public int hashCode() {
		int value=0;
		for (int i=0;i<bitmap.length;i++) value += bitmap[i];
		return value;
	}
}
