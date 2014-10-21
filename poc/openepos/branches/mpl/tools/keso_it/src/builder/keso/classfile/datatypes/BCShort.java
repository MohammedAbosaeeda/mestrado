/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/


package keso.classfile.datatypes; 
final public class BCShort extends BCIntegerDatatype {
  private short value; 

  public BCShort(short value) {this.value = value;}
  public short value() {return value;}
  public long longValue() {return value;}
  public String toString() {return String.valueOf(value); }
  public int type() {return SHORT;}

  protected BCIntegerDatatype getObjectFor(long value) {
    return new BCShort((short)value); 
  }
}
