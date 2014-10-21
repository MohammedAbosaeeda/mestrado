/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class FileOutputStream extends OutputStream
{ 
	public FileOutputStream(String name) throws IOException
	{
		throw new IOException();
	}

/*
	public FileOutputStream(File file) throws IOException
	{
		throw new IOException();
	}

	public FileOutputStream(FileDescriptor fd)
	{
		this.fd = fd;
	}

	public FileDescriptor getFD() throws IOException
	{
		if (fd == null)
			throw new IOException();
		return fd;
	}
*/
	public void write(int b) throws IOException
	{
		byte[] buf = new byte[1];
		buf[0] = (byte) (b & 0xFF);
		write(buf);
	}

	public void write(byte[] b) throws IOException
	{
		write(b, 0, b.length);
	}

	public void write(byte[] b, int off, int len) throws IOException
	{
		throw new IOException();
	}

	public synchronized void close() throws IOException
	{
		throw new IOException();
	}

	protected synchronized void finalize() throws IOException
	{
		// TODO:
	}
}
