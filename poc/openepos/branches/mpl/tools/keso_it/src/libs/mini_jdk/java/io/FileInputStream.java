/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class FileInputStream extends InputStream
{
//	private FileDescriptor fd;

	public FileInputStream(String name) throws FileNotFoundException
	{
		this(new File(name));

	}

	public FileInputStream(File file) throws FileNotFoundException
	{
		//this.file = file;
		// ....
	}

/*
	public FileInputStream(FileDescriptor fd)
	{
		this.fd = fd;
		// ....
	}
*/

	protected synchronized void finalize() throws IOException {
		close();
	}

	public synchronized void close() throws IOException {
		//fd = null;
	}

//	public FileDescriptor getFD() throws IOException	{
//		if (fd == null)
//			throw new IOException();
//		return fd;
//	}

	public int available() throws IOException	{
		return 0;
	}

	public int read() throws IOException
	{
		return 0;
	}

	public int read(byte[] b, int off, int len) throws IOException
	{
		return 0;
	}

	public int read(byte[] b) throws IOException
	{
		return 0;
	}

	public long skip(long n) throws IOException
	{
		return 0;
	}

}

