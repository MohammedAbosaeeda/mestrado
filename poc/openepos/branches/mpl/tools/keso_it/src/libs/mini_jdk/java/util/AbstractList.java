/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.util;

//public abstract class AbstractList extends AbstractCollection implements List 
public abstract class AbstractList  
{
    protected int capacityIncrement;
    protected int elementCount;
    protected Object[] elementData;

    protected final void ensureCapacity(int minCapacity) {
	if (minCapacity <= elementData.length) return;
	minCapacity=minCapacity+10;
	Object[] newData=new Object[minCapacity]; 
	copyInto(newData);
	elementData = newData;
    }

    public final void copyInto(Object[] array)
    {
	    for (int i=0;i<elementCount;i++) {
		    elementData[i] = array[i];
	    }
    }

    public boolean add(Object obj) {
	ensureCapacity(elementCount+1);
	elementData[elementCount] = obj;
	elementCount++;
	return true;
    }
}
