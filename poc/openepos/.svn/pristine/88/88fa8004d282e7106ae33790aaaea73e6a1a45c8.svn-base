/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/


package keso.classfile.datatypes; 

abstract public class BCIntegerDatatype extends BCNumericDatatype {
  
  abstract long longValue(); 

  abstract protected BCIntegerDatatype getObjectFor(long value);  

  // perform an arithmetic operation with this class 
  public BCNumericDatatype combined(int operator, BCNumericDatatype op2) {
    long val1 = this.longValue(); 
    long val2 = ((BCIntegerDatatype)op2).longValue(); 
    long result=0; 
    switch (operator) {
    case ADD:  result = val1 + val2;   break; 
    case SUB:  result = val1 - val2;   break; 
    case MUL:  result = val1 * val2;   break; 
    case DIV:  result = val1 / val2;   break; 
    case REM:  result = val1 % val2;   break; 
    case SHL:  result = val1 << val2;  break; 
    case SHR:  result = val1 >> val2;  break; 
    case USHR: result = val1 >>> val2; break; 
    case AND:  result = val1 & val2;   break; 
    case OR:   result = val1 | val2;   break; 
    case XOR:  result = val1 ^ val2;   break; 
    }
    return getObjectFor(result); 
  }

  public BCNumericDatatype negated() {
    return getObjectFor(-this.longValue());
  } 

}
