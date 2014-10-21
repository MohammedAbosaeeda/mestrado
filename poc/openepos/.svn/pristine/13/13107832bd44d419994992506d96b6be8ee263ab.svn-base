/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;

import java.util.TreeSet;
import java.util.Set;

import java.lang.Comparable;

/**
 * This class is used to transport information to the builder
 * after a configuration has entirely been read.
 * It contains things the builder might need and that are not statically
 * known.
 */
public final class FinalizeResult {
	/**
	 * Contains ProtectedMethod Objects for each protected Method.
	 */
	public final Set requiredMethods;

	FinalizeResult() {
		requiredMethods = new TreeSet();
	}

	public void requireMethod(String className, String method) {
		requiredMethods.add(new ProtectedMethod(className, method));
	}
	
	public final class ProtectedMethod implements Comparable {
		public final String className;
		public final String method;

		public ProtectedMethod(String c, String m) {
			className=c;
			method=m;
		}

		public int compareTo(Object o) {
			if(o instanceof ProtectedMethod) {
				ProtectedMethod p = (ProtectedMethod) o;
				int result = p.className.compareTo(className);

				if(result!=0) return result;
				if(p.method==null && method==null) return 0;
				if(p.method==null) return 1;
				if(method==null) return -1;
				return p.method.compareTo(method);
			}
			// should not happen in our context..
			return 1;
		}

		public boolean equals(Object o) {
			return compareTo(o)==0;
		}
	}
}
