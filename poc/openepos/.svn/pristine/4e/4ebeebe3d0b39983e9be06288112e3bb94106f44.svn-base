package keso.compiler.backend;

import keso.classfile.*;
import keso.classfile.constantpool.*;
import keso.classfile.datatypes.*;

import keso.compiler.*;
import keso.compiler.config.*;
import keso.compiler.imcode.*;

import keso.util.Debug;
import keso.util.Bitmap;

import java.io.*;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;


public class CoderJOSEK extends Coder {
	public CoderJOSEK(BuilderOptions opts, MethodTableFactory table,
			ClassStore store) throws CompileException {
		rules = new Vector();
		global = new StringBuffer();
		global_header = new StringBuffer();
		global_includes = new Hashtable();
		global_header_includes = new Hashtable();
		mdispatch = table;
		repository = store;
		this.opts=opts;
		ifmatrix = new InterfaceTypeMatrix(opts, this, store);
	}

	protected void writeRuntimeData(PrintStream out, Vector tasks) throws CompileException {
		IMClass task_class = repository.getClass("keso/core/Task");
		// add an alias to the task class type name for use by c-templates
		global_header_add("#define KESO_TASKCLASSTYPE ");
		global_header_add(task_class.getClassTypeString());
		global_header_add("\n");
		global_header_add("#define KESO_TASKCLASSID ");
		global_header_add(task_class.getClassID());
		global_header_add("\n");

		global_header_add("extern keso_stack_t* keso_stack_index[];\n");
		// KESO_MAX_TASK HACK
		global_add("keso_stack_t* keso_stack_index[KESO_MAX_TASK+1];\n");
		addManagedResource(opts.getSysTasks(), "Task", null, 0);

		/* global task pointer, updated by PreTaskHook */
		global_header_add("#define KESO_CURRENT_TASK keso_curr_task\n");
		global_header_add("#define KESO_SET_CURRENT_TASK(_x_) keso_curr_task=(_x_)\n");

		/*
		 * The TriCore registers a0, a1, a8 and a9 are not stored in the CSA
		 * and exist for global use. Therefore we can use a9 as global thread
		 * pointer.
		 */
		if (opts.hasOption("useGlobalReg")) {
			global_header_add("register KESO_TASKCLASSTYPE *keso_curr_task __asm__(\"a9\");\n");
		} else {
			global_header_add("extern KESO_TASKCLASSTYPE *keso_curr_task;\n");
			global_add("KESO_TASKCLASSTYPE *keso_curr_task;\n");
		}

		/* keso_curr_task_fkt() will return a reference to the running
		 * task with the help of OSEK GetTaskID(). It will be used in
		 * the PreTaskHook to update the keso_curr_task variable.
		 */
		global_header_add("KESO_TASKCLASSTYPE *keso_curr_task_fkt();\n");

		global_add("\n\n");
		global_add("/* Returns a pointer to the currently running Task\n");
		global_add(" * INVALID_TASK is assumed to be greater by one than\n");
		global_add(" * the highest TaskID. This is ProOSEK specific and\n");
		global_add(" * will likely not work on other OSEK implementations.\n");
		global_add(" * keso_task_index is initialised with NULL, which\n");
		global_add(" * represents the INVALID_TASK Java-Task object.\n");
		global_add(" */\n");
		global_add("KESO_TASKCLASSTYPE *keso_curr_task_fkt() {\n");
		global_add("\tTaskType taskID;\n");
		global_add("\tGetTaskID(&taskID);\n");
		global_add("\treturn keso_task_index[taskID];\n");
		global_add("}\n\n");

		/* RESOURCE MANAGEMENT */
		addManagedResource(opts.getSysResource(), "Resource", "INVALID_RESOURCE", ResourceDefinition.INVALID_RESOURCE);

		/* ALARM MANAGEMENT */
		addManagedResource(opts.getSysAlarms(), "Alarm", "INVALID_ALARM", AlarmDefinition.INVALID_ALARM);
	}

