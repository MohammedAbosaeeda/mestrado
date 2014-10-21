/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

final public class JoinPointChecker extends Weavelet {

	private Hashtable class_index = new Hashtable();
	private Hashtable jp_cache = new Hashtable();

	private Weavelet[] NOT_AFFECTED = new Weavelet[0];

	public JoinPointChecker(BuilderOptions opts, ClassStore repository) {
		super(opts,repository);
	}

	final public void reset() {
		class_index = new Hashtable();
		jp_cache = new Hashtable();
	}

	final public void registerWeavelet(Weavelet weavelet) {
		String[] pointcuts = weavelet.joinPoints;
		if (pointcuts==null || pointcuts.length<1) {
			opts.verbose("warning: Weavelet has no pointcuts! "+weavelet.toString());
			return;
		}

		if (jp_cache.size()>0) {
			opts.verbose("warning: register weavelet after usage!");
			jp_cache = new Hashtable();
		}

		for (int i=0;i<pointcuts.length;i++) {
			String pc = pointcuts[i];
			if (pc.indexOf('.')<1) {
				opts.verbose("warning: missing method definition in pointcut: "+pc);
				pc=pc+".*"; 
			}
			Vector wlet = (Vector)class_index.get(pc);
			if (wlet==null) { 
				wlet = new Vector();
				opts.vverbose("##### jp register: "+pc);
				class_index.put(pc,wlet);
			}
			wlet.add(weavelet);
		}

	}

	private Weavelet[] getJoinPoints(String className, String methodName) {

		String key = className+"."+methodName;
		
		Weavelet[] weavelet = (Weavelet[])jp_cache.get(key);
		if (weavelet!=null) return weavelet;

		Vector weavelet_spec = (Vector)class_index.get(key);
		Vector weavelet_all  = null;
		if (!methodName.equals("<CLASS>")) 
			weavelet_all = (Vector)class_index.get(className+".*");

		if (weavelet_spec==null) {
			if (weavelet_all==null) {
				weavelet = NOT_AFFECTED;
				//opts.vverbose("jp miss: "+key);
			} else {
				weavelet = new Weavelet[weavelet_all.size()];
				Enumeration e=weavelet_all.elements();
				for (int i=0;i<weavelet.length;i++) { 
					weavelet[i] = (Weavelet)e.nextElement();
				}
				//opts.vverbose("jp class: "+className+".* "+weavelet.length);
			}
		} else if (weavelet_all==null) {
			weavelet = new Weavelet[weavelet_spec.size()];
			Enumeration e=weavelet_spec.elements();
			for (int i=0;i<weavelet.length;i++) { 
				weavelet[i] = (Weavelet)e.nextElement();
			}
			//opts.vverbose("jp method: "+key+" "+weavelet.length);
		} else {
			Hashtable set = new Hashtable();
			for (int i=0;i<weavelet_all.size();i++) 
				set.put(weavelet_all.elementAt(i), weavelet_all.elementAt(i));  
			for (int i=0;i<weavelet_spec.size();i++) 
				set.put(weavelet_spec.elementAt(i), weavelet_spec.elementAt(i));  
			Enumeration e=set.elements();
			weavelet = new Weavelet[set.size()];
			for (int i=0;i<weavelet.length;i++) { 
				weavelet[i] = (Weavelet)e.nextElement();
			}
		}

		if (weavelet!=NOT_AFFECTED) opts.vverbose("jp hits: "+key+" "+weavelet.length);

		jp_cache.put(key, weavelet);
		return weavelet;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(clazz.getClassName(), method.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.ignoreMethodBody(clazz,method)) flag=true;
		}

		return flag;
	}

	public void require(int domainID, String className, String methodName)
	{
		Weavelet[] weavelet = getJoinPoints(className, methodName);
		if (weavelet==NOT_AFFECTED) return;

		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			a.require(domainID, className, methodName);
		}
	}


	public int checkAttribut(IMMethod self, int attr) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(self.getClassName(), self.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return NOTDEF;

		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			opts.vvverbose("checkAttribute: "+a.toString()+" "+attr);
			if (a.checkAttribut(self, attr)==TRUE) return TRUE;
			opts.vvverbose("checkAttribute: FALSE");
		}

		return FALSE;
	}

	public boolean addFields(IMClass clazz, Coder coder, StringBuffer raw_fields) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(clazz.getClassName(), "<CLASS>");
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.addFields(clazz,coder,raw_fields)) flag=true;
		}

		return flag;
	}
	
	public int checkFieldAttribut(String classname, String fieldname, int attr)
	{
		Weavelet[] weavelet = getJoinPoints(classname, "<CLASS>");
		if (weavelet==NOT_AFFECTED) return NOTDEF;

		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			opts.vvverbose("checkAttribute: "+a.toString()+" "+attr);
			if (a.checkFieldAttribut(classname,fieldname, attr)==TRUE) return TRUE;
			opts.vvverbose("checkAttribute: FALSE");
		}

		return FALSE;
	}

	public IMNode affectIMInvoke(IMInvoke self, IMMethod method, IMMethod callee,
			IMNode obj, IMNode args[]) throws CompileException {

		Weavelet[] weavelet = getJoinPoints(callee.getClassName(), callee.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return self;

		IMNode nself = self;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			nself = a.affectIMInvoke((IMInvoke)self, method, callee, obj, args);
			if (nself==null) {
				nself = self;
				continue;
			}
			if (!(nself instanceof IMInvoke)) break; 
		}

		return nself;
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(clazz.getClassName(), method.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.affectMethod(clazz,method,coder)) flag=true;
		}

		return flag;
	}


	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(callee.getClassName(), callee.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.affectInvokeStatic(node,caller,callee,args,coder)) flag=true;
		}

		return flag;
	}

	public boolean affectInvokeSpecial(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode obj, IMNode args[], Coder coder) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(callee.getClassName(), callee.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.affectInvokeSpecial(node,caller,callee,obj,args,coder)) flag=true;
		}

		return flag;
	}

	public boolean affectInvokeVirtual(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode obj, IMNode args[], Coder coder) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(callee.getClassName(), callee.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.affectInvokeVirtual(node,caller,callee,obj,args,coder)) flag=true;
		}

		return flag;
	}

	public boolean affectInvokeInterface(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode obj, IMNode args[], Coder coder) throws CompileException
	{
		Weavelet[] weavelet = getJoinPoints(callee.getClassName(), callee.getMethodNameAndType());
		if (weavelet==NOT_AFFECTED) return false;

		boolean flag = false;
		for (int i=0;i<weavelet.length;i++) {
			Weavelet a = weavelet[i];
			if (a.affectInvokeInterface(node,caller,callee,obj,args,coder)) flag=true;
		}

		return flag;
	}
}
