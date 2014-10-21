/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class FilterInputStream extends InputStream
{ 
	protected InputStream in;

	public int available() throws IOException
	{
		return in.available();
	}

	public void close() throws IOException
	{
		in.close();
	}

	public void mark(int readlimit)
	{
		in.mark(readlimit);
	}

	public boolean markSupported()
	{
		return in.markSupported();
	}

	public int read() throws IOException
	{
		return in.read();
	}

	public int read(byte[] b) throws IOException
	{
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException
	{
		return in.read(b, off, len);
	}

	public void reset() throws IOException
	{
		in.reset();
	}

	public long skip(long n) throws IOException
	{
		return in.skip(n);
	}

	protected FilterInputStream(InputStream in)
	{
		this.in = in;
	}
}

