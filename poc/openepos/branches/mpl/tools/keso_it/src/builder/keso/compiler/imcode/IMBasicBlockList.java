/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.compiler.CompileException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

class IMBasicBlockEnumeration implements Enumeration
{
	private IMBasicBlock[] source;
	private int index;
	private int elements;
	private int count;

	public IMBasicBlockEnumeration(IMBasicBlock[] src, int elements)
	{
		source = src;
		index = 0;
		this.elements=elements;
	}

	public boolean hasMoreElements()
	{
		return count<elements;
	}

	public Object nextElement() throws NoSuchElementException
	{
		do {
			index++;
			if (index>=source.length) throw new NoSuchElementException();
		} while (source[index]==null); 

		count++;
		return source[index];
	}
}

final public class IMBasicBlockList {

        private IMBasicBlock[] lst;
        private int count;

        public IMBasicBlockList() { this(2); }

        public IMBasicBlockList(IMBasicBlock block) {
		this(2);
		add(block);
	}

        public IMBasicBlockList(int size) {
                lst=new IMBasicBlock[size];
                count=0;
        }

	public IMBasicBlockList copy(IMCopyVisitor visitor) throws CompileException {
		IMBasicBlockList nbl = new IMBasicBlockList(count);

		for (int i=0;i<count;i++) { 
			nbl.lst[i]=visitor.updateBlock(lst[i]);
		}
		nbl.count=count;

		return nbl;
	}

        public void add(IMBasicBlockList blk) {
		for (int i=0;i<blk.length();i++) add(blk.at(i));
        }

        public void add(IMBasicBlock[] blk) {
		for (int i=0;i<blk.length;i++) add(blk[i]);
        }

        public void add(IMBasicBlock blk) {
                if (count==lst.length) {
                        IMBasicBlock[] nlst = new IMBasicBlock[count+100];
                        System.arraycopy(lst,0,nlst,0,count);
                        lst=nlst;
                }
                lst[count]=blk;
                count++;
        }

	public void remove(IMBasicBlock blk) {
		boolean move = false;
                for (int i=0;i<count;i++) {
			if (lst[i]==blk) move=true;
			if (move) {
				if (i+1<count) {
					lst[i] = lst[i+1];
				} else {
					lst[i] = null;
				}
			}
		}
		if (move) count--;
	}

	public void update(IMBasicBlock old_bb, IMBasicBlock new_bb) {
		for (int i=0;i<count;i++) {
			if (old_bb==lst[i]) lst[i] = new_bb;
		}
	}

	public void set(int i, IMBasicBlock bb) {
		if (i<0 || i>=count) throw new IndexOutOfBoundsException();
		lst[i] = bb;
	}

	public IMBasicBlock at(int i) { return lst[i]; }

	public Enumeration elements() {
		return new IMBasicBlockEnumeration(lst,count);
	}

        public int length() { return count; }

        public String toString() {
                String ret ="";
                for (int i=0;i<count;i++) {
                        //if (lst[i]!=null) ret+=" "+lst[i].toLabel();
                        if (lst[i]!=null) ret+=" "+lst[i].getBCPosition();
                }
                return ret;
        }
}

