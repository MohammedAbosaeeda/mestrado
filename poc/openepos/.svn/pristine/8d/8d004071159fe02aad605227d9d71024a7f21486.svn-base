/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler; 

import keso.compiler.imcode.*;

import keso.util.Debug; 
import keso.util.IntegerHashtable; 
import java.util.Enumeration;

import java.util.Vector;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import keso.util.Bitmap;

/**
 * This Bucket is a sorted Set. Every value appears only
 * once, and it is sorted in a FIFO.
 */
final class Bucket {

	Bitmap mark;
	int[] values;
	int size;
	int index;

	Bucket(int cap) {
		values = new int[cap];
		clear();
	}

	void add(int value) {
		if (mark.test(value)) return;
		values[size]=value;
		mark.mark(value);
		size++;
	}

	int first() {
		index = 0;
		return next();
	}

	int next() {
		if (index>=values.length) return 0;
		return values[index++];
	}

	int[] getArray() {
		if (size<values.length) {
			int[] a=new int[size];
			for (int i=0;i<size;i++) a[i]=values[i];
			values = a;
		}
		return values;
	}

	void clear() {
		mark = new Bitmap(values.length);
		size=0;
	}
}

final class DominatorEnumeration implements Enumeration
{
	private IMBasicBlock[] vertex;
	private int index;

	public DominatorEnumeration(IMBasicBlock[] src)
	{
		vertex = src;
		index = 1;
	}

	public boolean hasMoreElements()
	{
		return index<vertex.length;
	}

	public Object nextElement() throws NoSuchElementException
	{
		if (index>=vertex.length)
			throw new NoSuchElementException();
		Object o = vertex[index++];
		while (index<vertex.length && vertex[index]==null) index++;
		return o;
	}
}

final public class DominatorTree {

	private int     uniq_number;
	private int	number_of_bb;
	private IMMethod method;
	private IMBasicBlock   root;

	private IMBasicBlock[] vertex;
	
	private int[] parents;
	private	int[] idom; /* dominators */
	private Bucket[] df; /* dominance frontiers */
	private	int[] semi; /* semidominators */
 	private	int[] ancestor;
 	private	int[] best;

	public DominatorTree(IMMethod method, IMBasicBlock root) {
		this.method = method;
		this.root = root;
		uniq_number = 1;
		number_of_bb = 1;
	}

	/**
	 * sort all basic blocks by a deap first search.
	 */
	private void deapFirstSearch(IMBasicBlock parent, IMBasicBlock current) throws CompileException {
		if (current.getDFNum()==0) {
			current.setDFNum(uniq_number);
			vertex[uniq_number]=current;
			if (parent!=null) parents[uniq_number]=parent.getDFNum();
			uniq_number++;
			number_of_bb++;
	
			IMBasicBlockList successor = current.getSucc();
			for (int i=0;i<successor.length();i++) {
				deapFirstSearch(current,successor.at(i));
			}
		}
	}

	/*
	 * see Lengauer and Tarajan
	 */
	public void computeDominators() throws CompileException {
		int max_number_of_bb = method.getNumberOfBasicBlocks()+1;
		if (max_number_of_bb==1) return;

		vertex  = new IMBasicBlock[max_number_of_bb];
		parents = new int[max_number_of_bb];

		deapFirstSearch(null,root);

		idom = new int[number_of_bb];
		semi = new int[number_of_bb];
		ancestor = new int[number_of_bb];
		best = new int[number_of_bb];

		Bucket[] bucket = new Bucket[number_of_bb];
		for (int i=0;i<number_of_bb;i++) bucket[i] = new Bucket(number_of_bb);
		
		int[] samedom = new int[number_of_bb];

		for (int i = number_of_bb - 1; i>1 ; i--) {
			if (vertex[i]==null) {
				System.err.println("warn: vertex["+i+"]=null number_of_bb="+number_of_bb);
				System.err.println(method.dumpLabelIndex());
				continue;
			}
			int current = vertex[i].getDFNum();
			int parent = parents[current];
			int semidom = parent;

			/*
			 * Calculate the semidominator of _current_, based
			 * on the semidominator theorem.
			 */

			IMBasicBlockList pred = vertex[current].getPred();
			for (int j=0;j<pred.length();j++) {
				int s;
				int v = pred.at(j).getDFNum();
				if (v <= current) {
					s = v;
				} else {
					s = semi[ancestorWithLowestSemi(v)];
				}
				if (s<semidom) {
					semidom = s;
				}
			}

			semi[current] = semidom;

			/*
			 * Calculation of _current_'s dominator is deferred
			 * until the path from _semidom_ to _current_ has been
			 * linked into the forest.
			 */

			bucket[semidom].add(current);
			link(parent, current);

			/*
			 * Now that the path form _parent_ to _v_ has been linked into
			 * the spanning forest, these lines calculate the dominator of _v_,
			 * based on the first clause the dominator theorem, or else
			 * defer the calculation untile _y_'s dominator is known.
			 */

			for (int t=bucket[parent].first();t!=0;t=bucket[parent].next()) {
				int y = ancestorWithLowestSemi(t);
				if (semi[y] == semi[t]) {
					idom[t] = parent;
				} else {
					samedom[t] = y;
				}
			}
			bucket[parent].clear();
		}
		bucket=null;

		/*
		 * Now all the deferred dominator calculation, based on
		 * the second clause of the dominator theorem, are performed.
		 */

		for (int i=1;i<number_of_bb;i++) {
			if (samedom[i]!=0) idom[i]=idom[samedom[i]]; 
		}

		parents=null;
		semi=null;
		best=null;
		ancestor=null;
	}

