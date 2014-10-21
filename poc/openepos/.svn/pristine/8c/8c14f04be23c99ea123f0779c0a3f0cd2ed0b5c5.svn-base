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

/** 
    This class represents a constant pool of a class. 
    It stores a array of entries, each of which contains the
    data of a constant pool entry. 
    It can be initialized either with a java class file, or with 
    a instance of MetaxaConstantPool, or with a ClassInterface. 
    The last way ist actually deprecated, and not used anymore. 
    
    When initialized with a MetaxaConstantPool, the entries are
    loaded on demand, i.e. an entry is loaded when it is referenced 
    using one of the entryAt()-Methods. Only entry types that are actually 
    referenced by bytecode can be loaded through MetaxaConstantPool 
    (e.g. NameAndTypeCPEntry's can not). 
*/ 
public class ConstantPool {

	// the number of entries in the constant pool + 1
	private int numEntries; 

	/** the array of cp_info structures, numEntries in size.
	 * 0 is not a valid index into the CP (see JVM Spec).
	 * The first entry in this array is therefore not valid.
	 * A dummy entry will be placed in there.
	 */
	private ConstantPoolEntry[] entry; 

	/** 
	  necessary to access string entries of this constantpool through 
	  the interface in bytecode.execenv.ExecEnvironment
	  */ 
	private String className, superClassName; 

	/**  
	  constructors for reading from classFile 
	  (call readFromClassFile() after using this constructor) 
	  */   
	public ConstantPool() {
		//    throw new RuntimeException(); // this constructor is unsafe, does not create the entry array!
	}

	/**  
	  constructors for reading from classFile 
	  */ 
	public ConstantPool(DataInput input) throws IOException {
		readFromClassFile(input); 
	}

	/** 
	  set the index of the entry that contains the name of the 
	  constantpools class. 
	  */ 
	public void setThisClassCPIndex(int thisClassCPIndex) {
		className = ((ClassCPEntry)entryAt(thisClassCPIndex)).getClassName(); 
	}

	public void setSuperClassCPIndex(int cpIndex) {
		superClassName = ((ClassCPEntry)entryAt(cpIndex)).getClassName(); 
	}

	/** 
	  this method is necessay to access the address of 
	  strings in the constant pool through the interface int
	  bytecode.execenv.ExecEnvironment
	  */ 
	public String getClassName() {
		return className; 
	}

	public String getSuperClassName() {
		return superClassName; 
	}

	/** 
	  read all entries from a class file 
	  */
	public void readFromClassFile(DataInput input) throws IOException {
		numEntries = input.readUnsignedShort(); 
		entry = new ConstantPoolEntry[numEntries]; 
		entry[0] = new DummyCPEntry(); 
		int i = 1; 

		/* Read the constant pool. The number of entries is numEntries-1,
		 * valid indices range from 1 to numEntries.
		 */
		while (i < numEntries) {
			ConstantPoolEntry newEntry=null;     
			int tag = input.readUnsignedByte(); 

			switch (tag) {
				case ConstantPoolEntry.CONSTANT_UTF8: 
					newEntry = new UTF8CPEntry();
					break; 
				case ConstantPoolEntry.CONSTANT_INTEGER:
				case ConstantPoolEntry.CONSTANT_FLOAT:
				case ConstantPoolEntry.CONSTANT_LONG:
				case ConstantPoolEntry.CONSTANT_DOUBLE: 
					newEntry = new NumericCPEntry(tag);
					break; 
				case ConstantPoolEntry.CONSTANT_CLASS:	
					newEntry = new ClassCPEntry();
					break; 
				case ConstantPoolEntry.CONSTANT_STRING: 
					newEntry = new StringCPEntry();
					break; 
				case ConstantPoolEntry.CONSTANT_FIELDREF: 
					newEntry = new FieldRefCPEntry();
					break; 
				case ConstantPoolEntry.CONSTANT_METHODREF: 
					newEntry = new MethodRefCPEntry();
					break; 
				case ConstantPoolEntry.CONSTANT_INTERFACEMETHODREF: 
					newEntry = new InterfaceMethodRefCPEntry();
					break; 
				case ConstantPoolEntry.CONSTANT_NAMEANDTYPE: 
					newEntry = new NameAndTypeCPEntry();
					break; 
				default: 
					Debug.ASSERT(false, "This must not happen "+tag); 
					break; 
			}

			newEntry.readFromClassFile(input); 
			newEntry.setCPIndex(i);  

			this.entry[i++] = newEntry; 

			if (tag == ConstantPoolEntry.CONSTANT_LONG || 
					tag == ConstantPoolEntry.CONSTANT_DOUBLE) 
				this.entry[i++] = new DummyCPEntry();
		}

		// link the entries 
		for(int j=0;j<numEntries;j++) 
			entry[j].linkCPEntries(this); 
	}


