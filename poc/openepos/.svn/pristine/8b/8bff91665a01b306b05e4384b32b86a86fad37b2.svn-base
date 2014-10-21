/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.classfile.constantpool; 
import java.io.*; 
import keso.classfile.datatypes.*; 
import keso.util.Debug; 

public class UTF8CPEntry extends ConstantPoolEntry {
	private String value; 
	private int stringAddress; 

	public UTF8CPEntry() {}

	public UTF8CPEntry(String value, int stringAddress) {
		this.value = value;
		this.stringAddress = stringAddress; 
	}

	public UTF8CPEntry(String value) {
		this.value = value;
		this.stringAddress = 0; 
	}

	public int getTag() {return CONSTANT_UTF8;}

	void readFromClassFile(DataInput input) throws IOException {
		value = input.readUTF(); 
	}

	public String value() {return value;}

	public String getSimpleDescription() {
		return value; 
	}

	public String getDescription(ConstantPool cPool, boolean withIndex) {
		return "\"" + getSimpleDescription() + "\""; 
	}

	public String getDescription() {
		return "\"" + value + "\"";
	}

	int getStringAddress() {return stringAddress;}
}
