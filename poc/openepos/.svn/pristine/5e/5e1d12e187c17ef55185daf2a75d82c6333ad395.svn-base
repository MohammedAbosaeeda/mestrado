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
import keso.compiler.config.DomainDefinition;
import keso.compiler.config.TaskDefinition;

import java.util.Hashtable;
import java.util.Vector;

//
// Mit "Weavelet" kann man ganz einfach den Uebersetzungvorgang spezialisieren.
// 

public class TaskService extends KesoIndexedResource {

	IMClass task_clazz;

	// Why can the gc run while schedule is called?
	boolean schedule_gc_run = false;
	boolean more_inline = true;

	public TaskService(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		//
		// Hier alle Methoden aufzaehlen, die von dem "Weavelet" beeinflusst werden
		// sollen. 
		//
		joinPoints = new String[] { "keso/core/TaskService.*" };
		task_clazz = repository.getClass("keso/core/Task");
	}

	public void require(int domainID, String className, String methodName) {
		repository.requireClass(domainID, "keso/core/Task");
	}

	public int checkAttribut(IMMethod self, int attr) {
		if (schedule_gc_run && attr==Weavelet.ATTR_BLOCK) {
			if (self.termed("schedule()V")) return TRUE;
		}
		return FALSE;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}

	// 
	// Mit affectMethod wird der Methoden-Rumpf ersetzt. Der Aufruf bleibt ansonsten
	// erhalten. (siehe keso/compiler/weavelet/Object.)
	//

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException
	{
		this.coder = coder;
		this.callee = method;
		this.clazz = clazz;

		if (method.termed("getTaskState(Lkeso/core/Task;)I")) {
			getTaskState();
		} else if (!more_inline && method.termed("chain(Lkeso/core/Task;)I")) {
			coder.add("keso_stack_index[KESO_CURRENT_TASK->");
			coder.add(task_clazz.emitField("task_id"));
			coder.add("]=((void*)0);");
			retIgetResRef("ChainTask", "calls OSEK ChainTask", "keso/core/Task", "task_id");
		} else if (!more_inline && callee.termed("activate(Lkeso/core/Task;)I")) {
			retIgetResRef("ActivateTask", "calls OSEK ActivateTask", "keso/core/Task", "task_id");
		} else return false;

		return true;
	}

	//
	// Mit affectInvokeXXX wird der Aufruf ersetzt. In unserem Fall der Aufruf an dem 
	// Interface keso/core/TaskService. 
	//
	// node   Ist das IMNode-Objekt von dem wir aufgerufen werden.
	//
	// caller Ist die aufrufende Methode.
	//
	// callee Ist die Methode, die aufgerufen werden soll.
	//
	// obj    Ist das IMNode-Objekt welches den this-Pointer representiert. 
	//
	// args[] Sind die weiteren Parameter.
	//
	// coder  Ist das aktuelle C-Backend. 
	//
	// Rueckgabewert. Bei false wird die translate-Methode von node weiter ausgefuehrt
	//                als waere nichts passiert. Bei true wird diese Methode abgebrochen.
	//

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException
	{
		this.node   = node;
		this.caller = caller;
		this.callee = callee;
		this.args   = args;
		this.coder  = coder;

		if (callee.termed("terminate()I")) {
			TerminateTask();
		} else if (callee.termed("schedule()V")) {
			if (schedule_gc_run) 
				caller.getMethodFrame().codeEOLL(coder);
			Schedule();
		} else if (more_inline && callee.termed("activate(Lkeso/core/Task;)I")) {
			ActivateTask(args[0]);
		} else if (more_inline && callee.termed("chain(Lkeso/core/Task;)I")) {
			ChainTask(args[0]);
		} else if (callee.termed("getTaskID()Lkeso/core/Task;")) {
			coder.add("((object_t*)KESO_CURRENT_TASK)");
		} else if (callee.termed("getTaskByName(Ljava/lang/String;)Lkeso/core/Task;")) {
			getTaskByName();
			return true;
		} else return false;
		
		// return value true tells the builder that this invoke has been handled
		return true;
	}

	private void ChainTask(IMNode taskID) throws CompileException {
		if (task_clazz==null) task_clazz = repository.getClass("keso/core/Task");
		coder.add_class(task_clazz);
		coder.add("({keso_stack_index[KESO_CURRENT_TASK->");
		coder.add(task_clazz.emitField("task_id"));
		coder.add("]=((void*)0);");
		coder.add("(jint)ChainTask(");
		coder.add_getField(task_clazz, taskID, "task_id");
	       	coder.add(");})");
	}

	private boolean once = false;
	private void ActivateTask(IMNode taskID) throws CompileException {
		if (task_clazz==null) task_clazz = repository.getClass("keso/core/Task");
		coder.add_class(task_clazz);
		if (opts.hasOption("_X_josek_hacks") && opts.getTarget()==BuilderOptions.LINUX_JOSEK_TARGET) {
			if (!once) {
				coder.global_header_add("void activatetask(TaskType id);");
				once=true;
			}
			coder.add("(({activatetask(");
			coder.add_getField(task_clazz, taskID, "task_id");
			coder.add(");}),E_OK)");

		} else {
			coder.add("((jint)ActivateTask(");
			coder.add_getField(task_clazz, taskID, "task_id");
			coder.add("))");
		}
	}
	
	private void TerminateTask() throws CompileException {
		if (task_clazz==null) task_clazz = repository.getClass("keso/core/Task");
		switch( opts.getTarget() ) {
			case BuilderOptions.LINUX_TARGET:
				coder.add("exit(0)");
				break;
			default:	
				coder.add("({keso_stack_index[KESO_CURRENT_TASK->");
				coder.add(task_clazz.emitField("task_id"));
				coder.add("]=((void*)0);");
				coder.add("TerminateTask();})");
				break;
		}
	}

	private void Schedule() throws CompileException {
		switch( opts.getTarget() ) {
			case BuilderOptions.LINUX_TARGET:
				opts.warn("FIXME: Schedule not impl.");
				break;
			default:	
				coder.add("Schedule()");
				break;
		}
	}

	private void getTaskState() throws CompileException {
		IMClass task_class = repository.getClass("keso/core/Task");
		coder.add_class(task_class);

		coder.addln("/* Calls OSEK GetTaskState and passes the result to the application. */\n");

		if(opts.getTarget()==BuilderOptions.LINUX_TARGET) {
			opts.warn("FIXME: getTaskState not impl.");
			coder.addln("return (jint) 0;");
		} else { // FIXME not implemented for Linux
			coder.addln("TaskStateType state;\n");
			coder.add("GetTaskState(");
			coder.add_getField(task_class,"obj0","task_id");
			coder.addln(", &state);");
			coder.addln("return (jint) state;");
		}
	}
	
	private void getTaskByName() throws CompileException {
		// Build an array with task vectors for all domains
		Vector domains = opts.getSysDef().getDomains();
		Vector[] localResources = new Vector[domains.size()];
		
		for (int i=0;i<localResources.length;i++) {
			DomainDefinition dom = (DomainDefinition) domains.elementAt(i);
			localResources[i] = dom.getTasks();
		}
		
		getIndexedResourceByName(localResources, null, "task", "INVALID_TASK");
	}
}
