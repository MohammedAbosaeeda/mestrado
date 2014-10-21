/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler;

import keso.compiler.ClassStore;
import keso.compiler.imcode.IMClass;
import keso.compiler.imcode.IMMethod;
import keso.compiler.imcode.IMDomain;
import keso.compiler.imcode.IMPortalService;
import keso.compiler.backend.*;
import keso.compiler.MethodTableFactory;
import keso.compiler.imcode.*;

import keso.util.Debug;
import keso.util.IntegerHashtable;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import keso.compiler.config.*;

import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.File;
import java.io.FileReader;
import java.io.FileDescriptor;

final public class Builder {

	private BuilderOptions opts;
	private ClassStore repository;
	private MethodTableFactory table;
	private IMPortalService[] systemServices; 

	private Thread stdoutthread;
	private Thread stderrthread;
	
	public Builder(BuilderOptions opts) {
		this.opts=opts;
	}

	private void collectWorkToDo(String modul_dir, File module, Vector work_todo) {
		if (module.isDirectory()) {
			String[] subdirs = module.list();
			for (int i=0;i<subdirs.length;i++) {
				if (subdirs[i].startsWith(".")) continue;
				collectWorkToDo(modul_dir, new File(module.toString()+"/"+subdirs[i]), work_todo);
			}
		} else {
			if (module.getName().endsWith(".java")) {
				String todo = module.toString();
				work_todo.addElement(todo);
			}
		}
	}

	private void copy(String src, String dst) {

		int pos = dst.lastIndexOf(File.separatorChar);
		File dfile = new File(dst);
		File ddir = new File(dst.substring(0,pos));
		byte buffer[] = new byte[4096];
		int len;
		
		try {
			ddir.mkdirs();
			dfile.createNewFile();
			FileInputStream in = new FileInputStream(src);
			FileOutputStream out = new FileOutputStream(dst);
			while ((len=in.read(buffer))>-1) { out.write(buffer,0,len); }
			in.close();
			out.close();
		} catch (IOException ex) {
			opts.warn("copy "+src+" fails!");
			opts.verbose(ex);
			System.exit(1);
		}

	}

	private int javac(Vector args) {
		String[] nargs = new String[args.size()];
		StringBuffer out = new StringBuffer("javac");
		int i=0;

		Enumeration largs = args.elements();
		while (largs.hasMoreElements()) { 
			nargs[i] = (String)largs.nextElement();
			out.append(" ");
			out.append(nargs[i++]);
		}
		opts.verbose(out);

		return javac(nargs);
	}

	/**
	 * execute the java compiler from sun.
	 */
	private int javac(String[] args) {
		try {
			try {
				java.lang.Class c = java.lang.Class.forName("com.sun.tools.javac.Main");
				Class[] parameterTypes = new Class[1];
				parameterTypes[0] = args.getClass();
				java.lang.reflect.Method method = c.getMethod("main", parameterTypes);
				Object pars[] = new Object[1];
				pars[0] = args;
				try {
					method.invoke(null, pars);
				} catch (java.lang.reflect.InvocationTargetException e) {
					throw e.getTargetException();
				}
			} catch (NoExitException ex) {
				return ex.status;
			}
		} catch (Throwable ex) {
			Debug.out.println(ex);
		}

		return 0;
	}

	private boolean nothingToDo(Vector work_todo, long mtime) {
		if (mtime==0) return false;
		Enumeration work = work_todo.elements();
		while (work.hasMoreElements()) {
			File current = new File((String)work.nextElement());
			if (current.exists() && current.lastModified()>mtime) {
				opts.verbose(current.toString()+" is newer!");
				return false;
			}
		}
		return true;
	}


	public void startOutputThreads(final BufferedReader stdout, final BufferedReader stderr) {
		stdoutthread = new Thread() {
			public void run() {
				try {
					String text;
					while ((text = stdout.readLine()) != null) {
						System.out.println(text);
					}
				} catch (Exception e) {
					
				}
			}
		};
		stderrthread = new Thread() {
			public void run() {
				try {
					String text;
					while ((text = stderr.readLine()) != null) {
						System.err.println(text);
					}
				} catch (Exception e) {
					
				}
			}
		};

		stdoutthread.start();
		stderrthread.start();
	}

