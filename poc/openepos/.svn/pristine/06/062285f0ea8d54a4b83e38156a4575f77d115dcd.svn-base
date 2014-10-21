/**(c)

  Copyright (C) 2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.util;

public class Stack {

	private Object[] stack;
	private int ptr;
	private int[] marks;
	private int mptr;

	public Stack() {
		this(64);
	}

	public Stack(int size) {
		stack=new Object[size];
		ptr=0;
	}

	public void push(Object slot) {
		if (slot==null) throw new Error();
		if (ptr>=stack.length) extend();
		stack[ptr++]=slot;
	}

	public Object top() {
		if (ptr<=0) return null;
		return stack[ptr-1];
	}

	public Object pop() {
		if (ptr<=0) return null;
		return stack[--ptr];
	}

	public void mark() {
		if (marks==null) {
			marks=new int[32];
			mptr=0;
		}
		if (mptr>=marks.length) {
			int nm[] = new int[marks.length+8];
			for (int i=0;i<marks.length;i++) nm[i]=marks[i];
			marks=nm;
		}
		marks[mptr++]=ptr;
	}

	public void reset() {
		ptr=marks[--mptr];
	}

	public void clear() {
		stack=new Object[stack.length];
		marks=null;
		ptr=0;
	}

	private void extend() {
		Object[] new_stack = new Object[stack.length+64];
		for (int i=0;i<stack.length;i++) new_stack[i]=stack[i];
		stack=new_stack;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("[");
		for (int i=0;i<ptr;i++) {
			if (i>0) ret.append(",");
			ret.append(stack[i]);
		}
		ret.append("]");
		return ret.toString();
	}
}