	/**
	 * returns the dominance frontiers of basic block (bb)
	 *
	 * The dominance frontier of a node A is the set of nodes that A does not
	 * strictly dominate, but does dominate some immediate predecessor of. From A's
	 * point of view, these are the nodes at which other control paths that don't go
	 * through A make their earliest appearance. 
	 * (Wikipedia)
	 */

	public int[] getDominaceFrontierIDs(IMBasicBlock bb) {
		return getDominaceFrontierIDs(bb.getDFNum());
	}

	public int[] getDominaceFrontierIDs(int id) {
		if (id==0) return new int[0];
		if (df[id]==null) { 
			System.err.println("=========================================");
			System.err.println("method = "+method.getMethodNameAndType());
			System.err.println("basic block = "+id);
			System.err.println("=========================================");
			try { computeDominanceFrontier2(); } catch (Exception ex) {}
		}
		return df[id].getArray();
	}

	/**
	 * computes the dominace frontiers using a simple algorithmus
	 * published at wikipedia.
	 */
	public void computeDominanceFrontier2() throws CompileException {
		if (idom==null) computeDominators(); 

		df = new Bucket[number_of_bb];
		for (int b=1;b<number_of_bb;b++)
			df[b] = new Bucket(number_of_bb);

		// for each node b
		for (int b=1;b<vertex.length;b++) {
			if (vertex[b]==null) continue;
			IMBasicBlockList pred = vertex[b].getPred();
			// if the number of predecessors of b  2
			if (pred.length()>1) {
				//for each p in predecessors of b
				for (int i=0;i<pred.length();i++) {
					// runner := p
					int runner = pred.at(i).getDFNum();
					// while runner != idom(b)
					while (runner!=idom[b]) {
						//if (runner == b) throw new CompileException(vertex[b] + " predictor " + i + " = " + pred.at(i));
						df[runner].add(b);
						runner = idom[runner];
						if (runner==0) break;
					}
				}
			}
		}
	}

	/**
	 * computes the dominace frontiers
	 */
	public void computeDominanceFrontier() throws CompileException {
		if (idom==null) computeDominators(); 
		df = new Bucket[number_of_bb];
		computeDF(1);
		childs=null;
	}

	private void computeDF(int n) throws CompileException {
		Bucket S = new Bucket(number_of_bb);

		IMBasicBlockList succ = vertex[n].getSucc();
		for (int i=0;i<succ.length();i++) {
			int y = succ.at(i).getDFNum();
			if (idom[y]!=n) S.add(y);
		}

		Bucket C = childsOf(n);
		if (C!=null) {
			for (int c=C.first();c!=0;c=C.next()) {
				computeDF(c);
				Bucket DF = df[c];
				for (int w=DF.first();w!=0;w=DF.next()) {
					if (!dom(n,w)) S.add(w);	
				}
			}
		}

		df[n] = S;
	}

	private Bucket[] childs = null;

	private void computeChildsForDF() {
		childs=new Bucket[number_of_bb];
		for (int i=1;i<number_of_bb;i++) {
			int dom = idom[i];
			if (childs[dom]==null) childs[dom] = new Bucket(number_of_bb);
			childs[dom].add(i);
		}
	}

	private Bucket childsOf(int n) {
		if (childs==null) computeChildsForDF(); 
		return childs[n];
	}

	private void link(int parent, int current) {
		ancestor[current]=parent;
		best[current]=current;
	}

	private int ancestorWithLowestSemi(int v) {
		int a = ancestor[v];
		if (ancestor[a]!=0) {
			int b = ancestorWithLowestSemi(a);
			ancestor[v]=ancestor[a];
			if (semi[b]<semi[best[v]])
				best[v]=b;
		}
		return best[v];
	}

	/**
	 * returns true if "a" is a dominator of "b".
	 */
	private boolean dom(int a, int b) {
		if (a==1) return true;
		if (b<=1) return false;
		if (idom[b]==a || dom(a,idom[b])) return true;
		return false;
	}

	/**
	 * returns true if "a" is a dominator of "b".
	 */
	public boolean dom(IMBasicBlock a, IMBasicBlock b) {
		if (a==null) throw new Error("a is null");
		if (b==null) throw new Error("b is null");
		return dom(a.getDFNum(),b.getDFNum());
	}

	/**
	 * return the immediate dominator of basic block bb
	 */
	public IMBasicBlock getDominatorOf(IMBasicBlock bb) {
		return vertex[idom[bb.getDFNum()]];
	}

	public IMBasicBlock getIMBasicBlock(int id) {
		return vertex[id];
	}

	/**
	 * return a all basic blocks sorted by a deep first search
	 * of the dominator tree.
	 */
	public Enumeration getSortedBasicBlocks() {
		return new DominatorEnumeration(vertex);
	}

}