	public void waitForEndOfOutput() {
		try { stdoutthread.join(); } catch (InterruptedException ex) {}
		try { stderrthread.join(); } catch (InterruptedException ex) {}
	}

	public void callMake() throws IOException {
		String makefile = opts.getFirstLevelOutputPath()+"/Makefile";
		Runtime runtime = Runtime.getRuntime();
		Process make = runtime.exec("sh "+opts.getFirstLevelOutputPath()+"/make.sh");
		startOutputThreads(new BufferedReader(new InputStreamReader(make.getInputStream())),
				new BufferedReader(new InputStreamReader(make.getErrorStream())));
		waitForEndOfOutput();
	}

	public void writeFirstLevelMakefile() throws IOException {
		String doit = opts.getFirstLevelOutputPath()+"/make.sh";
		PrintStream doit_out = new PrintStream(new FileOutputStream(doit));
		doit_out.println("# THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT\n");
		doit_out.println("cd "+opts.getFirstLevelOutputPath());
		doit_out.println("make");
		doit_out.close();

		String makefile = opts.getFirstLevelOutputPath()+"/Makefile";
		PrintStream out = new PrintStream(new FileOutputStream(makefile));
		out.println("# THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT\n");
		out.println(".PHONY: all clear\n");

		out.println("all:");
		Attribut[] cattr = opts.getClusterAttrs();
		for (int i=0;i<cattr.length;i++) {
			Attribut attr = cattr[i];
			if (!(attr instanceof SystemDefinition)) continue; 
			SystemDefinition sys = (SystemDefinition) attr;
			out.print("\t@cd ");
			out.print(sys.getIdentifier());
			out.print("\t; make 2>&1");
			if (opts.hasOption("log_backend"))
				out.print(" > last-compile.log ");
			out.println("");
		}

		out.println("\nclean:");
		for (int i=0;i<cattr.length;i++) {
			Attribut attr = cattr[i];
			if (!(attr instanceof SystemDefinition)) continue; 
			SystemDefinition sys = (SystemDefinition) attr;
			out.print("\t@cd ");
			out.print(sys.getIdentifier());
			out.println("\t; make clean");
		}
		out.close();
	}

	public void preCompileModules() throws IOException, Exception {
		/* compile source files into classes */

		/* prepare directory for output */
		String classpath = opts.getClassDir();
		File classpathfile = new File(classpath);
		classpathfile.mkdirs();

		long mtime=0;
		File timestamp = new File(classpath+"/.timestamp");
		if (timestamp.exists()) {
			mtime = timestamp.lastModified();
			timestamp.setLastModified(System.currentTimeMillis());
		} else {
			timestamp.createNewFile();
		}

		String cp = opts.getJDKClassPath();
		if (cp!=null && !cp.equals("")) { classpath= classpath+":"+cp; }

		String[] modules  = opts.getAllModulPaths();
		modules = getRequiredModules(modules);

		boolean needcoreclasses=false;
		for (int i=0;i<modules.length;i++) {
			if (modules[i].equals("core")) {
				needcoreclasses=true;
				break;
			}
		}
		if (needcoreclasses) 
			GenerateKesoClasses.generateCoreClasses(opts);
			 

		Debug.out.println("");
		int ret=0;
		boolean lazy_compile = false;
		for (int i=0;i<modules.length;i++) {
			Vector   work_todo = new Vector();
			if (modules[i]==null) continue;
			String modul = "libs/"+modules[i];
			File modul_dir = new File(modul);

			if (modul_dir.isFile()) {
				if (modul.endsWith(".zip") || modul.endsWith(".jar")) {
					add_archiv(classpath, modul_dir);
				} else {
					System.err.println("Module "+modules[i]+" not found!");
					System.err.println("Please check modules and source path statement in the config file.");
					throw new IOException(modul_dir.toString()+" not exists!");
				}
			} else if (!modul_dir.exists()) {
				System.err.println("Module "+modules[i]+" not found!");
				System.err.println("Please check modules and source path statement in the config file.");
				throw new IOException(modul_dir.toString()+" not exists!");
			} else {
				File meta_file = new File(modul+"/META");

				opts.verbose("compile "+modul+" (.java -> .class)");
				collectWorkToDo(modul_dir.toString(), modul_dir, work_todo);

				if (lazy_compile && (lazy_compile=nothingToDo(work_todo, mtime))==true) continue;

				int s=0;
				int l=work_todo.size();
				while (l>0) {
					int w=(l>30?30:l);
					ret=compile_set(classpath,modul,work_todo,s,s+w);
					s=s+w; l=l-w;
				}
			}
		}
		Debug.out.println("");
	}

