/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler.imcode;

import keso.classfile.*;
import keso.classfile.constantpool.*;
import keso.classfile.datatypes.*;

import keso.compiler.*;

import keso.util.Debug;

public class IMOperant extends IMNode {

	public IMOperant(IMMethod method, int bc, int type, int bcpos) {
		super(method,bc,type,bcpos);
	}

	public boolean isAdd() {return false;}

	public boolean isSub() {return false;}

	public boolean isMul() {return false;}

	public boolean isBitOpr() {return false;}

	public IMOperant getLeftOperant() {
		return null;
	}

	public IMOperant getRightOperant() {
		return null;
	}

	public boolean checkReference() {
		return true;
	}

	public boolean checkArrayRange(int index) {
		return true;
	}

	public boolean checkArrayRange(IMOperant index) {
		return true;
	}
}