	private void addTaskCode(PrintStream out, IMMethod mainMethod, TaskDefinition task, IMClass mainClass) throws CompileException {

		boolean inline_task = (opts.hasOption("inline_task") && !mainMethod.canBlock());
		if (inline_task) writeIncludes(out, mainMethod.getMethodIncludes());

		out.print("TASK(");
		out.print(task.getIdentifier());
		out.print(") {\n");

		out.print("\tkeso_stack_t stack;\n");
		out.print("\tstack.domain_id = ");
		out.print(((DomainDefinition)task.parent).domainid);
		out.print(";\n");
		if (opts.hasLinkedListOfLocalReferences())
			out.print("\tstack.llrefs=(object_t**)KESO_EOLL;\n");
		out.print("\tstack.next=NULL;\n");

		out.print("\tkeso_stack_index[");
		out.print(task.getIdentifier());
		out.print("] = &stack;\n");

		if (inline_task) {
			StringBuffer body = mainMethod.getMethodBodyPretty();
			int pos=-1;
			pos = body.indexOf("obj0");
			if (pos>0) {
				out.print("\tobject_t* obj0 = ");
				out.print(add_accessStaticObject(mainClass, task.getIdentifier()+"_obj"));
				out.println(";");
			}
			out.println(body);
		} else {
			out.print(exec_task(new StringBuffer(), mainMethod, "stack.llrefs", add_accessStaticObject(mainClass, task.getIdentifier()+"_obj")));
		}

		out.print("\tkeso_stack_index[");
		out.print(task.getIdentifier());
		out.print("]=NULL;\n");
		// for backup, if application does not do it though it should!
		if (opts.hasOption("exit")) out.print("\texit(0);\n");
		out.print("\tTerminateTask();\n}\n\n");
	}

	public void writeMainFile() throws CompileException {
		try {
			String mainfile = opts.getOutputPath()+"/keso_main.c";
			PrintStream out = new PrintStream(new FileOutputStream(mainfile));
			Vector tasks = opts.getSysTasks();

			out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");

			/* include the headers for each task's class in global.h */
			out.print("#include \"global.h\"\n");
			for(int i=0; i<tasks.size(); i++) {
				TaskDefinition task = (TaskDefinition) tasks.elementAt(i);
				if (task instanceof NativeTask) continue;
				IMClass mainClass = repository.getClass(task.getMainClassName());
				String include = "#include \"" + mainClass.getAlias() + ".h\"\n";
				global_header_add(include);
			}

			writeHeaderClassConstructors(out);

			// includes for hooks
			OSDefinition os = opts.getSysDef().getOSDef();
			if (os!=null) {
				Object[] allHooks = os.getAllHooks();
				ComplexBoolAttribute[] hooks = (ComplexBoolAttribute[]) allHooks[0];
				String[] hookClasses = new String[hooks.length];
				for(int i=0;i<hooks.length;i++) {
					if (hooks[i]==null || !hooks[i].setting) continue;
					String hc = hooks[i].getAttribute("HookClass").valueString();
					for(int j=0; j<hookClasses.length;j++) {
						if(hookClasses[j]==null) {
							// this header must be included
							hookClasses[j] = hc;
							out.print("#include \"");
							out.print(repository.getClass(hc).getAlias());
							out.print(".h\"\n");
							break;
						} else if (hookClasses[j].compareTo(hc)==0) {
							// this header has already been included
							break;
						}
					}
				}
			} else {
				opts.warn("No OSEK Section found!");
			}

			out.print("#include \"os.h\"\n");

			// Runtime information such as Task objects, Resource objects, etc.
			writeRuntimeData(out, tasks);

			// Hook routines
			writeHookRoutines(out, tasks);

			// ISRs
			writeISRs(out);

			// alarm callback functions
			writeAlarmCallbacks(out, opts.getSysAlarms());

			// emit code for all tasks
			for(int i=0; i<tasks.size(); i++) {
				TaskDefinition task = (TaskDefinition) tasks.elementAt(i);
				if(task instanceof NativeTask) continue;
				IMClass mainClass = repository.getClass(task.getMainClassName());
				IMMethod mainMethod = mainClass.getMethod(task.getMainMethodName());
				addTaskCode(out, mainMethod, task, mainClass);
			}

			add_rule(new MakeRule(opts, "keso_main.o",
						"keso_main.c", modul.getAlias()+".h"));

			out.close();

		} catch (IOException ex) {
			throw new CompileException(ex.toString());
		}

		writeInfoFile();
	}

