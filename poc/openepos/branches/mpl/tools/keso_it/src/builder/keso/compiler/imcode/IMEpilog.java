/**(c)

  Copyright (C) 2005-2006 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.imcode; 

import keso.classfile.*;
import keso.classfile.constantpool.*; 
import keso.classfile.datatypes.*; 

import keso.compiler.*;
import keso.compiler.backend.*;
import keso.compiler.kni.*;

import keso.util.Debug; 
import keso.util.DecoratedNames; 
import keso.util.IntegerHashtable; 

import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;


/* AUTO GENERATED FILE DON'T EDIT */

public class IMEpilog extends IMNode {


	/* copy constuctor */
	protected IMEpilog() { }

    	private IMSlot var;

	public IMEpilog(IMMethod method, int bcpos) {
		super(method, -1, BCBasicDatatype.VOID, bcpos);
		var = method.getReturnValue();
	} 

	public IMSlot getIMSlot() {
		return var;
	}

	public void setIMSlot(IMSlot slot) {
		var = slot;	
	}
    
	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMEpilog nnode = new IMEpilog();
		nnode.copyNodeValues(visitor, this);

		if (visitor==null) {
			nnode.var=var;
			return nnode;
		}
		if (var!=null) nnode.var=visitor.adjustSlot(var);

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;
		visitor.visit(this);

	}

	final public IMNode ssa_optimize() throws CompileException {
		IMBasicBlock basicBlock = method.getCurrentBasicBlock();
		if (basicBlock==null) throw new CompileException("no bb in "+method.getAlias());

		if (var==null) return this;
		IMStoreLocalVariable store = (IMStoreLocalVariable)var.getDefinedStatement();
		if (store==null) {
			return this;
		}

		// bad idea!
		// IMNode alias = store.getOperant().ssa_optimize();
		IMNode alias = store.getOperant();

		// copy propagation
		if (alias instanceof IMReadLocalVariable) {
			IMSlot nvar=((IMReadLocalVariable)alias).getIMSlot();
			opts.vverbose("++ propagate "+nvar+"->"+var);
			var.delete_use(this);
			var=nvar;
			nvar.use(basicBlock, this);

			return this;
		}


		return this;
	}


	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		return !isArgument;
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (!isArgument) return this;
		return null;
	}

	public void analyseCall() throws CompileException {
		ClassStore rep = method.getClassStore();
		if (opts.hasOption("exceptions")) {
			IMExceptionHandler[] catch_list = method.getExceptionHandler();
			if (catch_list==null) return;
			for (int i=0;i<catch_list.length;i++) {
				rep.needInstanceOf(catch_list[i].getIMClass());
			}
		}
	}

	public String toReadableString() {
		if (method.getBasicReturnType()==BCBasicDatatype.VOID) {
			return "return";	    
		} else {
			if (var==null) return "return ???";
			return "return "+var.toString();	    
		}
	}

	public String dumpBC() {return "Return";}

	public int costs() throws CompileException {
		return 0;
	}

	public void translate(Coder coder) throws CompileException {
		if (opts.hasOption("exceptions")) {
			IMExceptionHandler[] catch_list = method.getExceptionHandler();
			if (catch_list!=null) {
				int[] ex_pos = method.getLocalExceptionCalls();
				coder.add_befor("\tgoto ");
				coder.add_befor(method.getAlias());
				coder.add_befor("_eh_end;\n");
				coder.add_befor("\t{\n");
				coder.add_befor("\t\tshort range;\n");
				if (ex_pos!=null) {
					for (int i=0;i<ex_pos.length;i++) {
						coder.add_befor(method.getLocalExceptionLabel(ex_pos[i]));
						coder.add_befor(":\n\t\trange=");
						coder.add_befor(ex_pos[i]);
						coder.add_befor(";\n\t\tgoto ");
						coder.add_befor(method.getAlias());
						coder.add_befor("_eh_start;\n");
					}
				}
				coder.add_befor(method.getAlias());
				coder.add_befor("_eh_start:\n");
				for (int i=0;i<catch_list.length;i++) {
					IMClass etype = catch_list[i].getIMClass();
					IMBasicBlock handler = catch_list[i].getHandler();
					coder.add_befor("\t\tif ((");
					coder.add_befor("range==");
					coder.add_befor(catch_list[i].getRangeID());
					coder.add_befor(")&&keso_instanceof(");
					coder.add_befor(etype.getClassID());
					coder.add_befor(",KESO_PENDING_EXCEPTION))\n\t\t\tgoto ");
					coder.add_befor(handler.toLabel());
					coder.add_befor(";\n");
				}
				coder.add_befor("\t}\n");
				coder.add_befor(method.getAlias());
				coder.add_befor("_eh_end:\n");
			}
		}
		if (method.getBasicReturnType()==BCBasicDatatype.VOID) {
			coder.add("return");
		} else if (method.getBasicReturnType()==BCBasicDatatype.REFERENCE) {
			coder.add("return (object_t*)");
			coder.add(var.toString());
		} else {
			coder.add("return ");
			coder.add(var.toString());
		}
	}

}
