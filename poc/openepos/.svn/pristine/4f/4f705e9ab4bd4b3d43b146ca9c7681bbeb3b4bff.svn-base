/**(c)

  Copyright (C) 2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.classfile; 

import java.io.*; 
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

public class InnerClassData {

	final private ConstantPool cPool;
	private int inner_class;
	private int outer_class;
	private int inner_name;
	private int inner_access_flags;

	public InnerClassData(DataInput input, ConstantPool cPool) throws IOException {
		this.cPool = cPool;
		inner_class = input.readShort();
		outer_class = input.readShort();
		inner_name = input.readShort();
		inner_access_flags = input.readShort();
	}
}