	protected void writeISRs(PrintStream out) throws CompileException {
		Vector isrs = opts.getSysISRs();

		boolean inline_isr = opts.hasOption("inline_isr");

		for(int i=0; i<isrs.size(); i++) {
			ISRDefinition isr = (ISRDefinition) isrs.elementAt(i);

			try {
				int cat = isr.getCategory();
				if(cat!=1 && cat!=2)
				       	throw new CompileException(isr + " does not have a valid Category attribute defined!");
				IMClass handlerClass = repository.getClass(isr.getAttribute("HandlerClass").valueString());
				IMMethod handlerMethod = handlerClass.getMethod(isr.getAttribute("HandlerMethod").valueString());
				String isrDomain="DOMAIN_ZERO";
				if(isr.parent instanceof DomainDefinition)
					isrDomain = Integer.toString( ((DomainDefinition)isr.parent).domainid );

			 	if (inline_isr)	writeIncludes(out, handlerMethod.getMethodIncludes());

				out.print("ISR");
				if(cat==1) out.print("1");
				out.print("(");
				out.print(isr.getIdentifier());
				out.println(") {");
				/* Domain environment
				 *  we do not rely on keso_curr_task being set because it might be NULL
				 *  instead we backup the domainid to restore it afterwards
				 * Task environment
				 *  if the currently running task is in a domain different to the ISR'
				 *  domain, we must ensure keso_curr_task is set to INVALID_TASK. If we
				 *  did not do this, a task reference could move to a different domain
				 *  enabling unauthorized use of services like SetEvent/ChainTask
				 *
				 * We only need to backup the domain id for ISR1, because PreTaskHook
				 * will run anyway after an ISR2
				 */

				if (!opts.isSingleDomainSystem()) {
					if (cat==1)
						out.println("\tunsigned char domain_id_backup = keso_current_domain_id;");

					out.print("\tkeso_current_domain_id = ");
					out.print(isrDomain);
					out.print(";\n");

					/*
					 * ISR cat1 may not use the getTaskID() service. We do not need to set
					 * the Task to INVALID_TASK for these.
					 */
					if(cat==2) {
						out.print("\tif(keso_curr_task!=NULL && keso_curr_task->_domain_id != ");
						out.print(isrDomain);
						out.println(")");
						out.println("\t\tkeso_curr_task=NULL;");
					}
				}

				if (inline_isr) {
					StringBuffer body = handlerMethod.getMethodBody();
					int pos=-1;
					while ((pos=body.indexOf("return;"))>0) {
						body.delete(pos,pos+7);
					}
					out.println(body);
				} else {
					out.println(exec_task(new StringBuffer(), handlerMethod, null, null));
				}

				if (!opts.isSingleDomainSystem()) {
					if(cat==1)
						out.print("\tkeso_current_domain_id = domain_id_backup;\n");
				}
				out.println("}\n");
			} catch (NullPointerException e) {
				e.printStackTrace();
				throw new CompileException(isr + " does not have all required attributes defined!");
			}
		}
	}