	// ***** Methods to access the entries ***** 

	public ConstantPoolEntry constantEntryAt(int index) {
		if (entry[index]==null) {
			Debug.throwError();
		}
		return entry[index]; 
	}


	public ClassCPEntry classEntryAt(int cpIndex) {
		if (entry[cpIndex]==null) {
			Debug.throwError();
		}
		if (!(entry[cpIndex] instanceof ClassCPEntry)) {
			Debug.out.println("cpIndex="+cpIndex+", entry="+getEntryStringAt(cpIndex));
			Debug.throwError("wrong CP entry type (expect ClassCPEntry)");
		}
		return (ClassCPEntry)entry[cpIndex]; 
	}

	public MethodRefCPEntry methodRefEntryAt(int index) {
		if (entry[index]==null)  {
			Debug.throwError();
		}
		return (MethodRefCPEntry)entry[index]; 
	}

	public FieldRefCPEntry fieldRefEntryAt(int index) {
		if (entry[index]==null)  {
			Debug.throwError();
		}
		return (FieldRefCPEntry)entry[index]; 
	}

	public InterfaceMethodRefCPEntry InterfaceMethodRefEntryAt(int index) {
		if (entry[index]==null)  {
			Debug.throwError();
		}
		return (InterfaceMethodRefCPEntry)entry[index]; 
	}

	/** 
	  only if the entries are loaded from a class file, 
	  you can use this method to access them. Otherwise, this 
	  class will throw an error. 
	  It is better to use the methods above for this task. 
	  */ 
	public ConstantPoolEntry entryAt(int index) {
		if (index == -1) {
			throw new Error("Attempt to access a dynamically generated CPEntry via an index.");
		}
		if (entry[index]==null)
			Debug.throwError("Accessing of entries not allowed"); 

		return entry[index];
	}

	public String toString() {
		String string = ""; 
		for(int i=0;i<numEntries;i++) {
			string += ""+i+" : "+entry[i].getTypeString() + ": " + 
				entry[i].getDescription(this, true) + "\n"; 
		}
		return string;
	}

	public int getNumberOfEntries() { return numEntries; }

	public String getEntryStringAt(int cpIndex) {
		return "(" + cpIndex + ") " + entry[cpIndex].getDescription(this, true); 
	}

	public String getUTF8StringAt(int cpIndex) {
		if (! (entry[cpIndex] instanceof UTF8CPEntry)) {
			Debug.out.println("cpIndex="+cpIndex+", entry="+getEntryStringAt(cpIndex));
			Debug.throwError("wrong CP entry type");
		}
		return ((UTF8CPEntry)entry[cpIndex]).value();
	}  

	// the next two functions are not used 

	private  BCBasicDatatype getNumericValueAt(int cpIndex) {
		return ((NumericCPEntry)entry[cpIndex]).value(); 
	}

	private int getIntegerAt(int cpIndex) {
		return ((BCInteger)getNumericValueAt(cpIndex)).value(); 
	}

	/*************** MODIFICATION SUPPORT **********************/
	/* The following methods can be used to modify the 
	 * constantpool, for example, to add new entries.
	 * This feature is used for the dynamic bytecode generation.
	 ***********************************************************/

	/** @return cp index of added entry */
	public int addEntry(ConstantPoolEntry newEntry) {
		int index = numEntries;
		newEntry.setCPIndex(index); 
		if (entry.length == numEntries) {
			ConstantPoolEntry[] e = new ConstantPoolEntry[numEntries + 10];
			System.arraycopy(entry, 0, e, 0, numEntries);
			entry = e;
		}
		entry[index] = newEntry; 
		numEntries++;
		return index;
	}
} 
