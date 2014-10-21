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

public class DummyCPEntry extends ConstantPoolEntry {
  
  public int getTag() {return -1;}
  
  void readFromClassFile(DataInput input) throws IOException {
  }

  public String getSimpleDescription() { return "NoValue";}
}