	protected void writeHookRoutines(PrintStream out, Vector tasks) throws CompileException {
		OSDefinition osdef = opts.getSysDef().getOSDef();

		// allHooks will contain {hooks, hookdescs, hookparams}
		Object[] allHooks = osdef.getAllHooks();
		ComplexBoolAttribute[] hooks = (ComplexBoolAttribute[]) allHooks[0];
		String[] hookDescs = (String[]) allHooks[1];
		String[][][] hookParams = (String[][][]) allHooks[2];

		out.println("#include \"domains.h\"");

		if (opts.hasOption("inline_clinit")) {
			Enumeration clinits = class_constructors.elements();
			while (clinits.hasMoreElements()) {
				IMMethod m = (IMMethod)clinits.nextElement();
				writeIncludes(out, m.getMethodIncludes());
			}
		}

		// Startup Hook specially handled
		out.println("void StartupHook() {");

		// Initialize heap
		IMDomain[] doms = opts.getDomains();
		out.println("\t/* Initialize heaps */");
		for(int i=0;i<doms.length;i++) {
			IMHeap dheap = doms[i].getHeap();
			dheap.initHeap(out);
		}
		out.println();

				/* We should do all the initialization work before calling
				 * user code (if possible). Constructors are user code..
				 */
		initManagedResource(opts.getSysTasks(), "Task", out);
		initManagedResource(opts.getSysResource(), "Resource", out);
		initManagedResource(opts.getSysAlarms(), "Alarm", out);

		/* Call class constructors */
		writeCallClassConstructors(out);

		// Call constructors of the resource object
		// We call the task's constructors last since we
		// don't know what they'll be doing
		// We do not call the constructors of Alarms and
		// Resources anymore because they are empty
		callManagedResourceConstructors(opts.getSysTasks(), "Task", out);

		// call user-defined hook here
		if (hooks[0]!=null && hooks[0].setting) {
			out.print("\n\t/* user defined startup hook */\n");
			addHookCode(out,hooks[0], hookParams[0]);
		}
		out.println("}\n");

		// PreTaskHook will be used to init keso_curr_task and keso_curr_domain
		out.println("void PreTaskHook() {");
		out.println("\t/* Init keso_curr_task and keso_current_domain_id */");
		out.println("\tKESO_TASKCLASSTYPE *ct = keso_curr_task_fkt();");
		if (!opts.isSingleDomainSystem()) {
			out.println("\tkeso_current_domain_id = ct->_e_domain_id;");
			out.println("\tif(ct->_e_domain_id == ct->_domain_id)");
			out.println("\t\tKESO_SET_CURRENT_TASK(ct);");
			out.println("\telse KESO_SET_CURRENT_TASK(NULL);");
		} else {
			out.println("\tKESO_SET_CURRENT_TASK(ct);");
		}

		// call user defined hook
		for(int i=1; i<hooks.length; i++) {
			if(hookDescs[i].compareTo("PreTaskHook")!=0) continue;
			if(hooks[i]==null || !hooks[i].setting) break;
			out.print("\n\t/* user defined pretask hook */\n");
			addHookCode(out,hooks[i], hookParams[i]);
		}
		out.println("}\n");

		/* PostTaskHook:
		 *  for some reason I don't know ProOSEK wants to have a PostTaskHook
		 *  when a PreTaskHook is used. So for god's sake, in case the user doe
		 *  not use one we will add an empty one here. We will, however, not enable
		 *  the OIL attribute
		 * ErrorHook:
		 *  It may happen that currently _no_ task is running when the ErrorHook i
		 *  called. We must ensure that in this case the errorhook runs in domain
		 *  zero and keso_curr_task is set to INVALID_TASK
		 */
		for(int i=1; i<hooks.length; i++) {
			if(hookDescs[i].compareTo("PostTaskHook")==0) {
				if(hooks[i]==null || !hooks[i].setting)
					out.println("void PostTaskHook() { /* empty PostTaskHook to satisfy ProOSEK */ }\n");
			} else if(hookDescs[i].compareTo("ErrorHook")==0) {
				if(hooks[i]!=null && hooks[i].setting) {
					out.println("void ErrorHook(");
					out.print(hookParams[i][0][0]);
					out.print(" ");
					out.print(hookParams[i][0][1]);
					out.print(") {");
					out.println("\tif(keso_curr_task_fkt() == keso_task_index[INVALID_TASK]) {");
					out.println("\t\tkeso_current_domain_id = DOMAIN_ZERO;");
					out.println("\t\tkeso_curr_task = keso_task_index[INVALID_TASK];");
					out.println("\t}");
					out.println("\t/* user defined ErrorHook */");
					addHookCode(out, hooks[i], hookParams[i]);
					out.println("\t/* No need to restore task/domain environment, since it was only modified");
					out.println("\t * in case there was no task running. In this case the PreTaskHook will restore");
					out.println("\t * the environment when the next task is launched.");
					out.println("\t */");
					out.println("}\n");
				}
			}
		}

		// the other hooks are purely user defined (for now ;-) )

		// [0] is the StartupHook, skip it
		for (int i=1; i<hooks.length; i++) {
			if(hooks[i]==null || !hooks[i].setting) continue;

			// skip specially handled hooks here
			// be sure to enable the always enabled Hook
			// in the OIL file by modifying OSDefinition.toOIL()
			if(hookDescs[i].compareTo("PreTaskHook")==0) continue;
			if(hookDescs[i].compareTo("ErrorHook")==0) continue;

			// handle non modified hook
			out.print("void " );
			out.print( hookDescs[i] );
			out.print( "(");
			if (hookParams[i]!=null) {
				for (int j=0; j<hookParams[i].length; j++) {
					if(j>0) out.print(", ");
					out.print(hookParams[i][j][0]); // param type
					out.print(" ");
					out.print(hookParams[i][j][1]); // param's name
				}
			}
			out.println(") {");

						/* just a slight mod for the ShutdownHook ;-)
						 * make sure it runs in DomainZero
						 * ShutdownHook must not call getTaskID, keso_curr_task
						 * does therefore not have to be set
						 */
			if(hookDescs[i].compareTo("ShutdownHook")==0)
				out.println("\tkeso_current_domain_id = DOMAIN_ZERO;");

			addHookCode(out, hooks[i], hookParams[i]);
			out.println("}\n");
		}
	}

