/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.lang;

import java.io.InputStream;
import java.io.PrintStream;

public class System {

	public static InputStream in= null;
	public static PrintStream out= null;
	public static PrintStream err= null;

	public static void arraycopy(Object src, int srcOffset, Object dst, int dstOffset, int count) {	
		if( src == dst && dstOffset > srcOffset ){
			if (src instanceof byte[] && dst instanceof byte[]) {
				arraycopy_byte_left((byte[])src,srcOffset,(byte[])dst,dstOffset,count);
			} else if (src instanceof char[] && dst instanceof char[]) {
				arraycopy_char_left((char[])src,srcOffset,(char[])dst,dstOffset,count);
			} else {
				arraycopy_left((Object[])src,srcOffset,(Object[])dst,dstOffset,count);
			}
		} else {
			if (src instanceof byte[] && dst instanceof byte[]) {
				arraycopy_byte_right((byte[])src,srcOffset,(byte[])dst,dstOffset,count);
			} else if (src instanceof char[] && dst instanceof char[]) {
				arraycopy_char_right((char[])src,srcOffset,(char[])dst,dstOffset,count);
			} else {
				arraycopy_right((Object[])src,srcOffset,(Object[])dst,dstOffset,count);
			}
		}
	}

	public static void exit(int status) { }

	public static void gc() { }

	public static void load(String filename) { }

	public static void loadLibrary(String libname) { }

	public static String getProperty(String key, String def) {
		return def;
	}

	public static int identityHashCode(Object obj) { return obj.hashCode(); } 

	/* byte[] */
	private static void arraycopy_byte_left(byte[] src, int srcOffset, byte[] dst, int dstOffset, int count) {
		srcOffset += count; dstOffset += count;
		for(int i=0; i<count; ++i) dst[--dstOffset] = src[--srcOffset];
	}

	private static void arraycopy_byte_right(byte[] src, int srcOffset, byte[] dst, int dstOffset, int count) {	
		for(int i=0; i<count; i++) dst[dstOffset+i] = src[srcOffset+i];
	}

	/* char[] */
	private static void arraycopy_char_left(char[] src, int srcOffset, char[] dst, int dstOffset, int count) {
		srcOffset += count; dstOffset += count;
		for(int i=0; i<count; ++i) dst[--dstOffset] = src[--srcOffset];
	}

	private static void arraycopy_char_right(char[] src, int srcOffset, char[] dst, int dstOffset, int count) {
		for(int i=0; i<count; i++) dst[dstOffset+i] = src[srcOffset+i];
	}

	/* else */
	private static void arraycopy_left(Object[] src, int srcOffset, Object[] dst, int dstOffset, int count) {
		srcOffset += count; dstOffset += count;
		for(int i=0; i<count; ++i) dst[--dstOffset] = src[--srcOffset];
	}

	private static void arraycopy_right(Object[] src, int srcOffset, Object[] dst, int dstOffset, int count) {
		for(int i=0; i<count; i++) dst[dstOffset+i] = src[srcOffset+i];
	}
}
