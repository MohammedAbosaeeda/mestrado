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

public class NameAndTypeCPEntry extends ConstantPoolEntry {
	private int nameCPIndex;
	private int typeCPIndex; 

	private UTF8CPEntry nameCPEntry;
	private UTF8CPEntry typeCPEntry; 

	public NameAndTypeCPEntry() {}

	NameAndTypeCPEntry(int nameCPIndex, int typeCPIndex) {
		this.nameCPIndex = nameCPIndex; 
		this.typeCPIndex = typeCPIndex;
	}

	public int getTag() {return CONSTANT_NAMEANDTYPE;}

	void readFromClassFile(DataInput input) throws IOException {
		nameCPIndex = input.readUnsignedShort();
		typeCPIndex = input.readUnsignedShort(); 
	}

	void linkCPEntries(ConstantPool cPool) {
		nameCPEntry = (UTF8CPEntry)cPool.entryAt(nameCPIndex); 
		typeCPEntry = (UTF8CPEntry)cPool.entryAt(typeCPIndex); 
	}

	public String getSimpleDescription() {
		return String.valueOf(nameCPIndex)+", "+
			String.valueOf(typeCPIndex); 
	}

	public String getName() {
		return nameCPEntry.value(); 
	}

	public String getTypeDesc() {
		return typeCPEntry.value(); 
	}

	public String getDescription(ConstantPool cPool, boolean withIndex) {
		return "name=" + getIndexDescString(cPool, nameCPIndex) + ", " + 
			"type=" + getIndexDescString(cPool, typeCPIndex); 
	}

	public String getDescription() {
		return getName() + " (" + getTypeDesc() + ") "; 
	}

	int getNameAddress() {return nameCPEntry.getStringAddress();}

	int getTypeDescAddress() {return typeCPEntry.getStringAddress();}
}
