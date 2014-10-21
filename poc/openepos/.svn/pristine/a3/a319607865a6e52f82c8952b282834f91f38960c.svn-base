/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler; 

import keso.compiler.imcode.IMClass;

final public class ClassTypeInfo {

	private IMClass type;
	private int 	start;
	private int 	end;

	public ClassTypeInfo(IMClass type, int start, int end) {
		this.type = type;
		this.start = start;
		this.end = end;
	}

	public boolean hasSubTypes() {
		return (start!=end);
	}

	public IMClass getIMClass() {
		return type;
	}

	public boolean isFinal() {
		return (start==end);
	}

	public int getClassTypeID() {
		return start;
	}

	public int getTypeRange() {
		return end;
	}

	public String toDigString() {
		if (type==null) {
			if (start==1) return "java/lang/Object\\n(1-"+end+")";
			return "null ("+start+"-"+end+")";
		}
		return type.getClassName()+"\\n("+start+"-"+end+")";
	}

	public String toString() {
		if (type==null) {
			if (start==1) return "java/lang/Object (1-"+end+")";
			return "null ("+start+"-"+end+")";
		}
		return type.getClassName()+" ("+start+"-"+end+")";
	}
}
