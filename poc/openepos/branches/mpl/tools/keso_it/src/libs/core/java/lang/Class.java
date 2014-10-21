/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.lang;

public final class Class
{
    public String getName() { return null; }
    
    public boolean isInterface() { return false; }

    public static Class getPrimitiveClass(String cname) { return null ; }

    public String toString()
    {
	return (isInterface() ? "interface " : "class ") + getName();
    }

    //public static Class forName(String className) throws ClassNotFoundException { }

    //public Class getComponentType() { throw new Error(); }

    //public boolean isInstance(Object o) { throw new Error("NOT IMPLEMENTED"); }

    //public Method[] getDeclaredMethods()  { throw new Error("NOT IMPLEMENTED"); }

    //public Class getSuperclass() { return null; }
    
    //public Class[] getInterfaces() { return null; }
    
    //public ClassLoader getClassLoader() { throw new Error("NOT IMPLEMENTED"); }
   
    //public Object newInstance() throws InstantiationException, IllegalAccessException { }
    
    // public boolean isAssignableFrom(Class c) { throw new Error("NOT IMPLEMENTED"); }

}
