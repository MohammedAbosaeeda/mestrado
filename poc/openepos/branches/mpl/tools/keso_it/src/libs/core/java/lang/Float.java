/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.lang;

public final class Float extends Number {

    public static final Class	TYPE = Class.getPrimitiveClass("float");

    public static final float POSITIVE_INFINITY = 1.0f / 0.0f;
    public static final float NEGATIVE_INFINITY = -1.0f / 0.0f;
    public static final float NaN = 0.0f / 0.0f;
    public static final float MAX_VALUE = 3.40282346638528860e+38f;
    public static final float MIN_VALUE = 1.40129846432481707e-45f;

    private float value;

    public Float(float value) {
	this.value = value;
    }

    public Float(double value) {
	this.value = (float)value;
    }

    public byte byteValue() {
	return (byte)value;
    }
    public short shortValue() {
	return (short)value;
    }
    public int intValue() {
	return (int)value;
    }
    public long longValue() {
	return (long)value;
    }
    public float floatValue() {
	return value;
    }
    public double doubleValue() {
	return (double)value;
    }
    public static Float valueOf(String s) throws NumberFormatException { 
	throw new Error("NOT IMPLEMENTED");
    }

    public static String toString(float f){
	throw new Error("NOT IMPLEMENTED");
	//return new FloatingDecimal(f).toJavaFormatString();
    }

    static public boolean isNaN(float v) {
	return (v != v);
    }

    /*

    public static Float valueOf(String s) throws NumberFormatException { 
	return new Float(Double.valueOf0(s));
    }

    static public boolean isInfinite(float v) {
	return (v == POSITIVE_INFINITY) || (v == NEGATIVE_INFINITY);
    }


    public Float(String s) throws NumberFormatException {
	// REMIND: this is inefficient
	this(valueOf(s).floatValue());
    }

    public boolean isNaN() {
	return isNaN(value);
    }
    public boolean isInfinite() {
	return isInfinite(value);
    }
    public String toString() {
	return String.valueOf(value);
    }
    public int hashCode() {
	return floatToIntBits(value);
    }
    public boolean equals(Object obj) {
	return (obj != null)
	       && (obj instanceof Float) 
	       && (floatToIntBits(((Float)obj).value) == floatToIntBits(value));
    }
*/
    public static  int floatToIntBits(float value) { throw new Error(); }
    public static  float intBitsToFloat(int bits) { throw new Error(); }

    public static float parseFloat(java.lang.String s) { throw new Error(); }
    public static int compare(float a, float b) {throw new Error();}
}