	/**	
	 * Parse META files and extend the module list.
	 */	
	private String[] getRequiredModules(String[] modules) throws IOException {
		Hashtable contains = new Hashtable();
		Vector required = new Vector();

		for (int i=0;i<modules.length;i++) {
			getMetaModulesFor(modules[i], contains, required);
		}

		for (int i=0;i<modules.length;i++) {
			if (contains.get(modules[i])==null) {
				contains.put(modules[i],modules[i]);
				required.add(modules[i]);
			}
		}
		
		String[] req = new String[required.size()];
		for (int i=0;i<required.size();i++) {
			req[i] = (String)required.elementAt(i);
			Debug.out.println("Module "+req[i]);
		}

		return req;
	}

	private void getMetaModulesFor(String mname, Hashtable contains, Vector required) throws IOException {
		String modul = "libs/"+mname;
		File modul_dir = new File(modul);

		if (modul_dir.isFile()) return;

		if (!modul_dir.exists()) {
			Debug.out.println("Module "+mname+" not found!");
			Debug.out.println("Please check modules and source path statement in the kcl file.");
			return;
		} 
			
		File meta_file = new File(modul+"/META");

		if (!meta_file.exists()) {
			opts.warn("Module "+mname+" is not valid!");
			opts.warn("META file is missing!");
			return;
		} 

		BufferedReader meta_reader = new BufferedReader(new FileReader(meta_file));
		String line;
		while (null!=(line=meta_reader.readLine())) {
			line = line.trim();
			if (line.startsWith("#")) continue;
			String[] val = line.split("=");
			if (val.length<2) continue;
			val[0] = val[0].trim().toUpperCase();
			val[1] = val[1].trim();
			if (val[0].equals("REQUIRE")) {
				if (contains.get(val[1])==null) {
					contains.put(val[1],val[1]);
					getMetaModulesFor(val[1], contains, required);
					required.add(val[1]);
				}
			}
		}
	}

	private int add_archiv(String classpath, File modul) throws IOException {
		ZipFile archiv = new ZipFile(modul);
		byte buffer[] = new byte[4096];

		Enumeration ee = archiv.entries();
		while (ee.hasMoreElements()) {
			ZipEntry entry = (ZipEntry)ee.nextElement();

			String dst = classpath+"/"+entry.getName();
			int pos = dst.lastIndexOf(File.separatorChar);
			File dfile = new File(dst);
			File ddir = new File(dst.substring(0,pos));
			ddir.mkdirs();
			dfile.createNewFile();

			InputStream in = archiv.getInputStream(entry);
			FileOutputStream out = new FileOutputStream(dst);

			int len;
			while ((len=in.read(buffer))>-1) { out.write(buffer,0,len); }

			out.close();
		}

		archiv.close();

		return 0;
	}

	private int compile_set(String classpath, String modul, Vector work_todo, int start, int end)
		throws IOException {
		Vector args = new Vector();
		//args.addElement("-verbose");
		args.addElement("-O");
		if (!opts.hasOption("no_javac_debug")) args.addElement("-g");
		//args.addElement("-target 1.4");
		//args.addElement("-nowarn");

		args.addElement("-d");
		args.addElement(classpath);
		args.addElement("-classpath");

		args.addElement(classpath);
		args.addElement("-sourcepath");
		args.addElement(modul+"/");

		for (int i=start;i<end;i++) {
			String cfile = (String)work_todo.elementAt(i);
			args.addElement(cfile);
		}

		return javac(args);
	}

