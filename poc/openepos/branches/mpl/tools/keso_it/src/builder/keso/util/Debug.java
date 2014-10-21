/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.util;

import java.io.PrintStream;

/**
 * Compile with -debug to activate message and assert
 */
final public class Debug {

	public static PrintStream out = System.err;

	public static void message(String message) {
		Debug.out.println(message);
	}

	public final static void ASSERT(boolean condition) {
		ASSERT(condition,""); 
	}

	public static final void ASSERT(boolean condition, String message) {
		if (!condition) {
			message("Assertion Failed :"+message);
			throw new Error("Assertion Failed :"+message);
		}
	}

	public static final void throwError() {
		throw new Error("ThrowError called :");
	}

	public static final void throwError(String message) {
		message(message);
		throw new Error("ThrowError called :" + message); 
	}    
}