	protected void addHookCode(PrintStream out, ComplexBoolAttribute hook, String[][] params) throws CompileException {
		Attribut hookClassName = hook.getAttribute("HookClass");
		Attribut hookMethodName = hook.getAttribute("HookMethod");
		if (hookMethodName==null || hookClassName==null)
			throw new CompileException("HookClass/HookMethod not specified for " + hook.ident);
		IMClass hookClass = repository.getClass(hookClassName.valueString());
		if (hookClass==null)
			throw new CompileException("HookClass "+hookClassName.valueString()+" specified for " + hook.ident + " does not exist");
		IMMethod hookMethod = hookClass.getMethod(hookMethodName.valueString());
		if (hookMethod==null)
			throw new CompileException("HookMethod "+hookMethodName.valueString()+" specified for " + hook.ident + " does not exist");
		if (!hookMethod.isStatic()) {
			throw new CompileException("HookMethod specified for " + hook.ident + " is not static");
		}

		// add call
		StringBuffer para = null;
		if(params != null) {
			for (int i=0; i<params.length; i++) {
			/* specifing a hook method with the correct signature
			 * is the responsibility of the application developer.
			 * If he specified a method with the wrong signature,
			 * the generated KESO system will not compile, just a
			 * an OSEK system would not compile if hooks with
			 * signatures that don't fit were specified
			 */
				if (para==null) para = new StringBuffer();
				else para.append(',');
				para.append(params[i][1]); // param name
			}
		}
		out.print(exec_task(new StringBuffer(), hookMethod, null, para));
	}

