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

public class Modifier { 
  public static boolean isPublic(int accessFlags) {return (accessFlags & ClassData.ACC_PUBLIC) != 0;}
  public static boolean isPrivate(int accessFlags) {return (accessFlags & ClassData.ACC_PRIVATE) != 0;}
  public static boolean isProtected(int accessFlags) {return (accessFlags & ClassData.ACC_PROTECTED) != 0;}
  public static boolean isStatic(int accessFlags) {return (accessFlags & ClassData.ACC_STATIC) != 0;}
  public static boolean isFinal(int accessFlags) {return (accessFlags & ClassData.ACC_FINAL) != 0;}
  public static boolean isSynchronized(int accessFlags) {return (accessFlags & ClassData.ACC_SYNCHRONIZED) != 0;}
  public static boolean isVolatile(int accessFlags) {return (accessFlags & ClassData.ACC_VOLATILE) != 0;}
  public static boolean isTransient(int accessFlags) {return (accessFlags & ClassData.ACC_TRANSIENT) != 0;}
  public static boolean isNative(int accessFlags) {return (accessFlags & ClassData.ACC_NATIVE) != 0;}
  public static boolean isInterface(int accessFlags) {return (accessFlags & ClassData.ACC_INTERFACE) != 0;}
  public static boolean isAbstract(int accessFlags) {return (accessFlags & ClassData.ACC_ABSTRACT) != 0;}
}
