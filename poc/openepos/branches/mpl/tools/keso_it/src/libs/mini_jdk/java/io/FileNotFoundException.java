/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public class FileNotFoundException extends IOException
{ 
  /**
   * Create an exception.
   */
  public FileNotFoundException() { }

  /**
   * Create an exception with a descriptive error message.
   *
   * @param message the descriptive error message
   */
  public FileNotFoundException(String message) { super(message); }
} 
