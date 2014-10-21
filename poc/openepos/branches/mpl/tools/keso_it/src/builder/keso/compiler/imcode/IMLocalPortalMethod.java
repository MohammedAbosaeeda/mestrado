/**(c)

  Copyright (C) 2005 Christian Wawersich 

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
import keso.compiler.kni.*;
import keso.compiler.backend.Coder;
import keso.compiler.config.*;

import keso.util.*; 

import java.util.Enumeration;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;

import java.util.Vector;

/**
 * This class implements the portal call to a service. 
 */
public class IMLocalPortalMethod extends IMSystemMethod {

	private boolean stack_allocation = false;
	private ImportDefinition importDefinition;
	private ClassStore repository;

	/**
	  IMPortalMethod representiert eine Portal-Methode und all ihre Daten in der
	  Zwischendarstellung des Compilers. Die Klasse der Methode und die Daten aus der
	  Kalssendatei werden als Parameter bei der Objekt-Erzeugung uebergeben. Der
	  Bytecode wird nicht gelesen. Die Methode dient als Proxy fuer die eigentliche
	  Implementierung. 
	  */
	public IMLocalPortalMethod(IMPortalClass clazz, MethodData source, ClassStore repository, ImportDefinition impDef) throws CompileException {
		super(clazz);
		this.source = source;
		hasNoCode = false;
		this.frame  = new IMMethodFrame(this, source.getInitialVariableTypes(), 0);
		this.repository = repository;
		importDefinition = impDef;
	}

	public void analyseCallGraph(IMCallGraphVisitor visitor) throws CompileException {
		IMPortalClass this_class = ((IMPortalClass)clazz);
		IMClass srv_class = importDefinition.getService().getServiceClass(repository);
		IMMethod callee = srv_class.getMethod(getMethodNameAndType());
		callee.calledFrom(this,null);
	}

	public void translate(Coder coder) throws CompileException {

		ServiceDefinition srv = importDefinition.getService();

		/*
		 * this_class is the class of the portal proxy.
		 * srv_class  is the class of the service
		 */
		IMPortalClass this_class = ((IMPortalClass)clazz);
		IMClass srv_class = srv.getServiceClass(repository);

		IMClass task_clazz = getIMClass("keso/core/Task");

		boolean has_return_value = (getBasicReturnType()!=BCBasicDatatype.VOID);

		IMMethod callee = srv_class.getMethod(getMethodNameAndType());
		// FIXME> AVR has no gc!
		boolean has_llref = (callee.canBlock() && opts.hasLinkedListOfLocalReferences() && !opts.isAVR());
		boolean has_llref_avr = (callee.canBlock() && opts.hasLinkedListOfLocalReferences() && opts.isAVR());

		try {
			coder.addInvokeMakro(this);

			coder.beginMethod(this);

			coder.add_class(clazz.getAlias());
			coder.add_class(srv_class.getAlias());
			coder.add_class(task_clazz.getAlias());

			frame.translate(coder);

			coder.add_portal_enter(task_clazz, srv.getDomain().domainid);

			coder.addln("/* call service object */");
			coder.add("obj0 = ");
			coder.add(IMPortalService.getGetMacro(srv.getServiceName()));
			coder.addln(";");

			String[] args = frame.getArgNames();

			int refs = 0;
			int[] types = frame.getArgTypes();

			if (has_llref) {
				refs++;
				for (int i=0;i<types.length;i++) 
					if (types[i]==BCBasicDatatype.REFERENCE) refs++; 

				if (refs>0) {
					coder.add("object_t* obj[");
					coder.add(refs);
					coder.addln("];");
				}
			}

			if (has_return_value) {
				coder.add(getReturnType());
				coder.addln(" ret_value;");
			}

			if (has_llref) {
				/*
				 * we do stack switch if target can block
				 */
				coder.addln("*pref=KESO_EOLL;");
				if (refs==0) {
					coder.addln("pref=(object_t**)&(stack.llrefs));");
				} else {
					coder.add("pref=&obj[");
					coder.add(refs-1);
					coder.addln("];");
					for (int i=0;i<types.length;i++) if (types[i]==BCBasicDatatype.REFERENCE) {
						coder.add("obj[");
						coder.add(i-1);
						coder.add("] = ");
						if (args[i].equals("obj0")) {
							coder.add(args[i]);
							coder.addln(";");
						} else {
							coder.add("keso_dup_object(");
							coder.add(args[i]);
							coder.addln(");");
						}
					} 
					coder.addln("stack.llrefs=(object_t**)obj;");
				}
			}

			if (has_return_value) coder.add("ret_value = ");
			coder.add(callee.getIdentifier());
			coder.add('(');
			for (int i=0;i<args.length;i++) {
				if (i>0) coder.add(',');
				if (types[i]==BCBasicDatatype.REFERENCE) {
					if (has_llref) {
						coder.add("obj[");
						coder.add(i-1);
						coder.add("]");
					} else {
						if (args[i].equals("obj0")) {
							coder.add(args[i]);
						} else {
							coder.add("keso_dup_object(");
							coder.add(args[i]);
							coder.add(")");
						}
					}
				} else {
					coder.add(args[i]);
				}
			}
			coder.addln(");\n");

			coder.add_portal_leave(task_clazz);

			if (has_return_value) {
				if (getBasicReturnType()==BCBasicDatatype.REFERENCE) {
					coder.addln("return keso_dup_object(ret_value);");
				} else {
					coder.addln("return ret_value;");
				}
			}

			coder.endMethod();
		} catch (CompileException ex) {
			System.err.println("CompileException in "+getClassName()+"."+getMethodNameAndType());
			throw ex;
		}
	}
}
