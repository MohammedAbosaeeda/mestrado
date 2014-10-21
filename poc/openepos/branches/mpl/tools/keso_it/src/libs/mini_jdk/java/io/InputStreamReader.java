/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class InputStreamReader extends Reader
{
	private InputStream in;

	public InputStreamReader(InputStream in)
	{
		this.in =in;
	}

	public InputStreamReader(InputStream in, String encoding_name)
		// throws UnsupportedEncodingException
	{
		this.in = in;
	}

	public String getEncoding()
	{
		//return in.getSchemeName();
		return null;
	}

	public void close() throws IOException
	{
		in.close();
	}

	public boolean ready() throws IOException
	{
		//return in.ready();
		return true;
	}

	public int read() throws IOException
	{
		return in.read();
	}

	public int read(char[] buf, int offset, int len) throws IOException
	{
		for (int i = 0; i < len; i++)
		{
			int c = read();
			if (c < 0)
				return i == 0 ? -1 : i;
			buf[offset + i] = (char) c;
		}
		return len;
	}

} 
