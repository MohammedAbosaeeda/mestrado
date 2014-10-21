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
 * All data about a field, that can be found
 * in a class file. This is a rather passive class.
 * It only reads the data from the class file and
 * stores it.
 */
public class FieldData {
	private int accessFlags;
	private int fieldNameCPIndex;
	private int fieldTypeCPIndex;
	private boolean hasInitialValue;
	private int constantValueCPIndex;
	private  ConstantPool constantPool;
	ClassData declaringClass;

	public FieldData(ClassData declaringClass, DataInput input, ConstantPool cPool) throws IOException {
		this.constantPool = cPool;
		this.declaringClass = declaringClass;
		readFromClassFile(input, cPool);
	}

	public void readFromClassFile(DataInput input, ConstantPool cPool)
		throws IOException {
	accessFlags = input.readUnsignedShort();
	fieldNameCPIndex = input.readUnsignedShort();
	fieldTypeCPIndex = input.readUnsignedShort();

	hasInitialValue = false;

	// System.out.println(this.getDescription(cPool)+"\n");

	int numAttributes = input.readUnsignedShort();
	for(int i=0; i<numAttributes; i++)
		readAttribute(input, cPool);
	}

	private void readAttribute(DataInput input, ConstantPool cPool)
		throws IOException {
		int attrNameCPIndex = input.readUnsignedShort();
		int numBytes = input.readInt();
		input.skipBytes(numBytes);
	}

	public String getName(ConstantPool cPool) {
		return constantPool.getUTF8StringAt(fieldNameCPIndex);
	}

	public String getType(ConstantPool cPool) {
		return cPool.getUTF8StringAt(fieldTypeCPIndex);
	}

	public String getName() {
		return getName(constantPool);
	}

	public String getType() {
		return getType(constantPool);
	}

	public BasicTypeDescriptor getBasicType() {
		return new BasicTypeDescriptor(getType());
	}

	/**
		@return a descriptive string for this field
	*/
	public String getDescription(ConstantPool cPool) {
		return "" + accessFlags + ", " +
			cPool.entryAt(fieldNameCPIndex).getDescription(cPool, true) + ", " +
			cPool.entryAt(fieldTypeCPIndex).getDescription(cPool, true);
	}

	public boolean isPublic() {return ClassData.isPublic(accessFlags);}
	public boolean isPrivate() {return ClassData.isPrivate(accessFlags);}
	public boolean isProtected() {return ClassData.isProtected(accessFlags);}
	public boolean isStatic() {return ClassData.isStatic(accessFlags);}
	public boolean isFinal() {return ClassData.isFinal(accessFlags);}
	public boolean isVolatile() {return ClassData.isVolatile(accessFlags);}
	public boolean isTransient() {return ClassData.isTransient(accessFlags);}

	/*
	 * Java Reflection "compatible" methods
	 */
	public int getModifiers() { return accessFlags; }
	public ClassData getDeclaringClass() { return declaringClass; }
}
