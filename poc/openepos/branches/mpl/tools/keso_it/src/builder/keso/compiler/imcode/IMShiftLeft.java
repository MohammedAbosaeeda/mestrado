/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;
import keso.compiler.kni.*;

import keso.util.Debug; 
import keso.util.DecoratedNames; 
import keso.util.IntegerHashtable; 

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;


/* AUTO GENERATED FILE DON'T EDIT */

public class IMShiftLeft extends IMBinaryNode {


	/* copy constuctor */
	protected IMShiftLeft() { }

	public IMShiftLeft(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}

	public boolean hasSideEffect() { return false; }

	public String toReadableString() {
		return "("+lOpr.toReadableString()+" << "+rOpr.toReadableString()+")";
	}

	public String dumpBC() {return "ShiftLeft";}

}