	/**
	 * Add code for all Alarm callback functions.
	 *
	 * We ensure that Alarm callbacks run in the domain they were configured in.
	 * Alarm callbacks may not call getTaskID and we trust the application
	 * developer that he does not do that because it would possibly migrate a Task
	 * reference to a domain that should not be able to access it.
	 */
	protected void writeAlarmCallbacks(PrintStream out, Vector alarmDefs) throws CompileException {
		for(int i=0; i<alarmDefs.size(); i++) {
			AlarmDefinition adef = (AlarmDefinition) alarmDefs.elementAt(i);
			ComplexAttribute action = adef.getAction();

			if(action.value.compareToIgnoreCase("ALARMCALLBACK")!=0) continue;

			out.print("ALARMCALLBACK(");
			out.print(adef.getIdentifier());
			out.println("_callback) {");

			Attribut cbClass = action.getAttribute("AlarmCallbackClass");
			Attribut cbMethod = action.getAttribute("AlarmCallbackMethod");

			if (cbClass==null || cbMethod==null)
				throw new CompileException("Alarm " + adef.getIdentifier() + " needs AlarmCallbackClass and AlarmCallbackMethod defined");

			IMClass cbIMClass = repository.getClass(cbClass.valueString());
			if (cbIMClass == null)
				throw new CompileException("Alarm " + adef.getIdentifier() + "'s callback class is not known");

			IMMethod cbIMMethod = cbIMClass.getMethod(cbMethod.valueString());
			if(cbIMMethod == null || !cbIMMethod.isStatic())
				throw new CompileException("Alarm " + adef.getIdentifier() + "'s callback method is not known or not static");

			String cbDomain = "DOMAIN_ZERO";
			if(adef.parent instanceof DomainDefinition)
				cbDomain = Integer.toString( ((DomainDefinition)adef.parent).domainid );

			/* Alarm callbacks must not use getTaskID, thus we do not need to adjust the current task here */
			out.println("\tunsigned char domain_backup = keso_current_domain_id;");
			out.print("\tkeso_current_domain_id = ");
			out.print(cbDomain);
			out.println(";");
			out.print(exec_task(new StringBuffer(), cbIMMethod, null, null));

			// We can probably save this too, PreTaskHook should run because
			// Alarm Callbacks seem to run on ISR Level, and Rescheduling is
			// performed when returning from Interrupt to Task Level, but
			// not 100% sure about this so I choose the save bet for now
			out.println("\tkeso_current_domain_id = domain_backup;");
			out.println("}");
		}
	}

