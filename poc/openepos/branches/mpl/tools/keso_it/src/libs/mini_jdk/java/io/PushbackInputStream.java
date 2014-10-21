/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class PushbackInputStream extends FilterInputStream
{ 
	private int pushBack = -1;  // -1 if no byte available. 

	public PushbackInputStream(InputStream in)
	{
		super(in);
	}

	public PushbackInputStream(InputStream in, int size) {
		super(in);
	}

	public synchronized int available() throws IOException
	{
		return super.available() + (pushBack >= 0 ? 1 : 0);
	}

	public boolean markSupported()
	{
		return false;
	}

	/**
	 * Read a single <em>byte</em> from this stream.
	 *
	 * @return the byte read.
	 */
	public synchronized int read() throws IOException
	{
		int retVal;

		/*
		 * If there is a valid pushback byte, return
		 * that, otherwise, get input from the 
		 * underlying stream.
		 */
		if (pushBack >= 0)
		{
			retVal = /*(byte)*/ pushBack;
			pushBack = -1;
		}
		else
			retVal = super.read();

		return retVal;
	}

	public synchronized int read(byte[] b, int off, int len) throws IOException
	{
		if (pushBack >= 0)
		{
			b[off] = (byte) pushBack;
			pushBack = -1;
			off++;
			len--;
		}
		return super.read(b, off, len);
	}

	public synchronized void unread(int c) throws IOException
	{
		// Fault if we already have a byte pushed back
		if (pushBack >= 0)
			throw new IOException();
		pushBack = c;
	}

}

