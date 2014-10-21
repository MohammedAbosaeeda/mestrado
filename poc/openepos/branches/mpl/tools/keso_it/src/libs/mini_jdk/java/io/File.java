/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class File
{
	private String path;

	public File(String filename) {
		this.path=filename;
	}

	public String getPath() {
		return path;
	}

	public void mkdirs() {
	}

	public boolean exists() {
		return false;
	}

	public boolean isDirectory() {
		return false;
	} 

	public String[] list() {
		return null;
	}
}