	/**
	 * copySourceFiles
	 *
	 * This method copies the java source files into the output directory. The
	 * source are used to get debug information. 
	 */
	public void copySourceFiles() throws IOException {

		if (opts.hasOption("no_javac_debug")) return;

		String[] modules  = opts.getModulPaths();

		int ret=0;
		boolean lazy_compile = false;
		for (int i=0;i<modules.length;i++) {
			Vector   work_todo = new Vector();
			if (modules[i]==null) continue;
			String modul = "libs/"+modules[i];
			opts.verbose("copy "+modul);

			File modul_dir = new File(modul);
			collectWorkToDo(modul_dir.toString(), modul_dir, work_todo);

			//if (lazy_compile && (lazy_compile=nothingToDo(work_todo, mtime))==true) continue;

			Enumeration work = work_todo.elements();
			while (work.hasMoreElements()) {
				String current = (String)work.nextElement();
				copy(current, opts.getOutputPath()+"/src/"+current.substring(modul.length()+1));
			}
		}
	}


	public void computeServices() {
		try {
			repository = new ClassStore(opts, opts.getSourcePaths());
			opts.registerWeavelets(repository);
			ServiceManager.instance.computeServiceMethods(repository);
			repository.destroy();
			repository = null;    
		} catch (Exception ex) {
			ex.printStackTrace(Debug.out);
			Debug.out.println(ex);
			System.exit(-1);
		}

	}


	/**
	 * loadModules is loading the all needes libs and remove unneeded
	 * classes and methods.
	 */
	public void loadModules() {
		try {
			if (!opts.pruneClassTree()) {
				this.repository = new ClassStore(opts,opts.getSourcePaths());
				opts.registerWeavelets(repository);

			} else {
				ClassStore disk = new ClassStore(opts,opts.getSourcePaths());
				this.repository = disk.getPrunedClassStore();
				disk.destroy();
			}                      
		} catch (Exception ex) {
			ex.printStackTrace(Debug.out);
			Debug.out.println(ex);
			System.exit(-1);
		}
	}

