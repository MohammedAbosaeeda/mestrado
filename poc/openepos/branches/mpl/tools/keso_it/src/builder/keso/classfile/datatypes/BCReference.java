/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/


package keso.classfile.datatypes;   

final public class BCReference extends BCBasicDatatype {
  private int value; 

  public BCReference(int value) {this.value = value;}
  public int value() {return value;}
  public String toString() {
    if (value==0) 
      return "NULL"; 
    else 
      return "Ref:"+String.valueOf(value); 
  }
  public int type() {return REFERENCE;}

  public static final BCReference NULL = new BCReference(0); 

}
