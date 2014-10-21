/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public abstract class OutputStream
{ 
    public OutputStream() { }

    public void close() throws IOException { }
    
    public void flush() throws IOException { }
    
    public abstract void write(int b) throws IOException;
    
    public synchronized void write(byte[] b, int off, int len) throws IOException
    {
	for (int i = 0; i < len; i++)
	    write(b[off + i]);
    }
    
    public void write(byte[] b) throws IOException
    {
	write(b, 0, b.length);
    }
}


