/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.util; 

import java.util.*;

class IntHashtableEnumeration implements Enumeration
{
	private Object[] source;
	private int index;
	private Object next;

	public IntHashtableEnumeration(Object[] src)
	{
		source = src;
		index = 0;
		next_element();
	}

	public boolean hasMoreElements()
	{
		return (next != null);
	}

	public Object nextElement() throws NoSuchElementException
	{
		if (next == null)
			throw new NoSuchElementException();
		Object obj = next;
		next_element();
		return obj;
	}

	private void next_element()
	{
		while (index < source.length)
		{
			Object obj = source[index++];
			if (obj == null)
				continue;

			next = obj;
			return;
		}
		next = null;
	}
}

final public class IntegerHashtable { 

	int[] keys;
	Object[] values;
	int capacity;
	private int load; // "real load" * 100 
	private int mask;
	private int size;

	public IntegerHashtable(int initialCapacity, int loadFactor) {
		this.load = loadFactor;
		size = 0;
		for (capacity = 1; capacity <= initialCapacity; capacity <<= 1) ;
		keys = new int[capacity];
		values = new Object[capacity];
		mask = (capacity - 1);
	}

	public IntegerHashtable(int initialCapacity) {
		this(initialCapacity, 75);
	}

	public IntegerHashtable() {
		this(32);
	}

	public Object get(int key) {

		int hash = key & mask;

		for (int i = 0; i < capacity; i++)
		{
			int index = (hash + i) & mask;

			if (values[index] != null) {
				if (keys[index]==key)
					return values[index];
				// Continue looking through collisions.
				continue;
			}

			// Fall through if nothing found at index
			int k = index;
			int diff = k - i;

			for (int j = i + 1; j < capacity; j++)
			{
				index = (hash + j) & mask;

				if ((values[index] == null) || (!(keys[index]==key)))
					continue;

				keys[k] = keys[index];
				values[k] = values[index];

				values[index] = null;

				return values[k];
			}

			return null;
		}

		return null;
	}

	public int[] keys() {
		int[] ret = new int[size];

		int r=0;
		for (int i=0;i<keys.length;i++) {
			if (values[i]==null) continue;
			ret[r]=keys[i];
			r++;
		}

		return ret;
	}

	public int[] sortedKeys() {
		int[] arr = keys();
		if (arr.length>1) quicksort(arr, 0, arr.length-1);
		return arr;
	}

	public Enumeration elements()
	{
		return new IntHashtableEnumeration(values);
	}

	private static void quicksort (int[] arr, int lo, int hi)
	{
		int i=lo, j=hi;
		int x=arr[(lo+hi)/2];

		while (i<=j)
		{    
			while (arr[i]<x) i++; 
			while (arr[j]>x) j--;
			if (i<=j)
			{
				int s = arr[i];
				arr[i]=arr[j];
				arr[j]=s;
				i++; j--;
			}
		}

		if (i<hi) quicksort(arr, i, hi);
		if (lo<j) quicksort(arr, lo, j);
	}

	public Object put(int key, Object value) {

		if (size*100 > capacity * load) rehash();

		if (value == null)
			throw new NullPointerException();

		int hash = key & mask;

		for (int i = 0; i < capacity; i++)
		{
			int n = (hash + i) & mask;

			if (values[n] != null) {
				if (keys[n]==key) {
					Object obj = values[n];

					keys[n] = key;
					values[n] = value;

					return obj;
				}

				continue;
			}

			keys[n] = key;
			values[n] = value;

			size++;

			for (i++; i < capacity; i++)
			{
				n = (hash + i) & mask;

				if ((values[n] == null) || (!(keys[n]==key)))
					continue;

				Object obj = values[n];

				values[n] = null;

				size--;

				return obj;
			}

			return null;
		}

		return null;
	}

	public Object remove(int key) {
		int hash = key & mask;

		for (int i = 0; i < capacity; i++)
		{
			int n = (hash + i) & mask;

			if (values[n] == null || (!(keys[n]==key)))
				continue;

			Object value = values[n];
			
			values[n] = null;

			size--;

			return value;
		}

		return null;
	}

	public int size() { return size; }

	public boolean isEmpty() { return (size == 0); }

	public boolean containsKey(int key) {

		int hash = key & mask;

		for (int i = 0; i < capacity; i++)
		{
			int n = (hash + i) & mask;

			if ((values[n] != null) && (keys[n]==key))
				return true;
		}
		return false;
	}

	public boolean contains(Object value) {
		if (value == null)
			return false;

		for (int i = 0; i < capacity; i++)
			if ((values[i] != null)
			    && (values[i].equals(value)))
				return true;
		return false;
	}


	private void rehash() {
		while (size*100 > capacity * load)
			capacity <<= 1;
		mask = (capacity - 1);

		int[] oldkeys = keys;
		Object[] oldvalues = values;
		keys = new int[capacity];
		values = new Object[capacity];
		size = 0;

		for (int i = 0; i < oldkeys.length; i++)
			if (oldvalues[i] != null)
				put(oldkeys[i], oldvalues[i]);
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("{");
		boolean first = true;

		for (int i = 0; i < values.length; i++)
			if (values[i] != null)
			{
				if (first)
					first = false;
				else
					buff.append(", ");

				buff.append(String.valueOf(keys[i]));
				buff.append("=");
				buff.append(String.valueOf(values[i]));
			}
		buff.append("}");

		return buff.toString();
	}
}
