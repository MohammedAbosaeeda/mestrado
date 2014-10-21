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

public class IMNew extends IMNode {


	/* copy constuctor */
	protected IMNew() { }

	protected ClassCPEntry cpEntry;

	public IMNew(IMMethod method, int bc, int bcpos, ClassCPEntry clazz) {
		super(method,bc,BCBasicDatatype.REFERENCE,bcpos);
		int domainID=0;
		this.cpEntry=clazz;
		String className = cpEntry.getClassName();
		method.requireClass(domainID, className);
	}


	final public IMNode copy(IMCopyVisitor visitor) throws CompileException {

		IMNew nnode = new IMNew();
		nnode.copyNodeValues(visitor, this);

		nnode.cpEntry=cpEntry;

		if (visitor==null) return nnode;
		return visitor.visit(nnode,this);
	}

	final public void visitNodes(IMVisitor visitor) throws CompileException {
		if (visitor.skipNode(this)) return;

		visitor.visit(this);


	}



	public void analyseCall() throws CompileException {
		method.isPure = false;
		method.isConst = false;
		if (!method.getMethodName().equals("<clinit>") && 
				opts.getGlobalHeap().allocationCanBlock()) method.blockInNew();
	}

	public boolean isChecked(IMBasicBlock bb) throws CompileException { return true; }

	private boolean does_not_escape = false;
	private IMNode escape_path = null;
	private IMNode why_escape = null;
	public void escapeAnalyses(int mode) throws CompileException {
		if (mode!=IMEscapeVisitor.PREPARE) {
			if (doEscape(this, false)) {
				why_escape=whyEscape(this, false);
				if (why_escape instanceof IMEpilog && !doEscape(this, true)) {
					opts.verbose(this.lineInfo()+" escaping object thru "+why_escape.toReadableString()+" only!");
				}
				does_not_escape = false;
			} else {
				does_not_escape = true;
			}
		}
	}

	public boolean doEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			opts.warn(this.lineInfo()+": new "+toReadableString()+" with unknown escape path!");
			return false;
		}
		return escape_path.doEscape(node, isArgument);
	}

	final public IMNode whyEscape(IMNode node, boolean isArgument) throws CompileException {
		if (escape_path==null) {
			return null;
		}
		return escape_path.whyEscape(this, isArgument);
	}

	public void setEscapePath(IMNode node) throws CompileException {
		escape_path = node;
	}

	public IMNode getEscapePath() throws CompileException {
		return escape_path;
	}

	public void translate_global(Coder coder) throws CompileException {
		if (opts.hasOption("no_inline_alloc")) {
			coder.global_header_add("object_t* keso_alloc_stack(object_t* obj, class_id_t cid);\n");
			coder.global_add("object_t* keso_alloc_stack(object_t* obj, class_id_t cid) {\n");
			coder.global_add("	obj_size_t size,roff;\n");
			coder.global_add("	size = CLASS(cid).size;\n");
			coder.global_add("	roff = CLS_ROFF(cid);\n");
		} else {
			coder.global_header_add("object_t* keso_alloc_stack(object_t* obj, class_id_t cid, obj_size_t size, obj_size_t roff);\n");
			coder.global_add("object_t* keso_alloc_stack(object_t* obj, class_id_t cid, obj_size_t size, obj_size_t roff) {\n");
		} 
		coder.global_add("	while (roff-->0) { ((object_t**)obj)[0]=(object_t*)0; obj = (object_t*) (((object_t**)obj)+1); }\n");
		coder.global_add("	obj->class_id = cid;\n");
		if (opts.getGlobalHeap().needGCInfo()) 
			coder.global_add("	obj->gcinfo = 1;\n");
		coder.global_add("	return (object_t*)obj;\n");
		coder.global_add("}\n");
	}
	public IMNode processStack(VirtualOperantenStack stack, IMNode extra_ops) throws CompileException {
		method.isConst=false;
		method.isPure=false;
		stack.store(bcPosition,extra_ops);
		stack.push(this);
		return null;
    	}

	public String toReadableString() {
		return "keso_allocObject("+cpEntry.getClassName()+")";
	}

	public String dumpBC() {return "New";}

	public int costs() throws CompileException {
		return 20;
	}

	public void translate(Coder coder) throws CompileException {
		IMClass clazz = method.getIMClass(cpEntry.getClassName());
		coder.add_class(clazz.getAlias());

		if (method.getMethodName().equals("<clinit>")) {
			coder.add_allocStaticObject(clazz, method.getIdentifier()+"_"+getBCPosition());
		} else {
			if (does_not_escape) {
				if (!opts.getGlobalHeap().needGCInfo() && clazz.getObjRefFieldCount()==0) {
					coder.add_allocStackObjectInlined(clazz);
				} else {
					ClassStore repository = method.getClassStore();
					repository.registerGlobalTranslationCallback("$keso_alloc_stack$", this);
					coder.add_allocStackObject(clazz);
				}
			} else {
				if (why_escape!=null) { 
					coder.add_befor("\t/* escaping: ");
					coder.add_befor(why_escape.toReadableString());
					coder.add_befor(" */\n");
				}
				if (opts.getGlobalHeap().allocationCanBlock()) 
					method.getMethodFrame().codeEOLL(coder);
				coder.add_allocObject(clazz);
			}
		}
	}

}
