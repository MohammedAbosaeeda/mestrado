/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.classfile; 

public class LineAttributeData {
    public int startBytecodepos;
    public int lineNumber;
    public LineAttributeData(int startBytecodepos, int lineNumber) {
	this.startBytecodepos = startBytecodepos;
	this.lineNumber = lineNumber;
    }
    public LineAttributeData copy() {
	return new LineAttributeData(startBytecodepos, lineNumber);
    }
}
