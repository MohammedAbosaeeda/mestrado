/**(c)

  Copyright (C) 2005 Christian Wawersich 

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
import keso.util.Debug; 

/** 
    All data about a exception handler, that can be found 
    in a class file. This is a rather passive class. 
    It only reads the data from the class file and 
    stores it. 
*/ 
public class ExceptionHandlerData {

	private int startBCIndex; 
	private int endBCIndex; 
	private int handlerBCIndex; 
	private int catchTypeCPIndex; 
	private ClassCPEntry catchTypeCPEntry; 

	public ExceptionHandlerData() {}

	public ExceptionHandlerData(DataInput input, ConstantPool cPool) throws IOException {
		readFromClassFile(input, cPool); 
	}

	//constructor for copy
	public ExceptionHandlerData(int startBCIndex, int endBCIndex, 
			int handlerBCIndex, int catchTypeCPIndex) {
		this.startBCIndex = startBCIndex;
		this.endBCIndex = endBCIndex;
		this.handlerBCIndex = handlerBCIndex;
		this.catchTypeCPIndex = catchTypeCPIndex;
	}

	public ExceptionHandlerData copy() {
		return new ExceptionHandlerData(startBCIndex, endBCIndex, handlerBCIndex, 
				catchTypeCPIndex);
	}

	public int getStartBCIndex() {return startBCIndex;}
	public int getEndBCIndex() {return endBCIndex; }
	public int getHandlerBCIndex() {return handlerBCIndex;}
	public int getCatchTypeCPIndex() {return catchTypeCPIndex; }

	public String getCatchTypeCPEntry() {
		if (catchTypeCPEntry==null) return null;
		return catchTypeCPEntry.getClassName();
	}

	/**
	 * read exception_table 
	 *
	 * @see JVM Spec 4.7.3 The Code Attribute
	 */
	public void readFromClassFile(DataInput input, ConstantPool cPool) throws IOException {
		startBCIndex = input.readUnsignedShort(); 
		endBCIndex = input.readUnsignedShort(); 
		handlerBCIndex = input.readUnsignedShort(); 
		catchTypeCPIndex = input.readUnsignedShort(); 
		if (catchTypeCPIndex!=0) { 
			catchTypeCPEntry = (ClassCPEntry)cPool.entryAt(catchTypeCPIndex); 
		}
	}

	public String toString() {
		return new String(startBCIndex+" - "+endBCIndex+" handler "+handlerBCIndex+" type "+catchTypeCPIndex);
	}
}
