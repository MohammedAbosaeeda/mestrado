/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/


package keso.classfile.datatypes; 
final public class BCLong extends BCIntegerDatatype {
  private long value; 
  
  public BCLong(long value) {this.value = value;}
  public long value() {return value;}
  public long longValue() {return value;}
  public int type() {return LONG;}

  public String toString() {return String.valueOf(value); }

  public static final BCLong VALUE_0 = new BCLong(0); 
  public static final BCLong VALUE_1 = new BCLong(1); 

  protected BCIntegerDatatype getObjectFor(long value) {
    return new BCLong(value); 
  }

}
