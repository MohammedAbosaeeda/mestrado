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
public class InterfaceMethodRefCPEntry extends ClassMemberCPEntry {

  public InterfaceMethodRefCPEntry() {}
  
  public InterfaceMethodRefCPEntry(int classCPIndex, int nameAndTypeCPIndex) {
    super(classCPIndex, nameAndTypeCPIndex);
  } 

  // for metaXa interface, incomplete constructor (indices not initialized)   
  public InterfaceMethodRefCPEntry(ClassCPEntry classCPEntry, 
				   NameAndTypeCPEntry nameAndTypeCPEntry) {
    super(classCPEntry, nameAndTypeCPEntry); 
  }

  public int getTag() {return CONSTANT_INTERFACEMETHODREF;}

  int getMethodIndex() {return 0;}
  int getInterfaceID() {return 0;}
} 