	protected void writeGlobalFiles() throws CompileException {
		try {
			writeOILFile();

			// Makefile
			String makefile = opts.getOutputPath()+"/Makefile";
			PrintStream out = new PrintStream(new FileOutputStream(makefile));
			out.println("# THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT\n\n");
			out.println(".PHONY: clean all recreate dbg");
			out.print("CC=");
			out.println(opts.getCC());
			out.print("STRIP=");
			out.println(opts.getStrip());
			out.print("SIZE=");
			out.println(opts.getObjSize());
			out.println("JOSEKFLAGS=-D assertions ");

			if (opts.getLinkerOptions()!=null) {
				out.print("LDSCR=");
				out.println(opts.getLinkerOptions());
			}

			out.print(opts.getCFlagsVarName());
			out.print( "=");
			out.print("-I ");
			out.print(opts.getCorePath());
			out.print(" ");
			out.println(opts.getCFlags());
			out.println("");
			out.print(opts.getObjVarName() );
			out.print( " = ");

			String obj_files[] = new String[rules.size()];

			for (int i=0;i<rules.size();i++) {
				obj_files[i] = ((MakeRule)rules.elementAt(i)).getTarget();
				out.print(obj_files[i]);
				out.print(" ");
			}
			out.print("dispatch.o");

			out.print("\nall: keso_main\n");

			out.print("\nkeso_main: keso_main.o libKESO.a");
			out.print("\n\t@echo LD  keso_main");
			out.print("\n\t@$(CC) $(" + opts.getCFlagsVarName() + ") -o keso_main keso_main.o  -L. -lKESO");
			if (opts.getCoreLibName()!=null) {
				out.print(" -L");
				out.print(opts.getCorePath());
				out.print(" -l");
				out.print(opts.getCoreLibName());
			}
			out.print("\n\t@cp keso_main keso_main_g");
			out.print("\n\t@$(STRIP) keso_main");
			//out.print("\n\t@$(OBJCPY) -R .comment keso_main");
			out.print("\n\t@echo $(KESORC) : $(JINOFLAGS)");
			out.println("\n\t@$(SIZE) keso_main");

			out.println("clean: ");
			out.println("\t@rm -rf keso_main keso_main_g *.o *.dot os.h os.c\n");

			out.println("recreate: ");
			out.println("\tcd ../.. ; make\n");

			out.println("valgrind: keso_main");
			out.println("\tvalgrind --db-attach=yes keso_main_g\n");

			out.println("cachegrind: keso_main");
			out.println("\tvalgrind --tool=cachegrind keso_main_g\n");

			out.print("libKESO.a: global.o os.o $(OBJS_KESO)\n");
			out.print("\t@$(AR) rc libKESO.a  global.o os.o $(OBJS_KESO)\n\n");

			out.print("global.o: global.c os.h");
			out.print("\n\t@echo OBJ global.o");
			//out.print("\n\t@mkexptab.pl -plain");
			out.print("\n\t@$(CC) $(" + opts.getCFlagsVarName() + ") -c -o global.o global.c");
			out.print("\n");

			out.println("\ndispatch.o: os.h");
			out.println("\t@echo OBJ dispatch.o");
			out.println("\t@$(CC) $(CFLAGS) -c -o dispatch.o dispatch.s");

			out.println("\nos.h: keso_main.oil");
			out.println("\t@echo JOSEK os.c os.h");
			out.println("\t@josek ${JOSEKFLAGS} -c ${JOSEKRULES} -o ./ -f keso_main.oil");

			out.println("\nos.c: keso_main.oil");
			out.println("\t@echo JOSEK os.c os.h");
			out.println("\t@josek ${JOSEKFLAGS} -c ${JOSEKRULES} -o ./ -f keso_main.oil");

			out.println("\nos.o: os.c");
			out.println("\t@echo OBJ os.o");
			//out.print("\t$(CC) -static -Wall -Wno-strict-aliasing -falign-functions=1 -g -Os  ");
			out.print("\t$(CC) -static $(CFLAGS) ");
			//out.print("\t$(CC) $(");
			//out.print(opts.getCFlagsVarName());
			//out.println(") -c -o os.o os.c\n");
			out.println(" -c -o os.o os.c\n");

			for (int i=0;i<rules.size();i++) {
				MakeRule rule = (MakeRule)rules.elementAt(i);
				rule.writeRule(out,"os.h global.h");
				out.print("\n");
			}

			out.println("\nkesoinfo: kesoinfo.c");
			out.print("\tgcc -I ");
			out.print(opts.getCorePath());
			out.println(" -O2 -ansi -Wall -o kesoinfo kesoinfo.c");

			out.println("tags:");
			out.println("\t$(CTAGBIN) *.h *.c");

			out.close();

			// Write global.h
			String filename = opts.getOutputPath()+"/global.h";
			PrintStream glb_out = new PrintStream(new FileOutputStream(filename));
			glb_out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
			glb_out.println("#ifndef _GLOBAL_H_");
			glb_out.println("#define _GLOBAL_H_ 1\n");

			glb_out.println("#define JOSEK_TARGET 1");
			glb_out.println("#include <keso_types.h>");
			glb_out.println("#include <keso_support.h>");
			writeIncludes(glb_out, global_header_includes);
			glb_out.println("#include \"os.h\"\n");
			glb_out.println(global_header);
			glb_out.println("\n#endif");
			glb_out.close();

			// Write global.c
			filename = opts.getOutputPath()+"/global.c";
			glb_out = new PrintStream(new FileOutputStream(filename));
			glb_out.println("/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */\n");
			glb_out.println("#include \"global.h\"");
			glb_out.println("#include \"domains.h\"");

			writeIncludes(glb_out, global_includes);
			glb_out.println(global);

			glb_out.close();

		} catch (IOException ex) {
			throw new CompileException(ex.toString());
		}
	}
}

