/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.imcode.*;
import keso.compiler.*;
import keso.compiler.config.*;
import keso.compiler.backend.Coder;

public class Config extends Weavelet {

	public Config(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] { "keso/core/Config.*" };
	}


	public void require(int domainID, String className, String methodName) {
		if (methodName.equals("getShortArray(Ljava/lang/String;)[S")) {
			repository.requireClass("[S");
		}
		if (methodName.equals("getIntArray(Ljava/lang/String;)[I")) {
			repository.requireClass("[I");
		}
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}

	public IMNode affectIMInvoke(IMInvoke self, IMMethod method, IMMethod callee,
			IMNode obj, IMNode args[]) throws CompileException {

		if (callee.termed("getShortArray(Ljava/lang/String;)[S") || 
				callee.termed("getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;") ||
				callee.termed("getIntArray(Ljava/lang/String;)[I")) {
			/* ignor this */
			return self;
		}

		String identifier = assert_string(args[0],"Config identifier");
		if (callee.termed("getInt(Ljava/lang/String;I)I") || 
				callee.termed("getShort(Ljava/lang/String;S)S")) {
			return getPropertyInteger(method, identifier, args[1]);
		}

		opts.todo("improvement - create IM-object in Config-Weavelet for "+callee.getMethodNameAndType());

		return self;
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		String identifier = assert_string(args[0],"Config identifier");

		if (callee.termed("getShortArray(Ljava/lang/String;)[S")) {
			Set current = opts.getSysDef();
			Attribut array = current.getAttribute(identifier);
			if (array==null) 
				throw new CompileException("Config: "+identifier+" not found!");

			int[] values = ((ArrayAttr)array).values();

			IMClass cclazz = caller.getIMClass("[S");
			coder.add_class(cclazz.getAlias());

			String static_array = caller.getAlias()+"_arr"+node.getBCPosition();
			String static_struct  = caller.getAlias()+"_s"+node.getBCPosition();

			coder.local_add("struct ");
			coder.local_add(static_struct);
			coder.local_add(" {\nARRAY_HEADER\njshort data[");
			coder.local_add(values.length);
			coder.local_add("];\n};\n");

			//coder.local_add(opts.declareConst());
			coder.local_add("static struct ");
			coder.local_add(static_struct);
			coder.local_add(" ");
			coder.local_add(static_array);
			coder.local_add(" HEAP_SECTION = {\n\t.class_id=SHORT_ARRAY_ID,\n");
			if (opts.getGlobalHeap().needGCInfo())
				coder.local_add("\t.gcinfo=1,\n");
		        coder.local_add("\t.size = ");
			coder.local_add(values.length);
			coder.local_add(",\n.data = {");
			for (int i=0;i<values.length;i++) { 
				if (i>0) coder.local_add(",");
				coder.local_add((values[i] & 0xffff));
			}
			coder.local_add("}\n};\n");

			coder.add(coder.add_accessStaticObject(cclazz, static_array));
			return true;
		}

		if (callee.termed("getIntArray(Ljava/lang/String;)[I")) {
			Set current = opts.getSysDef();
			Attribut array = current.getAttribute(identifier);

			if (array==null) 
				throw new CompileException("Config: "+identifier+" not found!");

			int[] values = ((ArrayAttr)array).values();

			IMClass cclazz = caller.getIMClass("[I");
			coder.add_class(cclazz.getAlias());

			String static_array = caller.getAlias()+"_arr"+node.getBCPosition();
			String static_struct  = caller.getAlias()+"_s"+node.getBCPosition();

			coder.local_add("struct ");
			coder.local_add(static_struct);
			coder.local_add(" {\nARRAY_HEADER\njint data[");
			coder.local_add(values.length);
			coder.local_add("];\n};\n");

			//coder.local_add(opts.declareConst());
			coder.local_add("static struct ");
			coder.local_add(static_struct);
			coder.local_add(" ");
			coder.local_add(static_array);
			coder.local_add(" HEAP_SECTION = {\n.class_id = INT_ARRAY_ID,\n");
			if (opts.getGlobalHeap().needGCInfo())
				coder.local_add(".gcinfo=1,");
			coder.local_add(".size = ");
			coder.local_add(values.length);
			coder.local_add(",\n.data = {");
			for (int i=0;i<values.length;i++) { 
				if (i>0) coder.local_add(",");
				coder.local_add(values[i]);
			}
			coder.local_add("}\n};\n");

			coder.add(coder.add_accessStaticObject(cclazz, static_array));
			return true;
		}

		String property = getPropertyString(identifier, args[1]);

		if (callee.termed("getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;")) {
			IMClass clazz = caller.getIMClass("java/lang/String");
			coder.add_class(clazz.getAlias());

			IMClass cclazz = caller.getIMClass("[C");
			coder.add_class(cclazz.getAlias());

			String static_string  = caller.getAlias()+"_str"+node.getBCPosition();
			String static_chararr = caller.getAlias()+"_arr"+node.getBCPosition();
			String static_struct  = caller.getAlias()+"_s"+node.getBCPosition();

			coder.local_add("struct ");
			coder.local_add(static_struct);
			coder.local_add(" {\nARRAY_HEADER\njchar data[");
			coder.local_add(property.length()+1);
			coder.local_add("];\n};\n");

			//coder.local_add(opts.declareConst());
			coder.local_add("static struct ");
			coder.local_add(static_struct);
			coder.local_add(" ");
			coder.local_add(static_chararr);
			coder.local_add(" HEAP_SECTION = {\n.class_id = CHAR_ARRAY_ID,\n");
			if (opts.getGlobalHeap().needGCInfo())
				coder.local_add(".gcinfo=1,");
			coder.local_add(".size = ");
			coder.local_add(property.length());
			coder.local_add(",\n.data = {");
			for (int i=0;i<property.length();i++) { 
				coder.local_add((int)property.charAt(i));
				coder.local_add(",");
			}
			coder.local_add("0}\n};\n");

	 		coder.add_allocConstObject(clazz, static_string);
	 		coder.add_init_field(clazz,"value","(object_t*)&"+static_chararr);
	 		coder.add_init_end();
		} else {
			coder.add(property);
		}
		
		// return value true tells the builder that this invoke has been handled
		return true;
	}

	private String getPropertyString(String id, IMNode dft) throws CompileException {

		IMConstant dft_node = assert_const(dft, "Default value");

		Attribut attr = getProperty(id); 
		if (attr==null) {
			opts.warn("ID String " +id + " not found");
			return dft_node.toReadableString();
		}

		String property = attr.valueString();
		if (property == null) {
			opts.warn("ID String " +id + " not found");
			return dft_node.toReadableString();
		}

		return property;
	}

	private IMConstant getPropertyInteger(IMMethod method, String id, IMNode dft) throws CompileException {

		IMIConstant dft_node = assert_iconst(dft, "Default value");

		Attribut attr = getProperty(id); 
		if (attr==null) {
			opts.warn("ID String " +id + " not found");
			return dft_node;
		}

		return method.createIMIConstant(attr.valueInt(), dft.getBCPosition());
	}

	private IMConstant getPropertyDouble(IMMethod method, String id, IMNode dft) throws CompileException {

		Attribut attr = getProperty(id); 
		if (attr==null) {
			opts.warn("ID String " +id + " not found");
			return assert_dconst(dft, "Default value");
		}

		return method.createIMDConstant(attr.valueDouble(), dft.getBCPosition());
	}

	private Attribut getProperty(String id) throws CompileException {
		String[] tokens = id.split("\\.");
		Set current = opts.getSysDef();

		for (int i=0; i< tokens.length-1; i++) {
			char type = tokens[i].charAt(0);
			String setName = tokens[i].substring(1);
			switch (type) {
				case 'a':
					current = ((AlarmContainer) current).getAlarm(setName);
					break;
				case 'c':
					// Counters are only defined in the SystemDefinition
					current = ((SystemDefinition) current).getCounter(setName);
					break;
				case 'd':
					// Domains are only defined in the SystemDefinition
					current = ((SystemDefinition) current).getDomain(setName);
					break;
				case 'e':
					// Events are only defined in the SystemDefinition
					current = ((SystemDefinition) current).getEvent(setName);
					break;
				case 'h':
					// Heaps are only defined in DomainDefinitions
					// (there is only one heap per domain)
					current = ((DomainDefinition) current).getHeap();
					break;
				case 'i':
					current = ((ISRContainer) current).getISR(setName);
					break;
				case 'm':
					// Appmodes are Part of the SystemDefinition
					current = ((SystemDefinition) current).getAppmode(setName);
					break;
				case 'o':
					current = ((SystemDefinition) current).getOSDef();
					break;
				case 'r':
					current = ((ResourceContainer) current).getResource(setName);
					break;
				case 't':
					current = ((DomainDefinition) current).getTask(setName);
					break;
				default:
					opts.critical("Unknown type " + type + " in ID String " +id + " at token " + i);
			}
			if (current == null) {
				opts.warn(setName+"("+type+")" + " in ID String " +id + " at token " + i + " not found");
				return null;
			}
		}

		return current.getAttribute(tokens[tokens.length-1]);
	}
}