	/**
	 * loadClasses simulates the class loading a head of time. 
	 *
	 * The repositorie only includes the required classes and
	 * methods so fare! (because this method is called after 
	 * loadModules)
	 */
	public void loadClasses() {
		try {
			opts.phase("load classes");
			// This call ensures that all domains
			// and heaps are initialized. 
			IMDomain[] doms = opts.getDomains();

			IMClass imclasses[] = repository.getAllClasses();
			for (int i=0;i<imclasses.length;i++) imclasses[i].load();
		} catch (Exception ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public void computeVTables() {
		try {
			opts.phase("compute vtables");
			table = new MethodTableFactory(opts, repository);
			if (!opts.hasOption("iface_no_omit_unused")) {
				IMClass imclasses[] = repository.getAllClasses();
				analyseCallGraph(imclasses);
				if (opts.hasOption("omit_unused_methods"))
					omitUnusedMethods(imclasses);
			}
			table.computeTables();
		} catch (Exception ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * inline method calls
	 */
	public void inlineMethodCalls() {
		if (!opts.doInlineCalls()) return;

		try {
			opts.phase("inline method calls");
			IMClass imclasses[] = repository.getAllClasses();
			analyseCallGraph(imclasses);

			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				imclass.inlineMethodCalls();
			}
		} catch (Exception ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * translate all java classes into c
	 */
	public void translate() {
		opts.phase("write C code");
		try {
			File outpath = new File(opts.getOutputPath());
			if (!outpath.exists()) outpath.mkdirs();
			Coder coder = null;

			if (table==null) computeVTables();

			IMClass imclasses[] = repository.getAllClasses();
			analyseCallGraph(imclasses);

			// Get the coder for the selected architecture
			switch (opts.getTarget()) {
				case BuilderOptions.TRICORE_TARGET:
					coder = new CoderTricore(opts, table, repository);
					break;

				case BuilderOptions.LINUX_TARGET:
					coder = new CoderI386Linux(opts, table, repository);
					break;

				case BuilderOptions.AVR_TARGET:
					coder = new CoderAVR(opts, table, repository);
					break;

				case BuilderOptions.LINUX_JOSEK_TARGET:
					coder = new CoderJOSEK(opts, table, repository);
					break;

				case BuilderOptions.EPOS_TARGET:
					coder = new CoderEPOS(opts, table, repository);
					break;

				default:
					coder = new CoderTricore(opts, table, repository);
					break;
			}


			// translate all classes and interfaces
			repository.translate(coder, table, class_type_info);

			// translate services
			if (systemServices != null) {
				for (int i = 0; i < systemServices.length; ++i) {
					systemServices[i].translate(coder);
				}
			}

			// create domains
			IMDomain.translateDomains(coder,opts);

			// write startup code
			coder.writeMainFile();

			coder.close();
			coder = null;

		} catch (CompileException ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public void dumpBC() {
		try {
			IMClass imclasses[] = repository.getAllClasses();
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				imclass.dumpBC();
			}
		} catch (IOException ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * perform constant folding and constant propagation 
	 */
	public void performConstantFolding() throws CompileException {
		opts.phase("ssa etc.");
		IMClass imclasses[] = repository.getAllClasses();

		if (opts.hasOption("omit_unused_methods")) {
			analyseCallGraph(imclasses);
			omitUnusedMethods(imclasses);
		}

		boolean run = true;
		while (run) {
			run = false;
			repository.resetFieldAccess();
			analyseCallGraph(imclasses);

			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				if (imclass.performConstantFolding()) run=true;
			}
		}
	}

	private void omitUnusedMethods(IMClass imclasses[]) throws CompileException {
		opts.verbose("##### omit unused methods ");
		for (int i=0;i<imclasses.length;i++) {
			IMClass imclass = imclasses[i];
			imclass.omitUnusedMethods();
		}
	}

	private void analyseCallGraph(IMClass imclasses[]) throws CompileException {

		opts.verbose("####### analyse call graph ");

		opts.verbose("##### reset call graph ");
		repository.resetCallGraph();
		for (int i=0;i<imclasses.length;i++) {
			IMClass imclass = imclasses[i];
			imclass.resetCallGraph();
		}

		// call graph for class constructors
		opts.vverbose("### analyse call graph <clinit>()V");
		for (int i=0;i<imclasses.length;i++) {
			IMClass imclass = imclasses[i];
			imclass.analyseCallGraph();
		}

		// call graph for tasks constructors
		opts.vverbose("### analyse call graph Task.<init>()V");
		IMClass taskClass = repository.getClass("keso/core/Task");
		IMMethod constructor;
		if (taskClass!=null) {
			constructor = taskClass.getMethod("<init>()V");
			constructor.calledFrom(null,null);
		}

		// call graph for isrs 
		opts.vverbose("### analyse call graph ISR");
		Vector isrs = opts.getSysISRs();
		for(int i=0; i<isrs.size(); i++) {
			ISRDefinition isr = (ISRDefinition) isrs.elementAt(i);
			IMClass handlerClass = repository.getClass(isr.getHandlerClass());
			IMMethod isr_handler = handlerClass.getMethod(isr.getHandlerMethod());
			isr_handler.calledFrom(null,null);
		}

		// call graph for hooks 
		opts.vverbose("### analyse call graph system hooks");
		OSDefinition os = opts.getSysDef().getOSDef();
		if (os!=null) {
			Object[] allHooks = os.getAllHooks();
			ComplexBoolAttribute[] hooks = (ComplexBoolAttribute[]) allHooks[0];
			String[] hookClasses = new String[hooks.length];
			for(int i=0;i<hooks.length;i++) {
				if (hooks[i]==null || !hooks[i].setting) continue;
				String hc = hooks[i].getAttribute("HookClass").valueString();
				String hm = hooks[i].getAttribute("HookMethod").valueString();
				IMClass handlerClass = repository.getClass(hc);
				IMMethod hookmethod = handlerClass.getMethod(hm);
				hookmethod.calledFrom(null,null);
			}
		}

		ComplexAttribute stack = opts.getSysDef().getStack();
		if (stack!=null) {
				String hc = stack.getAttribute("HandlerClass").valueString();
				String hm = stack.getAttribute("HandlerMethod").valueString();
				String sc = stack.getAttribute("StackChecks").valueString();
				if (sc.compareToIgnoreCase("true")==0) {
					IMClass handlerClass = repository.getClass(hc);
					IMMethod stackmethod = handlerClass.getMethod(hm);
					stackmethod.calledFrom(null,null);
				}
		}

		// call graph for services 
		// FIXME
		//IMPortalService services = opts.getPortalServices();
		//if (services!=null) services.calledFromSystem(repository);

		// call graph for tasks 
		Vector tasks = opts.getSysTasks();
		for(int i=0; i<tasks.size(); i++) {
			TaskDefinition task = (TaskDefinition) tasks.elementAt(i);
			if (task instanceof NativeTask) continue;
			taskClass = repository.getClass(task.getMainClassName());

			constructor = taskClass.getMethod("<init>()V");
			constructor.calledFrom(null,null);

			IMMethod launch = taskClass.getMethod(task.getMainMethodName());
			if (launch==null) {
				opts.critical("main method "+task.getMainMethodName()+" not found!");
			} else {
				launch.calledFrom(null,null);
			}
		}

		opts.verbose("##### analyse call graph done");
	}

	/**
	 * write a control flow graph for every method
	 */
	public void writeCFG() throws CompileException {
		if (!opts.createCFG()) return;
		try {
			IMClass imclasses[] = repository.getAllClasses();
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				imclass.writeCFG();
			}
		} catch (IOException ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * write a control flow graph for every method
	 */
	public void writeClassList() {
		if (!opts.hasOption("classlist")) return;
		try {
			String filename = opts.getOutputPath()+"/classes.lst";
			PrintStream out = new PrintStream(new FileOutputStream(filename));
			IMClass imclasses[] = repository.getAllClasses();
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				out.print("classes/");
				out.print(imclass.getClassName());
				out.println(".class");
			}
			out.flush();
			out.close();
		} catch (IOException ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * write UML diagrams of classes
	 */
	public void writeUMLDiagram() {
		if (!opts.createUMLDiagram()) return;
		try {
			IMClass imclasses[] = repository.getAllClasses();
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				imclass.writeUMLDiagram();
			}
		} catch (IOException ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	/**
	 * write dominator trees
	 */
	public void writeDomTree() {
		if (!opts.createDomTree()) return;
		try {
			IMClass imclasses[] = repository.getAllClasses();
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				imclass.writeDomTree();
			}
		} catch (Exception ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	private IntegerHashtable class_type_info;

	/**
	 * compute the class type info by a deep first enumeration of all classes.
	 */
	public void computeClassTypeInfo() throws CompileException {
		class_type_info = new IntegerHashtable();

		IMClass root = repository.getRootClass();
		if (root==null) throw new CompileException("class java.lang.Object not found!");
		int type_end = computeClassTypeInfoOfSubClass1(1,root);

		ClassTypeInfo cinfo = new ClassTypeInfo(root , 1, type_end-1);
		if (root!=null) root.setClassTypeInfo(cinfo);

		class_type_info.put(1, cinfo);
	}


	private int computeClassTypeInfoOfSubClass1(int start, IMClass current) throws CompileException {

		IMClass[] sub_classes_l1 = current.getSubClasses();

		/*
		 * Wir sortieren zu erst die Klassen auf der 1. Ebene.
		 *
		 * 1. Werden alle Array-Klassen nebeneinander angeordnet
		 *    damit wir auf einfache Art Array-Klassen von anderen
		 *    Klassen unterscheiden koennen.
		 *
		 * 2. Werden alle Klassen mit Interfaces zusammengefasst
		 *    damit koennen wir Speicherplatz in den dispatch-Tabellen
		 *    einsparren und vielleicht auch noch im Klassenspeicher.
		 */

		// we sort the classes first 
		int range_start = start + 1;

		int array_start = range_start;	
		int array_end = range_start;	
	
		IMClass[] sub_classes_sorted = new IMClass[sub_classes_l1.length];
		IMClass[] sub_classes_rest = new IMClass[sub_classes_l1.length];
		int pos = 0;
		int pos_rest = 0;

		// all array classes first
		for (int i=0;i<sub_classes_l1.length;++i) {
			IMClass cclass = sub_classes_l1[i];
			if (cclass.getClassName().charAt(0)=='[') {
				sub_classes_sorted[pos++] = cclass;
				array_end++;
			} else {
				sub_classes_rest[pos_rest++] = cclass;
			}
		}
	
		// all classes with interfaces next
		IMClass[] sub_classes_rest1 = new IMClass[pos_rest];
		int pos_rest1 = 0;
		if (!opts.hasOption("vt_fast_order")) {
			/*
			 * TODO:
			 * Dieser Algorithmus gruppiert die Klassen oder Klassenbaeume
			 * auf der ersten Ebene nach dem erst-besten Interface. Besser
			 * waere eine geschickte Auswahl. z.B. Interfaces nach der Anzahl
			 * der Methoden bevorzugen.
			 */
			Hashtable if_classes = new Hashtable();
			for (int i=0;i<pos_rest;++i) {
				IMClass cclass = sub_classes_rest[i];
				if (cclass.isInterface()) {
					sub_classes_rest1[pos_rest1++] = cclass;
				} else {
					String[] ifaces = collectInterfacesInSubClasses(cclass);
					if (ifaces!=null) {
						Vector if_group = (Vector)if_classes.get(ifaces[0]);
						if (if_group==null) {
							if_group=new Vector();
							if_classes.put(ifaces[0], if_group);
						}
						if_group.add(cclass);
					} else {
						sub_classes_rest1[pos_rest1++] = cclass;
					}
				}
			}
			Enumeration if_key = if_classes.keys();
			while (if_key.hasMoreElements()) {
				Vector ifs = (Vector)if_classes.get(if_key.nextElement());
				Enumeration if_enum = ifs.elements();
				while (if_enum.hasMoreElements()) {
					sub_classes_sorted[pos++] = (IMClass)if_enum.nextElement();
				}
			}
			if_classes=null;
		} else {
			/*
			 * Dieser Algorithmus gruppiert die Klassen auf der ersten
			 * Ebene nach einem ganz dummen Verfahren. Ist jedoch besser als
			 * nichts.
			 */
			for (int i=0;i<pos_rest;++i) {
				IMClass cclass = sub_classes_rest[i];
				if (cclass.isInterface()) {
					sub_classes_rest1[pos_rest1++] = cclass;
				} else {
					String[] ifaces = cclass.getInterfaceNames();
					if (ifaces!=null) {
						sub_classes_sorted[pos++] = cclass;
					} else {
						sub_classes_rest1[pos_rest1++] = cclass;
					}
				}
			}
		}
		sub_classes_rest=null;

		// all classes without interfaces next
		for (int i=0;i<pos_rest1;++i) {
			IMClass cclass = sub_classes_rest1[i];
			sub_classes_sorted[pos++] = cclass;
		}
		sub_classes_rest1=null;

		/*
		 * Nun kommt die eigentliche Vergabe der globalen Klassen-IDs
		 */

		for (int i=0; sub_classes_sorted!=null && i<sub_classes_sorted.length; i++) {
			IMClass cclass = sub_classes_sorted[i];

			int range_end = computeClassTypeInfoOfSubClass2(range_start, cclass);

			ClassTypeInfo cinfo = new ClassTypeInfo(cclass, range_start, range_end-1);
			cclass.setClassTypeInfo(cinfo);
			class_type_info.put(range_start, cinfo);

			range_start = range_end;
		}

		repository.setArrayTypeRange(array_start, array_end);

		return range_start;
	}

	private String[] collectInterfacesInSubClasses(IMClass cclass) throws CompileException {

		// TODO: find all interfaces not only the first once.
		
		String[] ifaces = cclass.getInterfaceNames();
		if (ifaces!=null) return ifaces;

		IMClass[] sub_classes = cclass.getSubClasses();
		for (int i=0; sub_classes!=null && i<sub_classes.length; i++) {
			ifaces = collectInterfacesInSubClasses(sub_classes[i]);
			if (ifaces!=null) return ifaces;
		}

		return null;
	}

	private int computeClassTypeInfoOfSubClass2(int start, IMClass current) throws CompileException {

		IMClass[] sub_classes = current.getSubClasses();

		int range_start = start + 1;

		for (int i=0; sub_classes!=null && i<sub_classes.length; i++) {
			IMClass cclass = sub_classes[i];

			int range_end = computeClassTypeInfoOfSubClass2(range_start, cclass);

			ClassTypeInfo cinfo = new ClassTypeInfo(cclass, range_start, range_end-1);
			cclass.setClassTypeInfo(cinfo);
			class_type_info.put(range_start, cinfo);

			range_start = range_end;
		}

		return range_start;
	}

	public void writeClassTree() {

		String filename = opts.getOutputClassTree();
		if (filename==null) return;

		try {
			PrintStream out = new PrintStream(new FileOutputStream(filename));

			out.println("digraph Flowgraph {\n");
			out.println("\tsize=\"7,10\"\n\tpage=\"8.25,11.75\"\n\torientation=landscape");
			//out.println("\tranksep=\"2.0\"");
			out.println("\tranksep=\"2.0 equally\"");
			out.println("\tnode [shape=box hight=.5 width=.5 fontsize=10];\n");
			//out.println("  node [shape=box fontsize=10];\n");

			IMClass root = repository.getClass("java/lang/Object");
			if (root==null) {
				writeClassAndSubClasses(out,"java/lang/Object");
			} else {
				writeClassAndSubClasses(out,root);
			}

			out.println();

			IMClass imclasses[] = repository.getAllClasses();
			out.println("\tsubgraph cluster_arrays {");
			out.println("\t\tstyle=filled");
			out.println("\t\tcolor=white");
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				if (!imclass.getClassName().startsWith("[")) continue;
				out.print("\t\t");
				out.print(imclass.getAlias());
				out.print(" [label=\"");
				out.print(imclass.getClassTypeInfo().toDigString());
				out.print("\" ");
				out.println("]");
			}
			out.println("\t}");

			out.println("\tsubgraph cluster_ifaces {");
			out.println("\t\tstyle=filled");
			out.println("\t\tcolor=white");
			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				if (imclass.getClassName().startsWith("[")) continue;
				if (!imclass.isInterface()) continue;
				out.print("\t\t");
				out.print(imclass.getAlias());
				out.print(" [label=\"");
				out.print(imclass.getClassTypeInfo().toDigString());
				out.println("\" shape=hexagon]");
			}
			out.println("\t}");

			out.println("\tsubgraph cluster_classes {");
			out.println("\t\tstyle=filled");
			out.println("\t\tcolor=white");

			for (int i=0;i<imclasses.length;i++) {
				IMClass imclass = imclasses[i];
				if (imclass.getClassName().startsWith("[")) continue;
				if (imclass.isInterface()) continue;
				out.print("\t\t");
				out.print(imclass.getAlias());
				out.print(" [label=\"");
				out.print(imclass.getClassTypeInfo().toDigString());
				out.print("\\nmethods: ");
				IMMethod[] methods = imclass.getMethods();
				out.print(methods.length);
				out.print("\" ");
				out.println("]");
			}
			out.println("\t}");

			out.println("}\n");

			out.flush();

			out.close();

		} catch (IOException ex) {
			Debug.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	private void writeClassAndSubClasses(PrintStream out, String rootname) throws IOException {
		IMClass subs[] = repository.getSubClasses(rootname);
		if (subs==null) return;

		for (int i=0;i<subs.length;i++) {
			out.print("\troot->");
			out.print(subs[i].getAlias());
			out.println(";");
			writeClassAndSubClasses(out,subs[i]);
		}

		out.print("  root [label=\"");
		out.print(rootname);
		out.println("\" rank=source]");
	}

	private void writeClassAndSubClasses(PrintStream out, IMClass imclass) throws IOException {
		IMClass subs[] = repository.getSubClasses(imclass.getClassName());

		for (int i=0;subs!=null && i<subs.length;i++) {
			out.print("\t");
			out.print(subs[i].getAlias());
			out.print("->");
			out.print(imclass.getAlias());
			out.println(";");
			writeClassAndSubClasses(out,subs[i]);
		}

		IMClass types[] = repository.findImplements(imclass.getClassName());
		for (int i=0;types!=null && i<types.length;i++) {
			out.print("  ");
			out.print(types[i].getAlias());
			out.print("->");
			out.print(imclass.getAlias());
			out.println(" [style=dotted];");
			//writeClassAndSubClasses(out,types[i]);
		}

	}
}
