/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package java.io;

public interface DataOutput {
    void write(int b) throws IOException;
    void write(byte b[]) throws IOException;
    void write(byte b[], int off, int len) throws IOException;
    void writeBoolean(boolean v) throws IOException;
    void writeByte(int v) throws IOException;
    void writeShort(int v) throws IOException;
    void writeChar(int v) throws IOException;
    void writeInt(int v) throws IOException;
    void writeLong(long v) throws IOException;
    void writeFloat(float v) throws IOException;
    void writeDouble(double v) throws IOException;
    void writeBytes(String s) throws IOException;
    void writeChars(String s) throws IOException;
    void writeUTF(String str) throws IOException;
}
