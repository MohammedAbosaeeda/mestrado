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

public class MethodRefCPEntry extends ClassMemberCPEntry {

  // index of this method in the methodtable 
  private int methodIndex; 

  public MethodRefCPEntry() {}
  
  public MethodRefCPEntry(int classCPIndex, int nameAndTypeCPIndex) {
    super(classCPIndex, nameAndTypeCPIndex);
  } 

  MethodRefCPEntry(int classCPIndex, int nameAndTypeCPIndex, 
			  int methodIndex) {
    super(classCPIndex, nameAndTypeCPIndex);
    this.methodIndex = methodIndex; 
  } 

  // for metaXa interface, incomplete constructor (indices not initialized)   
  public MethodRefCPEntry(ClassCPEntry classCPEntry, 
			  NameAndTypeCPEntry nameAndTypeCPEntry) {
    super(classCPEntry, nameAndTypeCPEntry); 
  }

  public int getTag() {return CONSTANT_METHODREF;}

  int getMethodIndex() {return 0;}

  int getStaticMethodAddress() {return 0;}
}
